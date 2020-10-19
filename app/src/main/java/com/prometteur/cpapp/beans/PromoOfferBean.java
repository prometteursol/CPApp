package com.prometteur.cpapp.beans;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromoOfferBean implements Serializable
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
private final static long serialVersionUID = 8610240840303232808L;

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

        @SerializedName("prooffer_id")
        @Expose
        private String proofferId;
        @SerializedName("prooffer_branch_id")
        @Expose
        private String proofferBranchId;
        @SerializedName("prooffer_name")
        @Expose
        private String proofferName;
        @SerializedName("prooffer_services")
        @Expose
        private String proofferServices;
        @SerializedName("prooffer_price")
        @Expose
        private String proofferPrice;
        @SerializedName("prooffer_discount")
        @Expose
        private String proofferDiscount;
        @SerializedName("prooffer_discount_price")
        @Expose
        private String proofferDiscountPrice;
        @SerializedName("prooffer_start_date")
        @Expose
        private String proofferStartDate;
        @SerializedName("prooffer_end_date")
        @Expose
        private String proofferEndDate;
        @SerializedName("services")
        @Expose
        private List<Service> services = null;
        private final static long serialVersionUID = -5902016185441435639L;

        public String getProofferId() {
            return proofferId;
        }

        public void setProofferId(String proofferId) {
            this.proofferId = proofferId;
        }

        public String getProofferBranchId() {
            return proofferBranchId;
        }

        public void setProofferBranchId(String proofferBranchId) {
            this.proofferBranchId = proofferBranchId;
        }

        public String getProofferName() {
            return proofferName;
        }

        public void setProofferName(String proofferName) {
            this.proofferName = proofferName;
        }

        public String getProofferServices() {
            return proofferServices;
        }

        public void setProofferServices(String proofferServices) {
            this.proofferServices = proofferServices;
        }

        public String getProofferPrice() {
            return proofferPrice;
        }

        public void setProofferPrice(String proofferPrice) {
            this.proofferPrice = proofferPrice;
        }

        public String getProofferDiscountPrice() {
            return proofferDiscountPrice;
        }

        public void setProofferDiscountPrice(String proofferDiscountPrice) {
            this.proofferDiscountPrice = proofferDiscountPrice;
        }

        public String getProofferStartDate() {
            return proofferStartDate;
        }

        public void setProofferStartDate(String proofferStartDate) {
            this.proofferStartDate = proofferStartDate;
        }

        public String getProofferEndDate() {
            return proofferEndDate;
        }

        public void setProofferEndDate(String proofferEndDate) {
            this.proofferEndDate = proofferEndDate;
        }

        public List<Service> getServices() {
            return services;
        }

        public void setServices(List<Service> services) {
            this.services = services;
        }

        public String getProofferDiscount() {
            return proofferDiscount;
        }

        public void setProofferDiscount(String proofferDiscount) {
            this.proofferDiscount = proofferDiscount;
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