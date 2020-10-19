package com.prometteur.cpapp.appointment;

import android.app.Dialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.CancelAppBean;
import com.prometteur.cpapp.beans.HistoryDataModel;
import com.prometteur.cpapp.beans.OfflineStatusBean;
import com.prometteur.cpapp.beans.OrderStatus;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.NetworkChangeReceiver;
import com.prometteur.cpapp.utils.RadioButtonCustomFont;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showProgress;
import static com.prometteur.cpapp.utils.Utils.showSuccessToast;


public class RejectionActivity extends AppCompatActivity {


    RadioGroup rgFilter;
    Button btnSubmit;
    OfflineStatusBean ongoingBean;
    private ArrayList<HistoryDataModel> mDataList = new ArrayList<HistoryDataModel>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejection);

        setToolbar();
        btnSubmit = findViewById(R.id.btnSubmit);
        rgFilter = findViewById(R.id.rgFilter);
        setDataListItems();
        final String[] val = {""};
        rgFilter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbMonth = findViewById(checkedId);
                val[0] = rbMonth.getText().toString();
                Log.i("RadioButton", val[0]);

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!val[0].isEmpty()) {
                    if(getIntent().getStringExtra("penaltyAmt")!=null) {
                        if (!getIntent().getStringExtra("penaltyAmt").equalsIgnoreCase("0")) {
                            getCancelAppoint(val[0]);
                        } else {
                            updateAcceptRejectStatus("2", val[0]);
                        }
                    }else {
                        updateAcceptRejectStatus("2", val[0]);
                    }

                } else {
                    showSuccessToast(RejectionActivity.this, "Please select reason.");
                }
            }
        });
    }

    public void setToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Rejection Reasons");
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (Build.VERSION_CODES.P == currentapiVersion) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }

    }

    private void setDataListItems() {
        mDataList = new ArrayList<HistoryDataModel>();
        mDataList.add(new HistoryDataModel("Product not available",               "2017-02-12 08:00:00", OrderStatus.INACTIVE));
        mDataList.add(new HistoryDataModel("Operator not available",     "2017-02-12 08:00:00", OrderStatus.ACTIVE));
        mDataList.add(new HistoryDataModel("Power Failure", "2017-02-11 21:00:00", OrderStatus.COMPLETED));
        mDataList.add(new HistoryDataModel("Sudden unforeseen issues",        "2017-02-11 18:00:00", OrderStatus.COMPLETED));
        mDataList.add(new HistoryDataModel("Other",     "2017-02-11 09:30:00", OrderStatus.COMPLETED));
        rgFilter.removeAllViews();
        for (int i = 0; i < mDataList.size(); i++) {

            RadioButtonCustomFont radioButton = new RadioButtonCustomFont(this);
            radioButton.setText(mDataList.get(i).getMessage());
            radioButton.setId(1000 + i);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(15, 15, 15, 15);
            radioButton.setLayoutParams(params);
            radioButton.setBackground(getResources().getDrawable(R.drawable.bg_bottom_line_rejection));
            rgFilter.addView(radioButton);
        }

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

    private void updateAcceptRejectStatus(String status, String reson) {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(RejectionActivity.this, 0);
        progressDialog.show();
        service.updateAcceptRejectStatus(getIntent().getStringExtra("aptId"), status, reson)
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
                        showFailToast(RejectionActivity.this,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (ongoingBean.getStatus() == 1) {
                            if(getIntent().getStringExtra("msgType").equalsIgnoreCase("reject")) {
                                showSuccessToast(RejectionActivity.this, getResources().getString(R.string.request_rejected));
                            }else
                            {
                                showSuccessToast(RejectionActivity.this, getResources().getString(R.string.request_cancelled));
                            }
                            setResult(101);
                            finish();
                        } else if (ongoingBean.getStatus() == 3) {
                            showFailToast(RejectionActivity.this,  "" + ongoingBean.getMsg());
                            logout(RejectionActivity.this);
                        }

                    }
                });
    }
    public NetworkChangeReceiver receiver;

    @Override
    protected void onResume() {
        super.onResume();
        checkInternet();
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


    CancelAppBean appointmentBean;
    private void getCancelAppoint(String reason) {

        final ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(RejectionActivity.this, 0);
        progressDialog.show();
        String penalty="";
        if(getIntent().getStringExtra("penaltyAmt")!=null) {
            if (getIntent().getStringExtra("penaltyAmt").equalsIgnoreCase("0")) {
                penalty = "";
            } else {
                penalty = getIntent().getStringExtra("penaltyAmt");
            }
        }
        service.getAppointmentCancel(getIntent().getStringExtra("aptId"),"7",reason,penalty)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CancelAppBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CancelAppBean loginBeanObj) {
                        progressDialog.dismiss();
                        appointmentBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        // showFailToast(RejectionActivity.this, getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();

                        if (appointmentBean.getStatus() == 1) {
                            showSuccessToast(RejectionActivity.this,"Appointment Cancelled");
                            setResult(101);
                            finish();
                        } else if (appointmentBean.getStatus() == 3) {
                            showFailToast(RejectionActivity.this, "" + appointmentBean.getMsg());
                            logout(RejectionActivity.this);
                        }
                        //setEmptyMsg(mDataList, comboPageBinding.recycleCombolist, ivNoData);
                    }
                });
    }
}
