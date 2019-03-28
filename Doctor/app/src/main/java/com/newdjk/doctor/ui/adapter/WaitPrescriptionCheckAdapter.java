package com.newdjk.doctor.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.PrescriptionEntity;
import com.newdjk.doctor.ui.fragment.HaveCheckPrescriptionFragment;
import com.newdjk.doctor.ui.fragment.RefuseCheckPrescriptionFragment;
import com.newdjk.doctor.ui.fragment.WaitForCheckPrescriptionFragment;
import com.newdjk.doctor.utils.TimeUtil;

import java.util.List;


public class WaitPrescriptionCheckAdapter extends BaseQuickAdapter<PrescriptionEntity.ReturnDataBean, BaseViewHolder> {

    private final Fragment fragment;
    private Gson mGson;

    public WaitPrescriptionCheckAdapter(@Nullable List<PrescriptionEntity.ReturnDataBean> data, Fragment context) {
        super(R.layout.prescription_check_item, data);
        mGson = new Gson();
        this.fragment = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final PrescriptionEntity.ReturnDataBean item) {

        try {

            try {
                if (fragment instanceof WaitForCheckPrescriptionFragment) {
                    helper.setText(R.id.create_time, TimeUtil.showTime(item.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));

                } else if (fragment instanceof RefuseCheckPrescriptionFragment) {
                    helper.setText(R.id.create_time, TimeUtil.showTime(item.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));

                } else if (fragment instanceof HaveCheckPrescriptionFragment) {
                    helper.setText(R.id.create_time, TimeUtil.showTime(item.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));

                }
            } catch (Exception e) {
                helper.setText(R.id.create_time, item.getCreateTime());
            }

            helper.setText(R.id.patient_name, item.getPatientName());

            int sexType = item.getPatientSex();
            String sex = "";
            switch (sexType) {
                case 1:
                    sex = "男";
                    break;
                case 2:
                    sex = "女";
                    break;
                case 3:
                    sex = "未知";
                    break;
            }
            helper.setText(R.id.sex, sex);
            String areaName = item.getPatientName();
            if (areaName != null) {
                helper.setText(R.id.patient_name, areaName);
            } else {
                helper.setText(R.id.patient_name, "");
            }
            helper.setText(R.id.age, item.getAge() + "");
            helper.setText(R.id.tv_type, "科别:" + (TextUtils.isEmpty(item.getDepartment())?"":item.getDepartment()) + "");
            helper.setText(R.id.tv_desc, "临床诊断:" + item.getDiagnoses() + "");
            helper.setText(R.id.prescriptionp_number, "处方编号:" + item.getNo() + "");
            helper.setText(R.id.tv_doctor, "医生:" + item.getDoctorName() + "");


        } catch (Exception e) {
            Log.i("ConsultMessageAdapter", "e=" + e.toString());
            e.printStackTrace();
        }
    }
}
