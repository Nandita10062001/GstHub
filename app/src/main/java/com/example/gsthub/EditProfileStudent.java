package com.example.gsthub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gsthub.profile.ProfileStudent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class EditProfileStudent extends AppCompatActivity {
 private ImageView img;
 FirebaseAuth auth;
    private Uri imgUri;
    private StorageReference storageReference;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button SaveProfile;
    private EditText Stname,Styear,Stbranch,Stteam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_student);
        img = findViewById(R.id.Photo2);
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
       user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        SaveProfile = findViewById(R.id.editProfileBtn);
        Stname = findViewById(R.id.studentNameEdit);
        Styear = findViewById(R.id.studentYearEdit);
        Stbranch = findViewById(R.id.studentBranchEdit);
        Stteam = findViewById(R.id.studentTeamEdit);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();           //after uploading to display it on that page every time you open the activity
        final StorageReference ref = storageReference.child("users/" + auth.getCurrentUser().getUid() + "/profile.jpg");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(img);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);  //on clicking the image
                startActivityForResult(intent, 1000);
            }
        });

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student studentProfile = snapshot.getValue(Student.class);                        // displaying all the data in edittext
                if (studentProfile != null) {
                    String Name = studentProfile.sName;
                    String Year = studentProfile.sYear;
                    String Branch = studentProfile.sBranch;
                    String Team = studentProfile.sTeam;

                    Stname.setText(Name);
                    Styear.setText(Year);
                    Stbranch.setText(Branch);
                    Stteam.setText(Team);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfileStudent.this, "Something went Wrong!", Toast.LENGTH_LONG).show();

            }
        });

        SaveProfile.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                updateProfile(Stname.getText().toString(), Styear.getText().toString(),

                        Stbranch.getText().toString(), Stteam.getText().toString());

            }

        });

    }

    private void updateProfile(String Stname, String Styear, String Stbranch, String Stteam) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

        HashMap<String, Object> edited = new HashMap<>();

        edited.put("sName", Stname);

        edited.put("sYear", Styear);

        edited.put("sBranch", Stbranch);

        edited.put("sTeam", Stteam);



        reference.updateChildren(edited).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override

            public void onSuccess(Void aVoid) {

                Toast.makeText(EditProfileStudent.this, "Profile Updated", Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(), ProfileStudent.class));

                finish();

            }

        });

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);                                   //imageUpload
        if(requestCode==1000 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imgUri = data.getData();
            img.setImageURI(imgUri);
            uploadImage(imgUri);
        }

    }
    private void uploadImage(Uri imgUri)       //method
    {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image....");
        pd.show();
        StorageReference fileRef = storageReference.child("users/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Snackbar.make(findViewById(android.R.id.content),"Image Uploaded!", Snackbar.LENGTH_LONG).show();
                pd.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failed to Upload!", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                pd.setMessage("Progress: "+(int)progressPercent+" %");
            }
        });
    }
}