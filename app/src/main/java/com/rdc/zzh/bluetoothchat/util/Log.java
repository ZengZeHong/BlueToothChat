package com.rdc.zzh.bluetoothchat.util;

/**
 * Created by ZengZeHong on 2017/5/17.
 */

public class Log {
    private static final boolean D = true;
    public static void e(String tag , String msg){
        if(D)
            android.util.Log.e(tag,  msg );
    }
    public static void i(String tag , String msg){
        if(D)
            android.util.Log.i(tag,  msg );
    }
    public static void d(String tag , String msg){
        if(D)
            android.util.Log.d(tag,  msg );
    }
}
