package com.example.grep1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button shop;
    Button rik;
    Button user;
    Button dir;
    Intent intentShopLogin;
    Intent intentRikLogin;
    Intent intentUserLogin;
    Intent intentMap;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("grep");

        shop=(Button)findViewById(R.id.btnShopLogin);
        rik=(Button)findViewById(R.id.btnRikLogin);
        user=(Button)findViewById(R.id.btnUserLogin);

        intentShopLogin=new Intent(getApplicationContext(),shopLogin.class);
        intentRikLogin=new Intent(getApplicationContext(),rikLoginAct.class);
        intentUserLogin=new Intent(getApplicationContext(),userLoginAct.class);
        intentMap=new Intent(getApplicationContext(),RikMapsActivity.class);
        intentMap.putExtra("ruid","f5IamgbK6sdaWc4CFKqFaYFRb733");
        intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=22.7177,75.8545"));

    }

    public void onClickShopLogin(View view)
    {
        startActivity(intentShopLogin);
    }

    public void onClickRikLogin(View view)
    {
        startActivity(intentRikLogin);
    }

    public void onClickUserLogin(View view)
    {
        startActivity(intentUserLogin);
    }

    public void onClickCallMap(View view)
    {
        startActivity(intentMap);
    }


    public void onClickDir(View view)
    {
        startActivity(intent);
    }

}
