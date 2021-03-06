package com.nathann.com.cyrptochainofcustody;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText email_et;
    EditText password_et;
    Button btn;

    FirebaseUser muser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_main);

        email_et = (EditText) findViewById(R.id.editText2);
        password_et = (EditText) findViewById(R.id.editText5);
        btn = (Button) findViewById(R.id.button);

        //FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

             String email = email_et.getText().toString();
             String password = password_et.getText().toString();

             mAuth.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d("MainActivity", "createUserWithEmail:success");

                            Toast.makeText(MainActivity.this, "Authentication succeded.",
                                    Toast.LENGTH_SHORT).show();

                            //FirebaseUser muser = mAuth.getCurrentUser();
                            muser = mAuth.getCurrentUser();

//                            Toast.makeText(MainActivity.this, muser.getEmail(),
//                                    Toast.LENGTH_LONG).show();

                            sendEmail(muser);

                            //go to sign in

                        } else {
                            Log.w("MainActivity", "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void sendEmail(final FirebaseUser mUser){

        //runOnUiThread(
        new Runnable() {
            public void run() {

                Toast.makeText(MainActivity.this, mUser.getEmail(), Toast.LENGTH_SHORT).show();

                mUser.sendEmailVerification().addOnCompleteListener(MainActivity.this,new OnCompleteListener<Void>(){


                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(MainActivity.this, "e-mail sent" , Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "e-mail NOT sent" , Toast.LENGTH_SHORT).show();

                    }
                });
            }
        };

    }

}
