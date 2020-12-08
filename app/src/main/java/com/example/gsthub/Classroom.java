package com.example.gsthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Classroom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.Classroom);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(),HomePage.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.Classroom:

                    case R.id.Forum:
                        startActivity(new Intent(getApplicationContext(), Forum2.class));
                        finish();
                        overridePendingTransition(0,0);
                        finish();
                }
            }
        });
    }
}