package com.example.walabotsmartsecuritydefense.manager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.walabotsmartsecuritydefense.Application;
import com.example.walabotsmartsecuritydefense.MainActivity;
import com.example.walabotsmartsecuritydefense.activity.BeginLoginActivity;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import timber.log.Timber;

public class CloudManager {
    static String TAG = CloudManager.class.getName();
    private RequestQueue volleyQueue;

    //全域變數
    private Application gv;
    private final Gson gson = new Gson();
    private Context context;

    public CloudManager(@NonNull Context context) {
//        gv = (Application) context.getApplicationContext();
        volleyQueue = Volley.newRequestQueue(context);
        this.context = context;
    }

    //private void getApitokenAsync() {//hannah_test
    public void getApitokenAsync(String url, String username, String password) {
        Log.d("tttt", "getApitokenAsync: " + url + "username=" + username + "&" + "password=" + password);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "username=" + username + "&" + "password=" + password)
//                .url("http://60.248.34.228:81/walabot/api/signin.php?username=xhwg85&password=hwacom")//hannah_test
                .build();
        client.newCall(request).enqueue(new Callback() {

            PreferenceManager preferenceManager = new PreferenceManager(context);

            @Override
            public void onFailure(Request request, IOException e) {
                final String myRequest = request.body().toString();
                Log.d("tttt", "onFailure " + myRequest + " ; " + e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d("tttt", "myResponse: " + myResponse);
                if (response.isSuccessful()) {//回調的方法執行在子線程。

                    try {
                        JSONObject json = new JSONObject(myResponse);
                        String strStatus = json.getString("status");
                        Log.d("tttt", "strStatus: " + strStatus);
                        if (strStatus.equals("Success")) {
                            String getApiToken = json.getString("apitoken");


                            Log.d("tttt", "getApitokenAsync(): " + getApiToken);
                            preferenceManager.saveApiToken(getApiToken);
                            preferenceManager.saveLoginStatus(true);

                            //範例
                            //mAccount.setText(json.getJSONObject("data").getString("first_name")+ " "+json.getJSONObject("data").getString("last_name"));
                            //mAccount.setText(apiToken);//hannah_test

                            Timber.i("Signin API Result Status, Signin Success");

//                            Intent intent = new Intent(BeginLoginActivity.this, MainActivity.class);
//                            intent.putExtra("isLogin", isLogin);
//                            startActivity(intent);
//                            finish();

                        } else {
                            Log.d("tttt", "signin failure!!!");

                            Timber.i("Signin API Result Status, Signin Failure");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }









//                if (response.isSuccessful()) {//回調的方法執行在子線程。
//                    BeginLoginActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                JSONObject json = new JSONObject(myResponse);
//                                String strStatus = json.getString("status");
//                                Log.d("tttt", "strStatus: " + strStatus);
//                                if (strStatus.equals("Success")) {
//                                    String apiToken = json.getString("apitoken");
//
//                                    Log.d("tttt", "apiToken: " + apiToken);
//                                    //範例
//                                    //mAccount.setText(json.getJSONObject("data").getString("first_name")+ " "+json.getJSONObject("data").getString("last_name"));
//                                    //mAccount.setText(apiToken);//hannah_test
//
//                                    Timber.i("Signin API Result Status, Signin Success");
//
//                                    Intent intent = new Intent(BeginLoginActivity.this, MainActivity.class);
//                                    intent.putExtra("isLogin", isLogin);
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    Log.d("tttt", "signin failure!!!");
//
//                                    Timber.i("Signin API Result Status, Signin Failure");
//
//                                    Toast.makeText(BeginLoginActivity.this, "登入失敗，無效帳戶或密碼", Toast.LENGTH_LONG).show();
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
            }
        });
    }
}
