package com.matthewcannefax.notificationpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    public NotificationReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {

        //when the button inside the notification is clicked the notification will be updated
        NotificationHelper.updateNotification(context);
    }


}
