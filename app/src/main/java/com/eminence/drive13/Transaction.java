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
import com.eminence.drive13.Adapter.TransactionAdapter;
import com.eminence.drive13.model.LeadsModel;
import com.eminence.drive13.model.TransactionModel;
import com.eminence.drive13.utils.YourPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.eminence.drive13.utils.Baseurl.baseurl;

public class Transaction extends AppCompatActivity {
    ShimmerRecyclerView recycler;
    ArrayList<TransactionModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        recycler = findViewById(R.id.recyclerview);
        submit();
    }

    public void submit() {
        String url = baseurl + "driver_transaction_list.php";
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        String id = yourPrefrence.getData("id");
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(Transaction.this);
        Map<String, String> params = new HashMap();
        params.put("driver_id",id);
        JSONObject parameters = new JSONObject(params);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    String r_code = obj.getString("status");
                    if (r_code.equalsIgnoreCase("1")) {
                        JSONArray jsonArray = obj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            TransactionModel transactionModel = new TransactionModel();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String id = jsonObject2.getString("id");
                            String transaction_for = jsonObject2.getString("transaction_for");
                            String name = jsonObject2.getString("name");
                            String location = jsonObject2.getString("location");
                            String bill_photo = jsonObject2.getString("bill_photo");
                            String selfi_photo = jsonObject2.getString("selfie");

                            String insertdate = jsonObject2.getString("insertdate");
                            String representative_name = jsonObject2.getString("representative_name");
                            String representative_mobile = jsonObject2.getString("representative_mobile");
                            String billing_amount = jsonObject2.getString("billing_amount");

                            transactionModel.setId(id);
                            transactionModel.setTransaction_for(transaction_for);
                            transactionModel.setName(name);
                            transactionModel.setLocation(location);
                            transactionModel.setBill_photo(bill_photo);
                            transactionModel.setSelfi_photo(selfi_photo);

                            transactionModel.setInsertdate(insertdate);
                            transactionModel.setRname(representative_name);
                            transactionModel.setRmobile(representative_mobile);
                            transactionModel.setBillaount(billing_amount);
                           arrayList.add(transactionModel);

                        }

                        TransactionAdapter newsAdapter = new TransactionAdapter(arrayList, Transaction.this);
                        recycler.setLayoutManager(new LinearLayoutManager(Transaction.this, LinearLayoutManager.VERTICAL, false));
                        recycler.setAdapter(newsAdapter);
                        newsAdapter.notifyDataSetChanged();

                    } else {
                        recycler.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Transaction.this, "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Transaction.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        stringRequest.setShouldCache(false);

        requestQueue.add(stringRequest);
    }

    public void transactionview(View view) {
        startActivity(new Intent(getApplicationContext(), Transactiondetails.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
    }
    public void addtransactionvc(View view) {
        startActivity(new Intent(getApplicationContext(), AddTransaction.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
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
}