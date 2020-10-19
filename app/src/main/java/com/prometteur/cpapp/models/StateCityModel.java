package com.prometteur.cpapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StateCityModel implements Serializable
{

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("state")
@Expose
private String state;
private final static long serialVersionUID = -7897966048572925759L;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getState() {
return state;
}

public void setState(String state) {
this.state = state;
}

}