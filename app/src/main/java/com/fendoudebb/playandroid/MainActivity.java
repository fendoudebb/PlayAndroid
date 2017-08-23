package com.fendoudebb.playandroid;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fendoudebb.playandroid.config.Constant;
import com.fendoudebb.playandroid.ui.activity.BaseActivity;
import com.fendoudebb.playandroid.ui.activity.SettingsActivity;
import com.fendoudebb.playandroid.util.SpUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity_zbj";
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawerLayout;

    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.main_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        return true;
    }

    @Override
    protected void initView() {
//        new RevealEffectUtil().createEnterRevealEffect(this);



        mToolbar.setLogo(R.drawable.vector_user_default_logo);
        mToolbar.setTitleMarginStart(30);
        setToolbarTitle("首页");

        mDrawerLayout = findView(R.id.drawer_layout);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,mToolbar, 0, 0);

        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);

        TextView view = findView(R.id.text_view);
        view.setOnClickListener(this);
    }

    @Override
    protected void initData() {
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
        boolean isNightTheme = SpUtil.with(this).getBoolean(Constant.NIGHT_THEME, false);
        if (isNightTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        SpUtil.with(this).putBoolean(Constant.NIGHT_THEME, !isNightTheme);
//        new RevealEffectUtil().createExitRevealEffect(this);

    }

    public void onClick2(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
