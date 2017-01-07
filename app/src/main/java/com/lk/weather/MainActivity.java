package com.lk.weather;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
        ListView listView = (ListView) findViewById(R.id.listView);
        //設定資料
        item_list = new ArrayList<>();
        item_list.add(new todoItem("雨量", "Rain", R.drawable.rain));
        item_list.add(new todoItem("風速", "Wind", R.drawable.wind));
        item_list.add(new todoItem("空氣", "Air", R.drawable.air));
        TodoAdapter todoAdapter = new TodoAdapter(this, item_list);
        listView.setAdapter(todoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(MainActivity.this, RainActivity.class));
                } else if (position == 1) {
                    startActivity(new Intent(MainActivity.this, WindSpeedActivity.class));
                } else if (position == 2) {
                    startActivity(new Intent(MainActivity.this, AirActivity.class));
                }
            }
        });
        //初始化access
        access = new DBAccess(this, "weather", null, 1);
        //addData();
    }
    public void  call(){

        Cursor cl1 = access.getData("country", null, null);
        cl1.moveToFirst();
        Log.e("Data country", cl1.getString(1) + " " + cl1.getString(2) + " "
                + cl1.getString(3) + " " + cl1.getString(4));

        Cursor cl2 = access.getData("windspeed", null, null);
        cl2.moveToFirst();
        Log.e("Data windspeed", cl2.getString(1) + " " + cl2.getString(2) + " "
                + cl2.getString(3) + " " + cl2.getString(4) + " " + cl2.getString(5) + " "
                + cl2.getString(6));

        Cursor cl3 = access.getData("rain", null, null);
        cl3.moveToFirst();
        Log.e("Data rain", cl3.getString(1) + " " + cl2.getString(2) + " "
                + cl2.getString(3));

        Cursor cl4 = access.getData("aqi", null, null);
        cl4.moveToFirst();
        Log.e("Data aqi", cl4.getString(0) + " " + cl4.getString(1) + " "
                + cl4.getString(2) + " " + cl4.getString(3));

        Cursor cl5 = access.getData("pm25", null, null);
        cl5.moveToFirst();
        Log.e("Data pm25", cl5.getString(0) + " " + cl5.getString(1) + " "
                + cl5.getString(2) + " " + cl5.getString(3) + " " + cl5.getString(4));

        Cursor cl6 = access.getData("air", null, null);
        cl6.moveToFirst();
        Log.e("Data air", cl6.getString(1) + " " + cl6.getString(2) + " "
                + cl6.getString(3) + " " + cl6.getString(4) + " " + cl6.getString(5));
    }
    public void addData() {

        long result =access.add();
        if(result>=0){
            Toast.makeText(MainActivity.this,"成功!",Toast.LENGTH_LONG).show();
            //Cursor c=access.getData("todolist",null,DBAccess.DATE_FIELD);

        }else{
            Toast.makeText(MainActivity.this,"失敗!",Toast.LENGTH_LONG).show();
        }

    }
}
