package com.example.walabotsmartsecuritydefense.ui.announcement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.walabotsmartsecuritydefense.R;

public class MessageAnnouncementFragment extends Fragment {

    private MessageAnnouncementViewModel announcementViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        announcementViewModel =
                ViewModelProviders.of(this).get(MessageAnnouncementViewModel.class);
        View root = inflater.inflate(R.layout.fragment_message_announcement, container, false);
        final TextView textView = root.findViewById(R.id.text_announcement);
        announcementViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}
