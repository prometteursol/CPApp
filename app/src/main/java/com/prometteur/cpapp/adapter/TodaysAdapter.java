package com.prometteur.cpapp.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.vipulasri.timelineview.TimelineView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.appointment.AppointmentBottomFragmentDetails;
import com.prometteur.cpapp.appointment.TodaysFragment;
import com.prometteur.cpapp.beans.OfflineStatusBean;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.dialogs.OtpStartEndDialogActivity;
import com.prometteur.cpapp.drawer.DashboardMainActivity;
import com.prometteur.cpapp.listener.OnItemClickListener;
import com.prometteur.cpapp.rating.RatingActivity;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.TextViewCustomFont;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.utils.Utils.getNoShowTime;
import static com.prometteur.cpapp.utils.Utils.getResizedDrawable;
import static com.prometteur.cpapp.utils.Utils.getTimeShow24to12HR;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showProgress;
import static com.prometteur.cpapp.utils.Utils.showSuccessToast;

public class TodaysAdapter extends RecyclerView.Adapter<TodaysAdapter.TimeLineViewHolder> {
    Context mContext;
    List<OngoingBean.Result> mDataList;
    OnItemClickListener listener;
    public TodaysAdapter(Context mContext, List<OngoingBean.Result> mDataList, OnItemClickListener listener)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;
        this.listener=listener;

    }
    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.todays_row,
                parent,
                false);
        return new TimeLineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, final int position) {
        holder.tvName.setText(mDataList.get(position).getUserFName()+" "+mDataList.get(position).getUserLName());
        holder.tvAppNo.setText(mDataList.get(position).getAptId());
        holder.tvTime.setText(getTimeShow24to12HR(mDataList.get(position).getAptTime()));
        Glide.with(holder.itemView).load(mDataList.get(position).getUserImg()).placeholder(R.drawable.placeholder_gray_circle).error(R.drawable.placeholder_gray_circle).into(holder.civProfileImage);
        getResizedDrawable(mContext,R.drawable.ic_time,holder.tvTime,null,null,R.dimen._11sdp);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               listener.onItemClick(mDataList.get(position));
           }
       });
       if(mDataList.get(position).getAptNoshowTime()!=null && !mDataList.get(position).getAptNoshowTime().isEmpty()) {
           mDataList.get(position).setAptNoshowTime(mDataList.get(position).getAptNoshowTime().replace("minute",""));
           if (getNoShowTime(mDataList.get(position).getAptDate(),mDataList.get(position).getAptTime(),mDataList.get(position).getAptNoshowTime().trim()  )) {
               holder.btnEnterStartOtp.setVisibility(View.GONE);
               holder.btnNoShow.setVisibility(View.VISIBLE);
               holder.btnNoShow.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       updateAcceptRejectStatus("9", mDataList.get(position).getAptId(), mDataList.get(position),position);
                   }
               });
           } else {
               holder.btnNoShow.setVisibility(View.GONE);
               holder.btnEnterStartOtp.setVisibility(View.VISIBLE);
           }
       }else {
           holder.btnNoShow.setVisibility(View.GONE);
           holder.btnEnterStartOtp.setVisibility(View.VISIBLE);
       }

        holder.btnEnterStartOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(btnEnterStartOtp.getText().toString().equalsIgnoreCase("Enter Start OTP")) {
//                DashboardMainActivity.aptId =mDataList.get(position).getAptId();
                AppointmentBottomFragmentDetails.strOTP=Integer.parseInt(mDataList.get(position).getAptStartOtp());
                ((Activity)mContext).startActivityForResult(new Intent(mContext, OtpStartEndDialogActivity.class).putExtra("startEnd", "Start").putExtra("from", "todays").putExtra("appId",mDataList.get(position).getAptId()), 102);
               /* }else
                {
                    startActivityForResult(new Intent(mContext, OtpStartEndDialogActivity.class).putExtra("startEnd","End"),endOtpResult);
                }*/
            }
        });
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
        @Bind(R.id.btnNoShow)
        Button btnNoShow;
        @Bind(R.id.btnEnterStartOtp)
        Button btnEnterStartOtp;
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




    OfflineStatusBean offlineStatusBean;
    private void updateAcceptRejectStatus(final String status, String aptId, final OngoingBean.Result result,int position) {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(mContext, 0);
        progressDialog.show();
        service.updateAcceptRejectStatus(aptId,status,"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OfflineStatusBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OfflineStatusBean loginBeanObj) {
                        progressDialog.dismiss();
                        offlineStatusBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(mContext,  mContext.getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (offlineStatusBean.getStatus() == 1) {
                            showSuccessToast(mContext,"Appointment moved to History");
                            mDataList.remove(position);
                            notifyDataSetChanged();
                        } else if (offlineStatusBean.getStatus() == 3) {
                            showFailToast(mContext,  "" + offlineStatusBean.getMsg());
                            logout(mContext);
                        }

                    }
                });
    }
}
