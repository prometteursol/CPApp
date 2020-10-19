package com.prometteur.cpapp.listener;

import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.beans.TimeLineModel;

public interface OnItemClickListener {
    void onItemClick(TimeLineModel item);
    void onItemClick(OngoingBean.Result item);
}