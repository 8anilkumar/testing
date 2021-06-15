package com.eminence.drive13.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eminence.drive13.HotelMain;
import com.eminence.drive13.R;
import com.eminence.drive13.utils.YourPreference;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static com.eminence.drive13.utils.Baseurl.baseurl;

public class PersnolDocuments extends AppCompatActivity {
    String text = "By continue  that i have read & agree to the \n <b>Term & Condition</b> and <b>Privacy Policy</b>";
    TextView textterm,dupload,adharupload,panupload;
    String dlicenseimage,panimage,adharimage;
    ImageView tickdriving,tickadhar,tickpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persnol_documents);
        textterm=findViewById(R.id.textterm);
        dupload=findViewById(R.id.dupload);
        adharupload=findViewById(R.id.adharupload);
        panupload=findViewById(R.id.panupload);
        tickdriving=findViewById(R.id.tickdriving);
        tickadhar=findViewById(R.id.tickadhar);
        tickpan=findViewById(R.id.tickpan);
        textterm.setText(Html.fromHtml(text));

        dupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbottomsheet(0,95);
            }
        });
        adharupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbottomsheet(1,96);

            }
        });
        panupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbottomsheet(2,97);

            }
        });

    }
     public void save(View view) {
         addtransaction();
    }

    public void addtransaction() {


      if (dlicenseimage.equalsIgnoreCase("")) {
            Toast.makeText(this, "Upload Driving License", Toast.LENGTH_SHORT).show();

        } else if (panimage.equalsIgnoreCase("")) {
            Toast.makeText(this, "Upload PAN Image", Toast.LENGTH_SHORT).show();

        }else if (adharimage.equalsIgnoreCase("")) {
            Toast.makeText(this, "Upload Aadhar Image", Toast.LENGTH_SHORT).show();

        } else {
            String url = baseurl + "driver_document_update.php";

            YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
            String id = yourPrefrence.getData("id");

            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(PersnolDocuments.this);

            Map<String, String> params = new HashMap();
            params.put("driver_id", id);
            params.put("d_license", dlicenseimage);
            params.put("adhar_card",adharimage);
            params.put("pan_card", panimage);



            JSONObject parameters = new JSONObject(params);


            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    // Toast.makeText(HotelMain.this, ""+response, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject obj = new JSONObject(String.valueOf(response));

                        String r_code = obj.getString("status");
                        Toast.makeText(PersnolDocuments.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();

                        if (r_code.equalsIgnoreCase("1")) {

                            startActivity(new Intent(getApplicationContext(), HotelMain.class));
                            finish();

                        }

                    } catch (Exception e) {


                        e.printStackTrace();
                        Toast.makeText(PersnolDocuments.this, "" + e, Toast.LENGTH_SHORT).show();


                    }
                    // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    Toast.makeText(PersnolDocuments.this, "" + error, Toast.LENGTH_SHORT).show();


                }
            }) {

            };
          stringRequest.setShouldCache(false);

          requestQueue.add(stringRequest);


        }


    }

    private void openbottomsheet(final int upload, final int take) {

        final AlertDialog alertDialog = new AlertDialog.Builder(PersnolDocuments.this).create();
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
                    startActivityForResult(intent, take);
                }

                alertDialog.dismiss();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, upload);
                alertDialog.dismiss();

            }
        });

        alertDialog.setView(convertView);
        alertDialog.show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //RC upload
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
                    dlicenseimage = ConvertBitmapToString(resizedBitmap);
                    tickdriving.setVisibility(View.VISIBLE);

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        //Insurance Policy upload
        if (data != null && requestCode == 1) {


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
                    adharimage = ConvertBitmapToString(resizedBitmap);
                    tickadhar.setVisibility(View.VISIBLE);


                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        if (data != null && requestCode == 2) {


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
                    panimage = ConvertBitmapToString(resizedBitmap);
                    tickpan.setVisibility(View.VISIBLE);


                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


        //take RC
        if (data != null && requestCode == 95) {


            if (resultCode == RESULT_OK) {
                // Uri targetUri = data.getData();

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                float aspectRatio = bitmap.getWidth() /
                        (float) bitmap.getHeight();
                int width = 280;
                int height = Math.round(width / aspectRatio);

                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                dlicenseimage = ConvertBitmapToString(resizedBitmap);
                tickdriving.setVisibility(View.VISIBLE);


            }
        }

        //take Insurance

        if (data != null && requestCode == 96) {


            if (resultCode == RESULT_OK) {
                // Uri targetUri = data.getData();

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                float aspectRatio = bitmap.getWidth() /
                        (float) bitmap.getHeight();
                int width = 280;
                int height = Math.round(width / aspectRatio);

                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                adharimage = ConvertBitmapToString(resizedBitmap);
                tickadhar.setVisibility(View.VISIBLE);


            }
        }
//take Insurance

        if (data != null && requestCode == 97) {


            if (resultCode == RESULT_OK) {
                // Uri targetUri = data.getData();

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                float aspectRatio = bitmap.getWidth() /
                        (float) bitmap.getHeight();
                int width = 280;
                int height = Math.round(width / aspectRatio);

                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                panimage = ConvertBitmapToString(resizedBitmap);
                tickpan.setVisibility(View.VISIBLE);


            }
        }

    }

    private String ConvertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] imageBytes = baos.toByteArray();

        String base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

        return base64String;
    }
}