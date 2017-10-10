package com.fendoudebb.playandroid.module.api.bean.gank;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * author : zbj on 2017/10/7 10:12.
 * 每日数据
 */

public class DailyData implements Parcelable {

    public boolean      error;
    public AllDataList  results;
    public List<String> category;

    @Override
    public String toString() {
        return "DailyData{" +
                "error=" + error +
                ", results=" + results +
                ", category=" + category +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.error ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.results, flags);
        dest.writeStringList(this.category);
    }

    public DailyData() {
    }

    protected DailyData(Parcel in) {
        this.error = in.readByte() != 0;
        this.results = in.readParcelable(AllDataList.class.getClassLoader());
        this.category = in.createStringArrayList();
    }

    public static final Creator<DailyData> CREATOR = new Creator<DailyData>() {
        @Override
        public DailyData createFromParcel(Parcel source) {
            return new DailyData(source);
        }

        @Override
        public DailyData[] newArray(int size) {
            return new DailyData[size];
        }
    };
}
