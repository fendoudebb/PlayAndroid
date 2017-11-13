package com.fendoudebb.address.googlei18n;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.Locale;

import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;

@RestrictTo(LIBRARY_GROUP)
class CountryDetector {
    private static final String TAG                 = "zbj0527";
    private static final String DEFAULT_COUNTRY_ISO = "CN";
    private static CountryDetector  sInstance;
    private final  TelephonyManager mTelephonyManager;

    private CountryDetector(Context context) {
        mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static CountryDetector getInstance(Context context) {
        if(sInstance == null) {
            synchronized (CountryDetector.class) {
                if (sInstance == null) {
                    sInstance = new CountryDetector(context);
                }
            }
        }
        return sInstance;
    }

    public String getCurrentCountryIso() {
        String result = null;
        boolean networkCountryCodeAvailable = isNetworkCountryCodeAvailable();
        Log.d(TAG, "networkCountryCodeAvailable: " + networkCountryCodeAvailable);
        if (networkCountryCodeAvailable) {
            result = getNetworkBasedCountryIso();
            Log.d(TAG," getNetworkBasedCountryIso: " + result);
        }
        if (TextUtils.isEmpty(result)) {
            result = getSimBasedCountryIso();
            Log.d(TAG,"getSimBasedCountryIso: " + result);
        }
        if (TextUtils.isEmpty(result)) {
            result = getLocaleBasedCountryIso();
            Log.d(TAG,"getLocaleBasedCountryIso: " + result);
        }
        if (TextUtils.isEmpty(result)) {
            result = DEFAULT_COUNTRY_ISO;
            Log.d(TAG,"DEFAULT_COUNTRY_ISO");
        }
        Log.d(TAG," result ==  " + result + "\n-------------------------------------------------");
        return result.toUpperCase(Locale.US);
    }

    /**
     * @return the country code of the current telephony network the user is connected to.
     */
    private String getNetworkBasedCountryIso() {
        return mTelephonyManager.getNetworkCountryIso();
    }

    /**
     * @return the country code of the SIM card currently inserted in the device.
     */
    private String getSimBasedCountryIso() {
        return mTelephonyManager.getSimCountryIso();
    }

    /**
     * @return the country code of the user's currently selected locale.
     */
    private String getLocaleBasedCountryIso() {
        Locale defaultLocale = Locale.getDefault();
        if (defaultLocale != null) {
            return defaultLocale.getCountry();
        }
        return null;
    }

    private boolean isNetworkCountryCodeAvailable() {
        // On CDMA TelephonyManager.getNetworkCountryIso() just returns the SIM's country code.
        // In this case, we want to ignore the value returned and fallback to location instead.
        return mTelephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM;
    }
}