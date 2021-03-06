package com.lk.weather.AirActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lk.weather.DBAccess;
import com.lk.weather.R;

import java.util.ArrayList;

/**
 * Created by andy6804tw on 2017/1/5.
 */



public class AirRecyclerAdapter extends RecyclerView.Adapter<AirRecyclerAdapter.ViewHolder> {

    ArrayList<AirDataModel>list=new ArrayList<>();
    Context mContext;
    DBAccess access;

    public AirRecyclerAdapter(ArrayList<AirDataModel>list, Context mContext) {
        this.list=list;
        this.mContext=mContext;
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        public TextView tvCountry,tvAqiDes,tvO3,tvOption;
        CardView card_view;
        ImageView imgFace,imgBuild;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCountry = (TextView) itemView.findViewById(R.id.tvCountry);
            tvAqiDes = (TextView) itemView.findViewById(R.id.tvAqiDes);
            tvO3 = (TextView)  itemView.findViewById(R.id.tvO3);
            tvOption = (TextView)  itemView.findViewById(R.id.tvOption);
            card_view=(CardView)itemView.findViewById(R.id.card_view);
            imgFace=(ImageView)itemView.findViewById(R.id.imgFace);
            imgBuild=(ImageView)itemView.findViewById(R.id.imgBuild);

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
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.air_card_layout, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        //對每一個cell註冊點擊事件
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index;
                LinearLayout linearLayout = (LinearLayout)v.findViewById(R.id.linearLayout);
                View subView = LayoutInflater.from(v.getContext()).inflate(R.layout.card_add_layout, (ViewGroup)v, false);
                //PM2.5的建議
                TextView tvDesc=(TextView)subView.findViewById(R.id.tvDesc);
                TextView tvSort=(TextView)subView.findViewById(R.id.tvSort);
                int pm25_index=Integer.parseInt(list.get(viewHolder.getAdapterPosition()).pm25);
                Cursor c3=access.getData("pm25","PM25_index= "+pm25_index, null);
                c3.moveToFirst();
                tvSort.setText("分類等級 : "+c3.getString(1));
                tvDesc.setText("建議 : " +c3.getString(3));


                //利用單元控制的標記值就標記為單元格的單元格，而不是單元格的單元格。標記值也就不存在了。
                //如果不取消重用，那麼將會出現未曾點擊就已經添加子視圖的效果，再點擊的時間會繼續添加而不是收回。
                if (v.findViewById(R.id.linearLayout).getTag() == null) {
                    index = 1;
                } else {
                    index = (int)v.findViewById(R.id.linearLayout).getTag();
                }

                //Log.i("Card", "Card點擊: " + index);

                // close狀態: 增加內容
                if (index == 1) {
                    linearLayout.addView(subView);
                    subView.setTag(1000);
                    //根據PM2.5變CardView顏色 使用PM25_level指標等級1~10
                    int pm25_level= Integer.parseInt(c3.getString(2));
                    if(pm25_level==1)
                        viewHolder.card_view.setCardBackgroundColor(0x6c9bfd9b);
                    else if(pm25_level==2)
                        viewHolder.card_view.setCardBackgroundColor(0x6c31fd01);
                    else if(pm25_level==3)
                        viewHolder.card_view.setCardBackgroundColor(0x6c31ce01);
                    else if(pm25_level==4)
                        viewHolder.card_view.setCardBackgroundColor(0x6cfdfd01);
                    else if(pm25_level==5)
                        viewHolder.card_view.setCardBackgroundColor(0x6cfdce01);
                    else if(pm25_level==6)
                        viewHolder.card_view.setCardBackgroundColor(0x6cfd9901);
                    else if(pm25_level==7)
                        viewHolder.card_view.setCardBackgroundColor(0x6cfd6464);
                    else if(pm25_level==8)
                        viewHolder.card_view.setCardBackgroundColor(0x6cfd0001);
                    else if(pm25_level==9)
                        viewHolder.card_view.setCardBackgroundColor(0x6c980001);
                    else
                        viewHolder.card_view.setCardBackgroundColor(0x6ccd30fe);


                    v.findViewById(R.id.linearLayout).setTag(2);
                } else {
                    // open狀態： 移除增加內容
                    linearLayout.removeView(v.findViewWithTag(1000));
                    v.findViewById(R.id.linearLayout).setTag(1);
                    viewHolder.card_view.setCardBackgroundColor(0xc8f7dab9);
                }
            }
        });
        // 取消viewHolder的重用機制（滑出View自動收回成預設狀態index=0 close）
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,final int i) {
        int aqi_index=Integer.parseInt(list.get(i).aqi);
        int pm25_index=Integer.parseInt(list.get(i).pm25);
        int aqiValue=Integer.parseInt(list.get(i).aqi);
        int imgBuild_index=1;

        //設定表情圖示
        if(aqiValue>=1&&aqiValue<=50)
            viewHolder.imgFace.setImageResource(R.drawable.face01);
        else if(aqiValue>50&&aqiValue<=100)
            viewHolder.imgFace.setImageResource(R.drawable.face02);
        else if(aqiValue>100&&aqiValue<=150)
            viewHolder.imgFace.setImageResource(R.drawable.face03);
        else if(aqiValue>150&&aqiValue<=200)
            viewHolder.imgFace.setImageResource(R.drawable.face04);
        else if(aqiValue>200&&aqiValue<=300)
            viewHolder.imgFace.setImageResource(R.drawable.face05);
        else if(aqiValue>300&&aqiValue<=500)
            viewHolder.imgFace.setImageResource(R.drawable.face06);
         Integer[] photos = new Integer[]
                {R.drawable.build1,R.drawable.build2,R.drawable.build3,R.drawable.build4,R.drawable.build5,R.drawable.build6,R.drawable.build7,R.drawable.build8,R.drawable.build9
                        ,R.drawable.build10,R.drawable.build11,R.drawable.build12,R.drawable.build13,R.drawable.build14,R.drawable.build15,R.drawable.build16,R.drawable.build17
                        ,R.drawable.build18,R.drawable.build19};



        //初始化access
        access=new DBAccess(mContext,"weather",null,1);
        Cursor c=access.getData("country",null, null);
        Cursor c2=access.getData("aqi","AQI_index= "+aqi_index, null);
        Cursor c3=access.getData("pm25","PM25_index= "+pm25_index, null);
        c2.moveToFirst();
        c3.moveToFirst();
        c.move(list.get(i).position);
        viewHolder.tvCountry.setText(c.getString(1));
        imgBuild_index=Integer.parseInt(c.getString(0));
        //設定建築物圖示
        viewHolder.imgBuild.setImageResource(photos[imgBuild_index-1]);
        viewHolder.tvO3.setText("O3臭氧濃度 : "+list.get(i).o3+" ppb");
        viewHolder.tvAqiDes.setText("建議 : "+c2.getString(3));
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
                                intent.setClass(mContext, AirModifyData.class); //設定新活動視窗類別
                                Bundle bundle = new Bundle();
                                bundle.putString("id", list.get(i).id);//將id傳遞到新的活動視窗中 從1開始?
                                intent.putExtras(bundle);
                                mContext.startActivity(intent); //開啟新的活動視窗
                                break;
                            case R.id.mnu_item_delete://刪除
                                access.delete("air",list.get(i).id);
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