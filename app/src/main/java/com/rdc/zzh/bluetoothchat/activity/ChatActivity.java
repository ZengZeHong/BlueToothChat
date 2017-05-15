package com.rdc.zzh.bluetoothchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.rdc.zzh.bluetoothchat.R;

/**
 * Created by ZengZeHong on 2017/5/15.
 */

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    public static final String DEVICE_MAC_INTENT = "device_mac";
    public static final String DEVICE_NAME_INTENT = "device_name";
    private String deviceName; //名字
    private String deviceMac; //地址
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        deviceName =  intent.getStringExtra(DEVICE_NAME_INTENT);
        deviceMac = intent.getStringExtra(DEVICE_MAC_INTENT);
        setTitle(deviceName);
        Log.e(TAG, "onCreate: mac " + deviceName);
        Log.e(TAG, "onCreate: name " + deviceMac) ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //返回事件监听
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
