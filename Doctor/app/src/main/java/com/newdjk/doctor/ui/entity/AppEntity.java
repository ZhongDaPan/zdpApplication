package com.newdjk.doctor.ui.entity;

public class AppEntity {


    /**
     * Code : 0
     * Message :
     * Data : {"AppCode":null,"AppName":null,"AppVersion":0,"AppPath":null,"Remark":null,"MustUpdate":0}
     */

    private int Code;
    private String Message;
    private DataBean Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * AppCode : null
         * AppName : null
         * AppVersion : 0
         * AppPath : null
         * Remark : null
         * MustUpdate : 0
         */

        private String AppCode;
        private String AppName;
        private String AppVersion;
        private String AppPath;
        private String Remark;
        private int MustUpdate;
        private String ReleaseVersion;

        public String getReleaseVersion() {
            return ReleaseVersion;
        }

        public void setReleaseVersion(String releaseVersion) {
            ReleaseVersion = releaseVersion;
        }

        public Object getAppCode() {
            return AppCode;
        }

        public void setAppCode(String AppCode) {
            this.AppCode = AppCode;
        }

        public Object getAppName() {
            return AppName;
        }

        public void setAppName(String AppName) {
            this.AppName = AppName;
        }

        public String getAppVersion() {
            return AppVersion;
        }

        public void setAppVersion(String AppVersion) {
            this.AppVersion = AppVersion;
        }

        public String getAppPath() {
            return AppPath;
        }

        public void setAppPath(String AppPath) {
            this.AppPath = AppPath;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public int getMustUpdate() {
            return MustUpdate;
        }

        public void setMustUpdate(int MustUpdate) {
            this.MustUpdate = MustUpdate;
        }
    }
}
