package com.example.grep1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class rikLoginAct extends AppCompatActivity {

    Button rikSignup;
    Button rikLogin;
    Intent intentRikSignUp;
    Intent intentNavDraw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rik_login);
        setTitle("Rikshaw Login");
        rikSignup=(Button)findViewById(R.id.btnCallSignup);
        rikLogin=(Button)findViewById(R.id.btnLogin);
        intentRikSignUp=new Intent(getApplicationContext(),shopSignUp.class);
        intentNavDraw=new Intent(getApplicationContext(),rikNavDrawAct.class);
    }
    public void onClickCallSignup(View view)

    {
        startActivity(intentRikSignUp);
    }

    public void onClickLogin(View view)
    {
        startActivity(intentNavDraw);
        Toast.makeText(rikLoginAct.this,"Login", Toast.LENGTH_SHORT).show();
    }
}
