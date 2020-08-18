package com.example.walabotsmartsecuritydefense.ui.announcement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MessageAnnouncementViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MessageAnnouncementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Message Announcement fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
