package com.example.gsthub;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Post extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 2;
    ImageButton imageAddButton;
    Button postBtn;

    private Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        imageAddButton = (ImageButton) findViewById(R.id.imageAddButton);
        postBtn = (Button) findViewById(R.id.postBtn);

        imageAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Posting...", Toast.LENGTH_LONG);
            }
        });
    }

        protected void onActivityResult ( int requestCode, int resultCode, Intent data){

            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
                uri = data.getData();
                imageAddButton.setImageURI(uri);
            }

        }

}
