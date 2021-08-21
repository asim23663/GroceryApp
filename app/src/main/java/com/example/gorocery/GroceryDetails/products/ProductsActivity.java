package com.example.gorocery.GroceryDetails.products;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gorocery.GroceryDetails.GrocerySubCatFields;
import com.example.gorocery.R;
import com.example.gorocery.ShoppingCart.ShoppingCartActivity;
import com.example.gorocery.ShoppingCart.ShoppingCartFields;
import com.example.gorocery.URLs;
import com.example.gorocery.Utility;
import com.example.gorocery.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProductsActivity extends AppCompatActivity {

    private String groceryName;
    private Toolbar mToolbar;
    private RecyclerView mRv;
    public static TextView mTvCartCounter;
    private ArrayList<ProductsFields> mList;

    public static ArrayList<ProductsFields> CART_LIST = null;

    public static ArrayList<ShoppingCartFields> SHOPPING_CART_LIST = null;
    public static GrocerySubCatFields grocerySubCatFields = null;
    private ImageView mImgCart;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_grocery);

        Objects.requireNonNull(getSupportActionBar()).hide();

        SHOPPING_CART_LIST = new ArrayList<>();

        CART_LIST = new ArrayList<>();


        mToolbar = findViewById(R.id.toolbar__grocery_sub);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTvCartCounter = findViewById(R.id.tv_cart_counter);

        mImgCart = findViewById(R.id.img_sub_cart);


        mRv = findViewById(R.id.rv_sub_grocery);
        mList = new ArrayList<>();
        mToolbar.setTitle(grocerySubCatFields.getcName());

        mList.add(new ProductsFields("1", "pId", "pSupplierId", "pCatId", "pName", "pPrice", "pSupplierPrice", "pUnit", "pModel", "pDetail",
                "imgThumb", "pBrandId", "varient", "pType", "pBestSale", "pOnSale", "pOnsalePrice", "pInvoiceDetails", "pImgLargeDetail", "pReview", "pDesc", "pTag", "pSpecs", "pStatus", "pCatName"));
        mRv.setAdapter(new ProductsAdapter(mList, ProductsActivity.this));
        mRv.setLayoutManager(new LinearLayoutManager(ProductsActivity.this));
//        loadProducts();


    }

    private void loadProducts() {

        final ProgressDialog dialog = Utility.show(ProductsActivity.this, "", "Loading . . ");

//        String url="http://aktechprojects.com/grocery/app/public/api/subCat?id=VH4CZIV5Y37CDEO";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_PRODUCTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if (status.equals("true")) {
                        JSONArray array = jsonObject.getJSONArray("data");

                        for (int i = 0; i <= array.length(); i++) {

                            JSONObject object = array.getJSONObject(i);
                            String id = object.getString("id");
                            String pId = object.getString("product_id");
                            String pSupplierId = object.getString("supplier_id");
                            String pCatId = object.getString("category_id");
                            String pName = object.getString("product_name");
                            String pPrice = object.getString("price");
                            String pSupplierPrice = object.getString("supplier_price");
                            String pUnit = object.getString("unit");
                            String pModel = object.getString("product_model");
                            String pDetail = object.getString("product_details");
                            String imgThumb = object.getString("image_thumb");
                            String pBrandId = object.getString("brand_id");
                            String varient = object.getString("variants");
                            String pType = object.getString("type");
                            String pBestSale = object.getString("best_sale");
                            String pOnSale = object.getString("onsale");
                            String pOnsalePrice = object.getString("onsale_price");
                            String pInvoiceDetails = object.getString("invoice_details");
                            String pImgLargeDetail = object.getString("image_large_details");
                            String pReview = object.getString("review");
                            String pDesc = object.getString("description");
                            String pTag = object.getString("tag");
                            String pSpecs = object.getString("specification");
                            String pStatus = object.getString("status");
                            String pCatName = object.getString("category_name");

                            mList.add(new ProductsFields(id, pId, pSupplierId, pCatId, pName, pPrice, pSupplierPrice, pUnit, pModel, pDetail,
                                    imgThumb, pBrandId, varient, pType, pBestSale, pOnSale, pOnsalePrice, pInvoiceDetails, pImgLargeDetail, pReview, pDesc, pTag, pSpecs, pStatus, pCatName));
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ProductsActivity.this, "Exception", Toast.LENGTH_SHORT).show();
                }

                mRv.setAdapter(new ProductsAdapter(mList, ProductsActivity.this));
                mRv.setLayoutManager(new LinearLayoutManager(ProductsActivity.this));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                //Utility.showErrorPopup(RegisterActivity.this, "Some Connection Error", HomeScreen.class, "no");
                Toast.makeText(ProductsActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", grocerySubCatFields.getcId());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void openCart(View view) {

        startActivity(new Intent(this, ShoppingCartActivity.class));


    }
}
