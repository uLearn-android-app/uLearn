package com.example.ulearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    TextView helloUser,email;
    Button logOut,update_pass,update_email;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        context =this;

        if(currentUser == null){
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
            finish();
            return;
        }

        helloUser = findViewById(R.id.hello_user);
        email = findViewById(R.id.user_email);

        helloUser.setText("Hi, "+currentUser.getEmail()+"!");

        logOut = findViewById(R.id.logout);
        update_pass = findViewById(R.id.update_pass);
        update_email = findViewById(R.id.update_email);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();

            }
        });

        update_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdatePasswordActivity();
            }
        });

        update_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateEmailActivity();
            }
        });

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference("users").child(currentUser.getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//                if( user != null){
//                    helloUser.setText("Username:"+user.username);
//                    email.setText("Email:"+user.email);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    private void showUpdateEmailActivity() {
        Intent updateEmailActivity = new Intent(HomeActivity.this,UpdateEmailActivity.class);
        startActivity(updateEmailActivity);
    }

    private void showUpdatePasswordActivity() {
        Intent updatePassActivity = new Intent(HomeActivity.this,UpdatePasswordActivity.class);
        startActivity(updatePassActivity);
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes, Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logOutUser();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void logOutUser() {
        auth.signOut();// end session
        Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();// end current activity
    }
}