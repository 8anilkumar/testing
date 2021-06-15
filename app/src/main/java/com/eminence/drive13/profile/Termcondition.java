package com.eminence.drive13.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eminence.drive13.R;

import org.json.JSONObject;

import static com.eminence.drive13.utils.Baseurl.baseurl;

public class Termcondition extends AppCompatActivity {

    TextView term;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termcondition);
        term=findViewById(R.id.term);
        submit();
    }
    public void submit() {


        String url = baseurl + "content.php";
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Termcondition.this);

        //  String username = sharedPreferences.getString("username", null);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(Termcondition.this);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Toast.makeText(HotelMain.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    String term_condition = obj.getString("term_condition");
                    //Toast.makeText(Selctservice.this, ""+response, Toast.LENGTH_SHORT).show();

                    term.setText(Html.fromHtml(term_condition));

                } catch (Exception e) {


                    e.printStackTrace();
                    Toast.makeText(Termcondition.this, "" + e, Toast.LENGTH_SHORT).show();


                }
                // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Termcondition.this, "" + error, Toast.LENGTH_SHORT).show();


            }
        }) {

        };
        stringRequest.setShouldCache(false);

        requestQueue.add(stringRequest);


    }

}