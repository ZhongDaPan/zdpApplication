package com.newdjk.doctor.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.annotation.Dimension;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.newdjk.doctor.MyApplication;
import com.newdjk.doctor.R;
import com.newdjk.doctor.tools.Contants;
import com.newdjk.doctor.ui.activity.CaseDetailActivity;
import com.newdjk.doctor.ui.activity.CustomLinkActivity;
import com.newdjk.doctor.ui.activity.DoctorPraiseActivity;
import com.newdjk.doctor.ui.activity.MedicalServiceActivity;
import com.newdjk.doctor.ui.activity.PrescriptionActivity;
import com.newdjk.doctor.ui.activity.ShowOriginPictureActivity;
import com.newdjk.doctor.ui.entity.CustomMessageEntity;
import com.newdjk.doctor.ui.entity.ImageInfoArrayEntity;
import com.newdjk.doctor.ui.entity.MsgContentEntity;
import com.newdjk.doctor.ui.fragment.MinFragment;
import com.newdjk.doctor.utils.GlideMediaLoader;
import com.newdjk.doctor.utils.MyTIMMessage;
import com.newdjk.doctor.utils.SpUtils;
import com.newdjk.doctor.utils.TimeUtil;
import com.newdjk.doctor.views.CircleImageView;
import com.newdjk.doctor.views.LoadDialog;
import com.newdjk.doctor.views.PlusDialog;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversation;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMImage;
import com.tencent.TIMImageElem;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageStatus;
import com.tencent.TIMSoundElem;
import com.tencent.TIMTextElem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gabriel on 2017/3/6.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {


    private List<MyTIMMessage> mTIMMessageList;
    private MediaPlayer mMediaPlayer;
    private static final String TAG = "ChatAdapter";
    public static AnimationDrawable mLastAnimationDrawable;
    private int mPosition = -1;
    private String mPath;
    private Context mContext;
    private Gson mGson;
    private String mDoctorMessage;
    private String mleftImagePath;
    private String mPatientName;
    private TIMConversation mTIMConversation;
    private boolean mIsHasOpenPres;

    public ChatAdapter(List<MyTIMMessage> timMessage, Context context, String doctorMessage, String leftImagePath, String patientName, TIMConversation timConversation,boolean isHasOpenPres) {
        mContext = context;
        mTIMMessageList = timMessage;
        mDoctorMessage = doctorMessage;
        mMediaPlayer = new MediaPlayer();
        mleftImagePath = leftImagePath;
        mIsHasOpenPres = isHasOpenPres;
        mPatientName = patientName;
        mTIMConversation = timConversation;
        mGson = new Gson();
    }

    public void setIsHasOpenPres(boolean isHasOpenPres) {
        mIsHasOpenPres = isHasOpenPres;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.
                from(MyApplication.getContext()).inflate(R.layout.item_message, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //由于sdk返回的list是按时间顺序排列的，所以显示时需要反过来
        position = mTIMMessageList.size() - position - 1;
        final int pposition = position;
        final  MyTIMMessage myTIMMessage = mTIMMessageList.get(position);
        final TIMMessage timMessage =myTIMMessage.getTimMessage();
        boolean isLocalMessage = myTIMMessage.isLocalMessage();
        boolean isRevoke = myTIMMessage.isRevoke();
        long timeStamp = 0;
        long lastimeStamp = 0;
        String sender = null;
        if (isLocalMessage) {
            timeStamp = myTIMMessage.getMsgTimestamp();
            if (position != mTIMMessageList.size() - 1) {
                lastimeStamp = mTIMMessageList.get(position + 1).getMsgTimestamp();
                if (lastimeStamp == 0) {
                    lastimeStamp = mTIMMessageList.get(position + 1).getTimMessage().timestamp();
                }
            }
            sender =myTIMMessage.getSendTarget();
        } else {
            sender = timMessage.getSender();
            timeStamp = timMessage.timestamp();
            if (position != mTIMMessageList.size() - 1) {
                lastimeStamp = mTIMMessageList.get(position + 1).getMsgTimestamp();
                if (lastimeStamp == 0) {
                    lastimeStamp = mTIMMessageList.get(position + 1).getTimMessage().timestamp();
                }
            }
        }
        final long timeStamp1 = timeStamp;
        Log.i(TAG,"sender="+sender);
        holder.systemMessage.setVisibility(View.VISIBLE);

        holder.systemMessage.setText(TimeUtil.getChatTimeStr(timeStamp));
        holder.systemMessageLayout.setVisibility(View.GONE);
        /*holder.systemMessage1.setBackgroundColor(0);
        holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.text_gray2));
        holder.systemMessageLayout.setBackgroundResource(0);*/
        if (position != mTIMMessageList.size() - 1) {
            TIMMessage lastTimMessage = mTIMMessageList.get(position + 1).getTimMessage();
            //如果上一条消息与当前这一条的时间间隔小于300秒，则不显示这一条消息的发送时间
            if (timeStamp - lastimeStamp < 300) {
                holder.systemMessage.setVisibility(View.GONE);
            }

        }

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout
                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                , RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);

        TIMElem element = timMessage.getElement(0);
        Log.i("aa", "sender=" + timMessage.getSender());
        Log.i("aa", "time=" + TimeUtil.getChatTimeStr(timeStamp));
        if (sender.equals(SpUtils.getString(Contants.identifier))) {     //自己向好友发送的消息
            //  GlideMediaLoader.load(MyApplication.getContext(), holder.rightAvatar, MinFragment.doctorPath);
       /*     holder.rightMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "aaaaaa", Toast.LENGTH_SHORT).show();
                }
            });*/
            holder.rightMessage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    long nowTime = System.currentTimeMillis() / 1000;
                    if (nowTime - timeStamp1 < 120 && !mTIMMessageList.get(pposition).isRevoke()) {
                        showPopupWindow(v,myTIMMessage);
                       /* mTIMConversation.revokeMessage(timMessage, new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(mContext, "撤回消息失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess() {
                                mTIMMessageList.get(pposition).setRevoke(true);
                                notifyDataSetChanged();
                                Toast.makeText(mContext, "撤回消息成功", Toast.LENGTH_SHORT).show();
                            }
                        });*/
                    }
                    else {
                        Toast.makeText(mContext, "超过两分钟，不能撤回", Toast.LENGTH_SHORT).show();
                    }
                        return false;
                    }

            });
            Glide.with(MyApplication.getContext())
                    .load(MinFragment.doctorPath)
                    .dontAnimate()
                    .placeholder(R.drawable.doctor_default_img)
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.rightAvatar);
            holder.leftPanel.setVisibility(View.GONE);
            holder.rightPanel.setVisibility(View.VISIBLE);
            holder.rightMessage.removeAllViews();
            holder.rightMessage.setBackgroundResource(R.drawable.chat_blue_bg);
            //显示自己向好友发送的消息的发送状态
            if (isLocalMessage) {
                holder.sendError.setVisibility(View.GONE);
                holder.sending.setVisibility(View.GONE);
            } else {
                switch (timMessage.status()) {
                    case Sending:
                        holder.sendError.setVisibility(View.GONE);
                        holder.sending.setVisibility(View.VISIBLE);
                        break;
                    case SendSucc:
                        holder.sendError.setVisibility(View.GONE);
                        holder.sending.setVisibility(View.GONE);
                        break;
                    case SendFail:
                        holder.sendError.setVisibility(View.VISIBLE);
                        holder.sending.setVisibility(View.GONE);
                        break;
                }
            }
            if (isRevoke || timMessage.status() == TIMMessageStatus.HasRevoked) {
                holder.systemMessage1.setText("你撤回了一条消息");
                holder.leftPanel.setVisibility(View.GONE);
                holder.rightPanel.setVisibility(View.GONE);
                holder.leftMessage.removeAllViews();
                holder.systemMessageLayout.setVisibility(View.VISIBLE);
                holder.sendError.setVisibility(View.GONE);
                holder.sending.setVisibility(View.GONE);
                holder.systemMessage1.setBackgroundColor(0);
                holder.systemMessageLayout.setBackgroundResource(R.drawable.system_message_background);
                holder.line.setVisibility(View.GONE);
                holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.white));
            }
            //文字信息处理
           else if (element.getType() == TIMElemType.Text) {
                TIMTextElem textElem = (TIMTextElem) element;
                TextView textView = new TextView(MyApplication.getContext());
                textView.setText(textElem.getText());
                textView.setTextSize(Dimension.SP, 14);
                textView.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.rightMessage.addView(textView, layoutParams);
                //语音信息处理
            } else if (element.getType() == TIMElemType.Sound) {
                TIMSoundElem elem = (TIMSoundElem) element;
                final ImageView imageView = new ImageView(MyApplication.getContext());
                imageView.setId(R.id.img_id);
                imageView.setImageResource(R.drawable.right_voice3);
                imageView.setLayoutParams(layoutParams);
                holder.rightMessage.addView(imageView);
                Log.i("zdp", ((TIMSoundElem) element).getDuration() + "\"");
                RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout
                        .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                        , RelativeLayout.LayoutParams.WRAP_CONTENT);
                TextView textView = new TextView(MyApplication.getContext());
                textView.setText(((TIMSoundElem) element).getDuration() + "\"");
                textView.setTextColor(mContext.getResources().getColor(R.color.white));
                textView.setTextSize(Dimension.SP, 14);
                textLayoutParams.rightMargin = 50;
                textLayoutParams.leftMargin = 30;
                textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                textLayoutParams.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                textView.setLayoutParams(textLayoutParams);
                holder.rightMessage.addView(textView);
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // mMediaPlayer.release();
                        mPosition = -1;
                        if (mLastAnimationDrawable != null) {
                            mLastAnimationDrawable.selectDrawable(0);
                            mLastAnimationDrawable.stop();
                        }
                        //   animationDrawable.stop();
                    }
                });
                holder.rightMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {


                            if (mPosition == pposition) {
                                if (mLastAnimationDrawable != null) {
                                    mLastAnimationDrawable.selectDrawable(0);
                                    mLastAnimationDrawable.stop();
                                }
                                imageView.setImageResource(R.drawable.right_voice3);
                                mMediaPlayer.stop();
                                mPosition = -1;
                                // playSound(mMediaPlayer, timMessage);
                                // animationDrawable.stop();
                            } else {
                                imageView.setImageResource(R.drawable.right_voice);
                                RelativeLayout relativeLayout = (RelativeLayout) v;
                                ImageView childImageView = (ImageView) relativeLayout.getChildAt(0);
                                AnimationDrawable AnimationDrawable = (AnimationDrawable) childImageView.getDrawable();
                                operatePlaySound(AnimationDrawable);
                                //   mMediaPlayer = new MediaPlayer();
                                playSound(mMediaPlayer, timMessage, false, null, null);
                                mPosition = pposition;
                            }

                        } catch (IllegalStateException e) {
                            Log.i("zdp", e.toString());
                        }
                    }
                });
                //图片信息处理
            } else if (element.getType() == TIMElemType.Image) {
                holder.rightMessage.setBackgroundResource(0);
                TIMImageElem imageElem = (TIMImageElem) element;
                for (TIMImage timImage : imageElem.getImageList()) {
                  /*  timImage.getImage(new TIMValueCallBack<byte[]>() {
                        @Override
                        public void onError(int code, String desc) {//获取图片失败
                            //错误码 code 和错误描述 desc，可用于定位请求失败原因
                            //错误码 code 含义请参见错误码表
                            Log.d("zdp", "getImage failed. code: " + code + " errmsg: " + desc);
                        }
                        @Override
                        public void onSuccess(byte[] data) {//成功，参数为图片数据
                            //doSomething
                            Log.d("zdp", "getImage success. data size: " + data.length);
                        }
                    });*/

                    if (timImage.getType().toString().equals("Large")) {
                        final String path = timImage.getUrl();
                        holder.rightMessage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                                intent.putExtra("path", path);
                                mContext.startActivity(intent);
                            }
                        });
                    }
                    if (timImage.getType().toString().equals("Thumb")) {
                        int height = (int) timImage.getHeight();
                        int width = (int) timImage.getWidth();
                        RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                                .LayoutParams(width
                                , height);
                        ImageView imageView = new ImageView(MyApplication.getContext());
                        Log.d("zdp", "image type: " + timImage.getType() +
                                " image size " + timImage.getSize() +
                                " image height " + timImage.getHeight() +
                                " image width " + timImage.getWidth());

                        GlideMediaLoader.load(MyApplication.getContext(), imageView, timImage.getUrl());
                        layoutParams.setMargins(0, 0, 0, 0);
                        holder.rightMessage.addView(imageView, pictureLayoutParams);

                    }
                }
            } else if (element.getType() == TIMElemType.Custom) {

                try {
                    TIMCustomElem customElem = (TIMCustomElem) element;
                    String desc = customElem.getDesc();
                    String s = new String(customElem.getData());
                    Log.i("ChatAdapter", "s=" + s);
                   /* RelativeLayout.LayoutParams customLayoutParams = new RelativeLayout
                            .LayoutParams(600
                            , RelativeLayout.LayoutParams.WRAP_CONTENT);*/
                    Log.i("ChatAdapter", "ssss");
                    if (desc != null && desc.equals("TIMSoundElem")) {
                        MsgContentEntity msgContentEntity = mGson.fromJson(s, MsgContentEntity.class);
                        final String url = msgContentEntity.getFileUrl();
                        final String uuid = msgContentEntity.getUUID();
                        final File file = new File(MyApplication.getContext().getFilesDir()
                                + File.separator + uuid);
                        final ImageView imageView = new ImageView(MyApplication.getContext());
                        imageView.setId(R.id.img_id);
                        imageView.setImageResource(R.drawable.right_voice3);
                        imageView.setLayoutParams(layoutParams);
                        holder.rightMessage.addView(imageView);
                        //     Log.i("zdp", ((TIMSoundElem) element).getDuration() + "\"");
                        RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout
                                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                                , RelativeLayout.LayoutParams.WRAP_CONTENT);
                        TextView textView = new TextView(MyApplication.getContext());
                        textView.setText(msgContentEntity.getSecond() + "\"");
                        textView.setTextColor(mContext.getResources().getColor(R.color.white));
                        textView.setTextSize(Dimension.SP, 14);
                        textLayoutParams.rightMargin = 50;
                        textLayoutParams.leftMargin = 30;
                        textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                        textLayoutParams.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                        textView.setLayoutParams(textLayoutParams);
                        holder.rightMessage.addView(textView);
                        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                // mMediaPlayer.release();
                                mPosition = -1;
                                if (mLastAnimationDrawable != null) {
                                    mLastAnimationDrawable.selectDrawable(0);
                                    mLastAnimationDrawable.stop();
                                }
                                //   animationDrawable.stop();
                            }
                        });
                        holder.rightMessage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Log.i("ChatAdapter", "s=" + file.getAbsolutePath());
                                    if (mPosition == pposition) {
                                        if (mLastAnimationDrawable != null) {
                                            mLastAnimationDrawable.selectDrawable(0);
                                            mLastAnimationDrawable.stop();
                                        }
                                        imageView.setImageResource(R.drawable.right_voice3);
                                        mMediaPlayer.stop();
                                        mPosition = -1;
                                        // playSound(mMediaPlayer, timMessage);
                                        // animationDrawable.stop();
                                    } else {
                                        imageView.setImageResource(R.drawable.right_voice);
                                        RelativeLayout relativeLayout = (RelativeLayout) v;
                                        ImageView childImageView = (ImageView) relativeLayout.getChildAt(0);
                                        AnimationDrawable AnimationDrawable = (AnimationDrawable) childImageView.getDrawable();
                                        operatePlaySound(AnimationDrawable);
                                        //   mMediaPlayer = new MediaPlayer();
                                        playSound(mMediaPlayer, timMessage, true, uuid, url);
                                        mPosition = pposition;
                                    }

                                } catch (IllegalStateException e) {
                                    Log.i("zdp", e.toString());
                                }

                            }

                        });
                    } else if (desc != null && desc.equals("TIMImageElem")) {
                        Log.i("TIMImageElem","s="+s);
                        holder.rightMessage.setBackgroundResource(0);
                        Type jsonType = new TypeToken< List<ImageInfoArrayEntity>>() {
                        }.getType();
                        List<ImageInfoArrayEntity> imageInfoArray =  mGson.fromJson(s, jsonType);
                        if (imageInfoArray != null && imageInfoArray.size() >0) {
                            for(int i = 0;i<imageInfoArray.size();i++) {
                                ImageInfoArrayEntity imageInfoArrayEntity = imageInfoArray.get(i);
                                int type = imageInfoArrayEntity.getType();
                                if (type == 1) {
                                    String url = imageInfoArrayEntity.getURL();
                                    final String path = url.replace("\\","/");
                                    holder.rightMessage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                                            intent.putExtra("path", path);
                                            mContext.startActivity(intent);
                                        }
                                    });
                                }
                               else if (type == 3) {
                                    int height =  imageInfoArrayEntity.getHeight();
                                    int width =  imageInfoArrayEntity.getWidth();
                                    RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                                            .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                                            , RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    ImageView imageView = new ImageView(MyApplication.getContext());
                                 /*   Log.d("zdp", "image type: " + timImage.getType() +
                                            " image size " + timImage.getSize() +
                                            " image height " + timImage.getHeight() +
                                            " image width " + timImage.getWidth());*/

                                    String url = imageInfoArrayEntity.getURL();
                                    String path = url.replace("\\","/");
                                    Log.i("TIMImageElem","URL="+path);
                                    GlideMediaLoader.load(MyApplication.getContext(), imageView, path);
                                    layoutParams.setMargins(0, 0, 0, 0);
                                    holder.rightMessage.addView(imageView, pictureLayoutParams);

                                }
                            }
                        }
                    } else {
                        holder.rightMessage.setBackgroundResource(0);
                        final CustomMessageEntity CustomMessageEntity = mGson.fromJson(s, CustomMessageEntity.class);
                        boolean isSystem = CustomMessageEntity.isIsSystem();
                        if (isSystem) {
                            holder.systemMessageLayout.setVisibility(View.VISIBLE);
                            int showType = CustomMessageEntity.getShowType();

                            if (showType == 0 || showType == 2) {
                                boolean IsShowDividingLine = CustomMessageEntity.isShowDividingLine();
                                if (IsShowDividingLine) {
                                    holder.line.setVisibility(View.VISIBLE);
                                    holder.systemMessage1.setBackgroundColor(mContext.getResources().getColor(R.color.activity_bg));
                                    holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.text_gray2));
                                    holder.systemMessageLayout.setBackgroundResource(0);
                                } else {
                                    holder.systemMessage1.setBackgroundColor(0);
                                    holder.systemMessageLayout.setBackgroundResource(R.drawable.system_message_background);
                                    holder.line.setVisibility(View.GONE);
                                    holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.white));
                                }
                                holder.systemMessage1.setText(CustomMessageEntity.getTitle());
                                holder.leftPanel.setVisibility(View.GONE);
                                holder.rightPanel.setVisibility(View.GONE);
                                holder.leftMessage.removeAllViews();
                                holder.systemMessageLayout.setVisibility(View.VISIBLE);
                            } else {
                                holder.leftPanel.setVisibility(View.GONE);
                                holder.rightPanel.setVisibility(View.GONE);
                                holder.leftMessage.removeAllViews();
                                holder.systemMessageLayout.setVisibility(View.GONE);
                            }
                        } else {

                            View serviceView = LayoutInflater.
                                    from(MyApplication.getContext()).inflate(R.layout.service_package, null);
                            LinearLayout.LayoutParams layoutParam = new LinearLayout
                                    .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                                    , LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParam.setMargins(10, 5, 10, 5);
                            serviceView.setLayoutParams(layoutParam);
                            final TextView servicePackageName = serviceView.findViewById(R.id.service_paceage_name);
                            RelativeLayout checkDetail = serviceView.findViewById(R.id.check_detail);
                            RelativeLayout titleLayout = serviceView.findViewById(R.id.title_layout);
                            TextView detailText = serviceView.findViewById(R.id.check_detail_text);
                            RecyclerView list = serviceView.findViewById(R.id.service_detail_list);
                            list.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false));
                            CustomMessageAdapter adapter = new CustomMessageAdapter(CustomMessageEntity.getContent());
                            list.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
                            list.setAdapter(adapter);
                            adapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
                                @Override
                                public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                                    long nowTime = System.currentTimeMillis() / 1000;
                                    if (nowTime - timeStamp1 < 120 && !mTIMMessageList.get(pposition).isRevoke()) {
                                        showPopupWindow(view,myTIMMessage);
                       /* mTIMConversation.revokeMessage(timMessage, new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(mContext, "撤回消息失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess() {
                                mTIMMessageList.get(pposition).setRevoke(true);
                                notifyDataSetChanged();
                                Toast.makeText(mContext, "撤回消息成功", Toast.LENGTH_SHORT).show();
                            }
                        });*/
                                    }
                                    else {
                                        Toast.makeText(mContext, "超过两分钟，不能撤回", Toast.LENGTH_SHORT).show();
                                    }
                                    return false;
                                }
                            });

                            View line = serviceView.findViewById(R.id.line);
                            //  setListViewHeightBasedOnChildren(list);
                            //  adapter.setDatalist(CustomMessageEntity.getContent());
                            final String url = CustomMessageEntity.getLinkUrl();
                            final CustomMessageEntity.ExtDataBean extraData = CustomMessageEntity.getExtData();

                            // adapter.setDatalist(CustomMessageEntity.getContent());
                            int type1 = 0;
                            if (extraData == null) {
                                if (url != null && !url.equals("")) {
                                    checkDetail.setVisibility(View.VISIBLE);
                                } else {
                                    checkDetail.setVisibility(View.GONE);
                                    line.setVisibility(View.GONE);
                                }
                            } else {
                                type1 = extraData.getType();
                                if (extraData.getType() == 26 || extraData.getType() == 25 || type1 > 100 && type1 < 200 || extraData.getType() == 33) {
                                    checkDetail.setVisibility(View.GONE);
                                    line.setVisibility(View.GONE);
                                } else {
                                    checkDetail.setVisibility(View.VISIBLE);
                                    if (extraData.getType() == 28) {
                                        servicePackageName.setText(CustomMessageEntity.getFocusTitle());
                                        detailText.setText("查看凭证");
                                    }
                                }
                                if (extraData.getType() == 399) {
                                    checkDetail.setVisibility(View.GONE);
                                    line.setVisibility(View.GONE);
                                    RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                                            .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                                            , RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    ImageView imageView = new ImageView(MyApplication.getContext());
                                 /*   Log.d("zdp", "image type: " + timImage.getType() +
                                            " image size " + timImage.getSize() +
                                            " image height " + timImage.getHeight() +
                                            " image width " + timImage.getWidth());*/


                                   final  String path = extraData.getData().getImagePath();
                                    Log.i("TIMImageElem","URL="+path);
                                    GlideMediaLoader.load(MyApplication.getContext(), imageView, path);
                                    layoutParams.setMargins(0, 0, 0, 0);
                                    holder.rightMessage.addView(imageView, pictureLayoutParams);
                                    holder.rightMessage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                                            intent.putExtra("path", path);
                                            mContext.startActivity(intent);
                                        }
                                    });
                                     return;
                                }
                            }
                            checkDetail.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    long nowTime = System.currentTimeMillis() / 1000;
                                    if (nowTime - timeStamp1 < 120 && !mTIMMessageList.get(pposition).isRevoke()) {
                                        showPopupWindow(v,myTIMMessage);
                       /* mTIMConversation.revokeMessage(timMessage, new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(mContext, "撤回消息失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess() {
                                mTIMMessageList.get(pposition).setRevoke(true);
                                notifyDataSetChanged();
                                Toast.makeText(mContext, "撤回消息成功", Toast.LENGTH_SHORT).show();
                            }
                        });*/
                                    }
                                    else {
                                        Toast.makeText(mContext, "超过两分钟，不能撤回", Toast.LENGTH_SHORT).show();
                                    }
                                    return true;
                                }
                            });
                            checkDetail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (url != null && !url.equals("")) {
                                        Intent intent = new Intent(mContext, CustomLinkActivity.class);
                                        intent.putExtra("url", url);
                                        mContext.startActivity(intent);
                                    } else if (extraData != null) {
                                        int type = extraData.getType();
                                        switch (type) {
                                            case 31:
                                                Intent prescriptionIntent = new Intent(mContext, PrescriptionActivity.class);
                                                prescriptionIntent.putExtra("action", String.valueOf(extraData.getData().getPrescriptionId()));
                                                prescriptionIntent.putExtra("prescriptionMessage", mDoctorMessage);
                                                mContext.startActivity(prescriptionIntent);
                                                break;
                                            case 32:
                                                Intent intent = new Intent(mContext, MedicalServiceActivity.class);
                                                intent.putExtra("action", String.valueOf(extraData.getData().getServiceId()));
                                                intent.putExtra("prescriptionMessage", mDoctorMessage);
                                                mContext.startActivity(intent);
                                                break;
                                            case 301:
                                                Intent CustomLinkintent = new Intent(mContext, CustomLinkActivity.class);
                                                CustomLinkintent.putExtra("url", extraData.getData().getLinkURL());
                                                mContext.startActivity(CustomLinkintent);
                                                break;
                                            case 28:

                                                String title = CustomMessageEntity.getFocusTitle();
                                                List<com.newdjk.doctor.ui.entity.CustomMessageEntity.ContentBean> Content = CustomMessageEntity.getContent();
                                                String number = "";
                                                String content = "";
                                                String stamp = extraData.getData().getStamp();
                                                if (Content != null && Content.size() == 2) {
                                                    number = Content.get(0).getContentElem().getText();
                                                    content = Content.get(1).getContentElem().getText();
                                                }
                                                //showPlusSign(title,content,stamp,number);
                                                PlusDialog PlusDialog = new PlusDialog(mContext);
                                                PlusDialog.show(title, content, stamp, number);
                                                break;

                                        }
                                    }
                                }
                            });
                            String title = CustomMessageEntity.getTitle();
                            Log.i("ChatAdapter", "title=" + title + ",type=" + type1);
                            if (title != null) {
                                servicePackageName.setText(title);
                            }  else if (type1 == 134) {
                                CustomMessageEntity.ExtDataBean.DataBean data = extraData.getData();
                                String time = data.getTime();
                                if (time != null && !time.equals("")) {
                                    servicePackageName.setText("视频通话时长："+time);
                                }
                                else {
                                    servicePackageName.setText("已挂断视频通话");
                                }

                            } else if (type1 == 133) {
                                servicePackageName.setText("我拒绝了视频邀请");
                            } else if (type1 == 132) {
                                servicePackageName.setText("对方无应答视频通话");
                            } else if (type1 == 131) {
                                servicePackageName.setText("已取消视频通话");
                            } else if (CustomMessageEntity.getFocusTitle() == null) {
                                titleLayout.setVisibility(View.GONE);
                            }
                            if (type1 == 33) {
                                list.setVisibility(View.GONE);
                                servicePackageName.setText("发放问卷：" + title);
                            } else {
                                list.setVisibility(View.VISIBLE);
                            }
                       /* else {
                            servicePackageName.setVisibility(View.GONE);
                        }
                        if(CustomMessageEntity.getFocusTitle() != null) {
                            servicePackageName.setVisibility(View.VISIBLE);
                            servicePackageName.setText(CustomMessageEntity.getFocusTitle());
                        }*/
                            if (type1 == 130 || type1 == 129) {
                                holder.leftPanel.setVisibility(View.GONE);
                                holder.rightPanel.setVisibility(View.GONE);
                                holder.leftMessage.removeAllViews();
                                holder.systemMessageLayout.setVisibility(View.GONE);
                            } else {
                                holder.rightMessage.addView(serviceView);
                            }

                            Log.i("ChatAdapter", "CustomMessageEntity=" + CustomMessageEntity.getTitle());
                        }
                    }
                } catch (JsonSyntaxException e) {
                    Log.i("ChatAdapter", "error=" + e.toString());
                    e.printStackTrace();
                }

              /*  else {
                    TextView textView = new TextView(MyApplication.getContext());
                    textView.setText(s);
                    textView.setTextSize(Dimension.SP, 16);
                    holder.rightMessage.addView(textView, layoutParams);
                }*/
            }
        } else {   //好友向自己发送的消息
            holder.leftPanel.setVisibility(View.VISIBLE);
            holder.rightPanel.setVisibility(View.GONE);
            holder.leftMessage.removeAllViews();
            holder.sender.setVisibility(View.GONE);
            holder.sender.setText(timMessage.getSender());
            holder.leftMessage.setBackgroundResource(R.drawable.chat_white_bg);
            // GlideMediaLoader.load(MyApplication.getContext(), holder.leftAvatar, mleftImagePath);
            Glide.with(MyApplication.getContext())
                    .load(mleftImagePath)
                    .dontAnimate()
                    .placeholder(R.drawable.patient_default_img)
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.leftAvatar);
            if (isRevoke || timMessage.status() == TIMMessageStatus.HasRevoked) {
                holder.systemMessage1.setText("对方撤回了一条消息");
                holder.leftPanel.setVisibility(View.GONE);
                holder.rightPanel.setVisibility(View.GONE);
                holder.leftMessage.removeAllViews();
                holder.systemMessageLayout.setVisibility(View.VISIBLE);
                holder.sendError.setVisibility(View.GONE);
                holder.sending.setVisibility(View.GONE);
            }
            //文字信息处理
           else if (element.getType() == TIMElemType.Text) {
                TIMTextElem textElem = (TIMTextElem) element;
                TextView textView = new TextView(MyApplication.getContext());
                textView.setText(textElem.getText());
                textView.setTextColor(Color.DKGRAY);
                textView.setTextSize(Dimension.SP, 14);

                holder.leftMessage.addView(textView, layoutParams);

                //语音信息处理
            } else if (element.getType() == TIMElemType.Sound) {
                final ImageView imageView = new ImageView(MyApplication.getContext());
                imageView.setId(R.id.img_id);
                imageView.setImageResource(R.drawable.left_voice3);
                holder.leftMessage.addView(imageView, layoutParams);
                RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout
                        .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                        , RelativeLayout.LayoutParams.WRAP_CONTENT);
                TextView textView = new TextView(MyApplication.getContext());
                textView.setText(((TIMSoundElem) element).getDuration() + "\"");
                textView.setTextSize(Dimension.SP, 14);
                textLayoutParams.rightMargin = 50;
                textLayoutParams.leftMargin = 30;
                textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                textLayoutParams.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                textView.setLayoutParams(textLayoutParams);
                textView.setTextColor(mContext.getResources().getColor(R.color.black));
                holder.leftMessage.addView(textView);
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // mMediaPlayer.release();
                        mPosition = -1;
                        if (mLastAnimationDrawable != null) {
                            mLastAnimationDrawable.selectDrawable(0);
                            mLastAnimationDrawable.stop();
                        }
                        //   animationDrawable.stop();
                    }
                });
                holder.leftMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {


                            if (mPosition == pposition) {
                                if (mLastAnimationDrawable != null) {
                                    mLastAnimationDrawable.selectDrawable(0);
                                    mLastAnimationDrawable.stop();
                                }
                                mMediaPlayer.stop();
                                imageView.setImageResource(R.drawable.left_voice3);
                                mPosition = -1;
                                // playSound(mMediaPlayer, timMessage);
                                // animationDrawable.stop();
                            } else {
                                imageView.setImageResource(R.drawable.left_voice);
                                RelativeLayout relativeLayout = (RelativeLayout) v;
                                ImageView childImageView = (ImageView) relativeLayout.getChildAt(0);
                                AnimationDrawable AnimationDrawable = (AnimationDrawable) childImageView.getDrawable();
                                operatePlaySound(AnimationDrawable);
                                //   mMediaPlayer = new MediaPlayer();
                                playSound(mMediaPlayer, timMessage, false, null, null);
                                mPosition = pposition;
                            }

                        } catch (IllegalStateException e) {
                            Log.i("zdp", e.toString());
                        }
                    }
                });

                //图片信息处理
            } else if (element.getType() == TIMElemType.Image) {
                holder.leftMessage.setBackgroundResource(0);
                TIMImageElem imageElem = (TIMImageElem) element;
                for (TIMImage timImage : imageElem.getImageList()) {
                    if (timImage.getType().toString().equals("Large")) {
                        final String path = timImage.getUrl();
                        holder.leftMessage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                                intent.putExtra("path", path);
                                mContext.startActivity(intent);
                            }
                        });
                    }
                    if (timImage.getType().toString().equals("Thumb")) {
                        int height = (int) timImage.getHeight();
                        int width = (int) timImage.getWidth();
                        RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                                .LayoutParams(width
                                , height);
                        ImageView imageView = new ImageView(MyApplication.getContext());
                        Log.d("zdp", "image type: " + timImage.getType() +
                                " image size " + timImage.getSize() +
                                " image height " + timImage.getHeight() +
                                " image width " + timImage.getWidth());
                        Glide.with(MyApplication.getContext())
                                .load(timImage.getUrl())
                                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                        layoutParams.setMargins(0, 0, 0, 0);

                        holder.leftMessage.addView(imageView, pictureLayoutParams);

                    }
                   /* ImageView imageView = new ImageView(MyApplication.getContext());
                    Glide.with(MyApplication.getContext())
                            .load(timImage.getUrl())
                         //   .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);
                    layoutParams.setMargins(0, 0, 0, 0);
                    holder.leftMessage.addView(imageView, layoutParams);*/
                }
            } else if (element.getType() == TIMElemType.Custom) {

                try {
                    TIMCustomElem customElem = (TIMCustomElem) element;
                    String desc = customElem.getDesc();
                    String s = new String(customElem.getData());
                    //   String s = mGson.toJson(data);
                    if (desc != null && desc.equals("TIMSoundElem")) {
                        MsgContentEntity msgContentEntity = mGson.fromJson(s, MsgContentEntity.class);
                        final String url = msgContentEntity.getFileUrl();
                        final String uuid = msgContentEntity.getUUID();
                        final File file = new File(MyApplication.getContext().getFilesDir()
                                + File.separator + uuid);
                        final ImageView imageView = new ImageView(MyApplication.getContext());
                        imageView.setId(R.id.img_id);
                        imageView.setImageResource(R.drawable.left_voice3);
                        holder.leftMessage.addView(imageView, layoutParams);
                        RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout
                                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                                , RelativeLayout.LayoutParams.WRAP_CONTENT);
                        TextView textView = new TextView(MyApplication.getContext());
                        textView.setText(msgContentEntity.getSecond() + "\"");
                        textView.setTextSize(Dimension.SP, 14);
                        textLayoutParams.rightMargin = 50;
                        textLayoutParams.leftMargin = 30;
                        textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                        textLayoutParams.addRule(RelativeLayout.RIGHT_OF, imageView.getId());
                        textView.setLayoutParams(textLayoutParams);
                        holder.leftMessage.addView(textView);
                        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                // mMediaPlayer.release();
                                mPosition = -1;
                                if (mLastAnimationDrawable != null) {
                                    mLastAnimationDrawable.selectDrawable(0);
                                    mLastAnimationDrawable.stop();
                                }
                                //   animationDrawable.stop();
                            }
                        });
                        holder.leftMessage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Log.i("ChatAdapter", "s=" + file.getAbsolutePath());
                                       /* holder.sendError.setVisibility(View.GONE);
                                        holder.sending.setVisibility(View.GONE);*/
                                    if (mPosition == pposition) {
                                        if (mLastAnimationDrawable != null) {
                                            mLastAnimationDrawable.selectDrawable(0);
                                            mLastAnimationDrawable.stop();
                                        }
                                        mMediaPlayer.stop();
                                        imageView.setImageResource(R.drawable.left_voice3);
                                        mPosition = -1;
                                        // playSound(mMediaPlayer, timMessage);
                                        // animationDrawable.stop();
                                    } else {
                                        imageView.setImageResource(R.drawable.left_voice);
                                        RelativeLayout relativeLayout = (RelativeLayout) v;
                                        ImageView childImageView = (ImageView) relativeLayout.getChildAt(0);
                                        AnimationDrawable AnimationDrawable = (AnimationDrawable) childImageView.getDrawable();
                                        operatePlaySound(AnimationDrawable);
                                        //   mMediaPlayer = new MediaPlayer();
                                        playSound(mMediaPlayer, timMessage, true, uuid, url);
                                        mPosition = pposition;
                                    }

                                } catch (IllegalStateException e) {
                                    Log.i("zdp", e.toString());
                                }
                            }
                        });


                    }
                    else if (desc != null && desc.equals("TIMImageElem")) {
                        holder.leftMessage.setBackgroundResource(0);
                        Type jsonType = new TypeToken< List<ImageInfoArrayEntity>>() {
                        }.getType();
                        Log.i("TIMImageElem","s="+s);
                        List<ImageInfoArrayEntity> imageInfoArray =  mGson.fromJson(s, jsonType);
                        if (imageInfoArray != null && imageInfoArray.size() >0) {
                            for(int i = 0;i<imageInfoArray.size();i++) {
                                ImageInfoArrayEntity imageInfoArrayEntity = imageInfoArray.get(i);
                                int type = imageInfoArrayEntity.getType();
                                if (type == 1) {
                                     String url = imageInfoArrayEntity.getURL();
                                    final String path = url.replace("\\","/");
                                    holder.leftMessage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(mContext, ShowOriginPictureActivity.class);
                                            intent.putExtra("path", path);
                                            mContext.startActivity(intent);
                                        }
                                    });
                                }
                                else if (type == 3) {
                                    int height =  imageInfoArrayEntity.getHeight();
                                    int width =  imageInfoArrayEntity.getWidth();
                                    RelativeLayout.LayoutParams pictureLayoutParams = new RelativeLayout
                                            .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                                            , RelativeLayout.LayoutParams.WRAP_CONTENT);
                                    ImageView imageView = new ImageView(MyApplication.getContext());

                                    String url = imageInfoArrayEntity.getURL();
                                    String path = url.replace("\\","/");
                                    Log.i("TIMImageElem","URL="+path);
                                    GlideMediaLoader.load(MyApplication.getContext(), imageView, path);
                                    layoutParams.setMargins(0, 0, 0, 0);
                                    holder.leftMessage.addView(imageView, pictureLayoutParams);

                                }
                            }
                        }
                    }
                    else if (s != null && !s.equals("")) {
                        holder.leftMessage.setBackgroundResource(0);
                        RelativeLayout.LayoutParams customLayoutParams = new RelativeLayout
                                .LayoutParams(600
                                , RelativeLayout.LayoutParams.WRAP_CONTENT);
                        CustomMessageEntity CustomMessageEntity = mGson.fromJson(s, CustomMessageEntity.class);
                        boolean isSystem = CustomMessageEntity.isIsSystem();
                        Log.e("ChatAdapter", "s=" + s);
                        if (isSystem) {
                            holder.systemMessageLayout.setVisibility(View.VISIBLE);
                            holder.leftPanel.setVisibility(View.GONE);
                            holder.rightPanel.setVisibility(View.GONE);
                            holder.leftMessage.removeAllViews();
                            boolean IsShowDividingLine = CustomMessageEntity.isShowDividingLine();
                            int showType = CustomMessageEntity.getShowType();
                            if (showType == 0 || showType == 2) {
                                if (IsShowDividingLine) {
                                    holder.line.setVisibility(View.VISIBLE);
                                    holder.systemMessage1.setBackgroundColor(mContext.getResources().getColor(R.color.activity_bg));
                                    holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.text_gray2));
                                    holder.systemMessageLayout.setBackgroundResource(0);
                                } else {
                                    holder.systemMessage1.setBackgroundColor(0);
                                    holder.systemMessageLayout.setBackgroundResource(R.drawable.system_message_background);
                                    holder.line.setVisibility(View.GONE);
                                    holder.systemMessage1.setTextColor(mContext.getResources().getColor(R.color.white));
                                }
                                holder.systemMessage1.setText(CustomMessageEntity.getTitle());
                            } else {
                                holder.leftPanel.setVisibility(View.GONE);
                                holder.rightPanel.setVisibility(View.GONE);
                                holder.leftMessage.removeAllViews();
                                holder.systemMessageLayout.setVisibility(View.GONE);
                            }
                        } else {

                            View serviceView = LayoutInflater.
                                    from(MyApplication.getContext()).inflate(R.layout.service_package, null, false);
                            TextView servicePackageName = serviceView.findViewById(R.id.service_paceage_name);
                            RelativeLayout checkDetail = serviceView.findViewById(R.id.check_detail);
                            RecyclerView list = serviceView.findViewById(R.id.service_detail_list);
                            View line = serviceView.findViewById(R.id.line);
                            CustomMessageAdapter adapter = new CustomMessageAdapter(CustomMessageEntity.getContent());
                            list.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
                            list.setAdapter(adapter);
                            list.setLayoutManager(new LinearLayoutManager(mContext, OrientationHelper.VERTICAL, false));
                            final String url = CustomMessageEntity.getLinkUrl();
                            final CustomMessageEntity.ExtDataBean extraData = CustomMessageEntity.getExtData();
                            // adapter.setDatalist(CustomMessageEntity.getContent());
                            int type1 = 0;
                            if (extraData == null) {
                                checkDetail.setVisibility(View.GONE);
                                line.setVisibility(View.GONE);
                            } else {
                                type1 = extraData.getType();
                                if (type1 == 34 ||type1 > 100) {
                                    checkDetail.setVisibility(View.GONE);
                                    line.setVisibility(View.GONE);
                                } else {
                                    checkDetail.setVisibility(View.VISIBLE);

                                }
                            }
                            checkDetail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (url != null && !url.equals("")) {
                                        Intent intent = new Intent(mContext, CustomLinkActivity.class);
                                        intent.putExtra("url", url);
                                        mContext.startActivity(intent);
                                    } else if (extraData != null) {
                                        int type = extraData.getType();
                                        switch (type) {
                                           /* case 31:
                                                Intent prescriptionIntent = new Intent(mContext, PrescriptionActivity.class);
                                                prescriptionIntent.putExtra("action", String.valueOf(extraData.getData().getPrescriptionId()));
                                                prescriptionIntent.putExtra("prescriptionMessage", mDoctorMessage);
                                                mContext.startActivity(prescriptionIntent);
                                                break;*/
                                            case 13:
                                                if (mIsHasOpenPres) {
                                                    Intent prescriptionIntent = new Intent(mContext, PrescriptionActivity.class);
                                                    prescriptionIntent.putExtra("id", String.valueOf(extraData.getData().getPrescriptionId()));
                                                    prescriptionIntent.putExtra("prescriptionMessage", mDoctorMessage);
                                                    mContext.startActivity(prescriptionIntent);
                                                }
                                                else {
                                                    Toast.makeText(mContext,"你的权限不足，无法开处方",Toast.LENGTH_SHORT).show();
                                                }
                                                break;
                                            case 32:
                                                Intent intent = new Intent(mContext, MedicalServiceActivity.class);
                                                intent.putExtra("action", String.valueOf(extraData.getData().getServiceId()));
                                                intent.putExtra("prescriptionMessage", mDoctorMessage);
                                                mContext.startActivity(intent);
                                                break;
                                            case 27:
                                                Log.i("ChatAdapter", "imgId=" + extraData.getData().getImageId() + ",medicalTempId=" + extraData.getData().getMedicalTempId() + ",patientName=" + extraData.getData().getPatientName());
                                                Intent caseDetailIntent = new Intent(mContext, CaseDetailActivity.class);
                                                caseDetailIntent.putExtra("imgId", extraData.getData().getImageId());
                                                caseDetailIntent.putExtra("medicalTempId", extraData.getData().getMedicalTempId());
                                                caseDetailIntent.putExtra("patientName", extraData.getData().getPatientName());
                                                caseDetailIntent.putExtra("prescriptionMessage", mDoctorMessage);
                                                mContext.startActivity(caseDetailIntent);
                                                break;
                                          /*  case 42:
                                                Intent doctorPraiseIntent = new Intent(mContext, DoctorPraiseActivity.class);
                                                mContext.startActivity(doctorPraiseIntent);
                                                break;*/
                                            case 37:
                                                Intent doctorPraiseIntent = new Intent(mContext, DoctorPraiseActivity.class);
                                                mContext.startActivity(doctorPraiseIntent);
                                                break;
                                        }
                                    }

                                }
                            });
                            String title = CustomMessageEntity.getTitle();
                            if (title != null) {
                                servicePackageName.setText(title);
                            }
                            if (type1 == 129) {
                                servicePackageName.setText( "对方开启了视频邀请");

                            }
                           else if (type1 == 133) {
                                    servicePackageName.setText( "对方拒绝了视频邀请");

                            }
                          else  if (type1 == 134) {
                                CustomMessageEntity.ExtDataBean.DataBean data = extraData.getData();
                                String time = data.getTime();
                                if (time != null && !time.equals("")) {
                                    servicePackageName.setText("视频通话时长："+time);
                                }
                                else {
                                    servicePackageName.setText("对方挂断了视频");
                                }
                            }
                          else  if (type1 == 132) {
                                servicePackageName.setText("对方无应答视频通话");

                            }
                           if (type1 == 130) {
                                holder.leftPanel.setVisibility(View.GONE);
                                holder.rightPanel.setVisibility(View.GONE);
                                holder.rightMessage.removeAllViews();
                                holder.systemMessageLayout.setVisibility(View.GONE);
                            } else {
                                holder.leftMessage.addView(serviceView, layoutParams);
                            }
                        }
                    }
                } catch (JsonSyntaxException e) {
                    Log.i("ChatAdapter", "error=" + e.toString());
                    e.printStackTrace();
                }
               /* TIMCustomElem customElem = (TIMCustomElem) element;
                String s = new String(customElem.getData());
                TextView textView = new TextView(MyApplication.getContext());
                textView.setText(s);
                textView.setTextSize(Dimension.SP, 16);
                holder.leftMessage.addView(textView, layoutParams);*/
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTIMMessageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.system_message_layout)
        FrameLayout systemMessageLayout;

        @BindView(R.id.systemMessage)
        TextView systemMessage;

        @BindView(R.id.leftPanel)
        RelativeLayout leftPanel;

        @BindView(R.id.rightPanel)
        RelativeLayout rightPanel;

        @BindView(R.id.leftAvatar)
        CircleImageView leftAvatar;

        @BindView(R.id.rightAvatar)
        CircleImageView rightAvatar;

        @BindView(R.id.sender)
        TextView sender;

        @BindView(R.id.leftMessage)
        RelativeLayout leftMessage;

        @BindView(R.id.rightMessage)
        RelativeLayout rightMessage;

        @BindView(R.id.sendStatus)
        RelativeLayout sendStatus;

        @BindView(R.id.rightDesc)
        TextView rightDesc;

        @BindView(R.id.sending)
        ProgressBar sending;

        @BindView(R.id.sendError)
        ImageView sendError;
        @BindView(R.id.line)
        View line;
        @BindView(R.id.systemMessage1)
        TextView systemMessage1;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public void playSound(final MediaPlayer mediaPlayer, TIMMessage timMessage, boolean isLocalMessage, String uuid, final String urlPath) {
        mediaPlayer.reset();
        // mediaPlayer = new MediaPlayer();
       /* mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
                animationDrawable .selectDrawable(0);
                animationDrawable.stop();
            }
        });*/
        if (isLocalMessage) {
            final String path = MyApplication.getContext().getFilesDir()
                    + File.separator + uuid;
            File soundFile = new File(path);
            Log.i("ChatAdapter", "path=" + path);
            if (soundFile.exists()) {
                try {
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                final MediaPlayer finalMediaPlayer = mediaPlayer;
                downFile(urlPath, soundFile, mediaPlayer);
            }
        } else {
            TIMElem element = timMessage.getElement(0);
            if (element.getType() == TIMElemType.Sound) {
                final TIMSoundElem soundElem = (TIMSoundElem) element;
                final String path = MyApplication.getContext().getFilesDir()
                        + File.separator + ((TIMSoundElem) element).getUuid();
                File soundFile = new File(path);
                Log.i("ChatAdapter", "path=" + path);
                if (soundFile.exists()) {
                    try {
                        mediaPlayer.setDataSource(path);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    final MediaPlayer finalMediaPlayer = mediaPlayer;
                    soundElem.getSoundToFile(path, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {

                        }

                        @Override
                        public void onSuccess() {
                            soundElem.setPath(path);
                            try {
                                finalMediaPlayer.setDataSource(path);
                                finalMediaPlayer.prepare();
                                finalMediaPlayer.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }


            }
        }
    }

    public void operatePlaySound(AnimationDrawable animationDrawable) {
        if (mLastAnimationDrawable == null) {
            mLastAnimationDrawable = animationDrawable;
            mLastAnimationDrawable.start();
        } else {
            try {
                mLastAnimationDrawable.selectDrawable(0);
                mLastAnimationDrawable.stop();
                mLastAnimationDrawable = animationDrawable;
                mLastAnimationDrawable.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void showPlusSign(final String title, String content, String stamp, String number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(mContext).inflate(R.layout.plus_sign_detail, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view);


        TextView titleTx = (TextView) view.findViewById(R.id.title);
        TextView numberTx = (TextView) view.findViewById(R.id.number);
        ImageView stampIv = (ImageView) view.findViewById(R.id.stamp);
        TextView signContentTx = (TextView) view.findViewById(R.id.sign_content);
        titleTx.setText(title);
        numberTx.setText(number);
        signContentTx.setText(content);
        Glide.with(MyApplication.getContext())
                .load(stamp)
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(stampIv);
        builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });

        builder.show();
    }

    public void stopMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }

    public void downFile(final String path, final File file, final MediaPlayer mediaPlayer) {
        LoadDialog.show(mContext);
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request
                .Builder()
                .get()
                .url(path)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    InputStream inputStream = response.body().byteStream();
                 /*   Log.i("ChatActivity","path1="+path+",uuid1="+uuid);
                    //将图片保存到本地存储卡中
                    File file = new File( MyApplication.getContext().getFilesDir()
                            + File.separator + uuid);*/
                    //  File file = new File(Environment.getExternalStorageDirectory(), uuid);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] temp = new byte[2048];
                    int length;
                    while ((length = inputStream.read(temp)) != -1) {
                        fileOutputStream.write(temp, 0, length);
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    if (mediaPlayer != null) {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(path);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    }
                    //    notifyDataSetChanged();
                    inputStream.close();
                    LoadDialog.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void showPopupWindow(View view,final MyTIMMessage myTIMMessage ) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(
                R.layout.revoke_window, null);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 设置按钮的点击事件
        TextView button = (TextView) contentView.findViewById(R.id.revoke);


        final PopupWindow popupWindow = new PopupWindow(contentView,
                contentView.getMeasuredWidth(), contentView.getMeasuredHeight() ,true);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TIMMessage timMessage = myTIMMessage.getTimMessage();
                mTIMConversation.revokeMessage(timMessage, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        popupWindow.dismiss();
                        Toast.makeText(mContext, "撤回消息失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess() {
                        popupWindow.dismiss();
                        myTIMMessage.setRevoke(true);
                        notifyDataSetChanged();
                        Toast.makeText(mContext, "撤回消息成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        popupWindow.setTouchable(true);

      /*  popupWindow.setTouchInterceptor(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.selectmenu_bg_downward));*/

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);


    }

}
