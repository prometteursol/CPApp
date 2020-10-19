package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.ProfileBean;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.listener.OnItemClickListener;
import com.prometteur.cpapp.listener.OnTopServiceItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class TopServicesAdapter extends RecyclerView.Adapter<TopServicesAdapter.TimeLineViewHolder> {
    Context mContext;
    List<ProfileBean.Topservice> mDataList;
    private final OnTopServiceItemClickListener listener;
    ArrayList<Integer> dynamicColorList;
    public TopServicesAdapter(Context mContext, List<ProfileBean.Topservice> mDataList, OnTopServiceItemClickListener listener, ArrayList<Integer> dynamicColorList)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;
        this.listener=listener;
        this.dynamicColorList=dynamicColorList;

    }
    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.top_services_row,
                parent,
                false);
        return new TimeLineViewHolder(v,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, final int position) {
        holder.tvServices.setText(mDataList.get(position).getSrvcCategory());

        if(mDataList.get(position).getTypes().equalsIgnoreCase("1"))
        {
            holder.tvTypes.setText(mDataList.get(position).getTypes()+" Type");
        }else
        {
            holder.tvTypes.setText(mDataList.get(position).getTypes()+" Types");
        }


       holder.cvService.setCardBackgroundColor(mContext.getResources().getColor(dynamicColorList.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(mDataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        CardView cvService;
        TextView tvServices;
        TextView tvTypes;
        public TimeLineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            tvServices = itemView.findViewById(R.id.tvServices);
            tvTypes = itemView.findViewById(R.id.tvTypes);
            cvService = itemView.findViewById(R.id.cvService);


        }
    }


}
