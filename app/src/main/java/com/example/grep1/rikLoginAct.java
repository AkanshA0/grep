package com.example.grep1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class rikLoginAct extends AppCompatActivity {

    EditText idEditText;
    EditText passwordEditText;
    private FirebaseAuth mAuth;
    private  String TAG ="TAG :";


    Button rikSignup;
    Button rikLogin;
    Intent intentRikSignUp;
    Intent intentNavDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rik_login);
        setTitle("Rikshaw Login");
        idEditText = (EditText)findViewById(R.id.txtId);
        passwordEditText = (EditText)findViewById(R.id.txtPass);
        rikSignup=(Button)findViewById(R.id.btnCallSignup);
        rikLogin=(Button)findViewById(R.id.btnLogin);
        intentRikSignUp=new Intent(getApplicationContext(),shopSignUp.class);
        intentNavDraw=new Intent(getApplicationContext(),rikNavDrawAct.class);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //Toast.makeText(rikLoginAct.this, mAuth.getCurrentUser().getUid() + "value" , Toast.LENGTH_SHORT).show();
        // updateUI(currentUser);
    }
    public void onClickCallSignup(View view)

    {
        startActivity(intentRikSignUp);
    }

    public void onClickLogin(View view)
    {

        Toast.makeText(rikLoginAct.this,"Login", Toast.LENGTH_SHORT).show();

        if(idEditText.getText().toString().contains("@") && idEditText.getText().toString().length() > 5 && passwordEditText.getText().toString().length() > 5) {


            mAuth.signInWithEmailAndPassword(idEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                intentNavDraw.putExtra("UId",user.getUid());
                                startActivity(intentNavDraw);

                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(rikLoginAct.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                // updateUI(null);
                            }

                            // ...
                        }
                    });
        }else{
            Toast.makeText(rikLoginAct.this, "INVALID USERNAME OR PASSWORD !",
                    Toast.LENGTH_LONG).show();
        }

    }
}
