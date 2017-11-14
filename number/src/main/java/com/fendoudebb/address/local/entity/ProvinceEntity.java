package com.fendoudebb.address.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * zbj on 2017-11-13 17:15.
 */

@Entity(tableName = ProvinceEntity.TABLE_NAME)
public class ProvinceEntity {

    public static final String TABLE_NAME = "geo_province";

    @NonNull
    @PrimaryKey
    public String id;

    public String name;


}
