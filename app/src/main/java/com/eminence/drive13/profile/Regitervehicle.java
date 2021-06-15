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
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.eminence.drive13.AddTransaction;
import com.eminence.drive13.HotelMain;
import com.eminence.drive13.R;
import com.eminence.drive13.utils.YourPreference;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static com.eminence.drive13.utils.Baseurl.baseurl;

public class Regitervehicle extends AppCompatActivity {

    EditText brandname, model, manufracture, numberplate, color;
    TextView rcbookupload, insuarnecpolicy,registertext;
    String rcbookimage = "", insurangeimage = "";
    ImageView tick,tick2;
    String op,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regitervehicle);
        brandname = findViewById(R.id.brandname);
        model = findViewById(R.id.model);
        manufracture = findViewById(R.id.manufracture);
        numberplate = findViewById(R.id.numberplate);
        color = findViewById(R.id.color);
        rcbookupload = findViewById(R.id.rcbookupload);
        registertext = findViewById(R.id.registertext);
        insuarnecpolicy = findViewById(R.id.insuarnecpolicy);
        tick2 = findViewById(R.id.tick2);
        tick = findViewById(R.id.tick);

          op=getIntent().getExtras().getString("operatiton");
        if (op.equalsIgnoreCase("update"))
        {
            String brand=getIntent().getExtras().getString("brand");
            String modell=getIntent().getExtras().getString("model");
            String manufacturt=getIntent().getExtras().getString("manufacturt");
            String coloor=getIntent().getExtras().getString("color");
            id=getIntent().getExtras().getString("id");
            String insuranceimage=getIntent().getExtras().getString("insuranceimage");
            String v_number=getIntent().getExtras().getString("v_number");
            String rcupload=getIntent().getExtras().getString("rcupload");
            brandname.setText(brand);
            model.setText(modell);
            manufracture.setText(manufacturt);
            numberplate.setText(v_number);
            color.setText(coloor);
            color.setText(coloor);
            registertext.setText("Update Details");
        }
        rcbookupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbottomsheet(0, 95);
            }
        });
        insuarnecpolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbottomsheet(1, 96);
            }
        });

    }

    public void back(View view) {
        onBackPressed();
    }

    public void Addvehicle(View view) {

        if (op.equalsIgnoreCase("update"))
        {
            updatevehicle();
        }
        else {
            addtransaction();
        }

    }

    public void addtransaction() {


        if (brandname.getText().toString().equalsIgnoreCase("")) {
            brandname.setError("*Required");
        } else if (model.getText().toString().equalsIgnoreCase("")) {
            model.setError("*Required");

        } else if (manufracture.getText().toString().equalsIgnoreCase("")) {
            manufracture.setError("*Required");

        } else if (numberplate.getText().toString().equalsIgnoreCase("")) {
            numberplate.setError("*Required");

        } else if (color.getText().toString().equalsIgnoreCase("")) {
            color.setError("*Required");

        } else if (rcbookimage.equalsIgnoreCase("")) {
            Toast.makeText(this, "Upload Rc Book", Toast.LENGTH_SHORT).show();

        } else if (insurangeimage.equalsIgnoreCase("")) {
            Toast.makeText(this, "Upload Insurance Policy", Toast.LENGTH_SHORT).show();

        } else {
            String url = baseurl + "driver_add_vehicle.php";

            YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
            String id = yourPrefrence.getData("id");

            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(Regitervehicle.this);

            Map<String, String> params = new HashMap();
            params.put("driver_id", id);
            params.put("brand_name", brandname.getText().toString());
            params.put("model", model.getText().toString());
            params.put("manufacturer", manufracture.getText().toString());
            params.put("vehicle_number", numberplate.getText().toString());
            params.put("vehicle_color", color.getText().toString());
            params.put("rc_upload", rcbookimage);
            params.put("insurance_pp", insurangeimage);


            JSONObject parameters = new JSONObject(params);


            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    // Toast.makeText(HotelMain.this, ""+response, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject obj = new JSONObject(String.valueOf(response));

                        String r_code = obj.getString("status");
                        Toast.makeText(Regitervehicle.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();

                        if (r_code.equalsIgnoreCase("1")) {

                            startActivity(new Intent(getApplicationContext(), HotelMain.class));
                            finish();

                        }

                    } catch (Exception e) {


                        e.printStackTrace();
                        Toast.makeText(Regitervehicle.this, "" + e, Toast.LENGTH_SHORT).show();


                    }
                    // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    Toast.makeText(Regitervehicle.this, "" + error, Toast.LENGTH_SHORT).show();


                }
            }) {

            };
            stringRequest.setShouldCache(false);

            requestQueue.add(stringRequest);


        }


    }

    public void updatevehicle() {


        if (brandname.getText().toString().equalsIgnoreCase("")) {
            brandname.setError("*Required");
        } else if (model.getText().toString().equalsIgnoreCase("")) {
            model.setError("*Required");

        } else if (manufracture.getText().toString().equalsIgnoreCase("")) {
            manufracture.setError("*Required");

        } else if (numberplate.getText().toString().equalsIgnoreCase("")) {
            numberplate.setError("*Required");

        } else if (color.getText().toString().equalsIgnoreCase("")) {
            color.setError("*Required");

        } else {
            String url = baseurl + "driver_vehicle_update.php";


            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(Regitervehicle.this);

            Map<String, String> params = new HashMap();
            params.put("vehicle_id", id);
            params.put("brand_name", brandname.getText().toString());
            params.put("model", model.getText().toString());
            params.put("manufacturer", manufracture.getText().toString());
            params.put("vehicle_number", numberplate.getText().toString());
            params.put("vehicle_color", color.getText().toString());
            params.put("rc_upload", rcbookimage);
            params.put("insurance_pp", insurangeimage);


            JSONObject parameters = new JSONObject(params);


            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    // Toast.makeText(HotelMain.this, ""+response, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject obj = new JSONObject(String.valueOf(response));

                        String r_code = obj.getString("status");
                        Toast.makeText(Regitervehicle.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();

                        if (r_code.equalsIgnoreCase("1")) {

                            startActivity(new Intent(getApplicationContext(), HotelMain.class));
                            finish();

                        }

                    } catch (Exception e) {


                        e.printStackTrace();
                        Toast.makeText(Regitervehicle.this, "" + e, Toast.LENGTH_SHORT).show();


                    }
                    // Toast.makeText(Login.this, ""+userid, Toast.LENGTH_SHORT).show();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    Toast.makeText(Regitervehicle.this, "" + error, Toast.LENGTH_SHORT).show();


                }
            }) {

            };
            stringRequest.setShouldCache(false);

            requestQueue.add(stringRequest);


        }


    }

    private void openbottomsheet(final int upload, final int take) {

        final AlertDialog alertDialog = new AlertDialog.Builder(Regitervehicle.this).create();
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
                    rcbookimage = ConvertBitmapToString(resizedBitmap);
                    tick.setVisibility(View.VISIBLE);

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
                    insurangeimage = ConvertBitmapToString(resizedBitmap);
                    tick2.setVisibility(View.VISIBLE);


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
                rcbookimage = ConvertBitmapToString(resizedBitmap);
                tick.setVisibility(View.VISIBLE);


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
                insurangeimage = ConvertBitmapToString(resizedBitmap);
                tick2.setVisibility(View.VISIBLE);


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