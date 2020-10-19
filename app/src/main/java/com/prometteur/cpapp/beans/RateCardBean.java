package com.prometteur.cpapp.beans;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateCardBean implements Serializable
{
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("result")
    @Expose
    private Result result = null;
    @SerializedName("services")
    @Expose
    private List<Service> services = null;
    private final static long serialVersionUID = -3589473645534276935L;

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }


    public class Service implements Serializable
    {

        @SerializedName("service")
        @Expose
        private String service;
        private final static long serialVersionUID = -2379193786937833351L;

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

    }
    public class Result implements Serializable
    {

        @SerializedName("Hair")
        @Expose
        private List<Hair> hair = null;
        @SerializedName("Spa")
        @Expose
        private List<Spa> spa = null;
        @SerializedName("Skin")
        @Expose
        private List<Skin> skin = null;
        @SerializedName("Nails")
        @Expose
        private List<Nail> nail = null;
        @SerializedName("Makeup")
        @Expose
        private List<Makeup> makeup = null;
        private final static long serialVersionUID = -4473022444850976628L;

        public List<Hair> getHair() {
            return hair;
        }

        public void setHair(List<Hair> hair) {
            this.hair = hair;
        }

        public List<Spa> getSpa() {
            return spa;
        }

        public void setSpa(List<Spa> spa) {
            this.spa = spa;
        }

        public List<Skin> getSkin() {
            return skin;
        }

        public void setSkin(List<Skin> skin) {
            this.skin = skin;
        }

        public List<Nail> getNail() {
            return nail;
        }

        public void setNail(List<Nail> nail) {
            this.nail = nail;
        }

        public List<Makeup> getMakeup() {
            return makeup;
        }

        public void setMakeup(List<Makeup> makeup) {
            this.makeup = makeup;
        }

    }
    public class Skin implements Serializable
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
        @SerializedName("srvc_discount_price")
        @Expose
        private String srvcDiscountPrice;
        @SerializedName("srvc_estimate_time")
        @Expose
        private String srvcEstimateTime;
        @SerializedName("brnd_name")
        @Expose
        private Object brndName;
        private final static long serialVersionUID = -64085209382737172L;

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

        public String getSrvcDiscountPrice() {
            return srvcDiscountPrice;
        }

        public void setSrvcDiscountPrice(String srvcDiscountPrice) {
            this.srvcDiscountPrice = srvcDiscountPrice;
        }

        public String getSrvcEstimateTime() {
            return srvcEstimateTime;
        }

        public void setSrvcEstimateTime(String srvcEstimateTime) {
            this.srvcEstimateTime = srvcEstimateTime;
        }

        public Object getBrndName() {
            return brndName;
        }

        public void setBrndName(Object brndName) {
            this.brndName = brndName;
        }

    }

    public class Spa implements Serializable
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
        @SerializedName("srvc_discount_price")
        @Expose
        private String srvcDiscountPrice;
        @SerializedName("srvc_estimate_time")
        @Expose
        private String srvcEstimateTime;
        @SerializedName("brnd_name")
        @Expose
        private Object brndName;
        private final static long serialVersionUID = 8625593496179958183L;

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

        public String getSrvcDiscountPrice() {
            return srvcDiscountPrice;
        }

        public void setSrvcDiscountPrice(String srvcDiscountPrice) {
            this.srvcDiscountPrice = srvcDiscountPrice;
        }

        public String getSrvcEstimateTime() {
            return srvcEstimateTime;
        }

        public void setSrvcEstimateTime(String srvcEstimateTime) {
            this.srvcEstimateTime = srvcEstimateTime;
        }

        public Object getBrndName() {
            return brndName;
        }

        public void setBrndName(Object brndName) {
            this.brndName = brndName;
        }

    }

    public class Nail implements Serializable
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
        @SerializedName("srvc_discount_price")
        @Expose
        private String srvcDiscountPrice;
        @SerializedName("srvc_estimate_time")
        @Expose
        private String srvcEstimateTime;
        @SerializedName("brnd_name")
        @Expose
        private Object brndName;
        private final static long serialVersionUID = 4861699627863980664L;

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

        public String getSrvcDiscountPrice() {
            return srvcDiscountPrice;
        }

        public void setSrvcDiscountPrice(String srvcDiscountPrice) {
            this.srvcDiscountPrice = srvcDiscountPrice;
        }

        public String getSrvcEstimateTime() {
            return srvcEstimateTime;
        }

        public void setSrvcEstimateTime(String srvcEstimateTime) {
            this.srvcEstimateTime = srvcEstimateTime;
        }

        public Object getBrndName() {
            return brndName;
        }

        public void setBrndName(Object brndName) {
            this.brndName = brndName;
        }

    }

    public class Makeup implements Serializable
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
        @SerializedName("srvc_discount_price")
        @Expose
        private String srvcDiscountPrice;
        @SerializedName("srvc_estimate_time")
        @Expose
        private String srvcEstimateTime;
        @SerializedName("brnd_name")
        @Expose
        private Object brndName;
        private final static long serialVersionUID = 3349100675682157739L;

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

        public String getSrvcDiscountPrice() {
            return srvcDiscountPrice;
        }

        public void setSrvcDiscountPrice(String srvcDiscountPrice) {
            this.srvcDiscountPrice = srvcDiscountPrice;
        }

        public String getSrvcEstimateTime() {
            return srvcEstimateTime;
        }

        public void setSrvcEstimateTime(String srvcEstimateTime) {
            this.srvcEstimateTime = srvcEstimateTime;
        }

        public Object getBrndName() {
            return brndName;
        }

        public void setBrndName(Object brndName) {
            this.brndName = brndName;
        }

    }



    public class Hair implements Serializable
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
        @SerializedName("srvc_discount_price")
        @Expose
        private String srvcDiscountPrice;
        @SerializedName("srvc_estimate_time")
        @Expose
        private String srvcEstimateTime;
        @SerializedName("brnd_name")
        @Expose
        private Object brndName;
        private final static long serialVersionUID = -1446428685326586031L;

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

        public String getSrvcDiscountPrice() {
            return srvcDiscountPrice;
        }

        public void setSrvcDiscountPrice(String srvcDiscountPrice) {
            this.srvcDiscountPrice = srvcDiscountPrice;
        }

        public String getSrvcEstimateTime() {
            return srvcEstimateTime;
        }

        public void setSrvcEstimateTime(String srvcEstimateTime) {
            this.srvcEstimateTime = srvcEstimateTime;
        }

        public Object getBrndName() {
            return brndName;
        }

        public void setBrndName(Object brndName) {
            this.brndName = brndName;
        }

    }
}