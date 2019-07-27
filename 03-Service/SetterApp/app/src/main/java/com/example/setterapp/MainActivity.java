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
import android.widget.CompoundButton;
import android.widget.Switch;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;

//import vendor.gl.ledcontrol.V1_0.Leds;

public class MainActivity extends AppCompatActivity {
    private static String LOG_TAG = "vvsSetterApp";
    private BroadcastReceiver mBroadReceiver = null;
    Switch mSwitchLed1 = null;
    Switch mSwitchLed2 = null;
    Switch mSwitchLed3 = null;
    Switch mSwitchLed4 = null;

    private void Log(String message) {
        Log.d(LOG_TAG, String.format(" -> %s", message));
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log("onPostResume()");

        IntentFilter filter = new IntentFilter("com.example.getsetservice.SET_VALUE_RESULT");
        mBroadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.hasExtra("value")) {
                    int value = intent.getIntExtra("value", 0);
                    Log(String.format("SetValue() method done: %d", value));
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

        Button setButton = findViewById(R.id.setValueBtn);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValueBtnOnClick();
            }
        });

        mSwitchLed1 = findViewById(R.id.ledSwitch1);
        mSwitchLed2 = findViewById(R.id.ledSwitch2);
        mSwitchLed3 = findViewById(R.id.ledSwitch3);
        mSwitchLed4 = findViewById(R.id.ledSwitch4);

        mSwitchLed1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                try {
                    Log(String.format("LED1 -> %s", isChecked ? "enabled" : "disabled"));
                    final String ledAction = isChecked ? "SET_LED" : "CLEAR_LED";
                    sendServiceActionWithExtraInt(ledAction, "led", /*Leds.LED_*/1);
                }
                catch(Exception ex) {   // formely catch (RemoteException ex)
                    Log(ex.toString());
                }
            }
        });
        mSwitchLed2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                try {
                    Log(String.format("LED2 -> %s", isChecked ? "enabled" : "disabled"));
                    final String ledAction = isChecked ? "SET_LED" : "CLEAR_LED";
                    sendServiceActionWithExtraInt(ledAction, "led", /*Leds.LED_*/2);
                }
                catch(Exception ex) {   // formely catch (RemoteException ex)
                    Log(ex.toString());
                }
            }
        });
        mSwitchLed3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                try {
                    Log(String.format("LED3 -> %s", isChecked ? "enabled" : "disabled"));
                    final String ledAction = isChecked ? "SET_LED" : "CLEAR_LED";
                    sendServiceActionWithExtraInt(ledAction, "led", /*Leds.LED_*/3);
                }
                catch(Exception ex) {   // formely catch (RemoteException ex)
                    Log(ex.toString());
                }
            }
        });
        mSwitchLed4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                try {
                    Log(String.format("LED4 -> %s", isChecked ? "enabled" : "disabled"));
                    final String ledAction = isChecked ? "SET_LED" : "CLEAR_LED";
                    sendServiceActionWithExtraInt(ledAction, "led", /*Leds.LED_*/4);
                }
                catch(Exception ex) {   // formely catch (RemoteException ex)
                    Log(ex.toString());
                }
            }
        });
    }

    private void setValueBtnOnClick() {
        Log("setValueBtnOnClick()");

        sendServiceActionWithExtraInt("SET_VALUE", "value", new Random().nextInt(100));
    }

    private void sendServiceActionWithExtraInt(String actionName, String extraName, int extraValue) {
        String packageName = "com.example.getsetservice";
        String className = packageName.concat(".").concat("GetSetService");

        Log("sendServiceActionWithExtraInt()");
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, className));
        intent.setAction( packageName.concat(".").concat(actionName));
        intent.setExtrasClassLoader(getClassLoader());
        intent.putExtra(extraName, extraValue);
        startForegroundService(intent);
    }
}
