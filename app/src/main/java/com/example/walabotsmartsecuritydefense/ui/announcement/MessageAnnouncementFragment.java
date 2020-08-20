package com.example.walabotsmartsecuritydefense.ui.announcement;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walabotsmartsecuritydefense.Application;
import com.example.walabotsmartsecuritydefense.R;
import com.example.walabotsmartsecuritydefense.adapter.AnnouncementAdapter;
import com.example.walabotsmartsecuritydefense.manager.CloudManager;
import com.example.walabotsmartsecuritydefense.table.Announcement;

import org.litepal.LitePal;

import java.util.List;

public class MessageAnnouncementFragment extends Fragment {

    private MessageAnnouncementViewModel announcementViewModel;

    private RecyclerView announcementList;
    private AnnouncementAdapter announcement_adapter;

    protected CloudManager cloudManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cloudManager = new CloudManager(getContext());
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
        announcement_adapter = new AnnouncementAdapter();
//        ListAdapter(getActivity(), this);
        BindData(announcementList);

        announcement_adapter.setItemClickListener(new AnnouncementAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view) {
                int position = announcementList.getChildAdapterPosition(view);
                switch (position) {
                    case 0:
                        Log.d("sssss", "ssssss");
                        //startActivity(new Intent(context,MainActivity.class));
                        break;
                }
            }

            @Override
            public void onItemLongClickListener(View view) {

            }
        });
        

//        announcement_adapter.setOnItemClickListener(new AnnouncementAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int pos) {
//
//            }
//        });

        //取得訊息佈告
        final String urlApiSys_note = Application.urlSys_note;
        cloudManager.sys_noteAsync(urlApiSys_note);

        List<Announcement> books = LitePal.findAll(Announcement.class);

        for (Announcement book : books) {
            Log.d("LitePal", "book name is " + book.getContent());
            Log.d("LitePal", "book author is " + book.getCategory());
            Log.d("LitePal", "book pages is " + book.getCreateDate());
            Log.d("LitePal", "book price is " + book.getId());
            Log.d("LitePal", "book price is " + book.getPublishFlag());
            Log.d("LitePal", "book price is " + book.getSort());

        }
    }

    //將訊息佈告，依遞增排序
    private void BindData(RecyclerView view) {
        //List list = LitePal.findAll(Caregivers.class);
        List list = LitePal.order("id asc").find(Announcement.class);
        //List list = LitePal.order("id desc").find(Caregivers.class);
        announcement_adapter.setAnnouncementListList(list);
        view.setAdapter(announcement_adapter);
    }
}
