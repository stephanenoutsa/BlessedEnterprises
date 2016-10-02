package com.blessedenterprises.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.blessedenterprises.AdminPanel;
import com.blessedenterprises.Adverts;

public class MyService extends Service {
    Context context = this;

    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start ad activity
        Intent activity = new Intent(context, Adverts.class);
        activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(activity);

        stopSelf();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
