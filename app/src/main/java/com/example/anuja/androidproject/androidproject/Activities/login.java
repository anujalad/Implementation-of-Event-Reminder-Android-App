package com.example.anuja.androidproject.androidproject.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anuja.androidproject.R;
import com.example.anuja.androidproject.androidproject.DatabaseAccess.DBConnection;

public class login extends AppCompatActivity implements View.OnClickListener {
EditText username;
EditText password;
Button sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        DBConnection b = new DBConnection(this);
//        b.insertUsers("anuja", "anuja", db);
//        b.insertUsers("harika", "harika", db);

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        sign=(Button)findViewById(R.id.signin);
        sign.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        DBConnection db=new DBConnection(this);
        boolean isExists=db.isUSerExists(username.getText().toString(),password.getText().toString());
        if(isExists){
        Intent shareUsername=new Intent(this,MainActivity.class)  ;
            shareUsername.putExtra("username",username.getText().toString());
//        Intent intent = new Intent(this, MainActivity.class);
        startActivity(shareUsername);
        }else{
            Toast.makeText(login.this, "not found", Toast.LENGTH_SHORT).show();
            return ;
        }
    }
}
