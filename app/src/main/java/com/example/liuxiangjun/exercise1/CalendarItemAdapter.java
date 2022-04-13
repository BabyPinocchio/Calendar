package com.example.liuxiangjun.exercise1;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liuxiangjun.exercise1.database.CalendarDate;
import com.example.liuxiangjun.exercise1.database.CalendarEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CalendarItemAdapter extends BaseAdapter {
    private Activity context;
    private GridView gridView;
    private Calendar calendar;
    //因为要根据当前的月份和年份显示日期  因此需要传入Calendar类的
    CalendarItemAdapter(Activity context, GridView gridView,Calendar calendar){
        this.context = context;
        this.gridView = gridView;
        this.calendar = calendar;
    }
    Calendar getFirstOfPage(){
        Calendar c1 = (Calendar) calendar.clone();
        int weekday = c1.get(Calendar.DAY_OF_WEEK) - 1;//1到7   1表示周日
        c1.add(Calendar.DAY_OF_MONTH, -weekday);
        Log.d("firstOfPage", c1.getTimeInMillis() % (1000 * 60 * 60 * 24) + "");
        return c1;
    }
    //获取月日、星期
    Calendar getFirstOfNextPage(){
        Calendar c1 = (Calendar) calendar.clone();
        c1.roll(Calendar.DAY_OF_MONTH,-1);
        int weekday = 8 - c1.get(Calendar.DAY_OF_WEEK);
        c1.add(Calendar.DAY_OF_MONTH, weekday);
        Log.d("lastOfPage", c1.getTimeInMillis() % (1000 * 60 * 60 * 24) + "");
        return c1;
    }

    @Override
    public int getCount() {
        long startTime = getFirstOfNextPage().getTimeInMillis();
        long endTime = getFirstOfPage().getTimeInMillis();
        return (int)((startTime - endTime) / 1000 / 60 / 60 / 24);   //得到该页所能显示的总天数
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(R.layout.calender_item,null);
        TextView tv = v.findViewById(R.id.date);
        Calendar c2 = getFirstOfPage();
        c2.add(Calendar.DAY_OF_MONTH,i);  //i是当前页面的天数总和（i是从0到天数总和）
        tv.setText(String.valueOf(c2.get(Calendar.DAY_OF_MONTH)));    //得到的是当前月的日期

        final CalendarDate date = new CalendarDate(context, c2);
        LinearLayout ll = v.findViewById(R.id.events);
        ll.removeAllViews();
        List<CalendarEvent> events = date.getAllEvents();

        //用for循环代替Adapter复用  对于每一行日程而言的
        for (final CalendarEvent event : events) {
            TextView eventView = new TextView(context);
            eventView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            eventView.setText(event.name);
            eventView.setLines(1);
            eventView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final EditText edit = new EditText(context);
                    edit.setText(event.name);
                    new AlertDialog.Builder(context) // 建造者模式
                            .setTitle("日程信息")
                            .setView(edit)
                            .setNeutralButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    date.deleteEvent(event.eid);
                                    CalendarItemAdapter.this.notifyDataSetChanged();
                                }
                            })
                            .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    date.deleteEvent(event.eid);
                                    date.addEvent(edit.getText().toString().trim());  //去除字符串两端空白字符，中间字符不受影响
                                    CalendarItemAdapter.this.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }
            });
            ll.addView(eventView);
        }

        int totalHeight = gridView.getHeight();
        int rowCount = getCount() / gridView.getNumColumns();   //gridView布局
        int rowHeight = totalHeight / rowCount;
        v.setLayoutParams(new AbsListView.LayoutParams(-1,rowHeight));//setLayoutParams设置一个格的高度和宽度

        //单击添加  对于整个格子的
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText edit = new EditText(context);
                new AlertDialog.Builder(context)
                        .setTitle("添加日程")
                        .setMessage("请输入日程信息")
                        .setView(edit)
                        .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String event = edit.getText().toString().trim();
                                if (!event.isEmpty()) {
                                    date.addEvent(event);
                                }
                                CalendarItemAdapter.this.notifyDataSetChanged();   //更新界面
                            }
                        })   //匿名内部类
                        .setNegativeButton("关闭", null)
                        .show();
            }
        });

        return v;
     }
}
