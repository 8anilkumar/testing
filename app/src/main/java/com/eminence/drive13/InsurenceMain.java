package com.eminence.drive13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
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
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eminence.drive13.Adapter.ServiceAdapter;
import com.eminence.drive13.model.ServiceModel;
import com.eminence.drive13.utils.YourPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.eminence.drive13.utils.Baseurl.baseurl;

public class InsurenceMain extends AppCompatActivity {
    ShimmerRecyclerView recycler;
    ArrayList<ServiceModel> arrayList = new ArrayList<>();
    String cityname,id,sublocality;
    TextView location;
    ImageView search,cross,searchinner;
    LinearLayout layoutserachnoti,serachcard;
    EditText searchedittext;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurence_main);
        recycler = findViewById(R.id.recyclerview);
        location=findViewById(R.id.location);
        search=findViewById(R.id.search);
        serachcard=findViewById(R.id.serachcard);
        layoutserachnoti=findViewById(R.id.layoutserachnoti);
        searchedittext=findViewById(R.id.searchedittext);
        searchinner=findViewById(R.id.searchinner);
        cross=findViewById(R.id.cross);
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        sublocality = yourPrefrence.getData("sublocality");
        cityname = yourPrefrence.getData("cityname");
        id = yourPrefrence.getData("id");
        location.setText(sublocality+", "+cityname);
        submit();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                serachcard.setVisibility(View.VISIBLE);
                layoutserachnoti.setVisibility(View.GONE);
                YoYo.with(Techniques.Tada)
                        .duration(200)
                        .repeat(1)
                        .playOn(findViewById(R.id.serachcard));
            }
        });cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                serachcard.setVisibility(View.GONE);
                layoutserachnoti.setVisibility(View.VISIBLE);
            }
        });searchinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchfun();
            }
        });

    }


    private void searchfun() {

        String url = baseurl + "search.php";

        arrayList.clear();
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(InsurenceMain.this);

        Map<String, String> params = new HashMap();
        params.put("category", "Insurance");
        params.put("driver_id", id);
        params.put("city", cityname);
        params.put("keyword", searchedittext.getText().toString());


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
                            String favourite = jsonObject2.getString("favourite");

                            serviceModel.setId(id);
                            serviceModel.setName(name);
                            serviceModel.setLocation(location);
                            serviceModel.setMobile(mobile);
                            serviceModel.setMainimage(main_image);
                            serviceModel.setLogo(logo);
                            serviceModel.setFavourite(favourite);


                            arrayList.add(serviceModel);

                        }
                        ServiceAdapter cateAdapter = new ServiceAdapter(arrayList, InsurenceMain.this,"Insurance");
                        recycler.setLayoutManager(new LinearLayoutManager(InsurenceMain.this, LinearLayoutManager.VERTICAL, false));
                        recycler.setAdapter(cateAdapter);
                        cateAdapter.notifyDataSetChanged();

                    } else {


                    }

                } catch (Exception e) {


                    e.printStackTrace();
                    Toast.makeText(InsurenceMain.this, "" + e, Toast.LENGTH_SHORT).show();


                }
                // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(InsurenceMain.this, "" + error, Toast.LENGTH_SHORT).show();


            }
        }) {

        };
        stringRequest.setShouldCache(false);

        requestQueue.add(stringRequest);


    }

    public void submit() {

        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        String cityname = yourPrefrence.getData("cityname");

        String url = baseurl + "services_list_by_category.php";
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(InsurenceMain.this);

        //  String username = sharedPreferences.getString("username", null);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(InsurenceMain.this);

        Map<String, String> params = new HashMap();
        params.put("category", "Insurance");
        params.put("driver_id", id);
        params.put("city", cityname);


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
                            String favourite = jsonObject2.getString("favourite");

                            serviceModel.setId(id);
                            serviceModel.setName(name);
                            serviceModel.setLocation(location);
                            serviceModel.setMobile(mobile);
                            serviceModel.setMainimage(main_image);
                            serviceModel.setLogo(logo);
                            serviceModel.setFavourite(favourite);


                            arrayList.add(serviceModel);

                        }
                        ServiceAdapter cateAdapter = new ServiceAdapter(arrayList, InsurenceMain.this,"Insurance");
                        recycler.setLayoutManager(new LinearLayoutManager(InsurenceMain.this, LinearLayoutManager.VERTICAL, false));
                        recycler.setAdapter(cateAdapter);
                        cateAdapter.notifyDataSetChanged();

                    } else {


                    }

                } catch (Exception e) {


                    e.printStackTrace();
                    Toast.makeText(InsurenceMain.this, "" + e, Toast.LENGTH_SHORT).show();


                }
                // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(InsurenceMain.this, "" + error, Toast.LENGTH_SHORT).show();


            }
        }) {

        };
        stringRequest.setShouldCache(false);

        requestQueue.add(stringRequest);


    }

    public void workshop(View view) {
        startActivity(new Intent(getApplicationContext(), WorkshopMain.class));
        overridePendingTransition(0, 0);
        finish();
    }

    public void hotel(View view) {
        startActivity(new Intent(getApplicationContext(), HotelMain.class));
        overridePendingTransition(0, 0);
        finish();
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
    public void hoteldetail(View view) {
        startActivity(new Intent(getApplicationContext(), HotelDetails.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);

    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            finishAffinity();
            return;
        }


        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to Close Drive13", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}