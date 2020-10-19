package com.prometteur.cpapp.beans;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdvertisementBean implements Serializable
{

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("bran_open_status")
@Expose
private String branOpenStatus;
@SerializedName("bran_close_status")
@Expose
private String branCloseStatus;
@SerializedName("result")
@Expose
private List<Result> result = null;
private final static long serialVersionUID = -4671273239182743367L;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public AdvertisementBean withStatus(Integer status) {
this.status = status;
return this;
}

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

public AdvertisementBean withMsg(String msg) {
this.msg = msg;
return this;
}

public List<Result> getResult() {
return result;
}

public void setResult(List<Result> result) {
this.result = result;
}

public AdvertisementBean withResult(List<Result> result) {
this.result = result;
return this;
}

    public String getBranOpenStatus() {
        return branOpenStatus;
    }

    public void setBranOpenStatus(String branOpenStatus) {
        this.branOpenStatus = branOpenStatus;
    }

    public String getBranCloseStatus() {
        return branCloseStatus;
    }

    public void setBranCloseStatus(String branCloseStatus) {
        this.branCloseStatus = branCloseStatus;
    }

    public class Result implements Serializable
    {

        @SerializedName("adv_id")
        @Expose
        private String advId;
        @SerializedName("adv_img")
        @Expose
        private String advImg;
        @SerializedName("adv_url")
        @Expose
        private String advUrl;
        @SerializedName("adv_for")
        @Expose
        private String advFor;
        @SerializedName("adv_pincode")
        @Expose
        private String advPincode;
        @SerializedName("adv_create_date")
        @Expose
        private String advCreateDate;
        @SerializedName("adv_create_by")
        @Expose
        private String advCreateBy;
        @SerializedName("adv_update_date")
        @Expose
        private String advUpdateDate;
        @SerializedName("adv_update_by")
        @Expose
        private String advUpdateBy;
        @SerializedName("adv_status")
        @Expose
        private String advStatus;
        private final static long serialVersionUID = -2557688402606704213L;

        public String getAdvId() {
            return advId;
        }

        public void setAdvId(String advId) {
            this.advId = advId;
        }

        public Result withAdvId(String advId) {
            this.advId = advId;
            return this;
        }

        public String getAdvImg() {
            return advImg;
        }

        public void setAdvImg(String advImg) {
            this.advImg = advImg;
        }

        public Result withAdvImg(String advImg) {
            this.advImg = advImg;
            return this;
        }

        public String getAdvUrl() {
            return advUrl;
        }

        public void setAdvUrl(String advUrl) {
            this.advUrl = advUrl;
        }

        public Result withAdvUrl(String advUrl) {
            this.advUrl = advUrl;
            return this;
        }

        public String getAdvFor() {
            return advFor;
        }

        public void setAdvFor(String advFor) {
            this.advFor = advFor;
        }

        public Result withAdvFor(String advFor) {
            this.advFor = advFor;
            return this;
        }

        public String getAdvPincode() {
            return advPincode;
        }

        public void setAdvPincode(String advPincode) {
            this.advPincode = advPincode;
        }

        public Result withAdvPincode(String advPincode) {
            this.advPincode = advPincode;
            return this;
        }

        public String getAdvCreateDate() {
            return advCreateDate;
        }

        public void setAdvCreateDate(String advCreateDate) {
            this.advCreateDate = advCreateDate;
        }

        public Result withAdvCreateDate(String advCreateDate) {
            this.advCreateDate = advCreateDate;
            return this;
        }

        public String getAdvCreateBy() {
            return advCreateBy;
        }

        public void setAdvCreateBy(String advCreateBy) {
            this.advCreateBy = advCreateBy;
        }

        public Result withAdvCreateBy(String advCreateBy) {
            this.advCreateBy = advCreateBy;
            return this;
        }

        public String getAdvUpdateDate() {
            return advUpdateDate;
        }

        public void setAdvUpdateDate(String advUpdateDate) {
            this.advUpdateDate = advUpdateDate;
        }

        public Result withAdvUpdateDate(String advUpdateDate) {
            this.advUpdateDate = advUpdateDate;
            return this;
        }

        public String getAdvUpdateBy() {
            return advUpdateBy;
        }

        public void setAdvUpdateBy(String advUpdateBy) {
            this.advUpdateBy = advUpdateBy;
        }

        public Result withAdvUpdateBy(String advUpdateBy) {
            this.advUpdateBy = advUpdateBy;
            return this;
        }

        public String getAdvStatus() {
            return advStatus;
        }

        public void setAdvStatus(String advStatus) {
            this.advStatus = advStatus;
        }

        public Result withAdvStatus(String advStatus) {
            this.advStatus = advStatus;
            return this;
        }

    }
}