package cn.gin.webmvc.support.util;

import cn.gin.webmvc.support.Constants;

import java.net.URL;

/**
 * <p><code>org.apache.commons.lang.StringUtils</code>-based string util class that provides more convenient methods
 * \related to the project</p>
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

    public static final String[] EMPTY_ARRAY = new String[0];

    /**
     * <p>Return the value of str and appendStr if the str is not empty.</p>
     * <p>Return the value of EMPTY if the str is empty.</p>
     *
     * @param str - The parameter that needs to operate.
     * @param appendStr - If the str is not EMPTY, append it to the end of str.
     *
     * @return The value of [str and appendStr] or [EMPTY].
     */
    public static String appendTo(String str, String appendStr) {

        return isEmpty(str) ? EMPTY : (str + appendStr);
    }

    /**
     * Remove all the white space
     *
     * @param str - The String to handle, may be null
     * @return The string that has no white space
     */
    public static String replaceWhiteSpace(String str) {

        if (str == null) {
            return str;
        }

        return str.replaceAll(Constants.MARK_WHITESPACE, StringUtils.EMPTY);
    }

    /**
     * Replace all the dots to slash, if the str is blank, empty string will be returned
     *
     * @param str - The String to handle, may be null
     * @return If the str is blank, empty string will be returned
     */
    public static String replaceDotsToSlash(String str) {

        if (isBlank(str)) {

            return EMPTY;
        }

        return str.replaceAll("\\.", "/");
    }

    /**
     * <p>Format a string as the small hump format.</p>
     *
     * @param str - The string that should be formatted.
     * @return The small hump format string of the target parameter.
     */
    public static String smallHumpFormat(String str) {

        if (isEmpty(str)) {
            return str;
        }

        String format = str.substring(0, 1).toLowerCase() + str.substring(1);

        if (format.contains(Constants.MVC_CONFIGURATION_SUFFIX_CLASSFILE)) {
            format = format.replace(Constants.MVC_CONFIGURATION_SUFFIX_CLASSFILE, EMPTY);
        }

        return format;
    }

    /**
     * <p>Perform a full outer join on the given two string arrays, taking the result of each join as a new string.
     * Return the final string array</p>
     *
     * @param args1 - The first array
     * @param args2 - The second array
     *
     * @return The finally result set is a string array contains all the join result
     */
    public static String[] toArrayOuterJoin(String[] args1, String[] args2) {

        if (args1 == null || args1.length == 0) {

            return args2;
        }

        if (args2 == null || args2.length == 0) {

            return args1;
        }

        String[] res = new String[args1.length * args2.length];

        for (int i = 0; i < args1.length; i++) {

            for (int j = 0; j < args2.length; j++) {

                res[i + j] = args1[i] + args2[j];
            }
        }

        return res;
    }

    /**
     * <p>According to the naming convention of the java bean, get the name of the setter method
     * corresponding to a field.</p>
     *
     * @param field - Need to get the field name of the setter method name
     * @return The setter name of the given field
     */
    public static String getSetterDescriptor(String field) {

        if (isEmpty(field)) {
            return null;
        }

        String firstChar = field.substring(0, 1).toUpperCase();
        String restOf = field.substring(1);
        String setterName = "set" + firstChar + restOf;

        return setterName;
    }

    public static String getRootPath(URL url) {

        if (url == null) {
            return null;
        }

        final String fileUrl = url.getFile();
        final int pos = fileUrl.indexOf('!');

        if (pos == -1) {
            return fileUrl;
        }

        return fileUrl.substring(5, pos);
    }
}
