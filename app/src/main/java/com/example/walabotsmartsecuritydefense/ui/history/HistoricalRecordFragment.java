package com.example.walabotsmartsecuritydefense.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.walabotsmartsecuritydefense.R;

public class HistoricalRecordFragment extends Fragment {

    private HistoricalRecordViewModel historyViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                ViewModelProviders.of(this).get(HistoricalRecordViewModel.class);
        View root = inflater.inflate(R.layout.fragment_historical_record, container, false);
        final TextView textView = root.findViewById(R.id.text_hisory);
        historyViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}