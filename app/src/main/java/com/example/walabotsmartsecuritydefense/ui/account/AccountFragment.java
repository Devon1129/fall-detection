package com.example.walabotsmartsecuritydefense.ui.account;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.walabotsmartsecuritydefense.BuildConfig;
import com.example.walabotsmartsecuritydefense.R;
import com.example.walabotsmartsecuritydefense.manager.PreferenceManager;

import static org.litepal.util.BaseUtility.capitalize;

public class AccountFragment extends Fragment {

    private String TAG = getClass().toString();

    private AccountViewModel accountViewModel;
    private TextView shoolName, account, name, appVersion, phoneModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        initial(root);
        setInformation();

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

    private void setInformation() {
        PreferenceManager preferenceManager = new PreferenceManager(getContext());

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
        String versionName = BuildConfig.VERSION_NAME;

        //Log.d("bbb1", "version: " + versionCode);
        Log.d(TAG, "versionName: " + versionName + "~~~");

        appVersion.setText(versionName);
    }


    private void initial(View root) {
        shoolName = (TextView)root.findViewById(R.id.tv_shool_name);
        account = (TextView)root.findViewById(R.id.tv_account);
        name = (TextView)root.findViewById(R.id.tv_name);
        appVersion = (TextView)root.findViewById(R.id.tv_app_version);
        phoneModel = (TextView)root.findViewById(R.id.tv_phone_model);
    }
}
