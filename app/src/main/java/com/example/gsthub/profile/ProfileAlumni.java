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

import com.example.gsthub.Alumni;
import com.example.gsthub.EditProfileAlumni;
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

public class ProfileAlumni extends AppCompatActivity {
    private ImageView img;
    private FirebaseAuth auth;
    private TextView AlName,AlYear,AlBranch,AlTeam;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button EditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_alumni);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();
        img=findViewById(R.id.alumniPhoto1);
        AlName=findViewById(R.id.AlumniProfileName);
        AlYear=findViewById(R.id.AlumniProfilePassoutYearFill);
        AlBranch=findViewById(R.id.AlumniProfileBranchFill);
        AlTeam=findViewById(R.id.AlumniProfileTeamsFill);
        EditProfile = findViewById(R.id.alumniEditProfileButton);
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfileAlumni.class));
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
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Alumni alumniProfile = snapshot.getValue(Alumni.class);
                if(alumniProfile!=null)
                {
                    String Name = alumniProfile.aName;
                    String Year = alumniProfile.aYear;                     //displaying the information
                    String Branch = alumniProfile.aBranch;
                    String Team = alumniProfile.aTeam;

                    AlName.setText(Name);
                    AlYear.setText(Year);
                    AlBranch.setText(Branch);
                    AlTeam.setText(Team);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(ProfileAlumni.this,"Something went Wrong!",Toast.LENGTH_LONG).show();

            }
        });
    }
}