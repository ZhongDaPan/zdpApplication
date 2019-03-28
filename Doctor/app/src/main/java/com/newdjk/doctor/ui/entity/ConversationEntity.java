package com.newdjk.doctor.ui.entity;

import com.tencent.TIMMessage;

public class ConversationEntity {
   private TIMMessage timMessage;
   private long unReadNum = 0;

    public TIMMessage getTimMessage() {
        return timMessage;
    }

    public void setTimMessage(TIMMessage timMessage) {
        this.timMessage = timMessage;
    }

    public long getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(long unReadNum) {
        this.unReadNum = unReadNum;
    }
}
