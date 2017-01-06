package com.lk.weather.WindSpeedActivity;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lk.weather.AirActivity.AirActivity;
import com.lk.weather.R;

import java.util.ArrayList;

/**
 * Created by andy6804tw on 2017/1/5.
 */

public class WindRecyclerAdapter extends RecyclerView.Adapter<WindRecyclerAdapter.ViewHolder> {
    ArrayList<String> list;

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView tvWater, tvDay, tvUpdate, tvDown;

        public ViewHolder(View itemView) {
            super(itemView);
            tvWater = (TextView) itemView.findViewById(R.id.tvWater);
            tvDay = (TextView) itemView.findViewById(R.id.tvDay);
            tvDown = (TextView) itemView.findViewById(R.id.tvDown);
            tvUpdate = (TextView) itemView.findViewById(R.id.tvUpdate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
        }
    }

    @Override
    public WindRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.air_card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvWater.setText("有效蓄水量:" + WindSpeedActivity.Water.get(i) + "萬立方公頃");
        viewHolder.tvDay.setText("預測剩餘天數:" + "60天以上");
        viewHolder.tvUpdate.setText("最後更新日期:" + WindSpeedActivity.Update.get(i));
        viewHolder.tvDown.setText("今日進水量:" + WindSpeedActivity.Down.get(i));

    }

    @Override
    public int getItemCount() {
        return WindSpeedActivity.Day.size();
    }
}


