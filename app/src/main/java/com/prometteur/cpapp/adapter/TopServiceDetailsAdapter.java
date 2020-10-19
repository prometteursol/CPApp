package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.ProfileBean;
import com.prometteur.cpapp.beans.RateCardBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.prometteur.cpapp.utils.Utils.getResizedDrawable;
import static com.prometteur.cpapp.utils.Utils.getStrikeString;

public class TopServiceDetailsAdapter extends RecyclerView.Adapter<TopServiceDetailsAdapter.TimeLineViewHolder> {
    Context mContext;
    List<ProfileBean.Service> mDataList;

    public TopServiceDetailsAdapter(Context mContext, List mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
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

            holder.tvName.setText(mDataList.get(position).getSrvcName());

        if(!mDataList.get(position).getSrvcDiscountPrice().isEmpty() && !mDataList.get(position).getSrvcDiscountPrice().equalsIgnoreCase("0.00")) {
            holder.tvMainPrice.setText("₹ " + mDataList.get(position).getSrvcPrice());
            holder.tvOfferPrice.setText("₹ " + mDataList.get(position).getSrvcDiscountPrice());
        }else
        {
            holder.tvOfferPrice.setText("₹ " + mDataList.get(position).getSrvcPrice());
            holder.tvMainPrice.setVisibility(View.GONE);
        }
            holder.tvTime.setText( mDataList.get(position).getSrvcEstimateTime()+" Min");
            if(mDataList.get(position).getBrndName()!=null) {
                holder.tvBrand.setText(mDataList.get(position).getBrndName().toString());
            }else {
                holder.tvBrand.setText("-");
                holder.tvServiceBrandtitle.setVisibility(View.GONE);
                holder.tvBrand.setVisibility(View.GONE);
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
        @Bind(R.id.tvServiceBrandtitle)
        TextView tvServiceBrandtitle;
        @Bind(R.id.tvMainPrice)
        TextView tvMainPrice;
        @Bind(R.id.tvOfferPrice)
        TextView tvOfferPrice;
        @Bind(R.id.tvTime)
        TextView tvTime;

        public TimeLineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvName = itemView.findViewById(R.id.tvServiceName);
            tvBrand = itemView.findViewById(R.id.tvBrand);


        }
    }


}
