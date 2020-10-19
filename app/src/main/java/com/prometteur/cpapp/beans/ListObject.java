package com.prometteur.cpapp.beans;

public abstract class ListObject {
        public static final int TYPE_DATE = 0;
        public static final int TYPE_GENERAL = 1;

        abstract public int getType();
    }