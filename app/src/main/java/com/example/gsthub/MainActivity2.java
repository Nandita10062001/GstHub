package com.example.gsthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
    Button signInPageOneBtn;
    Button registerPageOneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        signInPageOneBtn=findViewById(R.id.signInPageOneBtn);
        registerPageOneBtn=findViewById(R.id.registerPageOneBtn);


        signInPageOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
            }
        });
        registerPageOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInAs.class);
                startActivity(intent);
            }
        });
    }
}