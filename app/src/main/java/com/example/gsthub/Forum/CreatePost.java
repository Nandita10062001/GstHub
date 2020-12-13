package com.example.gsthub.Forum;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.gsthub.R;
import com.example.gsthub.SignIn;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;

public class CreatePost extends AppCompatActivity {
    ProgressDialog pd;

    Button PostBtn, imageBtn;
    EditText description;

    String name, email, uid, dp;

    FirebaseDatabase db;
    DatabaseReference ref;
    FirebaseAuth auth;

    Uri image_uri = null;

    Calendar cal;
    String Time, Date;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;

    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;


    String[] cameraPermissions;
    String[] storagePermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        description = findViewById(R.id.question);
        PostBtn = findViewById(R.id.postbtn);
        imageBtn = findViewById(R.id.addImage);

        pd = new ProgressDialog(this);

        /*int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int hour = cal.get(Calendar.HOUR);
        int min = cal.get(Calendar.MINUTE);
        month+=1;
        Time = "";
        Date = "";
        String ampm="AM";

        if(cal.get(Calendar.AM_PM) ==1)
        {
            ampm = "PM";
        }

        if(hour<10)
        {
            Time += "0";
        }
        Time += hour;
        Time +=":";

        if(min<10) {
            Time += "0";
        }

        Time +=min;
        Time +=(" "+ampm);
        Date = day+"/"+month+"/"+year;*/

        cameraPermissions = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        auth = FirebaseAuth.getInstance();
        checkUserStatus();

        ref = FirebaseDatabase.getInstance().getReference("Users");
        Query query = ref.orderByChild("pEmail").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    name = "" + ds.child("pName").getValue();
                    email = "" + ds.child("pEmail").getValue();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickDialogue();
            }
        });

        PostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descrp = description.getText().toString().trim();
                if (TextUtils.isEmpty(descrp)) {
                    Toast.makeText(CreatePost.this, "Ask Question!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (image_uri==null) {
                    uploadData(descrp, "noImage");
                }
                else {
                    uploadData(descrp, String.valueOf(image_uri));

                }

            }
        });

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("posts");


    }

    private void uploadData(final String descrp, String uri) {
        pd.setMessage("Posting...");
        pd.show();

        final String timeStamp = String.valueOf(System.currentTimeMillis());
        String filePathandName = "Posts/" + "post_" + timeStamp;

        if(!uri.equals("noImage")) {
            StorageReference reference = FirebaseStorage.getInstance().getReference().child(filePathandName);
            reference.putFile(Uri.parse(uri))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while(!uriTask.isSuccessful());

                            String downloadUri = uriTask.getResult().toString();

                            if (uriTask.isSuccessful()) {

                                HashMap<Object, String> hashMap = new HashMap<>();
                                hashMap.put("uid", uid);
                                hashMap.put("pName", name);
                                hashMap.put("pEmail", email);
                                hashMap.put("pId", timeStamp);
                                hashMap.put("pDescr", descrp);
                                hashMap.put("pImage", downloadUri);
                                hashMap.put("pTime", timeStamp);

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");

                                ref.child(timeStamp).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                pd.dismiss();
                                                Toast.makeText(CreatePost.this, "Post published", Toast.LENGTH_SHORT).show();
                                                description.setText("");
                                                image_uri = null;

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                pd.dismiss();
                                                Toast.makeText(CreatePost.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });


                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(CreatePost.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }
        else {

            HashMap<Object, String> hashMap = new HashMap<>();
            hashMap.put("uid", uid);
            hashMap.put("pName", name);
            hashMap.put("pEmail", email);
            hashMap.put("pId", timeStamp);
            hashMap.put("pDescr", descrp);
            hashMap.put("pImage", "noImage");
            hashMap.put("pTime", timeStamp);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");

            ref.child(timeStamp).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            Toast.makeText(CreatePost.this, "Post published", Toast.LENGTH_SHORT).show();
                            description.setText("");
                            image_uri = null;

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(CreatePost.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

        }


    }

    private void showImagePickDialogue() {
        String[] options = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image from");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    }
                    else {
                        pickFromCamera();
                    }

                }
                if (i == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    }
                    else {
                        pickFromGallery();
                    }

                }
            }
        });

        builder.create().show();;



    }

    private void checkUserStatus() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();

        } else {
            startActivity(new Intent(CreatePost.this, SignIn.class));
            finish();
        }
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.DESCRIPTION, "Temp Pick");
         image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;

    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length>0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(this, "Camera and Storage both permissions are necessary...", Toast.LENGTH_SHORT).show();
                    }
                }
                else {

                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length>0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        pickFromGallery();
                    }
                    else {
                        Toast.makeText(this, "Storage permission is necessary...", Toast.LENGTH_SHORT).show();
                    }
                }
                else {

                }

            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                image_uri = data.getData();
                //imageBtn.setImageURI(image_uri);
            }
            else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                //imageBtn.setImageURI(image_uri);


            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

