package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.ProfileBean;
import com.prometteur.cpapp.beans.TimeLineModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.prometteur.cpapp.profile.ProfileActivity.profileData;

public class OperatorProfileAdapter extends RecyclerView.Adapter<OperatorProfileAdapter.TimeLineViewHolder> {
    Context mContext;
    List<ProfileBean.Operator> mDataList;
    public OperatorProfileAdapter(Context mContext, List<ProfileBean.Operator> mDataList)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;

    }
    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.operator_profile_row,
                parent,
                false);
        return new TimeLineViewHolder(v,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {
        holder.tvName.setText(mDataList.get(position).getUserFName()+" "+mDataList.get(position).getUserLName());
        holder.tvSpeciality.setText(getSpeciality(mDataList.get(position).getUserSpeciality()));
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
        TextView tvSpeciality;
        CircleImageView profileImage;

        public TimeLineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvSpeciality = itemView.findViewById(R.id.tvSpeciality);
            profileImage = itemView.findViewById(R.id.profileImage);

        }
    }


    private String getSpeciality(String speciality) {
        ProfileBean.ServicesName servicesName= profileData.getServicesName();
        String strSpeciality = "";
        ArrayList<String> specArray=new ArrayList<>();
        for (int i = 0; i < speciality.split(",").length; i++) {
            switch (speciality.split(",")[i]) {
                case "1":
                    specArray.add(servicesName.get1());
                    break;
                case "2":
                    specArray.add(servicesName.get2());
                    break;
                case "3":
                    specArray.add(servicesName.get3());
                    break;
                case "4":
                    specArray.add(servicesName.get4());
                    break;
                case "5":
                    specArray.add(servicesName.get5());
                    break;
            }
        }
        strSpeciality= TextUtils.join(", ",specArray);
        return strSpeciality;
    }

}
