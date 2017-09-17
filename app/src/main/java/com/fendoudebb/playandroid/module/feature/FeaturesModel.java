package com.fendoudebb.playandroid.module.feature;

import com.fendoudebb.playandroid.module.feature.data.Feature;
import com.fendoudebb.playandroid.module.feature.data.FeatureRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 * zbj on 2017-09-15 15:45.
 */

public class FeaturesModel {

    private FeatureRepository mFeatureRepository;

    public FeaturesModel(){
        mFeatureRepository = new FeatureRepository();
    }

    public Observable<List<Feature>> getFeatures(){
        return mFeatureRepository.getData();
    }

}
