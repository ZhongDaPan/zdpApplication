package com.newdjk.doctor.utils;

import android.content.Intent;

import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.service.MyService;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.login.LoginActivity;
import com.newdjk.doctor.views.LoadDialog;
import com.tencent.TIMCallBack;
import com.tencent.TIMManager;


public class LogOutUtil implements TIMCallBack {
    private BasicActivity mActivity;

    @Override
    public void onError(int i, String s) {
        clearAllAndJump();
    }

    @Override
    public void onSuccess() {
        clearAllAndJump();
    }

    private static class SingletonHolder {
        private static LogOutUtil instance = new LogOutUtil();
    }

    private LogOutUtil() {
    }

    public static LogOutUtil getInstance() {
        return SingletonHolder.instance;
    }

    public void loginOut(BasicActivity activity,boolean isLogin) {
        mActivity = activity;
        if (mActivity != null) {
            activity.loading(true);
            if (isLogin){
                TIMManager.getInstance().logout(this);
                Intent intent = new Intent(mActivity, MyService.class);
                mActivity.stopService(intent);
            }else {
                clearAllAndJump();
            }

        } else {
            LogUtils.e("no activity result");
        }

    }


    protected void clearAllAndJump() {
        if (mActivity != null) {
            LoadDialog.clear();
            String userName = SpUtils.getString(Contants.userName);
            String password = SpUtils.getString(Contants.Password);
            Intent loginOutIntent = new Intent(mActivity, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            SpUtils.clear();
            SpUtils.put(Contants.IS_FIRST_USE, false);
            SpUtils.put(Contants.userName, userName);
            SpUtils.put(Contants.Password, password);
            mActivity.startActivity(loginOutIntent);
        }

    }

}
