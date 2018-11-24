package cn.gin.webmvc.support.util;

import cn.gin.webmvc.support.Constants;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Encode and decode tools.
 */
public class JCodec {

    public static String base64Encode(byte[] data) {

        return new String(Base64.getEncoder().encode(data));
    }

    public static String base64Encode(String data) {

        try {

            return new String(Base64.getEncoder().encode(data.getBytes(Constants.APPLICATION_ENCODING)));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] base64Decode(String data) {

        try {

            return Base64.getDecoder().decode(data.getBytes(Constants.APPLICATION_ENCODING));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String base64DecodeToString(String data) {

        try {

            return new String(Base64.getDecoder().decode(data.getBytes(Constants.APPLICATION_ENCODING)), Constants.APPLICATION_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String urlEncode(String data) {

        try {

            return URLEncoder.encode(data, Constants.APPLICATION_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String urlDecode(String data) {

        try {

            return URLDecoder.decode(data, Constants.APPLICATION_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String md5Encode(String data) {

        try {
            // 1. Get the message digest object.
            MessageDigest digest = MessageDigest.getInstance(Constants.CODEC_MD5);
            // 2. Encode the data to the decimal cipher text array.
            byte[] md5BytesValue = digest.digest(data.getBytes());
            // 3. Cast the decimal data to the hexadecimal data.
            return new BigInteger(1, md5BytesValue).toString(16).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateSalt() {

        return md5Encode(String.valueOf(RandomUtils.randomLong())).toUpperCase();
    }

    public static boolean validateSaltEncrypt(String client, String server, String salt) {

        ValidateUtils.assertNotNull(client, server, salt);

        if(client.length() != 32) {
            client = md5Encode(client);
        } else {
            client = client.toUpperCase();
        }

        return md5Encode(client + salt).equalsIgnoreCase(server);
    }
}