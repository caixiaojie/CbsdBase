package com.yjhs.cbsdbase.bean;

import com.google.gson.JsonElement;

/**
 * Author:Ariana Wong
 * Date: 2017-12-18
 * Desctiption:
 */

public class LoginResp {
    private String session_id;
    private UserinfoBean userinfo;
    private String strImgRootPath;

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public String getStrImgRootPath() {
        return strImgRootPath;
    }

    public void setStrImgRootPath(String strImgRootPath) {
        this.strImgRootPath = strImgRootPath;
    }



    public static class UserinfoBean {
        /**
         * strUserinfoId : 5428b7298fdc416d93f0a6e5269aa297
         * strUserPhone : 18408249795
         * strPassword : *******
         * strRegisterTime : 2018-04-27 10:43:28
         * strNickName : cl
         * strUserType : 0
         * iState : 0
         * iIsDelete : 0
         */

        private String strUserinfoId;
        private String strUserPhone;
        private String strPassword;
        private String strRegisterTime;
        private String strNickName;
        private String strUserType;
        private String strHeadImage;
        private String strRemark;
        private String strCompanyId;
        private int goldCoin;//金币
        private int iState;
        private int iIsDelete;


        public int getGoldCoin() {
            return goldCoin;
        }

        public void setGoldCoin(int goldCoin) {
            this.goldCoin = goldCoin;
        }

        public String getStrCompanyId() {
            return strCompanyId;
        }

        public void setStrCompanyId(String strCompanyId) {
            this.strCompanyId = strCompanyId;
        }

        public String getStrRemark() {
            return strRemark;
        }

        public void setStrRemark(String strRemark) {
            this.strRemark = strRemark;
        }

        public String getStrHeadImage() {
            return strHeadImage;
        }

        public void setStrHeadImage(String strHeadImage) {
            this.strHeadImage = strHeadImage;
        }

        public String getStrUserinfoId() {
            return strUserinfoId;
        }

        public void setStrUserinfoId(String strUserinfoId) {
            this.strUserinfoId = strUserinfoId;
        }

        public String getStrUserPhone() {
            return strUserPhone;
        }

        public void setStrUserPhone(String strUserPhone) {
            this.strUserPhone = strUserPhone;
        }

        public String getStrPassword() {
            return strPassword;
        }

        public void setStrPassword(String strPassword) {
            this.strPassword = strPassword;
        }

        public String getStrRegisterTime() {
            return strRegisterTime;
        }

        public void setStrRegisterTime(String strRegisterTime) {
            this.strRegisterTime = strRegisterTime;
        }

        public String getStrNickName() {
            return strNickName;
        }

        public void setStrNickName(String strNickName) {
            this.strNickName = strNickName;
        }

        public String getStrUserType() {
            return strUserType;
        }

        public void setStrUserType(String strUserType) {
            this.strUserType = strUserType;
        }

        public int getIState() {
            return iState;
        }

        public void setIState(int iState) {
            this.iState = iState;
        }

        public int getIIsDelete() {
            return iIsDelete;
        }

        public void setIIsDelete(int iIsDelete) {
            this.iIsDelete = iIsDelete;
        }
    }
}
