package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.ReschduleGetDataBean;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.listener.OnTimeslotItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class OperatorAdapter extends RecyclerView.Adapter<OperatorAdapter.TimeLineViewHolder> {
    Context mContext;
    List<ReschduleGetDataBean.Operator> mDataList;
    List<String> opIdList=new ArrayList<>();
    int index=-1;
    OnTimeslotItemClickListener listener;
    String optId;
    public OperatorAdapter(Context mContext, List<ReschduleGetDataBean.Operator> mDataList,OnTimeslotItemClickListener listener,String optId)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;
        this.listener=listener;
        this.optId=optId;
        if(!optId.isEmpty()) {
            for (int i = 0; i < optId.split(",").length; i++) {
                opIdList.add(optId.split(",")[i]);
            }
        }

    }
    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.operator_row,
                parent,
                false);
        return new TimeLineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, final int position) {
        holder.tvName.setText(mDataList.get(position).getUserFName()+" "+mDataList.get(position).getUserLName());
       /* holder.tvAppNo.setText("App No. "+(position+1));*/
        if(mDataList.get(position).getSelected().equalsIgnoreCase("selected"))
        {
            index=position;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index=position;
                listener.onOperatorClick(mDataList.get(position).getUserId());
                if(!mDataList.get(position).getSelected().equalsIgnoreCase("selected"))
                {
                    mDataList.get(position).setSelected("selected");
                }else
                {
                    mDataList.get(position).setSelected("");
                }
                notifyDataSetChanged();
            }
        });
        if(index==position){
            holder.profileImage.setBorderColor(mContext.getResources().getColor(R.color.skyBlueLight));
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.skyBlueLight));
        }else{
            if(opIdList.contains(mDataList.get(position).getUserId())){
                holder.profileImage.setBorderColor(mContext.getResources().getColor(R.color.skyBlueLight));
                holder.tvName.setTextColor(mContext.getResources().getColor(R.color.skyBlueLight));
            }else {
                holder.profileImage.setBorderColor(mContext.getResources().getColor(R.color.black));
                holder.tvName.setTextColor(mContext.getResources().getColor(R.color.black));
            }
        }
    }



    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvAppNo;
        @Bind(R.id.profileImage)
        CircleImageView profileImage;
        public TimeLineViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAppNo = itemView.findViewById(R.id.tvAppNo);


            tvName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tvName.setMarqueeRepeatLimit(-1);
            tvName.setSingleLine(true);
            tvName.setSelected(true);

        }
    }



}
