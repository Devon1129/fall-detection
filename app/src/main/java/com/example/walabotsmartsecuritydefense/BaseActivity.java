package com.example.walabotsmartsecuritydefense;

import android.os.Bundle;


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

public class BaseActivity extends AppCompatActivity {
    protected CloudManager cloudManager;
    protected PreferenceManager preferenceManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cloudManager = new CloudManager(this);
        preferenceManager = new PreferenceManager(this);

    }

    //進入畫面時，先取得apitoken
    protected String getPreferApiToken() {
        return preferenceManager.getApiToken();
    }


}
