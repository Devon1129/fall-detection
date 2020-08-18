package com.example.walabotsmartsecuritydefense.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoricalRecordViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HistoricalRecordViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is historical  record fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}