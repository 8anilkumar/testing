package com.eminence.drive13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.eminence.drive13.Adapter.ImageAdapter;
import com.eminence.drive13.Adapter.ServiceAdapter;
import com.eminence.drive13.model.Images;
import com.eminence.drive13.model.ServiceModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.eminence.drive13.utils.Baseurl.baseurl;
import static com.eminence.drive13.utils.Baseurl.imagebaseurl;

public class HotelDetails extends AppCompatActivity {

    ImageView mainimagee,logoo;
    TextView name,location,about,ownername,number;
    LinearLayout call;
    ShimmerRecyclerView recyclerview;
    ArrayList<Images> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);
        mainimagee=findViewById(R.id.mainimage);
        logoo=findViewById(R.id.logo);
        call=findViewById(R.id.call);
        name=findViewById(R.id.name);
        location=findViewById(R.id.location);
        ownername=findViewById(R.id.ownername);
        about=findViewById(R.id.about);
        number=findViewById(R.id.number);
        recyclerview=findViewById(R.id.recyclerview);



        String id=getIntent().getExtras().getString("id");
        String mainimage=getIntent().getExtras().getString("mainimage");
        String logo=getIntent().getExtras().getString("logo");
        String namee=getIntent().getExtras().getString("name");
        final String mobilee=getIntent().getExtras().getString("mobile");
        String fave=getIntent().getExtras().getString("fav");
        String locatione=getIntent().getExtras().getString("location");
        Glide.with(HotelDetails.this)
                .load(imagebaseurl +mainimage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .into(mainimagee);

        Glide.with(HotelDetails.this)
                .load(imagebaseurl +logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .into(logoo);

        name.setText(namee);
        location.setText(locatione);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isPermissionGranted()) {
                    call_action(mobilee);
                }
            }
        });
        submit(id);

    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions((Activity) HotelDetails.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
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
        startActivity(callIntent);

    }
    public void submit(String id) {


        String url = baseurl + "service_details.php";
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(HotelDetails.this);

        //  String username = sharedPreferences.getString("username", null);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(HotelDetails.this);

        Map<String, String> params = new HashMap();
        params.put("service_id", id);
        params.put("driver_id", "43");


        JSONObject parameters = new JSONObject(params);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Toast.makeText(HotelMain.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    String r_code = obj.getString("status");
                    //Toast.makeText(Selctservice.this, ""+response, Toast.LENGTH_SHORT).show();

                    if (r_code.equalsIgnoreCase("1")) {

                         String mobile=obj.getString("mobile");
                         String owner_name=obj.getString("owner_name");
                        String aboutt=obj.getString("about");


                         JSONArray jsonArray = obj.getJSONArray("more_images");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            Images images=new Images();
                            String image = jsonObject2.getString("image");
                            images.setImages(image);

                            arrayList.add(images);

                        }

                        ImageAdapter cateAdapter = new ImageAdapter(arrayList, HotelDetails.this);
                        recyclerview.setLayoutManager(new LinearLayoutManager(HotelDetails.this, LinearLayoutManager.VERTICAL, false));
                        recyclerview.setAdapter(cateAdapter);
                        cateAdapter.notifyDataSetChanged();

                        about.setText(aboutt);
                        ownername.setText(owner_name);
                        number.setText(mobile);

                    } else {


                    }

                } catch (Exception e) {


                    e.printStackTrace();
                    Toast.makeText(HotelDetails.this, "" + e, Toast.LENGTH_SHORT).show();


                }
                // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(HotelDetails.this, "" + error, Toast.LENGTH_SHORT).show();


            }
        }) {

        };
        stringRequest.setShouldCache(false);

        requestQueue.add(stringRequest);


    }


    public void back(View view) {
        onBackPressed();
    }
}