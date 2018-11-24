package cn.gin.webmvc.support.util;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {

    public static void release(Closeable closeable) {

        try {
            closeable.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
