package com.example.liuxiangjun.exercise1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.liuxiangjun.exercise1.database.CalendarEvent;
import com.example.liuxiangjun.exercise1.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AgendaListAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<CalendarEvent> lists;
    AgendaListAdapter(Activity context){
        this.context = context;
        lists = DatabaseHelper.getInstance(context).getDaysOfEvents();
//        Collections.sort(lists, new Comparator<CalendarEvent>() {
//            @Override
//            public int compare(CalendarEvent o1, CalendarEvent o2) {
//                return ;
//            }
//        });
    }
    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) { return null; }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = convertView;
        if (convertView == null) {
            v = inflater.inflate(R.layout.schedule_item,null);
        }
        TextView dateTV = v.findViewById(R.id.datetime);
        dateTV.setText(lists.get(position).date);        //获取日期

        TextView tv = v.findViewById(R.id.agenda);
        tv.setText(lists.get(position).name);            //获取相应日程

        return v;
    }
}
