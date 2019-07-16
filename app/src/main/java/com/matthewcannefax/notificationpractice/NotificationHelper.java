package com.matthewcannefax.notificationpractice;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper {
    private NotificationHelper(){}

    //id for the notification channel
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    //id for the notification
    private static final int NOTIFICATION_ID = 0;

    //update action string
    private static final String ACTION_UPDATE_NOTIFICATION = "com.matthewcannefax.notificationpractice.ACTION_UPDATE_NOTIFICATION";

    //initialize NotificationManager
    private static NotificationManager mNotificationManager;

    //get the update action string
    public static String getActionUpdateNotification(){return ACTION_UPDATE_NOTIFICATION;}

    public static void createNotificationChannel(Context context){
        //instantiate notification manager
        mNotificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        //check that we're working with the proper sdk version
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //create the notification channel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");

            //create the channel using the notification manager class
            //this is not a recursive call
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public static NotificationCompat.Builder getNotificationBuilder(Context context){

        //create an intent for the notification
        //on notification click the user will be directed to the mainactivity
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(context,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //build the notification using the builder
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        return notifyBuilder;
    }

    //this is called in the notify button click
    public static void sendNotification(Context context){

        //pending intent can only be used once
        //this is the intent for the button of the notification that updates the notification
        //in the onReceive method in NotificationReceiver class the notification updates
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        //create the builder using the getNotificationBuilder method
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(context);

        //this adds the button to the notification
        notifyBuilder.addAction(R.drawable.ic_action_name, "Update Notification", updatePendingIntent);

        //notify the notification that it has been updated
        mNotificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    public static void updateNotification(Context context){

        //convert the img file to a bitmap
        Bitmap androidIMG = BitmapFactory.decodeResource(context.getResources(), R.drawable.mascot_1);

        //create a notification builder with the img
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(context)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(androidIMG)
                        .setBigContentTitle("Notification Updated!"));


//        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(context)
//                .setStyle(new NotificationCompat.InboxStyle()
//                        .setBigContentTitle("Title").addLine("Here is the first one").addLine("This is the second one").addLine("Yay last one")
//                            .setSummaryText("Learn More"));


        //add the new settings to the current notification
        mNotificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    public static void cancelNotification(){
        mNotificationManager.cancel(NOTIFICATION_ID);
    }


}
