package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.entity.ConsultSettingEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.SpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoInterrogationSettingActivity extends BasicActivity {

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
    @BindView(R.id.interratin_switch)
    SwitchButton interratinSwitch;
    @BindView(R.id.accept_account)
    EditText acceptAccount;
    @BindView(R.id.interration_price)
    EditText interrationPrice;
    @BindView(R.id.interration_discount)
    EditText interrationDiscount;
    @BindView(R.id.interration_schedule)
    RelativeLayout interrationSchedule;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.accepts_switch_text)
    TextView acceptsSwitchText;
    @BindView(R.id.accept_account_text)
    TextView acceptAccountText;
    @BindView(R.id.interration_price_text)
    TextView interrationPriceText;
    @BindView(R.id.interration_discount_text)
    TextView interrationDiscountText;
    @BindView(R.id.interration_schedule_text)
    TextView interrationScheduleText;
    @BindView(R.id.tv_price_desc)
    TextView tvPriceDesc;
    private ConsultSettingEntity mConsultSettingEntity;
    private Gson mGson;
    private int mDoctype;

    @Override
    protected int initViewResId() {
        return R.layout.video_interrogation_setting;
    }

    @Override
    protected void initView() {
        mDoctype = SpUtils.getInt(Contants.DocType, 0);
        if (mDoctype == 1) {
            initBackTitle("视频问诊设置");
        } else if (mDoctype == 2) {
            initBackTitle("远程护理设置");
            acceptsSwitchText.setText("远程护理开通开关");
            acceptAccountText.setText("每日远程数量");
            interrationPriceText.setText("远程护理单价");
            interrationDiscountText.setText("远程护理优惠价");
            interrationScheduleText.setText("远程护理排班");
        }

        interrationSchedule.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        getInquirySetting();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                String discount = interrationDiscount.getText().toString().trim();
                String numberSource = acceptAccount.getText().toString().trim();
                String price = interrationPrice.getText().toString().trim();
                if (!discount.equals("") && !numberSource.equals("") && !price.equals("")) {
                    mConsultSettingEntity.setPrice(discount);
                    mConsultSettingEntity.setOriginalPrice(price);
                    mConsultSettingEntity.setNumberSource(numberSource);
                    saveInquirySetting();
                } else {
                    toast("请把信息填写完整");
                }
                break;
            case R.id.interration_schedule:
                Intent ScheduleIntent = new Intent(VideoInterrogationSettingActivity.this, ScheduleActivity.class);
                startActivity(ScheduleIntent);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void getInquirySetting() {
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 2)));
        map.put("DoctorType", String.valueOf(SpUtils.getInt(Contants.DocType, 1)));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetInquirySetting).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ConsultSettingEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ConsultSettingEntity> entity) {
                if (entity.getCode() == 0) {
                    mConsultSettingEntity = entity.getData();
                    VideoInterrogationSettingActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int isOn = mConsultSettingEntity.isOn();
                            interratinSwitch.setChecked(isOn == 0 ? false : true);
                            acceptAccount.setText(String.valueOf(mConsultSettingEntity.getNumberSource()));
                            interrationPrice.setText(String.valueOf(mConsultSettingEntity.getOriginalPrice()));
                            interrationDiscount.setText(String.valueOf(mConsultSettingEntity.getPrice()));
                            tvPriceDesc.setText(""+mConsultSettingEntity.getPriceRemark());


                        }
                    });
                } else {
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("zdp", "errorMsg=" + errorMsg + ",statusCode=" + statusCode);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mGson = new Gson();
    }

    private void saveInquirySetting() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("ServiceItemId", mConsultSettingEntity.getServiceItemId() + "");
        paramsMap.put("SericeItemCode", mConsultSettingEntity.getSericeItemCode() + "");
        paramsMap.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        paramsMap.put("DrName", SpUtils.getString(Contants.Name));
        paramsMap.put("OriginalPrice", mConsultSettingEntity.getOriginalPrice());
        paramsMap.put("NumberSource", mConsultSettingEntity.getNumberSource());
        paramsMap.put("Price", mConsultSettingEntity.getPrice());
        boolean checked = interratinSwitch.isChecked();
        paramsMap.put("IsOn", checked ? "1" : "0");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.SaveInquirySetting).headers(headMap).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                if (entity.getCode() == 0) {
                    toast("保存成功");
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }
}
