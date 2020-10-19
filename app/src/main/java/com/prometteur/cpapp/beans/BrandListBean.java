package com.prometteur.cpapp.beans;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandListBean implements Serializable
{

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("result")
@Expose
private Result result;
    @SerializedName("services")
    @Expose
    private List<Service> services = null;
private final static long serialVersionUID = -423752128631075782L;

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
public void setResult(Result result) {
this.result = result;
}
    public class Hair implements Serializable
    {

        @SerializedName("brnd_id")
        @Expose
        private String brndId;
        @SerializedName("brnd_name")
        @Expose
        private String brndName;
        @SerializedName("brnd_category")
        @Expose
        private String brndCategory;
        @SerializedName("brnd_img")
        @Expose
        private String brndImg;
        @SerializedName("brnd_create_date")
        @Expose
        private String brndCreateDate;
        @SerializedName("brnd_update_date")
        @Expose
        private String brndUpdateDate;
        @SerializedName("brnd_create_by")
        @Expose
        private String brndCreateBy;
        @SerializedName("brnd_update_by")
        @Expose
        private String brndUpdateBy;
        @SerializedName("brnd_status")
        @Expose
        private String brndStatus;
        @SerializedName("brnd_url")
        @Expose
        private String brnd_url;

        public String getBrnd_url() {
            return brnd_url;
        }

        public void setBrnd_url(String brnd_url) {
            this.brnd_url = brnd_url;
        }

        private final static long serialVersionUID = 5998642550286433269L;

        public String getBrndId() {
            return brndId;
        }

        public void setBrndId(String brndId) {
            this.brndId = brndId;
        }

        public String getBrndName() {
            return brndName;
        }

        public void setBrndName(String brndName) {
            this.brndName = brndName;
        }

        public String getBrndCategory() {
            return brndCategory;
        }

        public void setBrndCategory(String brndCategory) {
            this.brndCategory = brndCategory;
        }

        public String getBrndImg() {
            return brndImg;
        }

        public void setBrndImg(String brndImg) {
            this.brndImg = brndImg;
        }

        public String getBrndCreateDate() {
            return brndCreateDate;
        }

        public void setBrndCreateDate(String brndCreateDate) {
            this.brndCreateDate = brndCreateDate;
        }

        public String getBrndUpdateDate() {
            return brndUpdateDate;
        }

        public void setBrndUpdateDate(String brndUpdateDate) {
            this.brndUpdateDate = brndUpdateDate;
        }

        public String getBrndCreateBy() {
            return brndCreateBy;
        }

        public void setBrndCreateBy(String brndCreateBy) {
            this.brndCreateBy = brndCreateBy;
        }

        public String getBrndUpdateBy() {
            return brndUpdateBy;
        }

        public void setBrndUpdateBy(String brndUpdateBy) {
            this.brndUpdateBy = brndUpdateBy;
        }

        public String getBrndStatus() {
            return brndStatus;
        }

        public void setBrndStatus(String brndStatus) {
            this.brndStatus = brndStatus;
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

    public class Spa implements Serializable
    {

        @SerializedName("brnd_id")
        @Expose
        private String brndId;
        @SerializedName("brnd_name")
        @Expose
        private String brndName;
        @SerializedName("brnd_category")
        @Expose
        private String brndCategory;
        @SerializedName("brnd_img")
        @Expose
        private String brndImg;
        @SerializedName("brnd_create_date")
        @Expose
        private String brndCreateDate;
        @SerializedName("brnd_update_date")
        @Expose
        private String brndUpdateDate;
        @SerializedName("brnd_create_by")
        @Expose
        private String brndCreateBy;
        @SerializedName("brnd_update_by")
        @Expose
        private String brndUpdateBy;
        @SerializedName("brnd_status")
        @Expose
        private String brndStatus;
        @SerializedName("brnd_url")
        @Expose
        private String brnd_url;

        public String getBrnd_url() {
            return brnd_url;
        }

        public void setBrnd_url(String brnd_url) {
            this.brnd_url = brnd_url;
        }
        private final static long serialVersionUID = 7768357419548713943L;

        public String getBrndId() {
            return brndId;
        }

        public void setBrndId(String brndId) {
            this.brndId = brndId;
        }

        public String getBrndName() {
            return brndName;
        }

        public void setBrndName(String brndName) {
            this.brndName = brndName;
        }

        public String getBrndCategory() {
            return brndCategory;
        }

        public void setBrndCategory(String brndCategory) {
            this.brndCategory = brndCategory;
        }

        public String getBrndImg() {
            return brndImg;
        }

        public void setBrndImg(String brndImg) {
            this.brndImg = brndImg;
        }

        public String getBrndCreateDate() {
            return brndCreateDate;
        }

        public void setBrndCreateDate(String brndCreateDate) {
            this.brndCreateDate = brndCreateDate;
        }

        public String getBrndUpdateDate() {
            return brndUpdateDate;
        }

        public void setBrndUpdateDate(String brndUpdateDate) {
            this.brndUpdateDate = brndUpdateDate;
        }

        public String getBrndCreateBy() {
            return brndCreateBy;
        }

        public void setBrndCreateBy(String brndCreateBy) {
            this.brndCreateBy = brndCreateBy;
        }

        public String getBrndUpdateBy() {
            return brndUpdateBy;
        }

        public void setBrndUpdateBy(String brndUpdateBy) {
            this.brndUpdateBy = brndUpdateBy;
        }

        public String getBrndStatus() {
            return brndStatus;
        }

        public void setBrndStatus(String brndStatus) {
            this.brndStatus = brndStatus;
        }

    }

    public class Nail implements Serializable
    {

        @SerializedName("brnd_id")
        @Expose
        private String brndId;
        @SerializedName("brnd_name")
        @Expose
        private String brndName;
        @SerializedName("brnd_category")
        @Expose
        private String brndCategory;
        @SerializedName("brnd_img")
        @Expose
        private String brndImg;
        @SerializedName("brnd_create_date")
        @Expose
        private String brndCreateDate;
        @SerializedName("brnd_update_date")
        @Expose
        private String brndUpdateDate;
        @SerializedName("brnd_create_by")
        @Expose
        private String brndCreateBy;
        @SerializedName("brnd_update_by")
        @Expose
        private String brndUpdateBy;
        @SerializedName("brnd_status")
        @Expose
        private String brndStatus;
        @SerializedName("brnd_url")
        @Expose
        private String brnd_url;

        public String getBrnd_url() {
            return brnd_url;
        }

        public void setBrnd_url(String brnd_url) {
            this.brnd_url = brnd_url;
        }
        private final static long serialVersionUID = 7768357419548713943L;

        public String getBrndId() {
            return brndId;
        }

        public void setBrndId(String brndId) {
            this.brndId = brndId;
        }

        public String getBrndName() {
            return brndName;
        }

        public void setBrndName(String brndName) {
            this.brndName = brndName;
        }

        public String getBrndCategory() {
            return brndCategory;
        }

        public void setBrndCategory(String brndCategory) {
            this.brndCategory = brndCategory;
        }

        public String getBrndImg() {
            return brndImg;
        }

        public void setBrndImg(String brndImg) {
            this.brndImg = brndImg;
        }

        public String getBrndCreateDate() {
            return brndCreateDate;
        }

        public void setBrndCreateDate(String brndCreateDate) {
            this.brndCreateDate = brndCreateDate;
        }

        public String getBrndUpdateDate() {
            return brndUpdateDate;
        }

        public void setBrndUpdateDate(String brndUpdateDate) {
            this.brndUpdateDate = brndUpdateDate;
        }

        public String getBrndCreateBy() {
            return brndCreateBy;
        }

        public void setBrndCreateBy(String brndCreateBy) {
            this.brndCreateBy = brndCreateBy;
        }

        public String getBrndUpdateBy() {
            return brndUpdateBy;
        }

        public void setBrndUpdateBy(String brndUpdateBy) {
            this.brndUpdateBy = brndUpdateBy;
        }

        public String getBrndStatus() {
            return brndStatus;
        }

        public void setBrndStatus(String brndStatus) {
            this.brndStatus = brndStatus;
        }

    }
    public class Skin implements Serializable
    {

        @SerializedName("brnd_id")
        @Expose
        private String brndId;
        @SerializedName("brnd_name")
        @Expose
        private String brndName;
        @SerializedName("brnd_category")
        @Expose
        private String brndCategory;
        @SerializedName("brnd_img")
        @Expose
        private String brndImg;
        @SerializedName("brnd_create_date")
        @Expose
        private String brndCreateDate;
        @SerializedName("brnd_update_date")
        @Expose
        private String brndUpdateDate;
        @SerializedName("brnd_create_by")
        @Expose
        private String brndCreateBy;
        @SerializedName("brnd_update_by")
        @Expose
        private String brndUpdateBy;
        @SerializedName("brnd_status")
        @Expose
        private String brndStatus;
        @SerializedName("brnd_url")
        @Expose
        private String brnd_url;

        public String getBrnd_url() {
            return brnd_url;
        }

        public void setBrnd_url(String brnd_url) {
            this.brnd_url = brnd_url;
        }
        private final static long serialVersionUID = 7768357419548713943L;

        public String getBrndId() {
            return brndId;
        }

        public void setBrndId(String brndId) {
            this.brndId = brndId;
        }

        public String getBrndName() {
            return brndName;
        }

        public void setBrndName(String brndName) {
            this.brndName = brndName;
        }

        public String getBrndCategory() {
            return brndCategory;
        }

        public void setBrndCategory(String brndCategory) {
            this.brndCategory = brndCategory;
        }

        public String getBrndImg() {
            return brndImg;
        }

        public void setBrndImg(String brndImg) {
            this.brndImg = brndImg;
        }

        public String getBrndCreateDate() {
            return brndCreateDate;
        }

        public void setBrndCreateDate(String brndCreateDate) {
            this.brndCreateDate = brndCreateDate;
        }

        public String getBrndUpdateDate() {
            return brndUpdateDate;
        }

        public void setBrndUpdateDate(String brndUpdateDate) {
            this.brndUpdateDate = brndUpdateDate;
        }

        public String getBrndCreateBy() {
            return brndCreateBy;
        }

        public void setBrndCreateBy(String brndCreateBy) {
            this.brndCreateBy = brndCreateBy;
        }

        public String getBrndUpdateBy() {
            return brndUpdateBy;
        }

        public void setBrndUpdateBy(String brndUpdateBy) {
            this.brndUpdateBy = brndUpdateBy;
        }

        public String getBrndStatus() {
            return brndStatus;
        }

        public void setBrndStatus(String brndStatus) {
            this.brndStatus = brndStatus;
        }

    }

    public class Makeup implements Serializable
    {

        @SerializedName("brnd_id")
        @Expose
        private String brndId;
        @SerializedName("brnd_name")
        @Expose
        private String brndName;
        @SerializedName("brnd_category")
        @Expose
        private String brndCategory;
        @SerializedName("brnd_img")
        @Expose
        private String brndImg;
        @SerializedName("brnd_create_date")
        @Expose
        private String brndCreateDate;
        @SerializedName("brnd_update_date")
        @Expose
        private String brndUpdateDate;
        @SerializedName("brnd_create_by")
        @Expose
        private String brndCreateBy;
        @SerializedName("brnd_update_by")
        @Expose
        private String brndUpdateBy;
        @SerializedName("brnd_status")
        @Expose
        private String brndStatus;
        @SerializedName("brnd_url")
        @Expose
        private String brnd_url;

        public String getBrnd_url() {
            return brnd_url;
        }

        public void setBrnd_url(String brnd_url) {
            this.brnd_url = brnd_url;
        }
        private final static long serialVersionUID = 7768357419548713943L;

        public String getBrndId() {
            return brndId;
        }

        public void setBrndId(String brndId) {
            this.brndId = brndId;
        }

        public String getBrndName() {
            return brndName;
        }

        public void setBrndName(String brndName) {
            this.brndName = brndName;
        }

        public String getBrndCategory() {
            return brndCategory;
        }

        public void setBrndCategory(String brndCategory) {
            this.brndCategory = brndCategory;
        }

        public String getBrndImg() {
            return brndImg;
        }

        public void setBrndImg(String brndImg) {
            this.brndImg = brndImg;
        }

        public String getBrndCreateDate() {
            return brndCreateDate;
        }

        public void setBrndCreateDate(String brndCreateDate) {
            this.brndCreateDate = brndCreateDate;
        }

        public String getBrndUpdateDate() {
            return brndUpdateDate;
        }

        public void setBrndUpdateDate(String brndUpdateDate) {
            this.brndUpdateDate = brndUpdateDate;
        }

        public String getBrndCreateBy() {
            return brndCreateBy;
        }

        public void setBrndCreateBy(String brndCreateBy) {
            this.brndCreateBy = brndCreateBy;
        }

        public String getBrndUpdateBy() {
            return brndUpdateBy;
        }

        public void setBrndUpdateBy(String brndUpdateBy) {
            this.brndUpdateBy = brndUpdateBy;
        }

        public String getBrndStatus() {
            return brndStatus;
        }

        public void setBrndStatus(String brndStatus) {
            this.brndStatus = brndStatus;
        }

    }
}