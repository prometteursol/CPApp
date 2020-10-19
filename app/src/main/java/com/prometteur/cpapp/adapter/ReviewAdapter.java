package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.ProfileBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.prometteur.cpapp.utils.Utils.getReviewDate;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.TimeLineViewHolder> {
    Context mContext;
    List<ProfileBean.Review> mDataList;

    public ReviewAdapter(Context mContext, List<ProfileBean.Review> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;

    }

    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.review_profile_row,
                parent,
                false);
        return new TimeLineViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {
        holder.tvName.setText(mDataList.get(position).getUserFName()+" "+mDataList.get(position).getUserLName());
        holder.tvDuration.setText(getReviewDate(mContext,mDataList.get(position).getRevCreateDate()));
        if(!mDataList.get(position).getRevReview().isEmpty()) {
            holder.tvReview.setText(mDataList.get(position).getRevReview());
        }else
        {
            holder.tvReview.setVisibility(View.GONE);
        }
        holder.rbRatings.setRating(Float.parseFloat(mDataList.get(position).getRevRating()));

        if(mDataList.get(position).getUserImg()!=null && !mDataList.get(position).getUserImg().isEmpty()) {
            Glide.with(mContext).load(mDataList.get(position).getUserImg()).placeholder(R.drawable.placeholder_gray_circle).error(R.drawable.placeholder_gray_circle).into(holder.profileImage);
        }else
        {
            Glide.with(mContext).load(R.drawable.placeholder_gray_circle).placeholder(R.drawable.placeholder_gray_circle).error(R.drawable.placeholder_gray_circle).into(holder.profileImage);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvDuration,tvReview;
        RatingBar rbRatings;
        CircularImageView profileImage;

        public TimeLineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            rbRatings = itemView.findViewById(R.id.rbSalonRating);
            tvReview = itemView.findViewById(R.id.tvReview);
            profileImage = itemView.findViewById(R.id.profile_image);
        }
    }
}
