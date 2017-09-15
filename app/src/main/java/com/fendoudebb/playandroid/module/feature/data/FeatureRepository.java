package com.fendoudebb.playandroid.module.feature.data;


import com.fendoudebb.playandroid.module.BaseDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * zbj on 2017-09-15 16:48.
 */

public class FeatureRepository implements BaseDataSource<Feature> {

    @Override
    public Observable<List<Feature>> getData() {
        return null;
    }

    @Override
    public Observable<Feature> getData(String tag) {
        return null;
    }
}
