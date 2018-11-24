package cn.gin.webmvc.support.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtils extends org.apache.commons.lang.math.NumberUtils {

    public static int createInteger(String str, int defaultValue) {

        try {
            return Integer.valueOf(str);
        }
        catch(Exception exception) {
            return defaultValue;
        }
    }

    public static boolean isNumber(String str) {

        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);

        if (!isNum.matches()) {
            return false;
        }

        return true;
    }
}