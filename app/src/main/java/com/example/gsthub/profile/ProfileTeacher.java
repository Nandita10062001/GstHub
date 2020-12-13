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
import com.example.gsthub.EditProfileTeacher;
import com.example.gsthub.R;
import com.example.gsthub.Teacher;
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

public class ProfileTeacher extends AppCompatActivity {

    private ImageView img;
    private FirebaseAuth auth;
    private TextView TeName,TeDepartment,TeQualifications;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button EditProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_teacher);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();
        img=findViewById(R.id.teacherPhoto1);
        TeName=findViewById(R.id.TeacherProfileName);
        TeDepartment=findViewById(R.id.TeacherDepartmentFill);
        TeQualifications=findViewById(R.id.TeacherQualificationsFill);
        EditProfile = findViewById(R.id.teacherEditProfileButton);
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfileTeacher.class));
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
                Teacher teacherProfile = snapshot.getValue(Teacher.class);
                if(teacherProfile!=null)
                {
                    String Name = teacherProfile.tName;
                    String Department = teacherProfile.tDepartment;                     //displaying the information
                    String Qualifications = teacherProfile.tQualifications;


                    TeName.setText(Name);
                    TeDepartment.setText(Department);
                    TeQualifications.setText(Qualifications);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(ProfileTeacher.this,"Something went Wrong!",Toast.LENGTH_LONG).show();

            }
        });
    }
}