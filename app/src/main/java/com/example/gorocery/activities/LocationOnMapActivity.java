package com.example.gorocery.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.gorocery.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LocationOnMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    private static final float DEFAULT_ZOOM = 15.0f;

    String mLocation = "null";
    private TextView tvLocation;
    Marker marker;
    String latitude = "", longitude = "";
    LocationManager lm;
    LocationListener locationListener;

    PopupWindow popupWindow = null;
    //UserDbRef userDbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_on_map);

        Objects.requireNonNull(getSupportActionBar()).hide();

        //For Runtime Permissions Location Accessing
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    &&
                    ActivityCompat.checkSelfPermission
                            (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 1); // 1 is requestCode
                return;

            }
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.add_location_map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        init();

        //Show Current Location
        try {

            addLocationOnMap();
            //showLocation();
        }catch (Exception e){

            Toast.makeText(this, "No Location Found", Toast.LENGTH_SHORT).show();
        }
    }

    public void init(){


        tvLocation=findViewById(R.id.tv_current_location);
        //toolbar=findViewById(R.id.toolbar_add_home);
        //etAddAddress=findViewById(R.id.et_add_address);




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the mUser will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the mUser has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the mUser grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        LatLng lahore = new LatLng(31.4826352, 74.0541932);
//        mMap.addMarker(new MarkerOptions().position(lahore).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lahore,8));
        // Setting a click event handler for the map
        /*mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                //markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                //get the location name from latitude and longitude
                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> list;
                try {
                    list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    Address address = list.get(0);
                    String currentLocation = "";
                    for(int i =0 ; i<=address.getMaxAddressLineIndex(); i++)
                    {
                        currentLocation +=address.getAddressLine(i);
                    }
                    //moveCamera(latLng,DEFAULT_ZOOM,currentLocation);
                    tvLocation.setText(currentLocation);
                    // This will be displayed on taping the marker
                    markerOptions.title(currentLocation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);
            }
        });*/
        mMap.setMyLocationEnabled(true);
    }

    //Showing Map
    public void showLocation() {

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                //get the location name from latitude and longitude
                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> list;
                try {
                    list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Address address = list.get(0);
                    String currentLocation = "";
                    for(int i =0 ; i<=address.getMaxAddressLineIndex(); i++)
                    {
                        currentLocation +=address.getAddressLine(i);
                    }
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    //moveCamera(latLng,DEFAULT_ZOOM,currentLocation);
                    //tvLocation.setText(currentLocation);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the mUser grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);

    }
    @Override
    protected void onStop() {
        super.onStop();
        //lm.removeUpdates(locationListener);
    }


    //
    //Add Location After Selecting
    public void addLocationOnMap(){



        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        }

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_location_on_map);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setCountry("PAK");

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                mLocation = "" + place.getName();
                latitude = "" + Objects.requireNonNull(place.getLatLng()).latitude;
                longitude = "" + place.getLatLng().longitude;

                tvLocation.setText(mLocation);

                moveCamera(place.getLatLng(),DEFAULT_ZOOM,mLocation);

            }

            @Override
            public void onError(Status status) {

            }
        });
        popupWindow = new PopupWindow(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }
    //For Runtime Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 1:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(LocationOnMapActivity.this,"PERMISSION_DENIED",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LocationOnMapActivity.this,"PERMISSION_GRANTED",Toast.LENGTH_SHORT).show();
                    // permission granted do something
                   onMapReady(mMap);
                }
                break;
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String address) {
        //Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        marker = mMap.addMarker(new MarkerOptions().position(latLng).title(address));
    }

    public void setBackLocation(View view) {

        Intent intent= new Intent();
        intent.putExtra("location", tvLocation.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
