package com.example.anuja.androidproject.androidproject.Activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anuja.androidproject.R;
import com.example.anuja.androidproject.androidproject.DatabaseAccess.DBConnection;
import com.example.anuja.androidproject.androidproject.Fragments.DateDialog;
import com.example.anuja.androidproject.androidproject.Fragments.TimeDialog;

import java.io.ByteArrayOutputStream;

import static android.widget.Toast.LENGTH_LONG;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    EditText dateEdit, timeEdit, descEdit;
    Button updateButton, deleteButton;
    ImageView image;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        id = getIntent().getStringExtra("id");
        String description = getIntent().getStringExtra("description");
        Bitmap imageBitmap = convertByteArrayToBitmap(getIntent().getByteArrayExtra("image"));

        dateEdit = (EditText) findViewById(R.id.editText_date);
        timeEdit = (EditText) findViewById(R.id.editText_time);
        descEdit = (EditText) findViewById(R.id.editText_desc);
        image = (ImageView) findViewById(R.id.imageView_update);

        dateEdit.setText(date);
        timeEdit.setText(time);
        descEdit.setText(description);
        image.setImageBitmap(imageBitmap);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, 0);
            }
        });

        dateEdit.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            DateDialog dateDialog = new DateDialog(v);
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            dateDialog.show(ft, "DatePicker");
                        }
                    }
                }
        );

        timeEdit.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            TimeDialog timeDialog = new TimeDialog(v);
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            timeDialog.show(ft, "TimePicker");
                        }
                    }
                }
        );

        updateButton = (Button) findViewById(R.id.button_update);
        deleteButton = (Button) findViewById(R.id.button_delete);

        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    public Bitmap convertByteArrayToBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public byte[] convertBitMapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==-1) {
            Bundle b= data.getExtras();
            Bitmap imageBitmap= (Bitmap) b.get("data");
            image.setImageBitmap(imageBitmap);
        } else
            Toast.makeText(this,"Image is not captured", LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_update) {
            DBConnection db = new DBConnection(this);
            Bitmap map = ((BitmapDrawable) image.getDrawable()).getBitmap();
            byte[] imageArr = convertBitMapToByteArray(map);
            db.UpdateEvent(id, dateEdit.getText().toString(), timeEdit.getText().toString(),
                            descEdit.getText().toString(), imageArr);
            Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
        } else {
            DBConnection db = new DBConnection(this);
            db.DeleteEvent(descEdit.getText().toString());
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
