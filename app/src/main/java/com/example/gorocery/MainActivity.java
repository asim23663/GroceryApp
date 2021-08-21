package com.example.gorocery;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.gorocery.activities.WalletActivity;
import com.example.gorocery.model.UserFields;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gorocery.ShoppingCart.ShoppingCartActivity;
import com.example.gorocery.myOrders.MyOrderActivity;
import com.example.gorocery.activities.ProfileActivity;
import com.example.gorocery.activities.PromotionsActivity;
import com.example.gorocery.activities.SignInActivity;
import com.example.gorocery.grocery.GroceryAdapter;
import com.example.gorocery.grocery.GroceryItemsFields;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private RecyclerView recyclerView;
    private ArrayList<GroceryItemsFields> mList;

    TextView tvGuestUser;
    NavigationView navigationView;

    Menu menu;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.rv_main);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        menu = navigationView.getMenu();

        View headerView = navigationView.getHeaderView(0);

        tvGuestUser = headerView.findViewById(R.id.tv_guest_user);

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)).getState() == NetworkInfo.State.CONNECTED ||
                Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            Toast.makeText(this, "No internet!!", Toast.LENGTH_SHORT).show();

            connected = false;
        }

        if (connected) {
            mList = new ArrayList<>();

            mList.add(new GroceryItemsFields("Apple", "1", "imgUrl", "xyc", "sdhas", "kjasdhk"));
            mList.add(new GroceryItemsFields("Apple", "1", "imgUrl", "xyc", "sdhas", "kjasdhk"));
            mList.add(new GroceryItemsFields("Apple", "1", "imgUrl", "xyc", "sdhas", "kjasdhk"));
            mList.add(new GroceryItemsFields("Apple", "1", "imgUrl", "xyc", "sdhas", "kjasdhk"));
            findViewById(R.id.btn_retry).setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(new GroceryAdapter(mList, MainActivity.this));
//            loadGroceries();
        }


        if (SharedPrefManager.getInstance(MainActivity.this).isLoggedIn()) {
            UserFields userFields = SharedPrefManager.getInstance(MainActivity.this).getUser();
            tvGuestUser.setText(userFields.getName());
        }


    }

    private void loadGroceries() {

        final ProgressDialog dialog = Utility.show(MainActivity.this, "", "Loading . . ");

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, URLs.URL_ALL_CATEGORIES, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        dialog.dismiss();

                        findViewById(R.id.btn_retry).setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        mList = new ArrayList<>();
                        for (int i = 0; i <= response.length(); i++) {

                            JSONObject object = null;
                            try {

                                object = response.getJSONObject(i);
                                String cName = object.getString("category_name");
                                String cImgUrl = object.getString("cat_image");
                                String cId = object.getString("category_id");
                                String cTopMenu = object.getString("top_menu");
                                String cMenuPos = object.getString("menu_pos");
                                String cFavIcon = object.getString("cat_favicon");

                                mList.add(new GroceryItemsFields(cName, cId, cImgUrl, cTopMenu, cMenuPos, cFavIcon));

                            } catch (JSONException e) {
                                e.printStackTrace();
                                dialog.dismiss();
//                                Toast.makeText(MainActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();

                            }

                            //Toast.makeText(MainActivity.this, "Loc: "+object.getString("pro_loc"), Toast.LENGTH_SHORT).show();
                        }

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(new GroceryAdapter(mList, MainActivity.this));


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                recyclerView.setVisibility(View.GONE);
                findViewById(R.id.btn_retry).setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                Log.e("Error", Objects.requireNonNull(error.getMessage()));
                dialog.dismiss();


            }
        });

        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(arrayRequest);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //this.menu = menu;
        getMenuInflater().inflate(R.menu.shopping_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.shopping_cart) {
            startActivity(new Intent(this, ShoppingCartActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

        MenuItem drawerSignIn = menu.findItem(R.id.drawer_sign_in);
        MenuItem drawerSignOut = menu.findItem(R.id.drawer_sign_out);
        MenuItem drawerWallet = menu.findItem(R.id.drawer_wallet);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            drawerSignIn.setVisible(false);
            drawerSignOut.setVisible(true);
            drawerWallet.setVisible(true);
        } else {
            drawerSignIn.setVisible(true);
            drawerSignOut.setVisible(false);
            drawerWallet.setVisible(false);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



       /* if (id == R.id.drawer_category) {

            startActivity(new Intent(this, CategoriesActivity.class));
        } else */
        if (id == R.id.drawer_my_cart) {

            startActivity(new Intent(this, ShoppingCartActivity.class));

        } else if (id == R.id.drawer_my_profile) {

            startActivity(new Intent(this, ProfileActivity.class));

        } else if (id == R.id.drawer_my_order) {

            startActivity(new Intent(this, MyOrderActivity.class));

        } else if (id == R.id.drawer_my_promotions) {
            startActivity(new Intent(this, PromotionsActivity.class));

        } else if (id == R.id.drawer_share) {

            Toast.makeText(this, "Coming soon!", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.drawer_wallet) {

            startActivity(new Intent(this, WalletActivity.class));
            //Toast.makeText(this, "Coming soon!", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.drawer_faqs) {

            Toast.makeText(this, "Coming soon!", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.drawer_whats_app) {

            Uri uri = Uri.parse("smsto:" + "+923064312405");
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            startActivity(i);
            //startActivity(new Intent(this, LiveChatActivity.class));

        } else if (id == R.id.drawer_call_us) {

            if (isPermissionGranted()) {
                callAction();
            }


        } else if (id == R.id.drawer_sign_in) {

            startActivity(new Intent(this, SignInActivity.class));

        } else if (id == R.id.drawer_sign_out) {

            doLogOut();


            //startActivity(new Intent(this, SignInActivity.class));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //    Calling
    public void callAction() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:03227644832"));
        startActivity(callIntent);
    }

    public boolean isPermissionGranted() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v("TAG","Permission is granted");
                return true;
            } else {

//                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
//            Log.v("TAG","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    callAction();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    //    For LoggingOut
    public void doLogOut() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure you want to LogOut!");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                        finish();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void doRetry(View view) {

        loadGroceries();
    }
}
