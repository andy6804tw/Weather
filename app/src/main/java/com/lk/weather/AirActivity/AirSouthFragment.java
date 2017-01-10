package com.lk.weather.AirActivity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lk.weather.DBAccess;
import com.lk.weather.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AirSouthFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private FloatingActionButton fab;
    ArrayList<AirDataModel>list;
    DBAccess access;

    public AirSouthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_air_south, container, false);
        //設定RecyclerView
        recyclerView =(RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        fab=(FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(getActivity(),AirAddActivity.class));
            }
        });
        //初始化access
        access=new DBAccess(getActivity(),"weather",null,1);
        list=new ArrayList<AirDataModel>();
        Cursor c=access.getData("air",null, null);
        Cursor c2=access.getData("country",null, null);
        Cursor c3=access.getData("aqi",null, null);
        Cursor c4=access.getData("pm25",null, null);
        c.moveToFirst();
        c2.moveToFirst();
        for(int i=0;i<c.getCount();i++){
            c2.moveToFirst();
            c2.move(Integer.parseInt(c.getString(6))-1);
            Log.e("Data country",c2.getString(1)+" "+c2.getString(5)+"  "+c.getString(4));
            if(c2.getString(5).equals("南部")){
                list.add(new AirDataModel(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),Integer.parseInt(c.getString(6))));

            }
            c.moveToNext();
        }
        adapter = new AirRecyclerAdapter(list,getActivity());
        recyclerView.setAdapter(adapter);
        setHasOptionsMenu(true);//來告知這個fragment有另外的OptionsMenu(參考)
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(queryListener);//QueryListener的監聽事件

    }
    final private android.support.v7.widget.SearchView.OnQueryTextListener queryListener = new android.support.v7.widget.SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextChange(String newText) {


            newText=newText.toLowerCase();
            //逐一比對搜尋
            ArrayList<AirDataModel>myList=new ArrayList<AirDataModel>();
            access=new DBAccess(getActivity(),"weather",null,1);
            Cursor c=access.getData("air",null, null);
            Cursor c2=access.getData("country",null, null);
            c.moveToFirst();
            c2.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c2.moveToFirst();
                c2.move(Integer.parseInt(c.getString(6))-1);
                if(c2.getString(5).equals("南部")&&c2.getString(1).contains(newText)){
                    myList.add(new AirDataModel(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),Integer.parseInt(c.getString(6))));
                }
                c.moveToNext();
            }

            adapter = new AirRecyclerAdapter(myList,getActivity());
            recyclerView.setAdapter(adapter);
            return true;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            Log.d(TAG, "submit:"+query);
            return false;
        }
    };

}
