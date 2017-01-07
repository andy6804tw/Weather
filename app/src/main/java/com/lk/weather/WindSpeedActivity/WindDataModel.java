package com.lk.weather.WindSpeedActivity;

/**
 * Created by andy6804tw on 2017/1/7.
 */

public class WindDataModel {
    String id,w_date,deriction,speeed,pufu_speed,gust,pufu_gust;
    int position;

    public WindDataModel(String id, String w_date, String deriction, String speeed, String pufu_speed, String gust, String pufu_gust,int position) {
        this.id = id;
        this.w_date = w_date;
        this.deriction = deriction;
        this.speeed = speeed;
        this.pufu_speed = pufu_speed;
        this.gust = gust;
        this.pufu_gust = pufu_gust;
        this.position=position;
    }
}
