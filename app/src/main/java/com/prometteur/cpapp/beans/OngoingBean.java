package com.prometteur.cpapp.beans;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OngoingBean implements Serializable
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
    private final static long serialVersionUID = -1400580427481239150L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public OngoingBean withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OngoingBean withMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public OngoingBean withResult(List<Result> result) {
        this.result = result;
        return this;
    }

    public Integer getAptRequest() {
        return aptRequest;
    }

    public void setAptRequest(Integer aptRequest) {
        this.aptRequest = aptRequest;
    }

    public OngoingBean withAptRequest(Integer aptRequest) {
        this.aptRequest = aptRequest;
        return this;
    }


    public static class Result implements Serializable
    {

        public Result()
        {

        }
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
        @SerializedName("apt_transaction_id")
        @Expose
        private String aptTransactionId;
        @SerializedName("apt_order_id")
        @Expose
        private String aptOrderId;
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
        @SerializedName("apt_start_otp")
        @Expose
        private String aptStartOtp;
        @SerializedName("apt_end_otp")
        @Expose
        private String aptEndOtp;
        @SerializedName("apt_coupon_id")
        @Expose
        private String aptCouponId;
        @SerializedName("apt_coupon_price")
        @Expose
        private String aptCouponPrice;
        @SerializedName("apt_accept_time")
        @Expose
        private String aptAcceptTime;
        @SerializedName("apt_penality")
        @Expose
        private String aptPenality;
        @SerializedName("apt_service_charges")
        @Expose
        private String aptServiceCharges;
        @SerializedName("apt_redeem_point")
        @Expose
        private String aptRedeemPoint;
        @SerializedName("apt_discount")
        @Expose
        private String aptDiscount;
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
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("user_f_name")
        @Expose
        private String userFName;
        @SerializedName("user_l_name")
        @Expose
        private String userLName;
        @SerializedName("user_img")
        @Expose
        private String userImg;
        @SerializedName("user_gender")
        @Expose
        private String userGender;
        @SerializedName("services")
        @Expose
        private List<Service> services = null;
        @SerializedName("combo_services")
        @Expose
        private List<ComboService> comboServices = null;
        @SerializedName("promotional_services")
        @Expose
        private List<PromotionalService> promotionalServices = null;
        @SerializedName("user_rating")
        @Expose
        private String userRating;
        @SerializedName("review_count")
        @Expose
        private String reviewCount;
        @SerializedName("apt_noshow_time")
        @Expose
        private String aptNoshowTime;
        private final static long serialVersionUID = 7941232599710302475L;

        public String getAptId() {
            return aptId;
        }

        public void setAptId(String aptId) {
            this.aptId = aptId;
        }

        public Result withAptId(String aptId) {
            this.aptId = aptId;
            return this;
        }

        public String getAptCpBranch() {
            return aptCpBranch;
        }

        public void setAptCpBranch(String aptCpBranch) {
            this.aptCpBranch = aptCpBranch;
        }

        public Result withAptCpBranch(String aptCpBranch) {
            this.aptCpBranch = aptCpBranch;
            return this;
        }

        public String getAptUserId() {
            return aptUserId;
        }

        public void setAptUserId(String aptUserId) {
            this.aptUserId = aptUserId;
        }

        public Result withAptUserId(String aptUserId) {
            this.aptUserId = aptUserId;
            return this;
        }

        public String getAptDate() {
            return aptDate;
        }

        public void setAptDate(String aptDate) {
            this.aptDate = aptDate;
        }

        public Result withAptDate(String aptDate) {
            this.aptDate = aptDate;
            return this;
        }

        public String getAptTime() {
            return aptTime;
        }

        public void setAptTime(String aptTime) {
            this.aptTime = aptTime;
        }

        public Result withAptTime(String aptTime) {
            this.aptTime = aptTime;
            return this;
        }

        public String getAptSubtotal() {
            return aptSubtotal;
        }

        public void setAptSubtotal(String aptSubtotal) {
            this.aptSubtotal = aptSubtotal;
        }

        public Result withAptSubtotal(String aptSubtotal) {
            this.aptSubtotal = aptSubtotal;
            return this;
        }

        public String getAptGst() {
            return aptGst;
        }

        public void setAptGst(String aptGst) {
            this.aptGst = aptGst;
        }

        public Result withAptGst(String aptGst) {
            this.aptGst = aptGst;
            return this;
        }

        public String getAptAmount() {
            return aptAmount;
        }

        public void setAptAmount(String aptAmount) {
            this.aptAmount = aptAmount;
        }

        public Result withAptAmount(String aptAmount) {
            this.aptAmount = aptAmount;
            return this;
        }

        public String getAptPaymentStatus() {
            return aptPaymentStatus;
        }

        public void setAptPaymentStatus(String aptPaymentStatus) {
            this.aptPaymentStatus = aptPaymentStatus;
        }

        public Result withAptPaymentStatus(String aptPaymentStatus) {
            this.aptPaymentStatus = aptPaymentStatus;
            return this;
        }

        public String getAptPaymentType() {
            return aptPaymentType;
        }

        public void setAptPaymentType(String aptPaymentType) {
            this.aptPaymentType = aptPaymentType;
        }

        public Result withAptPaymentType(String aptPaymentType) {
            this.aptPaymentType = aptPaymentType;
            return this;
        }

        public String getAptPaymentDate() {
            return aptPaymentDate;
        }

        public void setAptPaymentDate(String aptPaymentDate) {
            this.aptPaymentDate = aptPaymentDate;
        }

        public Result withAptPaymentDate(String aptPaymentDate) {
            this.aptPaymentDate = aptPaymentDate;
            return this;
        }

        public String getAptTransactionId() {
            return aptTransactionId;
        }

        public void setAptTransactionId(String aptTransactionId) {
            this.aptTransactionId = aptTransactionId;
        }

        public Result withAptTransactionId(String aptTransactionId) {
            this.aptTransactionId = aptTransactionId;
            return this;
        }

        public String getAptOrderId() {
            return aptOrderId;
        }

        public void setAptOrderId(String aptOrderId) {
            this.aptOrderId = aptOrderId;
        }

        public Result withAptOrderId(String aptOrderId) {
            this.aptOrderId = aptOrderId;
            return this;
        }

        public String getAptResheduleBy() {
            return aptResheduleBy;
        }

        public void setAptResheduleBy(String aptResheduleBy) {
            this.aptResheduleBy = aptResheduleBy;
        }

        public Result withAptResheduleBy(String aptResheduleBy) {
            this.aptResheduleBy = aptResheduleBy;
            return this;
        }

        public String getAptResheduleDate() {
            return aptResheduleDate;
        }

        public void setAptResheduleDate(String aptResheduleDate) {
            this.aptResheduleDate = aptResheduleDate;
        }

        public Result withAptResheduleDate(String aptResheduleDate) {
            this.aptResheduleDate = aptResheduleDate;
            return this;
        }

        public String getAptStatus() {
            return aptStatus;
        }

        public void setAptStatus(String aptStatus) {
            this.aptStatus = aptStatus;
        }

        public Result withAptStatus(String aptStatus) {
            this.aptStatus = aptStatus;
            return this;
        }

        public String getAptRejectReason() {
            return aptRejectReason;
        }

        public void setAptRejectReason(String aptRejectReason) {
            this.aptRejectReason = aptRejectReason;
        }

        public Result withAptRejectReason(String aptRejectReason) {
            this.aptRejectReason = aptRejectReason;
            return this;
        }

        public String getAptStartOtp() {
            return aptStartOtp;
        }

        public void setAptStartOtp(String aptStartOtp) {
            this.aptStartOtp = aptStartOtp;
        }

        public Result withAptStartOtp(String aptStartOtp) {
            this.aptStartOtp = aptStartOtp;
            return this;
        }

        public String getAptEndOtp() {
            return aptEndOtp;
        }

        public void setAptEndOtp(String aptEndOtp) {
            this.aptEndOtp = aptEndOtp;
        }

        public Result withAptEndOtp(String aptEndOtp) {
            this.aptEndOtp = aptEndOtp;
            return this;
        }

        public String getAptCouponId() {
            return aptCouponId;
        }

        public void setAptCouponId(String aptCouponId) {
            this.aptCouponId = aptCouponId;
        }

        public Result withAptCouponId(String aptCouponId) {
            this.aptCouponId = aptCouponId;
            return this;
        }

        public String getAptCouponPrice() {
            return aptCouponPrice;
        }

        public void setAptCouponPrice(String aptCouponPrice) {
            this.aptCouponPrice = aptCouponPrice;
        }

        public Result withAptCouponPrice(String aptCouponPrice) {
            this.aptCouponPrice = aptCouponPrice;
            return this;
        }

        public String getAptAcceptTime() {
            return aptAcceptTime;
        }

        public void setAptAcceptTime(String aptAcceptTime) {
            this.aptAcceptTime = aptAcceptTime;
        }

        public Result withAptAcceptTime(String aptAcceptTime) {
            this.aptAcceptTime = aptAcceptTime;
            return this;
        }

        public String getAptPenality() {
            return aptPenality;
        }

        public void setAptPenality(String aptPenality) {
            this.aptPenality = aptPenality;
        }

        public Result withAptPenality(String aptPenality) {
            this.aptPenality = aptPenality;
            return this;
        }

        public String getAptServiceCharges() {
            return aptServiceCharges;
        }

        public void setAptServiceCharges(String aptServiceCharges) {
            this.aptServiceCharges = aptServiceCharges;
        }

        public Result withAptServiceCharges(String aptServiceCharges) {
            this.aptServiceCharges = aptServiceCharges;
            return this;
        }

        public String getAptRedeemPoint() {
            return aptRedeemPoint;
        }

        public void setAptRedeemPoint(String aptRedeemPoint) {
            this.aptRedeemPoint = aptRedeemPoint;
        }

        public Result withAptRedeemPoint(String aptRedeemPoint) {
            this.aptRedeemPoint = aptRedeemPoint;
            return this;
        }

        public String getAptDiscount() {
            return aptDiscount;
        }

        public void setAptDiscount(String aptDiscount) {
            this.aptDiscount = aptDiscount;
        }

        public Result withAptDiscount(String aptDiscount) {
            this.aptDiscount = aptDiscount;
            return this;
        }

        public String getAptCreateDate() {
            return aptCreateDate;
        }

        public void setAptCreateDate(String aptCreateDate) {
            this.aptCreateDate = aptCreateDate;
        }

        public Result withAptCreateDate(String aptCreateDate) {
            this.aptCreateDate = aptCreateDate;
            return this;
        }

        public String getAptCreateBy() {
            return aptCreateBy;
        }

        public void setAptCreateBy(String aptCreateBy) {
            this.aptCreateBy = aptCreateBy;
        }

        public Result withAptCreateBy(String aptCreateBy) {
            this.aptCreateBy = aptCreateBy;
            return this;
        }

        public String getAptUpdateDate() {
            return aptUpdateDate;
        }

        public void setAptUpdateDate(String aptUpdateDate) {
            this.aptUpdateDate = aptUpdateDate;
        }

        public Result withAptUpdateDate(String aptUpdateDate) {
            this.aptUpdateDate = aptUpdateDate;
            return this;
        }

        public String getAptUpdateBy() {
            return aptUpdateBy;
        }

        public void setAptUpdateBy(String aptUpdateBy) {
            this.aptUpdateBy = aptUpdateBy;
        }

        public Result withAptUpdateBy(String aptUpdateBy) {
            this.aptUpdateBy = aptUpdateBy;
            return this;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Result withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public String getUserFName() {
            return userFName;
        }

        public void setUserFName(String userFName) {
            this.userFName = userFName;
        }

        public Result withUserFName(String userFName) {
            this.userFName = userFName;
            return this;
        }

        public String getUserLName() {
            return userLName;
        }

        public void setUserLName(String userLName) {
            this.userLName = userLName;
        }

        public Result withUserLName(String userLName) {
            this.userLName = userLName;
            return this;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public Result withUserImg(String userImg) {
            this.userImg = userImg;
            return this;
        }

        public String getUserGender() {
            return userGender;
        }

        public void setUserGender(String userGender) {
            this.userGender = userGender;
        }

        public Result withUserGender(String userGender) {
            this.userGender = userGender;
            return this;
        }

        public List<Service> getServices() {
            return services;
        }

        public void setServices(List<Service> services) {
            this.services = services;
        }

        public Result withServices(List<Service> services) {
            this.services = services;
            return this;
        }

        public List<ComboService> getComboServices() {
            return comboServices;
        }

        public void setComboServices(List<ComboService> comboServices) {
            this.comboServices = comboServices;
        }

        public Result withComboServices(List<ComboService> comboServices) {
            this.comboServices = comboServices;
            return this;
        }

        public List<PromotionalService> getPromotionalServices() {
            return promotionalServices;
        }

        public void setPromotionalServices(List<PromotionalService> promotionalServices) {
            this.promotionalServices = promotionalServices;
        }

        public Result withPromotionalServices(List<PromotionalService> promotionalServices) {
            this.promotionalServices = promotionalServices;
            return this;
        }

        public String getUserRating() {
            return userRating;
        }

        public void setUserRating(String userRating) {
            this.userRating = userRating;
        }

        public Result withUserRating(String userRating) {
            this.userRating = userRating;
            return this;
        }

        public String getReviewCount() {
            return reviewCount;
        }

        public void setReviewCount(String reviewCount) {
            this.reviewCount = reviewCount;
        }

        public Result withReviewCount(String reviewCount) {
            this.reviewCount = reviewCount;
            return this;
        }

        public String getAptNoshowTime() {
            return aptNoshowTime;
        }

        public void setAptNoshowTime(String aptNoshowTime) {
            this.aptNoshowTime = aptNoshowTime;
        }
    }
    public static class Service implements Serializable
    {

        public Service()
        {}
        @SerializedName("srvc_name")
        @Expose
        private String srvcName;
        @SerializedName("brnd_name")
        @Expose
        private Object brndName;
        @SerializedName("srvc_price")
        @Expose
        private String srvcPrice;
        @SerializedName("user_f_name")
        @Expose
        private String userFName;
        @SerializedName("user_l_name")
        @Expose
        private String userLName;
        @SerializedName("srvc_discount_price")
        @Expose
        private String srvcDiscountPrice;
        private final static long serialVersionUID = -5391597087434390101L;

        public String getSrvcName() {
            return srvcName;
        }

        public void setSrvcName(String srvcName) {
            this.srvcName = srvcName;
        }

        public Object getBrndName() {
            return brndName;
        }

        public void setBrndName(Object brndName) {
            this.brndName = brndName;
        }

        public String getSrvcPrice() {
            return srvcPrice;
        }

        public void setSrvcPrice(String srvcPrice) {
            this.srvcPrice = srvcPrice;
        }

        public String getSrvcDiscountPrice() {
            return srvcDiscountPrice;
        }

        public void setSrvcDiscountPrice(String srvcDiscountPrice) {
            this.srvcDiscountPrice = srvcDiscountPrice;
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

    }
    public class Service_ implements Serializable
    {

        @SerializedName("srvc_name")
        @Expose
        private String srvcName;
        @SerializedName("brnd_name")
        @Expose
        private Object brndName;
        @SerializedName("srvc_price")
        @Expose
        private String srvcPrice;
        @SerializedName("user_f_name")
        @Expose
        private Object userFName;
        @SerializedName("user_l_name")
        @Expose
        private Object userLName;
        private final static long serialVersionUID = -4013309234307741700L;

        public String getSrvcName() {
            return srvcName;
        }

        public void setSrvcName(String srvcName) {
            this.srvcName = srvcName;
        }

        public Service_ withSrvcName(String srvcName) {
            this.srvcName = srvcName;
            return this;
        }

        public Object getBrndName() {
            return brndName;
        }

        public void setBrndName(Object brndName) {
            this.brndName = brndName;
        }

        public Service_ withBrndName(Object brndName) {
            this.brndName = brndName;
            return this;
        }

        public String getSrvcPrice() {
            return srvcPrice;
        }

        public void setSrvcPrice(String srvcPrice) {
            this.srvcPrice = srvcPrice;
        }

        public Service_ withSrvcPrice(String srvcPrice) {
            this.srvcPrice = srvcPrice;
            return this;
        }

        public Object getUserFName() {
            return userFName;
        }

        public void setUserFName(Object userFName) {
            this.userFName = userFName;
        }

        public Service_ withUserFName(Object userFName) {
            this.userFName = userFName;
            return this;
        }

        public Object getUserLName() {
            return userLName;
        }

        public void setUserLName(Object userLName) {
            this.userLName = userLName;
        }

        public Service_ withUserLName(Object userLName) {
            this.userLName = userLName;
            return this;
        }

    }
    public class ComboService implements Serializable
    {

        @SerializedName("aptser_id")
        @Expose
        private String aptserId;
        @SerializedName("offer_id")
        @Expose
        private String offerId;
        @SerializedName("offer_name")
        @Expose
        private String offerName;
        @SerializedName("offer_price")
        @Expose
        private String offerPrice;
        @SerializedName("offer_discount")
        @Expose
        private String offerDiscount;
        @SerializedName("offer_discount_price")
        @Expose
        private String offerDiscountPrice;
        @SerializedName("services")
        @Expose
        private List<Service> services = null;
        private final static long serialVersionUID = -964034534512421453L;

        public String getAptserId() {
            return aptserId;
        }

        public void setAptserId(String aptserId) {
            this.aptserId = aptserId;
        }

        public ComboService withAptserId(String aptserId) {
            this.aptserId = aptserId;
            return this;
        }

        public String getOfferId() {
            return offerId;
        }

        public void setOfferId(String offerId) {
            this.offerId = offerId;
        }

        public ComboService withOfferId(String offerId) {
            this.offerId = offerId;
            return this;
        }

        public String getOfferName() {
            return offerName;
        }

        public void setOfferName(String offerName) {
            this.offerName = offerName;
        }

        public ComboService withOfferName(String offerName) {
            this.offerName = offerName;
            return this;
        }

        public String getOfferPrice() {
            return offerPrice;
        }

        public void setOfferPrice(String offerPrice) {
            this.offerPrice = offerPrice;
        }

        public ComboService withOfferPrice(String offerPrice) {
            this.offerPrice = offerPrice;
            return this;
        }

        public String getOfferDiscount() {
            return offerDiscount;
        }

        public void setOfferDiscount(String offerDiscount) {
            this.offerDiscount = offerDiscount;
        }

        public ComboService withOfferDiscount(String offerDiscount) {
            this.offerDiscount = offerDiscount;
            return this;
        }

        public String getOfferDiscountPrice() {
            return offerDiscountPrice;
        }

        public void setOfferDiscountPrice(String offerDiscountPrice) {
            this.offerDiscountPrice = offerDiscountPrice;
        }

        public ComboService withOfferDiscountPrice(String offerDiscountPrice) {
            this.offerDiscountPrice = offerDiscountPrice;
            return this;
        }

        public List<Service> getServices() {
            return services;
        }

        public void setServices(List<Service> services) {
            this.services = services;
        }

        public ComboService withServices(List<Service> services) {
            this.services = services;
            return this;
        }

    }

    public class PromotionalService implements Serializable
    {

        @SerializedName("aptser_id")
        @Expose
        private String aptserId;
        @SerializedName("prooffer_id")
        @Expose
        private String proofferId;
        @SerializedName("prooffer_name")
        @Expose
        private String proofferName;
        @SerializedName("prooffer_price")
        @Expose
        private String proofferPrice;
        @SerializedName("prooffer_discount")
        @Expose
        private String proofferDiscount;
        @SerializedName("prooffer_discount_price")
        @Expose
        private String proofferDiscountPrice;
        @SerializedName("services")
        @Expose
        private List<Service_> services = null;
        private final static long serialVersionUID = -2681017873435389924L;

        public String getAptserId() {
            return aptserId;
        }

        public void setAptserId(String aptserId) {
            this.aptserId = aptserId;
        }

        public PromotionalService withAptserId(String aptserId) {
            this.aptserId = aptserId;
            return this;
        }

        public String getProofferId() {
            return proofferId;
        }

        public void setProofferId(String proofferId) {
            this.proofferId = proofferId;
        }

        public PromotionalService withProofferId(String proofferId) {
            this.proofferId = proofferId;
            return this;
        }

        public String getProofferName() {
            return proofferName;
        }

        public void setProofferName(String proofferName) {
            this.proofferName = proofferName;
        }

        public PromotionalService withProofferName(String proofferName) {
            this.proofferName = proofferName;
            return this;
        }

        public String getProofferPrice() {
            return proofferPrice;
        }

        public void setProofferPrice(String proofferPrice) {
            this.proofferPrice = proofferPrice;
        }

        public PromotionalService withProofferPrice(String proofferPrice) {
            this.proofferPrice = proofferPrice;
            return this;
        }

        public String getProofferDiscount() {
            return proofferDiscount;
        }

        public void setProofferDiscount(String proofferDiscount) {
            this.proofferDiscount = proofferDiscount;
        }

        public PromotionalService withProofferDiscount(String proofferDiscount) {
            this.proofferDiscount = proofferDiscount;
            return this;
        }

        public String getProofferDiscountPrice() {
            return proofferDiscountPrice;
        }

        public void setProofferDiscountPrice(String proofferDiscountPrice) {
            this.proofferDiscountPrice = proofferDiscountPrice;
        }

        public PromotionalService withProofferDiscountPrice(String proofferDiscountPrice) {
            this.proofferDiscountPrice = proofferDiscountPrice;
            return this;
        }

        public List<Service_> getServices() {
            return services;
        }

        public void setServices(List<Service_> services) {
            this.services = services;
        }

        public PromotionalService withServices(List<Service_> services) {
            this.services = services;
            return this;
        }

    }
}