package com.prometteur.cpapp.beans;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileBean implements Serializable
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
    private final static long serialVersionUID = 2409464694765294283L;

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


    public class Result implements Serializable
    {

        @SerializedName("bran_id")
        @Expose
        private String branId;
        @SerializedName("bran_name")
        @Expose
        private String branName;
        @SerializedName("bran_addr")
        @Expose
        private String branAddr;
        @SerializedName("bran_estblish_year")
        @Expose
        private String branEstblishYear;
        @SerializedName("bran_working_hrs")
        @Expose
        private String branWorkingHrs;
        @SerializedName("bran_off_day")
        @Expose
        private String branOffDay;
        @SerializedName("bran_holiday_from")
        @Expose
        private String branHolidayFrom;
        @SerializedName("bran_holiday_to")
        @Expose
        private String branHolidayTo;
        @SerializedName("bran_discription")
        @Expose
        private String branDiscription;
        @SerializedName("bran_img")
        @Expose
        private String branImg;
        @SerializedName("bran_gallary_img")
        @Expose
        private String branGallaryImg;
        @SerializedName("bran_open_status")//1=open 0=closed
        @Expose
        private String branOpenStatus;
        @SerializedName("bran_closed")//0=open 1=closed
        @Expose
        private String branClosed;
        @SerializedName("bran_city")
        @Expose
        private String branCity;
        @SerializedName("bran_state")
        @Expose
        private String branState;
        @SerializedName("bran_pin_code")
        @Expose
        private String branPinCode;
        @SerializedName("branch_rating")
        @Expose
        private String branchRating;
        @SerializedName("review_count")
        @Expose
        private String reviewCount;
        @SerializedName("topservices")
        @Expose
        private List<Topservice> topservices = null;
        @SerializedName("operators")
        @Expose
        private List<Operator> operators = null;
        @SerializedName("reviews")
        @Expose
        private List<Review> reviews = null;
        @SerializedName("services_name")
        @Expose
        private ServicesName servicesName;
        private final static long serialVersionUID = -6115828562888865232L;

        public String getBranId() {
            return branId;
        }

        public void setBranId(String branId) {
            this.branId = branId;
        }

        public String getBranName() {
            return branName;
        }

        public void setBranName(String branName) {
            this.branName = branName;
        }

        public String getBranAddr() {
            return branAddr;
        }

        public void setBranAddr(String branAddr) {
            this.branAddr = branAddr;
        }

        public String getBranEstblishYear() {
            return branEstblishYear;
        }

        public void setBranEstblishYear(String branEstblishYear) {
            this.branEstblishYear = branEstblishYear;
        }

        public String getBranWorkingHrs() {
            return branWorkingHrs;
        }

        public void setBranWorkingHrs(String branWorkingHrs) {
            this.branWorkingHrs = branWorkingHrs;
        }

        public String getBranOffDay() {
            return branOffDay;
        }

        public void setBranOffDay(String branOffDay) {
            this.branOffDay = branOffDay;
        }

        public String getBranHolidayFrom() {
            return branHolidayFrom;
        }

        public void setBranHolidayFrom(String branHolidayFrom) {
            this.branHolidayFrom = branHolidayFrom;
        }

        public String getBranHolidayTo() {
            return branHolidayTo;
        }

        public void setBranHolidayTo(String branHolidayTo) {
            this.branHolidayTo = branHolidayTo;
        }

        public String getBranDiscription() {
            return branDiscription;
        }

        public void setBranDiscription(String branDiscription) {
            this.branDiscription = branDiscription;
        }

        public String getBranImg() {
            return branImg;
        }

        public void setBranImg(String branImg) {
            this.branImg = branImg;
        }

        public String getBranGallaryImg() {
            return branGallaryImg;
        }

        public void setBranGallaryImg(String branGallaryImg) {
            this.branGallaryImg = branGallaryImg;
        }

        public String getBranOpenStatus() {
            return branOpenStatus;
        }

        public void setBranOpenStatus(String branOpenStatus) {
            this.branOpenStatus = branOpenStatus;
        }

        public String getBranCity() {
            return branCity;
        }

        public void setBranCity(String branCity) {
            this.branCity = branCity;
        }

        public String getBranState() {
            return branState;
        }

        public void setBranState(String branState) {
            this.branState = branState;
        }

        public String getBranPinCode() {
            return branPinCode;
        }

        public void setBranPinCode(String branPinCode) {
            this.branPinCode = branPinCode;
        }

        public String getBranchRating() {
            return branchRating;
        }

        public void setBranchRating(String branchRating) {
            this.branchRating = branchRating;
        }

        public String getReviewCount() {
            return reviewCount;
        }

        public void setReviewCount(String reviewCount) {
            this.reviewCount = reviewCount;
        }

        public List<Topservice> getTopservices() {
            return topservices;
        }

        public void setTopservices(List<Topservice> topservices) {
            this.topservices = topservices;
        }

        public List<Operator> getOperators() {
            return operators;
        }

        public void setOperators(List<Operator> operators) {
            this.operators = operators;
        }

        public List<Review> getReviews() {
            return reviews;
        }

        public void setReviews(List<Review> reviews) {
            this.reviews = reviews;
        }

        public ServicesName getServicesName() {
            return servicesName;
        }

        public void setServicesName(ServicesName servicesName) {
            this.servicesName = servicesName;
        }

        public String getBranClosed() {
            return branClosed;
        }

        public void setBranClosed(String branClosed) {
            this.branClosed = branClosed;
        }
    }



    public class ServicesName implements Serializable
    {

        @SerializedName("1")
        @Expose
        private String _1;
        @SerializedName("2")
        @Expose
        private String _2;
        @SerializedName("3")
        @Expose
        private String _3;
        @SerializedName("4")
        @Expose
        private String _4;
        @SerializedName("5")
        @Expose
        private String _5;
        private final static long serialVersionUID = -4952808921081076064L;

        public String get1() {
            return _1;
        }

        public void set1(String _1) {
            this._1 = _1;
        }

        public String get2() {
            return _2;
        }

        public void set2(String _2) {
            this._2 = _2;
        }

        public String get3() {
            return _3;
        }

        public void set3(String _3) {
            this._3 = _3;
        }

        public String get4() {
            return _4;
        }

        public void set4(String _4) {
            this._4 = _4;
        }

        public String get5() {
            return _5;
        }

        public void set5(String _5) {
            this._5 = _5;
        }

    }

    public class Review implements Serializable
    {

        @SerializedName("rev_id")
        @Expose
        private String revId;
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
        @SerializedName("rev_review")
        @Expose
        private String revReview;
        @SerializedName("rev_rating")
        @Expose
        private String revRating;
        @SerializedName("rev_create_date")
        @Expose
        private String revCreateDate;
        private final static long serialVersionUID = 7025740488350187220L;

        public String getRevId() {
            return revId;
        }

        public void setRevId(String revId) {
            this.revId = revId;
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

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public String getUserGender() {
            return userGender;
        }

        public void setUserGender(String userGender) {
            this.userGender = userGender;
        }

        public String getRevReview() {
            return revReview;
        }

        public void setRevReview(String revReview) {
            this.revReview = revReview;
        }

        public String getRevRating() {
            return revRating;
        }

        public void setRevRating(String revRating) {
            this.revRating = revRating;
        }

        public String getRevCreateDate() {
            return revCreateDate;
        }

        public void setRevCreateDate(String revCreateDate) {
            this.revCreateDate = revCreateDate;
        }

    }

    public class Service implements Serializable
    {

        @SerializedName("srvc_id")
        @Expose
        private String srvcId;
        @SerializedName("srvc_name")
        @Expose
        private String srvcName;
        @SerializedName("brnd_name")
        @Expose
        private String brndName;
        @SerializedName("srvc_estimate_time")
        @Expose
        private String srvcEstimateTime;
        @SerializedName("srvc_price")
        @Expose
        private String srvcPrice;
        @SerializedName("srvc_discount_price")
        @Expose
        private String srvcDiscountPrice;
        private final static long serialVersionUID = -6092730675593290711L;

        public String getSrvcId() {
            return srvcId;
        }

        public void setSrvcId(String srvcId) {
            this.srvcId = srvcId;
        }

        public String getSrvcName() {
            return srvcName;
        }

        public void setSrvcName(String srvcName) {
            this.srvcName = srvcName;
        }

        public String getBrndName() {
            return brndName;
        }

        public void setBrndName(String brndName) {
            this.brndName = brndName;
        }

        public String getSrvcEstimateTime() {
            return srvcEstimateTime;
        }

        public void setSrvcEstimateTime(String srvcEstimateTime) {
            this.srvcEstimateTime = srvcEstimateTime;
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

    }

    public class Topservice implements Serializable
    {

        @SerializedName("srvc_category")
        @Expose
        private String srvcCategory;
        @SerializedName("types")
        @Expose
        private String types;
        @SerializedName("services")
        @Expose
        private List<Service> services = null;
        private final static long serialVersionUID = 8609488235230864475L;

        public String getSrvcCategory() {
            return srvcCategory;
        }

        public void setSrvcCategory(String srvcCategory) {
            this.srvcCategory = srvcCategory;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public List<Service> getServices() {
            return services;
        }

        public void setServices(List<Service> services) {
            this.services = services;
        }

    }


    public class Operator implements Serializable
    {

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
        @SerializedName("user_speciality")
        @Expose
        private String userSpeciality;
        private final static long serialVersionUID = -7489721428415456175L;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

    }
}