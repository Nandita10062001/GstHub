package com.example.gsthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.gsthub.profile.ProfileStudent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
     DrawerLayout drawerLayout;
     Toolbar toolbar;
     ActionBarDrawerToggle actionBarDrawerToggle;
     FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.Home);

        NavigationView navigationView= findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       toolbar = (Toolbar)findViewById(R.id.toolbar) ;
       setSupportActionBar(toolbar);
       drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mAuth = FirebaseAuth.getInstance();
       actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,
               R.string.drawer_close);


       /*drawerLayout.addDrawerListener(actionBarDrawerToggle);
       actionBarDrawerToggle.syncState();*/


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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                startActivity(new Intent(getApplicationContext(), ProfileStudent.class));
                break;

            case R.id.info:
                startActivity(new Intent(getApplicationContext(), AboutUs.class));
                break;
            case R.id.contact:
                startActivity(new Intent(getApplicationContext(), Contact.class));
                break;
            case R.id.logout:
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
}