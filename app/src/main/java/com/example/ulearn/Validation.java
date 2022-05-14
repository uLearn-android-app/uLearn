package com.example.ulearn;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

public class Validation {
    Context context;

    Validation(Context context){
        this.context=context;
    }

    boolean checkEm(String email)
    {
        if(email.length() == 0)
        {
            Toast.makeText(context, "Please enter an email id", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) //validate email format
        {
            Toast.makeText(context, "Please enter a valid email id", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    boolean checkPass(String pass)
    {
        if(pass.length() == 0)
        {
            Toast.makeText(context, "Please enter a password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(pass.length() < 6){
            Toast.makeText(context, "Password must be atleast 6 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

}
