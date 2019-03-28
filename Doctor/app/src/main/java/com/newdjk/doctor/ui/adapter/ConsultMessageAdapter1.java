package com.newdjk.doctor.ui.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newdjk.doctor.R;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.IM.ChatActivity;
import com.newdjk.doctor.ui.entity.AllRecordForDoctorEntity;
import com.newdjk.doctor.ui.entity.ConsultMessageEntity;
import com.newdjk.doctor.ui.entity.DoctorPatientRelationEntity;
import com.newdjk.doctor.ui.entity.LoginEntity;
import com.newdjk.doctor.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.doctor.ui.entity.PrescriptionMessageEntity;
import com.newdjk.doctor.ui.entity.RecordDataEntity;
import com.newdjk.doctor.utils.GlideMediaLoader;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.TimeUtil;
import com.tencent.openqq.protocol.imsdk.msg;

import java.lang.reflect.Type;
import java.util.List;

public class ConsultMessageAdapter1 extends BaseQuickAdapter<AllRecordForDoctorEntity, BaseViewHolder> {


    private final Gson mGson;
    private int mType;

    public ConsultMessageAdapter1(@Nullable List<AllRecordForDoctorEntity> data,int type) {
        super(R.layout.consult_item, data);
        mGson = new Gson();
        mType = type;
    }


    @Override
    protected void convert(BaseViewHolder helper, final AllRecordForDoctorEntity item) {
        long unReadNum = item.getUnReadNum();
        Log.i("ConsultMessage", "unReadNum=" + unReadNum);
        try {
            if (unReadNum > 0) {
                helper.setVisible(R.id.consult_unread_num_layout, true);
                helper.setText(R.id.renewal_unread_num, unReadNum + "");
            } else {
                helper.setVisible(R.id.consult_unread_num_layout, false);
            }
            ImageView avatar = helper.getView(R.id.avatar);
            GlideMediaLoader.load(mContext, avatar, item.getApplicantHeadImgUrl());
            if (mType == 3) {
                String time = item.getEndTime();
                helper.setText(R.id.create_time, time);
            }
            else {
                String time = TimeUtil.getChatTimeStr(item.getTimeStamp());
                helper.setText(R.id.create_time, time);
            }

            Log.i("ConsultMessage", "item.getCreateTime()=" + item.getCreateTime());
            int IsDrKey = 0;
                IsDrKey = item.getIsDrKey();
            switch (IsDrKey) {
                case 0:
                    helper.setVisible(R.id.signature_type, false);
                    break;
                case 1:
                    helper.setVisible(R.id.signature_type, true);
                    helper.setText(R.id.signature_type, "重点关注");
                    break;
            }
            helper.setText(R.id.patient_name, item.getPatientName());
            helper.setText(R.id.dynamic, item.getContent());
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
            String areaName = item.getAreaName();
            if (areaName != null) {
                helper.setText(R.id.address, areaName);
            } else {
                helper.setText(R.id.address, "");
            }
            helper.setText(R.id.age, item.getAge());
        } catch (Exception e) {
            Log.i("ConsultMessageAdapter", "e=" + e.toString());
            e.printStackTrace();
        }
        helper.getView(R.id.consult_message_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json ;
                if (mType == 3) {
                    Type jsonType = new TypeToken<PrescriptionMessageEntity<ConsultMessageEntity>>() {
                    }.getType();
                    ConsultMessageEntity recordDataEntity = mGson.fromJson(item.getRecordData(),ConsultMessageEntity.class);
                    PrescriptionMessageEntity<ConsultMessageEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), jsonType);
                    prescriptionMessageEntity.setPatient(recordDataEntity);
                    json= mGson.toJson(prescriptionMessageEntity);
                }
                else {
                    Type jsonType = new TypeToken<PrescriptionMessageEntity<RecordDataEntity>>() {
                    }.getType();
                    RecordDataEntity recordDataEntity = mGson.fromJson(item.getRecordData(),RecordDataEntity.class);
                    PrescriptionMessageEntity<RecordDataEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), jsonType);
                    prescriptionMessageEntity.setPatient(recordDataEntity);
                    json= mGson.toJson(prescriptionMessageEntity);
                }
                Log.i("zdp", "json=" + json);
                Intent intentTalk = new Intent(mContext, ChatActivity.class);
                intentTalk.putExtra(Contants.FRIEND_NAME, item.getPatientName());
                intentTalk.putExtra(Contants.FRIEND_IDENTIFIER, item.getApplicantIMId());
                intentTalk.putExtra("consultMessageEntity", item);
                intentTalk.putExtra("action", "pictureConsult");
                intentTalk.putExtra("accountId",item.getApplicantId());
                intentTalk.putExtra("prescriptionMessage", json);
                mContext.startActivity(intentTalk);
            }
        });
    }

    public void setdata() {

    }
}
