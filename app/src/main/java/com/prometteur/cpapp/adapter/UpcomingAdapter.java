package com.prometteur.cpapp.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.vipulasri.timelineview.TimelineView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.appointment.RejectionActivity;
import com.prometteur.cpapp.beans.CheckPenaltyBean;
import com.prometteur.cpapp.beans.DateObject;
import com.prometteur.cpapp.beans.HistoryDataModelObject;
import com.prometteur.cpapp.beans.ListObject;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.beans.UpcomingDataModelObject;
import com.prometteur.cpapp.databinding.DialogAppointmentCancellationBinding;
import com.prometteur.cpapp.history.HistoryDetailsActivity;
import com.prometteur.cpapp.listener.OnItemClickListener;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.utils.Utils.getResizedDrawable;
import static com.prometteur.cpapp.utils.Utils.getTimeShow24to12HR;
import static com.prometteur.cpapp.utils.Utils.isNetworkAvailable;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showNoInternetDialog;
import static com.prometteur.cpapp.utils.Utils.showProgress;

public class UpcomingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<ListObject> mDataList;
    OnItemClickListener listener;
    public UpcomingAdapter(Context mContext, List<ListObject> mDataList,OnItemClickListener listener)
    {
        this.mContext=mContext;
        this.mDataList=mDataList;
        this.listener=listener;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ListObject.TYPE_GENERAL:
                View currentUserView = inflater.inflate(R.layout.upcoming_row, parent, false);
                viewHolder = new TimeLineViewHolder(currentUserView); // view holder for normal items
                break;

            case ListObject.TYPE_DATE:
                View v2 = inflater.inflate(R.layout.date_row, parent, false);
                viewHolder = new DateViewHolder(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case ListObject.TYPE_GENERAL:
                final UpcomingDataModelObject generalItem= (UpcomingDataModelObject) mDataList.get(position);
                TimeLineViewHolder viewHolder= (TimeLineViewHolder) holder;
                viewHolder.tvName.setText(generalItem.getTimeLineModel().getUserFName()+" "+generalItem.getTimeLineModel().getUserLName());
                viewHolder.tvAppNo.setText("Appo ID. "+generalItem.getTimeLineModel().getAptId());
                viewHolder.tvTime.setText(getTimeShow24to12HR(generalItem.getTimeLineModel().getAptTime()));
                Glide.with(holder.itemView).load(generalItem.getTimeLineModel().getUserImg()).placeholder(R.drawable.placeholder_gray_circle).error(R.drawable.placeholder_gray_circle).into(viewHolder.civProfileImage);
                getResizedDrawable(mContext,R.drawable.ic_time,viewHolder.tvTime,null,null,R.dimen._11sdp);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         listener.onItemClick(generalItem.getTimeLineModel());
                    }
                });
                viewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(generalItem.getTimeLineModel().getAptStatus().equalsIgnoreCase("1")){
                            if (isNetworkAvailable(mContext)) {
                                getCheckPenalty(generalItem.getTimeLineModel());
                            } else {
                                showNoInternetDialog(mContext);
                            }
                        }else {
                            ((Activity) mContext).startActivity(new Intent(mContext, RejectionActivity.class).putExtra("aptId", generalItem.getTimeLineModel().getAptId()).putExtra("msgType", "cancel"));
                        }
                    }
                });
                break;

            case ListObject.TYPE_DATE:

                DateObject dateItem = (DateObject) mDataList.get(position);
                DateViewHolder viewHolder1 =(DateViewHolder) holder;
                viewHolder1.bind(dateItem.getDate());
                break;
        }


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getType();
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvTime)
        TextView tvTime;
        TextView tvName;
        TextView tvAppNo;
        Button btnCancel;
        CircleImageView civProfileImage;
        public TimeLineViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAppNo = itemView.findViewById(R.id.tvAppNo);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            civProfileImage = itemView.findViewById(R.id.civProfileImage);


            tvName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tvName.setMarqueeRepeatLimit(-1);
            tvName.setSingleLine(true);
            tvName.setSelected(true);

        }
    }
    public void setDataChange(List<ListObject> asList) {
        this.mDataList = asList;
        //now, tell the adapter about the update
        notifyDataSetChanged();
    }
    public class DateViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        public DateViewHolder(View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.tvDate);
            //TODO initialize your xml views
        }

        public void bind(final String date) {
            //TODO set data to xml view via textivew.setText();
            tvDate.setText(""+date);
        }
    }

    CheckPenaltyBean checkPenaltyBean;
    String penaltyAmt="0",penaltyPer="0",psStartTime="",psEndTime="";
    private void getCheckPenalty(OngoingBean.Result timeLineModel) {

        final ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(mContext, 0);
        progressDialog.show();
        service.getCheckPenalty(timeLineModel.getAptId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CheckPenaltyBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CheckPenaltyBean loginBeanObj) {
                        progressDialog.dismiss();
                        checkPenaltyBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        //  showFailToast(nActivity, nActivity.getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();

                        if (checkPenaltyBean.getStatus() == 1) {
                            if(checkPenaltyBean.getPenaltyPercentage().size()!=0) {
                                penaltyPer = checkPenaltyBean.getPenaltyPercentage().get(0);
                                penaltyAmt = checkPenaltyBean.getResult().get(0);
                                psStartTime = checkPenaltyBean.getPsStartTime();
                                psEndTime = checkPenaltyBean.getPsEndTime();
                                if (!penaltyPer.equalsIgnoreCase("0")) {
                                    showCancelRequestDialog((Activity) mContext,penaltyPer,timeLineModel);
                                } else {
                                    mContext.startActivity(new Intent(mContext, RejectionActivity.class).putExtra("aptId", timeLineModel.getAptId()).putExtra("penaltyAmt",penaltyAmt).putExtra("msgType", "cancel"));
                                    //finish();
                                    // dialogCancelAppointment.dismiss();
                                }
                            }else {
                                mContext.startActivity(new Intent(mContext, RejectionActivity.class).putExtra("aptId", timeLineModel.getAptId()).putExtra("msgType", "cancel"));
                                //finish();
                                // dialogCancelAppointment.dismiss();
                            }
                        } else if (checkPenaltyBean.getStatus() == 3) {
                            showFailToast(mContext, "" + checkPenaltyBean.getMsg());
                            logout(mContext);
                        }
                        //setEmptyMsg(mDataList, comboPageBinding.recycleCombolist, ivNoData);
                    }
                });
    }
    DialogAppointmentCancellationBinding cancellationBinding;
    Dialog dialogCancelAppointment;
    public void showCancelRequestDialog(Activity inActivity,String penaltyPer,OngoingBean.Result timeLineModel) {
        dialogCancelAppointment=new Dialog(inActivity,R.style.CustomAlertDialog);
        cancellationBinding = DialogAppointmentCancellationBinding.inflate(LayoutInflater.from(inActivity));
        dialogCancelAppointment.setContentView(cancellationBinding.getRoot());
        Window window = dialogCancelAppointment.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        dialogCancelAppointment.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
/*if(penaltyPer.equalsIgnoreCase("50"))
{*/
        cancellationBinding.tvPenaltydesc.setText("Cancellation Time > "+psStartTime+"-"+psEndTime+" Hr > "+penaltyPer+"%");
/*}else if(penaltyPer.equalsIgnoreCase("25"))
{
    cancellationBinding.tvPenaltydesc.setText("Cancellation Time > 4-8 Hr > 25%");
}else if(penaltyPer.equalsIgnoreCase("10"))
{
    cancellationBinding.tvPenaltydesc.setText("Cancellation Time > 8-16 Hr > 10%");
}*/

        cancellationBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cancellationBinding.cbTermsConditions.isChecked()) {
                    dialogCancelAppointment.dismiss();
                    mContext.startActivity(new Intent(mContext, RejectionActivity.class).putExtra("aptId", timeLineModel.getAptId()).putExtra("penaltyAmt",penaltyAmt).putExtra("msgType", "cancel"));
                }else
                {
                    showFailToast(mContext,"Accept terms & conditions to proceed");
                }
                //finish();
            }
        });
        dialogCancelAppointment.show();

    }

}
