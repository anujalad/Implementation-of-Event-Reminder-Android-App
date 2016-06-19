package com.example.anuja.androidproject.androidproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.anuja.androidproject.R;
import com.example.anuja.androidproject.androidproject.DatabaseAccess.DBConnection;
import com.example.anuja.androidproject.androidproject.Others.CustomPagerAdapter;
import com.example.anuja.androidproject.androidproject.Interfaces.FragmentCommunicator;

public class MainActivity extends AppCompatActivity implements FragmentCommunicator {
    ViewPager view;
    TabLayout tab;
    String user;
    public static String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent username=getIntent();
        if(username.hasExtra("username"))
//            user=username.getStringExtra("username");
            name=username.getStringExtra("username");
        System.out.println("user:"+user);
        view= (ViewPager) findViewById(R.id.viewPager);
        CustomPagerAdapter adapter=new CustomPagerAdapter(getSupportFragmentManager());
        view.setAdapter(adapter);
        tab= (TabLayout) findViewById(R.id.tabLayout);

        tab.setupWithViewPager(view);
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            MainActivity.name="";
            Intent i=new Intent(this,login.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddNewEventButton() {
        view.setCurrentItem(1);
    }

    @Override
    public String getUsername() {
        return user;
    }
}
