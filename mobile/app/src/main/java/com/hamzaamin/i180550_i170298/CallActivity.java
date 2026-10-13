package com.hamzaamin.i180550_i170298;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class CallActivity extends AppCompatActivity {
    CircleImageView profile;
    TextView text;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        Intent data= getIntent();

        String uriString = data.getStringExtra("img");
        String name = data.getStringExtra("name");
        Uri uri = Uri.parse(uriString);

        profile = findViewById(R.id.profileImage);
        text = findViewById(R.id.CallerName);
        fab = findViewById(R.id.endCall);

        profile.setImageURI(uri);
        text.setText(name);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}