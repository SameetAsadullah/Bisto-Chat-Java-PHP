package com.hamzaamin.i180550_i170298;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONArray;
import org.json.JSONException;

public class Login extends AppCompatActivity {
    TextView register;
    EditText email, password;
    Button login;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        StatusBarUtil.setTranslucent(Login.this, 75);
        register = findViewById(R.id.Register);
        email = findViewById(R.id.EmailAddress);
        password = findViewById(R.id.Password);
        register.setOnClickListener(view -> register());
        login = findViewById(R.id.Login);
        Id.setIp("http://172.17.51.252/assignment4/");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email1=email.getText().toString().trim();
                final String password1=password.getText().toString().trim();
                if(email1.isEmpty()){
                    email.setError("Provide email id");
                    email.requestFocus();
                }

                else if(password1.isEmpty()){
                    password.setError("Provide password");
                    password.requestFocus();
                }

                else if(!email1.isEmpty() && !password1.isEmpty()){
                    String url=Id.getIp()+"login.php?email="+email.getText().toString()+"&password="+password.getText().toString();
                    StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray arr = new JSONArray(response);
                                Log.d("length",Integer.toString(arr.length()));
                                if(arr.length()>0){
                                    id = arr.getJSONObject(0).getString("id");
                                    String dp = arr.getJSONObject(0).getString("dp");
                                    Id.setDp(dp);
                                    Id.setName(arr.getJSONObject(0).getString("name"));
                                    Intent toHome=new Intent(Login.this,Dashboard.class);
                                    toHome.putExtra("id",id);
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("id",id);
                                    editor.apply(); // commit changes

                                    Id.setId(arr.getJSONObject(0).getString("id"));
                                    Id.setPath(arr.getJSONObject(0).getString("dp"));

                                    startActivity(toHome);
                                    finish();
                                }
                                else{
                                    Toast.makeText(Login.this,"Error in Login",Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Login.this,"Error in volley",Toast.LENGTH_LONG).show();
                        }
                    });
                    RequestQueue queue= Volley.newRequestQueue(Login.this);
                    queue.add(stringRequest);
                }
                else{
                    Toast.makeText(Login.this,"Error occurred",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    protected void register(){
        startActivity(new Intent(Login.this, Register.class));
    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    protected String getId() {
        return id;
    }
}