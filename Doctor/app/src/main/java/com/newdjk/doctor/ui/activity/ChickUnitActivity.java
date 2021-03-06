package com.newdjk.doctor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.model.HttpUrl;
import com.newdjk.doctor.tools.CommonMethod;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.adapter.ChickUnitAdapter;
import com.newdjk.doctor.ui.entity.ChickUnitEntity;
import com.newdjk.doctor.ui.entity.Entity;
import com.newdjk.doctor.ui.entity.ResponseEntity;
import com.newdjk.doctor.utils.SpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChickUnitActivity extends BasicActivity {
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
    @BindView(R.id.message_list)
    RecyclerView messageList;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.ignore_tip)
    CheckBox ignoreTip;
    private ChickUnitAdapter mChickUnitAdapter;
    private List<ChickUnitEntity> mDataList;
    private int mPosition = -1;
    private String mType;
    private String mPrescriptionMessage;
    private  SharedPreferences.Editor mEditor;

    @Override
    protected int initViewResId() {
        return R.layout.hospital_message_list;
    }

    @Override
    protected void initView() {
        initBackTitle("供药单位");
        SharedPreferences sp = getSharedPreferences("yunyisheng",
                Context.MODE_PRIVATE);
        mEditor = sp.edit();
        mType = getIntent().getStringExtra("type");
        if (mType != null && mType.equals("prescription")) {
            ignoreTip.setVisibility(View.VISIBLE);
        }
        mPrescriptionMessage = getIntent().getStringExtra("prescriptionMessage");
        mChickUnitAdapter = new ChickUnitAdapter(mDataList);
        mChickUnitAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mPosition != position) {
                    for (int i = 0; i < mDataList.size(); i++) {
                        mDataList.get(i).setIsDefault(0);
                    }
                    mDataList.get(position).setIsDefault(1);
                    mPosition = position;
                    mChickUnitAdapter.notifyDataSetChanged();
                }
            }
        });
        messageList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        messageList.setAdapter(mChickUnitAdapter);
        messageList.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
    }

    @Override
    protected void initListener() {
        submit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getMyMedicationCompany();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (mPosition != -1) {
                    setMyMedicationCompany(mDataList.get(mPosition).getId());
                } else {

                    if (mType != null && mType.equals("prescription")) {
                        if (ignoreTip.isChecked()) {
                            mEditor.putInt(Contants.IsIgnore,1);
                            mEditor.apply();
                        }
                        Intent prescriptionIntent = new Intent(ChickUnitActivity.this, PrescriptionActivity.class);
                        prescriptionIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
                        startActivity(prescriptionIntent);
                    } else {
                        toast("保存成功");
                        Intent intent = new Intent();
                        intent.putExtra("result", "nimei");
                        setResult(RESULT_OK, intent);
                    }
                    finish();
                }
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void getMyMedicationCompany() {
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("OrgId", "1");
        Log.i("ChickUnitActivity", HttpUrl.GetMyMedicationCompany);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetMyMedicationCompany).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<List<ChickUnitEntity>>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<List<ChickUnitEntity>> entity) {
                Log.i("ChickUnitActivity", "entity=" + entity.getData());
                if (entity.getCode() == 0) {
                    mDataList = entity.getData();

                    mChickUnitAdapter.setNewData(mDataList);
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

    private void setMyMedicationCompany(int MedicationCompanyId) {
        Map<String, String> map = new HashMap<>();
        map.put("DoctorId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
        map.put("MedicationCompanyId", String.valueOf(MedicationCompanyId));
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        Log.i("ChickUnitActivity", "mposition=" + mPosition + ",MedicationCompanyId=" + MedicationCompanyId);
        mMyOkhttp.post().url(HttpUrl.SetMyMedicationCompany).params(map).headers(headMap).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                Log.i("ChickUnitActivity", "setMyMedicationCompany=" + entity.getData());
                if (entity.getCode() == 0) {

                    if (mType != null && mType.equals("prescription")) {
                        if (ignoreTip.isChecked()) {
                            mEditor.putInt(Contants.IsIgnore,1);
                            mEditor.apply();
                        }
                        Intent prescriptionIntent = new Intent(ChickUnitActivity.this, PrescriptionActivity.class);
                        prescriptionIntent.putExtra("prescriptionMessage", mPrescriptionMessage);
                        startActivity(prescriptionIntent);
                    }
                    else {
                        toast("保存成功");
                    }
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
