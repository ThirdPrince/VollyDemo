package com.example.vollydemo;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyApplication extends Application {


    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    private static RequestQueue requestQueue ;

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue  = Volley.newRequestQueue(this);
    }
}
