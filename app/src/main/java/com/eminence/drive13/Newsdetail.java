package com.eminence.drive13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import static com.eminence.drive13.utils.Baseurl.imagebaseurl;

public class Newsdetail extends AppCompatActivity {

    ImageView image;
    TextView titile,content,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);
        image=findViewById(R.id.image);
        titile=findViewById(R.id.titile);
        date=findViewById(R.id.date);
        content=findViewById(R.id.content);
        String imagee=getIntent().getExtras().getString("imag");
        String datetime=getIntent().getExtras().getString("datetime");
        String headline=getIntent().getExtras().getString("headline");
        String contentt=getIntent().getExtras().getString("content");

        Glide.with(Newsdetail.this)
                .load(imagebaseurl + imagee)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
        date.setText(datetime);
        titile.setText(headline+" : "+ Html.fromHtml(contentt));
        date.setText(datetime);
        content.setText(Html.fromHtml(contentt));



    }

    public void back(View view) {
        onBackPressed();
    }
}