package com.example.walabotsmartsecuritydefense.broadcastreceivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.example.walabotsmartsecuritydefense.manager.CloudManager;
import com.example.walabotsmartsecuritydefense.manager.PreferenceManager;

import java.util.List;

//import me.leolin.shortcutbadger.ShortcutBadger;

public class NotificationCopyReceiver extends BroadcastReceiver {

//    ModelManager mModelManager;
    CloudManager mCloudManager;
    PreferenceManager preferenceManager;
    private Handler mHandler;

    String RECEIVE = "com.parse.push.intent.RECEIVE";
    @Override
    public void onReceive(final Context context, Intent intent) {
        if(intent.getAction().equals(RECEIVE)) {
            mHandler = new Handler();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    mCloudManager = new CloudManager(context);
                    preferenceManager = new PreferenceManager(context);
//                    //更新通知Notification_Copy Table資料
//                    mCloudManager.updateNotificationCopyTableData(preferenceManager.getApiToken());
//                    mHandler.post(new Runnable() {
//                        public void run() {
//                            mModelManager = new ModelManager(context);
//                            List<Notification_Copy_model> list_Notification_Unread = mModelManager.getValidNotificationCopysByUnReadFlag();
//                            if (list_Notification_Unread != null && list_Notification_Unread.size() > 0) {
//                                ShortcutBadger.with(context).count(list_Notification_Unread.size());
//                            }
//                        }
//                    });
                }
            }).start();
        }
        /*
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PrivateMessage");
        query.whereEqualTo("recipient", ParseUser.getCurrentUser());
        query.whereNotEqualTo("isRead", true);
        query.countInBackground(new ParseUtils.ParseCountCallback(
                new ParseUtils.ParseCountCallback.ParseCountCallbackListener() {
                    @Override
                    public void done(int count, ParseException e) {
                        if (e == null) {
                            ShortcutBadger.with(context).count(count);
                        } else {

                        }
                    }
                }));
        */
    }
}
