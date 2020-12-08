package com.example.gsthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gsthub.createProfile.CreateProfileGuest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterGuest extends AppCompatActivity {
    private EditText name, contact, createPassword, email;
    private Button signUp;
    private Spinner guestType;
    private TextView alreadyAcc, guestTypeOf;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_guest);



        //Spinner's backend code
        Spinner guestType = (Spinner) findViewById(R.id.guestType);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(RegisterGuest.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.guest_array));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        guestType.setAdapter(myAdapter);

        signUp = (Button) findViewById(R.id.signupButton);
        alreadyAcc = (TextView) findViewById(R.id.alreadyacc);
        email = findViewById(R.id.createEmailAddress);
        createPassword = findViewById(R.id.createPassword);
        name = findViewById(R.id.guestName);
        contact = findViewById(R.id.guestNumber);

        auth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editEmail = email.getText().toString();
                String editPass = createPassword.getText().toString();
                String editName = name.getText().toString();
                String editContact = contact.getText().toString();

                if(editEmail.isEmpty())
                {
                    email.setError("Please enter your emailId!");
                    email.requestFocus();
                }
                if(editPass.isEmpty())
                {
                    createPassword.setError("Please create your Password!");
                    createPassword.requestFocus();
                }
                if(editName.isEmpty())
                {
                    email.setError("Please enter your name!");
                    email.requestFocus();
                }
                if(editContact.isEmpty())
                {
                    email.setError("Please enter your contact number!");
                    email.requestFocus();
                }

                else if(!Patterns.EMAIL_ADDRESS.matcher(editEmail).matches())
                {
                    email.setError("Please Provide a Valid Email!");
                    email.requestFocus();
                }
                else if(editPass.length()<6)
                {
                    createPassword.setError("Password should be of minimum 6 characters!");
                    createPassword.requestFocus();
                }
                if(editEmail.isEmpty() && editPass.isEmpty() && editContact.isEmpty() && editName.isEmpty() )
                {
                    Toast.makeText(RegisterGuest.this,"All the fields are empty!",Toast.LENGTH_SHORT).show();
                }

                else if(!(editEmail.isEmpty() && editPass.isEmpty()))
                {
                    auth.createUserWithEmailAndPassword(editEmail,editPass).addOnCompleteListener(RegisterGuest.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(RegisterGuest.this,"You have been Registered Successfully!",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), SignInAs.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(RegisterGuest.this,"Something went Wrong! Please try again!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(RegisterGuest.this,"Error Occurred!",Toast.LENGTH_LONG).show();
                }

            }
        });
        alreadyAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
            }
        });
    }
}