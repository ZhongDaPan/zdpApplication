package com.newdjk.doctor.views;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newdjk.doctor.R;
import com.newdjk.doctor.utils.LogUtils;

public class RememberPasswordDialog {

    private Context mContext;
    private Dialog mDialog;

    public RememberPasswordDialog(Context mContext) {
        this.mContext = mContext;


    }

    public void show(String title, String content, final DialogListener listener) {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);//dialog样式
            View view = View.inflate(mContext, R.layout.dialog_remember_password, null);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(false);
            mDialog.setContentView(view);
            TextView tvsure = (TextView) view.findViewById(R.id.tv_sure);
            final CheckBox rememberOneDay = (CheckBox)view.findViewById(R.id.remember_one_day);
            final CheckBox rememberOneWeek = (CheckBox)view.findViewById(R.id.remember_one_week);

            rememberOneDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        rememberOneWeek.setChecked(false);
                    }

                }
            });
            rememberOneWeek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        rememberOneDay.setChecked(false);
                    }

                }
            });
            tvsure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rememberOneWeek.isChecked()) {
                        listener.confirm(7);
                    }
                   else if (rememberOneDay.isChecked()) {
                        listener.confirm(1);
                    }
                    else {
                        listener.confirm(0);
                    }
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                }
            });
            mDialog.show();
            Window dialogWindow = mDialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
            lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
            dialogWindow.setAttributes(lp);

        } catch (Exception e) {
            LogUtils.e("LoadDialog  error!!!");
        }
    }

    private void close() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public interface DialogListener {
        void cancel();

        void confirm(int keepDay );
    }
}
