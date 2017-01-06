package com.lk.weather.RainActivity;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


        public TextView tvDate,tvInday,tvBeforeday,tvCountry;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCountry = (TextView)itemView.findViewById(R.id.tvCountry);
            tvDate =(TextView)itemView.findViewById(R.id.tvDate);
            tvInday =(TextView)itemView.findViewById(R.id.tvInday);
            tvBeforeday =(TextView)itemView.findViewById(R.id.tvBeforeday);

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
    public void onBindViewHolder(ViewHolder viewHolder,int i) {
        viewHolder.tvDate.setText("今日日期:"+list.get(i).r_date);
        viewHolder.tvCountry.setText("預測剩餘天數:"+"60天以上");
        viewHolder.tvInday.setText("今日下雨量:"+list.get(i).acc_inday);
        viewHolder.tvBeforeday.setText("昨日下雨量:"+list.get(i).acc_beforeday);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
