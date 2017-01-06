package com.lk.weather.RainActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lk.weather.DBAccess;
import com.lk.weather.R;

import java.text.DateFormat;
import java.util.Calendar;

public class RainAddData extends AppCompatActivity {
    DBAccess access;
    EditText edtDate,edtInday,edtBeforeday;
    TextView tvDate,tvTime;
    DateFormat formatDateTime;
    Calendar dateTime ;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain_add_data);
        //判斷Statute bar寬高 fitsSystemWindows="true"
        Window window = this.getWindow();
        // Followed by google doc.
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        //window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorBar2));
        //當API大於棉花糖5.0才能更改statue bar顏色
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1){
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorBar2));
        }
        // For not opaque(transparent) color.
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        access=new DBAccess(this,"weather",null,1);
        //時間日期初始化
        formatDateTime= DateFormat.getDateTimeInstance();
        dateTime = Calendar.getInstance();
        tvDate=(TextView)findViewById(R.id.tvDate);
        //時間、日期PickerDialog監聽事件
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RainAddData.this, d, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH),
                        dateTime.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //若月份為單位數補零
            String str_monthOfYear=Integer.toString(monthOfYear+1);//月份+1
            if(str_monthOfYear.length()==1)
                str_monthOfYear="0"+str_monthOfYear;
            //若日期為單位數補零
            String str_dayOfMonth=Integer.toString(dayOfMonth);
            if(str_dayOfMonth.length()==1)
                str_dayOfMonth="0"+str_dayOfMonth;
            tvDate.setText(year+"/"+str_monthOfYear+"/"+str_dayOfMonth);
            //updateTextLabel();
        }
    };
    public void addData(View view) {
        edtInday = (EditText) findViewById(R.id.edtInday);
        edtBeforeday = (EditText) findViewById(R.id.edtBeforeday);
        //Log.e("Error",tvTime.getText().toString());
        if (TextUtils.isEmpty(tvDate.getText().toString()))
            Toast.makeText(RainAddData.this, "請正確選擇日期哦!", Toast.LENGTH_LONG).show();
        else {
            long result = access.add(tvDate.getText().toString(),Integer.parseInt(edtInday.getText().toString()), Integer.parseInt(edtBeforeday.getText().toString()));
            if (result >= 0) {
                Toast.makeText(RainAddData.this, "成功!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(RainAddData.this, "失敗!", Toast.LENGTH_LONG).show();
            }
            edtInday.setText("");
            tvDate.setText("");
            edtBeforeday.setText("");
            finish();

        }
    }
}
