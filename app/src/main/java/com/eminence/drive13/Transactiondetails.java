package com.eminence.drive13;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import static com.eminence.drive13.utils.Baseurl.imagebaseurl;

public class Transactiondetails extends AppCompatActivity {

    TextView namee,locationee,rnamee,rnumberr,billamountt,iumage,txt_serviceName,selfiImage;
    Button viewphoto,viewSelfiphoto;
    String text;
    String billphoto,selfiphoto;
    LinearLayout billPhotoLayout,selfiPhotoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactiondetails);

        viewphoto=findViewById(R.id.viewphoto);
        viewSelfiphoto=findViewById(R.id.viewSelfiphoto);
        namee=findViewById(R.id.namee);
        locationee=findViewById(R.id.locationee);
        rnamee=findViewById(R.id.rnamee);
        rnumberr=findViewById(R.id.rnumberr);
        billamountt=findViewById(R.id.billamountt);
        iumage=findViewById(R.id.iumage);
        txt_serviceName=findViewById(R.id.txt_serviceName);

        billPhotoLayout=findViewById(R.id.billPhotoLayout);
        selfiPhotoLayout=findViewById(R.id.selfiPhotoLayout);

        selfiImage=findViewById(R.id.selfiImage);

        String name=getIntent().getExtras().getString("name");
        String location=getIntent().getExtras().getString("location");
        String rname=getIntent().getExtras().getString("rname");
        String rmobile=getIntent().getExtras().getString("rmobile");
        String billamount=getIntent().getExtras().getString("billamount");


        billphoto=getIntent().getExtras().getString("billphoto");
        selfiphoto=getIntent().getExtras().getString("selfiphoto");

        txt_serviceName.setText(name);
        namee.setText(name);
        locationee.setText(location);
        rnamee.setText(rname);
        rnumberr.setText(rmobile);
        billamountt.setText("RS."+billamount);

//        if (billphoto!=null)
//        {
//           text = getLast13((billphoto));
//        }
//


        if (billphoto.equals("")){
            text = getLast13((selfiphoto));
            selfiImage.setText(text);
            billPhotoLayout.setVisibility(View.GONE);

        } else {
            text = getLast13((billphoto));
            iumage.setText(text);
            selfiPhotoLayout.setVisibility(View.GONE);
        }

        viewphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Showalert();
            }
        });


        viewSelfiphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowalertSelfiPhoto();
            }
        });




    }

    private void ShowalertSelfiPhoto() {
        final AlertDialog alertDialog = new AlertDialog.Builder(Transactiondetails.this).create();
        LayoutInflater inflater = getLayoutInflater();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View convertView = inflater.inflate(R.layout.images, null);
        ImageView image = convertView.findViewById(R.id.image);
        // Button btn2 = convertView.findViewById(R.id.btn2);

        Glide.with(this)
                .load(imagebaseurl +selfiphoto )
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(image);
        alertDialog.setView(convertView);
        alertDialog.show();

    }

    private void Showalert() {
        final AlertDialog alertDialog = new AlertDialog.Builder(Transactiondetails.this).create();
        LayoutInflater inflater = getLayoutInflater();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View convertView = inflater.inflate(R.layout.images, null);
        ImageView image = convertView.findViewById(R.id.image);
       // Button btn2 = convertView.findViewById(R.id.btn2);


        Glide.with(this)
                .load(imagebaseurl +billphoto )
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(image);


        alertDialog.setView(convertView);
        alertDialog.show();

    }

    public String getLast13(String myString) {
        if(myString.length() > 13)
            return myString.substring(myString.length()-13);
        else
            return myString;
    }

    public void back(View view) {
        onBackPressed();
    }

}