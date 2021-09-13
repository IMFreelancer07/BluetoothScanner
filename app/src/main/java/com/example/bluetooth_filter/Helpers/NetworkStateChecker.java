package com.example.bluetooth_filter.Helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bluetooth_filter.Adapters.myDbAdapter;
import com.example.bluetooth_filter.MainActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkStateChecker extends BroadcastReceiver  {

    private Context context;
    private myDbAdapter db;
    localData signUpResponsesData;

    public NetworkStateChecker(Context applicationContext) {
        context = applicationContext;
    }



    @Override
    public void onReceive (Context context, Intent intent)
    {
        this.context = context;
        db = new myDbAdapter(context);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        //if there is a network
        if (activeNetwork != null) {
            //if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                //getting all the unsynced data
                Cursor cursor = db.getUnsyncedData();
                if (cursor.moveToFirst()) {
                    do {
                        //calling the method to save the unsynced data to MySQL
                        saveData(
                                cursor.getInt(cursor.getColumnIndex(myDbAdapter.UID)),
                                cursor.getString(cursor.getColumnIndex(myDbAdapter.TP)),
                                cursor.getString(cursor.getColumnIndex(myDbAdapter.RFID)),
                                cursor.getString(cursor.getColumnIndex(myDbAdapter.TIME))
                        );
                    } while (cursor.moveToNext());
                }
            }
        }
    }

    private void saveData (final int id, final String TP, final String Rfid, final String Time)
    {


      final  StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.sync_URL_Local,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("Error Response ",response);

                        try {
//                            String success = response("status");
//                            String message = obj.getString("data");
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");
                            String message = jsonObject.getString("data");

                            Log.d("JSON Response", String.valueOf(response));

                            if (success.equals("200")) {
                                //updating the status in sqlite
                                db.updateDataStatus(id, MainActivity.NAME_SYNCED_WITH_SERVER);

                                //sending the broadcast to refresh the list
                                context.sendBroadcast(new Intent(MainActivity.DATA_SAVED_BROADCAST));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        VolleyLog.d("Error", "Failed because of Error: " + error.getMessage());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                JSONObject data = new JSONObject();
                JSONObject parentData = new JSONObject();
                try {
//            data = new JSONObject().getJSONObject("data");
                    //input your API parameters
                    data.put("tp", TP);
                    data.put("rfid", Rfid);
                    data.put("time", Time);

//                    parentData.put("data",data);


                } catch (JSONException e) {
                    Log.i("Data Encoding Issue ", e.toString());
                    e.printStackTrace();

                }
                Gson gson = new Gson();
                String json = gson.toJson(data);
                params.put("data", json);
                // Map.Entry<String,String>
                Log.d("Parameter", params.toString());
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}
