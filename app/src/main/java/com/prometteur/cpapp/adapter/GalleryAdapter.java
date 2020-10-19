package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.github.vipulasri.timelineview.TimelineView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.profile.ImageEnlargeActivity;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.TimeLineViewHolder> {
    Context mContext;
    List<String> mDataList;
    public GalleryAdapter(Context mContext, List<String> mDataList)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;

    }
    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.gallery_row,
                parent,
                false);
        return new TimeLineViewHolder(v,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, final int position) {
        Glide.with(mContext).load(mDataList.get(position)).placeholder(R.drawable.placeholder_gray_corner).error(R.drawable.placeholder_gray_corner).into(holder.profileImage);
        holder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent largeVP = new Intent(mContext, ImageEnlargeActivity.class);
                bundle.putInt("pager_position", position);
                ImageEnlargeActivity.GalaryPhotos = mDataList;
                largeVP.putExtras(bundle);
                mContext.startActivity(largeVP);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView profileImage;

        public TimeLineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);


        }
    }
}
