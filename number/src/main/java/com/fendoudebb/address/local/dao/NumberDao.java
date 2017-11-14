package com.fendoudebb.address.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.fendoudebb.address.local.entity.NumberEntity;

/**
 * zbj on 2017-11-14 11:59.
 */

@Dao
public interface NumberDao {

    @Query("SELECT * FROM geo_number")
    Cursor selectAll();

    @Query("SELECT * FROM geo_number WHERE number_prefix = :numberPrefix")
    Cursor selectByNumberPrefix(String numberPrefix);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(NumberEntity number);

}
