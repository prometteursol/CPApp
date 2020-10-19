package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.ComboListBean;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.dialogs.ComboListDialogActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.prometteur.cpapp.utils.Utils.getStrikeString;

public class ComboListAdapter extends RecyclerView.Adapter<ComboListAdapter.TimeLineViewHolder> {
    Context mContext;
    List<ComboListBean.Result> mDataList;
    public ComboListAdapter(Context mContext, List<ComboListBean.Result> mDataList)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;

    }
    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.combo_list_row,
                parent,
                false);
        return new TimeLineViewHolder(v,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, final int position) {
       holder.tvServiceName.setText(mDataList.get(position).getOfferName());
       holder.tvOfferPrice.setText("₹ " + mDataList.get(position).getOfferDiscountPrice());
       if(!mDataList.get(position).getOfferPrice().isEmpty()) {
           holder.tvMainPrice.setText("₹ " + mDataList.get(position).getOfferPrice());
       }else
       {
           holder.tvMainPrice.setVisibility(View.GONE);
       }
       List<ComboListBean.Service> services=mDataList.get(position).getServices();
       if(services.size()==1)
       {
           if(services.get(0).getBrndName()!=null)
           {
               holder.tvServiceOneBrand.setText(services.get(0).getSrvcName()+" brand : "+services.get(0).getBrndName());
           }else
           {
               holder.tvServiceOneBrand.setText(services.get(0).getSrvcName());
           }
           holder.tvServiceSecondBrand.setVisibility(View.GONE);
       }else if(services.size()==2)
       {
           if(services.get(0).getBrndName()!=null)
           {
               holder.tvServiceOneBrand.setText(services.get(0).getSrvcName()+" brand : "+services.get(0).getBrndName());
           }else
           {
               holder.tvServiceOneBrand.setText(services.get(0).getSrvcName());
           }
           if(services.get(1).getBrndName()!=null)
           {
               holder.tvServiceSecondBrand.setText(services.get(1).getSrvcName()+" brand : "+services.get(1).getBrndName());
           }else
           {
               holder.tvServiceSecondBrand.setText(services.get(1).getSrvcName());
           }
       }else if(services.size()>2)
       {
           if(services.get(0).getBrndName()!=null)
           {
               holder.tvServiceOneBrand.setText(services.get(0).getSrvcName()+" brand : "+services.get(0).getBrndName());
           }else
           {
               holder.tvServiceOneBrand.setText(services.get(0).getSrvcName());
           }
           if(services.get(1).getBrndName()!=null)
           {
               holder.tvServiceSecondBrand.setText(Html.fromHtml(services.get(1).getSrvcName()+" brand : "+services.get(1).getBrndName()+" <font color=\"#088383\"><b>+"+(services.size()-2)+"<b></font>"));
           }else
           {
               holder.tvServiceSecondBrand.setText(Html.fromHtml(services.get(1).getSrvcName()+" <font color=\"#088383\"><b>+"+(services.size()-2)+"<b></font>"));
           }
       }
        getStrikeString(holder.tvMainPrice);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mContext.startActivity(new Intent(mContext, ComboListDialogActivity.class).putExtra("objCombo",mDataList.get(position)));
           }
       });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvServiceName)
        TextView tvServiceName;
        @Bind(R.id.tvServiceOneBrand)
        TextView tvServiceOneBrand;
        @Bind(R.id.tvServiceSecondBrand)
        TextView tvServiceSecondBrand;
        @Bind(R.id.tvOfferPrice)
        TextView tvOfferPrice;
        @Bind(R.id.tvMainPrice)
        TextView tvMainPrice;
        public TimeLineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this,itemView);


            /*tvName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tvName.setMarqueeRepeatLimit(-1);
            tvName.setSingleLine(true);
            tvName.setSelected(true);*/

        }
    }


}
