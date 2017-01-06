package com.lk.weather.AirActivity;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lk.weather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AirEastFragment extends Fragment {


    public AirEastFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_air_east, container, false);
        //設定RecyclerView
        recyclerView =(RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        fab=(FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(getActivity(),AddEvent.class));
            }
        });
        adapter = new AirRecyclerAdapter();
        recyclerView.setAdapter(adapter);



        return view;
    }

}
