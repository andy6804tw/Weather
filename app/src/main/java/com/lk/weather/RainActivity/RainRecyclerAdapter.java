package com.lk.weather.RainActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

/**
 * Created by andy6804tw on 2017/1/5.
 */

public class RainRecyclerAdapter extends RecyclerView.Adapter<RainRecyclerAdapter.ViewHolder> {

    ArrayList<RainDataModel> list=new ArrayList<>();
    Context mContext;

    public RainRecyclerAdapter(ArrayList<RainDataModel>list,Context mContext) {
        this.list=list;
        this.mContext=mContext;
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        public TextView tvDate,tvInday,tvBeforeday,tvCountry,tvOption;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCountry = (TextView)itemView.findViewById(R.id.tvCountry);
            tvDate =(TextView)itemView.findViewById(R.id.tvDate);
            tvInday =(TextView)itemView.findViewById(R.id.tvInday);
            tvBeforeday =(TextView)itemView.findViewById(R.id.tvBeforeday);
            tvOption =(TextView)itemView.findViewById(R.id.tvOption);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

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
        viewHolder.tvDate.setText("今日日期:" + list.get(i).r_date);
        viewHolder.tvCountry.setText("縣市:" + "台南市");
        viewHolder.tvInday.setText("今日下雨量:" + list.get(i).acc_inday);
        viewHolder.tvBeforeday.setText("昨日下雨量:" + list.get(i).acc_beforeday);
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
