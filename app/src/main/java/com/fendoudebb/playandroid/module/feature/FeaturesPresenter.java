package com.fendoudebb.playandroid.module.feature;


import com.fendoudebb.playandroid.module.feature.data.Feature;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * zbj on 2017-09-15 14:59.
 */

public class FeaturesPresenter implements FeaturesContract.Presenter {

    private CompositeDisposable mCompositeDisposable;
    private FeaturesModel mFeaturesModel;
    private FeaturesContract.View mView;

    public FeaturesPresenter(FeaturesContract.View view) {
        mCompositeDisposable = new CompositeDisposable();
        mFeaturesModel = new FeaturesModel();
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        mFeaturesModel.getFeatures()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Feature>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<Feature> features) {
                        mView.showFeatures(features);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

}
