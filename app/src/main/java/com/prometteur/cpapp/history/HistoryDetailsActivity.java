package com.prometteur.cpapp.history;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.ComboListRequestAppDetailsAdapter;
import com.prometteur.cpapp.adapter.HistoryAdapter;
import com.prometteur.cpapp.adapter.HistoryDetailsAdapter;
import com.prometteur.cpapp.adapter.PromoOffListRequestAppDetailsAdapter;
import com.prometteur.cpapp.appointment.AppointmentBottomFragmentDetails;
import com.prometteur.cpapp.beans.DateObject;
import com.prometteur.cpapp.beans.HistoryDataModel;
import com.prometteur.cpapp.beans.HistoryDataModelObject;
import com.prometteur.cpapp.beans.ListObject;
import com.prometteur.cpapp.beans.OfflineStatusBean;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.beans.OrderStatus;
import com.prometteur.cpapp.dialogs.OtpStartEndDialogActivity;
import com.prometteur.cpapp.drawer.DashboardMainActivity;
import com.prometteur.cpapp.rating.RatingActivity;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.DateParser;
import com.prometteur.cpapp.utils.NetworkChangeReceiver;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.utils.Constants.BRANCHIDVAL;
import static com.prometteur.cpapp.utils.Utils.getTimeShow24to12HR;
import static com.prometteur.cpapp.utils.Utils.isNetworkAvailable;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.setEmptyMsg;
import static com.prometteur.cpapp.utils.Utils.setNoInternetMsg;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showProgress;
import static com.prometteur.cpapp.utils.Utils.showSuccessToast;


public class HistoryDetailsActivity extends AppCompatActivity {


    RecyclerView recyclerView,recycleComboSelected,recyclePromoOffSelected;
    private List<OngoingBean.Service> mDataList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    HistoryDetailsAdapter adapter;
    RadioGroup rgFilter;
    Button btnDone;
    ImageView ivNoInternet;
    TextView tvName,tvGender,tvAppNo,tvTime,tvPaymentStatus,tvPaymentType,
            tvSubTotal,tvGstAmt,tvTotal,tvTitle;
MaterialCardView mvPayType;
CircleImageView profileImage;
CoordinatorLayout coordLayout;
SwipeRefreshLayout pullToRefresh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        tvName=findViewById(R.id.tvName);
        tvGender=findViewById(R.id.tvGender);
        tvAppNo=findViewById(R.id.tvAppNo);
        tvTime=findViewById(R.id.tvTime);
        tvPaymentStatus=findViewById(R.id.tvPaymentStatus);
        tvPaymentType=findViewById(R.id.tvPaymentType);
        tvSubTotal=findViewById(R.id.tvSubTotal);
        tvGstAmt=findViewById(R.id.tvGstAmt);
        tvTotal=findViewById(R.id.tvTotal);
        tvTitle=findViewById(R.id.tvTitle);
        btnDone=findViewById(R.id.btnDone);
        recycleComboSelected=findViewById(R.id.recycle_ComboSelected);
        recyclePromoOffSelected=findViewById(R.id.recycle_PromoOffSelected);
        ivNoInternet=findViewById(R.id.ivNoInternet);
        profileImage=findViewById(R.id.profileImage);
        coordLayout=findViewById(R.id.coordLayout);
        pullToRefresh=findViewById(R.id.pullToRefresh);
        setToolbar();
        recyclerView = findViewById(R.id.recyclerView);
        rgFilter=findViewById(R.id.rgFilter);
        mvPayType=findViewById(R.id.mvPayType);
        coordLayout.setVisibility(View.GONE);
        mvPayType.setVisibility(View.GONE);


    }

    public void setToolbar()
    {
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if(getIntent().getStringExtra("pageType").equalsIgnoreCase("endOtp"))
        {
            setTitle("Appointment Details");
            btnDone.setVisibility(View.VISIBLE);
            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(btnDone.getText().toString().equalsIgnoreCase("Enter End OTP")){
                        AppointmentBottomFragmentDetails.strOTP = Integer.parseInt(ongoingBean.getResult().get(0).getAptEndOtp());
                        startActivity(new Intent(HistoryDetailsActivity.this, OtpStartEndDialogActivity.class).putExtra("startEnd", "End").putExtra("from", "others").putExtra("appId",ongoingBean.getResult().get(0).getAptId()));
                    }else {
                        updateAcceptRejectStatus("4");
                    }

                }
            });
        }else
        {
            setTitle("Appointment History Details");
            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(btnDone.getText().toString().equalsIgnoreCase("Enter End OTP")){
                        AppointmentBottomFragmentDetails.strOTP = Integer.parseInt(ongoingBean.getResult().get(0).getAptEndOtp());
                        startActivity(new Intent(HistoryDetailsActivity.this, OtpStartEndDialogActivity.class).putExtra("startEnd", "End").putExtra("from", "others").putExtra("appId",ongoingBean.getResult().get(0).getAptId()));
                    }else {
                        updateAcceptRejectStatus("4");
                    }

                }
            });
        }

        int currentapiVersion = Build.VERSION.SDK_INT;
        if(Build.VERSION_CODES.P==currentapiVersion)
        {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }

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

        mLayoutManager = new LinearLayoutManager(HistoryDetailsActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter =new HistoryDetailsAdapter(HistoryDetailsActivity.this,mDataList);
        recyclerView.setAdapter(adapter);

        //combo list
        recycleComboSelected.setLayoutManager(new LinearLayoutManager(HistoryDetailsActivity.this));
        recycleComboSelected.setAdapter(new ComboListRequestAppDetailsAdapter((AppCompatActivity) HistoryDetailsActivity.this,result.getComboServices(),false));
        //promo offer list
        recyclePromoOffSelected.setLayoutManager(new LinearLayoutManager(HistoryDetailsActivity.this));
        recyclePromoOffSelected.setAdapter(new PromoOffListRequestAppDetailsAdapter((AppCompatActivity) HistoryDetailsActivity.this,result.getPromotionalServices(),false));
    }






    @Override
    public boolean onSupportNavigateUp() {
        super.onSupportNavigateUp();
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    OngoingBean ongoingBean;
    public static OngoingBean.Result result;
    private void getHistoryDetails() {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(HistoryDetailsActivity.this, 0);
        progressDialog.show();
        service.getHistoryDetails(getIntent().getStringExtra("appId"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OngoingBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OngoingBean loginBeanObj) {
                        progressDialog.dismiss();
                        ongoingBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(HistoryDetailsActivity.this,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (ongoingBean.getStatus() == 1) {
                            coordLayout.setVisibility(View.VISIBLE);
                            result = ongoingBean.getResult().get(0);
                            tvName.setText(result.getUserFName()+" "+result.getUserLName());
                            tvAppNo.setText(result.getAptId());
                            tvTime.setText(getTimeShow24to12HR(result.getAptTime()));
                            tvSubTotal.setText(result.getAptSubtotal()+"/-");
                            tvGstAmt.setText(result.getAptServiceCharges()+"/-");
                            tvTotal.setText(result.getAptAmount()+"/-");
                            Glide.with(HistoryDetailsActivity.this).load(result.getUserImg()).placeholder(R.drawable.placeholder_gray_circle).error(R.drawable.placeholder_gray_circle).into(profileImage);

                            if(result.getUserGender().equalsIgnoreCase("1"))
                            {
                                tvGender.setText("Male");
                            }
                            else if(result.getUserGender().equalsIgnoreCase("2"))
                            {
                                tvGender.setText("Female");
                            }else if(result.getUserGender().equalsIgnoreCase("3"))
                            {
                                tvGender.setText("Other");
                            }
                            if(result.getAptPaymentStatus().equalsIgnoreCase("0"))
                            {
                                if(!getIntent().getStringExtra("pageType").equalsIgnoreCase("endOtp")) {
                                    tvPaymentStatus.setText("NA");
                                }else {
                                    tvPaymentStatus.setText("Unpaid");
                                }
                                tvPaymentStatus.setTextColor(getResources().getColor(R.color.red));
                            }
                            else
                            {
                                tvPaymentStatus.setText("Paid");
                            }
                            if (result.getAptPaymentType().equalsIgnoreCase("1")) {
                                tvPaymentType.setText("Online");
                            } else {
                                tvPaymentType.setText("Cash");
                            }
                            if(result.getAptStatus().equalsIgnoreCase("6") && result.getAptPaymentStatus().equalsIgnoreCase("0"))
                            {
                                btnDone.setVisibility(View.GONE);
                            }else if(result.getAptStatus().equalsIgnoreCase("6") &&result.getAptPaymentStatus().equalsIgnoreCase("1"))
                            {
                                btnDone.setVisibility(View.VISIBLE);
                                btnDone.setText("End Service");
                            }
                            mDataList=result.getServices();
                            if(mDataList.size()==0)
                            {
                                tvTitle.setVisibility(View.GONE);
                            }else
                            {
                                tvTitle.setVisibility(View.VISIBLE);
                            }
                            initRecyclerView();
                        } else if (ongoingBean.getStatus() == 3) {
                            showFailToast(HistoryDetailsActivity.this,  "" + ongoingBean.getMsg());
                            logout(HistoryDetailsActivity.this);
                        }
//                        cPresenter.updateCoinList(allCurrencyList);
                        //setEmptyMsg(results, viewPager, ivNoAppoint);
                    }
                });
    }


    OfflineStatusBean offlineStatusBean;
    private void updateAcceptRejectStatus(final String status) {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(HistoryDetailsActivity.this, 0);
        progressDialog.show();
        service.updateAcceptRejectStatus(result.getAptId(),status,"")
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
                        showFailToast(HistoryDetailsActivity.this,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (offlineStatusBean.getStatus() == 1) {
                            showSuccessToast(HistoryDetailsActivity.this,getString(R.string.service_completed));
                            startActivity(new Intent(HistoryDetailsActivity.this, RatingActivity.class).putExtra("aptObj",result));
                            finish();



                        } else if (offlineStatusBean.getStatus() == 3) {
                            showFailToast(HistoryDetailsActivity.this,  "" + offlineStatusBean.getMsg());
                            logout(HistoryDetailsActivity.this);
                        }

                    }
                });
    }

    public NetworkChangeReceiver receiver;

    @Override
    protected void onResume() {
        super.onResume();
        checkInternet();

        pullToRefresh.setNestedScrollingEnabled(false);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to make your refresh action
                if (isNetworkAvailable(HistoryDetailsActivity.this)) {
                    getHistoryDetails();
                } else {
                    // Toast.makeText(mContext, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

                    setNoInternetMsg(recyclerView,ivNoInternet);
                }
                if(pullToRefresh.isRefreshing()) {
                    pullToRefresh.setRefreshing(false);
                }
            }
        });
        if (isNetworkAvailable(HistoryDetailsActivity.this)) {
            getHistoryDetails();
        } else {
            // Toast.makeText(mContext, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

            setNoInternetMsg(recyclerView,ivNoInternet);
        }

    }

    public void checkInternet() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver(this);
        registerReceiver(receiver, filter);
    }
    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {

        }
    }
}
