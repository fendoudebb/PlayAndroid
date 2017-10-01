package com.fendoudebb.playandroid.module.feature.data;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : zbj on 2017/9/30 23:00.
 */

public class SystemIntent implements Parcelable{

    public String name;
    public Intent intent;

    public SystemIntent(String name, Intent intent) {
        this.name = name;
        this.intent = intent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelable(this.intent, flags);
    }

    protected SystemIntent(Parcel in) {
        this.name = in.readString();
        this.intent = in.readParcelable(Intent.class.getClassLoader());
    }

    public static final Creator<SystemIntent> CREATOR = new Creator<SystemIntent>() {
        @Override
        public SystemIntent createFromParcel(Parcel source) {
            return new SystemIntent(source);
        }

        @Override
        public SystemIntent[] newArray(int size) {
            return new SystemIntent[size];
        }
    };
}
