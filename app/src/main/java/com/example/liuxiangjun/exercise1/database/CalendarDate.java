package com.example.liuxiangjun.exercise1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;


public class CalendarDate {
    private int year;
    private int month;
    private int dayOfMonth;
    private Context context;

    public CalendarDate(Context context, int year, int month, int dayOfMonth) {
        this.context = context;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public CalendarDate(Context context, Calendar cal) {     //获取日期 年月日
        this(context, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }
    //把年月日变成字符串连起来
    @Override
    public String toString() {
        return this.year + "-" + (this.month + 1) + "-" + this.dayOfMonth;
    }
    //获取一天的所有日程
    public ArrayList<CalendarEvent> getAllEvents() {
        DatabaseHelper helper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        //Cursor是每一行的集合
        Cursor cursor = db.rawQuery("select eid, name from event where date=?", new String[]{this.toString()});
        ArrayList<CalendarEvent> list = new ArrayList<>();
        cursor.moveToFirst();        //定位到第一行
        while(!cursor.isAfterLast()) {
            CalendarEvent ev = new CalendarEvent();       //添加日历事件
            ev.eid = cursor.getInt(0);
            ev.name = cursor.getString(1);
            list.add(ev);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public int numberOfEvents() {
        // return !getAllEvents().isEmpty(); // 这样查询效率很慢，因为要一个一个取出所有的日程但又不需要使用它们
        DatabaseHelper helper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        //用这个cursor去遍历符合条件的数据项
        Cursor cursor = db.rawQuery("select eid, name from event where date=?", new String[]{this.toString()});
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //添加日程
    public void addEvent(String eventName) {
        DatabaseHelper helper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

//        ContentValues values = new ContentValues();   //添加用到ContentValues
//        values.put("name", eventName);
//        values.put("date", this.toString());
//        db.insert("event", null, values);
//        Log.i("addEvent", this.toString());

        db.execSQL("insert into event (name, date) values (?, ?)", new String[]{eventName, this.toString()});
    }
    //删除日程
    public void deleteEvent(int eid) {
        DatabaseHelper helper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        int result = db.delete("event", "eid=?", new String[]{String.valueOf(eid)});     //删除时可直接进行
        Log.i("deleteEvent", String.valueOf(eid) + ", " + (result == 1 ? "SUCCESS" : "FAIL"));
    }
}
