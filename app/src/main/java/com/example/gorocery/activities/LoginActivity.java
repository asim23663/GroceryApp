package com.example.gorocery.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gorocery.MainActivity;
import com.example.gorocery.R;
import com.example.gorocery.SharedPrefManager;
import com.example.gorocery.URLs;
import com.example.gorocery.Utility;
import com.example.gorocery.VolleySingleton;
import com.example.gorocery.model.UserFields;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {


    private EditText etEmail,etPassword;
    private TextView tvError;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();

        etEmail=findViewById(R.id.et_login_email);
        etPassword=findViewById(R.id.et_login_pass);
        tvError=findViewById(R.id.tv_error_login_pass);
    }

    public void doLogin(View view) {

        if (TextUtils.isEmpty(etEmail.getText().toString())){
            etEmail.setError("Email is required");
        }else if (!(Utility.isEmailValid(etEmail.getText().toString()))){
            etEmail.setError("Email is not valid");
        }else if (TextUtils.isEmpty(etPassword.getText().toString())){
            tvError.setVisibility(View.VISIBLE);
            //etPassword.setError("Password is required");
        }else{
            signIn();
        }
    }

    private void signIn() {
        tvError.setVisibility(View.GONE);
        final ProgressDialog dialog = Utility.show(LoginActivity.this, "Login", "Loading . . ");

//        String url="http://aktechprojects.com/grocery/app/public/api/cuslogin?customer_email=asm@gmail.com&password=123456";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if (status.equals("true")) {

                        JSONObject userObject = jsonObject.getJSONObject("data");
                        SharedPrefManager.getInstance(getApplicationContext()).saveUserData(new UserFields(
                                userObject.getString("customer_name"),
                                userObject.getString("customer_email"),
                                userObject.getString("customer_short_address"),
                                userObject.getString("password"),
                                userObject.getString("location"),
                                userObject.getString("customer_mobile"),
                                userObject.getString("gender"),
                                userObject.getString("date_of_birth"),
                                userObject.getString("api_token")));

                        //Toast.makeText(LoginActivity.this, "Login Successfully!\n name: "+userObject.getString("customer_name"), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials!!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Exception", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                //Utility.showErrorPopup(RegisterActivity.this, "Some Connection Error", HomeScreen.class, "no");
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.wtf("error", error.toString());
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("customer_email", etEmail.getText().toString().trim());
                params.put("password", etPassword.getText().toString().trim());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void doSignUp(View view) {

        startActivity(new Intent(this,RegistrationActivity.class));
        finish();
    }
}
