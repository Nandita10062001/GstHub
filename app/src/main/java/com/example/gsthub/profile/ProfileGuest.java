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
import com.example.gsthub.EditProfileGuest;
import com.example.gsthub.Guest;
import com.example.gsthub.R;
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

public class ProfileGuest extends AppCompatActivity {

    private ImageView img;
    private FirebaseAuth auth;
    private TextView GuName,GuContact,GuEmail, GuGuestType;
    private FirebaseUser guest;
    private DatabaseReference reference;
    private String guestID;
    private Button EditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_guest);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        guest=auth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Guest");
        guestID=guest.getUid();
        img=findViewById(R.id.guestPhoto1);
        GuName=findViewById(R.id.GuestProfileName);
        GuContact=findViewById(R.id.GuestNumberFill);
        GuEmail=findViewById(R.id.GuestEmailFill);
        GuGuestType=findViewById(R.id.GuestTypeFill);
        EditProfile = findViewById(R.id.guestEditProfileButton);
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfileGuest.class));
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
        reference.child(guestID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Guest guestProfile = snapshot.getValue(Guest.class);
                if(guestProfile!=null)
                {
                    String Name = guestProfile.gName;
                    String Email = guestProfile.gEmail;                     //displaying the information
                    String Contact = guestProfile.gContact;
                    String GuestType = guestProfile.gGuestType;

                    GuName.setText(Name);
                    GuEmail.setText(Email);
                    GuContact.setText(Contact);
                    GuGuestType.setText(GuestType);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(ProfileGuest.this,"Something went Wrong!",Toast.LENGTH_LONG).show();

            }
        });
    }
}