package fr.ece.ferminmoreno.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private GoogleMap mMap;

    private GoogleApiClient mGoogleApiClient;
    private TextView textViewLat;
    private TextView textViewLon;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;

    private ImageButton back;

    Double dbLatitude;
    Double dbLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(LocationServices.API)
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
//                .build();
        // mGoogleApiClient.connect();

        textViewLat = (TextView) findViewById(R.id.latitude);
        textViewLon = (TextView) findViewById(R.id.longitude);

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000);

        // Back button
//        if(getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }
        back = (ImageButton) findViewById(R.id.backMap);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                // TODO Add extras or a data URI to this intent as appropriate.
                resultIntent.putExtra("latitude", textViewLat.getText());
                resultIntent.putExtra("longitude", textViewLon.getText());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
        mGoogleApiClient.connect();

        dbLatitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
        dbLongitude = Double.parseDouble(getIntent().getStringExtra("longitude"));

        textViewLat.setText(dbLatitude+"");
        textViewLon.setText(dbLongitude+"");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");
        if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location == null) {
                // Blank for a moment...
            } else {
                handleNewLocation(location);
            }

        }

    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        mLastLocation = location;

        LatLng position = new LatLng(location.getLatitude(),location.getLongitude());

        //mMap.addMarker(new MarkerOptions().position(position).title("Match"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        LatLng sydney = new LatLng(dbLatitude, dbLongitude);


//        if (mLastLocation != null) {
//            //sydney = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
//            sydney = new LatLng(d,d);
//        }
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        final Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .draggable(true)
                .title("Match")
        );

        mMap.setOnMapClickListener(
                new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        textViewLat.setText(Double.toString(latLng.latitude));
                        textViewLon.setText(Double.toString(latLng.longitude));
                        marker.setPosition(latLng);
                        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                }
        );
    }





}
