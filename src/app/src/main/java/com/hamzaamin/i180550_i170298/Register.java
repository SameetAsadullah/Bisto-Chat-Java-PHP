package com.hamzaamin.i180550_i170298;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class Register extends AppCompatActivity {
    EditText email, pass, confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.EmailAddress);
        pass = findViewById(R.id.Password);
        confirmPass = findViewById(R.id.ConfirmPassword);
        Id.setIp("http://192.168.18.81/PHP_Files/");
        TextView login = findViewById(R.id.Login);
        login.setOnClickListener(view -> login());

        TextView register = findViewById(R.id.Signup);
        register.setOnClickListener(view -> register());
    }


    protected void register(){
        createProfile(email.getText().toString(),pass.getText().toString());
    }


    protected void login(){
        Intent signIn=new Intent(Register.this, Login.class);
        startActivity(signIn);
        finish();
    }

    protected void createProfile(String email, String password) {
        String url=Id.getIp()+"getProfileByEmail.php?email="+email;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    Log.d("length",Integer.toString(arr.length()));
                    if(arr.length()>0){
                        Toast.makeText(Register.this,"A user with same email exists,change email", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = new Intent(Register.this, CreateProfile.class);
                        intent.putExtra("email",email);
                        intent.putExtra("password",password);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Register.this,"Error in volley",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue= Volley.newRequestQueue(Register.this);
        queue.add(stringRequest);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}