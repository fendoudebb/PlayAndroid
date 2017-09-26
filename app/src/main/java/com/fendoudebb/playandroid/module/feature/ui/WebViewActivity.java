package com.fendoudebb.playandroid.module.feature.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.base.activity.BaseActivity;

/**
 * author : zbj on 2017/9/26 22:03.
 */

@SuppressWarnings("deprecation")
public class WebViewActivity extends BaseActivity {
    private static final String TAG = "WebViewActivity";
    private WebView     mWebView;
    private ProgressBar mProgressView;
    private View mErrorView;

    @Override
    protected int initContentView() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        mWebView = findView(R.id.web_view);
        mProgressView = findView(R.id.web_view_progress_bar);
        mErrorView = findView(R.id.web_view_error_view);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initData() {

        mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        //listview,webview中滚动拖动到顶部或者底部时的阴影
        mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //取消滚动条白边效果
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//设置渲染优先级
        webSettings.setLoadWithOverviewMode(true);//缩放至屏幕大小,一定要设置
        webSettings.setJavaScriptEnabled(true);//支持JS
        webSettings.setAllowFileAccess(true);//设置是否允许文件访问，默认是允许的
        webSettings.setSaveFormData(true);//设置是否保存表单数据，默认是保存的
        webSettings.setSavePassword(true);//设置是否保存密码，默认是保存的，过时了
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webSettings.setUseWideViewPort(true);//是否启用支持视窗meta标记
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);// 固定页面字体大小
        webSettings.setGeolocationEnabled(true);//启用地理定位
        webSettings.setDatabaseEnabled(true);// 开启database storage API功能
        webSettings.setDomStorageEnabled(true);// 开启DOM storage API 功能
        String databasePath = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(databasePath);// 设置数据库缓存路径
        webSettings.setGeolocationDatabasePath(databasePath);//设置定位的数据库路径
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
        webSettings.setSupportZoom(true);//支持缩放
        webSettings.setBuiltInZoomControls(true);//设置支持缩放机制
        webSettings.setDisplayZoomControls(false);//设置显示缩放控件

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置当一个安全站点企图加载来自一个不安全站点资源时WebView的行为
            //MIXED_CONTENT_ALWAYS_ALLOW
            //MIXED_CONTENT_NEVER_ALLOW
            //MIXED_CONTENT_COMPATIBILITY_MODE
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebView.setOnLongClickListener(new View.OnLongClickListener() {//禁止长按跳出复制弹窗
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                try {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            private boolean isError = false;//signal

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                Log.d(TAG, "zbj onReceivedSslError");
                //handler.cancel(); 默认的处理方式，WebView变成空白页
                handler.proceed();//接受证书
            }

            //WebView重定向，多次返回键无效
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                WebView.HitTestResult hitTestResult = view.getHitTestResult();
                //hitTestResult==null解决重定向问题
                if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                    view.loadUrl(url);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String url) {
                isError = true;
                if (view != null){
                    view.stopLoading();
                    view.setVisibility(View.GONE);//NullPointerException
                }
                mErrorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isError) {
                    isError = false;
                } else {
                    if (view != null)
                        view.setVisibility(View.VISIBLE);//NullPointerException
                    mErrorView.setVisibility(View.GONE);
                }
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, final int progress) {
                mProgressView.post(new Runnable() {
                    @Override
                    public void run() {
                        mProgressView.setProgress(progress);
                        if (progress <= 100) {
                            mProgressView.setVisibility(View.VISIBLE);
                        }
                        if (progress >= 100) {
                            mProgressView.post(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressView.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                });
                super.onProgressChanged(view, progress);
            }

            @Override
            public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(5 * 1024 * 1024);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, true);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.stopLoading();
            mWebView.goBack(); //goBack()表示返回mWebView的上一页面
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
