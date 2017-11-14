package com.fendoudebb.address;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fendoudebb.address.local.AddressDatabase;
import com.fendoudebb.address.local.dao.CarrierDao;
import com.fendoudebb.address.local.dao.CityDao;
import com.fendoudebb.address.local.dao.NumberDao;
import com.fendoudebb.address.local.dao.ProvinceDao;
import com.fendoudebb.address.local.entity.CarrierEntity;
import com.fendoudebb.address.local.entity.CityEntity;
import com.fendoudebb.address.local.entity.NumberEntity;
import com.fendoudebb.address.local.entity.ProvinceEntity;

/**
 * zbj on 2017-11-13 11:18.
 */

public class NumAddressProvider extends ContentProvider{

    public static final String AUTHORITY = "com.fendoudebb.address.numaddress.provider";

    public static final Uri URI_CARRIER = Uri.parse(
            "content://" + AUTHORITY + "/" + CarrierEntity.TABLE_NAME);

    public static final Uri URI_NUMBER = Uri.parse(
            "content://" + AUTHORITY + "/" + NumberEntity.TABLE_NAME);

    public static final Uri URI_PROVINCE = Uri.parse(
            "content://" + AUTHORITY + "/" + ProvinceEntity.TABLE_NAME);

    public static final Uri URI_CITY = Uri.parse(
            "content://" + AUTHORITY + "/" + CityEntity.TABLE_NAME);

    private static final int CODE_CARRIER_DIR = 1;
    private static final int CODE_CARRIER_ITEM = 2;

    private static final int CODE_NUMBER_DIR = 3;
    private static final int CODE_NUMBER_ITEM = 4;

    private static final int CODE_PROVINCE_DIR = 5;
    private static final int CODE_PROVINCE_ITEM = 6;

    private static final int CODE_CITY_DIR = 7;
    private static final int CODE_CITY_ITEM = 8;


    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, CarrierEntity.TABLE_NAME, CODE_CARRIER_DIR);
        MATCHER.addURI(AUTHORITY, CarrierEntity.TABLE_NAME + "/*", CODE_CARRIER_ITEM);

        MATCHER.addURI(AUTHORITY, NumberEntity.TABLE_NAME, CODE_NUMBER_DIR);
        MATCHER.addURI(AUTHORITY, NumberEntity.TABLE_NAME + "/*", CODE_NUMBER_ITEM);

        MATCHER.addURI(AUTHORITY, ProvinceEntity.TABLE_NAME, CODE_PROVINCE_DIR);
        MATCHER.addURI(AUTHORITY, ProvinceEntity.TABLE_NAME + "/*", CODE_PROVINCE_ITEM);

        MATCHER.addURI(AUTHORITY, CityEntity.TABLE_NAME, CODE_CITY_DIR);
        MATCHER.addURI(AUTHORITY, CityEntity.TABLE_NAME + "/*", CODE_CITY_ITEM);

    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s,
                        @Nullable String[] strings1, @Nullable String s1) {
        final Context context = getContext();
        if (context == null) {
            return null;
        }
        final int code = MATCHER.match(uri);
        final Cursor cursor;
        if (code == CODE_CARRIER_DIR || code == CODE_CARRIER_ITEM) {
            CarrierDao carrier = AddressDatabase.getInstance(context).getCarrierDao();
            if (code == CODE_CARRIER_DIR) {
                cursor = carrier.selectAll();
            } else {
                cursor = carrier.selectByNumberPrefix3(String.valueOf(ContentUris.parseId(uri)));
            }
        } else if (code == CODE_NUMBER_DIR || code == CODE_NUMBER_ITEM){
            NumberDao number = AddressDatabase.getInstance(context).getNumberDao();
            if (code == CODE_NUMBER_DIR) {
                cursor = number.selectAll();
            } else {
                cursor = number.selectByNumberPrefix(String.valueOf(ContentUris.parseId(uri)));
            }
        } else if (code == CODE_PROVINCE_DIR || code == CODE_PROVINCE_ITEM){
            ProvinceDao province = AddressDatabase.getInstance(context).getProvinceDao();
            if (code == CODE_PROVINCE_DIR) {
                cursor = province.selectAll();
            } else {
                cursor = province.selectById(String.valueOf(ContentUris.parseId(uri)));
            }
        } else if (code == CODE_CITY_DIR || code == CODE_CITY_ITEM){
            CityDao city = AddressDatabase.getInstance(context).getCityDao();
            if (code == CODE_CITY_DIR) {
                cursor = city.selectAll();
            } else {
                cursor = city.selectById(String.valueOf(ContentUris.parseId(uri)));
            }
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        cursor.setNotificationUri(context.getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_CARRIER_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + CarrierEntity.TABLE_NAME;
            case CODE_CARRIER_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + CarrierEntity.TABLE_NAME;

            case CODE_NUMBER_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + CarrierEntity.TABLE_NAME;
            case CODE_NUMBER_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + CarrierEntity.TABLE_NAME;

            case CODE_PROVINCE_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + CarrierEntity.TABLE_NAME;
            case CODE_PROVINCE_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + CarrierEntity.TABLE_NAME;

            case CODE_CITY_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + CarrierEntity.TABLE_NAME;
            case CODE_CITY_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + CarrierEntity.TABLE_NAME;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final Context context = getContext();
        if (context == null) {
            return null;
        }
        switch (MATCHER.match(uri)) {
            case CODE_CARRIER_DIR:
                final long carrierId = AddressDatabase.getInstance(context).getCarrierDao()
                        .insert(CarrierEntity.fromContentValues(contentValues));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, carrierId);
            case CODE_CARRIER_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);

            case CODE_NUMBER_DIR:
                final long numberId = AddressDatabase.getInstance(context).getNumberDao()
                        .insert(NumberEntity.fromContentValues(contentValues));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, numberId);
            case CODE_NUMBER_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
