package com.fendoudebb.address.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.fendoudebb.address.local.entity.CityEntity;

import java.util.List;

/**
 * zbj on 2017-11-13 17:33.
 */

@Dao
public interface CityDao {

    @Query("SELECT * FROM geo_city")
    Cursor selectAll();

    @Query("SELECT * FROM geo_city WHERE id = :id")
    Cursor selectById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCity(CityEntity cityEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<CityEntity> cities);

}
