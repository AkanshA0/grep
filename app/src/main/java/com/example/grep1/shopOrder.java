package com.example.grep1;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class shopOrder extends AppCompatActivity {
    String cUid;
    String sUid;
    String sName;
    String sAdd;
    String sPhone;
    String sList;

    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference reference2;
    TextView tName;
    TextView tPhone;
    TextView tAdd;
    TextView tDelAdd;
    TextView tList;
    Button submit;

    Location locationSet;
    LocationManager locationManager;
    LocationListener locationListener;
    DatabaseReference ref;
   // Geocoder geocoder;
    //List<Address> addresses;
    String add;
    Intent intentMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_order);
        setTitle("Pending Order");
        Intent intent=getIntent();
        cUid=intent.getStringExtra("cUid");
        sUid=intent.getStringExtra("sUid");
        sName=intent.getStringExtra("sName");
        sPhone=intent.getStringExtra("sPhone");
        sAdd=intent.getStringExtra("sAdd");
        sList=intent.getStringExtra("sList");

     //   geocoder = new Geocoder(this, Locale.getDefault());

        tName=(TextView) findViewById(R.id.txtShopName);
        tPhone=(TextView) findViewById(R.id.txtPhone);
        tAdd=(TextView) findViewById(R.id.txtAdd);
        tList=(TextView) findViewById(R.id.txtList);
        tDelAdd=(TextView)findViewById(R.id.txtDelAdd);

        intentMap=new Intent(getApplicationContext(),RikMapsActivity.class);

        submit=(Button)findViewById(R.id.btnRik);
        tName.setText(sName);
        tAdd.setText(sAdd);
        tPhone.setText(sPhone);
        tList.setText(sList);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("sid/"+sUid);
        reference2=database.getReference("uid/"+cUid);

    }
    public void onClickRik(View view)
    {
        //intentMap.putExtra("ruid","f5IamgbK6sdaWc4CFKqFaYFRb733");
        startActivity(intentMap);
    }

}

