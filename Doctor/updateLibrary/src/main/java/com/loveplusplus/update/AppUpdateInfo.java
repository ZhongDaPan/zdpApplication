package com.loveplusplus.update;

/**
 * Created by Struggle on 2018/10/19.
 */

public class AppUpdateInfo {

    /**
     * Code : 0
     * Message :
     * Data : {"AppManageId":2,"AppCode":"com.newdjk.doctor","AppName":"芸医生APP","AppVersion":"2.0","AppPath":"http://resource.newstarthealth.cn/apk/yys.apk","Invalid":0,"CreateTime":"2018-10-18 10:47:22","UpdateTime":"2018-10-18 18:27:55"}
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
         * AppManageId : 2
         * AppCode : com.newdjk.doctor
         * AppName : 芸医生APP
         * AppVersion : 2.0
         * AppPath : http://resource.newstarthealth.cn/apk/yys.apk
         * Invalid : 0
         * CreateTime : 2018-10-18 10:47:22
         * UpdateTime : 2018-10-18 18:27:55
         */

        private int AppManageId;
        private String AppCode;
        private String AppName;
        private String AppVersion;
        private String AppPath;
        private int Invalid;
        private String CreateTime;
        private String UpdateTime;
        private String H5Version;
        private String H5Path;
        private String Remark;

        public String getH5Version() {
            return H5Version;
        }

        public void setH5Version(String h5Version) {
            H5Version = h5Version;
        }

        public String getH5Path() {
            return H5Path;
        }

        public void setH5Path(String h5Path) {
            H5Path = h5Path;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public int getAppManageId() {
            return AppManageId;
        }

        public void setAppManageId(int AppManageId) {
            this.AppManageId = AppManageId;
        }

        public String getAppCode() {
            return AppCode;
        }

        public void setAppCode(String AppCode) {
            this.AppCode = AppCode;
        }

        public String getAppName() {
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

        public int getInvalid() {
            return Invalid;
        }

        public void setInvalid(int Invalid) {
            this.Invalid = Invalid;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }
    }
}
