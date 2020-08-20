package com.example.walabotsmartsecuritydefense.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.walabotsmartsecuritydefense.Application;
import com.example.walabotsmartsecuritydefense.BaseActivity;
import com.example.walabotsmartsecuritydefense.MainActivity;
import com.example.walabotsmartsecuritydefense.R;
import com.example.walabotsmartsecuritydefense.manager.PreferenceManager;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;

import timber.log.Timber;

public class BeginLoginActivity extends BaseActivity {


    private String TAG = getClass().toString();

    private Button mBeginLogin;
    private EditText mAccount, mPassword;

    private boolean isLogin = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_login);

        initial();

        preferenceManager = new PreferenceManager(this);

        mBeginLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //hannah_test
                ///Log.d("tttt", mAccount.getText().toString());
                ///Log.d("tttt", mPassword.getText().toString());
                LitePal.getDatabase();//建立資料庫

                ///判斷欄位是否為空
                if (mAccount.getText().toString().matches("") ||
                    mPassword.getText().toString().matches("")) {
                    Toast.makeText(BeginLoginActivity.this,"請填入帳號或密碼部", Toast.LENGTH_LONG).show();
                }else {

                    String account = mAccount.getText().toString().trim();
                    String password = mPassword.getText().toString().trim();

                    final String urlApiSignin = Application.urlSignin;
    //                        Application.urlSignin + "username=" + account + "&" + "password=" + password;//hannah_test
    //                        Application.urlSignin + "username=" + "xhwg85" + "&" + "password=" + "hwacom";//hannah_test

                    //hannah_test
                    //account = "xhwg85";
                    //password = "hwacom";

                    //to do:sharepreference save account&password
                    Log.d(TAG, "account: " + account + "; " + "password: " + password + "~~~");

                    //hannah_test
//                    boolean getApiLogin = cloudManager.getApitokenAsync(urlApiSignin, account, password);
                    cloudManager.getApitokenAsync(urlApiSignin, account, password);
                    preferenceManager.saveAccount(account);
                    preferenceManager.savePassword(password);

//                    Log.d("ttttt", "getApiLogin: " + getApiLogin);
//
//                    if (!getApiLogin) {
//                        Toast.makeText(BeginLoginActivity.this,"登入失敗", Toast.LENGTH_LONG).show();
//                    }

    //                String apitoken = preferenceManager.getApiToken();
    //                if (apitoken.equals("") | apitoken != null) {
    //                    cloudManager.getApitokenAsync(urlApiSignin, account, password);
    ////                    getApitokenAsync();//hannah_test
    //                }else {
    //
    //                }
    //
                    Log.d(TAG, "isLogin: " + isLogin + "~~~");
                    boolean isLogin = preferenceManager.getLoginStatus();
                    if (isLogin) {
                        Intent intent = new Intent(BeginLoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        //Toast.makeText(BeginLoginActivity.this,"查無此帳號", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void initial() {
        mAccount = findViewById(R.id.et_account);
        mPassword = findViewById(R.id.et_password);
        mBeginLogin = (Button) findViewById(R.id.btn_begin_login);
    }

    //hannah_test: test okhttp.
    //    public void getDatasync(){
//        String apitoken = "";
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String jsonData = "";
//
//                try {
//                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
//                    Request request = new Request.Builder()
////                            .url("http://www.baidu.com")//请求接口。如果需要传参拼接到接口后面。
//                            .url("http://60.248.34.228:81/walabot/api/signin.php?username=xhwg85&password=hwacom")
//                            .build();//创建Request 对象
//                    Response response = null;
//                    response = client.newCall(request).execute();//得到Response 对象
//                    if (response.isSuccessful()) {
//                        Log.d("kwwl","response.code()=="+response.code());
//                        Log.d("kwwl","response.message()=="+response.message());
//                        Log.d("kwwl","res=="+response.body().string());
//                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
//                        jsonData = response.body().string();
//                        //Timber.i(jsonData);
//
//
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }


}
