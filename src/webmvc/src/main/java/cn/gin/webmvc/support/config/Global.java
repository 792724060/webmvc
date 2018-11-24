package cn.gin.webmvc.support.config;

import cn.gin.webmvc.support.Constants;
import cn.gin.webmvc.support.util.IOUtils;
import cn.gin.webmvc.support.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * The application global configuration
 */
public class Global {

    /**
     * Storage the global configurations
     */
    private static final Properties props = new Properties();

    public static String getConfig(String key) {
        String value = (String) props.get(key);

        if (value == null) {
            PropertiesLoader.loadProperties(Constants.SYSTEM_CONFIG);
            value = props.getProperty(key);
        }

        return value;
    }

    public static void putConfig(String key, String value) {

        if(StringUtils.isEmpty(key)) {

            throw new IllegalArgumentException("Connot put the null key or value.");
        }

        props.put(key, value);
    }

    /**
     * The global configuration file loader.
     */
    public static class PropertiesLoader {

        public static InputStream getResourceAsStream(String resource) {

            InputStream in = null;

            try {
                in = PropertiesLoader.class.getClassLoader().getResourceAsStream(resource);
            }
            catch(Exception exception) {
                System.out.println("Load resource faild: " + resource);
                return null;
            }

            return in;
        }

        public static boolean loadProperties(String resource) {

            InputStream in = getResourceAsStream(resource);

            if (in == null) {
                return false;
            }

            try {

                if(in != null && in.available() > 0) {
                    props.load(new InputStreamReader(in, Constants.APPLICATION_ENCODING));
                    return true;
                }
            }
            catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            finally {
                IOUtils.release(in);
            }

            return false;
        }
    }
}