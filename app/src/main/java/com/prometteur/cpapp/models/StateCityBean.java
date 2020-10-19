package com.prometteur.cpapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class StateCityBean implements Serializable
{

@SerializedName("cities")
@Expose
private List<City> cities = null;
private final static long serialVersionUID = 1028706868844217693L;

public List<City> getCities() {
return cities;
}

public void setCities(List<City> cities) {
this.cities = cities;
}



    public class City implements Serializable
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
        private final static long serialVersionUID = -2238765134252580678L;

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

}