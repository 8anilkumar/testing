package com.eminence.drive13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.eminence.drive13.Adapter.ServiceAdapter;
import com.eminence.drive13.model.ServiceModel;
import com.eminence.drive13.utils.YourPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static com.eminence.drive13.utils.Baseurl.baseurl;

public class HotelMain extends AppCompatActivity {
    ShimmerRecyclerView recycler,recommendrecyclerview;
    ArrayList<ServiceModel> arrayList = new ArrayList<>();
    ArrayList<ServiceModel> arrayList2 = new ArrayList<>();
    String cityname,sublocality,id,service;
    TextView location,sp_fromLocation,sp_toLocation;
    ImageView search,cross,searchinner;
    LinearLayout layoutserachnoti,serachcard;
    EditText searchedittext;
    boolean doubleBackToExitPressedOnce = false;
    ImageView img_hotelResturants,img_ownerAndWheel,img_tourAndPackages,img_petrolPump,img_guidesAndShowrooms,img_insuranceAndWorkshop;
    ProgressBar mainProgressBar;
    TextView datanotfound;
    LinearLayout locationLayout;
    SpinnerDialog spinnerDialogFrom, spinnerDialogTo;
    Button btn_submitLocation;
    ArrayList<String> servicename = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotelmain);
        location=findViewById(R.id.location);
        search=findViewById(R.id.search);
        serachcard=findViewById(R.id.serachcard);
        layoutserachnoti=findViewById(R.id.layoutserachnoti);
        recommendrecyclerview=findViewById(R.id.recommendrecyclerview);
        searchedittext=findViewById(R.id.searchedittext);
        datanotfound=findViewById(R.id.datanotfound);
        searchinner=findViewById(R.id.searchinner);
        cross=findViewById(R.id.cross);
        mainProgressBar=findViewById(R.id.mainProgressBar);
        locationLayout=findViewById(R.id.locationLayout);

        img_hotelResturants=findViewById(R.id.img_hotelResturants);
        img_ownerAndWheel=findViewById(R.id.img_ownerAndWheel);
        img_tourAndPackages=findViewById(R.id.img_tourAndPackages);
        img_petrolPump=findViewById(R.id.img_petrolPump);
        img_guidesAndShowrooms=findViewById(R.id.img_guidesAndShowrooms);
        img_insuranceAndWorkshop=findViewById(R.id.img_insuranceAndWorkshop);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serachcard.setVisibility(View.VISIBLE);
                layoutserachnoti.setVisibility(View.GONE);
               /* YoYo.with(Techniques.Tada)
                        .duration(200)
                        .repeat(1)
                        .playOn(findViewById(R.id.serachcard));*/
            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serachcard.setVisibility(View.GONE);
                layoutserachnoti.setVisibility(View.VISIBLE);
                YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
                service = yourPrefrence.getData("serviceName");
                if(service.equals("Hotel")){
                    submit(service);
                    submitrecomended(service);
                } else if(service.equals("Owner Wheel")){
                    submit(service);
                    submitrecomended(service);
                } else if(service.equals("Tour Package")){
                    submit(service);
                    submitrecomended(service);
                } else if(service.equals("Petrol Pump")){
                    submit(service);
                    submitrecomended(service);
                } else if(service.equals("Workshop")){
                    submit(service);
                    submitrecomended(service);
                } else if(service.equals("Insurance")){
                    submit(service);
                    submitrecomended(service);
                }
            }
        });

        searchinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchfun();
            }
        });

        final YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        id = yourPrefrence.getData("id");
        cityname = yourPrefrence.getData("cityname");
        sublocality = yourPrefrence.getData("sublocality");
        String fromlocation = yourPrefrence.getData("fromlocation");
        String tolocation = yourPrefrence.getData("tolocation");

        if(tolocation.equalsIgnoreCase(""))
        {
            location.setText(sublocality+","+cityname);
        } else
        {
            location.setText(fromlocation+"-"+tolocation);
        }

        recycler = findViewById(R.id.recyclerview);

        submit("Hotel");
        submitrecomended("Hotel");
        yourPrefrence.saveData("serviceName","Hotel");

        locationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLocationData();
                final Dialog dialog = new Dialog(HotelMain.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.location_dialog);

                btn_submitLocation = dialog.findViewById(R.id.btn_submitLocation);
                sp_fromLocation = dialog.findViewById(R.id.sp_fromLocation);
                sp_toLocation = dialog.findViewById(R.id.sp_toLocation);

                YourPreference yourPreference=YourPreference.getInstance(HotelMain.this);
                String fromdata= yourPreference.getData("fromlocation");
                String todata=yourPreference.getData("tolocation");

                sp_fromLocation.setText(fromdata);
                sp_toLocation.setText(todata);
                sp_fromLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        spinnerDialogFrom.showSpinerDialog();
                    }
                });


                sp_toLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        spinnerDialogTo.showSpinerDialog();
                    }
                });


                btn_submitLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sp_fromLocation.getText().toString().equalsIgnoreCase("")) {
                            Toast.makeText(HotelMain.this, "Select Location From", Toast.LENGTH_SHORT).show();
                        } else if (sp_toLocation.getText().toString().equalsIgnoreCase("")) {
                            Toast.makeText(HotelMain.this, "Select Location To", Toast.LENGTH_SHORT).show();
                        } else {
                            yourPrefrence.saveData("fromlocation",sp_fromLocation.getText().toString());
                            yourPrefrence.saveData("tolocation",sp_toLocation.getText().toString());
                            //yourPrefrence.saveData("cityname",sp_toLocation.getText().toString()+"-"+sp_toLocation.getText().toString());

                            YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
                            String serviceNamee = yourPrefrence.getData("serviceName");
                            submit(serviceNamee);
                            submitrecomended(serviceNamee);
                            location.setText(sp_fromLocation.getText().toString()+"-"+sp_toLocation.getText().toString());

                            dialog.dismiss();

                        }
                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();

            }
        });




    }

    private void getLocationData(){
        servicename.clear();
        String url = baseurl + "cities.php";
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        String id = yourPrefrence.getData("id");
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(HotelMain.this);
        Map<String, String> params = new HashMap();
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    String r_code = obj.getString("status");
                    if (r_code.equalsIgnoreCase("1")) {
                        JSONArray jsonArray = obj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String name = jsonObject2.getString("name");
                            servicename.add(name);
                        }

                        spinnerDialogFrom = new SpinnerDialog(HotelMain.this, servicename, "Choose " , R.style.DialogAnimations_SmileWindow, "Close  ");// With 	Animation
                        spinnerDialogFrom.setCancellable(true); // for cancellable
                        spinnerDialogFrom.setShowKeyboard(false);// for open keyboard by default
                        spinnerDialogFrom.bindOnSpinerListener(new OnSpinerItemClick() {
                            @Override
                            public void onClick(String item, int position) {
                                sp_fromLocation.setText(item);
                            }
                        });

                        spinnerDialogTo = new SpinnerDialog(HotelMain.this, servicename, "Choose " , R.style.DialogAnimations_SmileWindow, "Close  ");// With 	Animation
                        spinnerDialogTo.setCancellable(true); // for cancellable
                        spinnerDialogTo.setShowKeyboard(false);// for open keyboard by default
                        spinnerDialogTo.bindOnSpinerListener(new OnSpinerItemClick() {
                            @Override
                            public void onClick(String item, int position) {
                                sp_toLocation.setText(item);
                            }
                        });

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(HotelMain.this, "" + e, Toast.LENGTH_SHORT).show();
                }
                // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HotelMain.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        stringRequest.setShouldCache(false);

        requestQueue.add(stringRequest);
    }

    private void searchfun() {
        String url = baseurl + "search.php";
        arrayList.clear();
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(HotelMain.this);
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        service = yourPrefrence.getData("serviceName");
        Map<String, String> params = new HashMap();
        params.put("category", service);
        params.put("driver_id", id);
        params.put("city", cityname);
        params.put("keyword", searchedittext.getText().toString());
        mainProgressBar.setVisibility(View.VISIBLE);
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mainProgressBar.setVisibility(View.GONE);
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    String r_code = obj.getString("status");
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
                        ServiceAdapter cateAdapter = new ServiceAdapter(arrayList, HotelMain.this,"Hotel");
                        recycler.setLayoutManager(new LinearLayoutManager(HotelMain.this, LinearLayoutManager.VERTICAL, false));
                        recycler.setAdapter(cateAdapter);
                        cateAdapter.notifyDataSetChanged();

                    } else {


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(HotelMain.this, "" + e, Toast.LENGTH_SHORT).show();
                }
                // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mainProgressBar.setVisibility(View.GONE);

                Toast.makeText(HotelMain.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);


    }

    public void submit(String hotel) {

        arrayList.clear();

        final ProgressDialog pd = new ProgressDialog(HotelMain.this);
        pd.setMessage("loading...");
        pd.show();
        pd.setCancelable(false);


        String url = baseurl + "services_list_by_category.php";
        RequestQueue requestQueue;
        YourPreference yourPreference=YourPreference.getInstance(HotelMain.this);
        String fromdata= yourPreference.getData("fromlocation");
        String todata=yourPreference.getData("tolocation");
        requestQueue = Volley.newRequestQueue(HotelMain.this);
        Map<String, String> params = new HashMap();
        params.put("category", hotel);
        params.put("driver_id", id);
        if (fromdata.equalsIgnoreCase(""))
        {
            params.put("city", cityname);
        }
        else {
            params.put("from", fromdata);
            params.put("to", todata);
        }

        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    String r_code = obj.getString("status1");

                    if (r_code.equalsIgnoreCase("1")) {
                        pd.dismiss();
                        JSONArray jsonArray = obj.getJSONArray("data1");
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
                            recycler.setVisibility(View.VISIBLE);
                            datanotfound.setVisibility(View.GONE);
                            ServiceAdapter cateAdapter = new ServiceAdapter(arrayList, HotelMain.this,"Hotel");
                            recycler.setLayoutManager(new LinearLayoutManager(HotelMain.this, LinearLayoutManager.VERTICAL, false));
                            recycler.setAdapter(cateAdapter);


                    } else {
                        pd.dismiss();

                         recycler.setVisibility(View.GONE);
                     }
                } catch (Exception e) {
                    pd.dismiss();

                    e.printStackTrace();
                 //   Toast.makeText(HotelMain.this, "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();

                //     Toast.makeText(HotelMain.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
        };
        stringRequest.setShouldCache(false);

        requestQueue.add(stringRequest);

    }

    public void submitrecomended(String hotel) {
        arrayList2.clear();
        String url = baseurl + "services_list_by_category.php";
        RequestQueue requestQueue;
        YourPreference yourPreference=YourPreference.getInstance(HotelMain.this);
        String fromdata= yourPreference.getData("fromlocation");
        String todata=yourPreference.getData("tolocation");
        requestQueue = Volley.newRequestQueue(HotelMain.this);
        Map<String, String> params = new HashMap();
        params.put("category", hotel);
        params.put("driver_id", id);
        if (fromdata.equalsIgnoreCase(""))
        {
            params.put("city", cityname);
        }
        else {
            params.put("from", fromdata);
            params.put("to", todata);
        }

        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    String r_code = obj.getString("status2");

                    if (r_code.equalsIgnoreCase("1")) {
                        JSONArray jsonArray = obj.getJSONArray("data2");
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
                            arrayList2.add(serviceModel);

                        }
                            recommendrecyclerview.setVisibility(View.VISIBLE);
                            datanotfound.setVisibility(View.GONE);
                            ServiceAdapter cateAdapter = new ServiceAdapter(arrayList2, HotelMain.this,"Hotel");
                        recommendrecyclerview.setLayoutManager(new LinearLayoutManager(HotelMain.this, LinearLayoutManager.VERTICAL, false));
                        recommendrecyclerview.setAdapter(cateAdapter);


                    } else {
                         recommendrecyclerview.setVisibility(View.GONE);
                      }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(HotelMain.this, "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HotelMain.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
        };
        stringRequest.setShouldCache(false);

        requestQueue.add(stringRequest);

    }

    public void workshopmain(View view) {
        img_hotelResturants.setColorFilter(Color.argb(255, 136, 136, 136));
        img_ownerAndWheel.setColorFilter(Color.argb(255, 136, 136, 136));
        img_tourAndPackages.setColorFilter(Color.argb(255, 136, 136, 136));
        img_petrolPump.setColorFilter(Color.argb(255, 136, 136, 136));
        img_guidesAndShowrooms.setColorFilter(Color.argb(255, 255, 219, 0));
        img_insuranceAndWorkshop.setColorFilter(Color.argb(255, 136, 136, 136));

          submit("Workshop");
          submitrecomended("Workshop");
          YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
          yourPrefrence.saveData("serviceName","Workshop");

    }

    public void insurence(View view) {
        img_hotelResturants.setColorFilter(Color.argb(255, 136, 136, 136));
        img_ownerAndWheel.setColorFilter(Color.argb(255, 136, 136, 136));
        img_tourAndPackages.setColorFilter(Color.argb(255, 136, 136, 136));
        img_petrolPump.setColorFilter(Color.argb(255, 136, 136, 136));
        img_guidesAndShowrooms.setColorFilter(Color.argb(255, 136, 136, 136));
        img_insuranceAndWorkshop.setColorFilter(Color.argb(255, 255, 219, 0));

        submit("Insurance");
        submitrecomended("Insurance");
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        yourPrefrence.saveData("serviceName","Insurance");

//        startActivity(new Intent(getApplicationContext(), InsurenceMain.class));
//        overridePendingTransition(0, 0);
//        finish();

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


    public void woneronwheel(View view) {
        submit("Owner Wheel");
        submitrecomended("Owner Wheel");
        img_hotelResturants.setColorFilter(Color.argb(255, 136, 136, 136));
        img_ownerAndWheel.setColorFilter(Color.argb(255, 255, 219, 0));
        img_tourAndPackages.setColorFilter(Color.argb(255, 136, 136, 136));
        img_petrolPump.setColorFilter(Color.argb(255, 136, 136, 136));
        img_guidesAndShowrooms.setColorFilter(Color.argb(255, 136, 136, 136));
        img_insuranceAndWorkshop.setColorFilter(Color.argb(255, 136, 136, 136));


        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        yourPrefrence.saveData("serviceName","Owner Wheel");
    }

    public void tourpackages(View view) {
        submit("Tour Package");
        submitrecomended("Tour Package");
        img_hotelResturants.setColorFilter(Color.argb(255, 136, 136, 136));
        img_ownerAndWheel.setColorFilter(Color.argb(255, 136, 136, 136));
        img_tourAndPackages.setColorFilter(Color.argb(255, 255, 219, 0));
        img_petrolPump.setColorFilter(Color.argb(255, 136, 136, 136));
        img_guidesAndShowrooms.setColorFilter(Color.argb(255, 136, 136, 136));
        img_insuranceAndWorkshop.setColorFilter(Color.argb(255, 136, 136, 136));
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        yourPrefrence.saveData("serviceName","Tour Package");
    }

    public void petrolpump(View view) {

        submit("Petrol Pump");
        submitrecomended("Petrol Pump");
        img_hotelResturants.setColorFilter(Color.argb(255, 136, 136, 136));
        img_ownerAndWheel.setColorFilter(Color.argb(255, 136, 136, 136));
        img_tourAndPackages.setColorFilter(Color.argb(255, 136, 136, 136));
        img_petrolPump.setColorFilter(Color.argb(255, 255, 219, 0));
        img_guidesAndShowrooms.setColorFilter(Color.argb(255, 136, 136, 136));
        img_insuranceAndWorkshop.setColorFilter(Color.argb(255, 136, 136, 136));
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        yourPrefrence.saveData("serviceName","Petrol Pump");
    }

    public void hotelAndRestorance(View view) {
        img_hotelResturants.setColorFilter(Color.argb(255, 255, 219, 0));
        img_ownerAndWheel.setColorFilter(Color.argb(255, 136, 136, 136));
        img_tourAndPackages.setColorFilter(Color.argb(255, 136, 136, 136));
        img_petrolPump.setColorFilter(Color.argb(255, 136, 136, 136));
        img_guidesAndShowrooms.setColorFilter(Color.argb(255, 136, 136, 136));
        img_insuranceAndWorkshop.setColorFilter(Color.argb(255, 136, 136, 136));
        submit("Hotel");
        submitrecomended("Hotel");
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        yourPrefrence.saveData("serviceName","Hotel");

    }

}