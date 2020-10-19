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
import com.prometteur.cpapp.beans.InvoiceBean;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.prometteur.cpapp.utils.Utils.getResizedDrawable;
import static com.prometteur.cpapp.utils.Utils.getTimeShow24to12HR;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.TimeLineViewHolder> {
    Context mContext;
  List<InvoiceBean.Result> mDataList;

    public InvoiceAdapter(Context mContext, List<InvoiceBean.Result> mDataList)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;

    }
    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.invoice_row,
                parent, false);
        return new TimeLineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, final int position) {
        holder.tvName.setText(mDataList.get(position).getUserFName()+" "+mDataList.get(position).getUserLName());
        holder.tvAppNo.setText("Appo ID. "+mDataList.get(position).getAptId());
        holder.tvTime.setText(getTimeShow24to12HR(mDataList.get(position).getAptTime()));
        holder.tvAmountPaid.setText("₹ "+mDataList.get(position).getAptAmount()+"/-");
        if(mDataList.get(position).getAptPaymentType().equalsIgnoreCase("1"))
        {
            holder.tvPaymentStatus.setText("Paid Online : ");
        }else
        {
            holder.tvPaymentStatus.setText("Paid at Salon : ");
        }
        Glide.with(holder.itemView).load(mDataList.get(position).getUserImg()).placeholder(R.drawable.placeholder_gray_circle).error(R.drawable.placeholder_gray_circle).into(holder.civProfileImage);
        getResizedDrawable(mContext,R.drawable.ic_time,holder.tvTime,null,null,R.dimen._11sdp);

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvTime)
        TextView tvTime;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.tvAppNo)
        TextView tvAppNo;
        @Bind(R.id.tvAmountPaid)
        TextView tvAmountPaid;
        @Bind(R.id.tvPaymentStatus)
        TextView tvPaymentStatus;
        @Bind(R.id.civProfileImage)
        CircleImageView civProfileImage;
        public TimeLineViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            tvName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tvName.setMarqueeRepeatLimit(-1);
            tvName.setSingleLine(true);
            tvName.setSelected(true);

        }
    }


}
