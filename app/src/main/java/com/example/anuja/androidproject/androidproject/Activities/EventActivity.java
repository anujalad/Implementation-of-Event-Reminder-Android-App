package com.example.anuja.androidproject.androidproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.anuja.androidproject.R;
import com.example.anuja.androidproject.androidproject.DatabaseAccess.DBConnection;
import com.example.anuja.androidproject.androidproject.Others.Event;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<Event> allEvents;
    ListView list;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        allEvents = (ArrayList<Event>) getIntent().getSerializableExtra("events");
        ArrayList<String> desc = new ArrayList<>();
        for(int i = 0; i < allEvents.size(); i++) {
            desc.add(allEvents.get(i).getDescription());
        }
        list = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, desc);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DBConnection db = new DBConnection(this);
        Event event = allEvents.get(position);
        Intent intent = new Intent(this, UpdateActivity.class);
        String eveid = db.GetId(event.getDescription());
        intent.putExtra("id", eveid);
        intent.putExtra("date", event.getDate());
        intent.putExtra("time", event.getTime());
        intent.putExtra("description", event.getDescription());
        intent.putExtra("image", event.getImage());
        startActivity(intent);
    }
}
