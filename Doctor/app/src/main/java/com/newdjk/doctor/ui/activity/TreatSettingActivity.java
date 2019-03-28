package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kyleduo.switchbutton.SwitchButton;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.SymptomSelectedAdapter;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.GetSecondDiagnosisSettingEntity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.views.ItemView;
import com.newdjk.doctor.views.LoadDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TreatSettingActivity extends BasicActivity {


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
    @BindView(R.id.accepts_switch_text)
    TextView acceptsSwitchText;
    @BindView(R.id.accepts_switch)
    SwitchButton acceptsSwitch;
    @BindView(R.id.accept_account_text)
    TextView acceptAccountText;
    @BindView(R.id.accept_account)
    EditText acceptAccount;
    @BindView(R.id.consult_price_text)
    TextView consultPriceText;
    @BindView(R.id.consult_price)
    EditText consultPrice;
    @BindView(R.id.consult_discount_text)
    TextView consultDiscountText;
    @BindView(R.id.consult_discount)
    EditText consultDiscount;
    @BindView(R.id.mFunReport)
    ItemView mFunReport;
    @BindView(R.id.recyleview)
    RecyclerView recyleview;
    @BindView(R.id.symptoms)
    LinearLayout symptoms;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.tv_price_desc)
    TextView tvPriceDesc;
    private Gson mGson;
    private String discount;
    private String numberSource;
    private String price;
    private int mDoctype;
    private GetSecondDiagnosisSettingEntity mGetSecondDiagnosisSettingEntity;
    private SymptomSelectedAdapter mSymptomSelectedAdapter;
    private List<GetSecondDiagnosisSettingEntity.DoctorDiseasesBean> mList;

    @Override
    protected int initViewResId() {
        return R.layout.treat_setting;
    }

    @Override
    protected void initView() {
        mDoctype = SpUtils.getInt(Contants.DocType, 0);
        mList = new ArrayList<>();
        GridLayoutManager mManagerLayout = new GridLayoutManager(mContext, 3);
        recyleview.setLayoutManager(mManagerLayout);
        mSymptomSelectedAdapter = new SymptomSelectedAdapter(mList);
        recyleview.setAdapter(mSymptomSelectedAdapter);
        if (mDoctype == 1) {
            initBackTitle(getString(R.string.treat_setting));
        } else if (mDoctype == 2) {
            acceptsSwitchText.setText("护理咨询开通开关");
            acceptAccountText.setText("每日护理数量");
            consultPriceText.setText("护理咨询单价");
            consultDiscountText.setText("护理咨询优惠价");
            initBackTitle("护理咨询设置");
        }


    }

    @Override
    protected void initListener() {
        mFunReport.setOnClickListener(this);
        save.setOnClickListener(this);
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
        GetSecondDiagnosisSetting();
        //  queryDoctorDiseases();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                discount = consultDiscount.getText().toString();
                numberSource = acceptAccount.getText().toString();
                price = consultPrice.getText().toString();
                if (discount.equals("")) {
                    discount = "0";
                }
                if (numberSource.equals("")) {
                    numberSource = "0";
                }
                if (price.equals("")) {
                    price = "0";
                }
                if (mList.size() > 0) {
                    boolean checked = acceptsSwitch.isChecked();
                    mGetSecondDiagnosisSettingEntity.setIsOn(checked ? 1 : 0);
                    mGetSecondDiagnosisSettingEntity.setPrice(Double.parseDouble(discount));
                    mGetSecondDiagnosisSettingEntity.setOriginalPrice(Double.parseDouble(price));
                    mGetSecondDiagnosisSettingEntity.setNumberSource(Integer.parseInt(numberSource));
                    SaveSecondDiagnosisSetting();
                } else {
                    toast("请选择诊疗的适应病症！");
                }
                break;
            case R.id.mFunReport:
                Intent intent = new Intent(this, DiseaseSelectedActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                String dieaseData = data.getStringExtra("DiseaseList");
                Type jsonType = new TypeToken<List<GetSecondDiagnosisSettingEntity.DoctorDiseasesBean>>() {
                }.getType();
                List<GetSecondDiagnosisSettingEntity.DoctorDiseasesBean> list = mGson.fromJson(dieaseData, jsonType);
                mGetSecondDiagnosisSettingEntity.setDoctorDiseases(list);
                //  List<GetSecondDiagnosisSettingEntity.DoctorDiseasesBean> diseaseList = new ArrayList<>();
                mList.clear();
                for (int i = 0; i < list.size(); i++) {
                    GetSecondDiagnosisSettingEntity.DoctorDiseasesBean doctorDiseasesBean = new GetSecondDiagnosisSettingEntity.DoctorDiseasesBean();
                    doctorDiseasesBean.setDiseaseName(list.get(i).getDiseaseName());
                    mList.add(doctorDiseasesBean);
                }
                mSymptomSelectedAdapter.setNewData(mList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //   queryDoctorDiseases();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void GetSecondDiagnosisSetting() {
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        String url = HttpUrl.GetSecondDiagnosisSetting + "?DrId=" + SpUtils.getInt(Contants.Id, 0);
        // String url = "http://172.18.30.4/NetHospSecondDiagnosisAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        mMyOkhttp.get().url(url).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<GetSecondDiagnosisSettingEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<GetSecondDiagnosisSettingEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    mList.clear();
                    Log.i("SymptomsSelectActivity", "entity=" + entity.getData().toString());
                    mGetSecondDiagnosisSettingEntity = entity.getData();
                    mList = mGetSecondDiagnosisSettingEntity.getDoctorDiseases();
                    mSymptomSelectedAdapter.setNewData(mList);
                    TreatSettingActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int isOn = mGetSecondDiagnosisSettingEntity.getIsOn();
                            acceptsSwitch.setChecked(isOn == 0 ? false : true);
                            acceptAccount.setText(String.valueOf(mGetSecondDiagnosisSettingEntity.getNumberSource()));
                            consultPrice.setText(String.valueOf(mGetSecondDiagnosisSettingEntity.getOriginalPrice()));
                            consultDiscount.setText(String.valueOf(mGetSecondDiagnosisSettingEntity.getPrice()));
                            if (TextUtils.isEmpty(mGetSecondDiagnosisSettingEntity.getPriceRemark())){
                                tvPriceDesc.setVisibility(View.GONE);
                            }else {
                                tvPriceDesc.setText(""+mGetSecondDiagnosisSettingEntity.getPriceRemark());
                            }


                        }
                    });
                } else {
                    toast(entity.getMessage() + "aaa");
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    /*    mDataList = new ArrayList<>();
        for (int i = 0;i < 20;i++) {
            SymptomEntity SymptomEntity = new SymptomEntity();
            SymptomEntity.setSymptom("喉癌"+i);
            mDataList.add(SymptomEntity);
        }
        mSymptomSelectAdapter.setDatalist(mDataList);*/
    }

    private void SaveSecondDiagnosisSetting() {

        String json = mGson.toJson(mGetSecondDiagnosisSettingEntity);
        loading(true);

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.SaveSecondDiagnosisSetting).headers(headMap).jsonParams(json).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    boolean result = (boolean) entity.getData();
                    if (result) {
                        toast("修改成功");
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        toast("保存失败");
                    }

                } else {
                    toast(entity.getMessage() + "aaa");
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }
  /*  private void queryDoctorDiseases() {
        loading(true);

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        String url =HttpUrl.QueryDoctorDiseases+"?DrId="+SpUtils.getInt(Contants.Id,0);
        // String url = "http://172.18.30.4/NetHospSecondDiagnosisAPI/MedicalService/QueryDoctorDiseases?DrId="+SpUtils.getInt(Contants.Id,0);
        mMyOkhttp.get().url(url).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<List<SymptomEntity>>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<List<SymptomEntity>> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    Log.i("SymptomsSelectActivity","entity="+entity.getData().toString());
                 mSymptomSelectedAdapter.setDataList(entity.getData());

                } else {
                    toast(entity.getMessage() + "aaa");
                }
                LoadDialog.clear();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });

    }*/
}
