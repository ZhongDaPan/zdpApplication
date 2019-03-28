package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
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

public class OnlinePartySettingActivity extends BasicActivity {

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
    @BindView(R.id.party_switch)
    SwitchButton partySwitch;
    @BindView(R.id.accept_account)
    EditText acceptAccount;
    @BindView(R.id.party_price)
    EditText partyPrice;
    @BindView(R.id.party_discount)
    EditText partyDiscount;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.tv_price_desc)
    TextView tvPriceDesc;
    private ConsultSettingEntity mConsultSettingEntity;
    private Gson mGson;
    private boolean toggle;

    @Override
    protected int initViewResId() {
        return R.layout.online_party_setting;
    }

    @Override
    protected void initView() {
        initBackTitle(getString(R.string.party_setting));
        save.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            toggle = getIntent().getBooleanExtra("toggle", false);
        }

        mConsultSettingEntity = new ConsultSettingEntity();
        getPartySetting();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                String discount = partyDiscount.getText().toString();
                String numberSource = acceptAccount.getText().toString();
                String price = partyPrice.getText().toString();
                if (!discount.equals("") && !numberSource.equals("") && !price.equals("")) {
                    mConsultSettingEntity.setPrice(discount);
                    mConsultSettingEntity.setOriginalPrice(price);
                    mConsultSettingEntity.setNumberSource(numberSource);
                    savePartySetting();
                } else {
                    toast("请把信息填写完整");
                }
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mGson = new Gson();
    }

    private void getPartySetting() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 2)));
        mMyOkhttp.post().url(HttpUrl.GetPrescriptionSetting).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ConsultSettingEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ConsultSettingEntity> entity) {
                //   mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    mConsultSettingEntity = entity.getData();
                    OnlinePartySettingActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int isOn = mConsultSettingEntity.isOn();
                            partySwitch.setChecked(isOn == 0 ? false : true);
                            acceptAccount.setText(String.valueOf(mConsultSettingEntity.getNumberSource()));
                            partyPrice.setText(String.valueOf(mConsultSettingEntity.getOriginalPrice()));
                            partyDiscount.setText(String.valueOf(mConsultSettingEntity.getPrice()));
                            tvPriceDesc.setText(""+mConsultSettingEntity.getPriceRemark());
                        }
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

    private void savePartySetting() {

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("ServiceItemId", mConsultSettingEntity.getServiceItemId() + "");
        paramsMap.put("SericeItemCode", mConsultSettingEntity.getSericeItemCode() + "");
        paramsMap.put("DrId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        paramsMap.put("DrName", SpUtils.getString(Contants.Name));
        paramsMap.put("OriginalPrice", mConsultSettingEntity.getOriginalPrice());
        paramsMap.put("NumberSource", mConsultSettingEntity.getNumberSource());
        paramsMap.put("Price", mConsultSettingEntity.getPrice());
        boolean checked = partySwitch.isChecked();
        paramsMap.put("IsOn", checked ? "1" : "0");

        mMyOkhttp.post().url(HttpUrl.SavePrescriptionSetting).headers(headMap).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
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
