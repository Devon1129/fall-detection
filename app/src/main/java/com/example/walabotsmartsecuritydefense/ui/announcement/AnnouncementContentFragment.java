package com.example.walabotsmartsecuritydefense.ui.announcement;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.walabotsmartsecuritydefense.MainActivity;
import com.example.walabotsmartsecuritydefense.R;
import com.example.walabotsmartsecuritydefense.table.Announcement;

import org.litepal.LitePal;

import java.util.List;


public class AnnouncementContentFragment extends Fragment {

    private String TAG = getClass().toString();

    private ImageView icon;
    private TextView createDate, content;

    private String serialNumber;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.announcement_content, container, false);

        Bundle bundle = this.getArguments();
        serialNumber = bundle.getString("serialNumber");

        initial(root);


        //查詢指定序列號(serialnumber)的相關欄位(content、createdate)
        if (serialNumber == null | serialNumber.matches("")) {
            createDate.setText("");
            content.setText("查無資料");
        }else {
            List<Announcement> announcementList =
                    LitePal.select( "content", "publishdate").where("serialnumber=?", serialNumber).find(Announcement.class);

            Log.d(TAG, "announcementList.size(): " + announcementList.size());

            String announcementContent = announcementList.get(0).getContent();
            String announcementCreateDate = announcementList.get(0).getPublishDate();

            createDate.setText(announcementCreateDate);
            content.setText(announcementContent);
        }


        return root;



    }
    private void initial(View root) {
        icon = (ImageView) root.findViewById(R.id.icon);
        createDate = (TextView)root.findViewById(R.id.create_date);
        content = (TextView)root.findViewById(R.id.content);
        content.setSingleLine(false);//取消只顯示一行機制
    }



        //實體按鍵
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if(keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent intent = new Intent(AnnouncementContentFragment.this, MainActivity.class);
//            intent.putExtra("announcement", "101");
//            startActivity(intent);
//
//            finish();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
}
