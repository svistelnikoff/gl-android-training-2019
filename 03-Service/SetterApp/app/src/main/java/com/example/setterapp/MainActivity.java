package com.example.setterapp;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import vendor.gl.ledcontrol.V1_0.ILedControl;
import vendor.gl.ledcontrol.V1_0.Leds;
import vendor.gl.ledcontrol.V1_0.LedState;

public class MainActivity extends AppCompatActivity {
    private static String LOG_TAG = "SetterApp";
    private BroadcastReceiver mBroadReceiver = null;
    Switch mSwitchLed1 = null;
    Switch mSwitchLed2 = null;
    Switch mSwitchLed3 = null;
    Switch mSwitchLed4 = null;
    ILedControl ledControl = null;

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

        try {
            ledControl = ILedControl.getService(true);
        }
        catch(RemoteException ex) {
            Log(ex.toString());
        }

        mSwitchLed1 = findViewById(R.id.ledSwitch1);
        mSwitchLed2 = findViewById(R.id.ledSwitch2);
        mSwitchLed3 = findViewById(R.id.ledSwitch3);
        mSwitchLed4 = findViewById(R.id.ledSwitch4);

        mSwitchLed1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                try {
                    Log(String.format("LED1 -> %s", isChecked ? "enabled" : "disabled"));
                    ledControl.setLedState(Leds.LED_1, isChecked ? LedState.LED_STATE_ON : LedState.LED_STATE_OFF);
                }
                catch(RemoteException ex) {
                    Log(ex.toString());
                }
            }
        });
        mSwitchLed2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                try {
                    Log(String.format("LED2 -> %s", isChecked ? "enabled" : "disabled"));
                    ledControl.setLedState(Leds.LED_2, isChecked ? LedState.LED_STATE_ON : LedState.LED_STATE_OFF);
                }
                catch (RemoteException ex) {
                    Log(ex.toString());
                }
            }
        });
        mSwitchLed3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                try {
                    Log(String.format("LED3 -> %s", isChecked ? "enabled" : "disabled"));
                    ledControl.setLedState(Leds.LED_3, isChecked ? LedState.LED_STATE_ON : LedState.LED_STATE_OFF);
                }
                catch (RemoteException ex) {
                    Log(ex.toString());
                }
            }
        });
        mSwitchLed4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                try {
                    Log(String.format("LED4 -> %s", isChecked ? "enabled" : "disabled"));
                    ledControl.setLedState(Leds.LED_4, isChecked ? LedState.LED_STATE_ON : LedState.LED_STATE_OFF);
                }
                catch (RemoteException ex) {
                    Log(ex.toString());
                }
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

    private void sendActionWithExtra(String actionName, String extraName, Object extraValue) {
        Log("sendActionWithExtra()");
    }
}
