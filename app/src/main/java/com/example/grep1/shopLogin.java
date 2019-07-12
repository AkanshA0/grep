package com.example.grep1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class shopLogin extends AppCompatActivity {

    Button shopSignup;
    Button shopLogin;
    Intent intentShopSignUp;
    Intent intentNavDraw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_login);
        setTitle("Shop Login");
        shopSignup=(Button)findViewById(R.id.btnCallSignup);
        shopLogin=(Button)findViewById(R.id.btnLogin);
        intentShopSignUp=new Intent(getApplicationContext(),shopSignUp.class);
        intentNavDraw=new Intent(getApplicationContext(),rikNavDrawAct.class);
    }

    public void onClickCallSignup(View view)
    {
        startActivity(intentShopSignUp);
    }

    public void onClickLogin(View view)
    {
        startActivity(intentNavDraw);
        Toast.makeText(shopLogin.this,"Login", Toast.LENGTH_SHORT).show();
    }
}
