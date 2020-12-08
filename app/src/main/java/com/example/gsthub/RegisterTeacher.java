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

public class RegisterTeacher extends AppCompatActivity {
    private EditText name, createPassword, qualifications, dept, email;
    private Button signUp;
    private TextView alreadyAcc;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_teacher);

        signUp = (Button) findViewById(R.id.signupButton);
        alreadyAcc = (TextView) findViewById(R.id.alreadyacc);
        email = findViewById(R.id.createEmailAddress);
        createPassword = findViewById(R.id.createPassword);
        name = findViewById(R.id.teacherName);
        dept = findViewById(R.id.teacherDepartment);
        qualifications = findViewById(R.id.teacherQualifications);

        auth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editEmail = email.getText().toString();
                String editPass = createPassword.getText().toString();
                String editName = name.getText().toString();
                String editQualifications = qualifications.getText().toString();
                String editDept = dept.getText().toString();

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
                if(editQualifications.isEmpty())
                {
                    email.setError("Please enter your qualifications!");
                    email.requestFocus();
                }
                if(editDept.isEmpty())
                {
                    email.setError("Please enter your department!");
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
                if(editEmail.isEmpty() && editPass.isEmpty() && editDept.isEmpty() && editName.isEmpty() && editQualifications.isEmpty())
                {
                    Toast.makeText(RegisterTeacher.this,"All the fields are empty!",Toast.LENGTH_SHORT).show();
                }

                else if(!(editEmail.isEmpty() && editPass.isEmpty()))
                {
                    auth.createUserWithEmailAndPassword(editEmail,editPass).addOnCompleteListener(RegisterTeacher.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(RegisterTeacher.this,"You have been Registered Successfully!",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), SignInAs.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(RegisterTeacher.this,"Something went Wrong! Please try again!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(RegisterTeacher.this,"Error Occurred!",Toast.LENGTH_LONG).show();
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