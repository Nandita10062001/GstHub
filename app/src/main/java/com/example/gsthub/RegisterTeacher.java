package com.example.gsthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RegisterTeacher extends AppCompatActivity {
    private EditText TeName,TeEmail,TePassword,TeDepartment,TeQualifications;
    private Button SignUp;
    private TextView AlreadyAcc;
    private FirebaseAuth auth;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_teacher);

        TeName=findViewById(R.id.TeacherName);
        TeEmail=findViewById(R.id.TeacherEmail);
        TePassword=findViewById(R.id.TeacherPassword);
        TeDepartment=findViewById(R.id.TeacherDepartment);
        TeQualifications=findViewById(R.id.TeacherQualifications);

        SignUp=findViewById(R.id.teachersignUpButton);
        AlreadyAcc=findViewById(R.id.alreadyAcc);
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();



        AlreadyAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignIn.class));
            }
        });


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Name = TeName.getText().toString().trim();
                final String Email = TeEmail.getText().toString().trim();
                final String Password = TePassword.getText().toString().trim();
                final String Department = TeDepartment.getText().toString().trim();
                final String Qualifications = TeQualifications.getText().toString().trim();


                if(Name.isEmpty())
                {
                    TeName.setError("Please fill in  your Name!");
                    TeName.requestFocus();
                    return;
                }
                if(Email.isEmpty())
                {
                    TeEmail.setError("Enter your Email!");
                    TeEmail.requestFocus();
                    return;
                }
                if(Password.isEmpty())
                {
                    TePassword.setError("Create a Strong Password!");
                    TePassword.requestFocus();
                    return;
                }
                if(Department.isEmpty())
                {
                    TeDepartment.setError("Mention Your Department!");
                    TeDepartment.requestFocus();
                    return;
                }
                if(Qualifications.isEmpty())
                {
                    TeQualifications.setError("Mention your Qualifications!");
                    TeQualifications.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
                {
                    TeEmail.setError("Please enter a valid Email Address!");
                    TeEmail.requestFocus();
                    return;
                }
                if(Password.length()<6)
                {
                    TePassword.setError("Password should be of Minimum 6 characters!..");
                    TePassword.requestFocus();
                    return;
                }

                auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            Teacher teacher = new Teacher(Name,Email,Department,Qualifications);
                            FirebaseDatabase.getInstance().getReference("Teacher")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(teacher).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterTeacher.this,"User has been registered successfully",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(),HomePage.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(RegisterTeacher.this,"Failed to register! Try Again!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(RegisterTeacher.this,"Failed to Register!Try Again!",Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
    }
}