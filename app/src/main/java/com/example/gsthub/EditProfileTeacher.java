package com.example.gsthub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gsthub.profile.ProfileAlumni;
import com.example.gsthub.profile.ProfileTeacher;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class EditProfileTeacher extends AppCompatActivity {
    private ImageView img;
    FirebaseAuth auth;
    private Uri imgUri;
    private StorageReference storageReference;
    private FirebaseUser Teacher;
    private DatabaseReference reference;
    private String TeacherID;
    private Button SaveProfile;
    private EditText Tename,Tedepartment,Tequalifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_teacher);
        img = findViewById(R.id.teacherPhoto2);
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        Teacher = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Teacher");
       TeacherID = Teacher.getUid();
        SaveProfile = findViewById(R.id.teachereditProfileBtn);
        Tename = findViewById(R.id.teacherNameEdit);
        Tedepartment = findViewById(R.id.teacherDeptEdit);
        Tequalifications = findViewById(R.id.teacherQualificationsEdit);


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

        reference.child(TeacherID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher teacherProfile = snapshot.getValue(Teacher.class);                        // displaying all the data in edittext
                if (teacherProfile != null) {
                    String Name = teacherProfile.tName;
                    String Department = teacherProfile.tDepartment;
                    String Qualifications = teacherProfile.tQualifications;


                    Tename.setText(Name);
                    Tedepartment.setText(Department);
                    Tequalifications.setText(Qualifications);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfileTeacher.this, "Something went Wrong!", Toast.LENGTH_LONG).show();

            }
        });

        SaveProfile.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                updateProfile(Tename.getText().toString(), Tedepartment.getText().toString(),

                        Tequalifications.getText().toString());

            }

        });

    }

    private void updateProfile(String Tename, String Tedepartment, String Tequalifications) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Teacher").child(Teacher.getUid());

        HashMap<String, Object> edited = new HashMap<>();

        edited.put("tName", Tename);

        edited.put("tDepartment", Tedepartment);

        edited.put("tQualifications", Tequalifications);




        reference.updateChildren(edited).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override

            public void onSuccess(Void aVoid) {

                Toast.makeText(EditProfileTeacher.this, "Profile Updated", Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(), ProfileTeacher.class));

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
