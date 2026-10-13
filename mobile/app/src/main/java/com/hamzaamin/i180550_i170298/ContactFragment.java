package com.hamzaamin.i180550_i170298;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A fragment representing a list of Items.
 */
public class ContactFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    RecyclerView rv;
    Button nContact;
    List<Profile> contactList;
    List<Profile> mutualContacts;
    ArrayList<String> arrayList;
    ContactRVAdapter adapter;
    private final int ADD_CONTACT = 0;
    EditText searchView;
    CharSequence search ="";
    CircleImageView profileImage;
    String senderId;
    Context context;
    TextView name;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ContactFragment newInstance(int columnCount) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        nContact = view.findViewById(R.id.newContact);
        nContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), AddContact.class), ADD_CONTACT);
            }
        });




        rv = view.findViewById(R.id.list);
        arrayList=new ArrayList<>();
        contactList=new ArrayList<>();
        mutualContacts=new ArrayList<>();
        profileImage = view.findViewById(R.id.profilePicc);
        Picasso.get().load(Id.getIp()+Id.getDp()).fit().centerCrop().into(profileImage);
        name = view.findViewById(R.id.name);
        name.setText(Id.getName());

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // requesting to the user for permission.
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);

        } else {
            //if app already has permission this block will execute.
            readContacts();
        }

        getAllUsers();

        RecyclerView.LayoutManager lm= new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        adapter=new ContactRVAdapter(mutualContacts, getContext());
        rv.setAdapter(adapter);

        return view;
    }

    public void getAllUsers(){
        ArrayList<Profile> temp=new ArrayList<>();
        String url=Id.getIp()+"get.php";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    for(int i=0;i<arr.length();i++) {
                        JSONObject object1=arr.getJSONObject(i);
                        String id=object1.get("id").toString();
                        String email=object1.get("email").toString();
                        String name=object1.get("name").toString();
                        String gender=object1.get("gender").toString();
                        String phone=object1.get("phoneno").toString();
                        String bio=object1.get("bio").toString();
                        String dp=object1.get("dp").toString();
                        if(arrayList.contains(phone)){
                            Log.d("list i:",id);
                            Log.d("list i:",email);
                            Log.d("list i:",name);
                            Log.d("list i:",gender);
                            mutualContacts.add(new Profile(id,email,name,gender,phone,bio,dp));
                        }

                    }
                    RecyclerView.LayoutManager lm= new LinearLayoutManager(getContext());
                    rv.setLayoutManager(lm);
                    adapter=new ContactRVAdapter(mutualContacts, getContext());
                    rv.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Error in c=vollleyy",Toast.LENGTH_SHORT).show();
            }

        });

        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(stringRequest);

    }

    @Override
    public void onResume() {
        super.onResume();
        //list.clear();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        readContacts();
    }
    // function to read contacts using content resolver
    @SuppressLint("Range")
    private void readContacts() {
        ContentResolver contentResolver=getContext().getContentResolver();
        Cursor phones=contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        String number;
        while (phones.moveToNext())
        {
            number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replace("+92","0");
            arrayList.add(number);
//                Log.d("Hello",phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        }

        phones.close();
    }
}