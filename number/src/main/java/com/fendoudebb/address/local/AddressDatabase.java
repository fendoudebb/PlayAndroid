package com.fendoudebb.address.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.fendoudebb.address.local.dao.CarrierDao;
import com.fendoudebb.address.local.dao.CityDao;
import com.fendoudebb.address.local.dao.NumberDao;
import com.fendoudebb.address.local.dao.ProvinceDao;
import com.fendoudebb.address.local.entity.CarrierEntity;
import com.fendoudebb.address.local.entity.CityEntity;
import com.fendoudebb.address.local.entity.NumberEntity;
import com.fendoudebb.address.local.entity.ProvinceEntity;

/**
 * zbj on 2017-11-13 17:48.
 */

@Database(
        entities = {
            CarrierEntity.class,
            NumberEntity.class,
            ProvinceEntity.class,
            CityEntity.class},
        version = 1)
public abstract class AddressDatabase extends RoomDatabase {

    public abstract CarrierDao getCarrierDao();

    public abstract CityDao getCityDao();

    public abstract NumberDao getNumberDao();

    public abstract ProvinceDao getProvinceDao();

    private static AddressDatabase sInstance;

    public static synchronized AddressDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(
                                context.getApplicationContext(),
                                AddressDatabase.class,
                                "test_db")
                            .build();
        }
        return sInstance;
    }




}
