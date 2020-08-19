package com.example.walabotsmartsecuritydefense;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.walabotsmartsecuritydefense.activity.BeginLoginActivity;
import com.example.walabotsmartsecuritydefense.manager.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import timber.log.Timber;

public class MainActivity extends BaseActivity {

    private String TAG = getClass().toString();

    private String apitoken;
    private boolean isLogined = false;

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
        Log.d(TAG, "loginStatus: " + loginStatus + "~~~");

        if (loginStatus) {
            isLogined = true;
        }else {
            isLogined = false;
        }

        Log.d(TAG, "isLogined: " + isLogined + "; " +
                "loginStatus: " + loginStatus + "~~~");

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

        if (!isLogined) {
            Intent intent_MobileVerify = new Intent(mContext, BeginLoginActivity.class);
            startActivity(intent_MobileVerify);
            finish();
        }else if(isLogined) {
            //獲取apitoken，並更新apitoken
            apitoken = preferenceManager.getApiToken();
            preferenceManager.setApiTokenResult(apitoken);
            Log.d(TAG, "apitoken: " + apitoken + "~~~");

            navView.setSelectedItemId(R.id.navigation_monitoring);
        }

    }

}
