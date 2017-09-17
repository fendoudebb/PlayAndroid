package com.fendoudebb.playandroid.module.feature.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * zbj on 2017-09-15 16:01.
 */

public class Feature implements Parcelable {

    public int rank;

    public String name;

    public int logo;

    public Feature() {

    }

    public Feature(int rank, String name, int logo) {
        this.rank = rank;
        this.name = name;
        this.logo = logo;
    }

    protected Feature(Parcel in) {
        rank = in.readInt();
        name = in.readString();
        logo = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rank);
        dest.writeString(name);
        dest.writeInt(logo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Feature> CREATOR = new Creator<Feature>() {
        @Override
        public Feature createFromParcel(Parcel in) {
            return new Feature(in);
        }

        @Override
        public Feature[] newArray(int size) {
            return new Feature[size];
        }
    };
}
