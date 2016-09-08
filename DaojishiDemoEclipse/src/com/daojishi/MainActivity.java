package com.daojishi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import com.daojishi.adapter.MainListAdapter;
import com.daojishi.entity.Person;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity{

    public static List<Person> list;
    private ListView listView;
    private MainListAdapter adapter;

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            if (msg.what ==1) {
                adapter.notifyDataSetChanged();
                //每隔1毫秒更新一次界面，如果只需要精确到秒的倒计时此处改成1000即可
                handler.sendEmptyMessageDelayed(1,1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        //测试数据
        list = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            Person person = new Person();
            person.setName("产品"+i);
            person.setTime(900000 + i + "");
            list.add(person);
        }
        // 数据拿到开始计时
        adapter = new MainListAdapter(this,list);
        listView.setAdapter(adapter);
        adapter.start();//开始倒计时

        handler.sendEmptyMessageDelayed(1,1);
    }
}
