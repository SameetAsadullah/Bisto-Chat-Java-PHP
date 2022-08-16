package com.hamzaamin.i180550_i170298;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CallFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    RecyclerView rv;
    List<Profile> contactList;
    List<Profile> mutualContacts;
    ArrayList<String> arrayList;
    CallRVAdapter adapter;
    private final int ADD_CONTACT = 0;
    EditText searchView;
    CharSequence search ="";
    String senderId;
    SinchClient sinchClient;
    Call call;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CallFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CallFragment newInstance(int columnCount) {
        CallFragment fragment = new CallFragment();
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
        View view = inflater.inflate(R.layout.fragment_call, container, false);

        rv = view.findViewById(R.id.list);
        arrayList=new ArrayList<>();
        contactList=new ArrayList<>();
        mutualContacts=new ArrayList<>();

        // requesting to the user for permission.
        requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 100);
        // requesting to the user for permission.
        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // requesting to the user for permission.
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);

        } else {
            //if app already has permission this block will execute.
            readContacts();
        }

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // requesting to the user for permission.
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);

        } else {
            //if app already has permission this block will execute.

        }

        getAllUsers();

        RecyclerView.LayoutManager lm= new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        adapter=new CallRVAdapter(mutualContacts, getContext(), CallFragment.this);
        rv.setAdapter(adapter);

        SharedPreferences prefs = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        senderId = prefs.getString("id", "0");

        sinchClient = Sinch.getSinchClientBuilder().context(getContext())
                        .userId(senderId)
                        .applicationKey("b862a54e-e886-489d-9356-42970da89d44")
                        .applicationSecret("uXaIyKiXkUql3MBEdaTNew==")
                        .environmentHost("clientapi.sinch.com")
                        .build();

        sinchClient.setSupportCalling(true);
        sinchClient.startListeningOnActiveConnection();

        sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener() {

        });

        sinchClient.start();

        return view;
    }

    private class SinchCallListener implements CallListener {

        @Override
        public void onCallProgressing(com.sinch.android.rtc.calling.Call call) {

            Toast.makeText(getContext(), "Ringing", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCallEstablished(com.sinch.android.rtc.calling.Call call) {
            Toast.makeText(getContext(), "Call Established", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCallEnded(com.sinch.android.rtc.calling.Call endedCall) {
            Toast.makeText(getContext(), "Call Ended", Toast.LENGTH_LONG).show();
            call = null;
            endedCall.hangup();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> list) {

        }
    }

    private class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, com.sinch.android.rtc.calling.Call incomingcall) {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("CALLING");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Reject", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    call.hangup();
                }
            });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Pick", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    call = incomingcall;
                    call.answer();
                    call.addCallListener(new SinchCallListener());
                    Toast.makeText(getContext(),"Call is Started", Toast.LENGTH_LONG).show();
                }
            });
            alertDialog.show();
        }
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
                    adapter=new CallRVAdapter(mutualContacts, getContext(), CallFragment.this);
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

    public void callUser(Profile user) {
        if(call == null) {
            call = sinchClient.getCallClient().callUser(user.getId());
            call.addCallListener(new SinchCallListener());

            openCallerDialog(call);
        }
    }

    private void openCallerDialog(final Call call) {
        AlertDialog alertDialogCall = new AlertDialog.Builder(getContext()).create();
        alertDialogCall.setTitle("ALERT");
        alertDialogCall.setMessage("CALLING");
        alertDialogCall.setButton(AlertDialog.BUTTON_NEUTRAL, "Reject", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                call.hangup();
            }
        });
        alertDialogCall.show();
    }
}
