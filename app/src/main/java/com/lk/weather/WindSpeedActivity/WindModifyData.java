package com.lk.weather.WindSpeedActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.lk.weather.DBAccess;
import com.lk.weather.R;

public class WindModifyData extends AppCompatActivity {

    EditText edtDirection,edtSpeed,edtPufu_speed,edtGust,edtPufu_gust;
    DBAccess access;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wind_modify_data);
        edtDirection=(EditText)findViewById(R.id.edtDirection);
        edtSpeed=(EditText)findViewById(R.id.edtSpeed);
        edtPufu_speed=(EditText)findViewById(R.id.edtPufu_speed);
        edtGust=(EditText)findViewById(R.id.edtGust);
        edtPufu_gust=(EditText)findViewById(R.id.edtPufu_gust);




        access=new DBAccess(this, "weather", null, 1);
        Bundle bundle=getIntent().getExtras();
        id=bundle.getString("id","0");
        Cursor c=access.getData("windspeed","c_id= "+id, null);//資料查詢，條件為_id等於上一個活動視窗傳遞過來的資料
        c.moveToFirst();//將指標一到第一筆

        edtDirection.setText(c.getString(2));//得到ID欄位的資料1
        edtSpeed.setText(c.getString(3));
        edtPufu_speed.setText(c.getString(4));
        edtGust.setText(c.getString(5));
        edtPufu_gust.setText(c.getString(6));

    }

    public void modify(View view) {
       access.update(edtDirection.getText().toString(),edtSpeed.getText().toString(),edtPufu_speed.getText().toString(),edtGust.getText().toString(),edtPufu_gust.getText().toString(),"c_id= "+id);
        finish();

    }
}

