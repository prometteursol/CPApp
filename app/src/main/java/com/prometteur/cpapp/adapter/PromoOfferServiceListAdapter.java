package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.ComboListBean;
import com.prometteur.cpapp.beans.ProfileBean;
import com.prometteur.cpapp.beans.PromoOfferBean;
import com.prometteur.cpapp.listener.OnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.prometteur.cpapp.utils.Utils.getResizedDrawable;

public class PromoOfferServiceListAdapter extends RecyclerView.Adapter<PromoOfferServiceListAdapter.TimeLineViewHolder> {
    Context mContext;
    List<PromoOfferBean.Service> mDataList;
    OnItemClickListener listener;
    public PromoOfferServiceListAdapter(Context mContext, List<PromoOfferBean.Service> mDataList)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;
        this.listener=listener;

    }
    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.service_rate_card_row,
                parent,
                false);
        return new TimeLineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, final int position) {
        getResizedDrawable(mContext,R.drawable.ic_tick_service,holder.tvServiceName,null,null,R.dimen._11sdp);
        holder.tvServiceName.setText(mDataList.get(position).getSrvcName());
        if(mDataList.get(position).getBrndName()!=null) {
            holder.tvBrand.setText(mDataList.get(position).getBrndName().toString());
        }else
        {
            holder.tvBrand.setText("-");
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvServiceName)
        TextView tvServiceName;
        @Bind(R.id.tvBrand)
        TextView tvBrand;

        public TimeLineViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}
