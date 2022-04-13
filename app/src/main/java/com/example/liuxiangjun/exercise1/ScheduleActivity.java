package com.example.liuxiangjun.exercise1;
//这是设置菜单项的（Add Remove那个）
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class ScheduleActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);   //第二个参数用于指定我们的菜单项将添加到哪一个menu对象中
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){    //判断点击的哪个菜单项
            case R.id.add_item:
                Toast.makeText(this,"添加好心情！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this,"移除不开心！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                ScheduleActivity.this.finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ListView lv = findViewById(R.id.agendaList);
        lv.setAdapter(new AgendaListAdapter(this));
    }
}
