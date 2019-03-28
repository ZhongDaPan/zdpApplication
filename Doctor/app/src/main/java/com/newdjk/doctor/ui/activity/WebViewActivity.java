package com.newdjk.doctor.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.PictureMessageEntity;
import com.newdjk.doctor.utils.LogOutUtil;
import com.newdjk.doctor.utils.SpUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class WebViewActivity extends BasicActivity {


    private static final String TAG = "WebViewActivity";
    @BindView(R.id.test_bridge_webView)
    BridgeWebView testBridgeWebView;
    @BindView(R.id.view_cover)
    View viewCover;
    private String mUrl = "";
    private int type;
    private int id, mDoctorType;
    private int code;
    private String mName;
    private Gson mGson;


    private static final int LOADING_SUCCESS = 2;
    private String mPayType;
    private PictureMessageEntity mPictureList;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADING_SUCCESS:
                    loading(false);
                    viewCover.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };
    private String mPatientName;
    private int mImgId;
    private int medicalTempId;
    private String mServiceType;
    private String mRelationId;
    private String mDocId;
    private int mPatientId;
    private int index;
    private String mName2;
    private String mAccountId;
    private String mSex;
    private String mMobilePhone;

    @Override
    protected int initViewResId() {
        return R.layout.my_pharmacy_webview;
    }

    @Override
    protected void initView() {
        setFillWindow(getResources().getColor(R.color.theme), this);
        mGson = new Gson();
        Intent intent = getIntent();
        loading(true);
        if (intent != null) {
            type = intent.getIntExtra("type", 0);
            id = intent.getIntExtra("id", 0);
            code = intent.getIntExtra("code", 0);
            mName = intent.getStringExtra("doctorName");
            mDoctorType = intent.getIntExtra("doctorType", -1);
            index = intent.getIntExtra("index", 0);
            mImgId = intent.getIntExtra("imgId", 0);
            medicalTempId = intent.getIntExtra("medicalTempId", 0);
            mPatientName = intent.getStringExtra("patientName");
            mDocId = intent.getStringExtra("docId");
            mServiceType = intent.getStringExtra("serviceType"); //服务类型
            mRelationId = intent.getStringExtra("relationId");//服务id
            mPatientId = intent.getIntExtra("patientId", 0);//就诊人id
            mName2 = intent.getStringExtra("Name");//就诊人id
            mAccountId = intent.getStringExtra("AccountId");//就诊人id
            mSex = intent.getStringExtra("Sex");//就诊人id
            mMobilePhone = intent.getStringExtra("Mobile");//就诊人id

        }
        testBridgeWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        testBridgeWebView.clearCache(true);
        testBridgeWebView.clearHistory();
        testBridgeWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        testBridgeWebView.getSettings().setSupportZoom(true);
        testBridgeWebView.getSettings().setBuiltInZoomControls(true);
        testBridgeWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        testBridgeWebView.getSettings().setUseWideViewPort(true);
        testBridgeWebView.getSettings().setDisplayZoomControls(false);
        testBridgeWebView.getSettings().setTextZoom(100);  //消除系统大小的设置对webview字体大小的影响
        testBridgeWebView.getSettings().setDomStorageEnabled(true); //解决加载不出webview白屏问题
        testBridgeWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        testBridgeWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        testBridgeWebView.setBackgroundColor(0); // 设置背景色

        mGson = new Gson();
        mUrl = "file:///android_asset/kefu.html?userId=" + mAccountId + "&nickName=" + mName2 + "&sex=" + mSex + "&phone=" + mMobilePhone;

        testBridgeWebView.loadUrl(mUrl);
        sendNative();
        testBridgeWebView.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("zdp", "data=" + data);
                finish();
            }
        });
        testBridgeWebView.registerHandler("Preview", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                mPictureList = mGson.fromJson(data, PictureMessageEntity.class);
                Intent intent = new Intent(WebViewActivity.this, PictureViewerActivity.class);
                intent.putExtra("pic_all", (ArrayList<String>) mPictureList.getUrl());
                intent.putExtra("pic_position", mPictureList.getPosition());
                startActivity(intent);
            }
        });
        testBridgeWebView.registerHandler("APP", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {

            }
        });
        testBridgeWebView.registerHandler("tokenInvalid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LogOutUtil.getInstance().loginOut(WebViewActivity.this, true);
            }
        });

        testBridgeWebView.setWebViewClient(new BridgeWebViewClient(testBridgeWebView) {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mHandler.sendEmptyMessageDelayed(LOADING_SUCCESS, 400);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }

    public void sendNative() {
        String userInfo = SpUtils.getString(Contants.LoginJson);
        Log.i("DoctorPraiseActivity", "userInfo=" + userInfo);
        testBridgeWebView.callHandler("UserInfo", userInfo, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i("zdp", data);

                // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
            }

        });

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (testBridgeWebView.canGoBack()) {
                testBridgeWebView.goBack(); //goBack()表示返回WebView的上一页面
                return true;
            } else {
                finish();
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        if (testBridgeWebView != null) {
            ViewParent viewParent = testBridgeWebView.getParent();
            if (viewParent != null) {
                ((ViewGroup) viewParent).removeView(testBridgeWebView);
            }

            testBridgeWebView.stopLoading();
            testBridgeWebView.getSettings().setJavaScriptEnabled(false);
            testBridgeWebView.clearHistory();
            testBridgeWebView.clearView();
            testBridgeWebView.removeAllViews();
            try {
                testBridgeWebView.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        if (testBridgeWebView.canGoBack()) {
            testBridgeWebView.goBack(); //goBack()表示返回WebView的上一页面
        } else {
            finish();
        }
        super.onBackPressed();
    }
}


