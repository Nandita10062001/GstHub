package com.example.gsthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gsthub.account.CreateAccount;

public class SignInAs extends AppCompatActivity {
    Button teacher,student,alumni,guest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_as);
        teacher = findViewById(R.id.signInTeacher);
        student = findViewById(R.id.signInStudent);
        alumni = findViewById(R.id.signInAlumni);
        guest = findViewById(R.id.signInGuest);

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage2.class);
                startActivity(intent);
            }
        });
        alumni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(intent);
            }
        });
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(intent);
            }
        });
    }
}