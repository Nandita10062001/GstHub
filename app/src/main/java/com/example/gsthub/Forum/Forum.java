package com.example.gsthub.Forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.gsthub.Classroom;
import com.example.gsthub.HomePage;
import com.example.gsthub.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Forum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.Forum);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.Classroom:
                        startActivity(new Intent(getApplicationContext(), Classroom.class));
                        finish();
                        overridePendingTransition(0,0);
                        finish();

                    case R.id.Forum:
                }
            }
        });
    }
}