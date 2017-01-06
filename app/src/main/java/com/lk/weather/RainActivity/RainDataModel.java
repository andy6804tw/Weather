package com.lk.weather.RainActivity;

/**
 * Created by andy6804tw on 2017/1/6.
 */

public class RainDataModel {

    String id,r_date;
    int acc_inday,acc_beforeday;

    public RainDataModel(String id,String r_date, int acc_inday,int acc_beforeday) {
        this.id=id;
        this.r_date = r_date;
        this.acc_inday = acc_inday;
        this.acc_beforeday = acc_beforeday;
    }

    public void setR_date(String r_date) {
        this.r_date = r_date;
    }

    public void setAcc_inday(int acc_inday) {
        this.acc_inday = acc_inday;
    }

    public void setAcc_beforedar(int acc_beforeday) {
        this.acc_beforeday = acc_beforeday;
    }
}
