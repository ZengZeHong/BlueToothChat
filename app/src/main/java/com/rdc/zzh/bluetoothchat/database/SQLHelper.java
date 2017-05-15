package com.rdc.zzh.bluetoothchat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ZengZeHong on 2017/4/22.
 */

public class SQLHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_WIFI = "create table wifi_table" +
            "(id int ," + "i1 varchar(100) ,  i2 varchar(100) , i3 varchar(100) , " +
            "i4 varchar(100) , i5 varchar(100) , i6 varchar(100) , i7 varchar(100) , " +
            "i8 varchar(100) , i9 varchar(100) ,i10 varchar(100) ,i11 varchar(100) ,i12 varchar(100)) ";
    private static final String CREATE_TABLE_BLUETOOTH = "create table blue_tooth_table" +
            "(id int ," + "i1 varchar(100) ,  i2 varchar(100) , i3 varchar(100) , " +
            "i4 varchar(100) , i5 varchar(100) , i6 varchar(100) , i7 varchar(100) , " +
            "i8 varchar(100) , i9 varchar(100) ,i10 varchar(100) ,i11 varchar(100) ,i12 varchar(100)) ";
    private String name;
    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.name = name;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表，表名为wifi_table
        if(name.equals("blue_tooth_db"))
            db.execSQL(CREATE_TABLE_BLUETOOTH);
        else
        db.execSQL(CREATE_TABLE_WIFI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
