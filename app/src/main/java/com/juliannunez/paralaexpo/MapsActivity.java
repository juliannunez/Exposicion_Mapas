package com.juliannunez.paralaexpo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView tView;
    private EditText longi, lati;
    private Button go, actual;
    private double dlongi, dlati;
    private LocationManager locationManager;
    private int y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tView = (TextView) findViewById(R.id.tView);
        longi = (EditText) findViewById(R.id.eLon);
        lati = (EditText) findViewById(R.id.eLat);
        go = (Button) findViewById(R.id.bGo);
        actual = (Button) findViewById(R.id.bActual);
        y = 0;


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Activamos la capa o layer MyLocation
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        LatLng udea = new LatLng(6.2675852, -75.5697014);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(udea));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        mMap.addMarker(new MarkerOptions().position(udea).title("Aqui estoy").snippet("App para la expo"));

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                makeUseOfNewLocation(location);
                y = 0;

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

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    public void makeUseOfNewLocation(Location location) {
        if (location != null) {
        }
        dlati = location.getLatitude();
        dlongi = location.getLongitude();
        tView.setText("Longitud: " + dlongi + "\nLatitud: " + dlati);
        LatLng actualizado = new LatLng(dlati, dlongi);


        if(y==1){
            mMap.addMarker(new MarkerOptions().position(actualizado).title("Marcador actual").snippet("App para la expo"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(actualizado));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        }

    }

    public void actual(View v) {
        mMap.clear();
        y = 1;
        makeUseOfNewLocation();
    }

    public void makeUseOfNewLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        makeUseOfNewLocation(location);
    }

    public void go (View v){
        mMap.clear();
        dlongi = Double.parseDouble(longi.getText().toString());
        dlati = Double.parseDouble(lati.getText().toString());
        LatLng buscar = new LatLng(dlati, dlongi);
        mMap.addMarker(new MarkerOptions().position(buscar).title("Marcador buscado").snippet("App para la expo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(buscar));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
    }
}
