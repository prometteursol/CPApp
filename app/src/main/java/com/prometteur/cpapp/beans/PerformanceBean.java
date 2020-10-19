package com.prometteur.cpapp.beans;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PerformanceBean implements Serializable
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
    @SerializedName("total_apt")
    @Expose
    private String totalApt;
    @SerializedName("total_services")
    @Expose
    private Integer totalServices;
    @SerializedName("week_start_date")
    @Expose
    private String weekStartDate;
    @SerializedName("week_end_date")
    @Expose
    private String weekEndDate;
    @SerializedName("net_amount")
    @Expose
    private String netAmount;
    @SerializedName("avg_service_cost")
    @Expose
    private String avgServiceCost;
    @SerializedName("weekly_rating")
    @Expose
    private String weeklyRating;
    @SerializedName("lifetime_rating")
    @Expose
    private String lifetimeRating;
    @SerializedName("acceptance_rate")
    @Expose
    private String acceptanceRate;
    @SerializedName("cancellation_rate")
    @Expose
    private String cancellationRate;
    @SerializedName("rejection_rate")
    @Expose
    private String rejectionRate;
    @SerializedName("operators")
    @Expose
    private List<Operator> operators = null;
    private final static long serialVersionUID = 4578124871977991562L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public PerformanceBean withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PerformanceBean withMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public PerformanceBean withResult(List<Result> result) {
        this.result = result;
        return this;
    }

    public String getTotalApt() {
        return totalApt;
    }

    public void setTotalApt(String totalApt) {
        this.totalApt = totalApt;
    }

    public PerformanceBean withTotalApt(String totalApt) {
        this.totalApt = totalApt;
        return this;
    }

    public Integer getTotalServices() {
        return totalServices;
    }

    public void setTotalServices(Integer totalServices) {
        this.totalServices = totalServices;
    }

    public PerformanceBean withTotalServices(Integer totalServices) {
        this.totalServices = totalServices;
        return this;
    }

    public String getWeekStartDate() {
        return weekStartDate;
    }

    public void setWeekStartDate(String weekStartDate) {
        this.weekStartDate = weekStartDate;
    }

    public PerformanceBean withWeekStartDate(String weekStartDate) {
        this.weekStartDate = weekStartDate;
        return this;
    }

    public String getWeekEndDate() {
        return weekEndDate;
    }

    public void setWeekEndDate(String weekEndDate) {
        this.weekEndDate = weekEndDate;
    }

    public PerformanceBean withWeekEndDate(String weekEndDate) {
        this.weekEndDate = weekEndDate;
        return this;
    }

    public String getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }

    public PerformanceBean withNetAmount(String netAmount) {
        this.netAmount = netAmount;
        return this;
    }

    public String getAvgServiceCost() {
        return avgServiceCost;
    }

    public void setAvgServiceCost(String avgServiceCost) {
        this.avgServiceCost = avgServiceCost;
    }

    public PerformanceBean withAvgServiceCost(String avgServiceCost) {
        this.avgServiceCost = avgServiceCost;
        return this;
    }

    public String getWeeklyRating() {
        return weeklyRating;
    }

    public void setWeeklyRating(String weeklyRating) {
        this.weeklyRating = weeklyRating;
    }

    public PerformanceBean withWeeklyRating(String weeklyRating) {
        this.weeklyRating = weeklyRating;
        return this;
    }

    public String getLifetimeRating() {
        return lifetimeRating;
    }

    public void setLifetimeRating(String lifetimeRating) {
        this.lifetimeRating = lifetimeRating;
    }

    public PerformanceBean withLifetimeRating(String lifetimeRating) {
        this.lifetimeRating = lifetimeRating;
        return this;
    }

    public String getAcceptanceRate() {
        return acceptanceRate;
    }

    public void setAcceptanceRate(String acceptanceRate) {
        this.acceptanceRate = acceptanceRate;
    }

    public PerformanceBean withAcceptanceRate(String acceptanceRate) {
        this.acceptanceRate = acceptanceRate;
        return this;
    }

    public String getCancellationRate() {
        return cancellationRate;
    }

    public void setCancellationRate(String cancellationRate) {
        this.cancellationRate = cancellationRate;
    }

    public PerformanceBean withCancellationRate(String cancellationRate) {
        this.cancellationRate = cancellationRate;
        return this;
    }

    public String getRejectionRate() {
        return rejectionRate;
    }

    public void setRejectionRate(String rejectionRate) {
        this.rejectionRate = rejectionRate;
    }

    public PerformanceBean withRejectionRate(String rejectionRate) {
        this.rejectionRate = rejectionRate;
        return this;
    }

    public List<Operator> getOperators() {
        return operators;
    }

    public void setOperators(List<Operator> operators) {
        this.operators = operators;
    }

    public PerformanceBean withOperators(List<Operator> operators) {
        this.operators = operators;
        return this;
    }

    public class Result implements Serializable
    {

        @SerializedName("weekday")
        @Expose
        private String weekday;
        @SerializedName("apt_date")
        @Expose
        private String aptDate;
        @SerializedName("amount")
        @Expose
        private String amount;
        private final static long serialVersionUID = -8094952989895398749L;

        public String getWeekday() {
            return weekday;
        }

        public void setWeekday(String weekday) {
            this.weekday = weekday;
        }

        public Result withWeekday(String weekday) {
            this.weekday = weekday;
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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public Result withAmount(String amount) {
            this.amount = amount;
            return this;
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
        @SerializedName("income")
        @Expose
        private String income;
        @SerializedName("total_services")
        @Expose
        private Integer totalServices;
        private final static long serialVersionUID = -6477538713484386365L;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Operator withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public String getUserFName() {
            return userFName;
        }

        public void setUserFName(String userFName) {
            this.userFName = userFName;
        }

        public Operator withUserFName(String userFName) {
            this.userFName = userFName;
            return this;
        }

        public String getUserLName() {
            return userLName;
        }

        public void setUserLName(String userLName) {
            this.userLName = userLName;
        }

        public Operator withUserLName(String userLName) {
            this.userLName = userLName;
            return this;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public Operator withUserImg(String userImg) {
            this.userImg = userImg;
            return this;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public Operator withIncome(String income) {
            this.income = income;
            return this;
        }

        public Integer getTotalServices() {
            return totalServices;
        }

        public void setTotalServices(Integer totalServices) {
            this.totalServices = totalServices;
        }

        public Operator withTotalServices(Integer totalServices) {
            this.totalServices = totalServices;
            return this;
        }

    }
}