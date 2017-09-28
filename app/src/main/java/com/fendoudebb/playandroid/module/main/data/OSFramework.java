package com.fendoudebb.playandroid.module.main.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : zbj on 2017/9/27 21:59.
 * OpenSourceFramework
 */

public class OSFramework implements Parcelable{

    public String name;

    public String website;

    public OSFramework(String name, String webSite) {
        this.name = name;
        this.website = webSite;
    }

    @Override
    public String toString() {
        return "OSFramework{" +
                "name='" + name + '\'' +
                ", website='" + website + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(website);
    }

    protected OSFramework(Parcel in) {
        name = in.readString();
        website = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OSFramework> CREATOR = new Creator<OSFramework>() {
        @Override
        public OSFramework createFromParcel(Parcel in) {
            return new OSFramework(in);
        }

        @Override
        public OSFramework[] newArray(int size) {
            return new OSFramework[size];
        }
    };
}
