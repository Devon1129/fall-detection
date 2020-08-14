package com.example.walabotsmartsecuritydefense.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.walabotsmartsecuritydefense.MainActivity;
import com.example.walabotsmartsecuritydefense.R;

public class BeginLoginActivity extends AppCompatActivity {

    private TextView mbtnBeginLogin; //登入Button
    private EditText mAccount, mPassword;

    private boolean isLogin = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_login);

        initial();

        mbtnBeginLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Log.d("tttt", mAccount.getText().toString());
                Log.d("tttt", mPassword.getText().toString());

                if (mAccount.getText().toString().matches("") ||
                    mPassword.getText().toString().matches("")) {
                Toast.makeText(BeginLoginActivity.this,"帳號或密碼部不正確", Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(BeginLoginActivity.this, MainActivity.class);
                intent.putExtra("isLogin", isLogin);
                startActivity(intent);
                finish();
            }
            }
        });
    }

    private void initial() {
        mAccount = findViewById(R.id.et_account);
        mPassword = findViewById(R.id.et_password);
        mbtnBeginLogin = (TextView) findViewById(R.id.btn_begin_login);
    }
}
