package com.example.bluetooth_filter;


import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bluetooth_filter.Helpers.SessionHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private SessionHandler session;

    private Button submit;
    private EditText pin_pswrd;
    private ProgressDialog pDialog;
    String stringPassword;


    public static String sync_URL_Local = "http://192.168.0.103:8080/bt_sync/syncData.php";
    public static String login_URL = "http://192.168.0.103:8080/bt_sync/login.php";

//    public static String sync_URL_Local = "http://10.0.2.2:8080/bt_sync/syncData.php";
//    public static String login_URL = "http://10.0.2.2:8080/bt_sync/login.php";


    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;

    public static final String DATA_SAVED_BROADCAST = "datasaved";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        session = new SessionHandler(getApplicationContext());

        if(session.isLoggedIn()){
            sendUsertoTimingPoints();
        }

        setContentView(R.layout.activity_main);
        pin_pswrd = (EditText) findViewById(R.id.pin_pswrd);
        submit = (Button) findViewById(R.id.btnsubmit);
        session = new SessionHandler(getApplicationContext());

        Async async = new Async();
        async.execute();

    }

    private void sendUsertoTimingPoints ()
    {
        Intent mainIntent = new Intent(getApplicationContext(), timingpointsActivity.class);
        startActivity(mainIntent);
        finish();
    }

    /**
     * Display Progress bar while Logging in
     */

    private void displayLoader() {
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }




    //Will Work Login Hope So

    private void Login()
    {

        stringPassword = pin_pswrd.getText().toString().trim();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, login_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("anyText",response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");
                            String message = jsonObject.getString("data");


                            if(success.equals("200")){
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                displayLoader();
                                session.loginUser();
                                sendUsertoTimingPoints();


                            }
                            if(success.equals("400")){
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Registration Error !1"+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Registration Error !2"+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();

                params.put("password",stringPassword);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



public class Async extends AsyncTask {

    protected Object doInBackground (Object[] objects)
    {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });


        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }
}


}


