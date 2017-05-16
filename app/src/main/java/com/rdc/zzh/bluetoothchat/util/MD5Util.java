package com.rdc.zzh.bluetoothchat.util;

import java.security.MessageDigest;

/**
 * Created by ZengZeHong on 2017/5/16.
 */

public class MD5Util {

    public final static String stringToMD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                //将每一个字节拆分为两个位
                byte byte0 = md[i];
                System.out.println(byte0);
                //一个低位
                str[k++] = hexDigits[byte0 >>> 4 & 0x0f];
                //一个高位
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
