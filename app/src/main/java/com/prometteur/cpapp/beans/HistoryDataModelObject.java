package com.prometteur.cpapp.beans;

public class HistoryDataModelObject extends ListObject {

        private OngoingBean.Result chatModel;

        public OngoingBean.Result getChatModel() {
            return chatModel;
        }

        public void setChatModel(OngoingBean.Result chatModel) {
            this.chatModel = chatModel;
        }

        @Override
        public int getType() {

                return TYPE_GENERAL;

        }
    }