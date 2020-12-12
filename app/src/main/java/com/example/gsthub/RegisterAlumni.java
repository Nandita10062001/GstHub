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

import io.grpc.internal.JsonUtil;

public class RegisterAlumni extends AppCompatActivity {
    private EditText AlName,AlEmail,AlPassword,AlYear,AlBranch,AlTeam;
    private Button SignUp;
    private TextView AlreadyAcc;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_alumni);

        AlName=findViewById(R.id.AlumniName);
        AlEmail=findViewById(R.id.AlumniEmail);
        AlPassword=findViewById(R.id.AlumniPassword);
        AlYear=findViewById(R.id.AlumniPassoutYear);
        AlBranch=findViewById(R.id.AlumniBranch);
        AlTeam=findViewById(R.id.AlumniTeam);
        SignUp=findViewById(R.id.alumnisignUpButton);
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
                final String Name = AlName.getText().toString().trim();
                final String Email = AlEmail.getText().toString().trim();
                final String Password = AlPassword.getText().toString().trim();
                final String Year = AlYear.getText().toString().trim();
                final String Branch = AlBranch.getText().toString().trim();
                final String team = AlTeam.getText().toString().trim();

                if(Name.isEmpty())
                {
                    AlName.setError("Please fill in  your Name!");
                    AlName.requestFocus();
                    return;
                }
                if(Email.isEmpty())
                {
                    AlEmail.setError("Enter your Email!");
                    AlEmail.requestFocus();
                    return;
                }
                if(Password.isEmpty())
                {
                    AlPassword.setError("Create a Strong Password!");
                    AlPassword.requestFocus();
                    return;
                }
                if(Year.isEmpty())
                {
                    AlYear.setError("Mention Your Passout Year!");
                    AlYear.requestFocus();
                    return;
                }
                if(Branch.isEmpty())
                {
                    AlBranch.setError("Mention your Branch!");
                    AlBranch.requestFocus();
                    return;
                }
                if(team.isEmpty())
                {
                    AlTeam.setError("If no teams, fill in Null");
                    AlTeam.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
                {
                    AlEmail.setError("Please enter a valid Email Address!");
                    AlEmail.requestFocus();
                    return;
                }
                if(Password.length()<6)
                {
                    AlPassword.setError("Password should be of Minimum 6 characters!..");
                    AlPassword.requestFocus();
                    return;
                }

                auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            Alumni alumni = new Alumni(Name,Email,Year,Branch,team);
                            FirebaseDatabase.getInstance().getReference("Alumni")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(alumni).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterAlumni.this,"User has been registered successfully",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(),HomePage.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(RegisterAlumni.this,"Failed to register! Try Again!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(RegisterAlumni.this,"Failed to Register!Try Again!",Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });


    }
}