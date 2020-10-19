package com.prometteur.cpapp.beans;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReschduleGetDataBean implements Serializable
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
    @SerializedName("services")
    @Expose
    private List<Service> services = null;
    @SerializedName("combo")
    @Expose
    private List<Combo> combo = null;
    @SerializedName("offers")
    @Expose
    private List<Offer> offers = null;
    private final static long serialVersionUID = -7196272332744215414L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ReschduleGetDataBean withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ReschduleGetDataBean withMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public ReschduleGetDataBean withResult(List<Result> result) {
        this.result = result;
        return this;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public ReschduleGetDataBean withServices(List<Service> services) {
        this.services = services;
        return this;
    }

    public List<Combo> getCombo() {
        return combo;
    }

    public void setCombo(List<Combo> combo) {
        this.combo = combo;
    }

    public ReschduleGetDataBean withCombo(List<Combo> combo) {
        this.combo = combo;
        return this;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public ReschduleGetDataBean withOffers(List<Offer> offers) {
        this.offers = offers;
        return this;
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
        @SerializedName("selected")
        @Expose
        private String selected;
        private final static long serialVersionUID = -5159864167137376241L;

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

        public String getSelected() {
            return selected;
        }

        public void setSelected(String selected) {
            this.selected = selected;
        }
    }


    public class Result implements Serializable
    {

        @SerializedName("apt_id")
        @Expose
        private String aptId;
        @SerializedName("bran_id")
        @Expose
        private String branId;
        @SerializedName("bran_work_hrs")
        @Expose
        private String branWorkHrs;
        @SerializedName("bran_off_day")
        @Expose
        private String branOffDay;
        @SerializedName("bran_holiday_from")
        @Expose
        private String branHolidayFrom;
        @SerializedName("bran_holiday_to")
        @Expose
        private String branHolidayTo;
        @SerializedName("bran_open_status")
        @Expose
        private String branOpenStatus;
        @SerializedName("bran_opened_on")
        @Expose
        private String branOpenedOn;
        private final static long serialVersionUID = 7003104869169675871L;

        public String getAptId() {
            return aptId;
        }

        public void setAptId(String aptId) {
            this.aptId = aptId;
        }



        public String getBranId() {
            return branId;
        }

        public void setBranId(String branId) {
            this.branId = branId;
        }



        public String getBranWorkHrs() {
            return branWorkHrs;
        }

        public void setBranWorkHrs(String branWorkHrs) {
            this.branWorkHrs = branWorkHrs;
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


        public String getBranOpenStatus() {
            return branOpenStatus;
        }

        public void setBranOpenStatus(String branOpenStatus) {
            this.branOpenStatus = branOpenStatus;
        }



        public String getBranOpenedOn() {
            return branOpenedOn;
        }

        public void setBranOpenedOn(String branOpenedOn) {
            this.branOpenedOn = branOpenedOn;
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
        @SerializedName("srvc_price")
        @Expose
        private String srvcPrice;
        @SerializedName("srvc_operators")
        @Expose
        private String srvcOperators;
        @SerializedName("brnd_name")
        @Expose
        private String brndName;
        @SerializedName("aptser_operator_id")
        @Expose
        private String aptserOperatorId;
        @SerializedName("operators")
        @Expose
        private List<Operator> operators = null;
        private final static long serialVersionUID = -350851694169032181L;
        @SerializedName("aptser_service_price")
        @Expose
        private String aptserServicePrice;
        @SerializedName("aptser_offer_price")
        @Expose
        private String aptserOfferPrice;
        @SerializedName("aptser_offer_main_price")
        @Expose
        private String aptserOfferMainPrice;
        @SerializedName("aptser_mooi_commission")
        @Expose
        private String aptserMooiCommission;

        @SerializedName("aptser_offer_name")
        @Expose
        private String aptserOfferName;
        @SerializedName("aptser_service_name")
        @Expose
        private String aptserServiceName;
        @SerializedName("aptser_brand_name")
        @Expose
        private String aptserBrandName;
        @SerializedName("aptser_mooi_discount")
        @Expose
        private String aptserMooiDiscount;
        @SerializedName("aptser_offer_discount")
        @Expose
        private String aptserOfferDiscount;
        //new


        public String getAptserOfferName() {
            return aptserOfferName;
        }

        public void setAptserOfferName(String aptserOfferName) {
            this.aptserOfferName = aptserOfferName;
        }

        public String getAptserServiceName() {
            return aptserServiceName;
        }

        public void setAptserServiceName(String aptserServiceName) {
            this.aptserServiceName = aptserServiceName;
        }

        public String getAptserBrandName() {
            return aptserBrandName;
        }

        public void setAptserBrandName(String aptserBrandName) {
            this.aptserBrandName = aptserBrandName;
        }

        public String getAptserMooiDiscount() {
            return aptserMooiDiscount;
        }

        public void setAptserMooiDiscount(String aptserMooiDiscount) {
            this.aptserMooiDiscount = aptserMooiDiscount;
        }

        public String getAptserOfferDiscount() {
            return aptserOfferDiscount;
        }

        public void setAptserOfferDiscount(String aptserOfferDiscount) {
            this.aptserOfferDiscount = aptserOfferDiscount;
        }

        //old
        public String getAptserServicePrice() {
            return aptserServicePrice;
        }

        public void setAptserServicePrice(String aptserServicePrice) {
            this.aptserServicePrice = aptserServicePrice;
        }

        public String getAptserOfferPrice() {
            return aptserOfferPrice;
        }

        public void setAptserOfferPrice(String aptserOfferPrice) {
            this.aptserOfferPrice = aptserOfferPrice;
        }

        public String getAptserOfferMainPrice() {
            return aptserOfferMainPrice;
        }

        public void setAptserOfferMainPrice(String aptserOfferMainPrice) {
            this.aptserOfferMainPrice = aptserOfferMainPrice;
        }

        public String getAptserMooiCommission() {
            return aptserMooiCommission;
        }

        public void setAptserMooiCommission(String aptserMooiCommission) {
            this.aptserMooiCommission = aptserMooiCommission;
        }

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

        public String getSrvcPrice() {
            return srvcPrice;
        }

        public void setSrvcPrice(String srvcPrice) {
            this.srvcPrice = srvcPrice;
        }

        public String getSrvcOperators() {
            return srvcOperators;
        }

        public void setSrvcOperators(String srvcOperators) {
            this.srvcOperators = srvcOperators;
        }

        public String getBrndName() {
            return brndName;
        }

        public void setBrndName(String brndName) {
            this.brndName = brndName;
        }

        public String getAptserOperatorId() {
            return aptserOperatorId;
        }

        public void setAptserOperatorId(String aptserOperatorId) {
            this.aptserOperatorId = aptserOperatorId;
        }

        public List<Operator> getOperators() {
            return operators;
        }

        public void setOperators(List<Operator> operators) {
            this.operators = operators;
        }

    }

    public class Offer implements Serializable
    {

        @SerializedName("offer_id")
        @Expose
        private String offerId;
        @SerializedName("offer_name")
        @Expose
        private String offerName;
        @SerializedName("offer_price")
        @Expose
        private String offerPrice;
        @SerializedName("offer_discount_price")
        @Expose
        private String offerDiscountPrice;
        @SerializedName("pro_services")
        @Expose
        private List<ProService> proServices = null;
        private final static long serialVersionUID = -1998113403424933124L;

        @SerializedName("aptser_offer_name")
        @Expose
        private String aptserOfferName;
        @SerializedName("aptser_service_name")
        @Expose
        private String aptserServiceName;
        @SerializedName("aptser_brand_name")
        @Expose
        private String aptserBrandName;
        @SerializedName("aptser_mooi_discount")
        @Expose
        private String aptserMooiDiscount;
        @SerializedName("aptser_offer_discount")
        @Expose
        private String aptserOfferDiscount;
        //new


        public String getAptserOfferName() {
            return aptserOfferName;
        }

        public void setAptserOfferName(String aptserOfferName) {
            this.aptserOfferName = aptserOfferName;
        }

        public String getAptserServiceName() {
            return aptserServiceName;
        }

        public void setAptserServiceName(String aptserServiceName) {
            this.aptserServiceName = aptserServiceName;
        }

        public String getAptserBrandName() {
            return aptserBrandName;
        }

        public void setAptserBrandName(String aptserBrandName) {
            this.aptserBrandName = aptserBrandName;
        }

        public String getAptserMooiDiscount() {
            return aptserMooiDiscount;
        }

        public void setAptserMooiDiscount(String aptserMooiDiscount) {
            this.aptserMooiDiscount = aptserMooiDiscount;
        }

        public String getAptserOfferDiscount() {
            return aptserOfferDiscount;
        }

        public void setAptserOfferDiscount(String aptserOfferDiscount) {
            this.aptserOfferDiscount = aptserOfferDiscount;
        }


        public String getOfferId() {
            return offerId;
        }

        public void setOfferId(String offerId) {
            this.offerId = offerId;
        }

        public Offer withOfferId(String offerId) {
            this.offerId = offerId;
            return this;
        }

        public String getOfferName() {
            return offerName;
        }

        public void setOfferName(String offerName) {
            this.offerName = offerName;
        }

        public Offer withOfferName(String offerName) {
            this.offerName = offerName;
            return this;
        }

        public String getOfferPrice() {
            return offerPrice;
        }

        public void setOfferPrice(String offerPrice) {
            this.offerPrice = offerPrice;
        }

        public Offer withOfferPrice(String offerPrice) {
            this.offerPrice = offerPrice;
            return this;
        }

        public String getOfferDiscountPrice() {
            return offerDiscountPrice;
        }

        public void setOfferDiscountPrice(String offerDiscountPrice) {
            this.offerDiscountPrice = offerDiscountPrice;
        }

        public Offer withOfferDiscountPrice(String offerDiscountPrice) {
            this.offerDiscountPrice = offerDiscountPrice;
            return this;
        }

        public List<ProService> getProServices() {
            return proServices;
        }

        public void setProServices(List<ProService> proServices) {
            this.proServices = proServices;
        }

        public Offer withProServices(List<ProService> proServices) {
            this.proServices = proServices;
            return this;
        }

    }

    public class ProService implements Serializable
    {

        @SerializedName("srvc_id")
        @Expose
        private String srvcId;
        @SerializedName("srvc_name")
        @Expose
        private String srvcName;
        @SerializedName("srvc_price")
        @Expose
        private String srvcPrice;
        @SerializedName("srvc_operators")
        @Expose
        private String srvcOperators;
        @SerializedName("aptser_operator_id")
        @Expose
        private String aptserOperatorId;
        @SerializedName("operators")
        @Expose
        private List<Operator> operators = null;
        private final static long serialVersionUID = -2945382508140390242L;
        @SerializedName("aptser_service_price")
        @Expose
        private String aptserServicePrice;
        @SerializedName("aptser_offer_price")
        @Expose
        private String aptserOfferPrice;
        @SerializedName("aptser_offer_main_price")
        @Expose
        private String aptserOfferMainPrice;
        @SerializedName("aptser_mooi_commission")
        @Expose
        private String aptserMooiCommission;
        @SerializedName("brnd_name")
        @Expose
        private String brndName;

        public String getAptserServicePrice() {
            return aptserServicePrice;
        }

        public void setAptserServicePrice(String aptserServicePrice) {
            this.aptserServicePrice = aptserServicePrice;
        }

        public String getAptserOfferPrice() {
            return aptserOfferPrice;
        }

        public void setAptserOfferPrice(String aptserOfferPrice) {
            this.aptserOfferPrice = aptserOfferPrice;
        }

        public String getAptserOfferMainPrice() {
            return aptserOfferMainPrice;
        }

        public void setAptserOfferMainPrice(String aptserOfferMainPrice) {
            this.aptserOfferMainPrice = aptserOfferMainPrice;
        }

        public String getAptserMooiCommission() {
            return aptserMooiCommission;
        }

        public void setAptserMooiCommission(String aptserMooiCommission) {
            this.aptserMooiCommission = aptserMooiCommission;
        }

        public String getSrvcId() {
            return srvcId;
        }

        public void setSrvcId(String srvcId) {
            this.srvcId = srvcId;
        }

        public ProService withSrvcId(String srvcId) {
            this.srvcId = srvcId;
            return this;
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

        public ProService withSrvcName(String srvcName) {
            this.srvcName = srvcName;
            return this;
        }

        public String getSrvcPrice() {
            return srvcPrice;
        }

        public void setSrvcPrice(String srvcPrice) {
            this.srvcPrice = srvcPrice;
        }

        public ProService withSrvcPrice(String srvcPrice) {
            this.srvcPrice = srvcPrice;
            return this;
        }

        public String getSrvcOperators() {
            return srvcOperators;
        }

        public void setSrvcOperators(String srvcOperators) {
            this.srvcOperators = srvcOperators;
        }

        public ProService withSrvcOperators(String srvcOperators) {
            this.srvcOperators = srvcOperators;
            return this;
        }

        public String getAptserOperatorId() {
            return aptserOperatorId;
        }

        public void setAptserOperatorId(String aptserOperatorId) {
            this.aptserOperatorId = aptserOperatorId;
        }

        public ProService withAptserOperatorId(String aptserOperatorId) {
            this.aptserOperatorId = aptserOperatorId;
            return this;
        }

        public List<Operator> getOperators() {
            return operators;
        }

        public void setOperators(List<Operator> operators) {
            this.operators = operators;
        }

        public ProService withOperators(List<Operator> operators) {
            this.operators = operators;
            return this;
        }

    }

    public class Combo implements Serializable
    {

        @SerializedName("offer_id")
        @Expose
        private String offerId;
        @SerializedName("offer_name")
        @Expose
        private String offerName;
        @SerializedName("offer_price")
        @Expose
        private String offerPrice;
        @SerializedName("offer_discount_price")
        @Expose
        private String offerDiscountPrice;
        @SerializedName("combo_services")
        @Expose
        private List<ComboService> comboServices = null;
        private final static long serialVersionUID = 7846494733041404037L;
        @SerializedName("aptser_offer_name")
        @Expose
        private String aptserOfferName;
        @SerializedName("aptser_service_name")
        @Expose
        private String aptserServiceName;
        @SerializedName("aptser_brand_name")
        @Expose
        private String aptserBrandName;
        @SerializedName("aptser_mooi_discount")
        @Expose
        private String aptserMooiDiscount;
        @SerializedName("aptser_offer_discount")
        @Expose
        private String aptserOfferDiscount;
        //new


        public String getAptserOfferName() {
            return aptserOfferName;
        }

        public void setAptserOfferName(String aptserOfferName) {
            this.aptserOfferName = aptserOfferName;
        }

        public String getAptserServiceName() {
            return aptserServiceName;
        }

        public void setAptserServiceName(String aptserServiceName) {
            this.aptserServiceName = aptserServiceName;
        }

        public String getAptserBrandName() {
            return aptserBrandName;
        }

        public void setAptserBrandName(String aptserBrandName) {
            this.aptserBrandName = aptserBrandName;
        }

        public String getAptserMooiDiscount() {
            return aptserMooiDiscount;
        }

        public void setAptserMooiDiscount(String aptserMooiDiscount) {
            this.aptserMooiDiscount = aptserMooiDiscount;
        }

        public String getAptserOfferDiscount() {
            return aptserOfferDiscount;
        }

        public void setAptserOfferDiscount(String aptserOfferDiscount) {
            this.aptserOfferDiscount = aptserOfferDiscount;
        }


        public String getOfferId() {
            return offerId;
        }

        public void setOfferId(String offerId) {
            this.offerId = offerId;
        }

        public Combo withOfferId(String offerId) {
            this.offerId = offerId;
            return this;
        }

        public String getOfferName() {
            return offerName;
        }

        public void setOfferName(String offerName) {
            this.offerName = offerName;
        }

        public Combo withOfferName(String offerName) {
            this.offerName = offerName;
            return this;
        }

        public String getOfferPrice() {
            return offerPrice;
        }

        public void setOfferPrice(String offerPrice) {
            this.offerPrice = offerPrice;
        }

        public Combo withOfferPrice(String offerPrice) {
            this.offerPrice = offerPrice;
            return this;
        }

        public String getOfferDiscountPrice() {
            return offerDiscountPrice;
        }

        public void setOfferDiscountPrice(String offerDiscountPrice) {
            this.offerDiscountPrice = offerDiscountPrice;
        }

        public Combo withOfferDiscountPrice(String offerDiscountPrice) {
            this.offerDiscountPrice = offerDiscountPrice;
            return this;
        }

        public List<ComboService> getComboServices() {
            return comboServices;
        }

        public void setComboServices(List<ComboService> comboServices) {
            this.comboServices = comboServices;
        }

        public Combo withComboServices(List<ComboService> comboServices) {
            this.comboServices = comboServices;
            return this;
        }

    }

    public class ComboService implements Serializable
    {

        @SerializedName("srvc_id")
        @Expose
        private String srvcId;
        @SerializedName("srvc_name")
        @Expose
        private String srvcName;
        @SerializedName("srvc_price")
        @Expose
        private String srvcPrice;
        @SerializedName("srvc_operators")
        @Expose
        private String srvcOperators;
        @SerializedName("aptser_operator_id")
        @Expose
        private String aptserOperatorId;
        @SerializedName("operators")
        @Expose
        private List<Operator> operators = null;
        private final static long serialVersionUID = 3101222740616220637L;
        @SerializedName("aptser_service_price")
        @Expose
        private String aptserServicePrice;
        @SerializedName("aptser_offer_price")
        @Expose
        private String aptserOfferPrice;
        @SerializedName("aptser_offer_main_price")
        @Expose
        private String aptserOfferMainPrice;
        @SerializedName("aptser_mooi_commission")
        @Expose
        private String aptserMooiCommission;
        @SerializedName("brnd_name")
        @Expose
        private String brndName;

        public String getAptserServicePrice() {
            return aptserServicePrice;
        }

        public void setAptserServicePrice(String aptserServicePrice) {
            this.aptserServicePrice = aptserServicePrice;
        }

        public String getAptserOfferPrice() {
            return aptserOfferPrice;
        }

        public void setAptserOfferPrice(String aptserOfferPrice) {
            this.aptserOfferPrice = aptserOfferPrice;
        }

        public String getAptserOfferMainPrice() {
            return aptserOfferMainPrice;
        }

        public void setAptserOfferMainPrice(String aptserOfferMainPrice) {
            this.aptserOfferMainPrice = aptserOfferMainPrice;
        }

        public String getAptserMooiCommission() {
            return aptserMooiCommission;
        }

        public void setAptserMooiCommission(String aptserMooiCommission) {
            this.aptserMooiCommission = aptserMooiCommission;
        }

        public String getSrvcId() {
            return srvcId;
        }

        public void setSrvcId(String srvcId) {
            this.srvcId = srvcId;
        }

        public ComboService withSrvcId(String srvcId) {
            this.srvcId = srvcId;
            return this;
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

        public ComboService withSrvcName(String srvcName) {
            this.srvcName = srvcName;
            return this;
        }

        public String getSrvcPrice() {
            return srvcPrice;
        }

        public void setSrvcPrice(String srvcPrice) {
            this.srvcPrice = srvcPrice;
        }

        public ComboService withSrvcPrice(String srvcPrice) {
            this.srvcPrice = srvcPrice;
            return this;
        }

        public String getSrvcOperators() {
            return srvcOperators;
        }

        public void setSrvcOperators(String srvcOperators) {
            this.srvcOperators = srvcOperators;
        }

        public ComboService withSrvcOperators(String srvcOperators) {
            this.srvcOperators = srvcOperators;
            return this;
        }

        public String getAptserOperatorId() {
            return aptserOperatorId;
        }

        public void setAptserOperatorId(String aptserOperatorId) {
            this.aptserOperatorId = aptserOperatorId;
        }

        public ComboService withAptserOperatorId(String aptserOperatorId) {
            this.aptserOperatorId = aptserOperatorId;
            return this;
        }

        public List<Operator> getOperators() {
            return operators;
        }

        public void setOperators(List<Operator> operators) {
            this.operators = operators;
        }

        public ComboService withOperators(List<Operator> operators) {
            this.operators = operators;
            return this;
        }

    }

    public class Operator_ implements Serializable
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
        @SerializedName("selected")
        @Expose
        private String selected;
        private final static long serialVersionUID = -6375995377263697603L;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Operator_ withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public String getUserFName() {
            return userFName;
        }

        public void setUserFName(String userFName) {
            this.userFName = userFName;
        }

        public Operator_ withUserFName(String userFName) {
            this.userFName = userFName;
            return this;
        }

        public String getUserLName() {
            return userLName;
        }

        public void setUserLName(String userLName) {
            this.userLName = userLName;
        }

        public Operator_ withUserLName(String userLName) {
            this.userLName = userLName;
            return this;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public Operator_ withUserImg(String userImg) {
            this.userImg = userImg;
            return this;
        }

        public String getSelected() {
            return selected;
        }

        public void setSelected(String selected) {
            this.selected = selected;
        }
    }
}