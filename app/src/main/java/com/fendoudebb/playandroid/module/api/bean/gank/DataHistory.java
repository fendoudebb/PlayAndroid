package com.fendoudebb.playandroid.module.api.bean.gank;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * author : zbj on 2017/10/7 10:01.
 * 发过干货的日期
 */
public class DataHistory implements Parcelable {

    public boolean      error;
    public List<String> results;

    @Override
    public String toString() {
        return "DataHistory{" +
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
        dest.writeStringList(this.results);
    }

    public DataHistory() {
    }

    protected DataHistory(Parcel in) {
        this.error = in.readByte() != 0;
        this.results = in.createStringArrayList();
    }

    public static final Creator<DataHistory> CREATOR = new Creator<DataHistory>() {
        @Override
        public DataHistory createFromParcel(Parcel source) {
            return new DataHistory(source);
        }

        @Override
        public DataHistory[] newArray(int size) {
            return new DataHistory[size];
        }
    };
}
