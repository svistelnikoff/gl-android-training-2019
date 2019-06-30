package com.example.setterapp;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static String LOG_TAG = "SetterApp";
    private com.example.setterapp.IGetSetServicelInterface mService;
    private BroadcastReceiver mBroadReceiver = null;

    private void Log(@NonNull String format, @NonNull Object... objects) {
        Log.d(LOG_TAG, "-> %s" + String.format(format, objects));
    }

    @Override
    protected void onPostResume() {
        Log("onPostResume()");
        super.onPostResume();

        IntentFilter filter = new IntentFilter("com.example.getsetservice.SET_VALUE_RESULT");
        mBroadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.hasExtra("value")) {
                    int value = intent.getIntExtra("value", 0);
                    Log("SetValue() method done: %d", value);
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
        Log("onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button setButton = findViewById(R.id.setValueBtn);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValueBtnOnClick();
            }
        });
    }

    private void setValueBtnOnClick() {
        Log("setValueBtnOnClick()");
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.getsetservice", "com.example.getsetservice.GetSetService"));
        intent.setAction("com.example.getsetservice.SET_VALUE");
        intent.setExtrasClassLoader(getClassLoader());
        intent.putExtra("value", new Random().nextInt(100));
        startForegroundService(intent);
    }
}
