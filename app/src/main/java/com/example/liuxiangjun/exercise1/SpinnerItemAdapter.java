package com.example.liuxiangjun.exercise1;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

//还需要在mainActivity中对spinner进行操作
//设置年份
public class SpinnerItemAdapter extends BaseAdapter {

    private final Activity context;

    SpinnerItemAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 100;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {      //回收作用（类比于微信）
        TextView tv;
        if (convertView != null) {
            tv = (TextView) convertView;
        } else {
            tv = new TextView(context);
            tv.setLayoutParams(new AbsListView.LayoutParams(-1, -1));
        }
        tv.setText(String.valueOf(1970 + i));        //显示的内容   要查找的年份
        return tv;
    }
}
