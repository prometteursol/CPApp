package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.RateCardBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.prometteur.cpapp.utils.Utils.getResizedDrawable;
import static com.prometteur.cpapp.utils.Utils.getStrikeString;

public class RateCardAdapter extends RecyclerView.Adapter<RateCardAdapter.TimeLineViewHolder> {
    Context mContext;
    List mDataList;
    List<RateCardBean.Hair> mDataListHair;
    List<RateCardBean.Skin> mDataListSkin;
    List<RateCardBean.Spa> mDataListSpa;
    List<RateCardBean.Nail> mDataListNail;
    List<RateCardBean.Makeup> mDataListMakeup;
    int pos;

    public RateCardAdapter(Context mContext, List mDataList, int pos) {
        this.mContext = mContext;
        this.mDataList = mDataList;
        this.mDataListHair = mDataList;
        this.mDataListSkin = mDataList;
        this.mDataListSpa = mDataList;
        this.mDataListNail = mDataList;
        this.mDataListMakeup = mDataList;
        this.pos = pos;

    }

    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.rate_card_row,
                parent,
                false);
        return new TimeLineViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {
        if (pos == 1) {
            holder.tvName.setText(mDataListHair.get(position).getSrvcName());
            if(!mDataListHair.get(position).getSrvcDiscountPrice().isEmpty() && !mDataListHair.get(position).getSrvcDiscountPrice().equalsIgnoreCase("0.00")) {
                holder.tvMainPrice.setText("₹ " + mDataListHair.get(position).getSrvcPrice());
                holder.tvOfferPrice.setText("₹ " + mDataListHair.get(position).getSrvcDiscountPrice());
            }else
            {
                holder.tvOfferPrice.setText("₹ " + mDataListHair.get(position).getSrvcPrice());
                holder.tvMainPrice.setVisibility(View.GONE);

            }

            holder.tvTime.setText( mDataListHair.get(position).getSrvcEstimateTime()+" Min");
            if(mDataListHair.get(position).getBrndName()!=null) {
                holder.tvBrand.setText(mDataListHair.get(position).getBrndName().toString());
            }else {
                holder.tvBrand.setVisibility(View.INVISIBLE);
                holder.tvServiceBrandtitle.setVisibility(View.INVISIBLE);
                holder.tvBrand.setText("-");
            }
        } else if (pos == 2) {
            holder.tvName.setText(mDataListSkin.get(position).getSrvcName());
            if(!mDataListSkin.get(position).getSrvcDiscountPrice().isEmpty() && !mDataListSkin.get(position).getSrvcDiscountPrice().equalsIgnoreCase("0.00")) {
                holder.tvMainPrice.setText("₹ " + mDataListSkin.get(position).getSrvcPrice());
                holder.tvOfferPrice.setText("₹ " + mDataListSkin.get(position).getSrvcDiscountPrice());
            }else
            {
                holder.tvOfferPrice.setText("₹ " + mDataListSkin.get(position).getSrvcPrice());
                holder.tvMainPrice.setVisibility(View.GONE);

            }
            holder.tvTime.setText( mDataListSkin.get(position).getSrvcEstimateTime()+" Min");
            if(mDataListSkin.get(position).getBrndName()!=null) {
                holder.tvBrand.setText(mDataListSkin.get(position).getBrndName().toString());
            }else {
                holder.tvBrand.setVisibility(View.INVISIBLE);
                holder.tvServiceBrandtitle.setVisibility(View.INVISIBLE);
                holder.tvBrand.setText("-");
            }
        } else if (pos == 3) {
            holder.tvName.setText(mDataListSpa.get(position).getSrvcName());
            if(!mDataListSpa.get(position).getSrvcDiscountPrice().isEmpty() && !mDataListSpa.get(position).getSrvcDiscountPrice().equalsIgnoreCase("0.00")) {
                holder.tvMainPrice.setText("₹ " + mDataListSpa.get(position).getSrvcPrice());
                holder.tvOfferPrice.setText("₹ " + mDataListSpa.get(position).getSrvcDiscountPrice());
            }else
            {
                holder.tvOfferPrice.setText("₹ " + mDataListSpa.get(position).getSrvcPrice());
                holder.tvMainPrice.setVisibility(View.GONE);

            }
            holder.tvTime.setText( mDataListSpa.get(position).getSrvcEstimateTime()+" Min");
            if(mDataListSpa.get(position).getBrndName()!=null) {
                holder.tvBrand.setText(mDataListSpa.get(position).getBrndName().toString());
            }else {
                holder.tvBrand.setText("-");
                holder.tvBrand.setVisibility(View.INVISIBLE);
                holder.tvServiceBrandtitle.setVisibility(View.INVISIBLE);
            }
        } else if (pos == 4) {
            holder.tvName.setText(mDataListNail.get(position).getSrvcName());
            if(!mDataListNail.get(position).getSrvcDiscountPrice().isEmpty() && !mDataListNail.get(position).getSrvcDiscountPrice().equalsIgnoreCase("0.00")) {
                holder.tvMainPrice.setText("₹ " + mDataListNail.get(position).getSrvcPrice());
                holder.tvOfferPrice.setText("₹ " + mDataListNail.get(position).getSrvcDiscountPrice());
            }else
            {
                holder.tvOfferPrice.setText("₹ " + mDataListNail.get(position).getSrvcPrice());
                holder.tvMainPrice.setVisibility(View.GONE);

            }
            holder.tvTime.setText( mDataListNail.get(position).getSrvcEstimateTime()+" Min");
            if(mDataListNail.get(position).getBrndName()!=null) {
                holder.tvBrand.setText(mDataListNail.get(position).getBrndName().toString());
            }else {
                holder.tvBrand.setText("-");
                holder.tvBrand.setVisibility(View.INVISIBLE);
                holder.tvServiceBrandtitle.setVisibility(View.INVISIBLE);
            }
        } else if (pos == 5) {
            holder.tvName.setText(mDataListMakeup.get(position).getSrvcName());
            if(!mDataListMakeup.get(position).getSrvcDiscountPrice().isEmpty() && !mDataListMakeup.get(position).getSrvcDiscountPrice().equalsIgnoreCase("0.00")) {
                holder.tvMainPrice.setText("₹ " + mDataListMakeup.get(position).getSrvcPrice());
                holder.tvOfferPrice.setText("₹ " + mDataListMakeup.get(position).getSrvcDiscountPrice());
            }else
            {
                holder.tvOfferPrice.setText("₹ " + mDataListMakeup.get(position).getSrvcPrice());
                holder.tvMainPrice.setVisibility(View.GONE);
            }
            holder.tvTime.setText( mDataListMakeup.get(position).getSrvcEstimateTime()+" Min");
            if(mDataListMakeup.get(position).getBrndName()!=null) {
                holder.tvBrand.setText(mDataListMakeup.get(position).getBrndName().toString());
            }else {
                holder.tvBrand.setText("-");
                holder.tvBrand.setVisibility(View.INVISIBLE);
                holder.tvServiceBrandtitle.setVisibility(View.INVISIBLE);
            }
        }


        getStrikeString(holder.tvMainPrice);
        getResizedDrawable(mContext, R.drawable.ic_time, holder.tvTime, null, null, R.dimen._11sdp);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvBrand;
        @Bind(R.id.tvMainPrice)
        TextView tvMainPrice;
        @Bind(R.id.tvOfferPrice)
        TextView tvOfferPrice;
        @Bind(R.id.tvTime)
        TextView tvTime;
        @Bind(R.id.tvServiceBrandtitle)
        TextView tvServiceBrandtitle;

        public TimeLineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvName = itemView.findViewById(R.id.tvServiceName);
            tvBrand = itemView.findViewById(R.id.tvBrand);


        }
    }


}
