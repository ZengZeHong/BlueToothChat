package com.rdc.zzh.bluetoothchat.receiver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rdc.zzh.bluetoothchat.vinterface.BlueToothInterface;

/**
 * Created by ZengZeHong on 2017/5/15.
 */

public class BlueToothReceiver extends BroadcastReceiver {
    private static final String TAG = "BlueToothReceiver";
    private BlueToothInterface mBlueToothInterface;
    public BlueToothReceiver(BlueToothInterface mBlueToothInterface){
        this.mBlueToothInterface = mBlueToothInterface;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //当扫描到设备的时候
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // 获取设备对象
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            //提取强度信息
            int rssi = intent.getExtras().getShort(BluetoothDevice.EXTRA_RSSI);
            Log.e(TAG, device.getName() + "\n" + device.getAddress() + "\n强度：" + rssi);
            if (mBlueToothInterface != null) {
                mBlueToothInterface.getBlutToothDevices(device , rssi);
            }
        } //搜索完成
        else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                .equals(action)) {
            if (mBlueToothInterface != null) {
                mBlueToothInterface.searchFinish();
            }
            Log.e(TAG, "onReceive: 搜索完成" );
        }
    }
}
