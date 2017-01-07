package com.lk.weather.RainActivity;

/**
 * Created by andy6804tw on 2017/1/6.
 */

public class RainDataModel {

    String id,r_date,acc_inday,acc_beforeday;
    int position;

    public RainDataModel(String id,String r_date, String acc_inday,String acc_beforeday,int position) {
        this.id=id;
        this.r_date = r_date;
        this.acc_inday = acc_inday;
        this.acc_beforeday = acc_beforeday;
        this.position=position;
    }

    public void setR_date(String r_date) {
        this.r_date = r_date;
    }

    public void setAcc_inday(String acc_inday) {
        this.acc_inday = acc_inday;
    }

    public void setAcc_beforedar(String acc_beforeday) {
        this.acc_beforeday = acc_beforeday;
    }
    public void setPosition(int position) {
        this.position = position;
    }
}
