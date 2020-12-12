package com.example.gsthub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterStudent extends AppCompatActivity {
    private EditText StName,StEmail,StPassword,StYear,StBranch,StTeam;
    private Button SignUp;
    private TextView AlreadyAcc;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

       StName=findViewById(R.id.StudentName);
       StEmail=findViewById(R.id.StudentEmail);
       StPassword=findViewById(R.id.StudentPassword);
       StYear=findViewById(R.id.StudentYear);
       StBranch=findViewById(R.id.StudentBranch);
       StTeam=findViewById(R.id.StudentTeam);
       SignUp=findViewById(R.id.signUpButton);
       AlreadyAcc=findViewById(R.id.alreadyAcc);
       auth = FirebaseAuth.getInstance();




       AlreadyAcc.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),SignIn.class));
           }
       });


       SignUp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               final String Name = StName.getText().toString().trim();
               final String Email = StEmail.getText().toString().trim();
               final String Password = StPassword.getText().toString().trim();
               final String Year = StYear.getText().toString().trim();
               final String Branch = StBranch.getText().toString().trim();
               final String team = StTeam.getText().toString().trim();

               if(Name.isEmpty())
               {
                   StName.setError("Please fill in  your Name!");
                   StName.requestFocus();
                   return;
               }
               if(Email.isEmpty())
               {
                  StEmail.setError("Enter your Email!");
                  StEmail.requestFocus();
                  return;
               }
               if(Password.isEmpty())
               {
                   StPassword.setError("Create a Strong Password!");
                   StPassword.requestFocus();
                   return;
               }
               if(Year.isEmpty())
               {
                   StYear.setError("Mention Your Year!");
                   StYear.requestFocus();
                   return;
               }
               if(Branch.isEmpty())
               {
                   StBranch.setError("Mention your Branch!");
                   StBranch.requestFocus();
                   return;
               }
               if(team.isEmpty())
               {
                   StTeam.setError("If no teams, fill in Null");
                   StTeam.requestFocus();
                   return;
               }
               if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
               {
                   StEmail.setError("Please enter a valid Email Address!");
                   StEmail.requestFocus();
                   return;
               }
               if(Password.length()<6)
               {
                   StPassword.setError("Password should be of Minimum 6 characters!..");
                   StPassword.requestFocus();
                   return;
               }

               auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful())
                       {
                           Student student = new Student(Name,Email,Year,Branch,team);
                           FirebaseDatabase.getInstance().getReference("Student")
                                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                   .setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       Toast.makeText(RegisterStudent.this,"User has been registered successfully",Toast.LENGTH_LONG).show();
                                       startActivity(new Intent(getApplicationContext(),HomePage.class));
                                       finish();
                                   }
                                   else{
                                       Toast.makeText(RegisterStudent.this,"Failed to register! Try Again!",Toast.LENGTH_LONG).show();
                                   }
                               }
                           });
                       }
                       else{
                           Toast.makeText(RegisterStudent.this,"Failed to Register!Try Again!",Toast.LENGTH_LONG).show();
                       }

                   }
               });
           }
       });
    }

    }







