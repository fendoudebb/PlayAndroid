package com.fendoudebb.playandroid.module.main;

import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.SimpleDrawerListener;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.config.C;
import com.fendoudebb.playandroid.module.base.activity.BaseActivity;
import com.fendoudebb.playandroid.module.media.ui.MusicListActivity;
import com.fendoudebb.playandroid.module.main.activity.NavDetailActivity;
import com.fendoudebb.playandroid.module.main.fragment.HomeFragment;
import com.fendoudebb.playandroid.util.ActivityUtil;
import com.fendoudebb.playandroid.util.RevealEffectUtil;
import com.fendoudebb.playandroid.util.ShortCutUtil;
import com.fendoudebb.playandroid.util.SpUtil;

public class MainActivity extends BaseActivity implements NavigationView
        .OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity_zbj";
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout          mDrawerLayout;

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

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
        HomeFragment homeFragment = HomeFragment.newInstance();
        ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), homeFragment,
                R.id.main_container);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    SimpleDrawerListener mDrawerCloseListener = new SimpleDrawerListener() {
        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            mDrawerLayout.removeDrawerListener(mDrawerCloseListener);
            handlerNavigationItemSelectedEvent(getMenuItem());
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        setMenuItem(item);
        mDrawerLayout.closeDrawer(GravityCompat.START);

        mDrawerLayout.addDrawerListener(mDrawerCloseListener);

        return true;
    }

    private MenuItem mMenuItem;

    private MenuItem getMenuItem() {
        return mMenuItem;
    }

    private void setMenuItem(MenuItem item) {
        mMenuItem = item;
    }

    private void handlerNavigationItemSelectedEvent(MenuItem item) {
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
            Intent intent = new Intent(this, MusicListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_open_source_framework) {
            Intent intent = new Intent(this, NavDetailActivity.class);
            intent.putExtra(C.intent.nav_name_id, R.string.nav_open_source_framework);
            startActivity(intent);
        } else if (id == R.id.nav_share_app) {
            Intent intent = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText("https://github.com/fendoudebb/PlayAndroid")
                    .getIntent();
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

}
