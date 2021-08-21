package com.example.gorocery.GroceryDetails;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.gorocery.R;
import com.example.gorocery.URLs;
import com.example.gorocery.Utility;
import com.example.gorocery.VolleySingleton;
import com.example.gorocery.grocery.GroceryItemsFields;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GrocerySubCategoryActivity extends AppCompatActivity {


    private RecyclerView mRv;
    private Toolbar mToolbar;

    public static GroceryItemsFields groceryItemsFields = null;

    private ArrayList<GrocerySubCatFields> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_details);


        Objects.requireNonNull(getSupportActionBar()).hide();


        mToolbar = findViewById(R.id.toolbar_grocery_details);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRv = findViewById(R.id.rv_grocery_details);
        mList = new ArrayList<>();


        mToolbar.setTitle(groceryItemsFields.getcName());

//        Toast.makeText(this, "Cat Id: "+groceryItemsFields.getcId(), Toast.LENGTH_SHORT).show();

        mList.add(new GrocerySubCatFields("cId", "cParentId", "cName", "cTopMenu", "cMenuPos", "cImgUrl", "cFavIcon", "cType", "cStatus"));
        mList.add(new GrocerySubCatFields("cId", "cParentId", "cName", "cTopMenu", "cMenuPos", "cImgUrl", "cFavIcon", "cType", "cStatus"));
        mList.add(new GrocerySubCatFields("cId", "cParentId", "cName", "cTopMenu", "cMenuPos", "cImgUrl", "cFavIcon", "cType", "cStatus"));
        mRv.setAdapter(new GrocerySubCatAdapter(mList, GrocerySubCategoryActivity.this));
        mRv.setLayoutManager(new GridLayoutManager(GrocerySubCategoryActivity.this, 2));

//        loadSubCatGroceries();


    }

    private void loadSubCatGroceries() {
        final ProgressDialog dialog = Utility.show(GrocerySubCategoryActivity.this, "", "Loading . . ");

//        String url="http://aktechprojects.com/grocery/app/public/api/subCat?id=VH4CZIV5Y37CDEO";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SUB_CAT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i <= array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        String cName = object.getString("category_name");
                        String cImgUrl = object.getString("cat_image");
                        String cId = object.getString("category_id");
                        String cParentId = object.getString("parent_category_id");
                        String cTopMenu = object.getString("top_menu");
                        String cMenuPos = object.getString("menu_pos");
                        String cFavIcon = object.getString("cat_favicon");
                        String cType = object.getString("cat_type");
                        String cStatus = object.getString("status");

                        mList.add(new GrocerySubCatFields(cId, cParentId, cName, cTopMenu, cMenuPos, cImgUrl, cFavIcon, cType, cStatus));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(GrocerySubCategoryActivity.this, "Exception", Toast.LENGTH_SHORT).show();
                }

                mRv.setAdapter(new GrocerySubCatAdapter(mList, GrocerySubCategoryActivity.this));
                mRv.setLayoutManager(new GridLayoutManager(GrocerySubCategoryActivity.this, 2));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                //Utility.showErrorPopup(RegisterActivity.this, "Some Connection Error", HomeScreen.class, "no");
                Toast.makeText(GrocerySubCategoryActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", groceryItemsFields.getcId());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


        /*JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.POST, URLs.GROCERIES_SUB_CAT, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //mList=new ArrayList<>();
                        for(int i=0; i<=response.length(); i++){

                            try {

                                JSONObject object = response.getJSONObject(i);
                                String cName=object.getString("category_name");
                                String cImgUrl=object.getString("cat_image");
                                String cId=object.getString("category_id");
                                String cParentId=object.getString("parent_category_id");
                                String cTopMenu=object.getString("top_menu");
                                String cMenuPos=object.getString("menu_pos");
                                String cFavIcon=object.getString("cat_favicon");
                                String cType=object.getString("cat_type");
                                String cStatus=object.getString("status");

                                mList.add(new GrocerySubCatFields(cId,cParentId,cName,cTopMenu,cMenuPos,cImgUrl,cFavIcon,cType,cStatus));

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(GrocerySubCategoryActivity.this, "Exception", Toast.LENGTH_SHORT).show();
                            }

                            //Toast.makeText(MainActivity.this, "Loc: "+object.getString("pro_loc"), Toast.LENGTH_SHORT).show();
                        }

                        mRv.setAdapter(new GrocerySubCatAdapter(mList,GrocerySubCategoryActivity.this));
                        mRv.setLayoutManager(new LinearLayoutManager(GrocerySubCategoryActivity.this));


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(GrocerySubCategoryActivity.this, "Volly Error", Toast.LENGTH_SHORT).show();
            }


        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", groceryItemsFields.getcId());

                return params;
            }
        };

        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(arrayRequest);

*/

        /*mList.add(new GroceryItemsFields("Grocery","Oil,Daalain,Olives etc.. ",R.drawable.grocery));
        mList.add(new GroceryItemsFields("Fruits & Vegetables","Fruits,potato,Orange etc.. ",R.drawable.fruits_veg));

*/


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
