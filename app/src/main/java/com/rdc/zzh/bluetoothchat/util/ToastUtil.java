package com.rdc.zzh.bluetoothchat.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ZengZeHong on 2017/5/16.
 */

public class ToastUtil {
    private static Toast toast;
    public static void showText(Context context , String s){
        if(toast == null)
            toast = Toast.makeText(context , s , Toast.LENGTH_SHORT);
        else {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(s);
        }
        toast.show();
    }
}
