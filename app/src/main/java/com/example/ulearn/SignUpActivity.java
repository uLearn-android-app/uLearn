package com.example.ulearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    Button signup_btn, go_to_login;
    TextInputEditText username, email, password, cpassword;
    TextInputLayout username_lay, email_lay, password_lay, cpassword_lay;
    ProgressBar progressBar;
    Validation validate;
    private String myName,myEmail,myPass,myCPass;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // access buttons
        signup_btn = findViewById(R.id.signup_btn);
        go_to_login = findViewById(R.id.go_to_login);

        myAuth = FirebaseAuth.getInstance();

        username_lay = findViewById(R.id.username_lay_signup);
        email_lay = findViewById(R.id.email_lay_signup);
        password_lay = findViewById(R.id.pass_lay_signup);
        cpassword_lay = findViewById(R.id.confirm_pass_lay_signup);


        // access input edit text views:
        username = (TextInputEditText) findViewById(R.id.username_signup);
        email = (TextInputEditText) findViewById(R.id.email_signup);
        password = (TextInputEditText) findViewById(R.id.password_signup);
        cpassword = (TextInputEditText) findViewById(R.id.cpassword_signup);

        validate = new Validation(this);

        progressBar = (ProgressBar) findViewById(R.id.signup_progress);

        // add on click listener to signup button
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }

            private void registerUser() {
                myName = username.getText().toString();
                myEmail = email.getText().toString();
                myPass = password.getText().toString();
                myCPass = cpassword.getText().toString();

                if (!myName.isEmpty()) {
                    if (myPass.equals(myCPass)) {
                        if (validate.checkEm(myEmail) && validate.checkPass(myPass)) {
                            myAuth.createUserWithEmailAndPassword(myEmail, myPass)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {

                                                // firebase user object has email,,password and other profile info inside the objet
                                                FirebaseUser user = myAuth.getCurrentUser();
                                                saveName(user);

                                                user.sendEmailVerification()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(SignUpActivity.this, "Verify your email", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                Toast.makeText(SignUpActivity.this, user.getEmail() + "Sign Up successful", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Toast.makeText(SignUpActivity.this, "Error occured"+task.getException(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        cpassword_lay.setError("Passwords do not match");
                    }
                } else {

                    Toast.makeText(SignUpActivity.this, "Enter a username!", Toast.LENGTH_SHORT).show();
                    username_lay.setError("Enter a username");

                }


            }

            private void saveName(FirebaseUser user) {
                FirebaseDatabase fdbase = FirebaseDatabase.getInstance();
                DatabaseReference rootref = fdbase.getReference();
                DatabaseReference nameref = rootref.child("Users").child(user.getUid()).child("Name");
                nameref.setValue(myName);
                DatabaseReference mailref = rootref.child("Users").child(user.getUid()).child("Email");
                mailref.setValue(myEmail);
            }

        });

        // add on click listener to move to login_activity
        go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }






}