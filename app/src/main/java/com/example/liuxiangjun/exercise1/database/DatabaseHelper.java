package com.example.liuxiangjun.exercise1.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "calendar.db"; // 数据库名称
    private static final int version = 1; //数据库版本
    private Context context;
    // 单例模式
    private static DatabaseHelper instance = null;
    public static DatabaseHelper getInstance(Context context) {   //创建数据库单例对象
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }
    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, version);
    }
            //构造函数

    public ArrayList<CalendarEvent> getDaysOfEvents(){
        DatabaseHelper helper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from event",new String[]{});
        ArrayList<CalendarEvent> list = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            CalendarEvent ev = new CalendarEvent();
            ev.eid = cursor.getInt(0);
            ev.name = cursor.getString(1);
            ev.date = cursor.getString(2);
            list.add(ev);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    @Override
    //对于安卓开发，主键而且是整数类型的会自动递增，不用写auto_increment  非主键没有auto_increment功能
    //对于其他数据库  在需要时需要手动写auto_increment
    public void onCreate(SQLiteDatabase db) {
        //创建一个名字为event的表，其中主键eid是从0开始的序号，名字（NAME)是1024字节的，data是20字节的字符串
        String sql = "create table event(eid INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(1024) not null, date varchar(20) not null);";
        db.execSQL(sql);   //运行
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}    //执行更新操作
}
//关于Context  是Application和Activity的父类
//利用该Context对象去构建应用级别操作(application-level operations)
//安卓里很多函数都需要传一个Context给它，或者需要通过Context对象来进行调用
//例如activity就是现成的context对象 如果在activity类里面调用getResources，直接调用就行了，但是如果我在其他类里面调用，就得想办法传过去