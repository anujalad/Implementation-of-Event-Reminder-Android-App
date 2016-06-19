package com.example.anuja.androidproject.androidproject.Fragments;


import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anuja.androidproject.R;
import com.example.anuja.androidproject.androidproject.Activities.MainActivity;
import com.example.anuja.androidproject.androidproject.DatabaseAccess.DBConnection;
import com.example.anuja.androidproject.androidproject.Interfaces.FragmentCommunicator;
import com.example.anuja.androidproject.androidproject.Others.MyBroadCastReceiver;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.widget.Toast.LENGTH_LONG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Second_Fragment extends Fragment implements View.OnClickListener {
Button addImage,save;
ImageView imview; EditText editTime, editDate,editDescription;
    Context context;
    String username;
    FragmentCommunicator comm;
    public Second_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context =  context;
        comm = (FragmentCommunicator) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_second_, container, false);
        addImage= (Button) v.findViewById(R.id.pictureButton);
        save= (Button) v.findViewById(R.id.saveButton);
        addImage.setOnClickListener(this);
        save.setOnClickListener(this);
        imview= (ImageView) v.findViewById(R.id.imageView);
        editDate= (EditText) v.findViewById(R.id.editTextDate);
        editTime= (EditText) v.findViewById(R.id.editTextTime);
        editDescription= (EditText) v.findViewById(R.id.editTextDescription);

        editDate.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus) {
                            DateDialog dateDialog = new DateDialog(v);
                            FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                            dateDialog.show(ft, "DatePicker");
                        }
                    }
                }
        );

        editTime.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus) {
                            TimeDialog timeDialog = new TimeDialog(v);
                            FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                            timeDialog.show(ft, "TimePicker");
                        }
                    }
                }
        );

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.pictureButton) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent, 0);
        }

        if(v.getId()==R.id.saveButton) {
           // username=comm.getUsername();
            username=MainActivity.name;
            Toast.makeText(getContext(),username,Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(),"static:"+ MainActivity.name,Toast.LENGTH_LONG).show();
            DBConnection db=new DBConnection(getActivity());
            String date = String.valueOf(editDate.getText());
            String time = String.valueOf(editTime.getText());
            String desc = String.valueOf(editDescription.getText());
            Bitmap map = ((BitmapDrawable) imview.getDrawable()).getBitmap();
            byte[] image = convertBitMapToByteArray(map);

            if(db.insertEvent(date,time,desc,image,username)) {  // Event data inserted to database
                Toast.makeText(getActivity(), "Data inserted", Toast.LENGTH_SHORT).show();
                SetAlarm(date, time, desc);   // Setting reminder with chosen date and time
            } else
                Toast.makeText(getActivity(), "Data not inserted", Toast.LENGTH_SHORT).show();
        }
    }

    public void SetAlarm(String dateStr, String timeStr, String desc) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        String time = "00:00";  // If time is not given
        if(timeStr.contains("AM")) {
            time = timeStr.substring(0,5);
        } else if(timeStr.contains("PM")) {
            int hour = Integer.parseInt(timeStr.substring(0,2));
            time = (hour+12) + ":" + timeStr.substring(3,5);
        }
        Date date = null;
        try {
            date = sdf.parse(dateStr + " " + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        Intent intent = new Intent(getActivity(), MyBroadCastReceiver.class);
        intent.putExtra("desc", desc);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 1253, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | Intent.FILL_IN_DATA);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        Toast.makeText(getActivity(), "Alarm Set.", Toast.LENGTH_LONG).show();
    }

    public byte[] convertBitMapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            Bundle b= data.getExtras();
            Bitmap image= (Bitmap) b.get("data");
            imview.setImageBitmap(image);
        } else
            Toast.makeText(getActivity(),"Image is not captured", LENGTH_LONG).show();
    }
}
