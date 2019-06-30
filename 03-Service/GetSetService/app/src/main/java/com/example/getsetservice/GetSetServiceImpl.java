package com.example.getsetservice;

import android.util.Log;

public class GetSetServiceImpl extends com.example.getsetservice.IGetSetServicelInterface.Stub {
    private static String LOG_TAG = "GetSetServiceImpl";
    private int m_value = 0;

    public int getValue() {
        Log.d(LOG_TAG, String.format("-> getValue: %d", m_value));
        return m_value;
    }

    public void setValue(int newValue) {
        Log.d(LOG_TAG, String.format("-> setValue: %d", newValue));
        this.m_value = newValue;
    }
}
