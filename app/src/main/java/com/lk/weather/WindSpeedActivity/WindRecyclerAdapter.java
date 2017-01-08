package com.lk.weather.WindSpeedActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ahmadnemati.wind.WindView;
import com.github.ahmadnemati.wind.enums.TrendType;
import com.lk.weather.DBAccess;
import com.lk.weather.R;

import java.util.ArrayList;

/**
 * Created by andy6804tw on 2017/1/5.
 */

public class WindRecyclerAdapter extends RecyclerView.Adapter<WindRecyclerAdapter.ViewHolder> {

    ArrayList<WindDataModel> list=new ArrayList<>();
    Context mContext;
    DBAccess access;

    public WindRecyclerAdapter(ArrayList<WindDataModel>list,Context mContext) {
        this.list=list;
        this.mContext=mContext;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView tvCountry, tvDate,tvOption;
        WindView windView;
        public ViewHolder(View itemView) {
            super(itemView);

            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvOption = (TextView) itemView.findViewById(R.id.tvOption);
            tvCountry= (TextView) itemView.findViewById(R.id.tvCountry);
            windView=(WindView)itemView.findViewById(R.id.windview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "\t\t\t\t\t\t\t☆風速蒲福: "+list.get(position).pufu_speed+"級 \t\t\t\t\t\t"+"☆陣風風速: "+list.get(position).gust+"mph",
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
        }
    }

    @Override
    public WindRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.wind_card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,final int i) {
        //初始化access
        access=new DBAccess(mContext,"weather",null,1);
        Cursor c=access.getData("country",null, null);
        c.move(list.get(i).position);
        viewHolder.tvDate.setText(list.get(i).w_date);
        viewHolder.tvCountry.setText(c.getString(1) );
        //動畫設置
        viewHolder. windView.setPressure(Integer.parseInt(list.get(i).pufu_gust));
        viewHolder.windView.setPressureUnit(" 級");
        viewHolder.windView.setWindSpeed(Integer.parseInt(list.get(i).speeed));
        viewHolder.windView.setWindText(list.get(i).deriction);
        viewHolder.windView.setBarometerText("陣風");
        viewHolder.windView.setWindSpeedUnit(" mph");
        viewHolder.windView.setTrendType(TrendType.UP);
        viewHolder.windView.start();

        //設定卡片選項item option
        viewHolder.tvOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Display option menu

                final PopupMenu popupMenu = new PopupMenu(mContext, viewHolder.tvOption);
                popupMenu.inflate(R.menu.card_option_menu);//畫一個menu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        final DBAccess access = new DBAccess(mContext, "weather", null, 1);

                        switch (item.getItemId()) {
                            case R.id.mnu_item_modify://修改
                                //Toast.makeText(mContext, "modify"+" "+MainActivity.list.get(i).getId(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent();
                                intent.setClass(mContext, WindModifyData.class); //設定新活動視窗類別
                                Bundle bundle = new Bundle();
                                bundle.putString("id", list.get(i).id);//將id傳遞到新的活動視窗中 從1開始?
                                intent.putExtras(bundle);
                                mContext.startActivity(intent); //開啟新的活動視窗
                                break;
                            case R.id.mnu_item_delete://刪除
                                access.delete("windspeed",list.get(i).id);
                                list.remove(i);
                                notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}


