package com.fendoudebb.playandroid.module.feature.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.fendoudebb.playandroid.R;

/**
 * author : zbj on 2017/9/20 22:43.
 */

public class ClockFragment extends Fragment {

    public static ClockFragment newInstance() {
        Bundle arguments = new Bundle();
        arguments.putString("", "");
        ClockFragment fragment = new ClockFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clock, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TimePicker timePicker = (TimePicker) view.findViewById(R.id.feature_time_picker);
        timePicker.setIs24HourView(true);

    }
}
