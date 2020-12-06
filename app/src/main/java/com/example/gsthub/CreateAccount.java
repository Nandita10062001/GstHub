package com.example.gsthub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import javax.crypto.KeyAgreement;

public class CreateAccount extends AppCompatActivity {
    private Button signUp;
    private TextView alreadyAcc;
    private EditText email, createPassword;
    private FirebaseAuth auth;
    private SignInButton signInButton;
    private GoogleSignInClient googleSignInClient;
    private int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        signUp = (Button) findViewById(R.id.signupButton);
        alreadyAcc = (TextView) findViewById(R.id.alreadyacc);
        email = findViewById(R.id.createEmailAddress);
        createPassword = findViewById(R.id.createPassword);
        auth = FirebaseAuth.getInstance();
        signInButton=findViewById(R.id.GoogleSignUp);

        GoogleSignInOptions googleSignInOptions= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editEmail = email.getText().toString();
                String editPass = createPassword.getText().toString();
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
                if(editEmail.isEmpty() && editPass.isEmpty())
                {
                    Toast.makeText(CreateAccount.this,"Both the fields are empty!",Toast.LENGTH_SHORT).show();
                }
                else if(!(editEmail.isEmpty() && editPass.isEmpty()))
                {
                    auth.createUserWithEmailAndPassword(editEmail,editPass).addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(CreateAccount.this,"You have been Registered Successfully!",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), SignInAs.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(CreateAccount.this,"Something went Wrong! Please try again!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(CreateAccount.this,"Error Occurred!",Toast.LENGTH_LONG).show();
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
 private void signIn()
 {
     Intent signInIntent = googleSignInClient.getSignInIntent();
     startActivityForResult(signInIntent,RC_SIGN_IN);
 }

 @Override
 protected void onActivityResult(int requestCode,int resultCode, @Nullable Intent data)
 {
     super.onActivityResult(requestCode,resultCode,data);
     if(requestCode == RC_SIGN_IN)
     {
         Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
         handleSignInResult(task);
     }
 }

 private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
 {
     try {
         GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
         Toast.makeText(CreateAccount.this,"Signed In Successfully!",Toast.LENGTH_SHORT).show();
         FirebaseGoogleAuth(acc);
     }
     catch (ApiException e)
     {
         Toast.makeText(CreateAccount.this,"Sign In Unsuccessful!",Toast.LENGTH_SHORT).show();
         FirebaseGoogleAuth(null);
     }
 }
 private void FirebaseGoogleAuth(GoogleSignInAccount acct)
 {
     AuthCredential authc= GoogleAuthProvider.getCredential(acct.getIdToken(),null);
     auth.signInWithCredential(authc).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
             if(task.isSuccessful())
             {
                 startActivity(new Intent(getApplicationContext(),SignInAs.class));
                 Toast.makeText(CreateAccount.this,"Successful",Toast.LENGTH_SHORT).show();
                 FirebaseUser user = auth.getCurrentUser();
                 updateUI(user);
             }
             else
             {
                 Toast.makeText(CreateAccount.this,"Failed!",Toast.LENGTH_SHORT).show();
                 updateUI(null);
             }
         }
     });
 }
 private void updateUI(FirebaseUser fUser)
 {
     GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
     if(account != null)
     {
         String name = account.getDisplayName();
         Toast.makeText(CreateAccount.this,"Welcome "+name+"!",Toast.LENGTH_SHORT).show();
     }
 }
}

