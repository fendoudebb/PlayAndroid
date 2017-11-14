package com.fendoudebb.address.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.fendoudebb.address.local.entity.CarrierEntity;

/**
 * zbj on 2017-11-14 11:55.
 */

@Dao
public interface CarrierDao {

    @Query("SELECT * FROM geo_carrier")
    Cursor selectAll();

    @Query("SELECT * FROM geo_carrier WHERE number_prefix_3 = :numberPrefix3")
    Cursor selectByNumberPrefix3(String numberPrefix3);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CarrierEntity carrier);

}
