package com.eminence.drive13.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eminence.drive13.AddTransaction;
import com.eminence.drive13.R;
import com.eminence.drive13.utils.YourPreference;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static com.eminence.drive13.utils.Baseurl.baseurl;

public class Payments extends AppCompatActivity {

    TextView phonepay,googlepay,helpno;
    Button uploadphoto;
    String cityname, convertedimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        phonepay=findViewById(R.id.phonepay);
        googlepay=findViewById(R.id.googlepay);
        helpno=findViewById(R.id.helpno);
        uploadphoto=findViewById(R.id.uploadphoto);

        uploadphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbottomsheet();
            }
        });
        submit();
    }

    private void openbottomsheet() {

        final AlertDialog alertDialog = new AlertDialog.Builder(Payments.this).create();
        LayoutInflater inflater = getLayoutInflater();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View convertView = inflater.inflate(R.layout.camera, null);
        LinearLayout camera = convertView.findViewById(R.id.camera);
        LinearLayout gallery = convertView.findViewById(R.id.gallery);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 95);
                }

                alertDialog.dismiss();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
                alertDialog.dismiss();

            }
        });

        alertDialog.setView(convertView);
        alertDialog.show();


    }

    public void submit() {


        String url = baseurl + "payment_details.php";
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Payments.this);

        //  String username = sharedPreferences.getString("username", null);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(Payments.this);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Toast.makeText(HotelMain.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    String phone_pay = obj.getString("phone_pay");
                    String google_pay = obj.getString("google_pay");
                    String helpline_number = obj.getString("helpline_number");

                    phonepay.setText(phone_pay);
                    googlepay.setText(google_pay);
                    helpno.setText("Helpline Number : "+helpline_number);

                } catch (Exception e) {


                    e.printStackTrace();
                    Toast.makeText(Payments.this, "" + e, Toast.LENGTH_SHORT).show();


                }
                // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Payments.this, "" + error, Toast.LENGTH_SHORT).show();


            }
        }) {

        };
        stringRequest.setShouldCache(false);

        requestQueue.add(stringRequest);


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //adhar upload
        if (data != null && requestCode == 0) {


            if (resultCode == RESULT_OK) {
                Uri targetUri = data.getData();
                Bitmap bitmap;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));

                    float aspectRatio = bitmap.getWidth() /
                            (float) bitmap.getHeight();
                    int width = 280;
                    int height = Math.round(width / aspectRatio);

                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    convertedimage = ConvertBitmapToString(resizedBitmap);
                    //  Toast.makeText(this, ""+image, Toast.LENGTH_LONG).show();
                    // cheked1.setVisibility(View.VISIBLE);
                    uploadphototoserver();

                    //Upload();

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


        //take adhar
        if (data != null && requestCode == 95) {


            if (resultCode == RESULT_OK) {
                // Uri targetUri = data.getData();

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                float aspectRatio = bitmap.getWidth() /
                        (float) bitmap.getHeight();
                int width = 280;
                int height = Math.round(width / aspectRatio);

                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                convertedimage = ConvertBitmapToString(resizedBitmap);
                //  Toast.makeText(this, ""+image, Toast.LENGTH_LONG).show();
                // cheked1.setVisibility(View.VISIBLE);
                uploadphototoserver();
                //Upload();

            }
        }


    }

    private String ConvertBitmapToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] imageBytes = baos.toByteArray();

        String base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

        return base64String;
    }


    public void back(View view) {
        onBackPressed();
    }
    public void uploadphototoserver() {


        String url = baseurl + "payment_receipt_upload.php";
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        String id = yourPrefrence.getData("id");

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(Payments.this);
        Map<String, String> params = new HashMap();
         params.put("payment_photo", convertedimage);
         params.put("driver_id", id);


        JSONObject parameters = new JSONObject(params);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));


                    Toast.makeText(Payments.this, ""+obj.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {


                    e.printStackTrace();
                    Toast.makeText(Payments.this, "" + e, Toast.LENGTH_SHORT).show();


                }
                // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Payments.this, "" + error, Toast.LENGTH_SHORT).show();


            }
        }) {

        };
        stringRequest.setShouldCache(false);

        requestQueue.add(stringRequest);


    }

}