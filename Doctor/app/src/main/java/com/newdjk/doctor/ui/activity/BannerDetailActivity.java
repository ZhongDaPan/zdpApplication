package com.newdjk.doctor.ui.activity;

import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;

import butterknife.BindView;


public class BannerDetailActivity extends BasicActivity {


    private static final String TAG = "BannerDetailActivity";
    @BindView(R.id.top_left)
    ImageView topLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_right)
    ImageView topRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.relat_titlebar)
    RelativeLayout relatTitlebar;
    @BindView(R.id.liear_titlebar)
    LinearLayout liearTitlebar;
    @BindView(R.id.mWebView)
    WebView mWebView;
    private String mContent;

    protected int initViewResId() {
        return R.layout.activity_banner;
    }

    @Override
    protected void initView() {
        initBackTitle("详情");

        mContent = getIntent().getStringExtra("banner");
        Log.d(TAG,"获取内容"+mContent);
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setTextZoom(100);  //消除系统大小的设置对webview字体大小的影响
        mWebView.getSettings().setDomStorageEnabled(true); //解决加载不出webview白屏问题
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDisplayZoomControls(false); //隐藏webview缩放按钮
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.setBackgroundColor(0); // 设置背景色
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadDataWithBaseURL(null, mContent, "text/html", "utf-8", null);


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }

    private String getWebContent(String content) {

        content = content.replace("<img", "<img style='max-width:100%';height:'auto'");
        /*return "<!DOCTYPE html>  \n" +
                "<head>  \n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />  \n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">   \n" +
                "<title> 标题 </title>  \n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/NewsDetail.css\">  \n" +
                "</head>  \n" +
                "<body>  \n" +

                content +

                "</body>  \n" +
                "</html>";*/

        return "<!DOCTYPE html>  \n" +
                        "<html> \n" +
                        "<head> \n" +
                        "<style type=\"text/css\"> \n" +
                        "body {margin:10;} \n" +
                        "</style> \n" +
                        "</head>  \n" +
                        "<body style='background:#ffffff!important;color:#ffffff;'> \n" +
                        "<script type='text/javascript'> \n" +
                        "window.onload = function(){ \n" +
                        "var $img = document.getElementsByTagName('img'); \n" +
                        "for(var p in  $img){ \n" +
                        "if($img[p].style.width > 100%%){ \n" +
                        "$img[p].style.width = '100%%'; \n" +
                        " $img[p].style.height ='auto' \n" +
                        " } \n" +
                        " } \n" +
                        "} \n" +
                        "</script> \n" +
                        content +
                        "</body> \n" +
                        "</html>";
    }
}
