package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.PerformanceBean;
import com.prometteur.cpapp.beans.TimeLineModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OperatorWeekAdapter extends RecyclerView.Adapter<OperatorWeekAdapter.TimeLineViewHolder> {
    Context mContext;
    ArrayList<PerformanceBean.Operator> mDataList;
    public OperatorWeekAdapter(Context mContext, ArrayList<PerformanceBean.Operator> mDataList)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;

    }
    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.operator_week_row,
                parent,
                false);
        return new TimeLineViewHolder(v,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {
holder.tvName.setText(""+mDataList.get(position).getUserFName()+" "+mDataList.get(position).getUserLName());
holder.tvCost.setText(""+new DecimalFormat("##.##").format(Double.parseDouble(mDataList.get(position).getIncome()))+" /- ");
        Glide.with(holder.itemView).load(mDataList.get(position).getUserImg()).placeholder(R.drawable.placeholder_gray_circle).error(R.drawable.placeholder_gray_circle).into(holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvCost;
        CircleImageView profileImage;

        public TimeLineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCost = itemView.findViewById(R.id.tvCost);
            profileImage = itemView.findViewById(R.id.profileImage);


        }
    }
}
