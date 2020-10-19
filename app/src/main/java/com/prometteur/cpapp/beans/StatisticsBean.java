package com.prometteur.cpapp.beans;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatisticsBean implements Serializable
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
@SerializedName("total_earning")
@Expose
private String totalEarning;
@SerializedName("mooi_payouts")
@Expose
private String mooiPayouts;
@SerializedName("mooi_cash_payouts")
@Expose
private String mooiCashPayouts;
@SerializedName("mooi_online_payouts")
@Expose
private String mooiOnlinePayouts;
@SerializedName("previous_balance")
@Expose
private Object previousBalance;
private final static long serialVersionUID = -6509057051824636130L;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public StatisticsBean withStatus(Integer status) {
this.status = status;
return this;
}

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

public StatisticsBean withMsg(String msg) {
this.msg = msg;
return this;
}

public List<Result> getResult() {
return result;
}

public void setResult(List<Result> result) {
this.result = result;
}

public StatisticsBean withResult(List<Result> result) {
this.result = result;
return this;
}

public String getTotalEarning() {
return totalEarning;
}

public void setTotalEarning(String totalEarning) {
this.totalEarning = totalEarning;
}

public StatisticsBean withTotalEarning(String totalEarning) {
this.totalEarning = totalEarning;
return this;
}

public String getMooiPayouts() {
return mooiPayouts;
}

public void setMooiPayouts(String mooiPayouts) {
this.mooiPayouts = mooiPayouts;
}

public StatisticsBean withMooiPayouts(String mooiPayouts) {
this.mooiPayouts = mooiPayouts;
return this;
}

public String getMooiCashPayouts() {
return mooiCashPayouts;
}

public void setMooiCashPayouts(String mooiCashPayouts) {
this.mooiCashPayouts = mooiCashPayouts;
}

public StatisticsBean withMooiCashPayouts(String mooiCashPayouts) {
this.mooiCashPayouts = mooiCashPayouts;
return this;
}

public String getMooiOnlinePayouts() {
return mooiOnlinePayouts;
}

public void setMooiOnlinePayouts(String mooiOnlinePayouts) {
this.mooiOnlinePayouts = mooiOnlinePayouts;
}

public StatisticsBean withMooiOnlinePayouts(String mooiOnlinePayouts) {
this.mooiOnlinePayouts = mooiOnlinePayouts;
return this;
}

public Object getPreviousBalance() {
return previousBalance;
}

public void setPreviousBalance(Object previousBalance) {
this.previousBalance = previousBalance;
}

public StatisticsBean withPreviousBalance(Object previousBalance) {
this.previousBalance = previousBalance;
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
}