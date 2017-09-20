package com.fendoudebb.playandroid.module.feature.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * zbj on 2017-09-15 16:01.
 */

public class Feature implements Parcelable {

    public int rank;

    public int nameId;

    public int logo;

    public Feature() {

    }

    public Feature(int rank, int nameId, int logo) {
        this.rank = rank;
        this.nameId = nameId;
        this.logo = logo;
    }

    protected Feature(Parcel in) {
        rank = in.readInt();
        nameId = in.readInt();
        logo = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rank);
        dest.writeInt(nameId);
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
