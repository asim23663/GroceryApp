package com.example.gorocery.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText etName,etEmail,etMobile,etDob,etGender,etLocation,etAddress;
    private TextView tvEditProfile,tvUpdateProfile;
    private Toolbar toolbar;
    Calendar mCalender;
    UserFields mUser;
    String genderKey="";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Objects.requireNonNull(getSupportActionBar()).hide();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mCalender = Calendar.getInstance();

        //starting the login activity
        if (!(SharedPrefManager.getInstance(this).isLoggedIn())) {
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }else {
            init();
        }







        /*//setting the values to the textviews
        textViewId.setText(String.valueOf(mUser.get_id()));
        textViewUsername.setText(mUser.getUsername());
        textViewEmail.setText(mUser.getEmail());
        textViewGender.setText(mUser.getGender());*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        //if the mUser is not logged in



    }
    public void init(){
        etName=findViewById(R.id.et_profile_name);
        etEmail=findViewById(R.id.et_profile_email);
        etMobile=findViewById(R.id.et_profile_mobile);
        etDob=findViewById(R.id.et_profile_dob);
        etGender=findViewById(R.id.et_profile_gender);
        etLocation=findViewById(R.id.et_profile_location);
        etAddress=findViewById(R.id.et_profile_address);
        tvEditProfile=findViewById(R.id.tv_edit_profile);
        tvUpdateProfile=findViewById(R.id.tv_update_profile);

        tvEditProfile.setOnClickListener(this);
        tvUpdateProfile.setOnClickListener(this);

        etLocation.setOnClickListener(this);
        etDob.setOnClickListener(this);
        etGender.setOnClickListener(this);
        toolbar=findViewById(R.id.toolbar_profile);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //getting the current mUser
        mUser = SharedPrefManager.getInstance(this).getUser();

        etName.setText(mUser.getName());
        etEmail.setText(mUser.getEmail());
        etMobile.setText(mUser.getMobile());
        etDob.setText(mUser.getDob());
        if (mUser.getGender().equals("0")){
            etGender.setText("Male");
        }else if (mUser.getGender().equals("1")){
            etGender.setText("Female");
        }

        etLocation.setText(mUser.getLocation());
        etAddress.setText(mUser.getAddress());

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_edit_profile:
                enableEditing();
                break;
            case R.id.tv_update_profile:
                updateProfile();
                break;
            case R.id.et_profile_gender:
                chooseGender();
                break;
            case R.id.et_profile_location:
                locationOnMap();
                break;
            case R.id.et_profile_dob:
                chooseDOB();
                break;


        }

    }


    //Updating mUser Profile

    public void updateProfile(){

        final ProgressDialog dialog = Utility.show(ProfileActivity.this, "Updating", "Loading . . ");

        if (etGender.getText().toString().equals("Male")){
            genderKey="0";
        }else if(etGender.getText().toString().equals("Female")){
            genderKey="1";
        }
        String url="http://aktechprojects.com/grocery/app/public/api/updateProfile?first_name=test&gender=1&date_of_birth=2-5-2019&location=testlocation&address=Lahore&api_token=S3RcXLwJgGq9jfGR";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if (status.equals("true")) {

                        JSONObject userObject = jsonObject.getJSONObject("data");


                        SharedPrefManager
                                .getInstance(getApplicationContext())
                                .saveUserData(new UserFields(
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
                        Toast.makeText(ProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(ProfileActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileActivity.this, "Exception", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                //Utility.showErrorPopup(RegisterActivity.this, "Some Connection Error", HomeScreen.class, "no");
                Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.wtf("error", error.toString());
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", etName.getText().toString().trim());
                params.put("gender", genderKey);
                params.put("date_of_birth", etDob.getText().toString().trim());
                params.put("location", etLocation.getText().toString().trim());
                params.put("mobile", etMobile.getText().toString().trim());
                params.put("address", etAddress.getText().toString().trim());
                params.put("api_token",mUser.getApiToken() );
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }
    //Choose Location from Map
    public void locationOnMap(){
        Intent intent = new Intent(ProfileActivity.this.getApplicationContext(), LocationOnMapActivity.class);
        startActivityForResult(intent, 2404);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 2404) {
            if(data != null) {
                String location = data.getStringExtra("location");
                etLocation.setText(location);
            }
        }
    }

    //Open Calender for choosing DOB
    public void chooseDOB(){

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                mCalender.set(Calendar.YEAR, year);
                mCalender.set(Calendar.MONTH, monthOfYear);
                mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        new DatePickerDialog(ProfileActivity.this, date, mCalender
                .get(Calendar.YEAR), mCalender.get(Calendar.MONTH),
                mCalender.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDob.setText(sdf.format(mCalender.getTime()));
    }


    //Open Dialog for choosing gender
    public void chooseGender(){

        final String[] gender = {"Male", "Female"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose gender");
        builder.setItems(gender, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the mUser clicked on colors[which]
                etGender.setText(gender[which]);
            }
        });
        builder.show();
    }

    public void enableEditing(){
        tvEditProfile.setVisibility(View.GONE);
        tvUpdateProfile.setVisibility(View.VISIBLE);
        etName.setEnabled(true);
        etDob.setEnabled(true);
        etGender.setEnabled(true);
        etLocation.setEnabled(true);
        etAddress.setEnabled(true);
    }
    public void disableEditing(){
        tvUpdateProfile.setVisibility(View.GONE);
        tvEditProfile.setVisibility(View.VISIBLE);
        etName.setEnabled(false);
        etDob.setEnabled(false);
        etGender.setEnabled(false);
        etLocation.setEnabled(false);
        etAddress.setEnabled(false);
    }
}
