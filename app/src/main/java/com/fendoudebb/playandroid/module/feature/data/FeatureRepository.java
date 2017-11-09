package com.fendoudebb.playandroid.module.feature.data;


import android.content.res.TypedArray;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.base.BaseDataSource;
import com.fendoudebb.util.ResUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * zbj on 2017-09-15 16:48.
 */

public class FeatureRepository implements BaseDataSource<Feature> {
    private static final String TAG = "FeatureRepository";
    @Override
    public Observable<List<Feature>> getData() {
        return Observable.create(new ObservableOnSubscribe<List<Feature>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Feature>> e) throws Exception {
                List<Feature> features = new ArrayList<>();
                String[] featuresNames = ResUtil.getStringArray(R.array.feature_name);
                Feature feature;
                TypedArray ar = ResUtil.getResources().obtainTypedArray(R.array.feature_logo);
                for (int i = 0; i < ar.length(); i++){
                    int stringResId = ResUtil.getStringId(featuresNames[i]);
//                    String featureName = ResUtil.getString(stringResId);
                    feature = new Feature(i, stringResId, ar.getResourceId(i, 0));
                    features.add(feature);
                }
                ar.recycle();

                /*for (int i = 0; i < featuresNames.length; i++) {
                    int stringResId = ResUtil.getStringId(featuresNames[i]);
                    String featureName = ResUtil.getString(stringResId);
                    String resName = ResUtil.getResName(stringResId);
                    int drawableResId = ResUtil.getDrawableId(resName);
                    feature = new Feature(i, featureName, drawableResId);
                    features.add(feature);
                }*/
                e.onNext(features);
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<Feature> getData(String tag) {
        return null;
    }
}
