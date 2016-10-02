package com.blessedenterprises.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {

    Alarm alarm = new Alarm();

    public MyBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Start service
        Intent service = new Intent(context, MyService.class);
        context.startService(service);

        // Schedule next alarm
        alarm.startAlarm(context);
    }
}
