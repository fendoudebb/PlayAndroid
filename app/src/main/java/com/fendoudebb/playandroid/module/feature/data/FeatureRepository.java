package com.fendoudebb.playandroid.module.feature.data;


import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.BaseDataSource;
import com.fendoudebb.playandroid.util.ResUtil;

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
                String[] featuresNames = ResUtil.getStringArray(R.array.feature_array);

                Feature feature;
                for (int i = 0; i < featuresNames.length; i++) {
                    int stringResId = ResUtil.getStringId(featuresNames[i]);
                    String featureName = ResUtil.getString(stringResId);
                    String resName = ResUtil.getResName(stringResId);
                    int drawableResId = ResUtil.getDrawableId(resName);
                    feature = new Feature(i, featureName, drawableResId);
                    features.add(feature);
                }
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
