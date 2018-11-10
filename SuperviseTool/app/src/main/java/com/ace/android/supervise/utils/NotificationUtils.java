package com.ace.android.supervise.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.ace.android.supervise.application.MyApplication;


/**
 *Created by ace on 2018/10/23.
 */

public class NotificationUtils {

    private NotificationUtils() {
        //no instance
    }

    public static Notification buildNotification(Context context, int smallIcon, String contentTitle,
                                                 String contentText, boolean autoCancel, PendingIntent intent) {
        return buildNotification(context, smallIcon, contentTitle, contentText, autoCancel, 0, intent);
    }

    public static Notification buildNotification(Context context, int smallIcon, String contentTitle,
                                                 String contentText, boolean autoCancel, int defaultId, PendingIntent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        return builder.setSmallIcon(smallIcon).setContentTitle(contentTitle)
                .setContentText(contentText).setContentIntent(intent)
                .setDefaults(defaultId)
                .setAutoCancel(autoCancel).build();
    }

    public static void sendNotification(Context context, int smallIcon, String contentTitle, String contentText,
                                        boolean autoCancel, int defaultId, PendingIntent intent, int notificationId) {
        Notification notification = NotificationUtils.buildNotification(context, smallIcon, contentTitle,
                contentText, autoCancel, defaultId, intent);
        NotificationManager manager = (NotificationManager) MyApplication.getApplicationContext(context)
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notificationId, notification);
    }

}
