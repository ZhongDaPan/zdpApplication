package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   AudioToTextEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/12/21 9:52
 *  @描述：
 */
public class AudioToTextEntity {

    /**
     * code : 0
     * desc : success
     * data : 开始录音，开始录音。
     * sid : 919901538ff748dea77ce53620ded53c
     */

    private int code;
    private String desc;
    private String data;
    private String sid;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
