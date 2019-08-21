package com.example.mit.exiomstask.utils;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.mit.exiomstask.R;
import com.example.mit.exiomstask.activities.MainActivity;
import com.example.mit.exiomstask.services.CheckDistanceService;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class Constant {

    public static LatLng source;
    public static LatLng dest;

    public static final String SOURCE="Source";
    public static final String DESTINATION ="Destination";

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void findDistance(Context context) {
        float[] results = new float[1];
        Location.distanceBetween(source.latitude, source.longitude,
                dest.latitude, dest.longitude, results);

        float distance= results.length>0?results[0]:-1;
        if (distance<1000){
            createNotification(context);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void createNotification(Context context){


        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Distance")
                .setContentText("You are about to reach!")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.notify(1,builder.build());

    }


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void scheduleJob(Context context) {

        ComponentName serviceComponent = new ComponentName(context, CheckDistanceService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setPeriodic(6 *1000); // repeate after every 6 sec
//        builder.setMinimumLatency(6 * 10000); // wait at least
//        builder.setOverrideDeadline(18 * 10000); // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }

}
