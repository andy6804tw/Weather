package com.lk.weather.WindSpeedActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lk.weather.DBAccess;
import com.lk.weather.R;

import java.text.DateFormat;
import java.util.Calendar;

public class WindAddActivity extends AppCompatActivity {

    DBAccess access;
    EditText edtDirection,edtSpeed,edtPufu_speed,edtGust,edtPufu_gust;
    TextView tvDate;
    DateFormat formatDateTime;
    Calendar dateTime ;
    Spinner spinner;
    String mStrCountry;
    int mPosition=0;
    String[]country=new String[]{"台北市","新北市","桃園市","基隆市","新竹市","新竹縣","宜蘭縣","台中市","苗栗縣","彰化縣","南投縣","雲林縣","台南市","高雄市","嘉義市","嘉義縣","屏東縣","台東縣","花蓮縣","澎湖縣","金門縣","連江縣"};
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wind_add);
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
        tvDate=(TextView)findViewById(R.id.tvCountry);
        //時間、日期PickerDialog監聽事件
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(WindAddActivity.this, d, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH),
                        dateTime.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,country);//設定資料來源陣列
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//顯示格式下拉
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStrCountry=parent.getSelectedItem().toString();
                mPosition=position+1;
                //Toast.makeText(WindAddActivity.this,position+" "+id+" "+country[position],Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        edtDirection = (EditText) findViewById(R.id.edtDeriction);
        edtSpeed = (EditText) findViewById(R.id.edtSpeed);
        edtPufu_speed = (EditText) findViewById(R.id.edtPufu_speed);
        edtGust = (EditText) findViewById(R.id.edtGust);
        edtPufu_gust = (EditText) findViewById(R.id.edtPufu_gust);
        //Log.e("Error",tvTime.getText().toString());
        if (TextUtils.isEmpty(tvDate.getText().toString()))
            Toast.makeText(WindAddActivity.this, "請正確選擇日期哦!", Toast.LENGTH_LONG).show();
        else if(TextUtils.isEmpty(edtDirection.getText().toString())||TextUtils.isEmpty(edtSpeed.getText().toString())||TextUtils.isEmpty(edtPufu_speed.getText().toString())||TextUtils.isEmpty(edtGust.getText().toString())||TextUtils.isEmpty(edtPufu_gust.getText().toString())){
            Toast.makeText(WindAddActivity.this, "請確實填入資訊哦!", Toast.LENGTH_LONG).show();
        }
        else {
            //檢查存在
            Cursor c=access.getData("windspeed",null, null);
            c.moveToFirst();
            int k;
            for(k=0;k<c.getCount();k++){
                if(Integer.parseInt(c.getString(7))==mPosition)
                    break;
                c.moveToNext();
            }
            if(k!=c.getCount()){
                Toast.makeText(WindAddActivity.this, "縣市已存在", Toast.LENGTH_LONG).show();
            }else{
                long result = access.add(tvDate.getText().toString(),edtDirection.getText().toString(),edtSpeed.getText().toString(),edtPufu_speed.getText().toString(),edtGust.getText().toString(),edtPufu_gust.getText().toString(),mPosition);
                if (result >= 0) {
                    Toast.makeText(WindAddActivity.this, "成功!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(WindAddActivity.this, "失敗!", Toast.LENGTH_LONG).show();
                }
            }


        }
    }
}
