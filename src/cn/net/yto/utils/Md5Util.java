package cn.net.yto.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class Md5Util {

    public static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private static String MD5 = "MD5";

    private static String MD5_SUFFIX = "123456";

    private Md5Util() {
    }

    public static String md5(String s) {

        MessageDigest sMd5MessageDigest = null;
        try {
            sMd5MessageDigest = MessageDigest.getInstance(MD5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        sMd5MessageDigest.reset();
        try {
            sMd5MessageDigest.update(s.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
        }

        byte digest[] = sMd5MessageDigest.digest();

        StringBuilder sStringBuilder = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            final int b = digest[i] & 255;
            if (b < 16) {
                sStringBuilder.append('0');
            }
            sStringBuilder.append(Integer.toHexString(b));
        }

        String ss = sStringBuilder.toString();
        sStringBuilder.delete(0, sStringBuilder.length() - 1);
        return ss;
    }

    public static String md5(String s, String imei) {
        return md5(s + imei + MD5_SUFFIX);
    }
    
    public static String md5ForFile(String path) throws Exception {
        InputStream fis;
        fis = new BufferedInputStream(new FileInputStream(path));
        byte[] buffer = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(MD5);
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            md5.update(buffer, 0, numRead);
        }
        fis.close();
        return toHexString(md5.digest());
    }

    public static String md5ForFile(File file) {
        MessageDigest md5 = null;
        try {
            InputStream fis;
            fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            md5 = MessageDigest.getInstance(MD5);
            int numRead = 0;
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
        } catch (Exception e) {
            // TODO handle exception
            e.printStackTrace();
        }
        return toHexString(md5.digest());
    }

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }

}
