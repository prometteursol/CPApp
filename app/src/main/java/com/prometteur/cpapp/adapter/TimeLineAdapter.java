package com.prometteur.cpapp.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.vipulasri.timelineview.TimelineView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.appointment.AppointmentBottomFragmentDetails;
import com.prometteur.cpapp.beans.OfflineStatusBean;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.dialogs.OtpStartEndDialogActivity;
import com.prometteur.cpapp.listener.OnItemClickListener;
import com.prometteur.cpapp.rating.RatingActivity;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.utils.Utils.getTimeShow24to12HR;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showProgress;
import static com.prometteur.cpapp.utils.Utils.showSuccessToast;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {
    Context mContext;
    List<OngoingBean.Result> mDataList;
    OnItemClickListener listener;
    public TimeLineAdapter(Context mContext, List<OngoingBean.Result> mDataList, OnItemClickListener listener)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;
        this.listener=listener;

    }
    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycler_row,
                parent,
                false);
        return new TimeLineViewHolder(v,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final TimeLineViewHolder holder, final int position) {
        holder.tvSrNo.setText("" + (position + 1));
        holder.tvName.setText(mDataList.get(position).getUserFName() + " " + mDataList.get(position).getUserLName());
        holder.tvAppNo.setText("Appo ID. " + mDataList.get(position).getAptId());
        holder.tvTime.setText(getTimeShow24to12HR(mDataList.get(position).getAptTime()));
        Glide.with(holder.itemView).load(mDataList.get(position).getUserImg()).placeholder(R.drawable.placeholder_gray_circle).error(R.drawable.placeholder_gray_circle).into(holder.civProfileImage);
        holder.timeline.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_buzz));
        if (mDataList.get(position).getComboServices().size() > 0) {
            for (int i = 0; i < mDataList.get(position).getComboServices().get(0).getServices().size(); i++) {
                OngoingBean.Service service = new OngoingBean.Service();
                service.setSrvcName(mDataList.get(position).getComboServices().get(0).getServices().get(i).getSrvcName());
                mDataList.get(position).getServices().add(service);
            }
        }
        if (mDataList.get(position).getPromotionalServices().size() > 0) {
            for (int i = 0; i < mDataList.get(position).getPromotionalServices().get(0).getServices().size(); i++) {
                OngoingBean.Service service = new OngoingBean.Service();
                service.setSrvcName(mDataList.get(position).getPromotionalServices().get(0).getServices().get(i).getSrvcName());
                mDataList.get(position).getServices().add(service);
            }
        }
        getServiceData(holder.rvServices, mDataList.get(position).getServices());
        if (mDataList.get(position).getAptStatus().equalsIgnoreCase("6") && mDataList.get(position).getAptPaymentStatus().equalsIgnoreCase("0")) {
            holder.btnFinish.setVisibility(View.GONE);
        } else if (mDataList.get(position).getAptStatus().equalsIgnoreCase("6") && mDataList.get(position).getAptPaymentStatus().equalsIgnoreCase("1")) {
            holder.btnFinish.setVisibility(View.VISIBLE);
            holder.btnFinish.setText("End Service");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(mDataList.get(position));
            }
        });
        holder.btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.btnFinish.getText().toString().equalsIgnoreCase("End Service")) {
                    updateAcceptRejectStatus("4", mDataList.get(position).getAptId(), mDataList.get(position));
                } else {
                    AppointmentBottomFragmentDetails.strOTP = Integer.parseInt(mDataList.get(position).getAptEndOtp());
                    mContext.startActivity(new Intent(mContext, OtpStartEndDialogActivity.class).putExtra("startEnd", "End").putExtra("from", "others").putExtra("appId", mDataList.get(position).getAptId()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvSrNo;
        TextView btnFinish;
        TextView tvAppNo;
        TextView tvTime;
        RecyclerView rvServices;
        TimelineView timeline;
CircleImageView civProfileImage;
        public TimeLineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            tvSrNo = itemView.findViewById(R.id.tvSrNo);
            tvName = itemView.findViewById(R.id.tvName);
            tvAppNo = itemView.findViewById(R.id.tvAppNo);
            rvServices = itemView.findViewById(R.id.rvServices);
            timeline = itemView.findViewById(R.id.timeline);
            tvTime = itemView.findViewById(R.id.tvTime);
            btnFinish = itemView.findViewById(R.id.btnFinish);
            civProfileImage = itemView.findViewById(R.id.civProfileImage);

                timeline.initLine(viewType);

            tvName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tvName.setMarqueeRepeatLimit(-1);
            tvName.setSingleLine(true);
            tvName.setSelected(true);

        }
    }

    public void getServiceData(RecyclerView rvServices, List<OngoingBean.Service> services)
    {

        GridLayoutManager homeIconGridLayoutManager =new GridLayoutManager(mContext, 2);
        rvServices.setLayoutManager(homeIconGridLayoutManager);
        //var serviceAdapterl: ArrayAdapter<String> =

        rvServices.setAdapter(new ServiceListAdapter(mContext,services));
    }


    OfflineStatusBean offlineStatusBean;
    private void updateAcceptRejectStatus(final String status, String aptId, final OngoingBean.Result result) {

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
                            showSuccessToast(mContext,mContext.getString(R.string.service_completed));
                            mContext.startActivity(new Intent(mContext, RatingActivity.class).putExtra("aptObj",result));
                            ((Activity)mContext).finish();



                        } else if (offlineStatusBean.getStatus() == 3) {
                            showFailToast(mContext,  "" + offlineStatusBean.getMsg());
                            logout(mContext);
                        }

                    }
                });
    }

}
