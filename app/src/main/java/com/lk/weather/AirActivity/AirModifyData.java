package com.lk.weather.AirActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.lk.weather.DBAccess;
import com.lk.weather.R;

public class AirModifyData extends AppCompatActivity {

    EditText edtDate,edtAqi,edtO3,edtPm25,edtPm10;
    DBAccess access;
    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_modify_data);
        edtAqi=(EditText)findViewById(R.id.edtAqi);
        edtO3=(EditText)findViewById(R.id.edtO3);
        edtDate=(EditText)findViewById(R.id.tvDate);
        edtPm25=(EditText)findViewById(R.id.edtPm25);
        edtPm10=(EditText)findViewById(R.id.edtPm10);


        access=new DBAccess(this, "weather", null, 1);
        Bundle bundle=getIntent().getExtras();
        id=bundle.getString("id","0");
        Cursor c=access.getData("air","c_id= "+id, null);//資料查詢，條件為_id等於上一個活動視窗傳遞過來的資料
        c.moveToFirst();//將指標一到第一筆

        edtDate.setText(c.getString(1));//得到ID欄位的資料1
        edtAqi.setText(c.getString(2));
        edtO3.setText(c.getString(3));
        edtPm25.setText(c.getString(4));
        edtPm10.setText(c.getString(5));

    }

    public void modify(View view) {
        access.update(edtDate.getText().toString(),edtAqi.getText().toString(),edtO3.getText().toString(),edtPm25.getText().toString(),edtPm10.getText().toString(),"c_id= "+id);
        finish();

    }
}
