package com.eminence.drive13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Leaddetails extends AppCompatActivity {
    TextView number,datetime,fromtodes,clientnumer,clientname,createddate,purpose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaddetails);
        number=findViewById(R.id.number);
        datetime=findViewById(R.id.datetime);
        fromtodes=findViewById(R.id.fromtodes);
        clientname=findViewById(R.id.clientname);
        clientnumer=findViewById(R.id.clientnumer);
        createddate=findViewById(R.id.createddate);
        purpose=findViewById(R.id.purpose);

        String tripnumber=getIntent().getExtras().getString("tripnumber");
        String tripfrom=getIntent().getExtras().getString("tripfrom");
        String tripdetintion=getIntent().getExtras().getString("tripdetintion");
        String triptime=getIntent().getExtras().getString("triptime");
        String tripdate=getIntent().getExtras().getString("tripdate");
        String createdat=getIntent().getExtras().getString("createdat");
        String clientnamee=getIntent().getExtras().getString("clientname");
        String clientnumbere=getIntent().getExtras().getString("clientnumber");
        String purposee = getIntent().getExtras().getString("purpose");

        number.setText("Trip Number #"+tripnumber);
        fromtodes.setText(tripfrom+" to "+tripdetintion);
        datetime.setText(triptime+" on "+tripdate);
        createddate.setText(createdat);
        clientname.setText(clientnamee);
        clientnumer.setText(clientnumbere);
        purpose.setText(purposee);

    }
    public void back(View view) {
        onBackPressed();
    }
}