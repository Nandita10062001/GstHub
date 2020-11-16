package com.example.gsthub.createProfile;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Bundle;

import com.example.gsthub.R;
import com.example.gsthub.SignIn;

public class CreateProfileGuest extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile_guest);

        Spinner guestType = (Spinner) findViewById(R.id.guestType);


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CreateProfileGuest.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.guest_array));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        guestType.setAdapter(myAdapter);
    }

}