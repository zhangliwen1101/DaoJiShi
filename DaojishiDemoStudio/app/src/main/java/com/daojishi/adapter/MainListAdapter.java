package com.daojishi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daojishi.R;
import com.daojishi.entity.Person;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by liuyi on 2015/12/16.
 */
public class MainListAdapter extends BaseAdapter {

    private List<Person> list;
    private LayoutInflater layoutInflater;

    public MainListAdapter(Context context, List<Person> list) {
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.adapter_main_lv_item, parent, false);
            viewHolder.tvTimer = (TextView) convertView.findViewById(R.id.tv_timer);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvName.setText(list.get(position).getName());

        if (!list.get(position).getTime().equals("时间到")) {
            //将一个int类型的数值转换成时间格式
            int tempTime = Integer.parseInt(list.get(position).getTime());
            //SSS为毫秒，如果精确到时分秒改成HH:mm:ss即可
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss:SSS");
            Date date = new Date(tempTime);
            String time = simpleDateFormat.format(date);
            //因为1秒是等于1000毫秒的，正常显示格式为05:40:99,所以此处截取掉最后一位数字
            viewHolder.tvTimer.setText(time.substring(0, time.length() - 1));
        } else {
            viewHolder.tvTimer.setText(list.get(position).getTime());
        }
        return convertView;
    }

    public class ViewHolder {
        TextView tvTimer;
        TextView tvName;
    }

    int result = 0;
    private Thread thread;

    public void start() {
        thread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        if (list == null || result == list.size()) {
                            break;
                        }
                        sleep(1);
                        for (Person person : list) {
                            if (!"时间到".equals(person.getTime())) {
                                if ("1".equals(person.getTime())) {
                                    person.setTime("时间到");
                                    result++;
                                } else {
                                    person.setTime((Integer.parseInt(person.getTime()) - 1) + "");
                                }
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }
}
