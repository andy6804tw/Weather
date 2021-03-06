package com.lk.weather.RainActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lk.weather.DBAccess;
import com.lk.weather.R;

import java.util.ArrayList;

import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * Created by andy6804tw on 2017/1/5.
 */

public class RainRecyclerAdapter extends RecyclerView.Adapter<RainRecyclerAdapter.ViewHolder> {

    ArrayList<RainDataModel> list=new ArrayList<>();
    Context mContext;
    DBAccess access;

    public RainRecyclerAdapter(ArrayList<RainDataModel>list,Context mContext) {
        this.list=list;
        this.mContext=mContext;
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        public TextView tvDate,tvInday,tvBeforeday,tvCountry,tvOption;
        WaveLoadingView mWaveLoadingView;
        CardView card_view;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCountry = (TextView)itemView.findViewById(R.id.tvCountry);
            tvDate =(TextView)itemView.findViewById(R.id.tvDate);
            tvInday =(TextView)itemView.findViewById(R.id.tvInday);
            tvBeforeday =(TextView)itemView.findViewById(R.id.tvBeforeday);
            tvOption =(TextView)itemView.findViewById(R.id.tvOption);
            card_view=(CardView)itemView.findViewById(R.id.card_view);
            mWaveLoadingView = (WaveLoadingView) itemView.findViewById(R.id.waveLoadingView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    /*Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rain_card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,final int i) {
        //初始化access
        access=new DBAccess(mContext,"weather",null,1);
        Cursor c=access.getData("country",null, null);
        c.move(list.get(i).position);
        viewHolder.tvDate.setText( list.get(i).r_date);
        viewHolder.tvCountry.setText(c.getString(1) );
        viewHolder.tvInday.setText("今日下雨量 : " + list.get(i).acc_inday+" mm");
        viewHolder.tvBeforeday.setText("昨日下雨量 : " + list.get(i).acc_beforeday+" mm");
        //設定wave progress
        viewHolder.mWaveLoadingView.setCenterTitle(list.get(i).acc_inday+" mm");
        viewHolder.mWaveLoadingView.setProgressValue(Integer.parseInt(list.get(i).acc_inday)/10);
        viewHolder.mWaveLoadingView.setAnimDuration(3000);
        viewHolder.mWaveLoadingView.resumeAnimation();
        viewHolder.mWaveLoadingView.startAnimation();
        //警界卡片顏色
        if(Integer.parseInt(list.get(i).acc_inday)>=200&&Integer.parseInt(list.get(i).acc_inday)<500){
            viewHolder.card_view.setCardBackgroundColor(0xe2fff399);
        }
        else if(Integer.parseInt(list.get(i).acc_inday)>=500){
            viewHolder.card_view.setCardBackgroundColor(0xe2df6873);
        }

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
                                intent.setClass(mContext, RainModifyData.class); //設定新活動視窗類別
                                Bundle bundle = new Bundle();
                                bundle.putString("id", list.get(i).id);//將id傳遞到新的活動視窗中 從1開始?
                                intent.putExtras(bundle);
                                mContext.startActivity(intent); //開啟新的活動視窗
                                break;
                            case R.id.mnu_item_delete://刪除
                                access.delete("rain",list.get(i).id);
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
