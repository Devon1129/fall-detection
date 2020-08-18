package com.example.walabotsmartsecuritydefense.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExceptionNotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ExceptionNotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is exception notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}