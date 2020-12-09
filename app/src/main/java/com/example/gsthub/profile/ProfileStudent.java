package com.example.gsthub.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.gsthub.EditProfileStudent;
import com.example.gsthub.R;

import com.example.gsthub.Student;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class ProfileStudent extends AppCompatActivity {
    private ImageView img;
    private FirebaseAuth auth;
    private TextView StName,StYear,StBranch,StTeam;
    private FirebaseUser student;
    private DatabaseReference reference;
    private String studentID;
    private Button EditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_student);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        student=auth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Student");
        studentID=student.getUid();
        img=findViewById(R.id.Photo1);
        StName=findViewById(R.id.StudentProfileName);
        StYear=findViewById(R.id.StudentProfileYearFill);
        StBranch=findViewById(R.id.StudentProfileBranchFill);
        StTeam=findViewById(R.id.StudentProfileTeamsFill);
        EditProfile = findViewById(R.id.EditProfileButton);
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfileStudent.class));
                finish();
            }
        });

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();        //displaying the image uploaded in the edit profile part
        StorageReference ref = storageReference.child("users/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(img);
            }
        });
        reference.child(studentID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student studentProfile = snapshot.getValue(Student.class);
                if(studentProfile!=null)
                {
                    String Name = studentProfile.sName;
                    String Year = studentProfile.sYear;                     //displaying the information
                    String Branch = studentProfile.sBranch;
                    String Team = studentProfile.sTeam;

                    StName.setText(Name);
                    StYear.setText(Year);
                    StBranch.setText(Branch);
                    StTeam.setText(Team);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(ProfileStudent.this,"Something went Wrong!",Toast.LENGTH_LONG).show();

            }
        });

    }
}
