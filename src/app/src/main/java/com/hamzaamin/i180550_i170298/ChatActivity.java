package com.hamzaamin.i180550_i170298;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private ScreenShotContentObserver screenShotContentObserver;
    RecyclerView recyclerView;
    ImageButton selectImage,clearImage;
    Button send;
    View imagePreviewBackground;
    EditText messageContent;
    MessageRVAdapter adapter;
    Uri selectedImage=null;
    ImageButton backButton;
    Bitmap bitmap;
    ArrayList<Message> messagesList;
    TextView appbar_heading;
    ImageView receiverImage,imagePreview;
    String id;
    String encodedImage;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setStatusBarColor(ContextCompat.getColor(ChatActivity.this,R.color.black));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        Intent data=getIntent();
        id=data.getStringExtra("id");
        String receiverName=data.getStringExtra("profileName");
        String receiverImageUri = data.getStringExtra("image");
        // Populate dummy messages in List, you can implement your code here
        send=findViewById(R.id.send_button);
        selectImage=findViewById(R.id.select_image);
        messageContent=findViewById(R.id.message_content);
        backButton = findViewById(R.id.back_button);
        imagePreview = findViewById(R.id.image_preview);
        clearImage = findViewById(R.id.clear_image);
        imagePreviewBackground = findViewById(R.id.image_preview_bg);

        appbar_heading = findViewById(R.id.appbarHeading);
        receiverImage = findViewById(R.id.recImg);
        appbar_heading.setText(receiverName);
        Picasso.get().load(Id.getIp()+receiverImageUri).fit().centerCrop().into(receiverImage);

        messagesList = new ArrayList<>();

        System.out.println("BRUH"+Id.getId());

        HandlerThread handlerThread = new HandlerThread("content_observer");
        handlerThread.start();
        final Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
            }
        };


        screenShotContentObserver = new ScreenShotContentObserver(handler, this) {
            @Override
            protected void onScreenShot(String path, String fileName) throws FileNotFoundException {
                File file = new File(path); //this is the file of screenshot image
                Uri screenshot = Uri.fromFile(file);

                System.out.println("\n\n\n\nWorks works works\n\n\n");
                Toast.makeText(ChatActivity.this,"Screenshot detected. "+path,Toast.LENGTH_SHORT).show();

                //  clearImage();

                InputStream inputStream = getContentResolver().openInputStream(screenshot);
                bitmap = BitmapFactory.decodeStream(inputStream);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);

                byte[] imageBytes = stream.toByteArray();

                encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);

                PostMessage(new Message(Id.getId(), id, "Screenshot Taken!", encodedImage));
            }
        };


        getChat();

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,200);
            }
        });

        backButton.setOnClickListener(v -> {
            finish();
        });

        send.setOnClickListener(view -> {
            if(messageContent.getText().toString().equals("")){
                Toast.makeText(this, "Message field empty!", Toast.LENGTH_SHORT).show();
            }
            else{
                PostMessage(new Message(Id.getId(), id, messageContent.getText().toString(), encodedImage));
                //clearImage();
            }
        });

        clearImage.setOnClickListener(v -> {
            clearImage();
        });

    }



    @Override
    public void onResume() {
        super.onResume();

        getContentResolver().registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                true,
                screenShotContentObserver
        );
    }

    @Override
    public void onPause() {
        super.onPause();

        try {
            getContentResolver().unregisterContentObserver(screenShotContentObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getContentResolver().unregisterContentObserver(screenShotContentObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200 && resultCode==RESULT_OK){
            selectedImage=data.getData();
            imagePreview.setImageURI(selectedImage);
            imagePreview.setVisibility(View.VISIBLE);
            clearImage.setVisibility(View.VISIBLE);
            imagePreviewBackground.setVisibility(View.VISIBLE);

            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                bitmap = BitmapFactory.decodeStream(inputStream);


                imageStore(bitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void imageStore(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);

        byte[] imageBytes = stream.toByteArray();

        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    void PostMessage(Message message){

        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = Id.getIp()+"postMessage.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("reponse",response);
                getChat();
                clearImage();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("message error",error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData;
                MyData = new HashMap<String, String>();
                if(encodedImage==null){
                    MyData.put("senderId", message.getSenderId());
                    MyData.put("receiverId", message.getReceiverId());
                    MyData.put("text", message.getText());
                    MyData.put("timestamp", Long.toString(message.getTimestamp()));
                    //clearImage();
                }
                else{
                    MyData.put("senderId", message.getSenderId());
                    MyData.put("receiverId", message.getReceiverId());
                    MyData.put("text", message.getText());
                    MyData.put("timestamp", Long.toString(message.getTimestamp()));
                    MyData.put("image", encodedImage);
                }

                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }
    public void clearImage(){
        selectedImage= null;
        encodedImage=null;
        messageContent.setText(null);
        imagePreview.setImageURI(null);
        imagePreview.setVisibility(View.GONE);
        clearImage.setVisibility(View.GONE);
        imagePreviewBackground.setVisibility(View.GONE);
    }

    public void getChat(){
        ArrayList<Profile> temp=new ArrayList<>();
        messagesList.clear();
        RequestQueue queue= Volley.newRequestQueue(ChatActivity.this);
        String url=Id.getIp()+"getMessages.php?senderId="+Id.getId()+"&receiverId="+id;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONArray arr = new JSONArray(response);
                    for(int i=0;i<arr.length();i++) {
                        System.out.println("Count: "+i);
                        JSONObject object1=arr.getJSONObject(i);
                        System.out.println("\n\n\nHeyHeyHey\n\n\n");
                        messagesList.add(new Message(object1.getString("senderId"),object1.getString("receiverId"),object1.getString("text"), object1.getString("timestamp"),object1.getString("image")));
                    }
                    adapter = new MessageRVAdapter(ChatActivity.this, messagesList,id,Id.getId());
                    recyclerView = findViewById(R.id.rv);
                    LinearLayoutManager lm = new LinearLayoutManager(ChatActivity.this);
                    lm.setStackFromEnd(true);
                    recyclerView.setLayoutManager(lm);
                    recyclerView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChatActivity.this,"Error in c=vollleyy",Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(stringRequest);

    }
}