package com.prometteur.cpapp.models.onboarding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class SurveyDetail //extends RealmObject
 implements Serializable
    {


        public SurveyDetail()
        {

        }
        @SerializedName("surv_local_id") //for local data insert
        @Expose
//        @PrimaryKey
        private int survLocalId;
        @SerializedName("surv_id")
        @Expose
        private String survId;
        @SerializedName("surv_user_id")
        @Expose
        private String survUserId;
        @SerializedName("surv_name")
        @Expose
        private String survName;
        @SerializedName("surv_addr")
        @Expose
        private String survAddr;
        @SerializedName("surv_estblish_year")
        @Expose
        private String survEstblishYear;
        @SerializedName("surv_lat")
        @Expose
        private String survLat;
        @SerializedName("surv_lon")
        @Expose
        private String survLon;
        @SerializedName("surv_start_time")
        @Expose
        private String survStartTime;
        @SerializedName("surv_end_time")
        @Expose
        private String survEndTime;
        @SerializedName("surv_slon_cat")
        @Expose
        private String survCatMain;
        /*@SerializedName("surv_slon_cat")
        @Expose
        private String survSlonCat;*/
        @SerializedName("surv_work_hrs")
        @Expose
        private String survWorkHrs;
        @SerializedName("surv_website")
        @Expose
        private String survWebsite;
        @SerializedName("surv_email")
        @Expose
        private String survEmail;
        @SerializedName("surv_facebook")
        @Expose
        private String survFacebook;
        @SerializedName("surv_instagram")
        @Expose
        private String survInstagram;
        @SerializedName("surv_sal_number")
        @Expose
        private String survSalNumber;
        @SerializedName("surv_gst")
        @Expose
        private String survGst;
        @SerializedName("surv_rate_of_gst")
        @Expose
        private String survRateOfGst;
        @SerializedName("surv_owner_name")
        @Expose
        private String survOwnerName;
        @SerializedName("surv_own_number")
        @Expose
        private String survOwnNumber;
        @SerializedName("surv_own_edu")
        @Expose
        private String survOwnEdu;
        @SerializedName("surv_man_name")
        @Expose
        private String survManName;
        @SerializedName("surv_man_number")
        @Expose
        private String survManNumber;
        @SerializedName("surv_no_hair")
        @Expose
        private String survNoHair;
        @SerializedName("surv_brand_used_hair")
        @Expose
        private String survBrandUsedHair;
        @SerializedName("surv_no_skin")
        @Expose
        private String survNoSkin;
        @SerializedName("surv_brand_used_skin")
        @Expose
        private String survBrandUsedSkin;
        @SerializedName("surv_no_nails")
        @Expose
        private String survNoNails;
        @SerializedName("surv_brand_used_nails")
        @Expose
        private String survBrandUsedNails;
        @SerializedName("surv_no_spa")
        @Expose
        private String survNoSpa;
        @SerializedName("surv_brand_used_spa")
        @Expose
        private String survBrandUsedSpa;
        @SerializedName("surv_no_makeup")
        @Expose
        private String survNoMakeup;
        @SerializedName("surv_brand_used_makeup")
        @Expose
        private String survBrandUsedMakeup;
        @SerializedName("surv_otr_service")
        @Expose
        private String survOtrService;
        @SerializedName("surv_otr_opt")
        @Expose
        private String survOtrOpt;
        @SerializedName("surv_otr_brand_used")
        @Expose
        private String survOtrBrandUsed;
        @SerializedName("surv_res_parking")
        @Expose
        private String survResParking;
        @SerializedName("surv_tv")
        @Expose
        private String survTv;
        @SerializedName("surv_free_wifi")
        @Expose
        private String survFreeWifi;
        @SerializedName("surv_ac")
        @Expose
        private String survAc;
        @SerializedName("surv_house_staf_count")
        @Expose
        private String survHouseStafCount;
        @SerializedName("surv_mgmt_staff_count")
        @Expose
        private String survMgmtStaffCount;
        @SerializedName("surv_hair_skin_chairs")
        @Expose
        private String survHairSkinChairs;
        @SerializedName("surv_img")
        @Expose
        private String survImg;
        @SerializedName("surv_create_date")
        @Expose
        private String survCreateDate;
        @SerializedName("surv_update_date")
        @Expose
        private String survUpdateDate;
        @SerializedName("surv_create_by")
        @Expose
        private String survCreateBy;
        @SerializedName("surv_update_by")
        @Expose
        private String survUpdateBy;
        @SerializedName("surv_status")
        @Expose
        private String survStatus;
        @SerializedName("surv_city")
        @Expose
        private String survCity;
        @SerializedName("surv_state")
        @Expose
        private String survState;
        @SerializedName("surv_pin_code")
        @Expose
        private String survPinCode;
        @SerializedName("surv_gst_no")
        @Expose
        private String survGstNo;
        @SerializedName("surv_certification")
        @Expose
        private String survCertification;
        private final static long serialVersionUID = 4632132073085798331L;

        public String getSurvId() {
            return survId;
        }

        public void setSurvId(String survId) {
            this.survId = survId;
        }

        public String getSurvUserId() {
            return survUserId;
        }

        public void setSurvUserId(String survUserId) {
            this.survUserId = survUserId;
        }

        public String getSurvName() {
            return survName;
        }

        public void setSurvName(String survName) {
            this.survName = survName;
        }

        public String getSurvAddr() {
            return survAddr;
        }

        public void setSurvAddr(String survAddr) {
            this.survAddr = survAddr;
        }

        public String getSurvEstblishYear() {
            return survEstblishYear;
        }

        public void setSurvEstblishYear(String survEstblishYear) {
            this.survEstblishYear = survEstblishYear;
        }

        public String getSurvLat() {
            return survLat;
        }

        public void setSurvLat(String survLat) {
            this.survLat = survLat;
        }

        public String getSurvLon() {
            return survLon;
        }

        public void setSurvLon(String survLon) {
            this.survLon = survLon;
        }

        public String getSurvStartTime() {
            return survStartTime;
        }

        public void setSurvStartTime(String survStartTime) {
            this.survStartTime = survStartTime;
        }

        public String getSurvEndTime() {
            return survEndTime;
        }

        public void setSurvEndTime(String survEndTime) {
            this.survEndTime = survEndTime;
        }

        public String getSurvCatMain() {
            return survCatMain;
        }

        public void setSurvCatMain(String survCatMain) {
            this.survCatMain = survCatMain;
        }

        public String getSurvWorkHrs() {
            return survWorkHrs;
        }

        public void setSurvWorkHrs(String survWorkHrs) {
            this.survWorkHrs = survWorkHrs;
        }

        public String getSurvWebsite() {
            return survWebsite;
        }

        public void setSurvWebsite(String survWebsite) {
            this.survWebsite = survWebsite;
        }

        public String getSurvEmail() {
            return survEmail;
        }

        public void setSurvEmail(String survEmail) {
            this.survEmail = survEmail;
        }

        public String getSurvFacebook() {
            return survFacebook;
        }

        public void setSurvFacebook(String survFacebook) {
            this.survFacebook = survFacebook;
        }

        public String getSurvInstagram() {
            return survInstagram;
        }

        public void setSurvInstagram(String survInstagram) {
            this.survInstagram = survInstagram;
        }

        public String getSurvSalNumber() {
            return survSalNumber;
        }

        public void setSurvSalNumber(String survSalNumber) {
            this.survSalNumber = survSalNumber;
        }

        public String getSurvGst() {
            return survGst;
        }

        public void setSurvGst(String survGst) {
            this.survGst = survGst;
        }

        public String getSurvRateOfGst() {
            return survRateOfGst;
        }

        public void setSurvRateOfGst(String survRateOfGst) {
            this.survRateOfGst = survRateOfGst;
        }

        public String getSurvOwnerName() {
            return survOwnerName;
        }

        public void setSurvOwnerName(String survOwnerName) {
            this.survOwnerName = survOwnerName;
        }

        public String getSurvOwnNumber() {
            return survOwnNumber;
        }

        public void setSurvOwnNumber(String survOwnNumber) {
            this.survOwnNumber = survOwnNumber;
        }

        public String getSurvOwnEdu() {
            return survOwnEdu;
        }

        public void setSurvOwnEdu(String survOwnEdu) {
            this.survOwnEdu = survOwnEdu;
        }

        public String getSurvManName() {
            return survManName;
        }

        public void setSurvManName(String survManName) {
            this.survManName = survManName;
        }

        public String getSurvManNumber() {
            return survManNumber;
        }

        public void setSurvManNumber(String survManNumber) {
            this.survManNumber = survManNumber;
        }

        public String getSurvNoHair() {
            return survNoHair;
        }

        public void setSurvNoHair(String survNoHair) {
            this.survNoHair = survNoHair;
        }

        public String getSurvBrandUsedHair() {
            return survBrandUsedHair;
        }

        public void setSurvBrandUsedHair(String survBrandUsedHair) {
            this.survBrandUsedHair = survBrandUsedHair;
        }

        public String getSurvNoSkin() {
            return survNoSkin;
        }

        public void setSurvNoSkin(String survNoSkin) {
            this.survNoSkin = survNoSkin;
        }

        public String getSurvBrandUsedSkin() {
            return survBrandUsedSkin;
        }

        public void setSurvBrandUsedSkin(String survBrandUsedSkin) {
            this.survBrandUsedSkin = survBrandUsedSkin;
        }

        public String getSurvNoNails() {
            return survNoNails;
        }

        public void setSurvNoNails(String survNoNails) {
            this.survNoNails = survNoNails;
        }

        public String getSurvBrandUsedNails() {
            return survBrandUsedNails;
        }

        public void setSurvBrandUsedNails(String survBrandUsedNails) {
            this.survBrandUsedNails = survBrandUsedNails;
        }

        public String getSurvNoSpa() {
            return survNoSpa;
        }

        public void setSurvNoSpa(String survNoSpa) {
            this.survNoSpa = survNoSpa;
        }

        public String getSurvBrandUsedSpa() {
            return survBrandUsedSpa;
        }

        public void setSurvBrandUsedSpa(String survBrandUsedSpa) {
            this.survBrandUsedSpa = survBrandUsedSpa;
        }

        public String getSurvNoMakeup() {
            return survNoMakeup;
        }

        public void setSurvNoMakeup(String survNoMakeup) {
            this.survNoMakeup = survNoMakeup;
        }

        public String getSurvBrandUsedMakeup() {
            return survBrandUsedMakeup;
        }

        public void setSurvBrandUsedMakeup(String survBrandUsedMakeup) {
            this.survBrandUsedMakeup = survBrandUsedMakeup;
        }

        public String getSurvOtrService() {
            return survOtrService;
        }

        public void setSurvOtrService(String survOtrService) {
            this.survOtrService = survOtrService;
        }

        public String getSurvOtrOpt() {
            return survOtrOpt;
        }

        public void setSurvOtrOpt(String survOtrOpt) {
            this.survOtrOpt = survOtrOpt;
        }

        public String getSurvOtrBrandUsed() {
            return survOtrBrandUsed;
        }

        public void setSurvOtrBrandUsed(String survOtrBrandUsed) {
            this.survOtrBrandUsed = survOtrBrandUsed;
        }

        public String getSurvResParking() {
            return survResParking;
        }

        public void setSurvResParking(String survResParking) {
            this.survResParking = survResParking;
        }

        public String getSurvTv() {
            return survTv;
        }

        public void setSurvTv(String survTv) {
            this.survTv = survTv;
        }

        public String getSurvFreeWifi() {
            return survFreeWifi;
        }

        public void setSurvFreeWifi(String survFreeWifi) {
            this.survFreeWifi = survFreeWifi;
        }

        public String getSurvAc() {
            return survAc;
        }

        public void setSurvAc(String survAc) {
            this.survAc = survAc;
        }

        public String getSurvHouseStafCount() {
            return survHouseStafCount;
        }

        public void setSurvHouseStafCount(String survHouseStafCount) {
            this.survHouseStafCount = survHouseStafCount;
        }

        public String getSurvMgmtStaffCount() {
            return survMgmtStaffCount;
        }

        public void setSurvMgmtStaffCount(String survMgmtStaffCount) {
            this.survMgmtStaffCount = survMgmtStaffCount;
        }

        public String getSurvHairSkinChairs() {
            return survHairSkinChairs;
        }

        public void setSurvHairSkinChairs(String survHairSkinChairs) {
            this.survHairSkinChairs = survHairSkinChairs;
        }

        public String getSurvImg() {
            return survImg;
        }

        public void setSurvImg(String survImg) {
            this.survImg = survImg;
        }

        public String getSurvCreateDate() {
            return survCreateDate;
        }

        public void setSurvCreateDate(String survCreateDate) {
            this.survCreateDate = survCreateDate;
        }

        public String getSurvUpdateDate() {
            return survUpdateDate;
        }

        public void setSurvUpdateDate(String survUpdateDate) {
            this.survUpdateDate = survUpdateDate;
        }

        public String getSurvCreateBy() {
            return survCreateBy;
        }

        public void setSurvCreateBy(String survCreateBy) {
            this.survCreateBy = survCreateBy;
        }

        public String getSurvUpdateBy() {
            return survUpdateBy;
        }

        public void setSurvUpdateBy(String survUpdateBy) {
            this.survUpdateBy = survUpdateBy;
        }

        public String getSurvStatus() {
            return survStatus;
        }

        public void setSurvStatus(String survStatus) {
            this.survStatus = survStatus;
        }

        public String getSurvCity() {
            return survCity;
        }

        public void setSurvCity(String survCity) {
            this.survCity = survCity;
        }

        public String getSurvState() {
            return survState;
        }

        public void setSurvState(String survState) {
            this.survState = survState;
        }

        public String getSurvPinCode() {
            return survPinCode;
        }

        public void setSurvPinCode(String survPinCode) {
            this.survPinCode = survPinCode;
        }

        public String getSurvGstNo() {
            return survGstNo;
        }

        public void setSurvGstNo(String survGstNo) {
            this.survGstNo = survGstNo;
        }

        public String getSurvCertification() {
            return survCertification;
        }

        public void setSurvCertification(String survCertification) {
            this.survCertification = survCertification;
        }

        public int getSurvLocalId() {
            return survLocalId;
        }

        public void setSurvLocalId(int survLocalId) {
            this.survLocalId = survLocalId;
        }

       /* public String getSurvSlonCat() {
            return survSlonCat;
        }

        public void setSurvSlonCat(String survSlonCat) {
            this.survSlonCat = survSlonCat;
        }*/

        @SerializedName("strEdtHairOtherBrand")
        @Expose
        private String strEdtHairOtherBrand;
        @SerializedName("strEdtSkinOtherBrand")
        @Expose
        private String strEdtSkinOtherBrand;
        @SerializedName("strEdtNailsOtherBrand")
        @Expose
        private String strEdtNailsOtherBrand;
        @SerializedName("strEdtSpaOtherBrand")
        @Expose
        private String strEdtSpaOtherBrand;
        @SerializedName("strEdtMakeupOtherBrand")
        @Expose
        private String strEdtMakeupOtherBrand;

        public String getStrEdtHairOtherBrand() {
            return strEdtHairOtherBrand;
        }

        public void setStrEdtHairOtherBrand(String strEdtHairOtherBrand) {
            this.strEdtHairOtherBrand = strEdtHairOtherBrand;
        }

        public String getStrEdtSkinOtherBrand() {
            return strEdtSkinOtherBrand;
        }

        public void setStrEdtSkinOtherBrand(String strEdtSkinOtherBrand) {
            this.strEdtSkinOtherBrand = strEdtSkinOtherBrand;
        }

        public String getStrEdtNailsOtherBrand() {
            return strEdtNailsOtherBrand;
        }

        public void setStrEdtNailsOtherBrand(String strEdtNailsOtherBrand) {
            this.strEdtNailsOtherBrand = strEdtNailsOtherBrand;
        }

        public String getStrEdtSpaOtherBrand() {
            return strEdtSpaOtherBrand;
        }

        public void setStrEdtSpaOtherBrand(String strEdtSpaOtherBrand) {
            this.strEdtSpaOtherBrand = strEdtSpaOtherBrand;
        }

        public String getStrEdtMakeupOtherBrand() {
            return strEdtMakeupOtherBrand;
        }

        public void setStrEdtMakeupOtherBrand(String strEdtMakeupOtherBrand) {
            this.strEdtMakeupOtherBrand = strEdtMakeupOtherBrand;
        }
    }