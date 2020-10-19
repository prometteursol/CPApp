package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.ComboListBean;
import com.prometteur.cpapp.beans.PromoOfferBean;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.dialogs.ComboListDialogActivity;
import com.prometteur.cpapp.dialogs.PromoOfferListDialogActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.prometteur.cpapp.utils.DateParser.convertDateToString;
import static com.prometteur.cpapp.utils.Utils.getStrikeString;

public class PromoListAdapter extends RecyclerView.Adapter<PromoListAdapter.TimeLineViewHolder> {
    Context mContext;
    List<PromoOfferBean.Result> mDataList;
    public PromoListAdapter(Context mContext, List<PromoOfferBean.Result> mDataList)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;

    }
    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.promo_offers_row,
                parent,
                false);
        return new TimeLineViewHolder(v,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, final int position) {
        holder.tvServiceName.setText(mDataList.get(position).getProofferName());
        holder.tvOfferPrice.setText("₹ " + mDataList.get(position).getProofferDiscountPrice());
        if(!mDataList.get(position).getProofferPrice().isEmpty()) {
            holder.tvMainPrice.setText("₹ " + mDataList.get(position).getProofferPrice());
        }else
        {
            holder.tvMainPrice.setVisibility(View.GONE);
        }

        holder.tvStartDate.setText(convertDateToString(mDataList.get(position).getProofferStartDate()));
        holder.tvEndDate.setText(convertDateToString(mDataList.get(position).getProofferEndDate()));

        List<PromoOfferBean.Service> services=mDataList.get(position).getServices();
        if(services.size()==1)
        {
            if(services.get(0).getBrndName()!=null)
            {
                holder.tvServiceOneBrand.setText(services.get(0).getSrvcName()+" brand : "+services.get(0).getBrndName());
            }else
            {
                holder.tvServiceOneBrand.setText(services.get(0).getSrvcName());
            }
            holder.tvServiceSecondBrand.setVisibility(View.GONE);
        }else if(services.size()==2)
        {
            if(services.get(0).getBrndName()!=null)
            {
                holder.tvServiceOneBrand.setText(services.get(0).getSrvcName()+" brand : "+services.get(0).getBrndName());
            }else
            {
                holder.tvServiceOneBrand.setText(services.get(0).getSrvcName());
            }
            if(services.get(1).getBrndName()!=null)
            {
                holder.tvServiceSecondBrand.setText(services.get(1).getSrvcName()+" brand : "+services.get(1).getBrndName());
            }else
            {
                holder.tvServiceSecondBrand.setText(services.get(1).getSrvcName());
            }
        }else if(services.size()>2)
        {
            if(services.get(0).getBrndName()!=null)
            {
                holder.tvServiceOneBrand.setText(services.get(0).getSrvcName()+" brand : "+services.get(0).getBrndName());
            }else
            {
                holder.tvServiceOneBrand.setText(services.get(0).getSrvcName());
            }
            if(services.get(1).getBrndName()!=null)
            {
                holder.tvServiceSecondBrand.setText(services.get(1).getSrvcName()+" brand : "+services.get(1).getBrndName()+"...");
            }else
            {
                holder.tvServiceSecondBrand.setText(services.get(1).getSrvcName()+"...");
            }
        }
        getStrikeString(holder.tvMainPrice);
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, PromoOfferListDialogActivity.class).putExtra("objOffer",mDataList.get(position)));
            }
        });*/

        getStrikeString(holder.tvMainPrice);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvServiceName)
        TextView tvServiceName;
        @Bind(R.id.tvServiceOneBrand)
        TextView tvServiceOneBrand;
        @Bind(R.id.tvServiceSecondBrand)
        TextView tvServiceSecondBrand;
        @Bind(R.id.tvOfferPrice)
        TextView tvOfferPrice;
        @Bind(R.id.tvMainPrice)
        TextView tvMainPrice;
        @Bind(R.id.tvStartDate)
        TextView tvStartDate;
        @Bind(R.id.tvEndDate)
        TextView tvEndDate;
        public TimeLineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}
