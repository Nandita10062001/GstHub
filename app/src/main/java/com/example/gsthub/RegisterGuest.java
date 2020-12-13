package com.example.gsthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RegisterGuest extends AppCompatActivity {
    private EditText GuName,GuEmail,GuPassword,GuContact;
    private Button SignUp;
    private TextView AlreadyAcc;
    private FirebaseAuth auth;
    private EditText GuGuestType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_guest);

        GuName=findViewById(R.id.GuestName);
        GuEmail=findViewById(R.id.GuestEmail);
        GuPassword=findViewById(R.id.GuestPassword);
        GuContact=findViewById(R.id.GuestContact);
        GuGuestType =findViewById(R.id.GuestType);
        SignUp=findViewById(R.id.guestsignUpButton);
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
                final String Name = GuName.getText().toString().trim();
                final String Email = GuEmail.getText().toString().trim();
                final String Password = GuPassword.getText().toString().trim();
                final String Contact = GuContact.getText().toString().trim();
                final String GuestType = GuGuestType.getText().toString().trim();



                if(Name.isEmpty())
                {
                    GuName.setError("Please fill in  your Name!");
                    GuName.requestFocus();
                    return;
                }
                if(Email.isEmpty())
                {
                    GuEmail.setError("Enter your Email!");
                    GuEmail.requestFocus();
                    return;
                }
                if(Password.isEmpty())
                {
                    GuPassword.setError("Create a Strong Password!");
                    GuPassword.requestFocus();
                    return;
                }
                if(Contact.isEmpty())
                {
                    GuContact.setError("Mention Your Phone number!");
                    GuContact.requestFocus();
                    return;
                }
               if(Contact.length()!=10)
               {
                   GuContact.setError("Please enter a valid contact number!");
                   GuContact.requestFocus();
               }

                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
                {
                    GuEmail.setError("Please enter a valid Email Address!");
                    GuEmail.requestFocus();
                    return;
                }
                if(Password.length()<6)
                {
                    GuPassword.setError("Password should be of Minimum 6 characters!..");
                    GuPassword.requestFocus();
                    return;
                }
            if(GuestType.isEmpty())
            {
                GuGuestType.setError("Mention who you are!");
                GuGuestType.requestFocus();
            }
                auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Guest guest = new Guest(Name,Email,Contact,GuestType);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(guest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterGuest.this,"User has been registered successfully",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(),HomePage.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(RegisterGuest.this,"Failed to register! Try Again!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(RegisterGuest.this,"Failed to Register!Try Again!",Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });


    }


}