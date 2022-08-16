package com.hamzaamin.i180550_i170298;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.dhaval2404.imagepicker.ImagePicker;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddContact extends AppCompatActivity {

    CircleImageView dp;
    Uri imgURI;

    Button back,uploadImg, post;
    EditText name, number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        back = findViewById(R.id.backbtn);
        name = findViewById(R.id.Name);
        number = findViewById(R.id.Number);
        dp = findViewById(R.id.dp);
        post = findViewById(R.id.post);



        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AddContact.this)
                        .galleryOnly()
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)*/
                        .start();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper helper = new DBHelper(AddContact.this);
                SQLiteDatabase database = helper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(MyContactContract.Contact._NAME, name.getText().toString());
                cv.put(MyContactContract.Contact._PHNO, number.getText().toString());
                cv.put(MyContactContract.Contact._IMAGE, imgURI.toString());
                database.insert(MyContactContract.Contact.TABLENAME, null, cv);
                database.close();
                helper.close();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imgURI = data.getData();
        dp.setImageURI(imgURI);
    }
}