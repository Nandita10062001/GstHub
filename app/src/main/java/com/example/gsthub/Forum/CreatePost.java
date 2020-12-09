package com.example.gsthub.Forum;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gsthub.R;

public class CreatePost extends AppCompatActivity {
    ProgressDialog pd;
    private Button Post;
    /*EditText description;
    FirebaseDatabase db;
    DatabaseReference ref;
    FirebaseAuth auth;

    Calendar cal;
    String uid;
    String Time,Date;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Post = findViewById(R.id.postbtn);
        /*pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);

        int day = cal.get(Calendar.DAY_OF_MONTH);
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
        Date = day+"/"+month+"/"+year;



        FirebaseUser user=auth.getInstance().getCurrentUser();
        if(user == null)
        {
            startActivity(new Intent(CreatePost.this, SignIn.class));
        }else
        {
            uid=user.getUid();
        }


        description = findViewById(R.id.question);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("posts");

        try {
            Post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Query findName = db.getReference("Users").child(uid);
                    if (description.getText().length() == 0) {
                        Toast.makeText(CreatePost.this, "Ask a Question.", Toast.LENGTH_LONG).show();
                    } else {
                        findName.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    //ref.child(uid).child("FullName").setValue(snapshot.getValue(Data.class).FullName);
                                    ref.child(uid).child("Time").setValue(Time);
                                    ref.child(uid).child("Date").setValue(Date);
                                    Toast.makeText(CreatePost.this, "Your Post has been created successfully", Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(CreatePost.this, Forum.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Database Error occurred", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override

                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d("User", error.getMessage());
                            }
                        });
                    }
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        pd.dismiss();
    }*/
    }
}