package com.rdc.zzh.bluetoothchat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.zzh.bluetoothchat.R;
import com.rdc.zzh.bluetoothchat.bean.BlueTooth;
import com.rdc.zzh.bluetoothchat.bean.BlueToothHolder;
import com.rdc.zzh.bluetoothchat.bean.ToastHolder;

import java.util.List;


/**
 * Created by ZengZeHong on 2017/5/10.
 */

public class RecyclerBlueToothAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BlueTooth> list;
    private Context context;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public RecyclerBlueToothAdapter(Context context){
        this.context = context;
    }
    public void setWifiData(List<BlueTooth> list){
        this.list = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == BlueTooth.TAG_NORMAL) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_blue_tooth, parent, false);
            return new BlueToothHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_toast, parent, false);
            return new ToastHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getTag();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(list.get(position).getTag() == BlueTooth.TAG_NORMAL) {
            BlueToothHolder blueToothHolder = (BlueToothHolder)holder;
            BlueTooth result = list.get(position);
            blueToothHolder.getTvLevel().setText(result.getRssi());
            blueToothHolder.getTvName().setText(result.getName());
            blueToothHolder.getTvMac().setText(result.getMac());
            blueToothHolder.getRlClick().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null)
                        onItemClickListener.onItemClick(position);
                }
            });
        }else{
            ToastHolder blueToothHolder = (ToastHolder)holder;
            BlueTooth result = list.get(position);
            blueToothHolder.getTvToast().setText(result.getName());
        }
    }

    @Override
    public int getItemCount() {
        if(list == null)
            return 0;
        else return list.size();
    }
}
