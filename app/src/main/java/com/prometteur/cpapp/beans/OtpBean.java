package com.prometteur.cpapp.beans;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpBean implements Serializable
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
private final static long serialVersionUID = -6435067170697214971L;

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
    public class Result implements Serializable
    {

        @SerializedName("otp")
        @Expose
        private Integer otp;
        private final static long serialVersionUID = -2041526709218802566L;

        public Integer getOtp() {
            return otp;
        }

        public void setOtp(Integer otp) {
            this.otp = otp;
        }

    }
}