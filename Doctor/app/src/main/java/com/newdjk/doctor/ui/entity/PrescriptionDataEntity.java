package com.newdjk.doctor.ui.entity;

/*
 *  @项目名：  Doctor
 *  @包名：    com.newdjk.doctor.ui.entity
 *  @文件名:   PrescriptionDataEntity
 *  @创建者:   huhai
 *  @创建时间:  2018/12/5 9:21
 *  @描述：
 */
public class PrescriptionDataEntity {

    /**
     * AuditorId : 1
     * AuditorName : sample string 2
     * PassCount : 3
     * RejectCount : 4
     * Total : 5
     * PassRate : 6.0
     * ResponseRate : 7.0
     */

    private int AuditorId;
    private String AuditorName;
    private int PassCount;
    private int RejectCount;
    private int Total;
    private double PassRate;
    private double ResponseRate;

    public int getAuditorId() {
        return AuditorId;
    }

    public void setAuditorId(int AuditorId) {
        this.AuditorId = AuditorId;
    }

    public String getAuditorName() {
        return AuditorName;
    }

    public void setAuditorName(String AuditorName) {
        this.AuditorName = AuditorName;
    }

    public int getPassCount() {
        return PassCount;
    }

    public void setPassCount(int PassCount) {
        this.PassCount = PassCount;
    }

    public int getRejectCount() {
        return RejectCount;
    }

    public void setRejectCount(int RejectCount) {
        this.RejectCount = RejectCount;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public double getPassRate() {
        return PassRate;
    }

    public void setPassRate(double PassRate) {
        this.PassRate = PassRate;
    }

    public double getResponseRate() {
        return ResponseRate;
    }

    public void setResponseRate(double ResponseRate) {
        this.ResponseRate = ResponseRate;
    }
}
