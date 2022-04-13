package com.example.liuxiangjun.exercise1;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


//PagerAdapter是基类适配器是一个通用的ViewPager适配器，相比PagerAdapter，
// FragmentPagerAdapter和FragmentStatePagerAdapter更专注于每一页是Fragment的情况，
// 而这两个子类适配器使用情况也是有区别的。
//viewPager  在这里把calendar都设置好
public class CalendarPagerAdapter extends PagerAdapter {
    Activity context;
    int year;
    CalendarPagerAdapter(Activity context,int year){   //传入要查询的年份
        this.context = context;
        this.year = year;
    }
    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }            //实现一个最基本的PagerAdapter


    // 构建View和销毁View的函数，用于自动复用
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d("myCalendar", "instantiateItem(" + position + ") called!");
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(R.layout.calender_page,null);
        TextView tv = v.findViewById(R.id.textView);
        tv.setText((position + 1) + "月");       //显示内容为月份

        GridView gv = v.findViewById(R.id.gridView);

        //在这里设置calendar的 年月日时分秒
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);           //year是通过spinner得到的
        calendar.set(Calendar.MONTH,position);      //月从零开始   日从1开始
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        gv.setAdapter(new CalendarItemAdapter(context, gv,calendar));   //调用CalendarItemAdapter的适配器
        container.addView(v);
        return v;
    }

    //最多只存在三个页
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d("myCalendar", "destroyItem(" + position + ") called!");
        container.removeView((View) object);
    }
}


