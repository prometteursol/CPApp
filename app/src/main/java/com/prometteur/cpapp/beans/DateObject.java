package com.prometteur.cpapp.beans;

public class DateObject extends ListObject {
        private String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public int getType() {
            return TYPE_DATE;
        }
    }