package com.example.liuxiangjun.exercise1;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.*;
import java.text.*;
import android.view.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(MainActivity.this,"查看日程 规划生活",   //创建Toast提醒事项
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,ScheduleActivity.class);
                startActivity(intent);
            }
        });

        final ViewPager viewPager = findViewById(R.id.viewPager);
        //在下面的语句中，调用CalendarPagerAdapter的构造函数，先获取当前的年份显示
        viewPager.setAdapter(new CalendarPagerAdapter(this,Calendar.getInstance().get(Calendar.YEAR)));

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new SpinnerItemAdapter(this));
        //得到i，setSelection(）得到我要呈现的数字   也是为了显示当前的年份
        spinner.setSelection(Calendar.getInstance().get(Calendar.YEAR) - 1970);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewPager.setAdapter(new CalendarPagerAdapter(MainActivity.this,1970 + i));
                //匿名内部类用到的外部对象引用必须是final  指向不能改
                // 调用CalendarPagerAdapter的适配器  将选择的年份传入  在setOnItemSelectedListener函数中  点击选择后才会改变
                //改的是viewPager，使其calendar的year改变
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}

//现在我通过java的calendar类 和 date类，可以获取年月日   低纬度模型不要包含高纬度模型
class CalendarModel {
    Calendar c1 = Calendar.getInstance();
    public int year;
    public int month;
    public int dayOfMonth;
    public boolean IsCurrentMonth;   //便于判定是否为当前月   进而选择展示哪个界面
    //Calendar calendar = Calendar.getInstance();
    public CalendarModel(Calendar calendar){
         year = calendar.get(Calendar.YEAR);
         month  = calendar.get(Calendar.MONTH);    //注意这个calendar累的月份是从零开始的，所以真正显示时需要加一
         //我需要利用这个月份、年份，进行计算  算出一个页面上是有五行还是六行
         //week = calendar.get(Calendar.DAY_OF_WEEK);
         dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
         //dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
    }
    SimpleDateFormat simple  = new SimpleDateFormat("EE-MM-dd-yyyy");
//    Date date = new Date();
//    long dateTime =  date.getTime()     //现在是得到了目前的时间    目前并不需要时间，时间可以放在日程类里
}
//最后没有用到这个类