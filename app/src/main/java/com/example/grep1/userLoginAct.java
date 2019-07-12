package com.example.grep1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class userLoginAct extends AppCompatActivity {

    Button userSignup;
    Button userLogin;
    Intent intentUserSignUp;
    Intent intentNavDraw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        setTitle("User Login");

        userSignup=(Button)findViewById(R.id.btnCallSignup);
        userLogin=(Button)findViewById(R.id.btnLogin);
        intentUserSignUp=new Intent(getApplicationContext(),shopSignUp.class);
        intentNavDraw=new Intent(getApplicationContext(),rikNavDrawAct.class);
    }

    public void onClickCallSignup(View view)
    {
        startActivity(intentUserSignUp);
    }

    public void onClickLogin(View view)
    {
        startActivity(intentNavDraw);
        Toast.makeText(userLoginAct.this,"Login", Toast.LENGTH_SHORT).show();
    }

}
