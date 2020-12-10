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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gsthub.profile.ProfileAlumni;
import com.example.gsthub.profile.ProfileGuest;
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

public class EditProfileGuest extends AppCompatActivity{
    private ImageView img;
    FirebaseAuth auth;
    private Uri imgUri;
    private StorageReference storageReference;
    private FirebaseUser guest;
    private DatabaseReference reference;
    private String guestID;
    private Button SaveProfile;
    private EditText Guname,Guemail,Gucontact;
    private Spinner Guguesttype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_guest);
        img = findViewById(R.id.guestPhoto2);
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        guest = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Guest");
        guestID = guest.getUid();
        SaveProfile = findViewById(R.id.guesteditProfileBtn);
        Guname = findViewById(R.id.guestNameEdit);
        Guemail = findViewById(R.id.guestEmailEdit);
        Gucontact = findViewById(R.id.guestNumberEdit);
        Guguesttype = findViewById(R.id.guestTypeEdit);

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

        reference.child(guestID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Guest guestProfile = snapshot.getValue(Guest.class);                        // displaying all the data in edittext
                if (guestProfile != null) {
                    String Name = guestProfile.gName;
                    String Email = guestProfile.gEmail;
                    String Contact = guestProfile.gContact;
                    String GuestType = guestProfile.gGuestType;

                    Guname.setText(Name);
                    Guemail.setText(Email);
                    Gucontact.setText(Contact);

                    

//             *************Yahape error aara*********************
//                    Guguesttype.setText(GuestType);




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfileGuest.this, "Something went Wrong!", Toast.LENGTH_LONG).show();

            }
        });

        SaveProfile.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                updateProfile(Guname.getText().toString(), Guemail.getText().toString(),

                        Gucontact.getText().toString(), Guguesttype.getSelectedItem().toString());

            }

        });

    }

    private void updateProfile(String Guname, String Guemail, String Gucontact, String Guguesttype) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Alumni").child(guest.getUid());

        HashMap<String, Object> edited = new HashMap<>();

        edited.put("gName", Guname);

        edited.put("gEmail", Guemail);

        edited.put("gContact", Gucontact);

        edited.put("gGuestType", Guguesttype);



        reference.updateChildren(edited).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override

            public void onSuccess(Void aVoid) {

                Toast.makeText(EditProfileGuest.this, "Profile Updated", Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(), ProfileGuest.class));

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
