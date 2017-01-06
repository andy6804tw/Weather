package com.lk.weather;

/**
 * Created by andy6804tw on 2017/1/5.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAccess extends SQLiteOpenHelper {


   public  DBAccess(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {//建構子1.哪個Activity呼叫2.資料庫名稱 3.資料庫物件4.版本
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//第一次建構資料表呼叫,應用程式第一次執行會做


        String sql1="create table country(c_id integer primary key autoincrement,"
                +"c_name text,"
                +"population text,"
                +"area text,"
                +"Number_of_village text"
                +")";
        Log.e("SQLDB",sql1);
        db.execSQL(sql1);

        String sql2="create table windspeed("
                +"c_id integer primary key autoincrement,"
                +"w_date text,"
                +"deirection text,"
                +"speed text,"
                +"pufu_speed text,"
                +"gust text,"
                +"pufu_gust text,"
                +"foreign key (c_id) references country(c_id) on delete cascade"
                +")";
        Log.e("SQLDB",sql2);
        db.execSQL(sql2);

        String sql3="create table rain("
                +"c_id integer primary key autoincrement,"
                +"r_date text,"
                +"acc_inday text,"
                +"acc_beforeday text,"
                +"foreign key (c_id) references country (c_id) ON DELETE CASCADE"
                +")";
        Log.e("SQLDB",sql3);
        db.execSQL(sql3);

        String sql4="create table aqi("
                +"AQI_index integer primary key,"
                +"AQI_suggest text,"
                +"AQI_statecolor text,"
                +"AQI_des text"
                +")";
        Log.e("SQLDB",sql4);
        db.execSQL(sql4);

        String sql5="create table pm25("
                +"PM25_index integer primary key,"
                +"PM25_sort text,"
                +"PM25_level text,"
                +"PM25_normalsuggest text,"
                +"PM25_allergysuggest text"
                +")";
        Log.e("SQLDB",sql5);
        db.execSQL(sql5);

        String sql6="create table air("
                +"c_id integer primary key autoincrement,"
                +"a_date text,"
                +"AQI text,"
                +"O3 text,"
                +"PM25 text,"
                +"PM10 text,"
                +"foreign key (c_id) references country (c_id) ON DELETE CASCADE,"
                +"foreign key (AQI)  references aqi(AQI_index)ON DELETE CASCADE,"
                +"foreign key (PM25) references pm25 (PM25_index) ON DELETE CASCADE"
                +")";
        Log.e("SQLDB",sql6);

        db.execSQL(sql6);
    }

    @Override//當應用程式有更新再次開起來會檢查新app資料庫版本和舊資料是否一致
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("drop table if exits country");
        db.execSQL("drop table if exits windspeed");
        db.execSQL("drop table if exits rain");
        db.execSQL("drop table if exits aqi");
        db.execSQL("drop table if exits pm25");
        db.execSQL("drop table if exits air");
        onCreate(db);

    }
    //建立新增rain
   public long add(String r_date, int  acc_indaay, int acc_beforeday) {

        SQLiteDatabase db = getWritableDatabase();//物件可寫入資料
        ContentValues val3 = new ContentValues();
        val3.put("r_date", r_date);
        val3.put("acc_inday",acc_indaay);
        val3.put("acc_beforeday",acc_beforeday);
        return db.insert("rain", null, val3);
    }
    //建立新增wind
    long add(String W_date,String direction,int speed,int pufu_speed,int gust,int pufu_gust){

        SQLiteDatabase db = getWritableDatabase();//物件可寫入資料
        ContentValues val2 = new ContentValues();
        val2.put("w_date", "2017/01/03");
        val2.put("deirection", "東");
        val2.put("speed", 25.25);
        val2.put("pufu_speed", 10);
        val2.put("gust", 23.45);
        val2.put("pufu_gust", 11);
        return db.insert("windspeed", null, val2);
    }
    long add(String a_date,int aqi,int o3,int pm25,int pm10){

        SQLiteDatabase db = getWritableDatabase();//物件可寫入資料
        ContentValues val6 = new ContentValues();
        val6.put("a_date", "2017/01/03");
        val6.put("AQI", 100);
        val6.put("O3", 55);
        val6.put("PM25", 99);
        val6.put("PM10", 753);
        return db.insert("air", null, val6);
    }


    long add() {

        SQLiteDatabase db = getWritableDatabase();//物件可寫入資料
        ContentValues values = new ContentValues();

        ContentValues val1 = new ContentValues();
        val1.put("c_name", "台北市");
        val1.put("population", 800);
        val1.put("Number_of_village", 8);
        val1.put("area", 1800);
        long result=db.insert("country", null, val1);


        for(int i =0;i<=500;i++){
            ContentValues valfor1 = new ContentValues();
            if(i<=50) {
                valfor1.put("AQI_index", i);
                valfor1.put("AQI_suggest","良好");
                valfor1.put("AQI_statecolor","綠色");
                valfor1.put("AQI_des","空氣品質為良好，汙染程度低或無汙染");
                db.insert("aqi",null,valfor1);
            }else if(i>=51 && i<=100){
                valfor1.put("AQI_index", i);
                valfor1.put("AQI_suggest","普通");
                valfor1.put("AQI_statecolor","黃色");
                valfor1.put("AQI_des","空氣品質普通，但對非常少數之極敏感族群產生輕微影響");
                db.insert("aqi",null,valfor1);
            }else if(i>=101 && i<=150){
                valfor1.put("AQI_index", i);
                valfor1.put("AQI_suggest","對敏感族群不健康");
                valfor1.put("AQI_statecolor","橘色");
                valfor1.put("AQI_des","空氣汙染物可能會對敏感族群的健康造成影響，但是對一般大眾的影響不明顯");
                db.insert("aqi",null,valfor1);
            }else if(i>=151 && i<=200){
                valfor1.put("AQI_index", i);
                valfor1.put("AQI_suggest","對所有族群不健康");
                valfor1.put("AQI_statecolor","紅色");
                valfor1.put("AQI_des","對所有人的健康開始產生影響，對於敏感族群可能產生較嚴重的健康影響");
                db.insert("aqi",null,valfor1);
            }else if(i>=201 && i<=300){
                valfor1.put("AQI_index", i);
                valfor1.put("AQI_suggest","非常不健康");
                valfor1.put("AQI_statecolor","紫色");
                valfor1.put("AQI_des","健康警報，對所有人都可能產生較嚴重的健康影響");
                db.insert("aqi",null,valfor1);
            }else{
                valfor1.put("AQI_index", i);
                valfor1.put("AQI_suggest","危害");
                valfor1.put("AQI_statecolor","褐紅色");
                valfor1.put("AQI_des","健康威脅達到警急，所有人都可能受到影響");
                db.insert("aqi",null,valfor1);
            }
        }

        for (int j = 0; j <= 999; j++) {
            ContentValues valfor2 = new ContentValues();
            if (j >= 0 && j <= 11) {
                valfor2.put("PM25_index", j);
                valfor2.put("PM25_sort", "低");
                valfor2.put("PM25_level", 1);
                valfor2.put("PM25_normalsuggest", "正常戶外活動");
                valfor2.put("PM25_allergysuggest", "正常戶外活動");
                db.insert("pm25",null,valfor2);
            } else if (j >= 12 && j <= 23) {
                valfor2.put("PM25_index", j);
                valfor2.put("PM25_sort", "低");
                valfor2.put("PM25_level", 2);
                valfor2.put("PM25_normalsuggest", "正常戶外活動");
                valfor2.put("PM25_allergysuggest", "正常戶外活動");
                db.insert("pm25",null,valfor2);
            } else if (j >= 24 && j <= 35){
                valfor2.put("PM25_index", j);
                valfor2.put("PM25_sort", "低");
                valfor2.put("PM25_level", 3);
                valfor2.put("PM25_normalsuggest", "正常戶外活動");
                valfor2.put("PM25_allergysuggest", "正常戶外活動");
                db.insert("pm25",null,valfor2);
            }else if (j >= 36 && j <= 41){
                valfor2.put("PM25_index", j);
                valfor2.put("PM25_sort", "中");
                valfor2.put("PM25_level", 4);
                valfor2.put("PM25_normalsuggest", "正常戶外活動");
                valfor2.put("PM25_allergysuggest", "有心臟、呼吸道及心血管疾病的成人與孩童感受到癥狀時，應考慮減少體力消耗，特別是減少戶外活動。");
                db.insert("pm25",null,valfor2);
            }else if (j >= 42 && j <= 47){
                valfor2.put("PM25_index", j);
                valfor2.put("PM25_sort", "中");
                valfor2.put("PM25_level", 5);
                valfor2.put("PM25_normalsuggest", "正常戶外活動");
                valfor2.put("PM25_allergysuggest", "有心臟、呼吸道及心血管疾病的成人與孩童感受到癥狀時，應考慮減少體力消耗，特別是減少戶外活動。");
                db.insert("pm25",null,valfor2);
            }else if (j >= 48 && j <= 53){
                valfor2.put("PM25_index", j);
                valfor2.put("PM25_sort", "中");
                valfor2.put("PM25_level", 6);
                valfor2.put("PM25_normalsuggest", "正常戶外活動");
                valfor2.put("PM25_allergysuggest", "有心臟、呼吸道及心血管疾病的成人與孩童感受到癥狀時，應考慮減少體力消耗，特別是減少戶外活動。");
                db.insert("pm25",null,valfor2);
            }else if (j >= 54 && j <= 58){
                valfor2.put("PM25_index", j);
                valfor2.put("PM25_sort", "高");
                valfor2.put("PM25_level", 7);
                valfor2.put("PM25_normalsuggest", "任何人如果有不適，如眼痛，咳嗽或喉嚨痛等，應該考慮減少戶外活動。");
                valfor2.put("PM25_allergysuggest", "1. 有心臟、呼吸道及心血管疾病的成人與孩童，應減少體力消耗，特別是減少戶外活動。\n" +
                        "2. 老年人應減少體力消耗。 \n" +
                        "3. 具有氣喘的人可能需增加使用吸入劑的頻率。\t\n");
                db.insert("pm25",null,valfor2);
            }else if (j >= 59 && j <= 64){
                valfor2.put("PM25_index", j);
                valfor2.put("PM25_sort", "高");
                valfor2.put("PM25_level", 8);
                valfor2.put("PM25_normalsuggest", "任何人如果有不適，如眼痛，咳嗽或喉嚨痛等，應該考慮減少戶外活動。");
                valfor2.put("PM25_allergysuggest", "1. 有心臟、呼吸道及心血管疾病的成人與孩童，應減少體力消耗，特別是減少戶外活動。\n" +
                        "2. 老年人應減少體力消耗。 \n" +
                        "3. 具有氣喘的人可能需增加使用吸入劑的頻率。\t\n");
                db.insert("pm25",null,valfor2);
            }else if (j >= 65 && j <= 70){
                valfor2.put("PM25_index", j);
                valfor2.put("PM25_sort", "高");
                valfor2.put("PM25_level", 9);
                valfor2.put("PM25_normalsuggest", "任何人如果有不適，如眼痛，咳嗽或喉嚨痛等，應該考慮減少戶外活動。");
                valfor2.put("PM25_allergysuggest", "1. 有心臟、呼吸道及心血管疾病的成人與孩童，應減少體力消耗，特別是減少戶外活動。\n" +
                        "2. 老年人應減少體力消耗。 \n" +
                        "3. 具有氣喘的人可能需增加使用吸入劑的頻率。\t\n");
                db.insert("pm25",null,valfor2);
            }else{
                valfor2.put("PM25_index", j);
                valfor2.put("PM25_sort", "非常高");
                valfor2.put("PM25_level", 10);
                valfor2.put("PM25_normalsuggest", "任何人如果有不適，如眼痛，咳嗽或喉嚨痛等，應減少體力消耗，特別是減少戶外活動。");
                valfor2.put("PM25_allergysuggest", "1. 有心臟、呼吸道及心血管疾病的成人與孩童，以及老年人應避免體力消耗，特別是避免戶外活動。\n" +
                        "2. 具有氣喘的人可能需增加使用吸入劑的頻率。\n");
                db.insert("pm25",null,valfor2);
            }
        }

        db.close();

        return 1;
    }
    public Cursor getData(String NAME, String whereStr, String orderbyStr){
        /*
         select _id, title, date, time
          from todolist
          where _id=5        過濾條件
          order by date      日期排序
        */
        SQLiteDatabase db=getReadableDatabase();
        switch (NAME) {
            case "country": {

                return db.query(NAME, new String[]{"c_id", "c_name", "population", "area"
                                , "Number_of_village"}
                        , whereStr, null, null, null, orderbyStr);
            }
            case "windspeed": {

                return db.query(NAME, new String[]{"c_id", "w_date", "deirection", "speed"
                                , "pufu_speed", "gust", "pufu_gust"}
                        , whereStr, null, null, null, orderbyStr);
            }

            case "rain": {

                return db.query(NAME, new String[]{"c_id", "r_date", "acc_inday", "acc_beforeday"}
                        , whereStr, null, null, null, orderbyStr);
            }
            case "aqi": {

                return db.query(NAME, new String[]{"AQI_index", "AQI_suggest", "AQI_statecolor"
                                , "AQI_des"}
                        , whereStr, null, null, null, orderbyStr);
            }
            case "pm25": {

                return db.query(NAME, new String[]{"PM25_index", "PM25_sort", "PM25_level"
                                , "PM25_normalsuggest", "PM25_allergysuggest"}
                        , whereStr, null, null, null, orderbyStr);
            }
            default: {

                return db.query(NAME, new String[]{"c_id", "a_date", "AQI", "O3"
                                , "PM25", "PM10"}
                        , whereStr, null, null, null, orderbyStr);
            }
        }



    }




}
