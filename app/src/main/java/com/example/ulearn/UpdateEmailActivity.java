package com.example.ulearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateEmailActivity extends AppCompatActivity {
    TextInputEditText new_email,old_email;
    TextInputLayout new_email_lay,old_email_lay;
    Button update_btn2;
    Validation validate;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        validate =new Validation(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        new_email_lay=findViewById(R.id.new_email_lay_update);
        old_email_lay = findViewById(R.id.old_email_lay_update);

        new_email = findViewById(R.id.new_email);
        old_email = findViewById(R.id.current_email);

        update_btn2 = findViewById(R.id.update_btn2);

        update_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldEmail = old_email.getText().toString();
                String newEmail = new_email.getText().toString();

                if(!oldEmail.equals(newEmail)){
                    if(validate.checkEm(newEmail)){
                        firebaseUser.updateEmail(newEmail)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(UpdateEmailActivity.this, "Email ID updated to :"+newEmail, Toast.LENGTH_SHORT).show();
                                            updateUser(firebaseUser);
                                        }
                                        else{
                                            Toast.makeText(UpdateEmailActivity.this, "Error occurred :"+task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    private void updateUser(FirebaseUser firebaseUser) {
                                        FirebaseDatabase fdbase = FirebaseDatabase.getInstance();
                                        DatabaseReference rootref = fdbase.getReference();
                                        DatabaseReference mailref = rootref.child("Users").child(firebaseUser.getUid()).child("Email");
                                        mailref.setValue(newEmail);
                                    }
                                });
                    }
                    else{
                        new_email_lay.setError("E-nail ID invalid!");
                    }
                }
                else{
                    new_email_lay.setError("New email id cannot be the same as the current one");
                }
            }




        });


    }


}