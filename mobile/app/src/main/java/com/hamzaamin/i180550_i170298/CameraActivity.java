package com.hamzaamin.i180550_i170298;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
    RecyclerView rv;
    ContactRVAdapter rvAdapter;
    List<Profile> list = new ArrayList<>();
    ImageView imageView;
    Uri imgURI;


    private final int ADD_CONTACT = 0;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        imageView = findViewById(R.id.takenImage);

        SharedPreferences pref = getSharedPreferences("UserData", Context.MODE_PRIVATE);




        rv = findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvAdapter = new ContactRVAdapter(list, this);
        rv.setAdapter(rvAdapter);

        ImagePicker.with(CameraActivity.this)
                .cameraOnly()
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)*/
                .saveDir(new File(getFilesDir(), "ImagePicker"))
                .start();
    }


    @SuppressLint("Range")
    void getData() {
        helper = new DBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] projection = new String[] {
                MyContactContract.Contact._NAME,
                MyContactContract.Contact._PHNO,
                MyContactContract.Contact._IMAGE
        };
        String sort = MyContactContract.Contact._NAME + " ASC";

        rvAdapter = new ContactRVAdapter(list, this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        list.clear();
        getData();
        imgURI = data.getData();
        imageView.setImageURI(imgURI);
    }
}