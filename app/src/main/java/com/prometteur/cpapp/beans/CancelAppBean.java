package com.prometteur.cpapp.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CancelAppBean implements Serializable
{

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("result")
@Expose
private List<Boolean> result = null;
private final static long serialVersionUID = -3791889009379969474L;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public CancelAppBean withStatus(Integer status) {
this.status = status;
return this;
}

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

public CancelAppBean withMsg(String msg) {
this.msg = msg;
return this;
}

public List<Boolean> getResult() {
return result;
}

public void setResult(List<Boolean> result) {
this.result = result;
}

public CancelAppBean withResult(List<Boolean> result) {
this.result = result;
return this;
}

}