package com.eminence.drive13.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.eminence.drive13.Adapter.ServiceAdapter;
import com.eminence.drive13.HotelDetails;
import com.eminence.drive13.R;
import com.eminence.drive13.model.ServiceModel;
import com.eminence.drive13.utils.YourPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.eminence.drive13.utils.Baseurl.baseurl;

public class Insuarencefav extends AppCompatActivity {
    ArrayList<ServiceModel> arrayList = new ArrayList<>();
    ShimmerRecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insuarencefav);
        recycler = findViewById(R.id.recyclerview);
        submit();
    }
    public void workshopfav(View view) {
        startActivity(new Intent(getApplicationContext(), Workshopfav.class));
        overridePendingTransition(0, 0);
        finish();
    }
    public void hotelfav(View view) {
        startActivity(new Intent(getApplicationContext(), HotelFavourite.class));
        overridePendingTransition(0, 0);
        finish();
    }
    public void hoteldetail(View view) {
        startActivity(new Intent(getApplicationContext(), HotelDetails.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);

    }
    public void submit() {


        String url = baseurl + "driver_favourite_services.php";
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        String id = yourPrefrence.getData("id");


        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(Insuarencefav.this);

        Map<String, String> params = new HashMap();
        params.put("category", "Insurance");
        params.put("driver_id", id);


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

                        JSONArray jsonArray = obj.getJSONArray("data");


                        for (int i = 0; i < jsonArray.length(); i++) {

                            ServiceModel serviceModel = new ServiceModel();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String id = jsonObject2.getString("id");
                            String name = jsonObject2.getString("name");
                            String location = jsonObject2.getString("location");
                            String mobile = jsonObject2.getString("mobile");
                            String main_image = jsonObject2.getString("main_image");
                            String logo = jsonObject2.getString("logo");

                            serviceModel.setId(id);
                            serviceModel.setName(name);
                            serviceModel.setLocation(location);
                            serviceModel.setMobile(mobile);
                            serviceModel.setMainimage(main_image);
                            serviceModel.setLogo(logo);
                            serviceModel.setFavourite("1");


                            arrayList.add(serviceModel);

                        }
                        ServiceAdapter cateAdapter = new ServiceAdapter(arrayList, Insuarencefav.this,"Insurance");
                        recycler.setLayoutManager(new LinearLayoutManager(Insuarencefav.this, LinearLayoutManager.VERTICAL, false));
                        recycler.setAdapter(cateAdapter);
                        cateAdapter.notifyDataSetChanged();

                    } else {

                        recycler.setVisibility(View.GONE);
                    }

                } catch (Exception e) {


                    e.printStackTrace();
                    Toast.makeText(Insuarencefav.this, "" + e, Toast.LENGTH_SHORT).show();


                }
                // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Insuarencefav.this, "" + error, Toast.LENGTH_SHORT).show();


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