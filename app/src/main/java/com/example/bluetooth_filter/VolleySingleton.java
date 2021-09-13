package com.example.bluetooth_filter;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton extends Application{
    public static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;


    VolleySingleton(Context mCtx) {

        VolleySingleton.mCtx = getApplicationContext();
        mRequestQueue = getRequestQueue();

    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(mCtx);


        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.

            mRequestQueue = Volley.newRequestQueue(mCtx);

        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
