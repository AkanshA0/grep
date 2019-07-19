package com.example.grep1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RikMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;


    Location locationSet;
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng latLong;
    String addressLine;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String curUid;
    Marker m1;
    Marker m2;
    int rno;

    //private Marker previousMarker = null;

    String[] rLat={"","","","","","","",""};
    String[] rLong={"","","","","","","",""};
    LatLng rLatLong;
    int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rik_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        Intent intent=getIntent();
        curUid =intent.getStringExtra("ruid");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("rid");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 rno = (int)dataSnapshot.getChildrenCount();

                Iterable<DataSnapshot> chlNames = dataSnapshot.getChildren();
                counter = 0;
                for (DataSnapshot contact : chlNames) {
                    rLat[counter] = dataSnapshot.child(contact.getKey()).child("location").child("latitude").getValue().toString();
                    rLong[counter] = dataSnapshot.child(contact.getKey()).child("location").child("longitude").getValue().toString();

                    rLatLong = new LatLng(Double.valueOf(rLat[counter]), Double.valueOf(rLong[counter]));
                    Toast.makeText(getApplicationContext(), rLatLong.toString(), Toast.LENGTH_SHORT).show();
                    //mMap2.clear();

                    m1=mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(rLatLong).title("R Location"));
                    if (latLong != null) {
                        m2 = mMap.addMarker(new MarkerOptions().position(latLong));
                    }
                    //   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PrisonerlatLng, 15 ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});



        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                UpdateLocationChangeInfo(location);

                locationSet = location;
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        //marker listener

       /* previousMarker=m1;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String locAddress = marker.getTitle();
                //fillTextViews(locAddress);
                if (previousMarker != null) {
                    previousMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                previousMarker = marker;

                return true;
            }
        });
        */

        //...

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);

            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap2 = googleMap;


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else{
           // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 5000 , 0 ,locationListener );
           // Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //LatLng mylocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
           // mMap.clear();
            //mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).position(mylocation).title("My Location"));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 15 ));
            Toast.makeText(RikMapsActivity.this, "Updating Rikshaw Location........", Toast.LENGTH_LONG).show();

        }
    }
    public void UpdateLocationChangeInfo(Location location) {

        latLong = new LatLng(location.getLatitude(), location.getLongitude());
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLong));


        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            addressLine = addressList.get(0).getAddressLine(0);
            // ToastMaker(addressLine);

        } catch (IOException e) {
            e.printStackTrace();
        }


        mMap.clear();
        m1= mMap.addMarker(new MarkerOptions().position(latLong));

        LatLng tmpLatLong;
        int tmp=rno;
        while(tmp>=0) {
            tmpLatLong= new LatLng(Double.valueOf(rLat[tmp]), Double.valueOf(rLong[tmp]));
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(tmpLatLong).title("Rikshaw Location"));
            tmp--;
        }
         //mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(rLatLong).title("Rikshaw Location"));

    }
}


