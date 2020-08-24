package com.example.walabotsmartsecuritydefense;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.walabotsmartsecuritydefense.activity.BeginLoginActivity;
import com.example.walabotsmartsecuritydefense.broadcastreceivers.FCMMessageingService;
import com.example.walabotsmartsecuritydefense.manager.PreferenceManager;
import com.example.walabotsmartsecuritydefense.table.Notification;
import com.example.walabotsmartsecuritydefense.table.monitoring.Device;
import com.example.walabotsmartsecuritydefense.table.monitoring.Room;
import com.example.walabotsmartsecuritydefense.table.monitoring.Zone;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.litepal.LitePal;

import timber.log.Timber;

public class MainActivity extends BaseActivity {

    private String TAG = getClass().toString();

    private String apitoken;
    private boolean isLogined = false;
    private boolean sendPushtoken = false;

    static Context mContext;
    private View mBottomNavigation;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        mContext = this;
        preferenceManager = new PreferenceManager(this);

        //獲取登入狀態
        boolean loginStatus = false;
        loginStatus = preferenceManager.getLoginStatus();
        preferenceManager.setLoginStatus(loginStatus);
        Log.d(TAG, "loginStatus: " + loginStatus);

        if (loginStatus) {
            isLogined = true;
            startFCMService();
        }else {
            isLogined = false;
        }

        Log.d(TAG, "isLogined: " + isLogined + "; " +
                "loginStatus: " + loginStatus);

        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_monitoring , R.id.navigation_history, R.id.navigation_notifications,
                R.id.navigation_announcement, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //LitePal.getDatabase();//建立資料庫//hannah_test

        Intent valueIntent = this.getIntent();
        String leaveAnnouncementContent = valueIntent.getStringExtra("announcement");

        if (!isLogined) {
            Intent intent_MobileVerify = new Intent(mContext, BeginLoginActivity.class);
            startActivity(intent_MobileVerify);
            finish();
        }else if(isLogined) {
            //獲取apitoken，並更新apitoken
            apitoken = preferenceManager.getApiToken();
            preferenceManager.setApiTokenResult(apitoken);
            Log.d(TAG, "apitoken: " + apitoken);

            navView.setSelectedItemId(R.id.navigation_monitoring);
        }

        if (leaveAnnouncementContent != null) {
            navView.setSelectedItemId(R.id.navigation_announcement);
        }


        //取得訊息佈告
        final String urlApiSys_note = Application.urlSys_note;
        cloudManager.sys_noteAsync(urlApiSys_note);

        //取得異常通知
        LitePal.deleteAll(Notification.class);
        final String urlApiSys_notification_copy= Application.urlSys_notification_copy;
        cloudManager.notification_copyAsync(urlApiSys_notification_copy, preferenceManager.getAccount());


    }

    private void startFCMService() {
        String regId = "";
        if(getPushToken().equals("")) {
            regId = FCMMessageingService.getFcmToken();
            Log.d(TAG, "startFCMService: getPushToken() is empty");
        }else {
            //如果上傳過，就不用在上傳
            sendPushtoken = preferenceManager.getPushtoken();
            Log.d("zzzz", "sendPushtoken: " + sendPushtoken);
            //to do:sendPushtoken 獲取的狀態不正確
//            if (!sendPushtoken) {
//                //上傳推播token
//                final String urlApiSys_pushtoken = Application.urlSys_pushtoken;
//                cloudManager.pushtokenAsync(urlApiSys_pushtoken, preferenceManager.getAccount());
//            }

            Log.d(TAG, "startFCMService: regId: " + getPushToken());
        }

    }

}
