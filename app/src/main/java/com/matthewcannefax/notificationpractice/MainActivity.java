package com.matthewcannefax.notificationpractice;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //buttons
    private Button notifyBTN;
    private Button updateBTN;
    private Button cancelBTN;

    //current context
    private final Context mContext = this;

    //the broadcast receiver to handle the update button inside the notification itself
    private NotificationReceiver mReceiver = new NotificationReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiate the buttons
        notifyBTN = findViewById(R.id.notifyBTN);
        updateBTN = findViewById(R.id.updateMe);
        cancelBTN = findViewById(R.id.CancelMe);

        //set the click listeners for the buttons
        setNotifyBTN();
        setUpdateBTN();
        setCancelBTN();

        //create the notifcation channel
        NotificationHelper.createNotificationChannel(mContext);

        //which buttons are going to show
        setBTNState(true, false, false);

        //register the broadcast receiver to handle the in notification button
        registerReceiver(mReceiver, new IntentFilter(NotificationHelper.getActionUpdateNotification()));

    }

    @Override
    protected void onDestroy() {
        //unregister the broadcast receiever
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    //set the state of the buttons based on the current state of the notification
    private void setBTNState(boolean notify, boolean update, boolean cancel){
        notifyBTN.setEnabled(notify);
        updateBTN.setEnabled(update);
        cancelBTN.setEnabled(cancel);
    }

    //click listener for the notify button
    private void setNotifyBTN(){
        notifyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send the Notification
                NotificationHelper.sendNotification(mContext);

                //change the state of the buttons
                setBTNState(false, true, true);
            }
        });
    }

    //click listener for the update button
    private void setUpdateBTN(){
        updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update the notification
                NotificationHelper.updateNotification(mContext);

                //change the state of the buttons
                setBTNState(false, false, true);
            }
        });
    }

    //click listener for the cancel button
    private void setCancelBTN(){
        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cancel the notification
                NotificationHelper.cancelNotification();

                //change the state of the buttons
                setBTNState(true, false, false);
            }
        });
    }


}
