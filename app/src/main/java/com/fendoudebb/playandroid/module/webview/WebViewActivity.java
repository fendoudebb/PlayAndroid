package com.fendoudebb.playandroid.module.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.support.v4.app.ShareCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.ClientCertRequest;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fendoudebb.activity.BaseActivity;
import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.config.C;
import com.fendoudebb.util.ClipboardUtil;
import com.fendoudebb.util.KeyBoardUtil;
import com.fendoudebb.util.ToastUtil;

/**
 * author : zbj on 2017/9/26 22:03.
 */

@SuppressWarnings("deprecation")
public class WebViewActivity extends BaseActivity implements View.OnClickListener, TextWatcher,
        TextView.OnEditorActionListener {
    private static final String TAG = "WebViewActivity-zbj";

    private static final String SCHEME_HTTP = "http";

    private WebView     mWebView;
    private ProgressBar mProgressView;
    private View        mErrorView;
    private EditText    mAddress;
    private View        mAddressDelete;
    private ImageView   mIcon;
    private TextView mWebViewTitle;
    private View mWebViewTitleView;
    private View mWebViewUrlView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_web_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String url = mWebView.getUrl();
        if (TextUtils.isEmpty(url)) {
            if (item.getItemId() != android.R.id.home) {
                ToastUtil.showToast(R.string.web_view_empty_link);
            }
            return super.onOptionsItemSelected(item);
        }
        Intent intent;
        switch (item.getItemId()) {
            case R.id.web_view_copy_link:
                ClipboardUtil.copyText(url);
                ToastUtil.showToast(R.string.web_view_clip_success);
                break;
            case R.id.web_view_go_browser:
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
                break;
            case R.id.web_view_share_link:
                intent = ShareCompat.IntentBuilder.from(this)
                        .setType("text/plain")
                        .setText(mWebView.getUrl())
                        .getIntent();
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        mWebView = findView(R.id.web_view);
        mProgressView = findView(R.id.web_view_progress_bar);
        mErrorView = findView(R.id.web_view_error_view);
        mIcon = findView(R.id.web_view_icon);
        mAddress = findView(R.id.web_view_address);
        mAddressDelete = findView(R.id.web_view_address_delete);

        mWebViewTitleView = findView(R.id.web_view_title_view);
        mWebViewUrlView = findView(R.id.web_view_url_view);
        mWebViewTitle = findView(R.id.web_view_title);

        findView(R.id.web_view_refresh).setOnClickListener(this);
        mAddress.addTextChangedListener(this);
        mAddress.setOnEditorActionListener(this);
        mAddressDelete.setOnClickListener(this);

        mAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "onFocusChange() called with: v = [" + v + "], hasFocus = [" +
                        hasFocus + "]");
            }
        });

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
        String databasePath = this.getApplicationContext().getDir("database", Context
                .MODE_PRIVATE).getPath();
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

        mWebView.setDownloadListener(new WebViewDownloadListener());

        mWebView.setWebViewClient(new WVC());

        mWebView.setWebChromeClient(new WCC());

        Intent intent = getIntent();
        String url = intent.getStringExtra(C.intent.web_view_url);

        mWebView.loadUrl(url);
        mAddress.setText(url);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            boolean clickDeleteIcon = isClickDeleteIcon(mAddressDelete, ev);
            if (clickDeleteIcon) {
                return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    protected void onKeyboardHide() {
        mWebViewTitleView.setVisibility(View.VISIBLE);
        mWebViewUrlView.setVisibility(View.INVISIBLE);
    }

    protected void onKeyboardShow() {
        mWebViewUrlView.setVisibility(View.VISIBLE);
        mWebViewTitleView.setVisibility(View.INVISIBLE);
    }

    private boolean isClickDeleteIcon(View v, MotionEvent event) {
        int[] leftTop = {0, 0};
        // 获取输入框当前的location位置
        v.getLocationInWindow(leftTop);
        int left = leftTop[0];
        int top = leftTop[1];
        int bottom = top + v.getHeight();
        int right = left + v.getWidth();
        return (event.getX() > left && event.getX() < right
                && event.getY() > top && event.getY() < bottom);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                mWebView.stopLoading();
                mWebView.goBack();//返回上一页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.web_view_address_delete:
                mAddress.setText("");
                break;
            case R.id.web_view_refresh:
                mWebView.reload();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            mAddressDelete.setVisibility(View.VISIBLE);
        } else {
            mAddressDelete.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            String address = mAddress.getText().toString().trim();
            if (!address.startsWith(SCHEME_HTTP)) {
                address = SCHEME_HTTP.concat("://").concat(address);
            }
            mWebView.loadUrl(address);
            KeyBoardUtil.hideSoftInput(mAddress);
        }
        return true;
    }

    private class WebViewDownloadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                    String mimetype, long contentLength) {
            Log.d(TAG, "onDownloadStart() called with: url = [" + url + "], userAgent = [" +
                    userAgent + "], contentDisposition = [" + contentDisposition + "], " +
                    "mimetype = [" + mimetype + "], contentLength = [" + contentLength + "]");
            try {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class WVC extends WebViewClient {
        private boolean isError = false;//signal

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Log.d(TAG, "zbj onReceivedSslError");
            //handler.cancel(); 默认的处理方式，WebView变成空白页
            handler.proceed();//接受证书
        }

        //WebView重定向，多次返回键无效
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "shouldOverrideUrlLoading() called with: view = [" + view + "], url = " +
                    "[" + url + "]");
            mAddress.setText(url);
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
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Log.d(TAG, "shouldOverrideUrlLoading() called with: view = [" + view + "], request = " +
                    "[" + request + "]");
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String url) {
            Log.d(TAG, "onReceivedError() called with: view = [" + view + "], errorCode = ["
                    + errorCode + "], description = [" + description + "], url = [" + url +
                    "]");
            isError = true;
            if (view != null) {
                view.stopLoading();
                view.setVisibility(View.GONE);//NullPointerException
            }
            mErrorView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            Log.d(TAG, "onLoadResource() called with: view = [" + view + "], url = [" + url +
                    "]");
        }

        @Override
        public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
            super.onTooManyRedirects(view, cancelMsg, continueMsg);
            Log.d(TAG, "onTooManyRedirects() called with: view = [" + view + "], cancelMsg = " +
                    "[" + cancelMsg + "], continueMsg = [" + continueMsg + "]");
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            Log.d(TAG, "onPageCommitVisible() called with: view = [" + view + "], url = [" +
                    url + "]");
        }

        @Override
        public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
            super.onReceivedClientCertRequest(view, request);
            Log.d(TAG, "onReceivedClientCertRequest() called with: view = [" + view + "], " +
                    "request = [" + request + "]");
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d(TAG, "onPageStarted() called with: view = [" + view + "], url = [" + url +
                    "], favicon = [" + favicon + "]");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d(TAG, "onPageFinished() called with: view = [" + view + "], url = [" + url +
                    "]");
            super.onPageFinished(view, url);
            if (isError) {
                isError = false;
            } else {
                if (view != null)
                    view.setVisibility(View.VISIBLE);//NullPointerException
                mErrorView.setVisibility(View.GONE);
            }
        }

    }

    private class WCC extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.d(TAG, "onReceivedTitle() called with: view = [" + view + "], title = [" + title
                    + "]");
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
            Log.d(TAG, "onReceivedIcon() called with: view = [" + view + "], icon = [" + icon +
                    "]");
            mIcon.setImageBitmap(icon);
        }

        @Override
        public void onPermissionRequest(PermissionRequest request) {
            super.onPermissionRequest(request);
        }

        @Override
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
            Log.d(TAG, "onGeolocationPermissionsHidePrompt() called");
        }

        @Override
        public void onProgressChanged(WebView view, final int progress) {
            Log.d(TAG, "onProgressChanged() called with: view = [" + view + "], progress = ["
                    + progress + "]");
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
        public void onExceededDatabaseQuota(String url, String databaseIdentifier, long
                currentQuota, long estimatedSize, long totalUsedQuota, WebStorage.QuotaUpdater
                quotaUpdater) {
            Log.d(TAG, "onExceededDatabaseQuota() called with: url = [" + url + "], " +
                    "databaseIdentifier = [" + databaseIdentifier + "], currentQuota = [" +
                    currentQuota + "], estimatedSize = [" + estimatedSize + "], " +
                    "totalUsedQuota = [" + totalUsedQuota + "], quotaUpdater = [" +
                    quotaUpdater + "]");
            quotaUpdater.updateQuota(5 * 1024 * 1024);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions
                .Callback callback) {
            Log.d(TAG, "onGeolocationPermissionsShowPrompt() called with: origin = [" +
                    origin + "], callback = [" + callback + "]");
            callback.invoke(origin, true, true);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    }


}
