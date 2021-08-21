package com.example.gorocery.ShoppingCart;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.renderscript.Short3;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
import com.example.gorocery.ShoppingCart.shoppingCartDb.CartDbClient;
import com.example.gorocery.URLs;
import com.example.gorocery.Utility;
import com.example.gorocery.VolleySingleton;
import com.example.gorocery.activities.LocationOnMapActivity;
import com.example.gorocery.activities.ProfileActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ShoppingCartActivity extends AppCompatActivity {


    private Toolbar mToolbar;

    private static RecyclerView mRv;

    public static LinearLayout mLinearCheckout, mLinearStartShopping;

    public static ScrollView mScrollViewRecipt;
    public static TextView tvYourBill, tvYourBillLine;
    public static ImageView imgRadioYourBill, imgRadioPlaceOrder, imgRadioCompleted;

    private TextView tvAddress;
    private TextView tvTotalCompletedCOst, tvEstimatedTime, tvCompeletedDeliveryAddress;

    public ShoppingCartFields shoppingCartFields;
    private TextView tvPlaceOrderAmount;
    private static LinearLayout linearLayoutTotalAmount;

    public static TextView tvSubtotal, tvDeliveryCharges, tvTotalAmount;

    static ArrayList<ShoppingCartFields> mCartList;

    //public static int subTotal = 0;

    private TextInputEditText etInstruction;

    public static ProgressBar pbar;

    public static Handler mHandler;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        //SHOPPING_CART_LIST=new ArrayList<>();
        mHandler = new Handler();
        init();

        try {

            loadItemInShoppingCart();

        } catch (Exception e) {
            //mLinearStartShopping.setVisibility(View.VISIBLE);
            // mLinearCheckout.setVisibility(View.GONE);
            //mLinearRecipt.setVisibility(View.GONE);
        }

    }

    /*Initialization*/
    public void init() {

        Objects.requireNonNull(getSupportActionBar()).hide();
        mToolbar = findViewById(R.id.toolbar_shopping_cart);

        mScrollViewRecipt = findViewById(R.id.scrol_view_recipt);
        mLinearCheckout = findViewById(R.id.linear_check_out);
        mLinearStartShopping = findViewById(R.id.linear_start_shoping);
        tvSubtotal = findViewById(R.id.tv_sub_total);
        tvDeliveryCharges = findViewById(R.id.tv_delivery_charges);
        tvTotalAmount = findViewById(R.id.tv_total_amount);
        imgRadioPlaceOrder = findViewById(R.id.img_radio_place_order);
        tvAddress = findViewById(R.id.tv_shopping_address);
        imgRadioCompleted = findViewById(R.id.img_radio_completed);
        etInstruction = findViewById(R.id.et_instruction);
        tvPlaceOrderAmount = findViewById(R.id.tv_place_order_amount);
        linearLayoutTotalAmount = findViewById(R.id.linear_total_amount);

        tvTotalCompletedCOst = findViewById(R.id.tv_completed_total_cost);
        tvEstimatedTime = findViewById(R.id.tv_estimated_time);
        tvCompeletedDeliveryAddress = findViewById(R.id.tv_completed_address);


        tvYourBillLine = findViewById(R.id.tv_your_bill_line);
        imgRadioYourBill = findViewById(R.id.img_radio_your_bill);
        tvYourBill = findViewById(R.id.tv_your_bill);
        pbar = findViewById(R.id.pb_price_loading);

        mRv = findViewById(R.id.rv_shopping_cart);
//        mCartList=new ArrayList<>();


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    //Refresh Price
    public static void refreshPrice() {

        // Final so we can access it from the other thread
        pbar.setVisibility(View.VISIBLE);
        linearLayoutTotalAmount.setVisibility(View.GONE);
        // Create a Handler instance on the main thread
        int subTotal = 0;

        for (int i = 0; i < mCartList.size(); i++) {

//            int onTypePrice = (Integer.parseInt(mCartList.get(i).getP_price()) * mCartList.get(i).getP_qty());
            subTotal = subTotal + 100;
            //int grandTotal=subTotal+(Integer.valueOf(tvDeliveryCharges.getText().toString()));

        }

        // Create and start a new Thread
        final int finalSubTotal = subTotal;
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                } // Just catch the InterruptedException

                // Now we use the Handler to post back to the main thread
                mHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    public void run() {


                        // Set the View's visibility back on the main UI Thread
                        pbar.setVisibility(View.GONE);
                        linearLayoutTotalAmount.setVisibility(View.VISIBLE);
                        int price = finalSubTotal + Integer.valueOf(tvDeliveryCharges.getText().toString());
                        tvSubtotal.setText(finalSubTotal + "");
                        tvTotalAmount.setText(price + "");
                    }
                });
            }
        }).start();
    }


    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    public void loadItemInShoppingCart() {


        final ProgressDialog dialog = Utility.show(ShoppingCartActivity.this, "", "Loading . . ");

        dialog.show();

        new Thread(new Runnable() {

            @Override
            public void run() {
                mCartList = (ArrayList<ShoppingCartFields>) CartDbClient
                        .getInstance(ShoppingCartActivity.this)
                        .getCartDb()
                        .shoppingCartDAO()
                        .getAll();

            }
        }).start();

        // Create and start a new Thread
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                } // Just catch the InterruptedException

                // Now we use the Handler to post back to the main thread
                mHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    public void run() {
                        dialog.dismiss();

                        if (mCartList.size() == 0) {

                            mLinearStartShopping.setVisibility(View.VISIBLE);
                            mScrollViewRecipt.setVisibility(View.GONE);
                            mLinearCheckout.setVisibility(View.GONE);

                        } else {


                            mLinearStartShopping.setVisibility(View.GONE);
                            tvYourBill.setTextColor(R.color.colorPrimary);
                            mLinearCheckout.setVisibility(View.VISIBLE);
                            imgRadioYourBill.setImageResource(R.drawable.ic_radio_button_checked);
                            mScrollViewRecipt.setVisibility(View.VISIBLE);

                            mRv.setHasFixedSize(true);
                            mRv.setLayoutManager(new LinearLayoutManager(ShoppingCartActivity.this));
                            mRv.setAdapter(new ShoppingCartAdapter(ShoppingCartActivity.this, mCartList));
                        }
                    }
                });
            }
        }).start();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //Start Shopping
    public void startShopping(View view) {

        onBackPressed();
        //Toast.makeText(this, "Clicked!!", Toast.LENGTH_SHORT).show();

    }


    //CheckOut
    @SuppressLint("SetTextI18n")
    public void checkOut(View view) {

        final ProgressDialog dialog = Utility.show(ShoppingCartActivity.this, "", "Please Wait . . ");

        dialog.show();


        // Create and start a new Thread
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                } // Just catch the InterruptedException

                // Now we use the Handler to post back to the main thread
                mHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    public void run() {
                        dialog.dismiss();

                        tvAddress.setText(SharedPrefManager.getInstance(ShoppingCartActivity.this).getUser().getLocation());
                        findViewById(R.id.linear_place_order).setVisibility(View.VISIBLE);
                        findViewById(R.id.scroll_view_place_order).setVisibility(View.VISIBLE);
                        imgRadioPlaceOrder.setImageResource(R.drawable.ic_radio_button_checked);

                        tvPlaceOrderAmount.setText(tvTotalAmount.getText().toString());

                        mScrollViewRecipt.setVisibility(View.GONE);
                        mLinearCheckout.setVisibility(View.GONE);


                    }
                });
            }
        }).start();


//        Toast.makeText(this, "pId"+ProductsActivity.CART_LIST.get(0).getP_name(), Toast.LENGTH_SHORT).show();

    }

    //Place the order
    public void placeOrder(View view) {

        final ProgressDialog dialog = Utility.show(ShoppingCartActivity.this, "", "Please wait . . ");

        if (TextUtils.isEmpty(Objects.requireNonNull(etInstruction.getText()).toString().trim())) {

            etInstruction.setText("No instruction");
        }
        dialog.show();


        for (final ShoppingCartFields list : mCartList) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_PLACE_ORDER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String status = jsonObject.getString("status");
                                String message = jsonObject.getString("message");
                                if (status.equals("true")) {

                                    Toast.makeText(ShoppingCartActivity.this, "Order Place Successfully!", Toast.LENGTH_LONG).show();
//
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            CartDbClient.getInstance(ShoppingCartActivity.this).getCartDb()
                                                    .shoppingCartDAO()
                                                    .emptyCart();
                                        }
                                    }).start();


                                    imgRadioCompleted.setImageResource(R.drawable.ic_radio_button_checked);
                                    findViewById(R.id.scroll_view_place_order).setVisibility(View.GONE);
                                    findViewById(R.id.linear_place_order).setVisibility(View.GONE);
                                    findViewById(R.id.linear_completed).setVisibility(View.VISIBLE);
                                    findViewById(R.id.btn_done_completed).setVisibility(View.VISIBLE);
                                    tvTotalCompletedCOst.setText(tvTotalAmount.getText());
                                    tvEstimatedTime.setText("01 Hour");
                                    tvCompeletedDeliveryAddress.setText(tvAddress.getText());

                                } else {
                                    Toast.makeText(ShoppingCartActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(ShoppingCartActivity.this, "User is Already Exist", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ShoppingCartActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("customer_api_token", SharedPrefManager.getInstance(ShoppingCartActivity.this).getUser().getApiToken());
                    params.put("total_amount", tvTotalAmount.getText().toString());
                    params.put("order", String.valueOf(mCartList.size()));
                    params.put("details", etInstruction.getText().toString());
                    params.put("total_discount", "0");
                    params.put("order_discount", "0");
                    params.put("service_charge", "0");
                    params.put("paid_amount", "0");
                    params.put("due_amount", "0");
                    params.put("product_id", list.getP_id());
                    params.put("quantity", String.valueOf(list.getP_qty()));
                    params.put("rate", list.getP_price());
                    params.put("total_price", tvTotalAmount.getText().toString());
                    params.put("discount", "0");
                    params.put("latitude", "");
                    params.put("longitude", "");
                    params.put("address", tvAddress.getText().toString());
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

        }


    }


    //Edit Delivery Address
    public void editDeliveryAddress(View view) {

        Intent intent = new Intent(ShoppingCartActivity.this.getApplicationContext(), LocationOnMapActivity.class);
        startActivityForResult(intent, 12);

//        startActivity(new Intent(this, LocationOnMapActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 12) {
            if (data != null) {
                String location = data.getStringExtra("location");
                tvAddress.setText(location);
            }
        }
    }

    //After placing Order
    public void done(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
