package com.knowledge.mnlin.viewutilmixed.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created on 2018/1/18
 * function : 特殊数据加密
 *
 * @author ACChain
 */

public final class EncodeUtils {
    public static final String SHA_1 = "SHA-1";
    public static final String MD5 = "MD5";
    public static final String SHA_256 = "SHA-256";
    public static final String SHA_384 = "SHA-384";

    public static String encode(String source, String type) {
        if (source == null || type == null) {
            return "";
        }

        MessageDigest md;
        String strDes;
        byte[] bt = source.getBytes();
        try {
            md = MessageDigest.getInstance(type);
            md.update(bt);
            strDes = bytes2Hex(md.digest());
            return strDes;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("签名失败！");
            return null;
        }
    }

    static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
