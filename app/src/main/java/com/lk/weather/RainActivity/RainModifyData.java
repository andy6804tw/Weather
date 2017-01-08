package com.lk.weather.RainActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.lk.weather.DBAccess;
import com.lk.weather.R;

public class RainModifyData extends AppCompatActivity {

    EditText edtInday,edtBeforeday,edtDate;
    DBAccess access;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain_modify_data);
        edtInday=(EditText)findViewById(R.id.edtDeriction);
        edtBeforeday=(EditText)findViewById(R.id.edtBeforeday);
        edtDate=(EditText)findViewById(R.id.tvDate);


        access=new DBAccess(this, "weather", null, 1);
        Bundle bundle=getIntent().getExtras();
        id=bundle.getString("id","0");
        Cursor c=access.getData("rain","c_id= "+id, null);//資料查詢，條件為_id等於上一個活動視窗傳遞過來的資料
        c.moveToFirst();//將指標一到第一筆

        edtInday.setText(c.getString(2));//得到ID欄位的資料1
        edtBeforeday.setText(c.getString(3));
        edtDate.setText(c.getString(1));

    }

    public void modify(View view) {
        access.update(edtDate.getText().toString(),edtInday.getText().toString(),edtBeforeday.getText().toString(),"c_id= "+id);
        finish();

    }
}

