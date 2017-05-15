package com.rdc.zzh.bluetoothchat.bean;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rdc.zzh.bluetoothchat.R;


/**
 * Created by ZengZeHong on 2017/5/10.
 */

public class BlueToothHolder extends RecyclerView.ViewHolder{
    private TextView tvName;
    private TextView tvLevel;
    private TextView tvMac;
    private RelativeLayout rlClick;
    public BlueToothHolder(View itemView) {
        super(itemView);
        tvMac = (TextView)itemView.findViewById(R.id.tv_mac);
        tvLevel = (TextView) itemView.findViewById(R.id.tv_level);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        rlClick = (RelativeLayout) itemView.findViewById(R.id.rl_click);
    }

    public RelativeLayout getRlClick() {
        return rlClick;
    }

    public void setRlClick(RelativeLayout rlClick) {
        this.rlClick = rlClick;
    }

    public TextView getTvName() {
        return tvName;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }

    public TextView getTvLevel() {
        return tvLevel;
    }

    public void setTvLevel(TextView tvLevel) {
        this.tvLevel = tvLevel;
    }

    public TextView getTvMac() {
        return tvMac;
    }

    public void setTvMac(TextView tvMac) {
        this.tvMac = tvMac;
    }
}
