package com.example.notifyservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.R.id.message;

// @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NLService extends NotificationListenerService {

    public static final String ACTION_STATUS_BROADCAST = "com.example.notifyservice.NLService_Status";

    private String TAG = this.getClass().getSimpleName();
    private NLServiceReceiver nlservicereciver;

    /**
     * The number of notifications added (since the service started)
     */
    private int nAdded = 0;
    /**
     * The number of notifications removed (since the service started)
     */
    private int nRemoved = 0;
    int temp = 5;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

    Date pdate = new Date(System.currentTimeMillis());

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //retrieving data from the received intent
        if(intent!=null) {}
        if (intent.hasExtra("command")) {
            Log.i("NLService", "Started for command '" + intent.getStringExtra("command"));
            broadcastStatus();
        } else if (intent.hasExtra("id")) {
            int id = intent.getIntExtra("id", 0);
            String message = intent.getStringExtra("msg");
            Log.i("NLService", "Requested to start explicitly - id : " + id + " message : " + message);
        }
        super.onStartCommand(intent, flags, startId);



        // NOTE: We return STICKY to prevent the automatic service termination
        return START_STICKY;
    }

    private void broadcastStatus() {
        Log.i("NLService", "Broadcasting status added(" + nAdded + ")/removed(" + nRemoved + ")");
        Intent i1 = new Intent(ACTION_STATUS_BROADCAST);
        i1.putExtra("serviceMessage", "Added: " + nAdded + " | Removed: " + nRemoved);

        LocalBroadcastManager.getInstance(this).sendBroadcast(i1);
        // sendBroadcast(i1);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("NLService", "NLService created!");
        nlservicereciver = new NLServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.notifyservice.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        registerReceiver(nlservicereciver, filter);
        Log.i("NLService", "NLService created!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nlservicereciver);
        Log.i("NLService", "NLService destroyed!");
    }


    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.i(TAG, "**********  onNotificationPosted");
        Log.i(TAG, "ID :" + sbn.getId() + "t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        Intent i = new Intent("com.example.notifyservice.NLService_Status");
        i.putExtra("notification_event", sbn.getPackageName() + "\n");
        i.putExtra("nAdded", nAdded);
        String packageName = sbn.getPackageName();

        LocalBroadcastManager.getInstance(this).sendBroadcast(i);

         nAdded++;
        //Count the hours that have passed
        Date dateTwo = null;

        dateTwo = new Date(System.currentTimeMillis());
        ;
       /* System.out.println("Date 1" + pdate + "date2" + dateTwo);*/
        long timeDiff = Math.abs(pdate.getTime() - dateTwo.getTime());
        int hours = (int) ((timeDiff / (1000 * 60 * 60)) % 24);
        System.out.println("Hours:" + hours);
        /*System.out.println("Hours" + hours);*/
        /*System.out.println("difference:" + timeDiff);   // difference: 0*/
        if (hours >= 0) {
            System.out.println("done");
            pdate = dateTwo;
            System.out.println("new currentdate" + pdate);

//            And then count the notifications
            if (nAdded > temp) {
                System.out.println("reached" + temp);
                temp = temp + 5;
                System.out.println(temp);

                System.out.println("This:" + packageName);
//            Create a notification
                sendNotification(sbn);
            }
        }

        broadcastStatus();
    }

    public void sendNotification(StatusBarNotification sbn) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setContentTitle("NotifyService")
                        .setContentText("Θα απαντήσετε σε μερικές ερωτήσεις;").setAutoCancel(true);
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("packname", sbn.getPackageName());
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);


// Sets an ID for the notification
        int mNotificationId = 001 + temp;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        System.out.println("called");
        nAdded--;
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG, "********** onNOtificationRemoved");
        Log.i(TAG, "ID :" + sbn.getId() + "t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        Intent i = new Intent("com.example.notify.NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event", "onNotificationRemoved :" + sbn.getPackageName() + "\n");

        sendBroadcast(i);

        nRemoved++;
        broadcastStatus();
    }

    class NLServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getStringExtra("command").equals("list")) {
                Intent i1 = new Intent("com.example.notify.NOTIFICATION_LISTENER_EXAMPLE");
                i1.putExtra("notification_event", "=====================");
                sendBroadcast(i1);
                int i = 1;
                for (StatusBarNotification sbn : NLService.this.getActiveNotifications()) {
                    Intent i2 = new Intent("com.example.notify.NOTIFICATION_LISTENER_EXAMPLE");
                    i2.putExtra("notification_event", i + " " + sbn.getPackageName() + "\n");
                    sendBroadcast(i2);
                    i++;
                }
                Intent i3 = new Intent("com.example.notify.NOTIFICATION_LISTENER_EXAMPLE");
                i3.putExtra("notification_event", "===== Notification List ====");
                sendBroadcast(i3);

            }

        }
    }

}
