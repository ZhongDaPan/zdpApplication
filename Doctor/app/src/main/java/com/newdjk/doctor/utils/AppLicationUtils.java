package com.newdjk.doctor.utils;

import com.newdjk.doctor.BuildConfig;
import com.newdjk.doctor.R;
import com.newdjk.doctor.ui.entity.AppLicationEntity;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.utils
 *  @文件名:   AppLicationUtils
 *  @创建者:   huhai
 *  @创建时间:  2019/1/17 14:42
 *  @描述：
 */
public class AppLicationUtils {
    static List<AppLicationEntity> listall = new ArrayList<>();
    static List<AppLicationEntity> listuse = new ArrayList<>();
    static List<AppLicationEntity> recordListuse = new ArrayList<>();


    public static List<AppLicationEntity> getListall() {
        listall.clear();
        if (listall.size() == 0) {
            for (int i = 1; i <= (BuildConfig.IS_DEBUG ? 9 : 8); i++) {
                AppLicationEntity appLicationEntity = new AppLicationEntity();
                appLicationEntity.setAppID(i);
                switch (i) {
                    case 1:
                        appLicationEntity.setAppDesc("图文问诊");
                        appLicationEntity.setImageID(1);
                        break;
                    case 2:
                        appLicationEntity.setAppDesc("在线续方");
                        appLicationEntity.setImageID(2);
                        break;
                    case 3:
                        appLicationEntity.setAppDesc("第二诊疗意见");
                        appLicationEntity.setImageID(3);
                        break;
                    case 4:
                        appLicationEntity.setAppDesc("胎心判读");
                        appLicationEntity.setImageID(4);
                        break;
                    case 5:
                        appLicationEntity.setAppDesc("履约记录");
                        appLicationEntity.setImageID(5);
                        break;
                    case 6:
                        appLicationEntity.setAppDesc("我的药房");
                        appLicationEntity.setImageID(6);
                        break;
                    case 7:
                        appLicationEntity.setAppDesc("邀请患者");
                        appLicationEntity.setImageID(7);
                        break;
                    case 8:
                        if (BuildConfig.IS_DEBUG) {
                            appLicationEntity.setAppDesc("视频问诊");
                            appLicationEntity.setImageID(8);
                        }
                        break;
                    case 9:
                        if (BuildConfig.IS_DEBUG) {
                            appLicationEntity.setAppDesc("分级转诊");
                            appLicationEntity.setImageID(9);
                        }
                        break;
                   /* case 8:
                        appLicationEntity.setAppDesc("随访记录");
                        break;
                    case 9:
                        appLicationEntity.setAppDesc("在线复诊");
                        break;*/

                }
                appLicationEntity.setIsselect(true);

                listall.add(appLicationEntity);
            }
        } else {
            return listall;
        }
        return listall;

    }

    public static List<AppLicationEntity> getListuse() {
        listuse.clear();
        if (listuse.size() == 0) {
            for (int i = 1; i <= (BuildConfig.IS_DEBUG ? 9 : 8); i++) {
                AppLicationEntity appLicationEntity = new AppLicationEntity();
                appLicationEntity.setAppID(i);
                switch (i) {
                    case 1:
                        appLicationEntity.setAppDesc("图文问诊");
                        appLicationEntity.setImageID(1);
                        break;
                    case 2:
                        appLicationEntity.setAppDesc("在线续方");
                        appLicationEntity.setImageID(2);
                        break;
                    case 3:
                        appLicationEntity.setAppDesc("第二诊疗意见");
                        appLicationEntity.setImageID(3);
                        break;
                    case 4:
                        appLicationEntity.setAppDesc("胎心判读");
                        appLicationEntity.setImageID(4);
                        break;
                    case 5:
                        appLicationEntity.setAppDesc("履约记录");
                        appLicationEntity.setImageID(5);
                        break;
                    case 6:
                        appLicationEntity.setAppDesc("我的药房");
                        appLicationEntity.setImageID(6);
                        break;
                    case 7:
                        appLicationEntity.setAppDesc("邀请患者");
                        appLicationEntity.setImageID(7);
                        break;
                    case 8:
                        if (BuildConfig.IS_DEBUG) {
                            appLicationEntity.setAppDesc("视频问诊");
                            appLicationEntity.setImageID(8);
                        }
                        break;
                    case 9:
                        if (BuildConfig.IS_DEBUG) {
                            appLicationEntity.setAppDesc("分级转诊");
                            appLicationEntity.setImageID(9);
                        }
                        break;
                }
                appLicationEntity.setIsselect(true);
                listuse.add(appLicationEntity);
            }
        } else {
            return listuse;
        }
        return listuse;

    }

    public static List<AppLicationEntity> getRecordListuse() {
        recordListuse.clear();
        if (recordListuse.size() == 0) {
            for (int i = 1; i <= 4; i++) {
                AppLicationEntity appLicationEntity = new AppLicationEntity();
                appLicationEntity.setAppID(i);
                switch (i) {
                    case 1:
                        appLicationEntity.setAppDesc("图文问诊");
                        appLicationEntity.setImageID(R.drawable.me_record_icon1);
                        break;
                    case 2:
                        appLicationEntity.setAppDesc("在线续方");
                        appLicationEntity.setImageID(R.drawable.me_record_icon2);
                        break;
                    case 3:
                        appLicationEntity.setAppDesc("服务包记录");
                        appLicationEntity.setImageID(R.drawable.me_record_icon3);
                        break;
                    case 4:
                        appLicationEntity.setAppDesc("第二诊疗意见");
                        appLicationEntity.setImageID(R.drawable.me_record_icon3);
                        break;


                }
                appLicationEntity.setIsselect(true);

                recordListuse.add(appLicationEntity);
            }
        } else {
            return recordListuse;
        }
        return recordListuse;

    }


    public static int getimageID(int id) {
        int defaultiID = R.mipmap.doctor_home_icon1;
        switch (id) {
            case 0:  //这是默认的全部
                defaultiID = R.drawable.all_function;
                break;
            case 1:
                defaultiID = R.drawable.doctor_home_icon1;
                break;
            case 2:
                defaultiID = R.drawable.doctor_home_icon3;
                break;
            case 3:
                defaultiID = R.drawable.seconddiagnose;
                break;
            case 4:
                defaultiID = R.drawable.taixin;
                break;
            case 5:
                defaultiID = R.drawable.lvyue;
                break;
            case 6:
                defaultiID = R.drawable.doctor_home_icon6;
                break;
            case 7:
                defaultiID = R.drawable.invitate_patient;
                break;
            case 8:
                defaultiID = R.mipmap.doctor_home_icon2;
                break;
            case 9:
                defaultiID = R.drawable.fenjizhuanzhen;
                break;
            default:
                defaultiID = R.drawable.doctor_home_icon1;
                break;
        }
        return defaultiID;
    }
}
