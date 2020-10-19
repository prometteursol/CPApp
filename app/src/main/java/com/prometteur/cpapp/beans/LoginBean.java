package com.prometteur.cpapp.beans;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginBean implements Serializable
{
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("apt_request")
    @Expose
    private Integer aptRequest;
    private final static long serialVersionUID = -5584638056136503349L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Integer getAptRequest() {
        return aptRequest;
    }

    public void setAptRequest(Integer aptRequest) {
        this.aptRequest = aptRequest;
    }

    public class Result implements Serializable
    {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("user_surv_id")
        @Expose
        private String userSurvId;
        @SerializedName("user_branch_id")
        @Expose
        private String userBranchId;
        @SerializedName("user_role")
        @Expose
        private String userRole;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_f_name")
        @Expose
        private String userFName;
        @SerializedName("user_l_name")
        @Expose
        private String userLName;
        @SerializedName("user_email")
        @Expose
        private String userEmail;
        @SerializedName("user_mb_no")
        @Expose
        private String userMbNo;
        @SerializedName("user_img")
        @Expose
        private String userImg;
        @SerializedName("user_speciality")
        @Expose
        private String userSpeciality;
        @SerializedName("user_documents")
        @Expose
        private String userDocuments;
        @SerializedName("user_password")
        @Expose
        private String userPassword;
        @SerializedName("user_fcm_key")
        @Expose
        private String userFcmKey;
        @SerializedName("user_session")
        @Expose
        private String userSession;
        @SerializedName("user_status")
        @Expose
        private String userStatus;
        @SerializedName("user_create_by")
        @Expose
        private String userCreateBy;
        @SerializedName("user_create_date")
        @Expose
        private String userCreateDate;
        @SerializedName("user_update_by")
        @Expose
        private String userUpdateBy;
        @SerializedName("user_update_date")
        @Expose
        private String userUpdateDate;
        @SerializedName("bran_open_status")//1=open 0=closed
        @Expose
        private String branOpenStatus;
        @SerializedName("bran_closed")//0=open 1=closed
        @Expose
        private String branClosed;
        @SerializedName("bran_name")
        @Expose
        private String branName;
        @SerializedName("bran_img")
        @Expose
        private String branImg;
        @SerializedName("bran_addr")
        @Expose
        private String branAddr;
        @SerializedName("bran_city")
        @Expose
        private String branCity;
        private final static long serialVersionUID = 3424943404650024596L;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserSurvId() {
            return userSurvId;
        }

        public void setUserSurvId(String userSurvId) {
            this.userSurvId = userSurvId;
        }

        public String getUserBranchId() {
            return userBranchId;
        }

        public void setUserBranchId(String userBranchId) {
            this.userBranchId = userBranchId;
        }

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
            this.userRole = userRole;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserFName() {
            return userFName;
        }

        public void setUserFName(String userFName) {
            this.userFName = userFName;
        }

        public String getUserLName() {
            return userLName;
        }

        public void setUserLName(String userLName) {
            this.userLName = userLName;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserMbNo() {
            return userMbNo;
        }

        public void setUserMbNo(String userMbNo) {
            this.userMbNo = userMbNo;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public String getUserSpeciality() {
            return userSpeciality;
        }

        public void setUserSpeciality(String userSpeciality) {
            this.userSpeciality = userSpeciality;
        }

        public String getUserDocuments() {
            return userDocuments;
        }

        public void setUserDocuments(String userDocuments) {
            this.userDocuments = userDocuments;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getUserFcmKey() {
            return userFcmKey;
        }

        public void setUserFcmKey(String userFcmKey) {
            this.userFcmKey = userFcmKey;
        }

        public String getUserSession() {
            return userSession;
        }

        public void setUserSession(String userSession) {
            this.userSession = userSession;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getUserCreateBy() {
            return userCreateBy;
        }

        public void setUserCreateBy(String userCreateBy) {
            this.userCreateBy = userCreateBy;
        }

        public String getUserCreateDate() {
            return userCreateDate;
        }

        public void setUserCreateDate(String userCreateDate) {
            this.userCreateDate = userCreateDate;
        }

        public String getUserUpdateBy() {
            return userUpdateBy;
        }

        public void setUserUpdateBy(String userUpdateBy) {
            this.userUpdateBy = userUpdateBy;
        }

        public String getUserUpdateDate() {
            return userUpdateDate;
        }

        public void setUserUpdateDate(String userUpdateDate) {
            this.userUpdateDate = userUpdateDate;
        }

        public String getBranOpenStatus() {
            return branOpenStatus;
        }

        public void setBranOpenStatus(String branOpenStatus) {
            this.branOpenStatus = branOpenStatus;
        }

        public String getBranClosed() {
            return branClosed;
        }

        public void setBranClosed(String branClosed) {
            this.branClosed = branClosed;
        }

        public String getBranName() {
            return branName;
        }

        public void setBranName(String branName) {
            this.branName = branName;
        }

        public String getBranImg() {
            return branImg;
        }

        public void setBranImg(String branImg) {
            this.branImg = branImg;
        }

        public String getBranAddr() {
            return branAddr;
        }

        public void setBranAddr(String branAddr) {
            this.branAddr = branAddr;
        }

        public String getBranCity() {
            return branCity;
        }

        public void setBranCity(String branCity) {
            this.branCity = branCity;
        }
    }
}