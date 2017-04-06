package com.museum;

import android.app.Application;

import com.estimote.sdk.EstimoteSDK;

public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        EstimoteSDK.initialize(getApplicationContext(), "stickers-museum-nzw", "f0a3de73bd382567368ee225c5ac3ffc");
    }
}
