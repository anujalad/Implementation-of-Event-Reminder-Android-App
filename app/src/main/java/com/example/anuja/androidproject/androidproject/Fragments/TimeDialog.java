package com.example.anuja.androidproject.androidproject.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by anuja on 1/29/2016.
 */
public class TimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    EditText txtTime;

    public TimeDialog() {}

    public TimeDialog(View view) {
        txtTime = (EditText) view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        boolean is24HourView = false;
        return new TimePickerDialog(getActivity(), this, hour, min, is24HourView);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String am_pm = " AM";
        String minStr, hourStr;
        if(hourOfDay > 12) {
            hourOfDay = hourOfDay - 12;
            am_pm = " PM";
        }
        if(hourOfDay==12)
            am_pm=" PM";

        hourStr = hourOfDay < 10 ? "0" + hourOfDay : String.valueOf(hourOfDay);
        minStr = minute < 10 ? "0" + minute : String.valueOf(minute);

        String time = hourStr + ":" + minStr + am_pm;
        txtTime.setText(time);
    }
}
