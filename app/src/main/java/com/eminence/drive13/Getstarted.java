package com.eminence.drive13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Getstarted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getstarted);
    }
    public void sendotp(View view) {
        startActivity(new Intent(getApplicationContext(), Signup.class));
        finish();
    }
    public void login(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();

    }
}