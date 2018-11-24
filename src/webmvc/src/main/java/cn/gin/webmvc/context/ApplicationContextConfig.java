package cn.gin.webmvc.context;

import cn.gin.webmvc.exception.ContextLoadException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * The <code>ApplicationContextConfig</code> stored the application context profiles
 */
public class ApplicationContextConfig {

    private Properties properties;

    /**
     * Used to map XML configuration information to java objects
     */
    private Context context;

    /**
     * Used to stored all the beans that were configured in the application context configuration
     */
    private Map<String, Bean> beans;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Map<String, Bean> getBeans() {

        if (beans == null) {
            beans = Maps.newHashMap();
        }

        return beans;
    }

    public void setBeans(Map<String, Bean> beans) {
        this.beans = beans;
    }

    public class Properties {

        private List<Resource> resources;

        public List<Resource> getResources() {

            if (resources == null) {
                resources = Lists.newArrayList();
            }

            return resources;
        }

        public void setResources(List<Resource> resources) {
            this.resources = resources;
        }

        public class Resource {

            private String location;
            private boolean loadSuccess;

            public String getLocation() {
                return location;
            }
            public void setLocation(String location) {

                if (StringUtils.isEmpty(location)) {
                    throw new ContextLoadException("Cannot use the empty value for the attribute: location.");
                }

                this.location = location;
            }
            public boolean isLoadSuccess() {
                return loadSuccess;
            }
            public void setLoadSuccess(boolean loadSuccess) {
                this.loadSuccess = loadSuccess;
            }
        }
    }

    public class Context {

        private ComponentScan scan;

        public ComponentScan getScan() {
            return scan;
        }

        public void setScan(ComponentScan scan) {
            this.scan = scan;
        }

        public class ComponentScan {

            private String basePackage;
            private String targetAnnotation;

            public String getBasePackage() {
                return basePackage;
            }

            public void setBasePackage(String basePackage) {
                this.basePackage = basePackage;
            }

            public String getTargetAnnotation() {
                return targetAnnotation;
            }

            public void setTargetAnnotation(String targetAnnotation) {
                this.targetAnnotation = targetAnnotation;
            }
        }
    }

    public class Bean {

        private String name;
        private String classPath;
        private String annotation;
        private List<Property> property;

        public String getName() {
            return name;
        }

        public void setName(String name) {

            if (StringUtils.isEmpty(name)) {
                throw new ContextLoadException("Cannot use the empty value for the attribute: name.");
            }

            this.name = name;
        }

        public String getClassPath() {
            return classPath;
        }

        public void setClassPath(String classPath) {

            if (StringUtils.isEmpty(classPath)) {
                throw new ContextLoadException("Cannot use the empty value for the attribute: classPath.");
            }

            this.classPath = classPath;
        }

        public String getAnnotation() {
            return annotation;
        }

        public void setAnnotation(String annotation) {
            this.annotation = annotation;
        }

        public List<Property> getProperty() {

            if (property == null) {
                property = Lists.newArrayList();
            }

            return property;
        }

        public void setProperty(List<Property> property) {
            this.property = property;
        }

        public class Property {

            private String name;
            private String value;
            private String ref;

            public String getName() {
                return name;
            }

            public void setName(String name) {

                if (StringUtils.isEmpty(name)) {
                    throw new ContextLoadException("Cannot use the empty value for the attribute: name.");
                }

                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getRef() {
                return ref;
            }

            public void setRef(String ref) {
                this.ref = ref;
            }
        }
    }
}