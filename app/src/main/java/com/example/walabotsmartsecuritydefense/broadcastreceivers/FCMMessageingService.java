package com.example.walabotsmartsecuritydefense.broadcastreceivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
//import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
//import android.support.v4.app.NotificationCompat;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.walabotsmartsecuritydefense.MainActivity;
import com.example.walabotsmartsecuritydefense.R;
import com.example.walabotsmartsecuritydefense.manager.CloudManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.example.walabotsmartsecuritydefense.BaseActivity.saveGetPushToken;

/**
 * Created by Jerry on 4/27/2018.
 */

public class FCMMessageingService extends FirebaseMessagingService {

    private static final String TAG = "FCMMessageingService";

    Context context = this;
    CloudManager mCloudManager;
    private Handler mHandler;
    public static String fcmToken="";
    public static boolean isFristGetFCMToken = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        fcmToken = s;

        isFristGetFCMToken = true;
        if(isFristGetFCMToken) {
            fristGetFcmToken();
        }

        Log.d(TAG, "newToken: " + fcmToken);
    }

    public static String getFcmToken(){
        return fcmToken;
    }

    public static void fristGetFcmToken(){
        Log.d(TAG, "fristGetFcmToken: " + fcmToken);
        saveGetPushToken(fcmToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //Log.e(TAG, "onMessageReceived:" + remoteMessage.getFrom());
        Log.e(TAG, "remoteMessage.getNotification().getBody(): " + remoteMessage.getNotification().getBody());
        if(/*remoteMessage.getData().size() > 0 &&*/ remoteMessage.getNotification() != null) {
            //Log.e(TAG, "getNotification");
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        Log.e(TAG, "onMessageSent: " + s);
    }

    private void sendNotification(String messageBody) {
        Log.e(TAG, "sendNotification: " + messageBody);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "fcm_default_channel";
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "")
                        .setSmallIcon(R.mipmap.warning)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                        .setContentTitle("FCM Message")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
