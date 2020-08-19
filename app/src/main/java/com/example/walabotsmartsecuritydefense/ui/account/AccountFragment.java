package com.example.walabotsmartsecuritydefense.ui.account;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.walabotsmartsecuritydefense.Application;
import com.example.walabotsmartsecuritydefense.BuildConfig;
import com.example.walabotsmartsecuritydefense.MainActivity;
import com.example.walabotsmartsecuritydefense.R;
import com.example.walabotsmartsecuritydefense.manager.CloudManager;
import com.example.walabotsmartsecuritydefense.manager.PreferenceManager;

import static org.litepal.util.BaseUtility.capitalize;

public class AccountFragment extends Fragment {

    private String TAG = getClass().toString();

    private AccountViewModel accountViewModel;
    private TextView shoolName, account, name, appVersion, osSystemVersion, phoneModel;
    private Button logout;

    protected CloudManager cloudManager;
    protected PreferenceManager preferenceManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cloudManager = new CloudManager(getContext());
        preferenceManager = new PreferenceManager(getContext());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        initial(root);
        showInformation();

        //登出button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String urlApiSignout = Application.urlSignout;

                cloudManager.logoutAsync(urlApiSignout, preferenceManager.getAccount(), preferenceManager.getPassword());

                boolean isLogin = preferenceManager.getLoginStatus();
                Log.d("ttttt", "isLogin: " + isLogin);

                //將登入狀態，儲存為登出狀態
                if (isLogin) {
                    preferenceManager.setLoginStatus(false);

                    Log.d("ttttt", "click logout button: " + "login status: " +
                            preferenceManager.getLoginStatus());
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    //intent.putExtra("checkLogin", isLogin);
                    startActivity(intent);
                }
            }
        });

        //hannah_test
        final TextView textView = root.findViewById(R.id.text_account);
        accountViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    private void showInformation() {
        phoneModel.setText(getDeviceName());
        getAppVersion();

        name.setText(preferenceManager.getUserName());
        shoolName.setText(preferenceManager.getShoolName());
        account.setText(preferenceManager.getAccount());
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            Log.d(TAG, capitalize(model) + "~~~");
            return capitalize(model);
        } else {
            Log.d(TAG, capitalize(manufacturer) + " " + model + "~~~");
            return capitalize(manufacturer) + " " + model;
        }
    }

    public void getAppVersion() {
        //int versionCode = BuildConfig.VERSION_CODE;
        //app版號
        String versionName = BuildConfig.VERSION_NAME;
        //Log.d(TAG, "version: " + versionCode);
        Log.d(TAG, "versionName: " + versionName + "~~~");


        //android 手機，作業系統版號
        String osSystem = Build.VERSION.RELEASE;
        Log.d(TAG, "ttttt: " + osSystem );

        appVersion.setText(versionName);
        osSystemVersion.setText("Android " + osSystem);
    }

    private void initial(View root) {
        shoolName = (TextView)root.findViewById(R.id.tv_shool_name);
        account = (TextView)root.findViewById(R.id.tv_account);
        name = (TextView)root.findViewById(R.id.tv_name);
        appVersion = (TextView)root.findViewById(R.id.tv_app_version);
        osSystemVersion = (TextView)root.findViewById(R.id.tv_os_system_version);
        phoneModel = (TextView)root.findViewById(R.id.tv_phone_model);
        logout = (Button) root.findViewById(R.id.logout);
    }
}
