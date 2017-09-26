package com.fendoudebb.playandroid.module.main;

import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.config.C;
import com.fendoudebb.playandroid.module.base.activity.BaseActivity;
import com.fendoudebb.playandroid.module.feature.FeaturesFragment;
import com.fendoudebb.playandroid.module.feature.ui.WebViewActivity;
import com.fendoudebb.playandroid.util.ActivityUtil;
import com.fendoudebb.playandroid.util.PhoneUtil;
import com.fendoudebb.playandroid.util.RevealEffectUtil;
import com.fendoudebb.playandroid.util.ShortCutUtil;
import com.fendoudebb.playandroid.util.SpUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener, NavigationView
        .OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity_zbj";
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout          mDrawerLayout;

    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.main_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        return true;
    }

    @Override
    protected void initView() {
        ShortCutUtil.addShortcut(getApplicationContext());

        new RevealEffectUtil().createEnterRevealEffect(this);

        mToolbar.setLogo(R.drawable.vector_user_default_logo);
        mToolbar.setTitleMarginStart(30);
        setToolbarTitle(getString(R.string.nav_home_page));

        mDrawerLayout = findView(R.id.drawer_layout);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, 0, 0);

        NavigationView navigationView = findView(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView userLogo = (ImageView) navigationView.findViewById(R.id.nav_user_logo);


        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);

        TextView view = findView(R.id.text_view);
        view.setOnClickListener(this);

        PackageManager pm = getPackageManager();

        FeatureInfo[] features = pm.getSystemAvailableFeatures(); //得到所有支援的硬件种类
        Log.d(TAG, "initView: feature:size:" + (features == null));
        for (FeatureInfo feature : features) {
            if (feature != null) {
                String name = feature.name;
                if (TextUtils.isEmpty(name)) {
                    Log.v(TAG, "name2: " + feature.reqGlEsVersion);
                } else {
                    Log.v(TAG, "name1: " + feature.name);
                }
            } else {
                Log.d(TAG, "空!");
            }
        }


    }

    @Override
    protected void initData() {
        String simOperatorName = PhoneUtil.getSimOperatorName();
        Log.d(TAG, "simOperatorName: " + simOperatorName);

        String operatorName = PhoneUtil.getSimOperator();
        Log.d(TAG, "operatorName: " + operatorName);

//        String imei = PhoneUtil.getIMEI();
//        String imsi = PhoneUtil.getIMSI();
//        Log.d(TAG, "imei: " + imei);
//        Log.d(TAG, "imsi: " + imsi);

//        String simSerialNumber = PhoneUtil.getSimSerialNumber();
//        Log.d(TAG, "simSerialNumber: " + simSerialNumber);

        String simCountryIso = PhoneUtil.getSimCountryIso();
        Log.d(TAG, "simCountryIso: " + simCountryIso);

//        String phoneNumber = PhoneUtil.getPhoneNumber();
        int phoneCount = PhoneUtil.getPhoneCount();
//        Log.d(TAG, "phoneNumber: " + phoneNumber);
        Log.d(TAG, "phoneCount: " + phoneCount);

//        String chineseOperatorName = PhoneUtil.getChineseOperatorName();
//        Log.d(TAG, "chineseOperatorName: " + chineseOperatorName);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
//            //如果是打开的，则关闭drawer
//            drawerLayout.closeDrawer(Gravity.LEFT);
//        }else {
//            //说明是关闭的，需要打开
//            drawerLayout.openDrawer(Gravity.LEFT);
//        }
        //更简单的做法
        mToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        /*Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);*/

//        ProgressDialog.show(this, null, "正在登录中,请稍等...");

        FeaturesFragment featuresFragment = FeaturesFragment.newInstance();
        ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), featuresFragment,
                R.id.contentFrame);

    }

    public void onClick2(View view) {
        /*Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);*/
//        int i = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission
//                .WRITE_EXTERNAL_STORAGE);
//        Log.d(TAG, "onClick2: i: " + i);

        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the action
        } else if (id == R.id.nav_my_collection) {

        } else if (id == R.id.nav_tool) {

        } else if (id == R.id.nav_theme) {
            boolean isNightTheme = SpUtil.with(this).getBoolean(C.config.night_theme, false);
            if (isNightTheme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            SpUtil.with(this).putBoolean(C.config.night_theme, !isNightTheme);
            new RevealEffectUtil().createExitRevealEffect(this);
        } else if (id == R.id.nav_author) {

        } else if (id == R.id.nav_open_source_framework) {

        } else if (id == R.id.nav_share_app) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
