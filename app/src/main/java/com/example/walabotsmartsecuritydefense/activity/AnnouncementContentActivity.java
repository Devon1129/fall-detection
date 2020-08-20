package com.example.walabotsmartsecuritydefense.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.walabotsmartsecuritydefense.MainActivity;
import com.example.walabotsmartsecuritydefense.R;
import com.example.walabotsmartsecuritydefense.table.Announcement;

import org.litepal.LitePal;

import java.util.List;

public class AnnouncementContentActivity extends AppCompatActivity {

    private String TAG = getClass().toString();

    private ImageView icon;
    private TextView createDate, content;

    private String serialNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcement_list_layout);

        Bundle bundle = getIntent().getExtras();
        serialNumber = bundle.getString("serialNumber");

        initial();

        Log.d(TAG, "serialNumber " + serialNumber);

        //查詢指定的序列號相關內容
        List<Announcement> announcementList =
                LitePal.select( "content", "createdate").where("serialnumber = ?", serialNumber).find(Announcement.class);

        Log.d(TAG, "announcementList.size(): " + announcementList.size());

        String announcementContent = announcementList.get(0).getContent();
        String announcementCreateDate = announcementList.get(0).getCreateDate();

        createDate.setText(announcementCreateDate);
        content.setSingleLine(false);//取消只顯示一行機制
        content.setText(announcementContent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(AnnouncementContentActivity.this, MainActivity.class);
            intent.putExtra("announcement", "101");
            startActivity(intent);

            finish();

            finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initial() {
        icon = (ImageView) findViewById(R.id.icon);
        createDate = (TextView)findViewById(R.id.create_date);
        content = (TextView)findViewById(R.id.content);
    }
}
