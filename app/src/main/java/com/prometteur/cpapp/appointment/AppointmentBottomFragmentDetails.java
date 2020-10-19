package com.prometteur.cpapp.appointment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.ComboListRequestAppDetailsAdapter;
import com.prometteur.cpapp.adapter.PromoOffListRequestAppDetailsAdapter;
import com.prometteur.cpapp.adapter.ServiceWithOperatorAdapter;
import com.prometteur.cpapp.beans.CheckPenaltyBean;
import com.prometteur.cpapp.beans.OfflineStatusBean;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.beans.OtpBean;
import com.prometteur.cpapp.databinding.DialogAppointmentCancellationBinding;
import com.prometteur.cpapp.dialogs.OtpStartEndDialogActivity;
import com.prometteur.cpapp.dialogs.RescheduleActivity;
import com.prometteur.cpapp.drawer.DashboardMainActivity;
import com.prometteur.cpapp.history.HistoryDetailsActivity;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.Preferences;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.appointment.AppointmentBottomFragment.appointmentTabAdapter;
import static com.prometteur.cpapp.drawer.DashboardMainActivity.bottomNavigationView;
import static com.prometteur.cpapp.utils.Constants.APTCOUNT;
import static com.prometteur.cpapp.utils.Constants.BRANCHNAME;
import static com.prometteur.cpapp.utils.Constants.BRANCHOPENSTATUS;
import static com.prometteur.cpapp.utils.Constants.MOBNO;
import static com.prometteur.cpapp.utils.Utils.getAppDetDate;
import static com.prometteur.cpapp.utils.Utils.getBottomNavigationCount;
import static com.prometteur.cpapp.utils.Utils.getNoShowTime;
import static com.prometteur.cpapp.utils.Utils.getResizedDrawable;
import static com.prometteur.cpapp.utils.Utils.getTimeShow24to12HR;
import static com.prometteur.cpapp.utils.Utils.isNetworkAvailable;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showNoInternetDialog;
import static com.prometteur.cpapp.utils.Utils.showProgress;
import static com.prometteur.cpapp.utils.Utils.showSuccessToast;


public class AppointmentBottomFragmentDetails extends Fragment implements Serializable {

    Context mContext;
    RecyclerView recyclerView;
    private List<OngoingBean.Service> mDataList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    ServiceWithOperatorAdapter adapter;
    String position,page;
    OngoingBean.Result appointData;
    TextView tvName;
    @Bind(R.id.btnReschedule)
    Button btnReschedule;
    @Bind(R.id.btnAccept)
    Button btnAccept;
    @Bind(R.id.btnReject)
    Button btnReject;
    @Bind(R.id.btnCancel)
    Button btnCancel;
    @Bind(R.id.btnEnterStartOtp)
    Button btnEnterStartOtp;
    @Bind(R.id.btnRemoveFromHere)
    Button btnRemoveFromHere;
    @Bind(R.id.linBtnSection)
    LinearLayout linBtnSection;

    @Bind(R.id.tvDate)
    TextView tvDate;
    @Bind(R.id.tvTime)
    TextView tvTime;
    @Bind(R.id.tvAppointNo)
    TextView tvAppointNo;
    @Bind(R.id.tvAcceptExTime)
    TextView tvAcceptExTime;
    @Bind(R.id.tvRatingCount)
    TextView tvRatingCount;
    @Bind(R.id.tvAmt)
    TextView tvAmt;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.rBar)
    RatingBar rBar;
    @Bind(R.id.recycle_ComboSelected)
    RecyclerView recycleComboSelected;
    @Bind(R.id.recycle_PromoOffSelected)
    RecyclerView recyclePromoOffSelected;
    @Bind(R.id.profileImage)
    CircleImageView profileImage;

private int fragResult=101;
private int finishFrag=103;
private int startOtpResult=102;
private int endOtpResult=104;
int strCancelRed=0;

    @Bind(R.id.linBtnSectionForTU)
    LinearLayout linBtnSectionForTU;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View view=inflater.inflate(R.layout.fragment_appointment_details, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        page=getArguments().getString("whichPage");
        appointData= (OngoingBean.Result) getArguments().getSerializable("appointDetails");



        recyclerView = view.findViewById(R.id.recyclerView);
        tvName = view.findViewById(R.id.tvName);
        String gender="";
        if(appointData.getUserGender().equalsIgnoreCase("1"))  //1= "Male"
        {
            gender="M";
        }else if(appointData.getUserGender().equalsIgnoreCase("2"))  //2 = "Female"
        {
            gender="F";
        }else {
            gender="O";
        }
        tvName.setText(appointData.getUserFName()+" "+appointData.getUserLName()+" ("+gender+")");
        tvAppointNo.setText("Appo ID. "+appointData.getAptId());
        if(appointData.getUserRating()!=null && !appointData.getUserRating().isEmpty()) {
            rBar.setRating(Float.parseFloat(appointData.getUserRating()));
            tvRatingCount.setText("("+appointData.getReviewCount()+" Reviews)");
        }
        tvDate.setText(getAppDetDate(appointData.getAptDate()));
        tvTime.setText(getTimeShow24to12HR(appointData.getAptTime()));
        tvAmt.setText("Amount : ₹ "+appointData.getAptAmount()+"/-");
        getResizedDrawable(mContext,R.drawable.ic_time,tvTime,null,null,R.dimen._13sdp);
        getResizedDrawable(mContext,R.drawable.ic_date,tvDate,null,null,R.dimen._13sdp);
        initRecyclerView();
        Glide.with(mContext).load(appointData.getUserImg()).placeholder(R.drawable.placeholder_gray_circle).error(R.drawable.placeholder_gray_circle).into(profileImage);
        if(appointData.getAptStatus().equalsIgnoreCase("0")) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final SimpleDateFormat simpleDateForMMss = new SimpleDateFormat("mm:ss");
            long longAcceptTime = 0, timeDiff = 0;
            try {
                longAcceptTime = simpleDateFormat.parse(appointData.getAptAcceptTime()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            timeDiff = longAcceptTime - new Date().getTime();
            new CountDownTimer(timeDiff, 1000) {

                public void onTick(long millisUntilFinished) {

                    try {
                        tvAcceptExTime.setText("Time Remain\n" + simpleDateForMMss.format(simpleDateForMMss.parse((millisUntilFinished / 1000) / 60 + ":" + (millisUntilFinished / 1000) % 60)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //here you can have your logic to set text to edittext
                }

                public void onFinish() {
                    tvAcceptExTime.setVisibility(View.GONE);
                    if(appointData.getAptStatus().equalsIgnoreCase("8")) {
                        btnRemoveFromHere.setVisibility(View.VISIBLE);
                        linBtnSection.setVisibility(View.GONE);
                        btnEnterStartOtp.setVisibility(View.GONE);
                        btnReschedule.setVisibility(View.GONE);
                        linBtnSectionForTU.setVisibility(View.GONE);
                    }
                }

            }.start();
        }else
        {
            tvAcceptExTime.setVisibility(View.GONE);
            if(appointData.getAptStatus().equalsIgnoreCase("8")) {
                btnRemoveFromHere.setVisibility(View.VISIBLE);
                linBtnSection.setVisibility(View.GONE);
                btnEnterStartOtp.setVisibility(View.GONE);
                btnReschedule.setVisibility(View.GONE);
                linBtnSectionForTU.setVisibility(View.GONE);
            }
        }


        if(page!=null)
        {
            if(page.equalsIgnoreCase("onGoingFrag"))
            {
                linBtnSection.setVisibility(View.GONE);
                btnReschedule.setVisibility(View.GONE);
            }else if(page.equalsIgnoreCase("todays"))
            {
                btnRemoveFromHere.setVisibility(View.GONE);
                linBtnSection.setVisibility(View.GONE);
                btnReschedule.setVisibility(View.GONE);
                linBtnSectionForTU.setVisibility(View.VISIBLE);
                if(appointData.getAptNoshowTime()!=null && !appointData.getAptNoshowTime().isEmpty()) {
                    appointData.setAptNoshowTime(appointData.getAptNoshowTime().replace("minute",""));
                    if (getNoShowTime(appointData.getAptDate(),appointData.getAptTime(),appointData.getAptNoshowTime().trim()  )) {
                        btnCancel.setText("No Show");
                        btnEnterStartOtp.setVisibility(View.GONE);
                    }else
                    {
                        btnEnterStartOtp.setVisibility(View.VISIBLE);
                    }
                }else{
                    btnEnterStartOtp.setVisibility(View.VISIBLE);
                }

            }else if(page.equalsIgnoreCase("upcoming"))
            {
                btnRemoveFromHere.setVisibility(View.GONE);
                linBtnSection.setVisibility(View.GONE);
                btnEnterStartOtp.setVisibility(View.GONE);
                btnReschedule.setVisibility(View.GONE);
                linBtnSectionForTU.setVisibility(View.VISIBLE);
            }else if(page.equalsIgnoreCase("appointment"))
            {
                tvAppointNo.setVisibility(View.GONE);
            }
        }


        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Preferences.getPreferenceValue(mContext, BRANCHOPENSTATUS).equalsIgnoreCase("1"))
                {
                    startEndMsg = "Start";
                    getOTP();
                }else{
                    showFailToast(mContext,"Appointments can't be accepted as your salon is offline at this time");
                }

            }
        });
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Preferences.getPreferenceValue(mContext, BRANCHOPENSTATUS).equalsIgnoreCase("1"))
                {
                strCancelRed=0;
                startActivityForResult(new Intent(mContext,RejectionActivity.class).putExtra("aptId",appointData.getAptId()).putExtra("msgType","reject"),fragResult);
                }else{
                    showFailToast(mContext,"Appointments can't be rejected as your salon is offline at this time");
                }
                }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnCancel.getText().toString().equalsIgnoreCase("No Show"))
                {
                    updateAcceptRejectStatus("9");
                }else {
                    strCancelRed = 1;
                    if(appointData.getAptStatus().equalsIgnoreCase("1")){
                        if (isNetworkAvailable(mContext)) {
                            getCheckPenalty();
                        } else {
                            showNoInternetDialog(mContext);
                        }
                    }else {
                        startActivityForResult(new Intent(mContext, RejectionActivity.class).putExtra("aptId", appointData.getAptId()).putExtra("msgType", "cancel"), fragResult);
                    }
                }
            }
        });
        btnReschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Preferences.getPreferenceValue(mContext, BRANCHOPENSTATUS).equalsIgnoreCase("1"))
                {
                strCancelRed=0;
                startActivityForResult(new Intent(mContext, RescheduleActivity.class).putExtra("appointmentId",appointData.getAptId()).putExtra("appDate",appointData.getAptDate()).putExtra("appTime",appointData.getAptTime()),finishFrag);
                }else{
                    showFailToast(mContext,"Appointments can't be rescheduled as your salon is offline at this time");
                }
                }
        });
        btnEnterStartOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(btnEnterStartOtp.getText().toString().equalsIgnoreCase("Enter Start OTP")) {
                strOTP=Integer.parseInt(appointData.getAptStartOtp());
                    startActivityForResult(new Intent(mContext, OtpStartEndDialogActivity.class).putExtra("startEnd", "Start").putExtra("from", "others").putExtra("appId",appointData.getAptId()), startOtpResult);
               /* }else
                {
                    startActivityForResult(new Intent(mContext, OtpStartEndDialogActivity.class).putExtra("startEnd","End"),endOtpResult);
                }*/
            }
        });
        btnRemoveFromHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAcceptRejectStatusForUnattended("1");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==fragResult)
        {
            if(strCancelRed==1){
                startActivity(new Intent(mContext, DashboardMainActivity.class).putExtra("objNoti", (Bundle) null));
                ((Activity)mContext).finish();
            }else {
                appointmentTabAdapter.removeCompetetorTabFragments(AppointmentBottomFragmentDetails.this);
            }
        }else if(resultCode==finishFrag)
        {
            if(strCancelRed==1){
                startActivity(new Intent(mContext, DashboardMainActivity.class).putExtra("objNoti", (Bundle) null));
                ((Activity)mContext).finish();
            }else {
                showSuccessToast(mContext,  "Appointment rescheduled");
                appointmentTabAdapter.removeCompetetorTabFragments(AppointmentBottomFragmentDetails.this);
            }
        }else if(resultCode==startOtpResult)
        {
            startEndMsg="End";
            getOTP();
        }

    }

    private void setGradientButton(int[] colors, Button button)
{
    float serial[]={0,1,2};
    Shader myShader = new LinearGradient(
            0, 50, 100, 100,
            colors,serial,
            Shader.TileMode.MIRROR );
    button.getPaint().setShader( myShader );
}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void initRecyclerView() {
        initAdapter();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //if(recyclerView.getChildAt(0).top < 0) dropshadow.setVisible(); else dropshadow.setGone();
            }
        });
    }

    private void initAdapter() {

        mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        mDataList=appointData.getServices();
        adapter =new ServiceWithOperatorAdapter(mContext,mDataList);
        recyclerView.setAdapter(adapter);
        if(mDataList.size()==0)
        {
            tvTitle.setVisibility(View.GONE);
        }else
        {
            tvTitle.setVisibility(View.VISIBLE);
        }


        //combo list
        recycleComboSelected.setLayoutManager(new LinearLayoutManager(mContext));
        recycleComboSelected.setAdapter(new ComboListRequestAppDetailsAdapter((AppCompatActivity) mContext,appointData.getComboServices(),false));
        //promo offer list
        recyclePromoOffSelected.setLayoutManager(new LinearLayoutManager(mContext));
        recyclePromoOffSelected.setAdapter(new PromoOffListRequestAppDetailsAdapter((AppCompatActivity) mContext,appointData.getPromotionalServices(),false));
    }


    OfflineStatusBean ongoingBean;
    private void updateAcceptRejectStatus(final String status) {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(mContext, 0);
        progressDialog.show();
        service.updateAcceptRejectStatus(appointData.getAptId(),status,"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OfflineStatusBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OfflineStatusBean loginBeanObj) {
                        progressDialog.dismiss();
                        ongoingBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(mContext, mContext.getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (ongoingBean.getStatus() == 1) {
                            if(status.equalsIgnoreCase("6"))
                            {
                                showSuccessToast(mContext,  "Service Finished");
                                startActivity(new Intent(mContext, HistoryDetailsActivity.class).putExtra("pageType","endOtp").putExtra("appId",appointData.getAptId()));
                                ((Activity)mContext).finish();
                            }else if(status.equalsIgnoreCase("5"))
                            {
                                showSuccessToast(mContext,  "Service Started");
                                startActivity(new Intent(mContext, DashboardMainActivity.class).putExtra("objNoti", (Bundle) null));
                                ((Activity)mContext).finish();
                            }else if(status.equalsIgnoreCase("1"))
                            {
                                if(Integer.parseInt(getArguments().getString("dynamicData"))==1) {
                                    Preferences.setPreferenceValue(mContext, APTCOUNT, 0);
                                    getBottomNavigationCount(mContext, bottomNavigationView);
                                }
                                showSuccessToast(mContext,  "Appointment accepted");
                                appointmentTabAdapter.removeCompetetorTabFragments(AppointmentBottomFragmentDetails.this);
                            }else if(status.equalsIgnoreCase("8"))
                            {
                                if(Integer.parseInt(getArguments().getString("dynamicData"))==1) {
                                    Preferences.setPreferenceValue(mContext, APTCOUNT, 0);
                                    getBottomNavigationCount(mContext, bottomNavigationView);
                                }
                                showSuccessToast(mContext,  "Unattended appointment removed successfully. You can view these appointments under appointment history");
                                appointmentTabAdapter.removeCompetetorTabFragments(AppointmentBottomFragmentDetails.this);
                            }else if(status.equalsIgnoreCase("9"))
                            {
                                showSuccessToast(mContext,  "Appointment moved to History");
                                appointmentTabAdapter.removeCompetetorTabFragments(AppointmentBottomFragmentDetails.this);
                                btnCancel.setVisibility(View.GONE);
                            }else{
                                appointmentTabAdapter.removeCompetetorTabFragments(AppointmentBottomFragmentDetails.this);
                            }


                        } else if (ongoingBean.getStatus() == 3) {
                            showFailToast(mContext,  "" + ongoingBean.getMsg());
                            logout(mContext);
                        }

                    }
                });
    }

    private void updateAcceptRejectStatusForUnattended(String unattendedStatus) {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(mContext, 0);
        progressDialog.show();
        service.updateAcceptRejectStatusForUnattended(appointData.getAptId(),"",unattendedStatus)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OfflineStatusBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OfflineStatusBean loginBeanObj) {
                        progressDialog.dismiss();
                        ongoingBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(mContext, mContext.getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (ongoingBean.getStatus() == 1) {

                                if(Integer.parseInt(getArguments().getString("dynamicData"))==1) {
                                    Preferences.setPreferenceValue(mContext, APTCOUNT, 0);
                                    getBottomNavigationCount(mContext, bottomNavigationView);
                                }
                                showSuccessToast(mContext,  "Unattended appointment removed successfully. You can view these appointments under appointment history");
                                appointmentTabAdapter.removeCompetetorTabFragments(AppointmentBottomFragmentDetails.this);



                        } else if (ongoingBean.getStatus() == 3) {
                            showFailToast(mContext,  "" + ongoingBean.getMsg());
                            logout(mContext);
                        }

                    }
                });
    }

    OtpBean otpBean;
    public static int strOTP=0;
    String startEndMsg="";
    private void getOTP() {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(mContext, 0);
        progressDialog.show();
        service.getOTP(Preferences.getPreferenceValue(mContext,MOBNO),startEndMsg,Preferences.getPreferenceValue(mContext, BRANCHNAME),appointData.getAptId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OtpBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OtpBean loginBeanObj) {
                        progressDialog.dismiss();
                        otpBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(mContext,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (otpBean.getStatus() == 1) {
                            strOTP=otpBean.getResult().getOtp();
                            if(startEndMsg.equalsIgnoreCase("Start")) {
                                updateAcceptRejectStatus("1");
                            }else if(startEndMsg.equalsIgnoreCase("End"))
                            {
                                updateAcceptRejectStatus("5");  //for start service
                            }
                        } else if (otpBean.getStatus() == 3) {
                            showFailToast(mContext, "" + otpBean.getMsg());
                            logout(mContext);
                        }

                    }
                });
    }


    CheckPenaltyBean checkPenaltyBean;
    String penaltyAmt="0",penaltyPer="0",psStartTime="",psEndTime="";
    private void getCheckPenalty() {

        final ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(mContext, 0);
        progressDialog.show();
        service.getCheckPenalty(appointData.getAptId())
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
                                    showCancelRequestDialog((Activity) mContext,penaltyPer);
                                } else {
                                    startActivityForResult(new Intent(mContext, RejectionActivity.class).putExtra("aptId", appointData.getAptId()).putExtra("penaltyAmt",penaltyAmt).putExtra("msgType", "cancel"), fragResult);
                                    //finish();
                                    // dialogCancelAppointment.dismiss();
                                }
                            }else {
                                startActivityForResult(new Intent(mContext, RejectionActivity.class).putExtra("aptId", appointData.getAptId()).putExtra("msgType", "cancel"), fragResult);
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
    public void showCancelRequestDialog(Activity inActivity,String penaltyPer) {
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
                    startActivityForResult(new Intent(mContext, RejectionActivity.class).putExtra("aptId", appointData.getAptId()).putExtra("penaltyAmt",penaltyAmt).putExtra("msgType", "cancel"), fragResult);
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
