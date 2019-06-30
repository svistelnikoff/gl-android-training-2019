package com.example.getsetservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class GetSetService extends Service {
    private static String LOG_TAG = "GetSetService";
    private GetSetServiceImpl mServiceImpl = null;

    public GetSetService() {
        Log("GetSetService()");
    }

    @Override
    public void onCreate() {
        Log("onCreate()");
        mServiceImpl = new GetSetServiceImpl();
        notifyForeground();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log("onStartCommand()");
        try {
            switch (intent.getAction()) {
                case "com.example.getsetservice.SET_VALUE":
                    if (intent.hasExtra("value")) {
                        int newValue = intent.getIntExtra("value", Integer.MIN_VALUE);
                        if (newValue != Integer.MIN_VALUE) {
                            Log("setValue( %d )", newValue);
                            mServiceImpl.setValue(newValue);
                            sendActionResult("com.example.getsetservice.SET_VALUE_RESULT");
                        }
                    } else {
                        Log("Nothing to do - missing \"value\" for %s", intent.getAction());
                    }
                    break;
                case "com.example.getsetservice.GET_VALUE":
                    Log("getValue()");
                    sendActionResult("com.example.getsetservice.GET_VALUE_RESULT");
                    break;
                default:
                    Log("%s unsupported method call: %s", LOG_TAG, intent.getAction());
                    break;
            }
        } catch (Exception ex) {
            Log("%s exception occured: %s", LOG_TAG, ex.toString());
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

    private void Log(@NonNull String format, @NonNull Object... objects) {
        Log.d(LOG_TAG, "-> " + String.format(format, objects));
    }
}
