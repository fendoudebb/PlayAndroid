package com.fendoudebb.address.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * zbj on 2017-11-13 16:47.
 */

@Entity(tableName = CityEntity.TABLE_NAME,foreignKeys = {
        @ForeignKey(
                entity = ProvinceEntity.class,
                parentColumns = "id",
                childColumns = "province_id",
                onDelete = ForeignKey.CASCADE)},indices = {
        @Index(value = "province_id")
})
public class CityEntity {

    public static final String TABLE_NAME = "geo_city";

    @NonNull
    @PrimaryKey
    public String id;

    public String name;

    public String province_id;
}
