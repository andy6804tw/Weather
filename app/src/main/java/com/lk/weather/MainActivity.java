package com.lk.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lk.weather.AirActivity.AirActivity;
import com.lk.weather.RainActivity.RainActivity;
import com.lk.weather.WindSpeedActivity.WindSpeedActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<todoItem> item_list;
    ArrayList<DataModel>list;
    DBAccess access;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //更改狀態欄顏色
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        ListView listView=(ListView)findViewById(R.id.listView);
        //設定資料
        item_list=new ArrayList<>();
        item_list.add(new todoItem("雨量","Rain",R.drawable.rain));
        item_list.add(new todoItem("風速","Wind",R.drawable.wind));
        item_list.add(new todoItem("空氣","Air",R.drawable.air));
        TodoAdapter todoAdapter=new TodoAdapter(this,item_list);
        listView.setAdapter(todoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    startActivity(new Intent(MainActivity.this, RainActivity.class));
                }
                else if(position==1){
                    startActivity(new Intent(MainActivity.this, WindSpeedActivity.class));
                }
                else if(position==2){
                    startActivity(new Intent(MainActivity.this, AirActivity.class));
                }

            }
        });
    }

}
