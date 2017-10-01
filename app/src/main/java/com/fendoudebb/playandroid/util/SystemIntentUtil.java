package com.fendoudebb.playandroid.util;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.feature.data.SystemIntent;

import java.util.ArrayList;
import java.util.List;

/**
 * author : zbj on 2017/10/1 18:59.
 */

public class SystemIntentUtil {

    public static final String accessibility_settings = Settings.ACTION_ACCESSIBILITY_SETTINGS;
    public static final String add_account = Settings.ACTION_ADD_ACCOUNT;
    public static final String airplane_mode_settings = Settings.ACTION_AIRPLANE_MODE_SETTINGS;
    public static final String wireless_settings = Settings.ACTION_WIRELESS_SETTINGS;
    public static final String apn_settings = Settings.ACTION_APN_SETTINGS;
    public static final String application_development_settings = Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS;
    public static final String application_settings = Settings.ACTION_APPLICATION_SETTINGS;
    public static final String manage_all_applications_settings = Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS;
    public static final String manage_applications_settings = Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS;
    public static final String bluetooth_settings = Settings.ACTION_BLUETOOTH_SETTINGS;
    public static final String data_roaming_settings = Settings.ACTION_DATA_ROAMING_SETTINGS;
    public static final String device_info_settings = Settings.ACTION_DEVICE_INFO_SETTINGS;
    public static final String display_settings = Settings.ACTION_DISPLAY_SETTINGS;
    public static final String dream_settings = Settings.ACTION_DREAM_SETTINGS;
    public static final String input_method_settings = Settings.ACTION_INPUT_METHOD_SETTINGS;
    public static final String input_method_subtype_settings = Settings.ACTION_INPUT_METHOD_SUBTYPE_SETTINGS;
    public static final String internal_storage_settings = Settings.ACTION_INTERNAL_STORAGE_SETTINGS;
    public static final String memory_card_settings = Settings.ACTION_MEMORY_CARD_SETTINGS;
    public static final String locale_settings = Settings.ACTION_LOCALE_SETTINGS;
    public static final String location_source_settings = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
    public static final String network_operator_settings = Settings.ACTION_NETWORK_OPERATOR_SETTINGS;
    public static final String nfc_settings = Settings.ACTION_NFC_SETTINGS;
    public static final String privacy_settings = Settings.ACTION_PRIVACY_SETTINGS;
    public static final String quick_launch_settings = Settings.ACTION_QUICK_LAUNCH_SETTINGS;
    public static final String search_settings = Settings.ACTION_SEARCH_SETTINGS;
    public static final String security_settings = Settings.ACTION_SECURITY_SETTINGS;
    public static final String settings = Settings.ACTION_SETTINGS;
    public static final String sync_settings = Settings.ACTION_SYNC_SETTINGS;
    public static final String user_dictionary_settings = Settings.ACTION_USER_DICTIONARY_SETTINGS;
    public static final String wifi_ip_settings = Settings.ACTION_WIFI_IP_SETTINGS;
    public static final String wifi_settings = Settings.ACTION_WIFI_SETTINGS;


    public static final String nfcsharing_settings = Settings.ACTION_NFCSHARING_SETTINGS;
    public static final String captioning_settings = Settings.ACTION_CAPTIONING_SETTINGS;
    public static final String print_settings = Settings.ACTION_PRINT_SETTINGS;
    public static final String sound_settings = Settings.ACTION_SOUND_SETTINGS;
    public static final String date_settings = Settings.ACTION_DATE_SETTINGS;
    public static final String nfc_payment_settings = Settings.ACTION_NFC_PAYMENT_SETTINGS;

    public static final String extra_account_types = Settings.EXTRA_ACCOUNT_TYPES;
    public static final String extra_authorities = Settings.EXTRA_AUTHORITIES;
    public static final String extra_input_method_id = Settings.EXTRA_INPUT_METHOD_ID;

    //api-21
    public static final String home_settings = Settings.ACTION_HOME_SETTINGS;
    public static final String cast_settings = Settings.ACTION_CAST_SETTINGS;
    public static final String usage_access_settings = Settings.ACTION_USAGE_ACCESS_SETTINGS;
    public static final String voice_input_settings = Settings.ACTION_VOICE_INPUT_SETTINGS;

    //api-22
    public static final String battery_saver_settings = Settings.ACTION_BATTERY_SAVER_SETTINGS;
    public static final String notification_listener_settings = Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS;

    //api-23
    public static final String manage_write_settings = Settings.ACTION_MANAGE_WRITE_SETTINGS;
    public static final String ignore_battery_optimization_settings = Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS;
    public static final String voice_control_airplane_mode = Settings.ACTION_VOICE_CONTROL_AIRPLANE_MODE;
    public static final String voice_control_battery_saver_mode = Settings.ACTION_VOICE_CONTROL_BATTERY_SAVER_MODE;
    public static final String voice_control_do_not_disturb_mode = Settings.ACTION_VOICE_CONTROL_DO_NOT_DISTURB_MODE;
    public static final String extra_do_not_disturb_mode_enabled = Settings.EXTRA_DO_NOT_DISTURB_MODE_ENABLED;
    public static final String extra_do_not_disturb_mode_minutes = Settings.EXTRA_DO_NOT_DISTURB_MODE_MINUTES;
    public static final String extra_airplane_mode_enabled = Settings.EXTRA_AIRPLANE_MODE_ENABLED;
    public static final String extra_battery_saver_mode_enabled = Settings.EXTRA_BATTERY_SAVER_MODE_ENABLED;

    //api-24
    public static final String vpn_settings = Settings.ACTION_VPN_SETTINGS;
    public static final String webview_settings = Settings.ACTION_WEBVIEW_SETTINGS;
    public static final String hard_keyboard_settings = Settings.ACTION_HARD_KEYBOARD_SETTINGS;
    public static final String vr_listener_settings = Settings.ACTION_VR_LISTENER_SETTINGS;

    public List<SystemIntent> getSystemIntents() {
        List<SystemIntent> systemIntents = new ArrayList<>();

        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.accessibility_settings),new Intent(accessibility_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.add_account),new Intent(add_account)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.airplane_mode_settings),new Intent(airplane_mode_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.wireless_settings),new Intent(wireless_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.apn_settings),new Intent(apn_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.application_development_settings),new Intent(application_development_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.application_settings),new Intent(application_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.manage_all_applications_settings),new Intent(manage_all_applications_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.manage_applications_settings),new Intent(manage_applications_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.bluetooth_settings),new Intent(bluetooth_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.data_roaming_settings),new Intent(data_roaming_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.device_info_settings),new Intent(device_info_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.display_settings),new Intent(display_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.dream_settings),new Intent(dream_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.input_method_settings),new Intent(input_method_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.input_method_subtype_settings),new Intent(input_method_subtype_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.internal_storage_settings),new Intent(internal_storage_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.memory_card_settings),new Intent(memory_card_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.locale_settings),new Intent(locale_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.location_source_settings),new Intent(location_source_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.network_operator_settings),new Intent(network_operator_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.nfc_settings),new Intent(nfc_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.privacy_settings),new Intent(privacy_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.quick_launch_settings),new Intent(quick_launch_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.search_settings),new Intent(search_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.security_settings),new Intent(security_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.settings),new Intent(settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.sync_settings),new Intent(sync_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.user_dictionary_settings),new Intent(user_dictionary_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.wifi_ip_settings),new Intent(wifi_ip_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.wifi_settings),new Intent(wifi_settings)));

        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.nfcsharing_settings),new Intent(nfcsharing_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.captioning_settings),new Intent(captioning_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.print_settings),new Intent(print_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.sound_settings),new Intent(sound_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.date_settings),new Intent(date_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.nfc_payment_settings),new Intent(nfc_payment_settings)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.extra_account_types),new Intent(extra_account_types)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.extra_authorities),new Intent(extra_authorities)));
        systemIntents.add(new SystemIntent(ResUtil.getString(R.string.extra_input_method_id),new Intent(extra_input_method_id)));



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.home_settings),new Intent(home_settings)));
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.cast_settings),new Intent(cast_settings)));
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.usage_access_settings),new Intent(usage_access_settings)));
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.voice_input_settings),new Intent(voice_input_settings)));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.battery_saver_settings),new Intent(battery_saver_settings)));
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.notification_listener_settings),new Intent(notification_listener_settings)));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.manage_write_settings),new Intent(manage_write_settings)));
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.ignore_battery_optimization_settings),new Intent(ignore_battery_optimization_settings)));
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.voice_control_airplane_mode),new Intent(voice_control_airplane_mode)));
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.voice_control_battery_saver_mode),new Intent(voice_control_battery_saver_mode)));
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.voice_control_do_not_disturb_mode),new Intent(voice_control_do_not_disturb_mode)));
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.extra_do_not_disturb_mode_enabled),new Intent(extra_do_not_disturb_mode_enabled)));
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.extra_do_not_disturb_mode_minutes),new Intent(extra_do_not_disturb_mode_minutes)));
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.extra_airplane_mode_enabled),new Intent(extra_airplane_mode_enabled)));
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.extra_battery_saver_mode_enabled),new Intent(extra_battery_saver_mode_enabled)));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.vpn_settings),new Intent(vpn_settings)));
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.webview_settings),new Intent(webview_settings)));
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.hard_keyboard_settings),new Intent(hard_keyboard_settings)));
            systemIntents.add(new SystemIntent(ResUtil.getString(R.string.vr_listener_settings),new Intent(vr_listener_settings)));
        }


        return systemIntents;
    }

}
