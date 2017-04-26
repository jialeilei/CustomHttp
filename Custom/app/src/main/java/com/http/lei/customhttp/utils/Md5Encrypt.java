package com.http.lei.customhttp.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lei on 2017/4/26.
 * Md5加密
 */
public class Md5Encrypt {

    public static String generateCode(String url){
        if (TextUtils.isEmpty(url)){
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");

            digest.update(url.getBytes());
            byte[] cipher = digest.digest();

            for (byte b : cipher) {
                String hexString = Integer.toHexString(b & 0xff);
                buffer.append(hexString.length() == 1?"0"+hexString:hexString);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }
}
