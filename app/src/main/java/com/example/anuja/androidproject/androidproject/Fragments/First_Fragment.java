package com.example.anuja.androidproject.androidproject.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.anuja.androidproject.R;
import com.example.anuja.androidproject.androidproject.Activities.EventActivity;
import com.example.anuja.androidproject.androidproject.Activities.MainActivity;
import com.example.anuja.androidproject.androidproject.DatabaseAccess.DBConnection;
import com.example.anuja.androidproject.androidproject.Others.Event;
import com.example.anuja.androidproject.androidproject.Interfaces.FragmentCommunicator;

import java.util.ArrayList;
/**
 * A simple {@link Fragment} subclass.
 */
public class First_Fragment extends Fragment implements View.OnClickListener {

Button  addNewEventButton, myEventsButton;
    FragmentCommunicator comm;
String username;
    public First_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        comm = (FragmentCommunicator) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_first_, container, false);
        addNewEventButton= (Button)v.findViewById(R.id.addNewEventButton);
        myEventsButton= (Button) v.findViewById(R.id.myEventsButton);

        addNewEventButton.setOnClickListener(this);
        myEventsButton.setOnClickListener(this);
        return v;

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.addNewEventButton) {
            comm.onAddNewEventButton();
        }

        if(v.getId()==R.id.myEventsButton) {
            username=MainActivity.name;
            DBConnection db = new DBConnection(getActivity());
            ArrayList<Event> allEvents = db.GetAllEvents(username);
            //username=comm.getUsername();
            //Toast.makeText(getContext(),username,Toast.LENGTH_LONG).show();
           // Toast.makeText(getContext(),"statis:"+MainActivity.name,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(), EventActivity.class);
            intent.putExtra("events", allEvents);
            startActivity(intent);
        }
    }
}
