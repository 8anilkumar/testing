package com.eminence.drive13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eminence.drive13.utils.SimpleToast;
import com.eminence.drive13.utils.YourPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.eminence.drive13.utils.Baseurl.baseurl;

public class OTPScreen extends AppCompatActivity {
    EditText ed1, ed2, ed3, ed4;
    String number,otp,name;
    String token;
    boolean doubleBackToExitPressedOnce = false;
    ProgressBar mainProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_screen);

        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        ed3 = findViewById(R.id.ed3);
        ed4 = findViewById(R.id.ed4);

        mainProgressBar = findViewById(R.id.mainProgressBar);

        signUpUrl();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            token = task.getResult().getToken();
                        } else {
                            token = "";
                        }
                    }
                });


        number = getIntent().getExtras().getString("number");
        name = getIntent().getExtras().getString("name");

        ed1.addTextChangedListener(new GenericTextWatcher(ed2, ed1));
        ed2.addTextChangedListener(new GenericTextWatcher(ed3, ed1));
        ed3.addTextChangedListener(new GenericTextWatcher(ed4, ed2));
        ed4.addTextChangedListener(new GenericTextWatcher(ed4, ed3));

        ed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                ed1.setBackground(ContextCompat.getDrawable(OTPScreen.this,R.drawable.blackcircle));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                ed2.setBackground(ContextCompat.getDrawable(OTPScreen.this,R.drawable.blackcircle));


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                ed3.setBackground(ContextCompat.getDrawable(OTPScreen.this,R.drawable.blackcircle));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                ed4.setBackground(ContextCompat.getDrawable(OTPScreen.this,R.drawable.blackcircle));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    public void Otpverify() {
        mainProgressBar.setVisibility(View.VISIBLE);
        String url = baseurl + "driver_otp_verify.php";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(OTPScreen.this);
        //  String username = sharedPreferences.getString("username", null);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(OTPScreen.this);
        Map<String, String> params = new HashMap();
        params.put("name", name);
        params.put("mobile",number);
        params.put("otp",otp);
        params.put("device_id",token);


        JSONObject parameters = new JSONObject(params);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Toast.makeText(HotelMain.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    String r_code = obj.getString("status");
                    Toast.makeText(OTPScreen.this, ""+obj.getString("message"), Toast.LENGTH_SHORT).show();

                    if (r_code.equalsIgnoreCase("1")) {

                        String name=obj.getString("name");
                        String id=obj.getString("id");
                        String email=obj.getString("email");
                        String mobile=obj.getString("mobile");
                        String address=obj.getString("address");

                        YourPreference yourPrefrence = YourPreference.getInstance(getApplicationContext());
                        yourPrefrence.saveData("name",name);
                        yourPrefrence.saveData("id",id);
                        yourPrefrence.saveData("email",email);
                        yourPrefrence.saveData("mobile",mobile);
                        yourPrefrence.saveData("address",address);

                        Intent intent=new Intent(OTPScreen.this,HotelMain.class);
                        startActivity(intent);

                        finish();

                    } else {


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

    public void Resend() {

        String url = baseurl + "driver_resend_otp.php";
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(OTPScreen.this);

        //  String username = sharedPreferences.getString("username", null);
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(OTPScreen.this);

        Map<String, String> params = new HashMap();
         params.put("mobile",number);


        JSONObject parameters = new JSONObject(params);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Toast.makeText(HotelMain.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                     Toast.makeText(OTPScreen.this, ""+obj.getString("message"), Toast.LENGTH_SHORT).show();


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
        requestQueue.add(stringRequest);


    }

    public void verify(View view) {

        otp=ed1.getText().toString()+ed2.getText().toString()+ed3.getText().toString()+ed4.getText().toString();
        if (otp.length()!=4)
        {
            Toast.makeText(this, "Required 4 Digit OTP", Toast.LENGTH_SHORT).show();
        }else
        {


        }
    }

    public void Resend(View view) {
        Resend();
    }

    public class GenericTextWatcher implements TextWatcher {
        private EditText etPrev;
        private EditText etNext;

        public GenericTextWatcher(EditText etNext, EditText etPrev) {
            this.etPrev = etPrev;
            this.etNext = etNext;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if (text.length() == 1)
                etNext.requestFocus();
            else if (text.length() == 0)
                etPrev.requestFocus();
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }
    }

    private void signUpUrl() {

        if (token != null && !token.equalsIgnoreCase("")) {
            Otpverify();
        } else {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                token = task.getResult().getToken();
                                OTPScreen.this.signUpUrl();
                            } else {
                                token = "";
                            }
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            finishAffinity();
            return;
        }


        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to Close Drive13", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}