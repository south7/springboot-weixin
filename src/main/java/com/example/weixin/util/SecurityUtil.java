package com.example.weixin.util;

import java.security.MessageDigest;

public class SecurityUtil {

    public  static String SHA1(String inStr){
        MessageDigest sha = null;
        byte[] byteArray=null;
        try {
            sha = MessageDigest.getInstance("SHA");
            byteArray = inStr.getBytes("UTF-8");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
