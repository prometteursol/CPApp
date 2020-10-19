package com.prometteur.cpapp.onboarding;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.ForgotPassBean;
import com.prometteur.cpapp.dialogs.OtpStartEndDialogActivity;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showInfoToast;
import static com.prometteur.cpapp.utils.Utils.showProgress;
import static com.prometteur.cpapp.utils.Utils.showSuccessToast;

public class OtpVerificationActivity extends AppCompatActivity  {
    private OtpTextView otpTextView;
    Button btnVerify;
    TextView tvEmailOrMobNoText,tvEmailOrMobNo,tvResend;
    String apiOTP="0";
    String strOtp,strEmailOrMobNo;
   public static final int RESOLVE_HINT=9090;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_otp_verification);
        int currentapiVersion = Build.VERSION.SDK_INT;
        if(Build.VERSION_CODES.P==currentapiVersion)
        {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }



        tvEmailOrMobNoText = findViewById(R.id.tvEmailOrMobNoText);
        tvEmailOrMobNo = findViewById(R.id.tvEmailOrMobNo);
        btnVerify = findViewById(R.id.btnVerify);
        otpTextView = findViewById(R.id.otpTextView);
        tvResend = findViewById(R.id.tvResend);

        if(getIntent().getStringExtra("email").contains("@"))
        {
            tvEmailOrMobNoText.setText(getResources().getString(R.string.please_verify_your_email_to_continue_n_otp_sent_on_email));
            strEmailOrMobNo=getIntent().getStringExtra("email");
            tvEmailOrMobNo.setText(strEmailOrMobNo);
        }else
        {
            tvEmailOrMobNoText.setText(getResources().getString(R.string.please_verify_your_mobile_to_continue_n_otp_sent_on_mobile_number));
            strEmailOrMobNo=getIntent().getStringExtra("mobNumber");
            tvEmailOrMobNo.setText(strEmailOrMobNo);
        }


        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // fired when user types something in the Otpbox
            }
            @Override
            public void onOTPComplete(String otp) {
                // fired when user has entered the OTP fully.
                strOtp=otp;
                //Toast.makeText(OtpVerificationActivity.this, "The OTP is " + otp,  Toast.LENGTH_SHORT).show();

            }
        });

        apiOTP=getIntent().getStringExtra("otp");
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(apiOTP.equalsIgnoreCase(strOtp))
                {
                    startActivity(new Intent(OtpVerificationActivity.this,ChangePasswordActivity.class).putExtra("email",getIntent().getStringExtra("email")).putExtra("mobNumber",getIntent().getStringExtra("mobNumber")));
                    finish();
                }else{
                    showFailToast(OtpVerificationActivity.this, "OTP is incorrect");
                }

//                startActivity(new Intent(OtpVerificationActivity.this,ChangePasswordActivity.class).putExtra("email",getIntent().getStringExtra("email")).putExtra("mobNumber",getIntent().getStringExtra("mobNumber")));
//                finish();
            }
        });


        tvResend.setEnabled(false);

        new CountDownTimer(180000, 1000) {

            public void onTick(long millisUntilFinished) {

                tvResend.setText("Resend OTP (" + millisUntilFinished / 1000 + ")");
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                apiOTP="0";
                tvResend.setEnabled(true);
                tvResend.setText("Resend OTP ");
            }

        }.start();

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResend.setEnabled(false);
                otpTextView.setOTP("");
                getForgotPass();
                    new CountDownTimer(180000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            tvResend.setText("Resend OTP (" + millisUntilFinished / 1000 + ")");

                        }

                        public void onFinish() {
                            tvResend.setEnabled(true);
                            tvResend.setText("Resend OTP ");
                        }

                    }.start();

                    if(!getIntent().getStringExtra("email").isEmpty()) {
                        showSuccessToast(OtpVerificationActivity.this, "OTP is sent on " + getIntent().getStringExtra("email"));
                    }else {
                        showSuccessToast(OtpVerificationActivity.this,"OTP is sent on " + getIntent().getStringExtra("mobNumber"));
                    }

            }
        });

    }





    ForgotPassBean loginBean;
    private void getForgotPass() {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog=showProgress(OtpVerificationActivity.this,0);
        progressDialog.show();
        service.getForgotPass(getIntent().getStringExtra("email"),getIntent().getStringExtra("mobNumber"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ForgotPassBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ForgotPassBean loginBeanObj) {
                        progressDialog.dismiss();
                        loginBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(OtpVerificationActivity.this,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (loginBean.getStatus() == 1) {
                            apiOTP=""+loginBean.getResult().get(0).getOtp();

                        }else
                        {
                            showFailToast(OtpVerificationActivity.this,  "" + loginBean.getMsg());
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
