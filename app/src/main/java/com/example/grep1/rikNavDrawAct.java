package com.example.grep1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class rikNavDrawAct extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String uid;
    String type;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int counter;
    String[] userNames={"","","","","","",""};

    Location locationSet;
    LocationManager locationManager;
    LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rik_nav_draw);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent=getIntent();
        uid=intent.getStringExtra("UId");
        type=intent.getStringExtra("type");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("rid/"+uid);
        Toast.makeText(getApplicationContext(),firebaseDatabase.getReference("rid/"+uid).toString(),Toast.LENGTH_SHORT).show();

        setTitle("grep");

       /* databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int NumberOfUsers = (int) dataSnapshot.getChildrenCount();
                Toast.makeText(getApplicationContext(), NumberOfUsers, Toast.LENGTH_SHORT).show();
                Iterable<DataSnapshot> chlNames = dataSnapshot.getChildren();
                //Log.i("No. of Prisoners", String.valueOf(NumberOfUsers) );
                // prisonerNames = new ArrayList<String>();
                // prisonerEmail = new ArrayList<String>();
                // prisonerStepCount = new ArrayList<String>();
                counter = 0;
                for (DataSnapshot contact : chlNames) {
                    //prinonerName.add(dataSnapshot.child(contact.getKey()).child("Email").getValue().toString());
                    //prisonerEmail.add(dataSnapshot.child(contact.getKey()).child("Name").getValue().toString());

                    //prisonerUid[counter]=contact.getKey();
                    userNames[counter] = dataSnapshot.child(contact.getKey()).child("name").getValue().toString();
                    //EmailArray[counter] = dataSnapshot.child(contact.getKey()).child("Email").getValue().toString();
                    //StepsArray[counter] =  dataSnapshot.child(contact.getKey()).child("StepCount").getValue().toString();
                    //PrisonersLocationLAT[counter] = dataSnapshot.child(contact.getKey()).child("Location").child("latitude").getValue().toString();
                    //PrisonersLocationLONG[counter] = dataSnapshot.child(contact.getKey()).child("Location").child("longitude").getValue().toString();
                    // String WatchOnPrisoner =  dataSnapshot.child(contact.getKey()).child("WatchOnBodyisTrue").getValue().toString();
                    Toast.makeText(getApplicationContext(), userNames[counter], Toast.LENGTH_SHORT).show();
                    //if(WatchOnPrisoner.equals("true")){WatchOnBodyisTrue[counter] = true; }
                    //else{WatchOnBodyisTrue[counter] = false;}

                    //if(Float.valueOf(PrisonersLocationLONG[counter]) > 77.97683122 || Float.valueOf(PrisonersLocationLONG[counter]) < 77.95683122 || Float.valueOf(PrisonersLocationLAT[counter]) < 30.40718828 || Float.valueOf(PrisonersLocationLAT[counter]) > 30.42718828 || !WatchOnBodyisTrue[counter] )
                    //{
                    //  locBoundCheck[counter] = 1; // Alarm needed
                    //isAlarmNeeded=true;

                    //}else{
                    //  locBoundCheck[counter] = 0;
                    //isAlarmNeeded=false;
                    //}

                    //if(isAlarmNeeded){
                    //  if(!audio.isPlaying()) {
                    //    audio.start();
                    //}
                    //}else{
                    //   if(audio.isPlaying()) {
                    //      audio.pause();
                    //}



                //Log.d("prisonersID :: ",  PrisonersLocationLAT[counter] +"      " + PrisonersLocationLONG[counter] );
                counter++;

            }
            //recyclerView.setAdapter(new AdapterProgram(NameArray,EmailArray,StepsArray,locBoundCheck));

        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });  */


        //location
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                Log.i("info :", "location");
                locationSet = location;
                Toast.makeText(getApplicationContext(),locationSet.toString(),Toast.LENGTH_SHORT).show();
                databaseReference.child("location").setValue(locationSet);
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
            Toast.makeText(getApplicationContext(),locationSet.toString(),Toast.LENGTH_SHORT).show();
            databaseReference.child("location").setValue(locationSet);
        }






    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

            }
        }  }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rik_nav_draw, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
