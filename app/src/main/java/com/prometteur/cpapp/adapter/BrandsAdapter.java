package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.BrandListBean;
import com.prometteur.cpapp.beans.HistoryDataModel;
import com.prometteur.cpapp.beans.RateCardBean;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.TimeLineViewHolder> {
    Context mContext;
    List mDataList;
    List<BrandListBean.Hair> mDataListHair;
    List<BrandListBean.Skin> mDataListSkin;
    List<BrandListBean.Spa> mDataListSpa;
    List<BrandListBean.Nail> mDataListNail;
    List<BrandListBean.Makeup> mDataListMakeup;
    int pos;
    public BrandsAdapter(Context mContext, List mDataList,int pos)
    {
        this.mContext=mContext;
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
        View v = LayoutInflater.from(mContext).inflate(R.layout.brand_row,
                parent,
                false);
        return new TimeLineViewHolder(v,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {
        final String[] url = {""};
        if (pos == 1) {
            Glide.with(mContext).load(mDataListHair.get(position).getBrndImg()).override(500,500).into(holder.ivBrandImg);
            url[0] =mDataListHair.get(position).getBrnd_url();
        } else if (pos == 2) {
            Glide.with(mContext).load(mDataListSkin.get(position).getBrndImg()).override(500,500).into(holder.ivBrandImg);
            url[0] =mDataListSkin.get(position).getBrnd_url();
        } else if (pos == 3) {
            Glide.with(mContext).load(mDataListSpa.get(position).getBrndImg()).override(500,500).into(holder.ivBrandImg);
            url[0] =mDataListSpa.get(position).getBrnd_url();
        } else if (pos == 4) {
            Glide.with(mContext).load(mDataListNail.get(position).getBrndImg()).override(500,500).into(holder.ivBrandImg);
            url[0] =mDataListNail.get(position).getBrnd_url();
        } else if (pos == 5) {
            Glide.with(mContext).load(mDataListMakeup.get(position).getBrndImg()).override(500,500).into(holder.ivBrandImg);
            url[0] =mDataListMakeup.get(position).getBrnd_url();
        }
        holder.ivBrandImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!url[0].startsWith("http://") && !url[0].startsWith("https://"))
                    url[0] = "http://" + url[0];
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url[0]));
                mContext.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {


        RoundedImageView ivBrandImg;

        public TimeLineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            ivBrandImg=itemView.findViewById(R.id.ivBrandImg);

        }
    }
}
