package com.newdjk.doctor.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
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
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.PictureMessageEntity;
import com.newdjk.doctor.ui.entity.SignFinshEntity;
import com.newdjk.doctor.ui.entity.YWXListenerEntity;
import com.newdjk.doctor.utils.AppUtils;
import com.newdjk.doctor.utils.ImageBase64;
import com.newdjk.doctor.utils.LogOutUtil;
import com.newdjk.doctor.views.RememberPasswordDialog;
import com.newdjk.doctor.views.SelectedPictureDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.org.bjca.sdk.core.kit.BJCASDK;
import cn.org.bjca.sdk.core.kit.YWXListener;
import cn.org.bjca.sdk.core.values.ConstantParams;

public class PrescriptionActivity extends BasicActivity {
    @BindView(R.id.test_bridge_webView)
    BridgeWebView testBridgeWebView;
    @BindView(R.id.view_cover)
    View viewCover;
    private String mPrescriptionMessage;
    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调
    private static final int LOADING_SUCCESS = 2;
    private Uri imageUri;
    private String testMessage = "{\"doctor\":{\"Code\":0,\"Data\":{\"Token\":\"eyJpZCI6NDcsImlhdCI6MTUzNzM1MTY5NCwiZXhwIjoxNTM3MzUxNjk0LCJ0eXBlIjoyLCJjbGllbnQiOm51bGwsInJlZ2lzdHJhdGlvbklkIjpudWxsfQ.qSng8Q6W-ACEEvruV5A9y_dV6lyknEzgiM9aqUusHXU\",\"User\":{\"DepartmentId\":0,\"DoctorId\":47,\"DoctorName\":\"赵季\",\"DrType\":1,\"Mobile\":\"18603016010\",\"OrgId\":[1],\"Position\":0,\"Sex\":3}},\"Message\":\"登录成功\"},\"patient\":{\"ChatStatus\":0,\"Content\":\"ceshi \",\"CreateId\":0,\"CreateTime\":\"\",\"DiseaseId\":\"0\",\"DoctorId\":47,\"DoctorName\":\"赵季\",\"EndTime\":\"\",\"EndType\":0,\"EvaluationTime\":\"\",\"Id\":9,\"OrgId\":1,\"PatientId\":16,\"PatientInfo\":{\"AccountId\":10,\"Birthday\":\"1993-09-18 00:00:00\",\"CreateId\":0,\"Education\":0,\"Invalid\":0,\"Job\":0,\"MedicalType\":0,\"PatientId\":16,\"PatientName\":\"hu\",\"PatientSex\":1,\"PatientType\":0,\"Region\":0,\"UpdateId\":0},\"PatientName\":\"hu\",\"PayStatus\":0,\"PayTime\":\"\",\"StartTime\":\"2018-09-18 19:00:59\",\"Status\":0,\"Type\":1,\"UpdateTime\":\"\"}}";
    private SelectedPictureDialog mSelectedPictureDialog;
    private CallBackFunction mFunction;
    private Gson mGson;
    private PictureMessageEntity mPictureList;
    private String mId;
    private String mPrescriptionId;
    private String mRejectId;
    private String mIsCancel = "false";
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
        return R.layout.my_pharmacy_webview;
    }

    @Override
    protected void initView() {
        mGson = new Gson();
        loading(true);
        mPrescriptionMessage = getIntent().getStringExtra("prescriptionMessage");
        mId = getIntent().getStringExtra("action");
        mPrescriptionId = getIntent().getStringExtra("id");
        mRejectId = getIntent().getStringExtra("rejectId");
        Log.i("PrescriptionActivity", mPrescriptionMessage);
        testBridgeWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
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
        testBridgeWebView.setBackgroundColor(0); // 设置背景色
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
        if (mId != null && !mId.equals("")) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/PrescriptionLlist?id=" + mId);
        } else if (mRejectId != null && !mRejectId.equals("")) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/PrescriptionLlist?rejectId=" + mRejectId);
        } else if (mPrescriptionId != null && !mPrescriptionId.equals("")) {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/prescription?MPrescriptionId=" + mPrescriptionId);
        } else {
            testBridgeWebView.loadUrl("file:///android_asset/index.html#/prescription");
        }
        // Log.i("zdp","mPrescriptionMessage="+mPrescriptionMessage);
        sendNative(mPrescriptionMessage);
        testBridgeWebView.registerHandler("BackToIM", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
            }
        });
        testBridgeWebView.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
            }
        });
        testBridgeWebView.registerHandler("tokenInvalid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LogOutUtil.getInstance().loginOut(PrescriptionActivity.this, true);
            }
        });
        testBridgeWebView.registerHandler("backConfirm", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("zdp", "data=" + data);
                mIsCancel = data;
                // finish();
            }
        });
        testBridgeWebView.registerHandler("changePharmacy", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent chickUnitIntent = new Intent(PrescriptionActivity.this, ChickUnitActivity.class);
                startActivityForResult(chickUnitIntent, 5);
            }
        });
        testBridgeWebView.registerHandler("signId", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
               /* Intent intent = new Intent();
                intent.putExtra("uniqueId", data);
                setResult(RESULT_OK, intent);
                finish();*/
                mFunction = function;
                Log.i("PrescriptionActivity","data="+data);
                List<String> uniqueIds = new ArrayList<>();
                uniqueIds.add(data);

                boolean	isPinExempt=	BJCASDK.getInstance().isPinExempt(PrescriptionActivity.this);
                showRememberDialog(PrescriptionActivity.this,uniqueIds,isPinExempt);
            }
        });
        testBridgeWebView.registerHandler("Preview", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                mPictureList = mGson.fromJson(data, PictureMessageEntity.class);
                Intent intent = new Intent(PrescriptionActivity.this, PictureViewerActivity.class);
                intent.putExtra("pic_all", (ArrayList<String>) mPictureList.getUrl());
                intent.putExtra("pic_position", mPictureList.getPosition());
                startActivity(intent);
            }
        });
        testBridgeWebView.registerHandler("APP", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (data != null && data.equals("Img")) {
                    mFunction = function;
                    mSelectedPictureDialog = new SelectedPictureDialog(PrescriptionActivity.this, "first");
                    mSelectedPictureDialog.show();
                }
            }
        });

        testBridgeWebView.registerHandler("SendMessage", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();

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
                            BJCASDK.getInstance().sign(PrescriptionActivity.this, Contants.clientId, uniqueIds, new YWXListener() {
                                @Override
                                public void callback(String result) {
                                    if (result != null) {
                                        SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                                        String status = signFinshEntity.getStatus();
                                        if (status.equals("0")) {
                                            mFunction.onCallBack("true");
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
            BJCASDK.getInstance().sign(PrescriptionActivity.this, Contants.clientId, uniqueIds, new YWXListener() {
                @Override
                public void callback(String result) {
                    if (result != null) {
                        SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                        String status = signFinshEntity.getStatus();
                        if (status.equals("0")) {
                            mFunction.onCallBack("true");
                        } else {
                            toast(signFinshEntity.getMessage());
                        }
                    }
                }
            });
        }
    }
  /*  public  void showRememberDialog (final Activity context,final List<String> uniqueIds) {
        RememberPasswordDialog dialog = new RememberPasswordDialog(context);
        dialog.show("", "", new RememberPasswordDialog.DialogListener() {
            @Override
            public void cancel() {

            }

            @Override
            public void confirm(int keeDay) {
                BJCASDK.getInstance().keepPin(context,Contants.clientId,keeDay,	new	YWXListener()	{
                    @Override
                    public	void	callback(String	msg)	{
                        YWXListenerEntity yWXListenerEntity = new Gson().fromJson(msg, YWXListenerEntity.class);
                        String status = yWXListenerEntity.getStatus();
                        String message = yWXListenerEntity.getMessage();
                        if (status.equals("0")) {
                            Toast.makeText(context,"设置免密成功",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                        BJCASDK.getInstance().sign(PrescriptionActivity.this,	Contants.clientId,	uniqueIds,	new	YWXListener()	{
                            @Override
                            public	void callback(String	result)	{
                                if (result != null) {
                                    SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                                    String status = signFinshEntity.getStatus();
                                    if (status.equals("0")) {
                                        mFunction.onCallBack("true");
                                    }
                                    else {
                                        toast(signFinshEntity.getMessage());
                                    }
                                }
                            }
                        });
                    }
                });
            }
        });
    }*/
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

    public void sendNative(String prescriptionMessage) {
        testBridgeWebView.callHandler("UserInfo", prescriptionMessage, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

                // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            testBridgeWebView.callHandler("confirmBack", "nimei", new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                    Log.i("MypharmacyActivity", "1111");
                    //    mIsCancel = true;
                    // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
                }

            });
            if (mIsCancel.equals("true")) {

            } else {
                if (testBridgeWebView.canGoBack()) {
                    testBridgeWebView.goBack(); //goBack()表示返回WebView的上一页面
                    return true;
                } else {
                    finish();
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5) {
            testBridgeWebView.callHandler("pharmacyChange", "NIMEI", new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                    Log.i("zdp", data);

                    // Toast.makeText(PrescriptionActivity.this, "buttonjs--->，"+ data, Toast.LENGTH_SHORT).show();
                }

            });
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    uploadPicture(mSelectedPictureDialog.getPicturePath());
                    break;
                case 3:
                    Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String path = cursor.getString(columnIndex);  //获取照片路径
                    uploadPicture(path);
                    break;
                case ConstantParams.ACTIVITY_SIGN_DATA:
             /*  BJCASDK.getInstance().getUserInfo(ChatActivity.this, Contants.clientId, new OperateListener() {
                    @Override
                    public void callback(String s) {
                        Log.i("zdp", "result=" + s);
                    }
                });*/
                    String result = null;
                    try {
                        result = data.getStringExtra(ConstantParams.KEY_SIGN_BACK);
                        if (result != null) {
                            SignFinshEntity signFinshEntity = mGson.fromJson(result, SignFinshEntity.class);
                            String status = signFinshEntity.getStatus();
                            if (status.equals("0")) {
                                mFunction.onCallBack("true");
                            } else {
                                toast(signFinshEntity.getMessage());
                            }
                        }
                        Log.i("Prescription", "result=" + result);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                    break;
            }
        }
    }

    private void uploadPicture(String path) {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String image64 = ImageBase64.bitmapToString(strings[0]);
                return image64;
            }

            @Override
            protected void onPostExecute(String s) {
                Map<String, String> map = new HashMap<>();

                map.put("Base64Str", s);
                mMyOkhttp.post().url(HttpUrl.ReportImageUpload).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                    @Override
                    public void onSuccess(int statusCode, Entity entituy) {
                        if (entituy.getCode() == 0) {
                            mFunction.onCallBack(entituy.getData().toString());
                        } else {
                            toast(entituy.getMessage());
                        }

                       /* DoctorRegImgEntity doctorRegImgEntity = new DoctorRegImgEntity();
                        if (imageType>3) {
                            if (!practiceLicense.getText().toString().equals("")) {
                                doctorRegImgEntity.setNumber(practiceLicense.getText().toString());
                            }
                            if (registerDate.getText().toString().equals("")) {
                                doctorRegImgEntity.setRegisterTime(registerDate.getText().toString());
                            }
                            if(registerValidity.getText().toString().equals("")) {
                                doctorRegImgEntity.setValidTime(registerValidity.getText().toString());
                            }
                        }
                        doctorRegImgEntity.setImgPath(entituy.getData().getSavePath());
                        doctorRegImgEntity.setImgType(imageType);
                        mList.add(doctorRegImgEntity);*/
                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        CommonMethod.requestError(statusCode, errorMsg);
                    }
                });
            }
        }.execute(path);

    }


}
