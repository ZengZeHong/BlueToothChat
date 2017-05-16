package com.rdc.zzh.bluetoothchat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.zzh.bluetoothchat.R;
import com.rdc.zzh.bluetoothchat.bean.ChatInfo;
import com.rdc.zzh.bluetoothchat.bean.ChatLeftHolder;
import com.rdc.zzh.bluetoothchat.bean.ChatRightHolder;

import java.util.List;

/**
 * Created by ZengZeHong on 2017/5/16.
 */

public class RecyclerChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ChatInfo> list;
    private Context context;
    public List<ChatInfo> getList() {
        return list;
    }

    public void setList(List<ChatInfo> list) {
        this.list = list;
    }

    public RecyclerChatAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getItemViewType(int position) {
        return list.get(position).getTag();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ChatInfo.TAG_LEFT){
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_chat_left , null);
            return new ChatLeftHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_chat_right , null);
            return new ChatRightHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (list.get(position).getTag() == ChatInfo.TAG_LEFT) {
            ChatLeftHolder chatLeftHolder = (ChatLeftHolder) holder;
            chatLeftHolder.getTvName().setText(list.get(position).getName());
            chatLeftHolder.getTvContent().setText(list.get(position).getContent());
        } else{
            ChatRightHolder chatRightHolder = (ChatRightHolder) holder;
            chatRightHolder.getTvName().setText(list.get(position).getName());
            chatRightHolder.getTvContent().setText(list.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
