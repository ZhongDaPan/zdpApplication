package com.newdjk.doctor.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.basic.BasicActivity;
import com.newdjk.doctor.utils.ImageBase64;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowOriginPictureActivity extends BasicActivity {


    @BindView(R.id.origin_picture)
    PhotoView originPicture;
    @BindView(R.id.photo_layout)
    LinearLayout photoLayout;
    private String mPath;
    private String mBase64;

    @Override
    protected int initViewResId() {
        return R.layout.show_picture;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        originPicture.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                                                @Override
                                                public void onPhotoTap(View view, float x, float y) {
                                                    finish();
                                                }

                                                @Override
                                                public void onOutsidePhotoTap() {

                                                }
                                            }
        );

        Intent intent = getIntent();
        mPath = intent.getStringExtra("path");
        mBase64 = intent.getStringExtra("base64");
        Log.i("zdp", "path=" + mPath);
        if (mPath != null) {
            Glide.with(MyApplication.getContext())
                    .load(mPath)
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(originPicture);
        } else if (mBase64 != null) {
            photoLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            Bitmap bitmap = ImageBase64.base64ToBitmap(mBase64);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();

            Glide.with(MyApplication.getContext())
                    .load(bytes)
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(originPicture);
        }
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
