package com.lk.weather.AirActivity;

/**
 * Created by andy6804tw on 2017/1/8.
 */

public class AirDataModel {
    String id,a_date,aqi,o3,pm25,pm10;
    int position;

    public AirDataModel(String id,String a_date, String aqi, String o3, String pm25, String pm10,int position) {
        this.id=id;
        this.a_date = a_date;
        this.aqi = aqi;
        this.o3 = o3;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.position=position;
    }
}
