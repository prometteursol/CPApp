package com.prometteur.cpapp.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.appointment.AppointmentDetailsActivity;
import com.prometteur.cpapp.beans.NotificationBean;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.dialogs.CollectDialogActivity;
import com.prometteur.cpapp.drawer.DashboardMainActivity;
import com.prometteur.cpapp.history.HistoryDetailsActivity;
import com.prometteur.cpapp.onboarding.LoginActivity;
import com.prometteur.cpapp.profile.ProfileActivity;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.Utils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.utils.Constants.BRANCHIDVAL;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showProgress;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.TimeLineViewHolder> {
    Context mContext;
    List<NotificationBean.Result> mDataList;

    public  NotificationListAdapter(Context mContext, List<NotificationBean.Result> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;

    }

    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.notification_row,
                parent,
                false);
        return new TimeLineViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, final int position) {
        holder.tvName.setText(mDataList.get(position).getNotiTitle());
        holder.tvDesc.setText(mDataList.get(position).getNotiMessage());
        holder.tvTime.setText(Utils.getReviewDate(mContext, mDataList.get(position).getNotiCreateDate()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getReadNotification(mDataList.get(position).getNotiId());
                switch (Integer.parseInt(mDataList.get(position).getNotiType())) {
                    case 1:// appoinment_request
                        if(mDataList.get(position).getAptStatus().equalsIgnoreCase("0")){
                            mContext.startActivity(new Intent(mContext, DashboardMainActivity.class).putExtra("objNoti",mDataList.get(position)));
                        }else if(mDataList.get(position).getAptStatus().equalsIgnoreCase("5")){
                            mContext.startActivity(new Intent(mContext, HistoryDetailsActivity.class).putExtra("pageType","endOtp").putExtra("appId",mDataList.get(position).getAptId()));
                        }/*else if(mDataList.get(position).getAptStatus().equalsIgnoreCase("1")){
                            OngoingBean.Result result=new OngoingBean.Result();
                            result.setAptId(mDataList.get(position).getAptId());
                            result.setAptAmount(mDataList.get(position).getAptAmount());
                            result.setAptStatus(mDataList.get(position).getAptStatus());
                            Bundle bundle = new Bundle();
                            bundle.putString("dynamicData", "" + 1);
                            bundle.putString("whichPage", "todays");
                            bundle.putSerializable("appointDetails", mDataList.get(position));
                            mContext.startActivity(new Intent(mContext, AppointmentDetailsActivity.class).putExtra("appointData",bundle));
                        }*/else
                        {
                            mContext.startActivity(new Intent(mContext, HistoryDetailsActivity.class).putExtra("pageType","history").putExtra("appId",mDataList.get(position).getAptId()));
                        }
                        ((Activity)mContext).finish();
                        break;
                    case 2: // appoinment

                        mContext.startActivity(new Intent(mContext, HistoryDetailsActivity.class).putExtra("pageType","history").putExtra("appId",mDataList.get(position).getAptId()));
                        ((Activity)mContext).finish();
                        break;
                    case 3: // review
                        mContext.startActivity(new Intent(mContext, ProfileActivity.class).putExtra("objNoti",mDataList.get(position)));
                        ((Activity)mContext).finish();
                        break;
                }
                //TODO: if notification after payment by customer uncomment
               // mContext.startActivity(new Intent(mContext, CollectDialogActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvDesc;
        TextView tvTime;

        public TimeLineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNotificationTitle);
            tvDesc = itemView.findViewById(R.id.tvNotificationDesc);
            tvTime = itemView.findViewById(R.id.tvTimeSpan);


            /*tvName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tvName.setMarqueeRepeatLimit(-1);
            tvName.setSingleLine(true);
            tvName.setSelected(true);*/

        }
    }

    OngoingBean ongoingBean;
    private void getReadNotification(String notiId) {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
       // final Dialog progressDialog = showProgress(mContext, 0);
       // progressDialog.show();
        service.readNotification(notiId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OngoingBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OngoingBean loginBeanObj) {
                       // progressDialog.dismiss();
                        ongoingBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                       // progressDialog.dismiss();
                        showFailToast(mContext,  mContext.getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                       // progressDialog.dismiss();
                        if (ongoingBean.getStatus() == 1) {


                        } else if (ongoingBean.getStatus() == 3) {
                            showFailToast(mContext,  "" + ongoingBean.getMsg());
                            logout(mContext);
                        }
                    }
                });
    }
}
