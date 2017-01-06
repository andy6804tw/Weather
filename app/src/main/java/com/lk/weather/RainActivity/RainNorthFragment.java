package com.lk.weather.RainActivity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lk.weather.DBAccess;
import com.lk.weather.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RainNorthFragment extends Fragment {


    public RainNorthFragment() {
        // Required empty public constructor
    }


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private FloatingActionButton fab;
    ArrayList<RainDataModel>list;
    DBAccess access;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_rain_north, container, false);
        //設定RecyclerView
        recyclerView =(RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        fab=(FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(getActivity(),RainAddData.class));
            }
        });
        //初始化access
        access=new DBAccess(getActivity(),"weather",null,1);
        list=new ArrayList<RainDataModel>();
        Cursor c=access.getData("rain",null, null);
        c.moveToFirst();
        for(int i=0;i<c.getCount();i++){
            list.add(new RainDataModel(c.getString(1),Integer.parseInt(c.getString(2)),Integer.parseInt(c.getString(3))));
            c.moveToNext();
        }
        adapter = new RainRecyclerAdapter(list,getActivity());
        recyclerView.setAdapter(adapter);




        return view;
    }

}
