package com.example.walabotsmartsecuritydefense;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.walabotsmartsecuritydefense.manager.CloudManager;
import com.example.walabotsmartsecuritydefense.manager.PreferenceManager;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import timber.log.Timber;

public class BaseActivity extends AppCompatActivity {

    private final static String PREFS_TOEKN = "prefs_token"; //push token


    protected CloudManager cloudManager;
    protected PreferenceManager preferenceManager;

    protected static SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cloudManager = new CloudManager(this);
        preferenceManager = new PreferenceManager(this);
        sharedPreferences = getSharedPreferences("com.example.walabotsmartsecuritydefense", Context.MODE_PRIVATE);

    }

    //進入畫面時，先取得apitoken
    protected String getPreferApiToken() {
        return preferenceManager.getApiToken();
    }




    public static String getPushToken() {
        Log.d("zzzz", sharedPreferences.getString(PREFS_TOEKN, ""));

        return sharedPreferences.getString(PREFS_TOEKN, "");
    }


    public static void setPushToken(String token) {
        String keyToken = PREFS_TOEKN;
//        String enKeyToken = encrypt(keyToken);
//        sharedPreferences.edit()
//                .putString(decrypt(enKeyToken), token)

        sharedPreferences.edit()
                .putString(keyToken, token)
                .apply();
    }

    //save first get of token form FCM
    public static void saveGetPushToken(String pushToken) {
        if(!pushToken.equals("") || pushToken != null) {
            setPushToken(pushToken);
            Timber.d("saveGetPushToken:%s", pushToken);
        }
    }


}
