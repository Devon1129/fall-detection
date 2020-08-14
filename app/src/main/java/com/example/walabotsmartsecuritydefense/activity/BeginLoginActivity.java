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
import com.example.walabotsmartsecuritydefense.MainActivity;
import com.example.walabotsmartsecuritydefense.R;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import timber.log.Timber;

public class BeginLoginActivity extends AppCompatActivity {

    private Button mBeginLogin;
    private EditText mAccount, mPassword;

    private boolean isLogin = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_login);

        initial();

        mBeginLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //hannah_test
                ///Log.d("tttt", mAccount.getText().toString());
                ///Log.d("tttt", mPassword.getText().toString());

                ///判斷欄位是否為空
//                if (mAccount.getText().toString().matches("") ||
//                    mPassword.getText().toString().matches("")) {
//                    Toast.makeText(BeginLoginActivity.this,"帳號或密碼部不正確", Toast.LENGTH_LONG).show();
//                }else {

                String account = mAccount.getText().toString();
                String password = mPassword.getText().toString();

                final String urlApiSignin =
                        Application.urlSignin;
//                        Application.urlSignin + "username=" + account + "&" + "password=" + password;//hannah_test
//                        Application.urlSignin + "username=" + "xhwg85" + "&" + "password=" + "hwacom";//hannah_test

                //hannah_test
                account = "xhwg85";
                password = "hwacom";

                Log.d("tttt", "account: " + account + "; " + "password: " + password);
                getApitokenAsync(urlApiSignin, account, password);
//                getApitokenAsync();//hannah_test

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

//private void getApitokenAsync() {//hannah_test
    private void getApitokenAsync(String url, String username, String password) {
        Log.d("tttt", "getApitokenAsync: " + url + "username=" + username + "&" + "password=" + password);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "username=" + username + "&" + "password=" + password)
//                .url("http://60.248.34.228:81/walabot/api/signin.php?username=xhwg85&password=hwacom")//hannah_test
                .build();
        client.newCall(request).enqueue(new Callback() {
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
                    BeginLoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject json = new JSONObject(myResponse);
                                String strStatus = json.getString("status");
                                Log.d("tttt", "strStatus: " + strStatus);
                                if (strStatus.equals("Success")) {
                                    String apiToken = json.getString("apitoken");

                                    Log.d("tttt", "apiToken: " + apiToken);
                                    //範例
                                    //mAccount.setText(json.getJSONObject("data").getString("first_name")+ " "+json.getJSONObject("data").getString("last_name"));
                                    //mAccount.setText(apiToken);//hannah_test

                                    Timber.i("Signin API Result Status, Signin Success");

                                    Intent intent = new Intent(BeginLoginActivity.this, MainActivity.class);
                                    intent.putExtra("isLogin", isLogin);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.d("tttt", "signin failure!!!");

                                    Timber.i("Signin API Result Status, Signin Failure");

                                    Toast.makeText(BeginLoginActivity.this, "登入失敗，無效帳戶或密碼", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
}
