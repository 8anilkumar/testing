package com.eminence.drive13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eminence.drive13.Adapter.ServiceAdapter;
import com.eminence.drive13.model.ServiceModel;
import com.eminence.drive13.utils.YourPreference;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static com.eminence.drive13.utils.Baseurl.baseurl;

public class AddTransaction extends AppCompatActivity {
    RelativeLayout  choosevalue,addTransaction;
    EditText location, name, number, amount,edit_city,edit_highway;
    Button uploadphoto,uploadselfi;
    TextView billphoto, choosetext, s_name;
    String  servicenametext = "";
    String serviceidtext = "";
    String transactionfor = "";
    SpinnerDialog spinnerDialog, spinnerDialog2, spinnerDialog3;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> servicename = new ArrayList<>();
    ArrayList<String> locationlist = new ArrayList<>();
    ArrayList<String> serviceid = new ArrayList<>();
    String cityname, convertedimage = "", selfi = "";
    LinearLayout choosetransaction,petrolPumpLayout,billNotProvideLayout,uploadPhotoLayout;
    RadioButton rb_hotelAndRestorante,rb_petrolPump;
    CheckBox cb_billNotProvide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        choosetransaction = findViewById(R.id.hotelAndRestoranteLayout);
        petrolPumpLayout = findViewById(R.id.petrolPumpLayout);
        rb_hotelAndRestorante = findViewById(R.id.rb_hotelAndRestorante);
        rb_petrolPump = findViewById(R.id.rb_petrolPump);
        cb_billNotProvide = findViewById(R.id.cb_billNotProvide);
        billNotProvideLayout = findViewById(R.id.billNotProvideLayout);
        uploadPhotoLayout = findViewById(R.id.uploadPhotoLayout);
        addTransaction = findViewById(R.id.addTransaction);
        choosevalue = findViewById(R.id.choosevalue);
        location = findViewById(R.id.location);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        amount = findViewById(R.id.amount);
        uploadphoto = findViewById(R.id.uploadphoto);
        uploadselfi = findViewById(R.id.uploadselfi);
        billphoto = findViewById(R.id.billphoto);
        s_name = findViewById(R.id.s_name);
        choosetext = findViewById(R.id.choosetext);
        edit_city = findViewById(R.id.edit_city);
        edit_highway = findViewById(R.id.edit_highway);

        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        cityname = yourPrefrence.getData("cityname");

        list.add("Hotel");
        list.add("Workshop");
        list.add("Insurance");

       // categorylist("Hotel");
        s_name.setText("Hotel or Workshop Name");
        transactionfor = "Hotel";

        cb_billNotProvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_billNotProvide.isChecked()){
                    billNotProvideLayout.setVisibility(View.VISIBLE);
                    uploadPhotoLayout.setVisibility(View.GONE);
                    convertedimage="";
                } else {
                    billNotProvideLayout.setVisibility(View.GONE);
                    uploadPhotoLayout.setVisibility(View.VISIBLE);
                    selfi="";
                }
            }
        });

        choosetransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb_hotelAndRestorante.setChecked(true);
                rb_petrolPump.setChecked(false);
                categorylist("Hotel");
                s_name.setText("Hotel or Workshop Name");
                transactionfor = "Hotel";
            }
        });


        petrolPumpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rb_hotelAndRestorante.setChecked(false);
                rb_petrolPump.setChecked(true);
                categorylist("Petrol Pump");
                s_name.setText("Petrol Pump");
                transactionfor = "Petrol Pump";

            }
        });


        rb_hotelAndRestorante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_hotelAndRestorante.setChecked(true);
                rb_petrolPump.setChecked(false);
                categorylist("Hotel");
                s_name.setText("Hotel or Workshop Name");
                transactionfor = "Hotel";

            }
        });

        rb_petrolPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_hotelAndRestorante.setChecked(false);
                rb_petrolPump.setChecked(true);
                categorylist("Petrol Pump");
                s_name.setText("Petrol Pump");
                transactionfor = "Petrol Pump";
              //  spinnerDialog.showSpinerDialog();
            }
        });


        spinnerDialog = new SpinnerDialog(AddTransaction.this,
                list,
                "Choose Transaction For",
                R.style.DialogAnimations_SmileWindow,
                "Close  ");// With 	Animation
                 spinnerDialog.setCancellable(true); // for cancellable
                 spinnerDialog.setShowKeyboard(false);// for open keyboard by default

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
               // transactionfor = item;
                choosetext.setText(item);

            }
        });

        choosevalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog2.showSpinerDialog();
            }
        });


        uploadphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbottomsheet("bill");
            }
        });

        uploadselfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openbottomsheet("selfi");
            }
        });


        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtransaction();
            }
        });

    }



    public void addtransaction() {

        if (transactionfor.equalsIgnoreCase("")) {
            Toast.makeText(this, "Select Transaction For", Toast.LENGTH_SHORT).show();

        } else if (serviceidtext.equalsIgnoreCase("")) {
            Toast.makeText(this, "Select Service ", Toast.LENGTH_SHORT).show();

        } else if (edit_city.getText().toString().equalsIgnoreCase("")) {
            edit_city.setError("*Required");

        }else if (location.getText().toString().equalsIgnoreCase("")) {
            location.setError("*Required");

        } else if (name.getText().toString().equalsIgnoreCase("")) {
            name.setError("*Required");

        } else if (number.getText().toString().equalsIgnoreCase("")) {
            number.setError("*Required");

        } else if (amount.getText().toString().equalsIgnoreCase("")) {
            amount.setError("*Required");

        }

        else if (convertedimage.equalsIgnoreCase("") && selfi.equalsIgnoreCase("")) {
            Toast.makeText(this, "Upload Bill Photo or Selfi", Toast.LENGTH_SHORT).show();
        }

        else {
            String url = baseurl + "driver_transaction.php";
            YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
            String id = yourPrefrence.getData("id");
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(AddTransaction.this);

            Map<String, String> params = new HashMap();
            params.put("driver_id", id);
            params.put("transaction_for", transactionfor);
            params.put("service_id", serviceidtext);
            params.put("city", edit_city.getText().toString());
            params.put("highway", edit_highway.getText().toString());
            params.put("location", location.getText().toString());
            params.put("representative_name", name.getText().toString());
            params.put("representative_mobile", number.getText().toString());
            params.put("billing_amount", amount.getText().toString());
            params.put("bill_photo", convertedimage);
            params.put("selfie", selfi);
            JSONObject parameters = new JSONObject(params);

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    // Toast.makeText(HotelMain.this, ""+response, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject obj = new JSONObject(String.valueOf(response));

                        String r_code = obj.getString("status");
                        Toast.makeText(AddTransaction.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();

                        if (r_code.equalsIgnoreCase("1")) {

                            startActivity(new Intent(getApplicationContext(), HotelMain.class));
                            finish();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AddTransaction.this, "" + e, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddTransaction.this, "" + error, Toast.LENGTH_SHORT).show();
                }
            }) {
            };
            stringRequest.setShouldCache(false);

            requestQueue.add(stringRequest);
        }
    }


    public void categorylist(final String hotel) {

        serviceid.clear();
        servicename.clear();
        String url = baseurl + "services_list_by_category.php";
        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
        String id = yourPrefrence.getData("id");
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(AddTransaction.this);

        Map<String, String> params = new HashMap();
        params.put("category", hotel);
        params.put("driver_id", id);
        params.put("city", cityname);
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
                            ServiceModel serviceModel = new ServiceModel();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String id = jsonObject2.getString("id");
                            String name = jsonObject2.getString("name");
                            String location = jsonObject2.getString("location");
                            servicename.add(name);
                            serviceid.add(id);
                            locationlist.add(location);
                        }

                        choosevalue.setVisibility(View.VISIBLE);
                        spinnerDialog2 = new SpinnerDialog(AddTransaction.this, servicename, "Choose " + hotel, R.style.DialogAnimations_SmileWindow, "Close  ");// With 	Animation
                        spinnerDialog2.setCancellable(true); // for cancellable
                        spinnerDialog2.setShowKeyboard(false);// for open keyboard by default
                        spinnerDialog2.bindOnSpinerListener(new OnSpinerItemClick() {
                            @Override
                            public void onClick(String item, int position) {
                                servicenametext = item;
                                s_name.setText(item);
                                serviceidtext = serviceid.get(position);
                                String locationn = locationlist.get(position);
                                location.setText(locationn);
                                location.setEnabled(false);
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AddTransaction.this, "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddTransaction.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        stringRequest.setShouldCache(false);

        requestQueue.add(stringRequest);


    }

    private void openbottomsheet(final String type) {

        final AlertDialog alertDialog = new AlertDialog.Builder(AddTransaction.this).create();
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
                    if (type.equalsIgnoreCase("bill")) {
                        startActivityForResult(intent, 95);
                    }
                    else
                    {
                        startActivityForResult(intent, 105);
                    }
                }

                alertDialog.dismiss();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (type.equalsIgnoreCase("bill")) {
                    startActivityForResult(intent, 0);
                }
                else
                {
                    startActivityForResult(intent, 100);
                }
               // startActivityForResult(intent, 0);
                alertDialog.dismiss();

            }
        });

        alertDialog.setView(convertView);
        alertDialog.show();


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
                    float aspectRatio = bitmap.getWidth() / (float) bitmap.getHeight();
                    int width = 280;
                    int height = Math.round(width / aspectRatio);

                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    convertedimage = ConvertBitmapToString(resizedBitmap);

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } if (data != null && requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Uri targetUri = data.getData();
                Bitmap bitmap;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                    float aspectRatio = bitmap.getWidth() / (float) bitmap.getHeight();
                    int width = 280;
                    int height = Math.round(width / aspectRatio);

                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    selfi = ConvertBitmapToString(resizedBitmap);

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
                convertedimage = ConvertBitmapToString(bitmap);
            }
        }  if (data != null && requestCode == 105) {
            if (resultCode == RESULT_OK) {
                // Uri targetUri = data.getData();
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                selfi = ConvertBitmapToString(bitmap);
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


}