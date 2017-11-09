package com.fendoudebb.util;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * //  发送事件
 *   RxBus.INSTANCE
 *   .post(new SomeEvent())
 *
 *   //  接收事件
 *   RxBus.INSTANCE
 *   .toObservable(SomeEvent.class)
 *   .subscribe(event -> {
 *   ...
 *   }, Debug::e);
 *
 */
public enum RxBus {

    INSTANCE;

    private PublishSubject<Object> mSubject = PublishSubject.create();

    public void post(Object object) {
        mSubject.onNext(object);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mSubject.ofType(eventType);
    }
}