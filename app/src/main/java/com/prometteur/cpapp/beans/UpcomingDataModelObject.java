package com.prometteur.cpapp.beans;

public class UpcomingDataModelObject extends ListObject {

        private OngoingBean.Result timeLineModel;

    public OngoingBean.Result getTimeLineModel() {
        return timeLineModel;
    }

    public void setTimeLineModel(OngoingBean.Result timeLineModel) {
        this.timeLineModel = timeLineModel;
    }

    @Override
        public int getType() {

                return TYPE_GENERAL;

        }
    }