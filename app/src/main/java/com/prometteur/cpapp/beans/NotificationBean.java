package com.prometteur.cpapp.beans;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationBean implements Serializable
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
private final static long serialVersionUID = -6311977973105987031L;

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
    public static class Result implements Serializable
    {
public Result()
{}
        @SerializedName("noti_id")
        @Expose
        private String notiId;
        @SerializedName("noti_title")
        @Expose
        private String notiTitle;
        @SerializedName("noti_message")
        @Expose
        private String notiMessage;
        @SerializedName("noti_appointment_id")
        @Expose
        private String notiAppointmentId;
        @SerializedName("noti_branch_id")
        @Expose
        private String notiBranchId;
        @SerializedName("noti_end_user_id")
        @Expose
        private String notiEndUserId;
        @SerializedName("noti_cp_user_id")
        @Expose
        private String notiCpUserId;
        @SerializedName("noti_from")
        @Expose
        private String notiFrom;
        @SerializedName("noti_to")
        @Expose
        private String notiTo;
        @SerializedName("noti_type")
        @Expose
        private String notiType;
        @SerializedName("noti_read_status")
        @Expose
        private String notiReadStatus;
        @SerializedName("noti_create_date")
        @Expose
        private String notiCreateDate;
        @SerializedName("apt_id")
        @Expose
        private String aptId;
        @SerializedName("apt_cp_branch")
        @Expose
        private String aptCpBranch;
        @SerializedName("apt_user_id")
        @Expose
        private String aptUserId;
        @SerializedName("apt_date")
        @Expose
        private String aptDate;
        @SerializedName("apt_time")
        @Expose
        private String aptTime;
        @SerializedName("apt_subtotal")
        @Expose
        private String aptSubtotal;
        @SerializedName("apt_gst")
        @Expose
        private String aptGst;
        @SerializedName("apt_amount")
        @Expose
        private String aptAmount;
        @SerializedName("apt_payment_status")
        @Expose
        private String aptPaymentStatus;
        @SerializedName("apt_payment_type")
        @Expose
        private String aptPaymentType;
        @SerializedName("apt_payment_date")
        @Expose
        private String aptPaymentDate;
        @SerializedName("apt_reshedule_by")
        @Expose
        private String aptResheduleBy;
        @SerializedName("apt_reshedule_date")
        @Expose
        private String aptResheduleDate;
        @SerializedName("apt_status")
        @Expose
        private String aptStatus;
        @SerializedName("apt_reject_reason")
        @Expose
        private String aptRejectReason;
        @SerializedName("apt_create_date")
        @Expose
        private String aptCreateDate;
        @SerializedName("apt_create_by")
        @Expose
        private String aptCreateBy;
        @SerializedName("apt_update_date")
        @Expose
        private String aptUpdateDate;
        @SerializedName("apt_update_by")
        @Expose
        private String aptUpdateBy;
        private final static long serialVersionUID = 7753518684640954141L;

        public String getNotiId() {
            return notiId;
        }

        public void setNotiId(String notiId) {
            this.notiId = notiId;
        }

        public String getNotiTitle() {
            return notiTitle;
        }

        public void setNotiTitle(String notiTitle) {
            this.notiTitle = notiTitle;
        }

        public String getNotiMessage() {
            return notiMessage;
        }

        public void setNotiMessage(String notiMessage) {
            this.notiMessage = notiMessage;
        }

        public String getNotiAppointmentId() {
            return notiAppointmentId;
        }

        public void setNotiAppointmentId(String notiAppointmentId) {
            this.notiAppointmentId = notiAppointmentId;
        }

        public String getNotiBranchId() {
            return notiBranchId;
        }

        public void setNotiBranchId(String notiBranchId) {
            this.notiBranchId = notiBranchId;
        }

        public String getNotiEndUserId() {
            return notiEndUserId;
        }

        public void setNotiEndUserId(String notiEndUserId) {
            this.notiEndUserId = notiEndUserId;
        }

        public String getNotiCpUserId() {
            return notiCpUserId;
        }

        public void setNotiCpUserId(String notiCpUserId) {
            this.notiCpUserId = notiCpUserId;
        }

        public String getNotiFrom() {
            return notiFrom;
        }

        public void setNotiFrom(String notiFrom) {
            this.notiFrom = notiFrom;
        }

        public String getNotiTo() {
            return notiTo;
        }

        public void setNotiTo(String notiTo) {
            this.notiTo = notiTo;
        }

        public String getNotiType() {
            return notiType;
        }

        public void setNotiType(String notiType) {
            this.notiType = notiType;
        }

        public String getNotiReadStatus() {
            return notiReadStatus;
        }

        public void setNotiReadStatus(String notiReadStatus) {
            this.notiReadStatus = notiReadStatus;
        }

        public String getNotiCreateDate() {
            return notiCreateDate;
        }

        public void setNotiCreateDate(String notiCreateDate) {
            this.notiCreateDate = notiCreateDate;
        }

        public String getAptId() {
            return aptId;
        }

        public void setAptId(String aptId) {
            this.aptId = aptId;
        }

        public String getAptCpBranch() {
            return aptCpBranch;
        }

        public void setAptCpBranch(String aptCpBranch) {
            this.aptCpBranch = aptCpBranch;
        }

        public String getAptUserId() {
            return aptUserId;
        }

        public void setAptUserId(String aptUserId) {
            this.aptUserId = aptUserId;
        }

        public String getAptDate() {
            return aptDate;
        }

        public void setAptDate(String aptDate) {
            this.aptDate = aptDate;
        }

        public String getAptTime() {
            return aptTime;
        }

        public void setAptTime(String aptTime) {
            this.aptTime = aptTime;
        }

        public String getAptSubtotal() {
            return aptSubtotal;
        }

        public void setAptSubtotal(String aptSubtotal) {
            this.aptSubtotal = aptSubtotal;
        }

        public String getAptGst() {
            return aptGst;
        }

        public void setAptGst(String aptGst) {
            this.aptGst = aptGst;
        }

        public String getAptAmount() {
            return aptAmount;
        }

        public void setAptAmount(String aptAmount) {
            this.aptAmount = aptAmount;
        }

        public String getAptPaymentStatus() {
            return aptPaymentStatus;
        }

        public void setAptPaymentStatus(String aptPaymentStatus) {
            this.aptPaymentStatus = aptPaymentStatus;
        }

        public String getAptPaymentType() {
            return aptPaymentType;
        }

        public void setAptPaymentType(String aptPaymentType) {
            this.aptPaymentType = aptPaymentType;
        }

        public String getAptPaymentDate() {
            return aptPaymentDate;
        }

        public void setAptPaymentDate(String aptPaymentDate) {
            this.aptPaymentDate = aptPaymentDate;
        }

        public String getAptResheduleBy() {
            return aptResheduleBy;
        }

        public void setAptResheduleBy(String aptResheduleBy) {
            this.aptResheduleBy = aptResheduleBy;
        }

        public String getAptResheduleDate() {
            return aptResheduleDate;
        }

        public void setAptResheduleDate(String aptResheduleDate) {
            this.aptResheduleDate = aptResheduleDate;
        }

        public String getAptStatus() {
            return aptStatus;
        }

        public void setAptStatus(String aptStatus) {
            this.aptStatus = aptStatus;
        }

        public String getAptRejectReason() {
            return aptRejectReason;
        }

        public void setAptRejectReason(String aptRejectReason) {
            this.aptRejectReason = aptRejectReason;
        }

        public String getAptCreateDate() {
            return aptCreateDate;
        }

        public void setAptCreateDate(String aptCreateDate) {
            this.aptCreateDate = aptCreateDate;
        }

        public String getAptCreateBy() {
            return aptCreateBy;
        }

        public void setAptCreateBy(String aptCreateBy) {
            this.aptCreateBy = aptCreateBy;
        }

        public String getAptUpdateDate() {
            return aptUpdateDate;
        }

        public void setAptUpdateDate(String aptUpdateDate) {
            this.aptUpdateDate = aptUpdateDate;
        }

        public String getAptUpdateBy() {
            return aptUpdateBy;
        }

        public void setAptUpdateBy(String aptUpdateBy) {
            this.aptUpdateBy = aptUpdateBy;
        }
    }
}