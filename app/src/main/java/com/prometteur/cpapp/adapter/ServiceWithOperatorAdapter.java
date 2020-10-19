package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.utils.TextViewCustomFont;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.prometteur.cpapp.utils.Utils.getResizedDrawable;
import static com.prometteur.cpapp.utils.Utils.getStrikeString;

public class ServiceWithOperatorAdapter extends RecyclerView.Adapter<ServiceWithOperatorAdapter.TimeLineViewHolder> {
    Context mContext;
    List<OngoingBean.Service> mDataList;
    public ServiceWithOperatorAdapter(Context mContext, List<OngoingBean.Service> mDataList)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;

    }
    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(mContext).inflate(R.layout.service_operator_row,
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycle_services_selected,
                parent,
                false);
        return new TimeLineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {
        if ((position + 1) < 10) {
            holder.tvSerialNo.setText("0" + (position + 1) + ".");
        } else{
            holder.tvSerialNo.setText("" + (position + 1) + ".");
        }
        holder.tvService.setText(mDataList.get(position).getSrvcName());
        if(mDataList.get(position).getBrndName()!=null) {
            holder.tvServiceBrand.setText("" + mDataList.get(position).getBrndName());
        }else
        {
            holder.tvServiceBrandtitle.setVisibility(View.GONE);
            holder.tvServiceBrand.setVisibility(View.GONE);
        }


        if(mDataList.get(position).getSrvcDiscountPrice()!=null && !mDataList.get(position).getSrvcDiscountPrice().isEmpty() && !mDataList.get(position).getSrvcDiscountPrice().equalsIgnoreCase("0.00")) {

            holder.tvServiceCostOffer.setVisibility(View.VISIBLE);
            holder.tvServiceCostOffer.setText("₹ " + mDataList.get(position).getSrvcDiscountPrice());
            getStrikeString(holder.tvServiceCost);
        }else{
            holder.tvServiceCostOffer.setVisibility(View.GONE);
        }
        holder.tvServiceCost.setText("₹ " +mDataList.get(position).getSrvcPrice());

        if(mDataList.get(position).getUserFName()!=null && mDataList.get(position).getUserLName()!=null) {
            holder.tvServiceOperator.setText("" + mDataList.get(position).getUserFName() + " " + mDataList.get(position).getUserLName());
        }else
        {
            holder.tvServiceOperator.setVisibility(View.GONE);
            holder.tvServiceOperatorTitle.setVisibility(View.GONE);
        }
        //mDataList.get(position).getSrvcPrice()
        //getResizedDrawable(mContext,R.drawable.ic_tick_service,holder.tvServiceName,null,null,R.dimen._11sdp);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        /*@Bind(R.id.tvServiceName)
        TextView tvServiceName;
        @Bind(R.id.tvBrand)
        TextView tvBrand;
        @Bind(R.id.tvOpName)
        TextView tvOpName;
        @Bind(R.id.linBrands)
        LinearLayout linBrands;
        @Bind(R.id.linOperator)
        LinearLayout linOperator;
        public TimeLineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this,itemView);


        }*/

        TextViewCustomFont tvSerialNo;
        TextViewCustomFont tvService;
        TextViewCustomFont tvServiceBrand;
        TextViewCustomFont tvServiceOperator;
        //  TextViewCustomFont tvAddedOperatorName;
        TextViewCustomFont tvServiceCost;
        TextViewCustomFont tvServiceCostOffer;
        TextViewCustomFont tvServiceBrandtitle;
        TextViewCustomFont tvServiceOperatorTitle;

        public TimeLineViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSerialNo = itemView.findViewById(R.id.tvSerialNo);
            tvService = itemView.findViewById(R.id.tvService);
            tvServiceBrand = itemView.findViewById(R.id.tvServiceBrand);
            tvServiceOperator = itemView.findViewById(R.id.tvServiceOperator);
            //tvAddedOperatorName = itemView.findViewById(R.id.tvAddedOperatorName);
            tvServiceCost = itemView.findViewById(R.id.tvServiceCost);
            tvServiceCostOffer = itemView.findViewById(R.id.tvServiceCostOffer);
            tvServiceBrandtitle = itemView.findViewById(R.id.tvServiceBrandtitle);
            tvServiceOperatorTitle = itemView.findViewById(R.id.tvServiceOperatorTitle);
        }
    }



}
