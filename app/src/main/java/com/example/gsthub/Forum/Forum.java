package com.example.gsthub.Forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.gsthub.Classroom;
import com.example.gsthub.Dashboard;
import com.example.gsthub.HomePage;
import com.example.gsthub.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Forum extends AppCompatActivity {
    private View view;
    private RecyclerView Posts;
    private DatabaseReference ref;
    FirebaseAuth auth;
    private DataAdapter requestAdapter;
    private ArrayList<Data> postLists;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);


        /*Posts =(RecyclerView)view.findViewById(R.id.recyclerViewForum);
        Posts.setLayoutManager(new LinearLayoutManager(getContext()));

        ref = FirebaseDatabase.getInstance().getReference();
        postLists = new ArrayList<>();

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);

        auth = FirebaseAuth.getInstance();
        getActivity().setTitle("Forum");

        requestAdapter = new DataAdapter(postLists);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        Posts.setLayoutManager(layoutManager);
        Posts.setItemAnimator(new DefaultItemAnimator());
        Posts.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        Posts.setAdapter(requestAdapter);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreatePost.class));
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setSelectedItemId(R.id.Forum);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Dashboard:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Forum:
                        return true;
                    case R.id.Classroom:
                        startActivity(new Intent(getApplicationContext(), Classroom.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;/
            }
        });
    }*/}

}