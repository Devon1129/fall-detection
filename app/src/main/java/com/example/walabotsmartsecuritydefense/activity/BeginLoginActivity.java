package com.example.walabotsmartsecuritydefense.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.walabotsmartsecuritydefense.Application;
import com.example.walabotsmartsecuritydefense.BaseActivity;
import com.example.walabotsmartsecuritydefense.MainActivity;
import com.example.walabotsmartsecuritydefense.R;
import com.example.walabotsmartsecuritydefense.manager.PreferenceManager;

import org.litepal.LitePal;

public class BeginLoginActivity extends BaseActivity {


    private String TAG = getClass().toString();

    private Button mBeginLogin;
    private EditText mAccount, mPassword;

    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch(msg.what){ //signinAsync
                case 1:
                    Log.d( "BeginLogin", "handler: case 1" );
                    Intent intent = new Intent(BeginLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                case 2:
                    Log.d( "BeginLogin", "handler: case 2" );

                    Toast.makeText(BeginLoginActivity.this,"帳密錯誤", Toast.LENGTH_LONG).show();
                    break;
            }
            Log.d( "BeginLogin", "handler: case" );

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_login);

        initial();

        preferenceManager = new PreferenceManager(this);

        mBeginLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

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

                    //to do:sharepreference save account&password
                    Log.d(TAG, "account: " + account + "; " + "password: " + password);


                    cloudManager.signinAsync(urlApiSignin, account, password, handler);
                    preferenceManager.saveAccount(account);
                    preferenceManager.savePassword(password);

                    boolean isLogin = preferenceManager.getLoginStatus();
                    Log.d(TAG, "isLogin: " + isLogin);
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
