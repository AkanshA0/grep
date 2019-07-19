package com.example.grep1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class usrOrder extends AppCompatActivity {

    String cUid;
    String sUid;
    String sName;
    String sAdd;
    String sPhone;
    String cPhone="541";

    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference reference2;
    TextView tName;
    TextView tPhone;
    TextView tAdd;
    EditText tDelAdd;
    EditText tList;
    Button submit;

    Location locationSet;
    LocationManager locationManager;
    LocationListener locationListener;
    DatabaseReference ref;
    Geocoder geocoder;
    List<Address> addresses;
    String add;
    Intent intentNavDraw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usr_order);

        Intent intent=getIntent();
        cUid=intent.getStringExtra("cUid");
        sUid=intent.getStringExtra("sUid");
        sName=intent.getStringExtra("sName");
        sPhone=intent.getStringExtra("sPhone");
        sAdd=intent.getStringExtra("sAdd");
        intentNavDraw=new Intent(getApplicationContext(),usrNavDrawAct.class);

        geocoder = new Geocoder(this, Locale.getDefault());

        tName=(TextView) findViewById(R.id.txtShopName);
        tPhone=(TextView) findViewById(R.id.txtPhone);
        tAdd=(TextView) findViewById(R.id.txtAdd);
        tList=(EditText) findViewById(R.id.txtList);
        tDelAdd=(EditText)findViewById(R.id.txtDelAdd);

        submit=(Button)findViewById(R.id.btnRik);
        tName.setText(sName);
        tAdd.setText(sAdd);
        tPhone.setText(sPhone);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("sid/"+sUid);
        reference2=database.getReference("uid/"+cUid);

        /*reference2.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Iterable<DataSnapshot> chlNames = dataSnapshot.getChildren();


            for (DataSnapshot contact : chlNames) {
                cPhone=dataSnapshot.child(contact.getKey()).child("phone").getValue().toString();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });*/


    }

    public void onClickSend(View view)
    {
        ref=reference.child("pendingRequests").child(cUid);
       // ref.child("cName").setValue(tList.getText().toString());
        ref.child("list").setValue(tList.getText().toString());
        ref.child("delLocation").child("latitude").setValue("26.86885453");
        ref.child("delLocation").child("longitude").setValue("75.76313627");
        ref.child("cPhone").setValue(cPhone);
        ref.child("state").setValue(0);
        startActivity(intentNavDraw);
    }
    public void onClickCurLoc(View view)
    {
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble("26.86885453"), Double.parseDouble("75.76313627"), 1);
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // O
            add=address+" "+city+" "+state+""+country+" "+postalCode+" "+knownName;

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"unknown address",Toast.LENGTH_SHORT).show();
            add="unknown Location";
        }
         tDelAdd.setText(add);
        /*Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_SHORT).show();
        tDelAdd.setText(locationSet.toString());

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

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

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            // Toast.makeText(getApplicationContext(),"no permission",Toast.LENGTH_SHORT).show();

        }
        else {



            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
            locationSet = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }*/


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

            }
        }  }
}
