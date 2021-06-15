package com.eminence.drive13.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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
import com.eminence.drive13.R;
import com.eminence.drive13.model.Images;
import com.eminence.drive13.model.ServiceModel;

import java.util.ArrayList;

import static com.eminence.drive13.utils.Baseurl.imagebaseurl;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    Context context;
     ArrayList<Images> subcat;

    public ImageAdapter(ArrayList<Images> subcat, Context context) {
        this.subcat = subcat;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.moreimages, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Glide.with(context)
                .load(imagebaseurl +subcat.get(position).getImages() )
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                  .into(holder.image);

        }




    @Override
    public int getItemCount() {
        return subcat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,location ;
        ImageView image,logo,favorite;
        LinearLayout call;

        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);



        }
    }


    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    public void call_action(String mobile) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" +mobile ));
        context.startActivity(callIntent);

    }

}


