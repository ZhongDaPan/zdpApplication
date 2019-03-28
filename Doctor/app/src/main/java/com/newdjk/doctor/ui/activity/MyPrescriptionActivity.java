package com.newdjk.doctor.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.SignFinshEntity;
import com.newdjk.doctor.ui.entity.UpdatePatientViewEntity;
import com.newdjk.doctor.ui.entity.YWXListenerEntity;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.LogOutUtil;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.ToastUtil;
import com.newdjk.doctor.views.RememberPasswordDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.kit.YWXListener;
import cn.org.bjca.sdk.core.values.ConstantParams;

public class MyPrescriptionActivity extends BasicActivity {
    private static final String TAG = "MyPrescriptionActivity";
    @BindView(R.id.test_bridge_webView)
    BridgeWebView testBridgeWebView;
    @BindView(R.id.view_cover)
    View viewCover;
    private int mAction;
    private static final int LOADING_SUCCESS = 2;
    private Gson mGson;
    private CallBackFunction mFunction;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
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

    @Override
    protected int initViewResId() {
        return R.layout.my_pharmacy;
    }


    @Override
    protected void initView() {
        mGson = new Gson();
        testBridgeWebView.clearCache(true);
        testBridgeWebView.clearHistory();
        testBridgeWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        testBridgeWebView.getSettings().setSupportZoom(true);
        testBridgeWebView.getSettings().setBuiltInZoomControls(true);
        testBridgeWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        testBridgeWebView.getSettings().setUseWideViewPort(true);
        testBridgeWebView.getSettings().setTextZoom(100);  //消除系统大小的设置对webview字体大小的影响
        testBridgeWebView.getSettings().setDomStorageEnabled(true); //解决加载不出webview白屏问题
        testBridgeWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        testBridgeWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        testBridgeWebView.setBackgroundColor(0); // 设置背


        testBridgeWebView.loadUrl("file:///android_asset/index.html#/my-prescription");
        sendNative();
        testBridgeWebView.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("zdp", "data=" + data);
                finish();
            }
        });
        testBridgeWebView.registerHandler("tokenInvalid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LogOutUtil.getInstance().loginOut(MyPrescriptionActivity.this, true);
            }
        });
        testBridgeWebView.registerHandler("BackToIM", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
            }
        });
        testBridgeWebView.setWebViewClient(new BridgeWebViewClient(testBridgeWebView) {

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

        testBridgeWebView.registerHandler("signId", new BridgeHandler() {
            @Override
            public void handler(final String data,final CallBackFunction function) {
                boolean isExists = BJCASDK.getInstance().existsCert(MyPrescriptionActivity.this);
                boolean ExistStamp = BJCASDK.getInstance().existsStamp(MyPrescriptionActivity.this);
                /// BJCASDK.getInstance().clearCert(ChatActivity.this);
                // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 3);
                // BJCASDK.getInstance().startUrl(ChatActivity.this, Contants.clientId, 1);
                if (!isExists) {
                    BJCASDK.getInstance().certDown(MyPrescriptionActivity.this, Contants.clientId, SpUtils.getString(Contants.userName), new YWXListener() {
                        @Override
                        public void callback(String s) {
                            YWXListenerEntity yWXListenerEntity = mGson.fromJson(s, YWXListenerEntity.class);
                            String status = yWXListenerEntity.getStatus();
                            String message = yWXListenerEntity.getMessage();
                            if (status != null && status.equals("0")) {
                                boolean ExistStamp1 = BJCASDK.getInstance().existsStamp(MyPrescriptionActivity.this);
                                if (!ExistStamp1) {
                                    BJCASDK.getInstance().drawStamp(MyPrescriptionActivity.this,	Contants.clientId,	new	YWXListener()	{
                                        @Override
                                        public	void callback(String msg)	{
                                            YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                                            String status = yWXListenerEntity.getStatus();
                                            String message = yWXListenerEntity.getMessage();
                                            if (status != null && status.equals("0")) {
                                                String signId = data;
                                                List<String> uniqueIds = new ArrayList<>();
                                                uniqueIds.add(signId);
                                                boolean	isPinExempt=	BJCASDK.getInstance().isPinExempt(MyPrescriptionActivity.this);
                                                showRememberDialog(MyPrescriptionActivity.this,uniqueIds,isPinExempt);
                                            }
                                            else {
                                                ToastUtil.setToast(message);
                                            }
                                        }
                                    });
                                } else {
                                    String signId = data;
                                    List<String> uniqueIds = new ArrayList<>();
                                    uniqueIds.add(signId);
                                    boolean	isPinExempt=	BJCASDK.getInstance().isPinExempt(MyPrescriptionActivity.this);
                                    showRememberDialog(MyPrescriptionActivity.this,uniqueIds,isPinExempt);
                                }
                            }
                            else {
                                ToastUtil.setToast(message);
                            }
                        }

                    });
                } else {
                    //   BJCASDK.getInstance().clearCert(ChatActivity.this);
                    Log.i("zdp", "证书已下载");
                }
                if (!ExistStamp) {
                    BJCASDK.getInstance().drawStamp(MyPrescriptionActivity.this,	Contants.clientId,	new	YWXListener()	{
                        @Override
                        public	void callback(String msg)	{
                            YWXListenerEntity yWXListenerEntity = mGson.fromJson(msg, YWXListenerEntity.class);
                            String status = yWXListenerEntity.getStatus();
                            String message = yWXListenerEntity.getMessage();
                            if (status != null && status.equals("0")) {
                                String signId = data;
                                List<String> uniqueIds = new ArrayList<>();
                                uniqueIds.add(signId);
                                boolean	isPinExempt=	BJCASDK.getInstance().isPinExempt(MyPrescriptionActivity.this);
                                showRememberDialog(MyPrescriptionActivity.this,uniqueIds,isPinExempt);
                            }
                            else {
                                ToastUtil.setToast(message);
                            }
                        }
                    });
                } else {
                    String signId = data;
                    List<String> uniqueIds = new ArrayList<>();
                    uniqueIds.add(signId);
                    boolean	isPinExempt=	BJCASDK.getInstance().isPinExempt(MyPrescriptionActivity.this);
                    showRememberDialog(MyPrescriptionActivity.this,uniqueIds,isPinExempt);

                }
             //   Log.d(TAG, "验证证书" + data);
              /*  boolean credentialIsExists = BJCASDK.getInstance().existsCert(MyPrescriptionActivity.this);
                boolean credentialExistStamp = BJCASDK.getInstance().existsStamp(MyPrescriptionActivity.this);
                /// BJCASDK.getInstance().clearCert(ChatActivity.this);
                if (!credentialIsExists) {
                    BJCASDK.getInstance().startUrl(MyPrescriptionActivity.this, Contants.clientId, 1, SpUtils.getString(Contants.userName));
                } else {
                    //   BJCASDK.getInstance().clearCert(ChatActivity.this);
                    Log.i("zdp", "证书已下载");
                }
                if (!credentialExistStamp) {
                    BJCASDK.getInstance().startUrl(MyPrescriptionActivity.this, Contants.clientId, 3);
                } else {
                    int result = BJCASDK.getInstance().signRecipe(MyPrescriptionActivity.this, Contants.clientId, data);

                    if (result != ConstantParams.CALL_SUCCESS) {
                        //调用失败，可以根据集成文档查看失败原因
                        toast("调用失败！错误返回码为:" + result);
                    } else {
                        function.onCallBack("成功");
                    }
                }*/
            }
        });
    }

    public  void showRememberDialog (final Activity context, final List<String> uniqueIds,boolean isPinExempt) {
        if (!isPinExempt) {
            RememberPasswordDialog dialog = new RememberPasswordDialog(context);
            dialog.show("", "", new RememberPasswordDialog.DialogListener() {
                @Override
                public void cancel() {

                }

                @Override
                public void confirm(int keeDay) {
                    BJCASDK.getInstance().keepPin(context, Contants.clientId, keeDay, new YWXListener() {
                        @Override
                        public void callback(String msg) {
                            YWXListenerEntity yWXListenerEntity = new Gson().fromJson(msg, YWXListenerEntity.class);
                            String status = yWXListenerEntity.getStatus();
                            String message = yWXListenerEntity.getMessage();
                            if (status.equals("0")) {
                                Toast.makeText(context, "设置免密成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                            BJCASDK.getInstance().sign(MyPrescriptionActivity.this, Contants.clientId, uniqueIds, new YWXListener() {
                                @Override
                                public void callback(String result) {
                                    if (result != null) {
                                        SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                                        String status = signFinshEntity.getStatus();
                                        if (status.equals("0")) {
                                            mFunction.onCallBack("成功");
                                        } else {
                                            toast(signFinshEntity.getMessage());
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
            });
        }
        else {
            BJCASDK.getInstance().sign(MyPrescriptionActivity.this, Contants.clientId, uniqueIds, new YWXListener() {
                @Override
                public void callback(String result) {
                    if (result != null) {
                        SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                        String status = signFinshEntity.getStatus();
                        if (status.equals("0")) {
                            mFunction.onCallBack("成功");
                        } else {
                            toast(signFinshEntity.getMessage());
                        }
                    }
                }
            });
        }
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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public void sendNative() {
        String userInfo = SpUtils.getString(Contants.LoginJson);
        testBridgeWebView.callHandler("UserInfo", userInfo, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i("zdp", data);
            }

        });

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
        EventBus.getDefault().post(new UpdatePatientViewEntity(true));
        super.onDestroy();
    }
}
