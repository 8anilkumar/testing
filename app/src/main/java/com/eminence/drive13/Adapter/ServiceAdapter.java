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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eminence.drive13.HotelDetails;
import com.eminence.drive13.model.ServiceModel;

import com.eminence.drive13.R;
import com.eminence.drive13.profile.MyProfile;
import com.eminence.drive13.utils.YourPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.eminence.drive13.utils.Baseurl.baseurl;
import static com.eminence.drive13.utils.Baseurl.imagebaseurl;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {
    Context context;
     ArrayList<ServiceModel> subcat;
     String category;

    public ServiceAdapter(ArrayList<ServiceModel> subcat, Context context,String category) {
        this.subcat = subcat;
        this.context = context;
        this.category = category;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainlayout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
              holder.name.setText(subcat.get(position).getName());
              holder.location.setText(subcat.get(position).getLocation());
              holder.favorite.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      String url = baseurl + "make_favourite_unfavourite.php";
                      YourPreference yourPrefrence = YourPreference.getInstance(context);
                      String id = yourPrefrence.getData("id");
                      RequestQueue requestQueue;
                      requestQueue = Volley.newRequestQueue(context);
                      Map<String, String> params = new HashMap();
                      params.put("driver_id", id);
                      params.put("service_id", subcat.get(position).getId());
                      params.put("category", category);

                      JSONObject parameters = new JSONObject(params);
                      JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                          @Override
                          public void onResponse(JSONObject response) {
                              try {
                                  JSONObject obj = new JSONObject(String.valueOf(response));
                                   String r_code = obj.getString("status");
                                   String fav = obj.getString("fav");

                                  if (r_code.equalsIgnoreCase("1")) {
                                      if (fav.equalsIgnoreCase("1"))
                                      {
                                          holder.favorite.setImageResource(R.drawable.ic_nav_favourite_active);
                                      } else {
                                          holder.favorite.setImageResource(R.drawable.ic_favorite_inactive);
                                      }
                                  }

                              } catch (Exception e) {
                                  e.printStackTrace();
                                  Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
                              }
                          }
                      }, new Response.ErrorListener() {
                          @Override
                          public void onErrorResponse(VolleyError error) {
                              Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show();
                          }
                      }) {
                      };

                      requestQueue.add(stringRequest);

                  }
              });
              
        if (subcat.get(position).getFavourite().equalsIgnoreCase("1"))
        {
            holder.favorite.setImageResource(R.drawable.ic_nav_favourite_active);

        }

        Glide.with(context)
                .load(imagebaseurl +subcat.get(position).getMainimage() )
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(holder.mainimage);

        Glide.with(context)
                .load(imagebaseurl +subcat.get(position).getLogo() )
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(holder.logo);

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isPermissionGranted()) {
                    call_action(subcat.get(position).getMobile());
                }
            }
        });

        holder.mainimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HotelDetails.class);
                intent.putExtra("id",subcat.get(position).getId());
                intent.putExtra("mainimage",subcat.get(position).getMainimage());
                intent.putExtra("logo",subcat.get(position).getLogo());
                intent.putExtra("name",subcat.get(position).getName());
                intent.putExtra("mobile",subcat.get(position).getMobile());
                intent.putExtra("fav",subcat.get(position).getFavourite());
                intent.putExtra("location",subcat.get(position).getLocation());
                context.startActivity(intent);

            }
        });
      }




    @Override
    public int getItemCount() {
        return subcat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,location ;
        ImageView mainimage,logo,favorite;
        LinearLayout call;

        public MyViewHolder(View view) {
            super(view);
            mainimage = view.findViewById(R.id.mainimage);
            logo = view.findViewById(R.id.logo);
            name = view.findViewById(R.id.name);
            location = view.findViewById(R.id.location);
             call = view.findViewById(R.id.call);
            favorite = view.findViewById(R.id.favorite);


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


