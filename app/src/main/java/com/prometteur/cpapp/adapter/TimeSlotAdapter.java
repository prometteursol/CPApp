package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.listener.OnTimeslotItemClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.prometteur.cpapp.utils.Utils.getTimeShow24to12HR;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeLineViewHolder> {
    Context mContext;
    List<String> mDataList;
    OnTimeslotItemClickListener listener;
    int index=-1;
    String appTime;
    public TimeSlotAdapter(Context mContext, List<String> mDataList,OnTimeslotItemClickListener listener,String appTime)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;
        this.listener=listener;
        this.appTime=appTime;

    }
    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.time_slot_row,
                parent,
                false);
        return new TimeLineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, final int position) {
        holder.tvSlotTime.setText(mDataList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index=position;
                appTime="0";
                listener.onItemClick(mDataList.get(position));
                notifyDataSetChanged();
            }
        });
        if(!appTime.equalsIgnoreCase("0")) {
            if (getTimeShow24to12HR(appTime).equalsIgnoreCase(mDataList.get(position))) {
                index = position;
                listener.onItemClick(mDataList.get(position));
            }
        }
        if(index==position){
            holder.tvSlotTime.setBackground(mContext.getResources().getDrawable(R.drawable.row_rounded_time_slot_background_light_blue));
            holder.tvSlotTime.setTextColor(mContext.getResources().getColor(R.color.skyBlueLight));
        }else{
            holder.tvSlotTime.setBackground(mContext.getResources().getDrawable(R.drawable.row_rounded_time_slot_background_white));
            holder.tvSlotTime.setTextColor(mContext.getResources().getColor(R.color.grey));

        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        TextView tvSlotTime;
        public TimeLineViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSlotTime = itemView.findViewById(R.id.tvSlotTime);

        }
    }



}
