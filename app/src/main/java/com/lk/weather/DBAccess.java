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


        String sql1="create table country("
                +"c_id integer primary key autoincrement,"
                +"c_name text,"
                +"population text,"
                +"area text,"
                +"Number_of_village text,"
                +"region text"
                +")";
        Log.e("SQLDB",sql1);
        db.execSQL(sql1);

        String sql2="create table windspeed("
                +"c_id integer primary key autoincrement,"
                +"w_date text,"
                +"direction text,"
                +"speed text,"
                +"pufu_speed text,"
                +"gust text,"
                +"pufu_gust text,"
                +"position text,"
                +"foreign key (c_id) references country(c_id) on delete cascade"
                +")";
        Log.e("SQLDB",sql2);
        db.execSQL(sql2);

        String sql3="create table rain("
                +"c_id integer primary key autoincrement,"
                +"r_date text,"
                +"acc_inday text,"
                +"acc_beforeday text,"
                +"position text,"
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
                +"position text,"
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
   public long add(String r_date, String  acc_indaay, String acc_beforeday,int position) {

        SQLiteDatabase db = getWritableDatabase();//物件可寫入資料
        ContentValues val3 = new ContentValues();
        val3.put("position",position);
        val3.put("r_date", r_date);
        val3.put("acc_inday",acc_indaay);
        val3.put("acc_beforeday",acc_beforeday);
        return db.insert("rain", null, val3);
       //return db.update("rain", val3, whereClause, null);
    }
    //建立新增wind
    public long add(String w_date,String direction,String speed,String pufu_speed,String gust,String pufu_gust,int position){

        SQLiteDatabase db = getWritableDatabase();//物件可寫入資料
        ContentValues val2 = new ContentValues();
        val2.put("w_date",w_date);
        val2.put("direction",direction);
        val2.put("speed", speed);
        val2.put("pufu_speed", pufu_speed);
        val2.put("gust", gust);
        val2.put("pufu_gust", pufu_gust);
        val2.put("position",position);
        return db.insert("windspeed", null, val2);
    }
    //新增Air
    public long add(String a_date,String aqi,String o3,String pm25,String pm10,int position){

        SQLiteDatabase db = getWritableDatabase();//物件可寫入資料
        ContentValues val6 = new ContentValues();
        val6.put("a_date", a_date);
        val6.put("AQI", aqi);
        val6.put("O3", o3);
        val6.put("PM25", pm25);
        val6.put("PM10", pm10);
        val6.put("position", position);
        return db.insert("air", null, val6);
    }


    long add() {

        SQLiteDatabase db = getWritableDatabase();//物件可寫入資料
        ContentValues values = new ContentValues();
        //城市

        ContentValues val01 = new ContentValues();
        val01.put("c_name", "台北市");
        val01.put("population", 270);
        val01.put("Number_of_village", 12);
        val01.put("area", 271.80);
        val01.put("region", "北部");
        db.insert("country", null, val01);

        ContentValues val02 = new ContentValues();
        val02.put("c_name", "新北市");
        val02.put("population", 397);
        val02.put("Number_of_village", 29);
        val02.put("area", 2052.57);
        val02.put("region", "北部");
        db.insert("country", null, val02);

        ContentValues val03 = new ContentValues();
        val03.put("c_name", "桃園市");
        val03.put("population", 210);
        val03.put("Number_of_village", 13);
        val03.put("area", 1220.95);
        val03.put("region", "北部");
        db.insert("country", null, val03);

        ContentValues val04 = new ContentValues();
        val04.put("c_name", "基隆市");
        val04.put("population", 37);
        val04.put("Number_of_village", 7);
        val04.put("area", 132.76);
        val04.put("region", "北部");
        db.insert("country", null, val04);

        ContentValues val05 = new ContentValues();
        val05.put("c_name", "新竹市");
        val05.put("population", 43);
        val05.put("Number_of_village", 3);
        val05.put("area", 104.15);
        val05.put("region", "北部");
        db.insert("country", null, val05);

        ContentValues val06 = new ContentValues();
        val06.put("c_name", "新竹縣");
        val06.put("population", 54);
        val06.put("Number_of_village", 13);
        val06.put("area", 1427.54);
        val06.put("region", "北部");
        db.insert("country", null, val06);

        ContentValues val07 = new ContentValues();
        val07.put("c_name", "宜蘭縣");
        val07.put("population", 45);
        val07.put("Number_of_village", 12);
        val07.put("area", 2143.63);
        val07.put("region", "北部");
        db.insert("country", null, val07);

        ContentValues val08 = new ContentValues();
        val08.put("c_name", "台中市");
        val08.put("population", 274);
        val08.put("Number_of_village", 29);
        val08.put("area", 2214.90);
        val08.put("region", "中部");
        db.insert("country", null, val08);

        ContentValues val09 = new ContentValues();
        val09.put("c_name", "苗栗縣");
        val09.put("population", 56);
        val09.put("Number_of_village", 18);
        val09.put("area", 1820.31);
        val09.put("region", "中部");
        db.insert("country", null, val09);

        ContentValues val10 = new ContentValues();
        val10.put("c_name", "彰化縣");
        val10.put("population", 128);
        val10.put("Number_of_village", 26);
        val10.put("area", 1074.40);
        val10.put("region", "中部");
        db.insert("country", null, val10);

        ContentValues val11 = new ContentValues();
        val11.put("c_name", "南投縣");
        val11.put("population", 50);
        val11.put("Number_of_village", 13);
        val11.put("area", 4106.44);
        val11.put("region", "中部");
        db.insert("country", null, val11);

        ContentValues val12 = new ContentValues();
        val12.put("c_name", "雲林縣");
        val12.put("population", 70);
        val12.put("Number_of_village", 20);
        val12.put("area", 1290.83);
        val12.put("region", "中部");
        db.insert("country", null, val12);

        ContentValues val13 = new ContentValues();
        val13.put("c_name", "台南市");
        val13.put("population", 188);
        val13.put("Number_of_village", 37);
        val13.put("area", 2191.65);
        val13.put("region", "南部");
        db.insert("country", null, val13);

        ContentValues val14 = new ContentValues();
        val14.put("c_name", "高雄市");
        val14.put("population", 278);
        val14.put("Number_of_village", 38);
        val14.put("area", 2951.85);
        val14.put("region", "南部");
        db.insert("country", null, val14);

        ContentValues val15 = new ContentValues();
        val15.put("c_name", "嘉義市");
        val15.put("population", 27);
        val15.put("Number_of_village", 2);
        val15.put("area", 60.03);
        val15.put("region", "南部");
        db.insert("country", null, val15);

        ContentValues val16 = new ContentValues();
        val16.put("c_name", "嘉義縣");
        val16.put("population", 51);
        val16.put("Number_of_village", 18);
        val16.put("area", 1903.64);
        val16.put("region", "南部");
        db.insert("country", null, val16);

        ContentValues val17 = new ContentValues();
        val17.put("c_name", "屏東縣");
        val17.put("population", 84);
        val17.put("Number_of_village", 33);
        val17.put("area", 2775.60);
        val17.put("region", "東部");
        db.insert("country", null, val17);

        ContentValues val18 = new ContentValues();
        val18.put("c_name", "台東縣");
        val18.put("population", 22);
        val18.put("Number_of_village", 16);
        val18.put("area", 3515.25);
        val18.put("region", "東部");
        db.insert("country", null, val18);

        ContentValues val19 = new ContentValues();
        val19.put("c_name", "花蓮縣");
        val19.put("population", 33);
        val19.put("Number_of_village", 13);
        val19.put("area", 4628.57);
        val19.put("region", "東部");
        db.insert("country", null, val19);

        ContentValues val20 = new ContentValues();
        val20.put("c_name", "澎湖縣");
        val20.put("population", 10);
        val20.put("Number_of_village", 6);
        val20.put("area", 126.86);
        val20.put("region", "外島");
        db.insert("country", null, val20);

        ContentValues val21 = new ContentValues();
        val21.put("c_name", "金門縣");
        val21.put("population", 33);
        val21.put("Number_of_village", 6);
        val21.put("area", 151.66);
        val21.put("region", "外島");
        db.insert("country", null, val21);

        ContentValues val22 = new ContentValues();
        val22.put("c_name", "連江縣");
        val22.put("population", 1);
        val22.put("Number_of_village", 4);
        val22.put("area", 28.80);
        val22.put("region", "外島");
        db.insert("country", null, val22);


        //pm
        for(int i =1;i<=500;i++){
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

        for (int j = 1; j <= 999; j++) {
            ContentValues valfor2 = new ContentValues();
            if (j >= 1 && j <= 11) {
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
                                , "Number_of_village","region"}
                        , whereStr, null, null, null, orderbyStr);
            }
            case "windspeed": {

                return db.query(NAME, new String[]{"c_id", "w_date", "direction", "speed"
                                , "pufu_speed", "gust", "pufu_gust","position"}
                        , whereStr, null, null, null, orderbyStr);
            }

            case "rain": {

                return db.query(NAME, new String[]{"c_id", "r_date", "acc_inday", "acc_beforeday","position"}
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
                                , "PM25", "PM10","position"}
                        , whereStr, null, null, null, orderbyStr);
            }
        }



    }
    //刪除
    public int delete(String TABLE_NAME,String _id){
        SQLiteDatabase db=this.getWritableDatabase();//取得讀寫資料表物件
        int result=0;
        //進行刪除
        result=db.delete(TABLE_NAME,"c_id ="+_id, null);
        db.close();
        return result;//回傳刪除筆數
    }
    //更新rain
    public  long update(String r_date,String acc_indaay,String acc_beforeday,String whereClause) {
    SQLiteDatabase db=this.getWritableDatabase();//取得讀寫資料表物件
    ContentValues values =new ContentValues();
        values.put("r_date",r_date);
        values.put("acc_inday",acc_indaay);
        values.put("acc_beforeday",acc_beforeday);
    //執行更新資料
    long result=db.update("rain", values, whereClause, null);
    db.close();
    return result;//回傳更新資料筆數
}
    //更新wind
    public  long update(String w_date,String direction,String speed,String pufu_speed,String gust,String pufu_gust,String whereClause) {
        SQLiteDatabase db=this.getWritableDatabase();//取得讀寫資料表物件
        ContentValues values =new ContentValues();
        values.put("w_date",w_date);
        values.put("direction",direction);
        values.put("speed",speed);
        values.put("pufu_speed",pufu_speed);
        values.put("gust",gust);
        values.put("pufu_gust",pufu_gust);
        //執行更新資料
        long result=db.update("windspeed", values, whereClause, null);
        db.close();
        return result;//回傳更新資料筆數
    }
    //更新air
    public  long update(String a_date,String AQI,String O3,String PM25,String PM10,String whereClause) {
        SQLiteDatabase db=this.getWritableDatabase();//取得讀寫資料表物件
        ContentValues values =new ContentValues();
        values.put("a_date",a_date);
        values.put("AQI",AQI);
        values.put("O3",O3);
        values.put("PM25",PM25);
        values.put("PM10",PM10);
        //執行更新資料
        long result=db.update("air", values, whereClause, null);
        db.close();
        return result;//回傳更新資料筆數
    }
}
