package com.eminence.drive13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eminence.drive13.utils.GPSTracker;
import com.eminence.drive13.utils.YourPreference;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.eminence.drive13.utils.Baseurl.baseurl;

public class Login extends AppCompatActivity {

    EditText number;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        number=findViewById(R.id.number);

        GPSTracker mGPS = new GPSTracker(getApplicationContext());

        Double lat,lng;

        if (mGPS.canGetLocation) {
            mGPS.getLocation();
            lat = mGPS.getLatitude();
            lng = mGPS.getLongitude();
            getCompleteAddressString(lat,lng);

        } else {
            Toast.makeText(mGPS, "location not Available", Toast.LENGTH_SHORT).show();
        }

    }


    private void getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            String cityName = addresses.get(0).getLocality();
            String subLocality = addresses.get(0).getSubLocality();
            String countryName = addresses.get(0).getAddressLine(2);
            YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
            yourPrefrence.saveData("cityname",cityName);
            yourPrefrence.saveData("sublocality",subLocality);
            // Toast.makeText(this, ""+cityName+stateName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
    }

    public void submit() {
        String url = baseurl + "driver_login.php";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);

        //  String username = sharedPreferences.getString("username", null);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(Login.this);

        Map<String, String> params = new HashMap();
        params.put("mobile", number.getText().toString());
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Toast.makeText(HotelMain.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    String r_code = obj.getString("status");
                    if (r_code.equalsIgnoreCase("1")) {
                        String name = obj.getString("name");
                        Intent intent=new Intent(Login.this,OTPScreen.class);
                        intent.putExtra("number",number.getText().toString());
                        intent.putExtra("name",name);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //     Toast.makeText(Signup.this, "" + e, Toast.LENGTH_SHORT).show();
                }
                // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(Signup.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    public void sendotp(View view) {
        if(number.getText().toString().length()!=10)
        {
            number.setError("Required 10 Digit Number");
        }
        else {
            submit();
        }
    }

    public void signup(View view) {
        startActivity(new Intent(getApplicationContext(), Signup.class));
    }

}