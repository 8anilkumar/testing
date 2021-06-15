package com.eminence.drive13.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eminence.drive13.HotelDetails;
import com.eminence.drive13.HotelMain;
import com.eminence.drive13.Newsdetail;
import com.eminence.drive13.OTPScreen;
import com.eminence.drive13.R;
import com.eminence.drive13.model.NewsModel;
import com.eminence.drive13.model.ServiceModel;

import java.util.ArrayList;

import static com.eminence.drive13.utils.Baseurl.imagebaseurl;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    Context context;
    ArrayList<NewsModel> subcat;

    public NewsAdapter(ArrayList<NewsModel> subcat, Context context) {
        this.subcat = subcat;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newslayout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.title.setText(subcat.get(position).getHeadline() + " : " + Html.fromHtml(subcat.get(position).getContent()));
        holder.date.setText(subcat.get(position).getDate_time());

        Glide.with(context)
                .load(imagebaseurl + subcat.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);

        holder.layout.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Intent intent=new Intent(context, Newsdetail.class);
                                                 intent.putExtra("imag",subcat.get(position).getImage());
                                                 intent.putExtra("datetime",subcat.get(position).getDate_time());
                                                 intent.putExtra("headline",subcat.get(position).getHeadline());
                                                 intent.putExtra("content",subcat.get(position).getContent());
                                                 context.startActivity(intent);

                                             }
                                         }
        );

    }


    @Override
    public int getItemCount() {
        return subcat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date;
        ImageView image;
        LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
            date = view.findViewById(R.id.date);
            layout = view.findViewById(R.id.layout);


        }
    }


}


