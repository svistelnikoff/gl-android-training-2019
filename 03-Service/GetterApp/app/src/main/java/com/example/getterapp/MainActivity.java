package com.example.getterapp;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;u
import android.view.View;
import android.widget.Button;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static String LOG_TAG = "vvsGetterApp";
    private BroadcastReceiver mBroadReceiver = null;

    private void Log(@NonNull String format, @NonNull Object... objects) {
        Log.d(LOG_TAG, "-> " + String.format(format, objects));
    }
    private void Log(@NonNull String message) {
        Log.d(LOG_TAG, String.format(" -> %s", message));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log("onPostResume()");

        IntentFilter filter = new IntentFilter("com.example.getsetservice.GET_VALUE_RESULT");
        mBroadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.hasExtra("value")) {
                    int value = intent.getIntExtra("value", 0);
                    Log(String.format( "GetValue() method done: %d", value));
                }

            }
        };
        this.registerReceiver(mBroadReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log("onPause()");

        this.unregisterReceiver(mBroadReceiver);
        mBroadReceiver = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log("onCreate()");
        setContentView(R.layout.activity_main);

        Button getValueBtn = findViewById(R.id.getValueBtn);
        getValueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValueBtnOnClick();
            }
        });
    }

    private void getValueBtnOnClick() {
        Log("getValueBtnOnClick()");
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.getsetservice", "com.example.getsetservice.GetSetService"));
        intent.setAction("com.example.getsetservice.GET_VALUE");
        startForegroundService(intent);
    }
}
