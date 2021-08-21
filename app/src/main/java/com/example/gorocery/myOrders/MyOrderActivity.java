package com.example.gorocery.myOrders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.gorocery.R;
import com.example.gorocery.SharedPrefManager;
import com.example.gorocery.URLs;
import com.example.gorocery.Utility;
import com.example.gorocery.VolleySingleton;
import com.example.gorocery.activities.SignInActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyOrderActivity extends AppCompatActivity {

    Toolbar mToolbar;
    LinearLayout linearLogin;
    RecyclerView rvMyOrders;
    ArrayList<CustomerOrderFields> mList;
    CustomerOrderFields customerOrderFields;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        Objects.requireNonNull(getSupportActionBar()).hide();

        init();

        //Loading Orders
        loadOrders();

    }

    //Initialization
    public void init(){


        mToolbar = findViewById(R.id.toolbar_my_order);
        linearLogin = findViewById(R.id.linear_login);
        rvMyOrders = findViewById(R.id.rv_my_order);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    public void loadOrders(){


        final ProgressDialog dialog = Utility.show(MyOrderActivity.this, "", "Loading . . ");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_CUSTOMER_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    JSONArray message = jsonObject.getJSONArray("message");

//                    String message = jsonObject.getString("message");
                    if (status.equals("true")) {
                        mList = new ArrayList<>();
                        for (int i=0; i<message.length();i++){
                            JSONObject order=message.getJSONObject(i);

                            customerOrderFields = new CustomerOrderFields();
                            String order_id=order.getString("order_id");
                            String customer_id=order.getString("customer_id");
                            String store_id=order.getString("store_id");
                            String user_id=order.getString("user_id");
                            String date=order.getString("date");
                            Integer total_amount=order.getInt("total_amount");
                            String orders=order.getString("order");
                            String details=order.getString("details");
                            String total_discount=order.getString("total_discount");
                            String order_discount=order.getString("order_discount");
                            String service_charge=order.getString("service_charge");
                            String paid_amount=order.getString("paid_amount");
                            String due_amount=order.getString("due_amount");
                            String file_path=order.getString("file_path");
                            Integer statuss=order.getInt("status");
                            String order_details_id=order.getString("order_details_id");
                            String product_id=order.getString("product_id");
                            String variant_id=order.getString("variant_id");
                            String quantity=order.getString("quantity");
                            String rate=order.getString("rate");
                            String supplier_rate=order.getString("supplier_rate");
                            String total_price=order.getString("total_price");
                            String discount=order.getString("discount");
                            String latitude=order.getString("latitude");
                            String longitude=order.getString("longitude");
                            String address=order.getString("address");


                            customerOrderFields.setOrderId(order_id);
                            customerOrderFields.setDate(date);
                            customerOrderFields.setTotalAmount(total_amount);
                            customerOrderFields.setStatus(statuss);

                            mList.add(customerOrderFields);

                           /* mList.add(new CustomerOrderFields(order_id,
                                    customer_id,
                                    store_id,user_id,date,Integer.parseInt(total_amount),orders,details,Integer.valueOf(total_discount),Integer.valueOf(order_discount),Integer.valueOf(service_charge),Integer.valueOf(paid_amount),Integer.valueOf(due_amount),file_path,Integer.valueOf(statuss),order_details_id,product_id,variant_id,Integer.valueOf(quantity),Integer.valueOf(rate),Integer.valueOf(supplier_rate),Integer.valueOf(total_price),Integer.valueOf(discount),latitude,longitude,address));
                          */ /* mList.add(new CustomerOrderFields(
                                    order.getString("order_id"),
                                    order.getString("customer_id"),
                                    order.getString("store_id"),
                                    order.getString("user_id"),
                                    order.getString("date"),
                                    order.getInt("total_amount"),
                                    order.getString("order"),
                                    order.getString("details"),
                                    order.getInt("total_discount"),
                                    order.getInt("order_discount"),
                                    order.getInt("service_charge"),
                                    order.getInt("paid_amount"),
                                    order.getInt("due_amount"),
                                    order.get("file_path"),
                                    order.getInt("status"),
                                    order.getString("order_details_id"),
                                    order.getString("product_id"),
                                    order.getString("variant_id"),
                                    order.getInt("quantity"),
                                    order.getInt("rate"),
                                    order.getInt("supplier_rate"),
                                    order.getInt("total_price"),
                                    order.getInt("discount"),
                                    order.get("latitude"),
                                    order.get("longitude"),
                                    order.getString("address")

                                    ));*/

                        }

                        /*rvMyOrders.setHasFixedSize(true);*/
//                        rvMyOrders.setItemAnimator(new DefaultItemAnimator());
                        rvMyOrders.setAdapter(new MyOrderAdapter(mList,MyOrderActivity.this));
                        rvMyOrders.setLayoutManager(new LinearLayoutManager(MyOrderActivity.this));


                    } else {
                        Toast.makeText(MyOrderActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MyOrderActivity.this, "Error", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                //Utility.showErrorPopup(RegisterActivity.this, "Some Connection Error", HomeScreen.class, "no");
                Toast.makeText(MyOrderActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.wtf("error", error.toString());
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("api_token", SharedPrefManager.getInstance(MyOrderActivity.this).getUser().getApiToken());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

   /* @Override
    protected void onStart() {
        super.onStart();
        //if the mUser is not logged in
        //starting the login activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            linearLogin.setVisibility(View.GONE);

        }else {
            linearLogin.setVisibility(View.VISIBLE);
        }
    }*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void startShopping(View view) {

        onBackPressed();
    }

    public void doLogin(View view) {

        finish();
        startActivity(new Intent(this, SignInActivity.class));

    }
}
