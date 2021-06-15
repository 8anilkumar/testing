package com.eminence.drive13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.eminence.drive13.profile.About;
import com.eminence.drive13.profile.ContactUs;
import com.eminence.drive13.profile.HotelFavourite;
import com.eminence.drive13.profile.MyProfile;
import com.eminence.drive13.profile.Payments;
import com.eminence.drive13.profile.PersnolDocuments;
import com.eminence.drive13.profile.Regitervehicle;
import com.eminence.drive13.profile.Termcondition;
import com.eminence.drive13.profile.Vehiclelist;
import com.eminence.drive13.profile.privacypolicy;
import com.eminence.drive13.utils.GPSTracker;
import com.eminence.drive13.utils.YourPreference;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

    }

    public void profile(View view) {
        startActivity(new Intent(getApplicationContext(), Profile.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
        finish();
    }

    public void transaction(View view) {
        startActivity(new Intent(getApplicationContext(), Transaction.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
        finish();
    }

    public void news(View view) {
        startActivity(new Intent(getApplicationContext(), News.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
        finish();
    }

    public void leads(View view) {
        startActivity(new Intent(getApplicationContext(), Leads.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
        finish();
    }

    public void hotelmain(View view) {
        startActivity(new Intent(getApplicationContext(), HotelMain.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
        finish();
    }

    public void myprofile(View view) {
        startActivity(new Intent(getApplicationContext(), MyProfile.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);

    }

    public void myvehicle(View view) {
        Intent intent=new Intent(Profile.this, Vehiclelist.class);
        startActivity(intent);
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);

    }

    public void persnoldocument(View view) {
        startActivity(new Intent(getApplicationContext(), PersnolDocuments.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);

    }

    public void favourite(View view) {
        startActivity(new Intent(getApplicationContext(), HotelFavourite.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);

    }

    public void payment(View view) {
        startActivity(new Intent(getApplicationContext(), Payments.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);

    }

    public void termcondition(View view) {
        startActivity(new Intent(getApplicationContext(), Termcondition.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);

    }

    public void privacy(View view) {
        startActivity(new Intent(getApplicationContext(), privacypolicy.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);

    }

    public void about(View view) {
        startActivity(new Intent(getApplicationContext(), About.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);

    }

    public void contact(View view) {
        startActivity(new Intent(getApplicationContext(), ContactUs.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);

    }

    public void logout(View view) {
        Toast.makeText(this, "Clearing Sessions...", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("YourCustomNamedPreference", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();

            }
        }, 1500);
    }
}