package cn.gin.webmvc.support.util;

public class ValidateUtils {

    /**
     * <p>Check whether the parameters in the parameter list are empty.If one is empty,
     * the runtime exception is thrown.And the exception message will describe the index
     * of the parameter, start with 0, 1, 2...</p>
     *
     * @param args - The parameters list.
     */
    public static void assertNotNull(Object... args) {

        for (int i = 0; i < args.length; i++) {

            if (args[i] == null) {
                throw new RuntimeException("The argument[" + i + "] is null.");
            }

        }
    }

    /**
     * <p>Check whether the parameters in the parameter list are empty.If one is empty,
     * the runtime exception is thrown.And the exception message will describe the index
     * of the parameter, start with 0, 1, 2...</p>
     *
     * @param args - The parameters list.
     */
    public static void assertNotEmpty(String... args) {

        for (int i = 0; i < args.length; i++) {

            if (StringUtils.isEmpty(args[i])) {
                throw new RuntimeException("The argument[" + i + "] is empty.");
            }

        }
    }
}