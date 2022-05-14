package com.example.ulearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button lgn_btn,go_to_signup;
    private TextInputLayout email,password;
    Validation validate;
    private String em,ps;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.login_progress);

        mAuth = FirebaseAuth.getInstance();
//        if(mAuth.getCurrentUser() != null){
//            finish();
//            return;
//        }

        lgn_btn = findViewById(R.id.login_btn);
        go_to_signup = findViewById(R.id.go_to_signup);

        validate = new Validation(this);

        lgn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser();
            }
        });

        go_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void authenticateUser() {
        showProgressBar();
        TextInputEditText myEmail =(TextInputEditText) findViewById(R.id.email_login);
        TextInputEditText myPassword = (TextInputEditText) findViewById(R.id.password_login);

        em = myEmail.getText().toString();
        ps = myPassword.getText().toString();
        if(validate.checkEm(em) && validate.checkPass(ps)) {
            mAuth.signInWithEmailAndPassword(em, ps)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                hideProgressBar();
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                showHomeActivity();
                            } else {
                                Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        MyTask t1 =new MyTask();
        t1.execute();
    }

    private void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showHomeActivity() {
        Intent i =new Intent(this,HomeActivity.class);
        startActivity(i);
        finish();
    }


    private class MyTask extends AsyncTask<String,Integer,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            int i=0;
            while(i<100){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                i++;
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            super.onProgressUpdate(values);
        }
    }
}
