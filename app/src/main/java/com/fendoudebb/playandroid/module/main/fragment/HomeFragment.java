package com.fendoudebb.playandroid.module.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.base.fragment.BaseFragment;
import com.fendoudebb.playandroid.module.feature.FeaturesFragment;
import com.fendoudebb.playandroid.module.feature.ui.WebViewActivity;
import com.fendoudebb.playandroid.util.ActivityUtil;

/**
 * author : zbj on 2017/9/27 21:17.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    public static HomeFragment newInstance() {
        Bundle arguments = new Bundle();
        arguments.putString("", "");
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_main_home;
    }

    @Override
    protected void initView(View view) {
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        textView.setOnClickListener(this);
        view.findViewById(R.id.text_view_2).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view:
                FeaturesFragment featuresFragment = FeaturesFragment.newInstance();
                ActivityUtil.addFragmentToActivity(getFragmentManager(), featuresFragment,
                        R.id.contentFrame);
                break;
            case R.id.text_view_2:
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }

}
