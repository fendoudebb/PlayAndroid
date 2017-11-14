package com.fendoudebb.address.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * zbj on 2017-11-13 17:26.
 */

@Entity(tableName = CarrierEntity.TABLE_NAME)
public class CarrierEntity {

    public static final String TABLE_NAME = "geo_carrier";

    @NonNull
    @PrimaryKey
    public String number_prefix_3;

    public String carrier;

    public static CarrierEntity fromContentValues(ContentValues values) {
        final CarrierEntity carrier = new CarrierEntity();
        if (values.containsKey("number_prefix_3")) {
            carrier.number_prefix_3 = values.getAsString("number_prefix_3");
        }
        if (values.containsKey("carrier")) {
            carrier.carrier = values.getAsString("carrier");
        }
        return carrier;
    }

}
