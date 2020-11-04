package com.example.gsthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {
     DrawerLayout drawerLayout;
     Toolbar toolbar;
     ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.Home);

       toolbar = (Toolbar)findViewById(R.id.toolbar) ;
       setSupportActionBar(toolbar);
       drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

       actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,
               R.string.drawer_close);


       drawerLayout.addDrawerListener(actionBarDrawerToggle);


        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.Home:

                    case R.id.Classroom:
                        startActivity(new Intent(getApplicationContext(),Classroom.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.Forum:
                        startActivity(new Intent(getApplicationContext(),Forum.class));
                        finish();
                        overridePendingTransition(0,0);
                        finish();
                }
            }
        });

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
}