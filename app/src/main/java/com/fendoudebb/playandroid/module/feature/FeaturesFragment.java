package com.fendoudebb.playandroid.module.feature;

import android.support.v4.app.Fragment;

/**
 * zbj on 2017-09-15 14:45.
 */

public class FeaturesFragment extends Fragment implements FeaturesContract.View{

    private FeaturesContract.Presenter mPresenter;

    @Override
    public void setPresenter(FeaturesContract.Presenter presenter) {
        mPresenter = presenter;
    }

}
