package com.fendoudebb.playandroid.module.feature;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;

/**
 * zbj on 2017-09-15 14:59.
 */

public class FeaturesPresenter implements FeaturesContract.Presenter {

    private CompositeDisposable mCompositeDisposable;

    public FeaturesPresenter(@NonNull FeaturesContract.View view) {

        mCompositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
