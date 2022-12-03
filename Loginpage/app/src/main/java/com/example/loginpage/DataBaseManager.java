package com.example.loginpage;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DataBaseManager {
    private Context context;

    public RequestQueue queue;

    public DataBaseManager(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

}
