package com.example.spiros.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Location;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class PinwheelMap extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    private Boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "PinwheelMap";

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    double lat, lng;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference();

    Button search;
    Spinner spinner2, spinner3;

    ArrayList<Vasi2> vasi2 = new ArrayList<Vasi2>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinwheel_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLocationPermission();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addItems();

            }
        });
        // Add a marker in Serres  and move the camera
        //LatLng serres = new LatLng(41.090923, 23.54132);
        //mMap.addMarker(new MarkerOptions().position(serres).title("Serres"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(serres, 15.0f));


        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(lat = currentLocation.getLatitude(), lng = currentLocation.getLongitude()),
                                    15f);
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(PinwheelMap.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(PinwheelMap.this);
    }



    private void addItems() {

        // Firebase Setup to Retrieve Data

        String rent = spinner2.getSelectedItem().toString();
        String house = spinner3.getSelectedItem().toString();

        Query query = dbRef.child("Adds").orderByChild("vasirent_vasihouse").equalTo(rent+"_"+house);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMap.clear();
                //create builder to add makrers positions
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                boolean hasMarkers=false;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Vasi2 vasi2 = (Vasi2) postSnapshot.getValue(Vasi2.class);
                    String lat1, lng1;
                    LatLng serres = new LatLng(Double.parseDouble(lat1 = vasi2.getVasicountry()), Double.parseDouble(lng1 = vasi2.getVasitk()));
                    String title = vasi2.getVasiaddress();
                    //mMap.addMarker(new MarkerOptions().position(serres).title(title));


                    float f = distance(lat , lng ,Double.parseDouble(lat1), Double.parseDouble(lng1));
                    final String distf = String.format("%.02f", f);
                    vasi2.vasidist=distf;
                    MarkerOptions options = new MarkerOptions()
                            .title(vasi2.getVasiaddress() + "(" + distf + "km)" + vasi2.getVasifirstname() + " " + vasi2.getVasilastname())
                            .position(serres)
                            .snippet( vasi2.getVasiemail() + " " + vasi2.getVasitel() + " " + vasi2.getVasicomments());

                    if(mMap != null){
                        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                            @Override
                            public View getInfoWindow(Marker marker) {
                                return null;
                            }

                            @Override
                            public View getInfoContents(Marker marker) {
                                View v = getLayoutInflater().inflate(R.layout.info_window, null);
                                TextView fname2 = (TextView) v.findViewById(R.id.fname2);
                                TextView lname2 = (TextView) v.findViewById(R.id.lname2);
                                TextView email2 = (TextView) v.findViewById(R.id.email2);
                                TextView tel2 = (TextView) v.findViewById(R.id.tel2);
                                TextView address2 = (TextView) v.findViewById(R.id.address2);
                                TextView comment2 = (TextView) v.findViewById(R.id.comment2);
                                TextView distance2 = (TextView) v.findViewById(R.id.distance2);
                                ImageView imageView3 = (ImageView) v.findViewById(R.id.imageView3);
                                Vasi2 vasFromTag=(Vasi2)marker.getTag();//get object from the the marker
                                LatLng ll = marker.getPosition();
                                Picasso.with(getApplicationContext()).load(vasFromTag.getVasiurl()).into(imageView3);
                                fname2.setText(vasFromTag.getVasifirstname());
                                lname2.setText(vasFromTag.getVasilastname());
                                email2.setText(vasFromTag.getVasiemail());
                                tel2.setText(vasFromTag.getVasitel());
                                address2.setText(vasFromTag.getVasiaddress());
                                comment2.setText(vasFromTag.getVasicomments());
                                distance2.setText("Distance to Target " + vasFromTag.getVasidist() + " Km ");

                                return v;

                            }
                        });
                    }
                    Marker m=mMap.addMarker(options);
                    m.setTag(vasi2);//assign to the marker an object
                    //add positions to builder to zoom to markers
                    builder.include(m.getPosition());
                    hasMarkers=true;
                }
                if(hasMarkers) {
                    //Finally use builder to show markers
                    LatLngBounds bounds = builder.build();
                    int padding = 100; // offset from edges of the map in pixels
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    mMap.animateCamera(cu);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }


    public float distance (double lat, double lng, double lat1, double lng1 ){
        double  earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat-lat1);
        double lngDiff = Math.toRadians(lng-lng1);
        double a = Math.sin(latDiff/2)*Math.sin(latDiff /2)+Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat))*
                Math.sin(lngDiff/2)*Math.sin(lngDiff);
        double c=2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius*c;

        double meterConversion =  1.609;

        return new Float(distance * meterConversion).floatValue();
    }
}



