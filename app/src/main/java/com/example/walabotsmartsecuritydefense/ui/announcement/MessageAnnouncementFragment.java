package com.example.walabotsmartsecuritydefense.ui.announcement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walabotsmartsecuritydefense.Application;
import com.example.walabotsmartsecuritydefense.R;
import com.example.walabotsmartsecuritydefense.activity.AnnouncementContentActivity;
import com.example.walabotsmartsecuritydefense.adapter.AnnouncementAdapter;
import com.example.walabotsmartsecuritydefense.manager.CloudManager;
import com.example.walabotsmartsecuritydefense.table.Announcement;

import org.litepal.LitePal;

import java.util.List;

public class MessageAnnouncementFragment extends Fragment {

    private String TAG = getClass().toString();

    private MessageAnnouncementViewModel announcementViewModel;

    private RecyclerView announcementList;
    private AnnouncementAdapter announcementAdapter;

    protected CloudManager cloudManager;
    private List<Announcement> announcements;

    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){ //sys_noteAsync
                case 1:
                    BindData(announcementList);
                    break;
            }
            Log.d( "MainActivity", "handler: case" );
        }
    };

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cloudManager = new CloudManager(getContext());
        announcements = LitePal.findAll(Announcement.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_message_announcement, container, false);
        initial(root);

        return root;
    }

    private void initial(View root) {
        announcementViewModel =
                ViewModelProviders.of(this).get(MessageAnnouncementViewModel.class);
        announcementList = root.findViewById(R.id.announcement_list);


        //hannah_test
//        final TextView textView = root.findViewById(R.id.text_announcement1);
//        announcementViewModel.getText().observe(this, new Observer<String>() {
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
        announcementList.setLayoutManager(layoutManager);
        announcementAdapter = new AnnouncementAdapter();

        //取得訊息佈告
        LitePal.deleteAll(Announcement.class);
        final String urlApiSys_note = Application.urlSys_note;
        cloudManager.sys_noteAsync(urlApiSys_note, handler);

        //點擊訊息佈告項目
        announcementAdapter.setOnItemClickListener(new AnnouncementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Log.d(TAG, "onItemClick pos " + pos);

                Intent intent = new Intent();
                intent.setClass(getActivity(), AnnouncementContentActivity.class);

                Announcement announcementInfo = announcements.get(pos);
                int id = announcementInfo.getId();
                Log.d(TAG, "onItemClick announcementInfo: " + announcementInfo);

                Log.d(TAG, "onItemClick id: " + id);
                String serialNumber = announcementInfo.getSerialNumber();

                Log.d(TAG, "onItemClick serialNumber: " + serialNumber);
                //Toast.makeText(getContext(), "SerialNumber: " + announcementInfo.getSerialNumber() + "; pos: " + pos, Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("serialNumber", serialNumber);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        List<Announcement> announcement = LitePal.findAll(Announcement.class);

        //hannah_test
        for (Announcement announcements : announcement) {
            Log.d("LitePal", "announcements serialNumber is " + announcements.getSerialNumber());
            Log.d("LitePal", "announcements content is " + announcements.getContent());
            Log.d("LitePal", "announcements category is " + announcements.getCategory());
            Log.d("LitePal", "announcements createDate is " + announcements.getPublishDate());
            Log.d("LitePal", "announcements id is " + announcements.getId());
            Log.d("LitePal", "announcements publishFlag is " + announcements.getPublishFlag());
            Log.d("LitePal", "announcements sort is " + announcements.getSort());

        }
    }


    //將訊息佈告，依遞增排序
    private void BindData(RecyclerView view) {
        //List list = LitePal.findAll(Caregivers.class);
        List list = LitePal.order("id asc").find(Announcement.class);
        //List list = LitePal.order("id desc").find(Caregivers.class);
        announcementAdapter.setAnnouncementList(list);
        view.setAdapter(announcementAdapter);
    }
}
