package com.prometteur.cpapp.beans;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComboListBean implements Serializable
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
private final static long serialVersionUID = 302802510111013445L;

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

        @SerializedName("offer_id")
        @Expose
        private String offerId;
        @SerializedName("offer_branch_id")
        @Expose
        private String offerBranchId;
        @SerializedName("offer_name")
        @Expose
        private String offerName;
        @SerializedName("offer_services")
        @Expose
        private String offerServices;
        @SerializedName("offer_price")
        @Expose
        private String offerPrice;
        @SerializedName("offer_discount_price")
        @Expose
        private String offerDiscountPrice;
        @SerializedName("services")
        @Expose
        private List<Service> services = null;
        private final static long serialVersionUID = 4297905542788554949L;

        public String getOfferId() {
            return offerId;
        }

        public void setOfferId(String offerId) {
            this.offerId = offerId;
        }

        public String getOfferBranchId() {
            return offerBranchId;
        }

        public void setOfferBranchId(String offerBranchId) {
            this.offerBranchId = offerBranchId;
        }

        public String getOfferName() {
            return offerName;
        }

        public void setOfferName(String offerName) {
            this.offerName = offerName;
        }

        public String getOfferServices() {
            return offerServices;
        }

        public void setOfferServices(String offerServices) {
            this.offerServices = offerServices;
        }

        public String getOfferPrice() {
            return offerPrice;
        }

        public void setOfferPrice(String offerPrice) {
            this.offerPrice = offerPrice;
        }

        public String getOfferDiscountPrice() {
            return offerDiscountPrice;
        }

        public void setOfferDiscountPrice(String offerDiscountPrice) {
            this.offerDiscountPrice = offerDiscountPrice;
        }

        public List<Service> getServices() {
            return services;
        }

        public void setServices(List<Service> services) {
            this.services = services;
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
        private Object brndName;
        private final static long serialVersionUID = -7419006728499833827L;

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

        public Object getBrndName() {
            return brndName;
        }

        public void setBrndName(Object brndName) {
            this.brndName = brndName;
        }

    }
}