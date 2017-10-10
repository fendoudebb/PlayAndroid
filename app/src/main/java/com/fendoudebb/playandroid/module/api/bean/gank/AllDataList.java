package com.fendoudebb.playandroid.module.api.bean.gank;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * author : zbj on 2017/10/7 10:23.
 * 所有分类数据的集合类
 */

public class AllDataList implements Parcelable{

    public List<Gank> Android;
    public List<Gank> iOS;
    public List<Gank> 休息视频;
    public List<Gank> 拓展资源;
    public List<Gank> 瞎推荐;
    public List<Gank> 福利;

    @Override
    public String toString() {
        return "AllDataList{" +
                "Android=" + Android +
                ", iOS=" + iOS +
                ", 休息视频=" + 休息视频 +
                ", 拓展资源=" + 拓展资源 +
                ", 瞎推荐=" + 瞎推荐 +
                ", 福利=" + 福利 +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.Android);
        dest.writeTypedList(this.iOS);
        dest.writeTypedList(this.休息视频);
        dest.writeTypedList(this.拓展资源);
        dest.writeTypedList(this.瞎推荐);
        dest.writeTypedList(this.福利);
    }

    public AllDataList() {
    }

    protected AllDataList(Parcel in) {
        this.Android = in.createTypedArrayList(Gank.CREATOR);
        this.iOS = in.createTypedArrayList(Gank.CREATOR);
        this.休息视频 = in.createTypedArrayList(Gank.CREATOR);
        this.拓展资源 = in.createTypedArrayList(Gank.CREATOR);
        this.瞎推荐 = in.createTypedArrayList(Gank.CREATOR);
        this.福利 = in.createTypedArrayList(Gank.CREATOR);
    }

    public static final Creator<AllDataList> CREATOR = new Creator<AllDataList>() {
        @Override
        public AllDataList createFromParcel(Parcel source) {
            return new AllDataList(source);
        }

        @Override
        public AllDataList[] newArray(int size) {
            return new AllDataList[size];
        }
    };
}
