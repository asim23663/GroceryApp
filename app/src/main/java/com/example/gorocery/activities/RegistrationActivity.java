package com.example.gorocery.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gorocery.R;
import com.example.gorocery.URLs;
import com.example.gorocery.Utility;
import com.example.gorocery.VolleySingleton;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {



    private LinearLayout etLocation;
    String mLocation = "null";
    PopupWindow popupWindow = null;
    String latitude = "", longitude = "";

    private TextView tvErrror,tvNotMatch;

    String phoneNumber ="null";
    private TextInputEditText etEmail,etDeliveryAddress,etPassword,etConfirmPassword,etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

       //Objects.requireNonNull(getSupportActionBar()).hide();

        popupWindow = new PopupWindow();
        etLocation = findViewById(R.id.etLocation);

        tvErrror=findViewById(R.id.tv_error);
        tvNotMatch=findViewById(R.id.tv_error_not_matching);

        phoneNumber=getIntent().getStringExtra("phoneNumber");

        etName=findViewById(R.id.et_name);
        etEmail=findViewById(R.id.et_email);
        etDeliveryAddress=findViewById(R.id.et_delivery_address);
        etPassword=findViewById(R.id.et_password);
        etConfirmPassword=findViewById(R.id.et_confirm_password);

        addLocation();

       /* etLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLocation();
            }
        });*/
    }

    //Add Location After Selecting
    public void addLocation(){


        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        }

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setCountry("PAK");

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                mLocation = "" + place.getName();
                latitude = "" + place.getLatLng().latitude;
                longitude = "" + place.getLatLng().longitude;

            }

            @Override
            public void onError(Status status) {

            }
        });
        popupWindow = new PopupWindow(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    public void doSignUp(View view) {


        if (TextUtils.isEmpty(etName.getText().toString())){
            etName.setError("Name is required");
        }
        else if (TextUtils.isEmpty(etEmail.getText().toString())){
            etEmail.setError("Email is required");
        }else if (!(Utility.isEmailValid(etEmail.getText().toString()))){
            etEmail.setError("Email is not valid");
        }else if (TextUtils.isEmpty(etDeliveryAddress.getText().toString())){
            etDeliveryAddress.setError("Delivery Address is required");
        }else if (TextUtils.isEmpty(etPassword.getText().toString())){
            tvErrror.setVisibility(View.VISIBLE);
            //etPassword.setError("Password is required");
        }else if(!(etPassword.getText().toString().equals(etConfirmPassword.getText().toString()))){
            tvErrror.setVisibility(View.GONE);
            tvNotMatch.setVisibility(View.VISIBLE);
            //etConfirmPassword.setError("Password is not matching");
        }else if (mLocation.equals("null")){
            tvNotMatch.setVisibility(View.GONE);
            Toast.makeText(this, "Kindly choose location", Toast.LENGTH_SHORT).show();
        }else {

            signUp();

        }


        //Toast.makeText(this, "Email: "+ email +"\n Lat: "+latitude, Toast.LENGTH_SHORT).show();
    }


    private void signUp() {

        tvNotMatch.setVisibility(View.GONE);
        //String url= URLs.URL_REGISTER +"first_name="+etName.getText().toString()+"&username="+etEmail.getText().toString()+"&password="+etPassword.getText().toString()+"&location="+mLocation+"&mobile="+"03124631816"+"&address="+etDeliveryAddress.getText().toString();

        //String url="http://aktechprojects.com/grocery/app/public/api/cusSignup?first_name=test&customer_email=asim@gmail.com&password=123456&adress=testadress&location=testlocation&mobile=0312-4637816";

        final ProgressDialog dialog = Utility.show(RegistrationActivity.this, "Registration", "Loading . . ");



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("true")) {

//                        JSONObject userObject = jsonObject.getJSONObject("mUser");
//                        SharedPrefManager.getInstance(getApplicationContext()).userProfile(userObject.getString("first_name"), userObject.getString("email"), userObject.getString("phone_number"), uPassword, userObject.getString("api_token"),userObject.getString("pb_id"));
                        Toast.makeText(RegistrationActivity.this, "Register Successfully!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegistrationActivity.this, "User is Already Exist", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                //Utility.showErrorPopup(RegisterActivity.this, "Some Connection Error", HomeScreen.class, "no");
                Toast.makeText(RegistrationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.wtf("error", error.toString());
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", etName.getText().toString().trim());
                params.put("gender", "");
                params.put("date_of_birth", "null");
                params.put("customer_email", etEmail.getText().toString().trim());
                params.put("password", etPassword.getText().toString().trim());
                params.put("location", mLocation);
                params.put("mobile", phoneNumber);
                params.put("address", etDeliveryAddress.getText().toString().trim());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }

    public void doLogin(View view) {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }


    /*JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    boolean status=response.getBoolean("status");
                    if (status){
                        Toast.makeText(RegistrationActivity.this, "True", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(RegistrationActivity.this, "False", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(RegistrationActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });*/
}
