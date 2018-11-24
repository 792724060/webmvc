package cn.gin.webmvc.context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.google.common.collect.Maps;

import cn.gin.webmvc.context.ApplicationContextConfig.Bean;
import cn.gin.webmvc.context.ApplicationContextConfig.Bean.Property;
import cn.gin.webmvc.context.ApplicationContextConfig.Context;
import cn.gin.webmvc.context.ApplicationContextConfig.Context.ComponentScan;
import cn.gin.webmvc.context.ApplicationContextConfig.Properties;
import cn.gin.webmvc.context.ApplicationContextConfig.Properties.Resource;
import cn.gin.webmvc.controller.Controller;
import cn.gin.webmvc.controller.ControllerMapping;
import cn.gin.webmvc.exception.ContextLoadException;
import cn.gin.webmvc.support.Constants;
import cn.gin.webmvc.support.util.StringUtils;
import cn.gin.webmvc.support.config.Global.PropertiesLoader;

/**
 * <p>At the start of the project, request this class to load the context configuration file
 * and initialize the application context.</p>
 */
public class ApplicationContext {

    /*
     * Context about
     */
    private String configPath;
    private ApplicationContextConfig config;
    private Map<String, String> classesQualifiedName;
    private Map<String, Object> beans;

    /*
     * Controller about.
     */
    private Map<String, Controller> controllers;
    private ControllerMapping controllerMapping;

    private final ClassLoader loader = this.getClass().getClassLoader();

    public ApplicationContext() {
        this.init();
    }

    public ApplicationContext(String configPath) {
        this.configPath = configPath;
        this.init();
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public ApplicationContextConfig getConfig() {
        return config;
    }

    public void setConfig(ApplicationContextConfig config) {
        this.config = config;
    }

    public Map<String, Controller> getControllers() {

        if (controllers == null) {
            controllers = Maps.newHashMap();
        }

        return controllers;
    }

    public void setControllers(Map<String, Controller> controllers) {
        this.controllers = controllers;
    }

    public Map<String, String> getClassesQualifiedName() {

        if (classesQualifiedName == null) {
            classesQualifiedName = Maps.newHashMap();
        }
        return classesQualifiedName;
    }

    public void setClassesQualifiedName(Map<String, String> classesQualifiedName) {
        this.classesQualifiedName = classesQualifiedName;
    }

    public ControllerMapping getControllerMapping() {
        return controllerMapping;
    }

    public void setControllerMapping(ControllerMapping controllerMapping) {
        this.controllerMapping = controllerMapping;
    }

    public Map<String, Object> getBeans() {

        if (beans == null) {
            beans = Maps.newHashMap();
        }
        return beans;
    }

    /**
     * <p>Need to load the context configuration and initialized the application context.</p>
     */
    private void init() {

        if (!this.loadConfig() || config == null) {
            throw new ContextLoadException("Occurred an exception when the ApplicationContextConfig was loaded.");
        }

        // ~ Load the properties
        List<Resource> resources = config.getProperties().getResources();

        for (Resource resource : resources) {
            String location = resource.getLocation();
            PropertiesLoader.loadProperties(location);
        }

        // ~ Initialized the component scan
        ComponentScan scan = config.getContext().getScan();
        String[] basePackage = scan.getBasePackage().split(Constants.MARK_COMMA);
        String targetAnnotation = scan.getTargetAnnotation();
        scanComponent(basePackage, targetAnnotation);

        // ~ Initialize all the beans

        //// Initialize the component scan beans
        for (Entry<String, String> beanDefinition : classesQualifiedName.entrySet()) {

            try {
                Object newInstance = Class.forName(beanDefinition.getValue()).newInstance();
                this.getBeans().put(beanDefinition.getKey(), newInstance);

                if (newInstance instanceof Controller) {
                    this.getControllers().put(beanDefinition.getKey(), (Controller) newInstance);
                }
            }
            catch (InstantiationException | IllegalAccessException | ClassNotFoundException exception) {
                throw new ContextLoadException(exception);
            }
        }

        //// Initialize the beans in configuration file
        for (Map.Entry<String, Bean> configBean : config.getBeans().entrySet()) {

            Bean bean = configBean.getValue();

            if ("true".equals(bean.getAnnotation())) {
                continue;
            }

            try {
                Object newInstance = Class.forName(bean.getClassPath()).newInstance();
                this.getBeans().put(bean.getName(), newInstance);

                if (newInstance instanceof Controller) {
                    this.getControllers().put(bean.getName(), (Controller) newInstance);
                }
            }
            catch (InstantiationException | IllegalAccessException | ClassNotFoundException exception) {
                throw new ContextLoadException(exception + ":" + bean.getName() + ":" + bean.getClassPath());
            }
        }

        // ~ Initialize the controller mapping
        if (this.controllerMapping == null) {
            this.controllerMapping = new ControllerMapping();
        }
        this.controllerMapping.setControllers(this.getControllers());
        this.controllerMapping.init();

        // ~ Handler the dependency injection
        Map<String, Bean> beansMap = config.getBeans();

        for (Map.Entry<String, Bean> bean : beansMap.entrySet()) {

            //// Get the bean's properties that need injected
            List<Bean.Property> properties = bean.getValue().getProperty();

            for (Bean.Property property : properties) {

                if (StringUtils.isEmpty(property.getValue()) && StringUtils.isEmpty(property.getRef())) {
                    // TODO Output to log: Cannot set the property[name:property.getName()] of bean[name:bean.getKey()]
                    continue;
                }

                Class<?> parameterType = null;

                if (!StringUtils.isEmpty(property.getValue())) {
                    parameterType = String.class;
                }

                if (!StringUtils.isEmpty(property.getRef())) {
                    parameterType = beans.get(property.getRef()).getClass();
                }

                //// Get the parameter's setter method and invoke it.
                final String propertyName = property.getName();
                final String setterName = StringUtils.getSetterDescriptor(propertyName);
                Object currentBean = beans.get(bean.getKey());
                Method setter = null;
                boolean injected = false;

                try {

                    try {
                        setter = currentBean.getClass().getDeclaredMethod(setterName, parameterType);
                    }
                    catch (NoSuchMethodException noSuchMethodException) {

                        try {
                            setter = currentBean.getClass().getDeclaredMethod(setterName, parameterType.getSuperclass());
                        }
                        catch (NoSuchMethodException noSuchMethodException2) {

                            try {
                                if (parameterType.getInterfaces().length > 0) {
                                    setter = currentBean.getClass().getDeclaredMethod(setterName, parameterType.getInterfaces()[0]);
                                }
                            }
                            catch (NoSuchMethodException noSuchMethodException3) {
                                // Cannot find the setter method
                            }
                        }
                    }

                    if (setter != null) {
                        setter.invoke(currentBean, beans.get(property.getRef()));
                        injected = true;
                    }
                }
                catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
                    throw new ContextLoadException(exception);
                }

                if (!injected) {
                    throw new ContextLoadException("The setter of " + setterName + " is not found");
                }
            }
        }
    }

    /**
     * Load the context configuration
     */
    private boolean loadConfig() {

        if (StringUtils.isEmpty(configPath)) {
            setConfigPath(Constants.MVC_CONFIGURATION_DEFAULT);
        }
        else {
            setConfigPath(StringUtils.replaceWhiteSpace(this.getConfigPath()));
        }

        InputStream in = PropertiesLoader.getResourceAsStream(this.getConfigPath());

        if (in == null) {
            throw new ContextLoadException(Constants.MVC_ERROR_CONFIGURATION_NO);
        }

        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;
        Element element;
        ApplicationContextConfig config;

        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse(in);
            element = document.getDocumentElement();
            config = new ApplicationContextConfig();
        }
        catch (SAXException | IOException | ParserConfigurationException exception) {
            throw new ContextLoadException(exception);
        }

        // ~ Get the properties configuration
        NodeList propertiesNodes = element.getElementsByTagName(Constants.MVC_CONFIGURATION_TAG_KEY_PROPERTIES);

        if (propertiesNodes != null && propertiesNodes.getLength() > 0) {

            if (propertiesNodes.item(0) instanceof Element) {

                Element propertiesEle = (Element) propertiesNodes.item(0);
                Properties properties = config.new Properties();
                NodeList childNodes = propertiesEle.getChildNodes();

                for (int i = 0; i < childNodes.getLength(); i++) {

                    if (childNodes.item(i) instanceof Element) {
                        Element resEle = (Element) childNodes.item(i);
                        Resource res = properties.new Resource();
                        res.setLocation(resEle.getAttribute(Constants.MVC_CONFIGURATION_TAG_KEY_ATTR_LOCATION));
                        res.setLoadSuccess(true);
                        properties.getResources().add(res);
                    }
                }
                config.setProperties(properties);
            }
        }

        // ~ Get the context configuration
        NodeList contextNodes = element.getElementsByTagName(Constants.MVC_CONFIGURATION_TAG_KEY_CONTEXT);

        if (contextNodes != null && contextNodes.getLength() > 0) {
            Element contextEle = (Element) contextNodes.item(0);
            Context context = config.new Context();
            NodeList childNodes = contextEle.getChildNodes();

            for (int i = 0; i < childNodes.getLength(); i++) {

                if (childNodes.item(i) instanceof Element) {
                    Element componentScanEle = (Element) childNodes.item(i);
                    ComponentScan componentScan = context.new ComponentScan();
                    componentScan.setBasePackage(componentScanEle.getAttribute(Constants.MVC_CONFIGURATION_TAG_KEY_ATTR_BASEPACKAGE));
                    context.setScan(componentScan);
                }
            }
            config.setContext(context);
        }

        // ~ Get the bean configuration
        NodeList beanNodes = element.getElementsByTagName(Constants.MVC_CONFIGURATION_TAG_KEY_BEAN);

        if (beanNodes == null) {
            return true;
        }

        int beansLen = beanNodes.getLength();
        Map<String, Bean> beans = Maps.newHashMap();

        for (int i = 0; i < beansLen; i++) {
            Element beanEle = (Element) beanNodes.item(i);
            Bean bean = config.new Bean();

            if (beans.containsKey(beanEle.getAttribute(Constants.MVC_CONFIGURATION_TAG_KEY_ATTR_NAME))) {
                continue;
            }

            //// Set the bean's attribute
            bean.setName(beanEle.getAttribute(Constants.MVC_CONFIGURATION_TAG_KEY_ATTR_NAME));
            bean.setClassPath(beanEle.getAttribute(Constants.MVC_CONFIGURATION_TAG_KEY_ATTR_CLASS));
            bean.setAnnotation(beanEle.getAttribute(Constants.MVC_CONFIGURATION_TAG_KEY_ATTR_ANNOTATION));

            //// Set the dependence
            NodeList childNodes = beanEle.getChildNodes();

            for (int j = 0; j < childNodes.getLength(); j++) {

                if (childNodes.item(j) instanceof Element) {
                    Element child = (Element) childNodes.item(j);
                    Property property = bean.new Property();
                    property.setName(child.getAttribute(Constants.MVC_CONFIGURATION_TAG_KEY_ATTR_NAME));
                    property.setValue(child.getAttribute(Constants.MVC_CONFIGURATION_TAG_KEY_ATTR_VALUE));
                    property.setRef(child.getAttribute(Constants.MVC_CONFIGURATION_TAG_KEY_ATTR_REF));
                    bean.getProperty().add(property);
                }
            }
            beans.put(bean.getName(), bean);
        }
        config.setBeans(beans);
        this.setConfig(config);

        return true;
    }

    /**
     * <p>Scan the class witch with the target annotation under the base package.</p>
     */
    private void scanComponent(String[] basePackage, String targetAnnotation) {

        if (basePackage == null || basePackage.length == 0) {
            return;
        }

        for (String pack : basePackage) {
            doScan(pack, getClassesQualifiedName());
        }
    }

    /**
     * <p>A component scan will recursively call this method to scan each folder.</p>
     */
    private Map<String, String> doScan(String basePackage, Map<String, String> nameList) {

        String noSlashPackage = StringUtils.replaceDotsToSlash(basePackage);
        URL url = loader.getResource(noSlashPackage);

        if (url == null) {
            throw new ContextLoadException("Execute the component scan failed on the base package: " + basePackage);
        }

        String filePath = StringUtils.getRootPath(url);
        File file = new File(filePath);
        String[] names = file.list();

        for (String name : (names == null ? StringUtils.EMPTY_ARRAY : names)) {

            if (name.endsWith(Constants.MVC_CONFIGURATION_SUFFIX_CLASSFILE)) {

                // This is a .class file
                String beanName = StringUtils.smallHumpFormat(name);
                String qualifiedName = basePackage + Constants.MARK_DOT + name.replace(Constants.MVC_CONFIGURATION_SUFFIX_CLASSFILE, StringUtils.EMPTY);
                nameList.put(beanName, qualifiedName);
            }
            else {

                // This is a directory
                doScan(basePackage + Constants.MARK_DOT + name, nameList);
            }
        }

        return nameList;
    }
}