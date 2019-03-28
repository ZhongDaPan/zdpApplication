package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class ConsultSettingActivity extends BasicActivity {

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
    @BindView(R.id.accepts_switch)
    SwitchButton acceptsSwitch;
    @BindView(R.id.accept_account)
    EditText acceptAccount;

    @BindView(R.id.consult_discount)
    EditText consultDiscount;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.consult_price)
    TextView consultPrice;
    @BindView(R.id.accepts_switch_text)
    TextView acceptsSwitchText;
    @BindView(R.id.accept_account_text)
    TextView acceptAccountText;
    @BindView(R.id.consult_price_text)
    TextView consultPriceText;
    @BindView(R.id.consult_discount_text)
    TextView consultDiscountText;
    @BindView(R.id.tv_price_desc)
    TextView tvPriceDesc;
    private Gson mGson;
    private ConsultSettingEntity mConsultSettingEntity;
    private String discount;
    private String numberSource;
    private String price;
    private int mDoctype;

    @Override
    protected int initViewResId() {
        return R.layout.consult_setting;
    }

    @Override
    protected void initView() {
        mDoctype = SpUtils.getInt(Contants.DocType, 0);
        if (mDoctype == 1) {
            initBackTitle(getString(R.string.consult_setting));
        } else if (mDoctype == 2) {
            acceptsSwitchText.setText("护理咨询开通开关");
            acceptAccountText.setText("每日护理数量");
            consultPriceText.setText("护理咨询单价");
            consultDiscountText.setText("护理咨询优惠价");
            initBackTitle("护理咨询设置");
            tvPriceDesc.setVisibility(View.GONE);

        }

        save.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mGson = new Gson();
        // Intent intent = getIntent();
       /* if (intent != null) {
            String title = intent.getStringExtra("title");
            topTitle.setText(title);
        }*/
        //获取图文问诊的数据
        getConsultSetting();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                discount = consultDiscount.getText().toString();
                numberSource = acceptAccount.getText().toString();
                price = consultPrice.getText().toString();
                if (!discount.equals("") && !numberSource.equals("") && !price.equals("")) {
                    mConsultSettingEntity.setPrice(discount);
                    mConsultSettingEntity.setOriginalPrice(price);
                    mConsultSettingEntity.setNumberSource(numberSource);
                    saveConsultSetting();
                } else {
                    toast("请把信息填写完整");
                }
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * 获取图文问诊的数据
     */
    private void getConsultSetting() {
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 2)));
        map.put("DoctorType", String.valueOf(SpUtils.getInt(Contants.DocType, 1)));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetConsultSetting).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ConsultSettingEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ConsultSettingEntity> entity) {

                if (entity.getCode() == 0) {
                    mConsultSettingEntity = entity.getData();
                    ConsultSettingActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int isOn = mConsultSettingEntity.isOn();
                            acceptsSwitch.setChecked(isOn == 0 ? false : true);
                            acceptAccount.setText(String.valueOf(mConsultSettingEntity.getNumberSource()));
                            consultPrice.setText(String.valueOf(mConsultSettingEntity.getOriginalPrice()));
                            consultDiscount.setText(String.valueOf(mConsultSettingEntity.getPrice()));
                            if (TextUtils.isEmpty(mConsultSettingEntity.getPriceRemark())){
                                tvPriceDesc.setVisibility(View.GONE);
                            }else {
                                tvPriceDesc.setText(""+mConsultSettingEntity.getPriceRemark());
                            }                        }
                    });
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

    private void saveConsultSetting() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("ServiceItemId", mConsultSettingEntity.getServiceItemId() + "");
        paramsMap.put("SericeItemCode", mConsultSettingEntity.getSericeItemCode() + "");
        paramsMap.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        paramsMap.put("DrName", SpUtils.getString(Contants.Name));
        paramsMap.put("OriginalPrice", mConsultSettingEntity.getOriginalPrice());
        paramsMap.put("NumberSource", mConsultSettingEntity.getNumberSource());
        paramsMap.put("Price", mConsultSettingEntity.getPrice());
        boolean checked = acceptsSwitch.isChecked();
        paramsMap.put("IsOn", checked ? "1" : "0");
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.SaveConsultSetting).headers(headMap).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
