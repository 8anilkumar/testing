package com.eminence.drive13.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
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

public class ContactUs extends AppCompatActivity {
    Button phone1,phone2,email1,email2;
    TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        phone1=findViewById(R.id.phone1);
        phone2=findViewById(R.id.phone2);
        email1=findViewById(R.id.email1);
        email2=findViewById(R.id.email2);
        address=findViewById(R.id.address);
        submit();
    }
    public void mobile(View view) {
        dialContactPhone("9999999999");
    }
    public void mail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "help@car13.in", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Car13 Enquiry");
        startActivity(Intent.createChooser(emailIntent, null));

    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }


    public void submit() {


        String url = baseurl + "content.php";
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(ContactUs.this);

        //  String username = sharedPreferences.getString("username", null);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(ContactUs.this);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Toast.makeText(HotelMain.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    String landline_number = obj.getString("landline_number");
                    String mobile_number = obj.getString("mobile_number");
                    String first_email = obj.getString("first_email");
                    String second_email = obj.getString("second_email");
                    String addresss = obj.getString("address");
                    //Toast.makeText(Selctservice.this, ""+response, Toast.LENGTH_SHORT).show();

                    phone1.setText(landline_number);
                    phone2.setText(mobile_number);
                    email1.setText(first_email);
                    email2.setText(second_email);
                    address.setText(addresss);

                } catch (Exception e) {


                    e.printStackTrace();
                    Toast.makeText(ContactUs.this, "" + e, Toast.LENGTH_SHORT).show();


                }
                // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(ContactUs.this, "" + error, Toast.LENGTH_SHORT).show();


            }
        }) {

        };
        stringRequest.setShouldCache(false);

        requestQueue.add(stringRequest);


    }

}