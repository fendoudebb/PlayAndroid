package com.fendoudebb.playandroid.module.api.bean.gank;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * author : zbj on 2017/10/7 09:45.
 */

public class GankData implements Parcelable{

    public boolean    error;
    public List<Gank> results;

    @Override
    public String toString() {
        return "GankData{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.error ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.results);
    }

    public GankData() {
    }

    protected GankData(Parcel in) {
        this.error = in.readByte() != 0;
        this.results = in.createTypedArrayList(Gank.CREATOR);
    }

    public static final Creator<GankData> CREATOR = new Creator<GankData>() {
        @Override
        public GankData createFromParcel(Parcel source) {
            return new GankData(source);
        }

        @Override
        public GankData[] newArray(int size) {
            return new GankData[size];
        }
    };
}
