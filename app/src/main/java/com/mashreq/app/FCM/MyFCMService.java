package com.mashreq.app.FCM;

/**
 * Created by ahmed on 7/16/2017.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mashreq.app.R;

public class MyFCMService extends FirebaseMessagingService {
    private String message="تم إضافه اعلان جديد";
    private String title="تم إضافه اعلان جديد";
    private LocalBroadcastManager broadcaster;
    Context context;
    private String action;
    private String userTypeId;
    private Intent intent;
    private int numMessages;



    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
    }
    @Override
    public void onNewToken(String mToken) {
        super.onNewToken(mToken);
        Log.e("TOKEN",mToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

          title = remoteMessage.getNotification().getTitle();
          message = remoteMessage.getNotification().getBody();
        sendNotification(message,title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            title = remoteMessage.getNotification().getTitle();
            message = remoteMessage.getNotification().getBody();
            sendNotification(message,title);
        }
    }
    private void sendNotification(String messageBody, String title) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo_sp)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }


}
