package com.example.blockchaincertificate.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class HexUtils {

    public static String strToHex(String str) throws UnsupportedEncodingException {
        return String.format("%x", new BigInteger(1, str.getBytes("UTF-8")));
    }

}