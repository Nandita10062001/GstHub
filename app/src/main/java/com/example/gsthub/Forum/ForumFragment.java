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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.gsthub.HomePage;
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
     RecyclerView recyclerView;
     DatabaseReference ref;
     FirebaseAuth auth;
     PostAdapter postAdapter;
     List<Post> postLists;
     ProgressDialog pd;
     FloatingActionButton floatingActionButton;

    public ForumFragment() {
        // Required empty public constructor
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_forum, container, false);

            floatingActionButton = view.findViewById(R.id.floatingactionbutton);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), CreatePost.class));
                }
            });



            auth = FirebaseAuth.getInstance();

            recyclerView = view.findViewById(R.id.recyclerViewForum);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setStackFromEnd(true);
            layoutManager.setReverseLayout(true);

            recyclerView.setLayoutManager(layoutManager);


            postLists = new ArrayList<>();
            loadPosts();

            return view;

    }


    private void loadPosts() {
        ref = FirebaseDatabase.getInstance().getReference("Posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postLists.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    Post post = ds.getValue(Post.class);
                    postLists.add(post);
                    postAdapter = new PostAdapter(getActivity(), postLists);
                    recyclerView.setAdapter(postAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void searchPosts(String searchQuery) {

    }


}