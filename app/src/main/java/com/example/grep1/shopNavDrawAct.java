package com.example.grep1;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class shopNavDrawAct extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    String[] nameArray = {"","","",""};
    String[] latArray = {"","","",""};
    String[] longArray = {"","","",""};
    String[] addArray = {"","","",""};
    String[] cUid = {"","","",""};
    String[] phoneArray={"","","",""};
    String[] listArray={"","","",""};
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int counter=0;
    Intent intentshopOrder;
    Geocoder geocoder;
    List<Address> addresses;

    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_nav_draw);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //intentshopOrder=new Intent(getApplicationContext(),shopOrder.class);
        Intent intent=getIntent();
        uid=intent.getStringExtra("UId");

        geocoder = new Geocoder(this, Locale.getDefault());

        intentshopOrder=new Intent(getApplicationContext(),shopOrder.class);

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


        recyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("sid/"+uid+"/pendingRequests");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> chlNames = dataSnapshot.getChildren();

                counter = 0;
                for (DataSnapshot contact : chlNames) {
                    cUid[counter]=contact.getKey();
                    nameArray[counter] = dataSnapshot.child(contact.getKey()).child("cName").getValue().toString();
                    phoneArray[counter] = dataSnapshot.child(contact.getKey()).child("cPhone").getValue().toString();
                    listArray[counter] = dataSnapshot.child(contact.getKey()).child("list").getValue().toString();
                    latArray[counter] = dataSnapshot.child(contact.getKey()).child("deliveryAdd").child("latitude").getValue().toString();
                    longArray[counter] = dataSnapshot.child(contact.getKey()).child("deliveryAdd").child("longitude").getValue().toString();
                    //shoptype[counter]= dataSnapshot.child(contact.getKey()).child("type").getValue().toString();
                    Toast.makeText(getApplicationContext(),nameArray[counter],Toast.LENGTH_SHORT).show();

                    try {
                        addresses = geocoder.getFromLocation(Double.parseDouble(latArray[counter]), Double.parseDouble(longArray[counter]), 1);
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName(); // O
                        addArray[counter]=address+" "+city+" "+state+""+country+" "+postalCode+" "+knownName;

                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(),"unknown address",Toast.LENGTH_SHORT).show();
                        addArray[counter]="unknown Location";
                    }


                    counter++;

                }
                recyclerView.setAdapter(new AdapterProgram(nameArray,addArray));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView.setAdapter(new AdapterProgram(nameArray,addArray));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(shopNavDrawAct.this, String.valueOf(position), Toast.LENGTH_SHORT).show();

                intentshopOrder.putExtra("cUid",uid);
                intentshopOrder.putExtra("sUid",cUid[position]);
                intentshopOrder.putExtra("sAdd",addArray[position]);
                intentshopOrder.putExtra("sName",nameArray[position]);
                intentshopOrder.putExtra("sPhone",phoneArray[position]);
                intentshopOrder.putExtra("sPhone",listArray[position]);

                startActivity(intentshopOrder);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

    }

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
        getMenuInflater().inflate(R.menu.shop_nav_draw, menu);
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
