package com.example.walabotsmartsecuritydefense;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.walabotsmartsecuritydefense.activity.BeginLoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private View mBottomNavigation;
    private boolean isLogined = false;

    static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        mContext = this;
        setContentView(R.layout.activity_main);


//        mBottomNavigation = findViewById(R.id.nav_view);
//        mBottomNavigation.setVisibility(View.INVISIBLE);

        Intent isLogin = this.getIntent();
        boolean loginSuccess = isLogin.getBooleanExtra("isLogin", false);

        if (loginSuccess) {
            isLogined = true;
        }

        if (!isLogined) {
            Intent intent_MobileVerify = new Intent(mContext, BeginLoginActivity.class);
            startActivity(intent_MobileVerify);
            finish();
        }


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_monitoring, R.id.navigation_notifications,
                R.id.navigation_even, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
