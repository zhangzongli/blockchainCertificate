package com.example.blockchaincertificate.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class HexUtils {

    public static String strToHex(String str) throws UnsupportedEncodingException {
        return String.format("%x", new BigInteger(1, str.getBytes("UTF-8")));
    }

    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }
}