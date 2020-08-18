package com.example.walabotsmartsecuritydefense.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.walabotsmartsecuritydefense.R;

import timber.log.Timber;

public class PreferenceManager {
    private Context context;

    private String mApiToken;
    private boolean mLoginStatus;
    private SharedPreferences pref;
    private static final String PREFS_GET_API_TOKEN_RESULT = "get_api_token_result";
    private static final String PREFS_GET_API_LOGIN_RESULT = "get_api_login_result";

    public PreferenceManager(@NonNull Context context) {
        this.context = context;
        String preferenceName = this.context.getResources().getString(R.string.shared_pref);
        this.pref = context.getSharedPreferences(preferenceName, 0);

    }

    /*
        about login
     */
    //about login
    public boolean getLoginStatus() {
        boolean loginStatus = pref.getBoolean(PREFS_GET_API_LOGIN_RESULT, mLoginStatus);
        Log.d("PreferenceManager", "get login status: " + loginStatus);

        return loginStatus;
    }

    //儲存login success.
    public void saveLoginStatus(boolean value) {
        String loginStatus = PREFS_GET_API_LOGIN_RESULT;
        pref.edit().putBoolean(loginStatus, value)
                .apply();
        mLoginStatus = value;
        Timber.d("saveLoginStatus:%s", value);
    }

    //重新進入app時，儲存local端資料
    public void setLoginStatus(boolean value) {
        Timber.d("setLoginStatus:%s", value);
        String loginStatus = PREFS_GET_API_LOGIN_RESULT;
        pref.edit().putBoolean(loginStatus, value)
                .apply();
    }


    /*
        about api token
     */
    //about api token
    public String getApiToken() {
        String apiToken = pref.getString(PREFS_GET_API_TOKEN_RESULT, mApiToken);
        Log.d("PreferenceManager", "get api token: " + apiToken);

        return apiToken;
    }

    //儲存呼叫的 api token
    public void saveApiToken(String value) {
        String keyToken = PREFS_GET_API_TOKEN_RESULT;

        pref.edit().putString(keyToken, value)
                .apply();
        mApiToken = value;
        Timber.d("saveApiToken:%s", value);
    }

    //重新進入app時，儲存local端資料
    public void setApiTokenResult(String token) {
        Timber.d("setApiTokenResult:%s", token);
        String keyToken = PREFS_GET_API_TOKEN_RESULT;

        pref.edit().putString(keyToken, token)
                .apply();
    }
}
