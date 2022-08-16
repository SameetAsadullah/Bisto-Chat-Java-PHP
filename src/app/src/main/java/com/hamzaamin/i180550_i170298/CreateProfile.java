package com.hamzaamin.i180550_i170298;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateProfile extends AppCompatActivity {

    CircleImageView dp;
    Uri imgURI;
    Bitmap bitmap;
    Button back, post;
    EditText firstName, lastName, gender, phoneNo, aboutMe;
    String encodedImage;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        getWindow().setStatusBarColor(ContextCompat.getColor(CreateProfile.this, R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        Intent data = getIntent();
        String id = data.getStringExtra("id");
        String email = data.getStringExtra("email");

        back = findViewById(R.id.backbtn);
        firstName = findViewById(R.id.FirstName);
        lastName = findViewById(R.id.LastName);
        gender = findViewById(R.id.Gender);
        dp = findViewById(R.id.dp);
        post = findViewById(R.id.post);
        phoneNo = findViewById(R.id.phoneNo);
        aboutMe = findViewById(R.id.AboutMe);

        back.setOnClickListener(view -> login());

        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(CreateProfile.this)
                        .galleryOnly()
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)*/
                        //.saveDir(new File(getFilesDir(), "ImagePicker"))
                        .start();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String name = firstName.getText().toString() + " " + lastName.getText().toString();
                    String email = getIntent().getStringExtra("email").toString();
                    String password = getIntent().getStringExtra("password").toString();
                    String Gender = gender.getText().toString();
                    String phoneno = phoneNo.getText().toString();
                    String bio = aboutMe.getText().toString();
                    String url = Id.getIp()+"insert.php";
                    RequestQueue requestQueue = Volley.newRequestQueue(CreateProfile.this);
                    StringRequest request = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject x=new JSONObject(response);
                                        Id.setId(x.getString("id"));
                                        Id.setPath(x.getString("dp"));
                                        Toast.makeText(CreateProfile.this,"Data Inserted", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(CreateProfile.this,Dashboard.class);
                                        startActivity(intent);
                                        finish();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(CreateProfile.this,"Data not Inserted", Toast.LENGTH_SHORT).show();
                        }
                    }
                    ) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String,String>();
                            params.put("name",name);
                            params.put("email",email);
                            params.put("password",password);
                            params.put("gender",Gender);
                            params.put("phoneno", phoneno);
                            params.put("bio",bio);
                            params.put("dp",encodedImage);

                            return params;
                        }
                    };
                requestQueue.add(request);
            }
        });
    }

    protected void login() {
        startActivity(new Intent(CreateProfile.this, Login.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imgURI = data.getData();
        dp.setImageURI(imgURI);

        try {
            InputStream inputStream = getContentResolver().openInputStream(imgURI);
            bitmap = BitmapFactory.decodeStream(inputStream);


            imageStore(bitmap);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void imageStore(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);

        byte[] imageBytes = stream.toByteArray();

        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);

    }

}
