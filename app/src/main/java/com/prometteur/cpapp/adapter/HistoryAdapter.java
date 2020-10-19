package com.prometteur.cpapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.vipulasri.timelineview.TimelineView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.DateObject;
import com.prometteur.cpapp.beans.HistoryDataModelObject;
import com.prometteur.cpapp.beans.ListObject;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.dialogs.OtpStartEndDialogActivity;
import com.prometteur.cpapp.history.HistoryActivity;
import com.prometteur.cpapp.history.HistoryDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.prometteur.cpapp.utils.Utils.getResizedDrawable;
import static com.prometteur.cpapp.utils.Utils.getTimeShow24to12HR;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<ListObject> mDataList;
    Activity activity;
    public HistoryAdapter(Context mContext, ArrayList<ListObject> mDataList)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;
        //this.activity=activity;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ListObject.TYPE_GENERAL:
                View currentUserView = inflater.inflate(R.layout.todays_row, parent, false);
                viewHolder = new HistoryViewHolder(currentUserView); // view holder for normal items
                break;

            case ListObject.TYPE_DATE:
                View v2 = inflater.inflate(R.layout.date_row, parent, false);
                viewHolder = new DateViewHolder(v2);
                break;
        }

        return viewHolder;
    }

    public void setDataChange(List<ListObject> asList) {
        this.mDataList = asList;
        //now, tell the adapter about the update
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case ListObject.TYPE_GENERAL:
                final HistoryDataModelObject generalItem= (HistoryDataModelObject) mDataList.get(position);
                HistoryViewHolder viewHolder= (HistoryViewHolder) holder;
                viewHolder.tvName.setText(generalItem.getChatModel().getUserFName()+" "+generalItem.getChatModel().getUserLName());
                viewHolder.tvTime.setText(getTimeShow24to12HR(generalItem.getChatModel().getAptTime()));
                viewHolder.tvAppNo.setText(generalItem.getChatModel().getAptId());
                Glide.with(holder.itemView).load(generalItem.getChatModel().getUserImg()).placeholder(R.drawable.placeholder_gray_circle).error(R.drawable.placeholder_gray_circle).into(viewHolder.civProfileImage);
                getResizedDrawable(mContext,R.drawable.ic_time,viewHolder.tvTime,null,null,R.dimen._11sdp);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, HistoryDetailsActivity.class).putExtra("pageType","history").putExtra("appId",generalItem.getChatModel().getAptId()));

                    }
                });
                viewHolder.tvAppointmentStatus.setVisibility(View.VISIBLE);
                if(generalItem.getChatModel().getAptStatus().equalsIgnoreCase("0")) {
                    viewHolder.tvAppointmentStatus.setText("Pending");
                    viewHolder.tvAppointmentStatus.setTextColor(mContext.getResources().getColor(R.color.skyBlueDark));
                }else
                if(generalItem.getChatModel().getAptStatus().equalsIgnoreCase("1")) {
                    viewHolder.tvAppointmentStatus.setText("Accepted");
                    viewHolder.tvAppointmentStatus.setTextColor(mContext.getResources().getColor(R.color.skyBlueDark));

                }else if(generalItem.getChatModel().getAptStatus().equalsIgnoreCase("5")) {

                    viewHolder.tvAppointmentStatus.setText("Ongoing");
                    viewHolder.tvAppointmentStatus.setTextColor(mContext.getResources().getColor(R.color.skyBlueDark));
                }else if(generalItem.getChatModel().getAptStatus().equalsIgnoreCase("6")) {
                    viewHolder.tvAppointmentStatus.setText("Finished");
                    viewHolder.tvAppointmentStatus.setTextColor(mContext.getResources().getColor(R.color.skyBlueDark));

                }else if(generalItem.getChatModel().getAptStatus().equalsIgnoreCase("4")) {
                    viewHolder.tvAppointmentStatus.setText("Completed");
                    viewHolder.tvAppointmentStatus.setTextColor(mContext.getResources().getColor(R.color.skyBlueDark));
                }else if(generalItem.getChatModel().getAptStatus().equalsIgnoreCase("2") || generalItem.getChatModel().getAptStatus().equalsIgnoreCase("7")|| generalItem.getChatModel().getAptStatus().equalsIgnoreCase("8") || generalItem.getChatModel().getAptStatus().equalsIgnoreCase("9")) {
                    viewHolder.tvAppointmentStatus.setTextColor(mContext.getResources().getColor(R.color.red));
                    if(generalItem.getChatModel().getAptStatus().equalsIgnoreCase("2")) {
                        viewHolder.tvAppointmentStatus.setText("Declined");
                    }else if(generalItem.getChatModel().getAptStatus().equalsIgnoreCase("7")) {
                        viewHolder.tvAppointmentStatus.setText("Cancelled");
                    }else if(generalItem.getChatModel().getAptStatus().equalsIgnoreCase("8")) {
                        viewHolder.tvAppointmentStatus.setText("Unattended");
                    }else if(generalItem.getChatModel().getAptStatus().equalsIgnoreCase("9")) {
                        viewHolder.tvAppointmentStatus.setText("No Show");
                    }


                }else if(generalItem.getChatModel().getAptStatus().equalsIgnoreCase("3"))
                {
                    viewHolder.tvAppointmentStatus.setText("Rescheduled");
                    viewHolder.tvAppointmentStatus.setTextColor(mContext.getResources().getColor(R.color.skyBlueDark));
                }
                break;

            case ListObject.TYPE_DATE:

                DateObject dateItem = (DateObject) mDataList.get(position);
                DateViewHolder viewHolder1 =(DateViewHolder) holder;
                viewHolder1.bind(dateItem.getDate());
                break;
        }


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getType();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvTime)
        TextView tvTime;
        TextView tvName;
        TextView tvAppNo;
        TextView tvAppointmentStatus;
        CircleImageView civProfileImage;
        RecyclerView rvServices;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAppNo = itemView.findViewById(R.id.tvAppNo);
            rvServices = itemView.findViewById(R.id.rvServices);
            tvAppointmentStatus = itemView.findViewById(R.id.tvAppointmentStatus);
            civProfileImage = itemView.findViewById(R.id.civProfileImage);

            /*tvName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tvName.setMarqueeRepeatLimit(-1);
            tvName.setSingleLine(true);
            tvName.setSelected(true);*/


        }
    }



    public class DateViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        public DateViewHolder(View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.tvDate);
            //TODO initialize your xml views
        }

        public void bind(final String date) {
            //TODO set data to xml view via textivew.setText();
            tvDate.setText(""+date);
        }
    }


}
