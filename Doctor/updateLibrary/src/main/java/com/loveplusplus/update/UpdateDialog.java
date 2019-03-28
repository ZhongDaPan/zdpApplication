package com.loveplusplus.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

class UpdateDialog {



    static void show(final Context context, String content, final String downloadUrl) {
        if (isContextValid(context)){
            GroupButtonDialog mDialog = new GroupButtonDialog(context);
            mDialog.show(context.getResources().getString(R.string.android_auto_update_dialog_title), content, new GroupButtonDialog.DialogListener() {
                @Override
                public void cancel() {

                }

                @Override
                public void confirm() {
                    goToDownload(context, downloadUrl);
                }
            });
        }

       /* if (isContextValid(context)) {
            new AlertDialog.Builder(context,R.style.dialogNoBg)
                    .setTitle(R.string.android_auto_update_dialog_title)
                    .setMessage(content)
                    .setPositiveButton(R.string.android_auto_update_dialog_btn_download, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .setNegativeButton(R.string.android_auto_update_dialog_btn_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .setCancelable(false)
                    .show();
        }*/
    }

    private static boolean isContextValid(Context context) {
        return context instanceof Activity && !((Activity) context).isFinishing();
    }


    private static void goToDownload(Context context, String downloadUrl) {
        Intent intent = new Intent(context.getApplicationContext(), DownloadService.class);
        intent.putExtra(Constants.APK_DOWNLOAD_URL, downloadUrl);
        context.startService(intent);
    }

    public void showDialog(final Context context, String content, final String downloadUrl){

    }
}
