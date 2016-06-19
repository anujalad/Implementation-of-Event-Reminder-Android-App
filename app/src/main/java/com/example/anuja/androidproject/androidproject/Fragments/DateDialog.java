package com.example.anuja.androidproject.androidproject.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by anuja on 1/29/2016.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText txtDate;

    public DateDialog() {}

    public DateDialog(View view) {
        txtDate = (EditText) view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear = monthOfYear + 1;
        String dayStr = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
        String date = (monthOfYear)  + "-" + dayStr + "-" + year;
        if(monthOfYear <10) {
            date = "0" + date;
        }
        txtDate.setText(date);
    }
}
