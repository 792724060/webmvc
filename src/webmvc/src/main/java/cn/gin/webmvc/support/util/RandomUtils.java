package cn.gin.webmvc.support.util;

import cn.gin.webmvc.support.Constants;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class RandomUtils {

    private static SecureRandom random = new SecureRandom();

    /**
     * Generate the unique string by JDK UUID.
     */
    public static String uuid() {

        return UUID.randomUUID().toString().replaceAll(Constants.SEPARATOR_CENTER_LINE, Constants.EMPTY);
    }

    public static long randomLong() {

        return Math.abs(random.nextLong());
    }

    public static Serializable idGenerator() {

        return RandomUtils.uuid();
    }

    public static long randomWithSeed(long seed) {

        return Math.abs(new Random(seed).nextLong());
    }
}