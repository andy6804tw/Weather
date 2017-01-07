package com.lk.weather.RainActivity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lk.weather.DBAccess;
import com.lk.weather.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RainEastFragment extends Fragment {


    public RainEastFragment() {
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
        final View view=inflater.inflate(R.layout.fragment_rain_east, container, false);
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
        Cursor c2=access.getData("country",null, null);
        c.moveToFirst();
        c2.moveToFirst();
        for(int i=0;i<c.getCount();i++){
            c2.moveToFirst();
            c2.move(Integer.parseInt(c.getString(4))-1);
            Log.e("Data country",c2.getString(1)+" "+c2.getString(5)+"  "+c.getString(4));
            if(c2.getString(5).equals("東部")){
                list.add(new RainDataModel(c.getString(0),c.getString(1),c.getString(2),c.getString(3),Integer.parseInt(c.getString(4))));
               /* Log.e("Data country", c2.getString(1) + " " + c2.getString(2) + " "
                        + c2.getString(3) + " " + c2.getString(4)+" "+c2.getString(5)+" "+c.getString(4));*/
            }
            c.moveToNext();
        }
        adapter = new RainRecyclerAdapter(list,getActivity());
        recyclerView.setAdapter(adapter);



        return view;
    }
}
