package com.example.gsthub.Forum;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gsthub.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ForumFragment extends Fragment {
    private View view;
    private RecyclerView Posts;
    private DatabaseReference ref;
    FirebaseAuth auth;
    private DataAdapter requestAdapter;
    private ArrayList<Data> postLists;
    private ProgressDialog pd;
    FloatingActionButton fab;

    public ForumFragment() {
        // Required empty public constructor
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_forum, container, false);

       /* Posts =view.findViewById(R.id.recyclerViewForum);
        Posts.setLayoutManager(new LinearLayoutManager(getContext()));

        ref = FirebaseDatabase.getInstance().getReference();
        postLists = new ArrayList<>();

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);*/

       /* auth = FirebaseAuth.getInstance();
        requestAdapter = new DataAdapter(postLists);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        Posts.setLayoutManager(layoutManager);
        Posts.setItemAnimator(new DefaultItemAnimator());
        Posts.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        Posts.setAdapter(requestAdapter);


        AddPosts();
        return view;
    }

   /* private void AddPosts() {
        Query allPosts = ref.child("posts");
        allPosts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot single : snapshot.getChildren()) {
                        Data customUserData = single.getValue(Data.class);
                        postLists.add(customUserData);
                        requestAdapter.notifyDataSetChanged();
                    }

                } else {
                    Toast.makeText(getActivity(), "Database is empty now!", Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Users", error.getMessage());
            }
        });
    }*/
}