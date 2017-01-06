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

class DBAccess extends SQLiteOpenHelper {
    private final static String TABLE_NAME="todolist";//建議字串常數
    final static String ID_FIELD="_id";
    final static String TITLE_FIELD="title";
    final static String DATE_FIELD="date";
    final static String TIME_FIELD="time";

    DBAccess(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {//建構子1.哪個Activity呼叫2.資料庫名稱 3.資料庫物件4.版本
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//第一次建構資料表呼叫,應用程式第一次執行會做
         /*
            create table todoList{
                _id integer primary key autoincrement,
                title text,
                date text,
                time text
            }
         */
        String sql="create table "+TABLE_NAME+"("+ID_FIELD+" integer primary key autoincrement,"
                +TITLE_FIELD+" text,"
                +DATE_FIELD+" text,"
                +TIME_FIELD+" text)";
        Log.e("SQLDB",sql);
        db.execSQL(sql);//不回傳資料的資料庫都能跑,更新新增刪除

         /*
            create table cotegory{
                _id integer primary key autoincrement,
                vategory text,
                desc text
                foreign key (_id) references todolist(_id)
            };
         */
        String sq2="create table category ("+ID_FIELD+" integer primary key autoincrement,"
                +"category text,"
                +"desc text,"
                +"FOREIGN KEY ("+ID_FIELD+") REFERENCES todolist("+ID_FIELD+"))";
        Log.e("SQLDB",sq2);
        db.execSQL(sq2);

         /*
            create table todoList{
                _id integer primary key autoincrement,
                state text,
                foreign key (_id) references todolist(_id)
            }
         */
        String sq3="create table state ("+ID_FIELD+" integer primary key autoincrement,"
                +"state text,"
                +"FOREIGN KEY ("+ID_FIELD+") REFERENCES todolist("+ID_FIELD+"))";
        Log.e("SQLDB",sq3);
        db.execSQL(sq3);

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

        db.execSQL("drop table if exits "+TABLE_NAME);
        db.execSQL("drop table if exits category");
        db.execSQL("drop table if exits state");

        db.execSQL("drop table if exits country");
        db.execSQL("drop table if exits windspeed");
        db.execSQL("drop table if exits rain");
        db.execSQL("drop table if exits aqi");
        db.execSQL("drop table if exits pm25");
        db.execSQL("drop table if exits air");
        onCreate(db);

    }

    long add(String title, String date, String time) {

        SQLiteDatabase db = getWritableDatabase();//物件可寫入資料
        ContentValues values = new ContentValues();
        if (title != null) {
            values.put(TITLE_FIELD, title);
        }
        if (date != null) {
            values.put(DATE_FIELD, date);
        }
        if (time != null) {
            values.put(TIME_FIELD, time);
        }
        long result = db.insert(TABLE_NAME, null, values);

        ContentValues values2 = new ContentValues();
        values2.put("category", "學校");
        values2.put("desc", "上課");
        db.insert("category", null, values2);

        ContentValues values3 = new ContentValues();
        values3.put("state", "完成");
        db.insert("state", null, values3);

        ContentValues val1 = new ContentValues();
        val1.put("c_name", "台北市");
        val1.put("population", 800);
        val1.put("Number_of_village", 8);
        val1.put("area", 1800);
        db.insert("country", null, val1);

        ContentValues val2 = new ContentValues();
        val2.put("w_date", "2017/01/03");
        val2.put("deirection", "東");
        val2.put("speed", 25.25);
        val2.put("pufu_speed", 10);
        val2.put("gust", 23.45);
        val2.put("pufu_gust", 11);
        db.insert("windspeed", null, val2);

        ContentValues val3 = new ContentValues();
        val3.put("r_date", "2017/01/03");
        val3.put("acc_inday",20768);
        val3.put("acc_beforeday",20768);
        db.insert("rain", null, val3);

        ContentValues val4 = new ContentValues();
        val4.put("AQI_index", 100);
        val4.put("AQI_suggest", "呵呵");
        val4.put("AQI_statecolor", "紅色");
        val4.put("AQI_des", "哈哈");
        db.insert("aqi", null, val4);

        ContentValues val5 = new ContentValues();
        val5.put("PM25_index", 999);
        val5.put("PM25_sort", "非常高");
        val5.put("PM25_level", 9);
        val5.put("PM25_normalsuggest", "正常");
        val5.put("PM25_allergysuggest", "正常");
        db.insert("pm25", null, val5);

        ContentValues val6 = new ContentValues();
        val6.put("a_date", "2017/01/03");
        val6.put("AQI", 100);
        val6.put("O3", 55);
        val6.put("PM25", 99);
        val6.put("PM10", 753);
        db.insert("air", null, val6);

        db.close();

        return result;
    }
    Cursor getData(String NAME, String whereStr, String orderbyStr){
        /*
         select _id, title, date, time
          from todolist
          where _id=5        過濾條件
          order by date      日期排序
        */
        SQLiteDatabase db=getReadableDatabase();
        switch (NAME) {
            case "todolist": {

                return db.query(NAME, new String[]{ID_FIELD, TITLE_FIELD, DATE_FIELD, TIME_FIELD}
                        , whereStr, null, null, null, orderbyStr);
            }
            case "category": {

                return db.query(NAME, new String[]{ID_FIELD, "category", "desc"}
                        , whereStr, null, null, null, orderbyStr);
            }
            case "state": {

                return db.query(NAME, new String[]{ID_FIELD, "state"}
                        , whereStr, null, null, null, orderbyStr);
            }
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
    //修改
    long update(String title,String date, String time,String whereClause) {
        SQLiteDatabase db=this.getWritableDatabase();//取得讀寫資料表物件
        ContentValues values =new ContentValues();
        if(date!=null)  values.put(DATE_FIELD, date);
        if(time!=null)  values.put(TIME_FIELD, time);
        if(title!=null)  values.put(TITLE_FIELD, title);
        //執行更新資料
        long result=db.update(TABLE_NAME, values, whereClause, null);
        db.close();
        return result;//回傳更新資料筆數
    }
    //刪除
    int delete(String _id){
        SQLiteDatabase db=this.getWritableDatabase();//取得讀寫資料表物件
        int result=db.delete(TABLE_NAME, ID_FIELD+" ="+_id, null); //進行刪除
        db.close();
        return result;//回傳刪除筆數
    }



}
