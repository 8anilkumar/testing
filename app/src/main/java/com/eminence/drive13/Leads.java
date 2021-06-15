package com.eminence.drive13;

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
import com.eminence.drive13.Adapter.LeadsAdapter;
import com.eminence.drive13.Adapter.NewsAdapter;
import com.eminence.drive13.model.LeadsModel;
import com.eminence.drive13.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.eminence.drive13.utils.Baseurl.baseurl;

public class Leads extends AppCompatActivity {
    ShimmerRecyclerView recycler;
    ArrayList<LeadsModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leads);
        recycler = findViewById(R.id.recyclerview);
        submit();
    }


    public void submit() {


        String url = baseurl + "lead_list.php";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Leads.this);

        //  String username = sharedPreferences.getString("username", null);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(Leads.this);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
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

                            LeadsModel leadsModel = new LeadsModel();
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                String id = jsonObject2.getString("id");
                                String lead_id = jsonObject2.getString("lead_id");
                            String trip_date = jsonObject2.getString("trip_date");
                            String trip_time = jsonObject2.getString("trip_time");
                            String trip_from = jsonObject2.getString("trip_from");
                            String trip_destination = jsonObject2.getString("trip_destination");
                            String created_at = jsonObject2.getString("created_at");
                            String client_name = jsonObject2.getString("client_name");
                            String client_mobile = jsonObject2.getString("client_mobile");
                            String purpose = jsonObject2.getString("purpose");

                            leadsModel.setLead_id(lead_id);
                            leadsModel.setTrip_date(trip_date);
                            leadsModel.setTrip_time(trip_time);
                            leadsModel.setTrip_from(trip_from);
                            leadsModel.setTrip_destination(trip_destination);
                            leadsModel.setCreated_at(created_at);
                            leadsModel.setClientname(client_name);
                            leadsModel.setClientnumber(client_mobile);
                            leadsModel.setPurpose(purpose);

                            arrayList.add(leadsModel);

                        }
                        LeadsAdapter newsAdapter = new LeadsAdapter(arrayList, Leads.this);
                        recycler.setLayoutManager(new LinearLayoutManager(Leads.this, LinearLayoutManager.VERTICAL, false));
                        recycler.setAdapter(newsAdapter);
                        newsAdapter.notifyDataSetChanged();

                    } else {


                    }

                } catch (Exception e) {


                    e.printStackTrace();
                    Toast.makeText(Leads.this, "" + e, Toast.LENGTH_SHORT).show();


                }
                // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Leads.this, "" + error, Toast.LENGTH_SHORT).show();


            }
        }) {

        };
        stringRequest.setShouldCache(false);

        requestQueue.add(stringRequest);


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

    public void leadsdetails(View view) {
        startActivity(new Intent(getApplicationContext(), Leaddetails.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
    }
}