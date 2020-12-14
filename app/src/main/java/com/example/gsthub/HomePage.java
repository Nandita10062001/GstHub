package com.example.gsthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.gsthub.Forum.CreatePost;
import com.example.gsthub.Forum.Forum;
import com.example.gsthub.Forum.ForumFragment;
import com.example.gsthub.profile.ProfileStudent;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;
    private View view;
    FirebaseUser user;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
//        isTeacher.getReference().child("Teacher");
//        isStudent.getReference().child("Student");
//        isAlumni.getReference().child("Alumni");
//        isGuest.getReference().child("Guest");
        NavigationView navigationView= findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar = (Toolbar)findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mAuth = FirebaseAuth.getInstance();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,
                R.string.drawer_close);

        GoogleSignInOptions googleSignInOptions= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);


    }


            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Forum:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ForumFragment()).commit();
                break;
            case R.id.Dashboard:
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                break;
            case R.id.Classroom:
                startActivity(new Intent(getApplicationContext(), Classroom.class));
                break;
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
                googleSignInClient.signOut();
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
