package com.prometteur.cpapp.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.appointment.AppointmentBottomFragmentDetails;
import com.prometteur.cpapp.appointment.RejectionActivity;
import com.prometteur.cpapp.beans.OfflineStatusBean;
import com.prometteur.cpapp.beans.OtpBean;
import com.prometteur.cpapp.drawer.DashboardMainActivity;
import com.prometteur.cpapp.history.HistoryDetailsActivity;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.Preferences;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.appointment.AppointmentBottomFragment.appointmentTabAdapter;
import static com.prometteur.cpapp.appointment.AppointmentBottomFragmentDetails.strOTP;
import static com.prometteur.cpapp.utils.Constants.BRANCHNAME;
import static com.prometteur.cpapp.utils.Constants.MOBNO;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showProgress;
import static com.prometteur.cpapp.utils.Utils.showSuccessToast;


public class OtpStartEndDialogActivity extends AppCompatActivity  {
    private OtpTextView otpTextView;
    @Bind(R.id.btnStartEnd)
    Button btnStartEnd;

    @Bind(R.id.tvLableStartEnd)
    TextView tvLableStartEnd;
    int strEnteredOTP=0;
    String startEndMsg="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_otp_start_end);
        ButterKnife.bind(this);
        if(getIntent().getStringExtra("startEnd").equalsIgnoreCase("Start"))
        {
            startEndMsg="Start";
        }else if(getIntent().getStringExtra("startEnd").equalsIgnoreCase("End"))
        {
            startEndMsg="End";
            btnStartEnd.setText("Proceed");
            tvLableStartEnd.setText("End OTP");
        }
      // getOTP();

        otpTextView = findViewById(R.id.otp_view);
        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // fired when user types something in the Otpbox
            }
            @Override
            public void onOTPComplete(String otp) {
                // fired when user has entered the OTP fully.
                //Toast.makeText(OtpStartEndDialogActivity.this, "The OTP is " + otp,  Toast.LENGTH_SHORT).show();
                strEnteredOTP=Integer.parseInt(otp);
            }
        });

        btnStartEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btnStartEnd.getText().toString().equalsIgnoreCase("Proceed"))
                {
                    //strOTP=4321; //TODO have to revert only for testing
                    if(strEnteredOTP==strOTP)
                    {
                        updateAcceptRejectStatus("6");

                    }else
                    {
                        showFailToast(OtpStartEndDialogActivity.this, "Enter valid OTP.");
                    }

                }else
                {
                  //  strOTP=1234; //TODO have to revert only for testing
                    if(strEnteredOTP==strOTP)
                    {
                        if(getIntent().getStringExtra("from").equalsIgnoreCase("todays"))
                        {
                            startEndMsg="End";
                            getOTP();
                        }else {
                            setResult(102);
                            finish();
                        }

                    }else
                    {
                        showFailToast(OtpStartEndDialogActivity.this, "Enter valid OTP.");
                    }
                }
            }
        });
    }



    OfflineStatusBean ongoingBean;
    private void updateAcceptRejectStatus(final String status) {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(OtpStartEndDialogActivity.this, 0);
        progressDialog.show();
        service.updateAcceptRejectStatus(getIntent().getStringExtra("appId"),status,"")
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
                        showFailToast(OtpStartEndDialogActivity.this,   getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (ongoingBean.getStatus() == 1) {
                            if(status.equalsIgnoreCase("5"))
                            {
                                showSuccessToast(OtpStartEndDialogActivity.this,  "Service Started");
                                startActivity(new Intent(OtpStartEndDialogActivity.this, DashboardMainActivity.class).putExtra("objNoti", (Bundle) null));
                               finish();
                            }else {
                                showSuccessToast(OtpStartEndDialogActivity.this, "Service Finished");
                                startActivity(new Intent(OtpStartEndDialogActivity.this, HistoryDetailsActivity.class).putExtra("pageType", "endOtp").putExtra("appId", getIntent().getStringExtra("appId")));
                                finish();
                            }
                        } else if (ongoingBean.getStatus() == 3) {
                            showFailToast(OtpStartEndDialogActivity.this,  "" + ongoingBean.getMsg());
                            logout(OtpStartEndDialogActivity.this);
                        }

                    }
                });
    }
    OtpBean otpBean;
    private void getOTP() {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(OtpStartEndDialogActivity.this, 0);
        progressDialog.show();
        service.getOTP(Preferences.getPreferenceValue(OtpStartEndDialogActivity.this,MOBNO),startEndMsg,Preferences.getPreferenceValue(OtpStartEndDialogActivity.this, BRANCHNAME),getIntent().getStringExtra("appId"))
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
                        showFailToast(OtpStartEndDialogActivity.this,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (otpBean.getStatus() == 1) {
                            AppointmentBottomFragmentDetails.strOTP =otpBean.getResult().getOtp();
                            if(startEndMsg.equalsIgnoreCase("End")) {
                                updateAcceptRejectStatus("5");
                            }
                        } else if (otpBean.getStatus() == 3) {
                            showFailToast(OtpStartEndDialogActivity.this, "" + otpBean.getMsg());
                            logout(OtpStartEndDialogActivity.this);
                        }

                    }
                });
    }

}
