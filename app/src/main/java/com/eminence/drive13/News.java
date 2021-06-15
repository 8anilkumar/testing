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
import com.eminence.drive13.Adapter.NewsAdapter;
import com.eminence.drive13.Adapter.ServiceAdapter;
import com.eminence.drive13.model.NewsModel;
import com.eminence.drive13.model.ServiceModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.eminence.drive13.utils.Baseurl.baseurl;

public class News extends AppCompatActivity {
    ShimmerRecyclerView recycler;
    ArrayList<NewsModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recycler = findViewById(R.id.recyclerview);
        submit();
    }

    public void submit() {
        String url = baseurl + "news_list.php";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(News.this);

        RequestQueue requestQueue = Volley.newRequestQueue(News.this);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    String r_code = obj.getString("status");
                    if (r_code.equalsIgnoreCase("1")) {
                        JSONArray jsonArray = obj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            NewsModel newsModel = new NewsModel();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String id = jsonObject2.getString("id");
                            String headline = jsonObject2.getString("headline");
                            String content = jsonObject2.getString("content");
                            String image = jsonObject2.getString("image");
                            String date_time = jsonObject2.getString("date_time");
                            newsModel.setId(id);
                            newsModel.setHeadline(headline);
                            newsModel.setContent(content);
                            newsModel.setImage(image);
                            newsModel.setDate_time(date_time);
                            arrayList.add(newsModel);
                        }
                        NewsAdapter newsAdapter = new NewsAdapter(arrayList, News.this);
                        recycler.setLayoutManager(new LinearLayoutManager(News.this, LinearLayoutManager.VERTICAL, false));
                        recycler.setAdapter(newsAdapter);
                        newsAdapter.notifyDataSetChanged();

                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(News.this, "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(News.this, "" + error, Toast.LENGTH_SHORT).show();
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

    public void newsdetail(View view) {
        startActivity(new Intent(getApplicationContext(), Newsdetail.class));
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);

    }
}