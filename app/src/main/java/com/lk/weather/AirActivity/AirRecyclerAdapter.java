package com.lk.weather.AirActivity;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lk.weather.R;

import java.util.ArrayList;

/**
 * Created by andy6804tw on 2017/1/5.
 */



public class AirRecyclerAdapter extends RecyclerView.Adapter<AirRecyclerAdapter.ViewHolder> {
    ArrayList<String> list;

    class ViewHolder extends RecyclerView.ViewHolder{


        public TextView tvWater,tvDay,tvUpdate,tvDown;
        CardView card_view;

        public ViewHolder(View itemView) {
            super(itemView);
            tvWater = (TextView)itemView.findViewById(R.id.tvCountry);
            tvDay =(TextView)itemView.findViewById(R.id.tvInday);
            tvDown =(TextView)itemView.findViewById(R.id.tvCountry);
            tvUpdate =(TextView)itemView.findViewById(R.id.tvUpdate);
            card_view =(CardView) itemView.findViewById(R.id.card_view);

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
                .inflate(R.layout.air_card_layout, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        //對每一個cell註冊點擊事件
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index;
                LinearLayout linearLayout = (LinearLayout)v.findViewById(R.id.linearLayout);
                View subView = LayoutInflater.from(v.getContext()).inflate(R.layout.card_add_layout, (ViewGroup)v, false);
                TextView tvDesc=(TextView)subView.findViewById(R.id.tvDesc);
                TextView tvCategory=(TextView)subView.findViewById(R.id.tvCategory);
                String strDesc="";
                strDesc="desc";
                if(strDesc.equals(""))
                    strDesc="無";
                tvDesc.setText("備註: " +strDesc);
                tvCategory.setText("類別: " + "home");

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
                    subView.setTag(1000);viewHolder.card_view.setCardBackgroundColor(0xe2fff399);
                    v.findViewById(R.id.linearLayout).setTag(2);
                } else {
                    // open狀態： 移除增加內容
                    linearLayout.removeView(v.findViewWithTag(1000));
                    v.findViewById(R.id.linearLayout).setTag(1);viewHolder.card_view.setCardBackgroundColor(0xe2df6873);
                }
            }
        });
        // 取消viewHolder的重用機制（滑出View自動收回成預設狀態index=0 close）
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,int i) {
        viewHolder.tvWater.setText("有效蓄水量:"+AirActivity.Water.get(i)+"萬立方公頃");
        viewHolder.tvDay.setText("預測剩餘天數:"+"60天以上");
        viewHolder.tvUpdate.setText("最後更新日期:"+AirActivity.Update.get(i));
        viewHolder.tvDown.setText("今日進水量:"+AirActivity.Down.get(i));

    }

    @Override
    public int getItemCount() {
        return AirActivity.Day.size();
    }

}