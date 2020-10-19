package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.HistoryDataModel;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.beans.TimeLineModel;

import java.util.ArrayList;
import java.util.List;

import static com.prometteur.cpapp.utils.Utils.getStrikeString;

public class HistoryDetailsAdapter extends RecyclerView.Adapter<HistoryDetailsAdapter.TimeLineViewHolder> {
    Context mContext;
    List<OngoingBean.Service> mDataList;
    public HistoryDetailsAdapter(Context mContext, List<OngoingBean.Service> mDataList)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;

    }
    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.appointment_details_row,
                parent,
                false);
        return new TimeLineViewHolder(v,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {
        holder.tvServiceName.setText(mDataList.get(position).getSrvcName());
        if(mDataList.get(position).getBrndName()!=null) {
            holder.tvBrands.setText(mDataList.get(position).getBrndName().toString());
        }else
        {
            holder.tvBrands.setText("-");
            holder.linBrands.setVisibility(View.GONE);
        }
        if(mDataList.get(position).getUserFName()!=null && mDataList.get(position).getUserLName()!=null) {
            holder.tvOperator.setText("Op : " + mDataList.get(position).getUserFName() + " " + mDataList.get(position).getUserLName());
        }else
        {
            holder.tvOperator.setVisibility(View.GONE);
        }
        if(mDataList.get(position).getSrvcDiscountPrice()!=null && !mDataList.get(position).getSrvcDiscountPrice().isEmpty() && !mDataList.get(position).getSrvcDiscountPrice().equalsIgnoreCase("0.00")) {

            holder.tvServiceCostOffer.setVisibility(View.VISIBLE);
            holder.tvServiceCostOffer.setText("₹ " + mDataList.get(position).getSrvcDiscountPrice());
            getStrikeString(holder.tvServiceAmt);
        }else{
            holder.tvServiceCostOffer.setVisibility(View.GONE);
        }
        holder.tvServiceAmt.setText(mDataList.get(position).getSrvcPrice()+"");
        if ((position + 1) < 10) {
            holder.tvSrNo.setText("0" + (position + 1) + ".");
        } else{
            holder.tvSrNo.setText("" + (position + 1) + ".");
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        TextView tvServiceName;
        TextView tvBrands,tvOperator,tvServiceAmt,tvSrNo,tvServiceCostOffer;
        LinearLayout linBrands;
        public TimeLineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            tvServiceName   = itemView.findViewById(R.id.tvService);
            tvBrands        = itemView.findViewById(R.id.tvServiceBrand);
            tvOperator      = itemView.findViewById(R.id.tvServiceOperator);
            tvServiceCostOffer    = itemView.findViewById(R.id.tvServiceCostOffer);
            tvServiceAmt    = itemView.findViewById(R.id.tvServiceCost);
            tvSrNo    = itemView.findViewById(R.id.tvSerialNo);
            linBrands    = itemView.findViewById(R.id.linBrands);

        }
    }


}
