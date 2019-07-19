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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class shopSignUp extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EditText email;
    EditText name;
    EditText phone;
    EditText password;
    Button signup;
    Intent intentLogin;

    public void UpdateFireBase(String phone,String email , String name , String uuid) {

        DatabaseReference dataRef;
        dataRef = databaseReference.child("rid");
        // dataRef.child(uuid).setValue(name);


        DatabaseReference uidRef = dataRef.child(uuid);
        uidRef.child("name").setValue(name);
        uidRef.child("email").setValue(email);
        uidRef.child("phone").setValue(phone);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_sign_up);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        email = (EditText)findViewById(R.id.txtemail);
        name = (EditText)findViewById(R.id.txtname);
        phone = (EditText)findViewById(R.id.txtphone);

        password = (EditText)findViewById(R.id.txtPass);
        signup = (Button) findViewById(R.id.btnSignUp);
        intentLogin=new Intent(getApplicationContext(),shopLogin.class);
    }
    @Override
    public void onStart() {
        super.onStart();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void onClickSignUp(View view) {



        if(email.getText().toString().contains("@") && email.getText().toString().length() > 5 && password.getText().toString().length() > 5) {
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                FirebaseUser user = mAuth.getCurrentUser();
                                UpdateFireBase(phone.getText().toString(),email.getText().toString() , name.getText().toString(), user.getUid() );

                                //LoginIntent.putExtra("UId",user.getUid());

                                startActivity(intentLogin);
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(shopSignUp.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });


        }else{
            Toast.makeText(shopSignUp.this, "INVALID USERNAME OR PASSWORD !",
                    Toast.LENGTH_LONG).show();
        }
    }


}
