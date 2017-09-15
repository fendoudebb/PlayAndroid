package com.fendoudebb.playandroid.module;

import java.util.List;

import io.reactivex.Observable;

/**
 * zbj on 2017-09-15 16:49.
 */

public interface BaseDataSource<T> {

    Observable<List<T>> getData();

    Observable<T> getData(String tag);

}
