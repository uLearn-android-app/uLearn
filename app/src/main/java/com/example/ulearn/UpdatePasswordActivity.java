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

public class UpdatePasswordActivity extends AppCompatActivity {

    TextInputEditText password,cpassword;
    TextInputLayout pass_lay,cpass_lay;
    Button update_btn;
    Validation validate;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_update_password);

        validate =new Validation(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        pass_lay=findViewById(R.id.pass_lay_signup);
        cpass_lay =findViewById(R.id.confirm_pass_lay_signup);

        password = findViewById(R.id.password_signup);
        cpassword = findViewById(R.id.cpassword_signup);
        update_btn = findViewById(R.id.update_btn);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = password.getText().toString();
                String cpass = cpassword.getText().toString();

                if(validate.checkPass(cpass)){
                    if(pass.equals(cpass)){
                        //update firebase info
                        firebaseUser.updatePassword(cpass)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(UpdatePasswordActivity.this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(UpdatePasswordActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }else{
                        pass_lay.setError("Passwords do not match");
                        cpass_lay.setError("Passwords do not match");
                    }
                }else{
                    cpass_lay.setError("wrong password! Please retry");
                }
            }
        });



    }
}