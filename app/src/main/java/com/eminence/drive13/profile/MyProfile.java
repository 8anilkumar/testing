package com.eminence.drive13.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eminence.drive13.R;
import com.eminence.drive13.utils.YourPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.eminence.drive13.utils.Baseurl.baseurl;

public class MyProfile extends AppCompatActivity {

    EditText number,name,email,address;
    RelativeLayout save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        number=findViewById(R.id.number);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        address=findViewById(R.id.address);
        save=findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateprofile();
            }
        });


        Driverprofile();
    }

    private void updateprofile() {

        if (name.getText().toString().equalsIgnoreCase("")) {
            name.setError("*Required");

        } else if (number.getText().toString().equalsIgnoreCase("")) {
            number.setError("*Required");

        } else if (email.getText().toString().equalsIgnoreCase("")) {
            email.setError("*Required");

        } else if (address.getText().toString().equalsIgnoreCase("")) {
            address.setError("*Required");

        }  else {
            String url = baseurl + "driver_profile_update.php";
            YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
            String id = yourPrefrence.getData("id");

            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(MyProfile.this);
            Map<String, String> params = new HashMap();
            params.put("name", name.getText().toString());
            params.put("driver_id", id);
            params.put("email", email.getText().toString());
            params.put("mobile", number.getText().toString());
            params.put("address", address.getText().toString());


            JSONObject parameters = new JSONObject(params);


            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONObject obj = new JSONObject(String.valueOf(response));

                        String r_code = obj.getString("status");
                        Toast.makeText(MyProfile.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();

                        if (r_code.equalsIgnoreCase("1")) {


                        }


                        //Toast.makeText(MyProfile.this, ""+obj.getString("message"), Toast.LENGTH_SHORT).show();


                    } catch (Exception e) {


                        e.printStackTrace();
                        Toast.makeText(MyProfile.this, "" + e, Toast.LENGTH_SHORT).show();


                    }
                    // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    Toast.makeText(MyProfile.this, "" + error, Toast.LENGTH_SHORT).show();


                }
            }) {

            };
            stringRequest.setShouldCache(false);

            requestQueue.add(stringRequest);


        }
    }
    public void Driverprofile() {


        String url = baseurl + "driver_profile.php";
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        String id = yourPrefrence.getData("id");

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(MyProfile.this);
        Map<String, String> params = new HashMap();
         params.put("driver_id", id);


        JSONObject parameters = new JSONObject(params);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    String r_code = obj.getString("status");
                    //Toast.makeText(Selctservice.this, ""+response, Toast.LENGTH_SHORT).show();

                    if (r_code.equalsIgnoreCase("1")) {

                        JSONArray jsonArray = obj.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                            String namee=jsonObject2.getString("name");
                            String mobile=jsonObject2.getString("mobile");
                            String emaill=jsonObject2.getString("email");
                            String addresss=jsonObject2.getString("address");
                            String d_license=jsonObject2.getString("d_license");
                            String adhar_card=jsonObject2.getString("adhar_card");
                            String pan_card=jsonObject2.getString("pan_card");

                          if (!namee.equalsIgnoreCase(""))
                          {
                              name.setText(namee);

                          }
                            if (!mobile.equalsIgnoreCase(""))
                            {
                                number.setText(mobile);

                            }
                            if (!emaill.equalsIgnoreCase(""))
                            {
                                email.setText(emaill);

                            }
                            if (!addresss.equalsIgnoreCase(""))
                            {
                                address.setText(addresss);

                            }

                        }
                        }


                        //Toast.makeText(MyProfile.this, ""+obj.getString("message"), Toast.LENGTH_SHORT).show();






                } catch (Exception e) {


                    e.printStackTrace();
                    Toast.makeText(MyProfile.this, "" + e, Toast.LENGTH_SHORT).show();


                }
                // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(MyProfile.this, "" + error, Toast.LENGTH_SHORT).show();


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