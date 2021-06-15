package com.eminence.drive13.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.eminence.drive13.Adapter.TransactionAdapter;
import com.eminence.drive13.Adapter.VehicleAdapter;
import com.eminence.drive13.HotelMain;
import com.eminence.drive13.R;
import com.eminence.drive13.Transaction;
import com.eminence.drive13.model.TransactionModel;
import com.eminence.drive13.model.VehicleModel;
import com.eminence.drive13.utils.YourPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.eminence.drive13.utils.Baseurl.baseurl;

public class Vehiclelist extends AppCompatActivity {
    ShimmerRecyclerView recycler;
    ArrayList<VehicleModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiclelist);
        recycler = findViewById(R.id.recyclerview);
        submit();
    }

    public void submit() {


        String url = baseurl + "driver_vehicle_list.php";
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Vehiclelist.this);
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        String id = yourPrefrence.getData("id");
        //  String username = sharedPreferences.getString("username", null);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(Vehiclelist.this);
        Map<String, String> params = new HashMap();
        params.put("driver_id", id);
        JSONObject parameters = new JSONObject(params);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Toast.makeText(HotelMain.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    String r_code = obj.getString("status");
                    //  Toast.makeText(Transaction.this, ""+obj.getString("message"), Toast.LENGTH_SHORT).show();

                    if (r_code.equalsIgnoreCase("1")) {

                        JSONArray jsonArray = obj.getJSONArray("data");


                        for (int i = 0; i < jsonArray.length(); i++) {

                            VehicleModel vehicleModel = new VehicleModel();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String id = jsonObject2.getString("id");
                            String brand_name = jsonObject2.getString("brand_name");
                            String model = jsonObject2.getString("model");
                            String manufacturer = jsonObject2.getString("manufacturer");
                            String vehicle_number = jsonObject2.getString("vehicle_number");
                            String vehicle_color = jsonObject2.getString("vehicle_color");
                            String rc_upload = jsonObject2.getString("rc_upload");
                            String insurance_pp = jsonObject2.getString("insurance_pp");

                            vehicleModel.setId(id);
                            vehicleModel.setBrand(brand_name);
                            vehicleModel.setColor(vehicle_color);
                            vehicleModel.setModel(model);
                            vehicleModel.setInsurancepp(insurance_pp);
                            vehicleModel.setManufracture(manufacturer);
                            vehicleModel.setV_number(vehicle_number);
                            vehicleModel.setRcupload(rc_upload);

                            arrayList.add(vehicleModel);

                        }
                        VehicleAdapter vehicleAdapter = new VehicleAdapter(arrayList, Vehiclelist.this);
                        recycler.setLayoutManager(new LinearLayoutManager(Vehiclelist.this, LinearLayoutManager.VERTICAL, false));
                        recycler.setAdapter(vehicleAdapter);
                        vehicleAdapter.notifyDataSetChanged();

                    } else {
                        recycler.setVisibility(View.GONE);

                    }

                } catch (Exception e) {


                    e.printStackTrace();
                    Toast.makeText(Vehiclelist.this, "" + e, Toast.LENGTH_SHORT).show();


                }
                // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Vehiclelist.this, "" + error, Toast.LENGTH_SHORT).show();


            }
        }) {

        };
        stringRequest.setShouldCache(false);

        requestQueue.add(stringRequest);


    }


    public void addtransactionvc(View view) {
        Intent intent = new Intent(getApplicationContext(), Regitervehicle.class);
        intent.putExtra("operatiton", "insert");
        startActivity(intent);


    }
}