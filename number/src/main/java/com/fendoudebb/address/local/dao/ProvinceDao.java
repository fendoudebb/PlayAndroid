package com.fendoudebb.address.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.database.Cursor;

/**
 * zbj on 2017-11-14 11:53.
 */

@Dao
public interface ProvinceDao {

    @Query("SELECT * FROM geo_province")
    Cursor selectAll();

    @Query("SELECT * FROM geo_province WHERE id = :id")
    Cursor selectById(String id);

}
