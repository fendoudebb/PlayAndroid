package com.fendoudebb.playandroid.module.feature;

import com.fendoudebb.playandroid.module.base.BasePresenter;
import com.fendoudebb.playandroid.module.base.BaseView;
import com.fendoudebb.playandroid.module.feature.data.Feature;

import java.util.List;

/**
 * zbj on 2017-09-15 14:49.
 */

public interface FeaturesContract {

    interface View extends BaseView<Presenter> {
        void showFeatures(List<Feature> features);
    }

    interface Presenter extends BasePresenter {

    }

}
