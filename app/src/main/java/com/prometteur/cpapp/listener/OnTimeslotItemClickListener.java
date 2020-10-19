package com.prometteur.cpapp.listener;

import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.beans.TimeLineModel;

public interface OnTimeslotItemClickListener {
    void onItemClick(String item);
    void onOperatorClick(String opId);
}