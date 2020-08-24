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
    private String mUserName;
    private String mShoolName;
    private String mRole;
    private String mAccount;
    private String mPassword;
    private Boolean mLoginStatus = false;
    private Boolean mHavePhsuToken = false;



    private SharedPreferences pref;
    private static final String PREFS_GET_FCM_PUSHTOKEN_RESULT = "get_fcm_pushtoken_result";
    private static final String PREFS_GET_API_TOKEN_RESULT = "get_api_token_result";
    private static final String PREFS_GET_API_LOGIN_RESULT = "get_api_login_result";
    private static final String PREFS_GET_API_LOGIN_NAME = "get_api_login_name";
    private static final String PREFS_GET_API_LOGIN_SHOOL = "get_api_login_shool";
    private static final String PREFS_GET_API_LOGIN_ROLE = "get_api_login_role";
    private static final String PREFS_GET_ACCOUNT = "get_account";
    private static final String PREFS_GET_PASSWORD = "get_password";

    public PreferenceManager(@NonNull Context context) {
        this.context = context;
        String preferenceName = this.context.getResources().getString(R.string.shared_pref);
        this.pref = context.getSharedPreferences(preferenceName, 0);

    }

    /*
       about Account
    */
    public String getAccount() {
        String account = pref.getString(PREFS_GET_ACCOUNT, mAccount);
        Log.d("PreferenceManager", "get login account: " + account);

        return account;
    }

    //儲存帳號名稱
    public void saveAccount(String account) {
        String myAccount = PREFS_GET_ACCOUNT;
        pref.edit().putString(myAccount, account)
                .apply();
        mAccount = account;
        Timber.d("saveAccount:%s", account);
    }

    /*
       about Password
    */
    public String getPassword() {
        String myPassword = pref.getString(PREFS_GET_PASSWORD, mPassword);
        Log.d("PreferenceManager", "get login password: " + myPassword);

        return myPassword;
    }

    //儲存密碼
    public void savePassword(String password) {
        String myPassword = PREFS_GET_PASSWORD;
        pref.edit().putString(myPassword, password)
                .apply();
        mPassword = password;
        Timber.d("savePassword:%s", password);
    }

     /*
        about shool
     */
    public String getShoolName() {
        String shoolName = pref.getString(PREFS_GET_API_LOGIN_SHOOL, mShoolName);
        Log.d("PreferenceManager", "get login shool Name: " + shoolName);

        return shoolName;
    }

    //儲存學校名稱
    public void saveShoolName(String shool) {
        String shoolName = PREFS_GET_API_LOGIN_SHOOL;
        pref.edit().putString(shoolName, shool)
                .apply();
        mShoolName = shool;
        Timber.d("saveShool:%s", shool);
    }

    /*
       about role
    */
    public String getRoleName() {
        String role = pref.getString(PREFS_GET_API_LOGIN_ROLE, mRole);
        Log.d("PreferenceManager", "get login role: " + role);

        return role;
    }

    //儲存角色
    public void saveRoleName(String role) {
        String roleName = PREFS_GET_API_LOGIN_ROLE;
        pref.edit().putString(roleName, role)
                .apply();
        mRole = role;
        Timber.d("saveRole:%s", role);
    }

    /*
        about user name
     */
    //about login
    public String getUserName() {
        String userName = pref.getString(PREFS_GET_API_LOGIN_NAME, mUserName);
        Log.d("PreferenceManager", "get login user Name: " + userName);

        return userName;
    }

    //儲存login success.
    public void saveUserName(String name) {
        String userName = PREFS_GET_API_LOGIN_NAME;
        pref.edit().putString(userName, name)
                .apply();
        mUserName = name;
        Timber.d("saveLoginStatus:%s", name);
    }

    //重新進入app時，儲存local端資料
    public void setUserName(String name) {
        Timber.d("setUserName:%s", name);
        String loginStatus = PREFS_GET_API_LOGIN_NAME;
        pref.edit().putString(loginStatus, name)
                .apply();
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
        about FCM token
     */
    //about FCM token.
    public boolean getPushtoken() {
        boolean havePushtoken = pref.getBoolean( PREFS_GET_FCM_PUSHTOKEN_RESULT, mHavePhsuToken);
        Log.d("PreferenceManager", "get FCM token status: " + havePushtoken);

        return havePushtoken;
    }

    //儲存FCM token value.
    public void savePushtoken(boolean value) {
        String pushtokenValue =  PREFS_GET_FCM_PUSHTOKEN_RESULT;
        pref.edit().putBoolean(pushtokenValue, value)
                .apply();
        mHavePhsuToken = value;
        Timber.d("savePushtoken:%s", value);
    }

    //重新進入app時，儲存local端資料
    public void setPushtoken(boolean value) {
        Timber.d("setPushtoken:%s", value);
        String pushtokenValue =  PREFS_GET_FCM_PUSHTOKEN_RESULT;
        pref.edit().putBoolean(pushtokenValue, value)
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
