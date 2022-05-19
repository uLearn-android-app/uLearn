package com.example.ulearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;



public class HomePageActivity extends AppCompatActivity {




    Button ml,cn,cg,flat,cc,cns;
    Intent intent;

    ImageView profile_icon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        // to go to profile page:
        profile_icon = findViewById(R.id.profile_pic);


        ml=(Button) findViewById(R.id.ml_explore);
        cn=(Button) findViewById(R.id.cn_explore);
        cg=(Button) findViewById(R.id.cg_explore);
        flat=(Button) findViewById(R.id.flat_explore);
        cc=(Button) findViewById(R.id.cc_explore);
        cns=(Button) findViewById(R.id.crypto_explore);
        ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getApplicationContext(),FiveUnitActivity.class);
                intent.putExtra("SubjectId",ml.getId());
                intent.putExtra("SubjectName","Machine Learning");
                startActivity(intent);
            }
        });
        cn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getApplicationContext(),FiveUnitActivity.class);
                intent.putExtra("SubjectId",cn.getId());
                intent.putExtra("SubjectName","Computer Networks");
                startActivity(intent);
            }
        });
        cg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getApplicationContext(),FiveUnitActivity.class);
                intent.putExtra("SubjectId",cg.getId());
                intent.putExtra("SubjectName","Computer Graphics");
                startActivity(intent);
            }
        });
        flat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getApplicationContext(),FiveUnitActivity.class);
                intent.putExtra("SubjectId",flat.getId());
                intent.putExtra("SubjectName","Formal Language and Automata Theory");
                startActivity(intent);
            }
        });
        cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getApplicationContext(),ThreeUnitActivity.class);
                intent.putExtra("SubjectId",cc.getId());
                intent.putExtra("SubjectName","Cloud Computing");
                startActivity(intent);
            }
        });
        cns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getApplicationContext(),ThreeUnitActivity.class);
                intent.putExtra("SubjectId",cns.getId());
                intent.putExtra("SubjectName","Cryptography");
                startActivity(intent);
            }
        });

        profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}