package com.example.gsthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class SignIn extends AppCompatActivity {
    String TAG ;
    EditText mEmail,mPassword;
    Button mLogin;
    TextView mRegister,forgotPassword;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mEmail = findViewById(R.id.signinEmailAddress);
        mPassword = findViewById(R.id.signinPassword);
        mLogin = findViewById(R.id.signinButton);
        mRegister = (TextView) findViewById(R.id.createacc);
        forgotPassword = findViewById(R.id.resetPassword);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotPassword.class));
            }
        });
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email =  mEmail.getText().toString().trim();
                String Password = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(Email)){
                    mEmail.setError("Email Address Required..");
                    return;
                }
                if (TextUtils.isEmpty(Password)){
                    mPassword.setError("Password is Required..");
                    return;
                }
                if (Password.length() < 6){
                    mPassword.setError("Password must be Atleast 6 characters");
                    return;
                }
                fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user.isEmailVerified())
                            {
                                Toast.makeText(SignIn.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),HomePage.class));
                            }
                            else
                            {
                                user.sendEmailVerification();
                                Toast.makeText(SignIn.this,"Check Your Email to verify your account",Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(SignIn.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        if(user != null)
        {
            Intent i = new Intent(SignIn.this,HomePage.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
        else
        {
            Log.d(TAG,"onAuthStateChanged:signed_out");
        }
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateAccount.class));
            }
        });

    }
}

