package com.fendoudebb.address.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * zbj on 2017-11-13 17:22.
 */

@Entity(tableName = NumberEntity.TABLE_NAME, foreignKeys = {
        @ForeignKey(
                entity = CityEntity.class,
                parentColumns = "id",
                childColumns = "city_id",
                onDelete = ForeignKey.CASCADE)},indices = {
        @Index(value = {"city_id"})
})
public class NumberEntity {

    public static final String TABLE_NAME = "geo_number";

    @NonNull
    @PrimaryKey
    public String number_prefix;

    public String city_id;

    public static NumberEntity fromContentValues(ContentValues values) {
        final NumberEntity number = new NumberEntity();
        if (values.containsKey("number_prefix")) {
            number.number_prefix = values.getAsString("number_prefix");
        }
        if (values.containsKey("city_id")) {
            number.city_id = values.getAsString("city_id");
        }
        return number;
    }

}
