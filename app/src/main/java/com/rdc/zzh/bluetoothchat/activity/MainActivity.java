package com.rdc.zzh.bluetoothchat.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.rdc.zzh.bluetoothchat.R;
import com.rdc.zzh.bluetoothchat.adapter.RecyclerBlueToothAdapter;
import com.rdc.zzh.bluetoothchat.bean.BlueTooth;
import com.rdc.zzh.bluetoothchat.database.SQLHelper;
import com.rdc.zzh.bluetoothchat.receiver.BlueToothReceiver;
import com.rdc.zzh.bluetoothchat.vinterface.BlueToothInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity  implements CompoundButton.OnCheckedChangeListener , BlueToothInterface , RecyclerBlueToothAdapter.OnItemClickListener{
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private Switch st;
    private BluetoothAdapter mBluetoothAdapter;
    private Timer timer;
    private WifiTask task;
    private RecyclerBlueToothAdapter recyclerAdapter;
    private SQLHelper sqlHelper;
    private List<BlueTooth> list = new ArrayList<>();
    private BlueToothReceiver mReceiver;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == BluetoothAdapter.STATE_ON){
                st.setText("蓝牙已开启");
                Log.e(TAG, "onCheckedChanged: startIntent" );
               //自动刷新
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                        onRefreshListener.onRefresh();
                    }
                }, 300);
            }else if(msg.what == BluetoothAdapter.STATE_OFF){
                st.setText("蓝牙已关闭");
                recyclerAdapter.setWifiData(null);
                recyclerAdapter.notifyDataSetChanged();
            }
            timer.cancel();
            timer = null;
            task = null;
            st.setClickable(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        st = (Switch) findViewById(R.id.st);

        st.setOnCheckedChangeListener(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        recyclerAdapter = new RecyclerBlueToothAdapter(this);
        recyclerAdapter.setWifiData(list);
        recyclerAdapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); //获取本地蓝牙实例
        //判断蓝牙是否开启来设置状态
        if(mBluetoothAdapter.isEnabled()){
            //已经开启
            st.setChecked(true);
            st.setText("蓝牙已开启");
        }else {
            st.setChecked(false);
            st.setText("蓝牙已关闭");
        }

        sqlHelper = new SQLHelper(MainActivity.this ,  "blue_tooth_db" ,null , 1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new BlueToothReceiver(this);
        //注册扫描设备广播
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked == true){
            if (mBluetoothAdapter.getState() != BluetoothAdapter.STATE_ON) {
                mBluetoothAdapter.enable();  //打开蓝牙
                st.setText("正在开启蓝牙");
                Toast.makeText(this, "正在开启蓝牙", Toast.LENGTH_SHORT).show();
            }
        }else {
            if (mBluetoothAdapter.getState() != BluetoothAdapter.STATE_OFF) {
                mBluetoothAdapter.disable();  //打开蓝牙
                st.setText("正在关闭Wifi");
                Toast.makeText(this, "正在关闭蓝牙", Toast.LENGTH_SHORT).show();
            }
        }
        st.setClickable(false);
        if(timer == null || task == null) {
            timer = new Timer();
            task = new WifiTask();
            task.setChecked(isChecked);
            timer.schedule(task , 0 , 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        close();
    }

    private void close(){
        if(timer != null)
            timer.cancel();
        //取消扫描
        mBluetoothAdapter.cancelDiscovery();
        swipeRefreshLayout.setRefreshing(false);
        unregisterReceiver(mReceiver);
    }
    /**
     * RecyclerView Item 点击处理
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this , ChatActivity.class);
        BlueTooth blueTooth = list.get(position);
        intent.putExtra(ChatActivity.DEVICE_MAC_INTENT , blueTooth.getMac());
        intent.putExtra(ChatActivity.DEVICE_NAME_INTENT , blueTooth.getName());
        startActivity(intent);
        //关闭其他资源
        close();
    }


    private class WifiTask extends TimerTask {
        private boolean isChecked;
        public void setChecked(boolean isChecked){
            this.isChecked = isChecked;
        }

        @Override
        public void run() {
            if(isChecked){
                if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_ON)
                    handler.sendEmptyMessage(BluetoothAdapter.STATE_ON);
            }else
            {
                if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF)
                    handler.sendEmptyMessage(BluetoothAdapter.STATE_OFF);
            }
        }
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
                list.clear();
                //扫描的是已配对的
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    list.add(new BlueTooth("已配对的设备" ,  BlueTooth.TAG_TOAST));
                    for (BluetoothDevice device : pairedDevices) {
                        Log.e(TAG, device.getName() + "\n" + device.getAddress());
                        list.add(new BlueTooth(device.getName() , device.getAddress() , ""));
                    }
                    list.add(new BlueTooth("已扫描的设备" , BlueTooth.TAG_TOAST));
                } else {
                    Toast.makeText(getApplicationContext(), "没有找到已匹对的设备！", Toast.LENGTH_SHORT).show();
                    list.add(new BlueTooth("已扫描的设备" , BlueTooth.TAG_TOAST));
                }
                recyclerAdapter.notifyDataSetChanged();
                //开始扫描设备
                mBluetoothAdapter.startDiscovery();
                Toast.makeText(MainActivity.this, "开始扫描设备", Toast.LENGTH_SHORT).show();
            }else{
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "请开启蓝牙", Toast.LENGTH_SHORT).show();
            }
        }
    };
    //数据保存
    public void save(View view){
     /*   if(list  != null){
            SQLiteDatabase db = sqlHelper.getWritableDatabase();
            int row = Integer.parseInt(etRow.getText().toString());
            int line = Integer.parseInt(etLine.getText().toString());
            //数据保存格式
            StringBuffer sb = new StringBuffer();
            sb.append("(");
            for(BlueTooth blueTooth : list){
                sb.append(blueTooth.getName() + " : " + blueTooth.getRssi());
                sb.append(" , ");
            }
            sb.replace(sb.toString().length() - 2 , sb.toString().length() - 1 , ")");
            //是否有对应的记录
            Cursor cursor = db.query("blue_tooth_table", null, "id=?", new String[]{line + ""}, null, null, null);
            //表示一开始没有数据，则插入一条数据
            if(!cursor.moveToNext()){
                ContentValues contentValues = new ContentValues();
                contentValues.put("id" , line);
                contentValues.put("i" + row , sb.toString());
                db.insert("blue_tooth_table" , null , contentValues);
            }else{
                ContentValues contentValues = new ContentValues();
                contentValues.put("i" + row, sb.toString());
                String [] whereArgs = {String.valueOf(line)};
                db.update("blue_tooth_table" , contentValues , "id=?" , whereArgs);
            }
            Toast.makeText(MainActivity.this , "保存成功" , Toast.LENGTH_SHORT).show();
        }*/
    }

    /**
     * 扫描设备回调监听
     * @param device
     * @param rssi
     */
    @Override
    public void getBlutToothDevices(BluetoothDevice device , int rssi) {
        list.add(new BlueTooth(device.getName() , device.getAddress() , rssi + ""));
        //更新UI
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void searchFinish() {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(MainActivity.this , "扫描完成" , Toast.LENGTH_SHORT).show();
    }
}
