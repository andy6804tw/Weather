package com.lk.weather.AirActivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.lk.weather.R;
import com.lk.weather.ViewPagerAdapter;

import java.util.ArrayList;

public class AirActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    public static ArrayList<String> Water;
    public static ArrayList<String> Day;
    public static ArrayList<String>Update;
    public static ArrayList<String>Down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air);
        //更改狀態欄顏色
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("空氣");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new AirNorthFragment(), "北部");
        viewPagerAdapter.addFragments(new AirWestFragment(), "中部");
        viewPagerAdapter.addFragments(new AirSouthFragment(), "南部");
        viewPagerAdapter.addFragments(new AirEastFragment(), "東部");

        //setupTabIcons();
        Water=new ArrayList<>();
        Day=new ArrayList<>();
        Update=new ArrayList<>();
        Down=new ArrayList<>();
        Water.add("10003");
        Water.add("1400");
        Water.add("15699");
        Water.add("15699");
        Water.add("15699");
        Day.add("2016/12/31");
        Day.add("2016/12/30");
        Day.add("2016/12/29");
        Day.add("2016/12/29");
        Day.add("2016/12/29");
        Update.add("18hr");
        Update.add("1hr");
        Update.add("1hr");
        Update.add("1hr");
        Update.add("10hr");
        Down.add("16500mm");
        Down.add("1800mm");
        Down.add("1800mm");
        Down.add("1800mm");
        Down.add("1760mm");


    }

    @Override
    protected void onResume() {

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        super.onResume();
    }
}
