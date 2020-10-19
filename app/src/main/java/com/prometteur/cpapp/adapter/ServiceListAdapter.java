package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.prometteur.cpapp.utils.Utils.getResizedDrawable;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.TimeLineViewHolder> {
    Context mContext;
    List<OngoingBean.Service> mDataList;
    OnItemClickListener listener;
    public ServiceListAdapter(Context mContext, List<OngoingBean.Service> mDataList)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;
        this.listener=listener;

    }
    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_service,
                parent,
                false);
        return new TimeLineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, final int position) {
        if(position<4) {
            getResizedDrawable(mContext, R.drawable.service_drawable, holder.tvServiceName, null, null, R.dimen._11sdp);
            holder.tvServiceName.setText(mDataList.get(position).getSrvcName());
        }
    }

    @Override
    public int getItemCount() {
        if(mDataList.size()>4) {
            return 4;
        }else {
            return mDataList.size();
        }
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvServiceName)
        TextView tvServiceName;

        public TimeLineViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}
