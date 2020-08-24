package com.example.walabotsmartsecuritydefense.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walabotsmartsecuritydefense.Application;
import com.example.walabotsmartsecuritydefense.MainActivity;
import com.example.walabotsmartsecuritydefense.R;
import com.example.walabotsmartsecuritydefense.adapter.NotificationAdapter;
import com.example.walabotsmartsecuritydefense.manager.CloudManager;
import com.example.walabotsmartsecuritydefense.manager.PreferenceManager;
import com.example.walabotsmartsecuritydefense.table.Notification;

import org.litepal.LitePal;

import java.util.List;

public class ExceptionNotificationsFragment extends Fragment {

    private String TAG = getClass().toString();

    private ExceptionNotificationsViewModel notificationsViewModel;

    private RecyclerView notificationList;
    private NotificationAdapter notificationAdapter;

    protected CloudManager cloudManager;
    protected PreferenceManager preferenceManager;
    private List<Notification> notifications;

//    Handler handler = new Handler(Looper.myLooper()) {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch(msg.what){ //notification_copyAsync
//                case 3:
//
//                    break;
//
//            }
//            Log.d( "ExceptionNF", "handler: case" );
//
//        }
//    };



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cloudManager = new CloudManager(getContext());
        preferenceManager = new PreferenceManager(getContext());
        notifications = LitePal.findAll(Notification.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_exception_notifications, container, false);
        initial(root);

        return root;
    }

    private void initial(View root) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(ExceptionNotificationsViewModel.class);
        notificationList = root.findViewById(R.id.notification_list);

//        //hannah_test
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        notificationsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        notificationList.setLayoutManager(layoutManager);
        notificationAdapter = new NotificationAdapter();
//        ListAdapter(getActivity(), this);
        BindData(notificationList);

        //點擊訊息佈告項目
        notificationAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int pos) {
                Log.d(TAG, "onItemClick pos " + pos);

//                Intent intent = new Intent();
//                intent.setClass(getActivity(), NotificationContentActivity.class);
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), MainActivity.class);
//
//
//                Notification notification = notifications.get(pos);
//                int id = notification.getId();
//                Log.d(TAG, "onItemClick notification: " + notification);
//
//                Log.d(TAG, "onItemClick id: " + id);
//                String serialNumber = notification.getSerialNumber();
//
//                Log.d(TAG, "onItemClick serialNumber: " + serialNumber);
//
//                //hannah_test
////                Log.d("LitePal", "id " + announcementInfo.getId());
////                Log.d("LitePal", "createDate. " + announcementInfo.getCreateDate());
////                Log.d("LitePal", "sort. " + announcementInfo.getSort());
//
//                //Toast.makeText(getContext(), "SerialNumber: " + announcementInfo.getSerialNumber() + "; pos: " + pos, Toast.LENGTH_SHORT).show();
//
//                Bundle bundle = new Bundle();
//                bundle.putString("serialNumber", serialNumber);
//                intent.putExtras(bundle);
//                startActivity(intent);
            }
        });



//        //取得異常通知
//        LitePal.deleteAll(Notification.class);
//        final String urlApiSys_notification_copy= Application.urlSys_notification_copy;
//        cloudManager.notification_copyAsync(urlApiSys_notification_copy, preferenceManager.getAccount());


        List<Notification> notification = LitePal.findAll(Notification.class);

        //hannah_test
        for (Notification notifications : notification) {
            Log.d("LitePal", "notifications serialNumber is " + notifications.getSerialNumber());
            Log.d("LitePal", "notifications source is " + notifications.getSource());
            Log.d("LitePal", "notifications username is " + notifications.getUsername());
            Log.d("LitePal", "notifications readFlag is " + notifications.getReadFlag());
            Log.d("LitePal", "notifications createTime is " + notifications.getCreateTime());
            Log.d("LitePal", "notifications updateTime is " + notifications.getUpdateTime());
            Log.d("LitePal", "notifications customerId is " + notifications.getCustomerId());
            Log.d("LitePal", "notifications roomId is " + notifications.getRoomId());
            Log.d("LitePal", "notifications category is " + notifications.getCategory());
            Log.d("LitePal", "notifications title is " + notifications.getTitle());
            Log.d("LitePal", "notifications content is " + notifications.getContent());
            Log.d("LitePal", "notifications link is " + notifications.getLink());
            Log.d("LitePal", "notifications id is " + notifications.getId());

        }
    }

    //將訊息佈告，依遞增排序
    private void BindData(RecyclerView view) {
        //List list = LitePal.findAll(Caregivers.class);
        List list = LitePal.order("id asc").find(Notification.class);
        //List list = LitePal.order("id desc").find(Caregivers.class);
        notificationAdapter.setNotificationList(list);
        view.setAdapter(notificationAdapter);
    }
}