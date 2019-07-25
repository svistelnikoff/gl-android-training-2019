package com.example.getsetservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

//import androidx.annotation.NonNull;
import android.support.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;

import vendor.gl.ledcontrol.V1_0.ILedControl;
import vendor.gl.ledcontrol.V1_0.Leds;
import vendor.gl.ledcontrol.V1_0.LedState;

public class GetSetService extends Service {
    private static String LOG_TAG = "GetSetService";
    private GetSetServiceImpl mServiceImpl = null;
    private ILedControl ledControl = null;

    public GetSetService() {
        Log("GetSetService()");
    }

    @Override
    public void onCreate() {
        Log("onCreate()");
        mServiceImpl = new GetSetServiceImpl();
        notifyForeground();
        try {
            ledControl = ILedControl.getService(true);
        }
        catch(RemoteException ex) {
            Log(ex.toString());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            final String actionName = intent.getAction();
            Log("onStartCommand() action: ".concat(actionName));
            switch (actionName) {
                case "com.example.getsetservice.SET_VALUE":
                    if (intent.hasExtra("value")) {
                        int newValue = intent.getIntExtra("value", Integer.MIN_VALUE);
                        if (newValue != Integer.MIN_VALUE) {
                            Log(String.format("setValue( %d )", newValue));
                            mServiceImpl.setValue(newValue);
                            sendActionResult("com.example.getsetservice.SET_VALUE_RESULT");
                        }
                    } else {
                        Log(String.format("Nothing to do - missing \"value\" for %s", intent.getAction()));
                    }
                    break;
                case "com.example.getsetservice.GET_VALUE":
                    Log("getValue()");
                    sendActionResult("com.example.getsetservice.GET_VALUE_RESULT");
                    break;
                case "com.example.getsetservice.SET_LED":
                    processLedAction(intent, LedState.LED_STATE_ON);
                    break;
                case "com.example.getsetservice.CLEAR_LED":
                    processLedAction(intent, LedState.LED_STATE_OFF);
                    break;
                default:
                    Log(String.format("%s unsupported method call: %s", LOG_TAG, intent.getAction()));
                    break;
            }
        } catch (Exception ex) {
            Log(String.format("%s exception occured: %s", LOG_TAG, ex.toString()));
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log("onDestroy() ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log("onBind() " + intent.getAction());
        return mServiceImpl;
    }

    private void sendActionResult(String actionName) {
        Intent resultIntent = new Intent(actionName);
        resultIntent.putExtra("value", mServiceImpl.getValue());
        sendBroadcast(resultIntent);
    }

    private void processLedAction(Intent intent, byte state) {
        if (intent.hasExtra("led")) {
            int ledNumber = intent.getIntExtra("led", Integer.MIN_VALUE);
            if (ledNumber != Integer.MIN_VALUE) {
                try {
                    Log(String.format("Sel led %d to %d)", ledNumber, state));
                    ledControl.setLedState((byte)ledNumber, state );
                }
                catch( Exception ex) {
                    Log("Failed to set led.Exception: ".concat(ex.toString()));
                }
            }
        } else {
            Log(String.format("Nothing to do - missing \"value\" for %s", intent.getAction()));
        }

    }

    private void notifyForeground() {
        final String channelId = "getsetservice";
        NotificationChannel channel = new NotificationChannel(channelId, "Get/Set service", NotificationManager.IMPORTANCE_NONE);
        channel.setLightColor(Color.BLUE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        Notification notification = notificationBuilder.setOngoing(true).setSmallIcon(R.mipmap.ic_launcher).setPriority(Notification.PRIORITY_MIN).setCategory(Notification.CATEGORY_SERVICE).build();

        startForeground(9670, notification);
    }

    private void Log(@NonNull String message) {
        Log.d(LOG_TAG, String.format(" -> %s", message));
    }
}
