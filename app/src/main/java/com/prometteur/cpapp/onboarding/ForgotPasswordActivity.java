package com.prometteur.cpapp.onboarding;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.ForgotPassBean;
import com.prometteur.cpapp.beans.LoginBean;
import com.prometteur.cpapp.drawer.DashboardMainActivity;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.Preferences;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.utils.Constants.ISLOGIN;
import static com.prometteur.cpapp.utils.Constants.MOBNO;
import static com.prometteur.cpapp.utils.Constants.USERID;
import static com.prometteur.cpapp.utils.Utils.isNetworkAvailable;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showNoInternetDialog;
import static com.prometteur.cpapp.utils.Utils.showProgress;
import static com.prometteur.cpapp.utils.Utils.showSuccessToast;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.edtEmail)
    EditText edtEmail;

    @Bind(R.id.edtMobileNo)
    EditText edtMobileNo;

    @Bind(R.id.btnRequestOTP)
    Button btnRequestOTP;
    String userEmail="",userMob="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password);
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion>=Build.VERSION_CODES.P ) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }
        ButterKnife.bind(this);
        setListeners();

    }

    private void setListeners() {
        btnRequestOTP.setOnClickListener(this);
    }

String inputType="";
    final long[] mLastClickTime = {0};
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRequestOTP:
                if (SystemClock.elapsedRealtime() - mLastClickTime[0] < 5000) {
                    return;
                }
                mLastClickTime[0] = SystemClock.elapsedRealtime();
                userEmail="";
                userMob="";
                if(!edtEmail.getText().toString().isEmpty() || !edtMobileNo.getText().toString().isEmpty()) {
                    if (!edtEmail.getText().toString().isEmpty()) {
                        if (edtEmail.getText().toString().length() < 9) {
                            edtEmail.setError(getResources().getString(R.string.please_enter_registered_email_id));
                            edtEmail.requestFocus();
                        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
                            edtEmail.setError(getResources().getString(R.string.please_enter_valid_registered_email_id));
                            edtEmail.requestFocus();
                        } else {
                            userEmail = edtEmail.getText().toString();
                            if (isNetworkAvailable(ForgotPasswordActivity.this)) {
                                inputType = "1";
                                getForgotPass();
                            } else {
                                // Toast.makeText(ForgotPasswordActivity.this, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();
                                showNoInternetDialog(ForgotPasswordActivity.this);
                            }
                        }
                    }else if (!edtMobileNo.getText().toString().isEmpty()) {
                        if (edtMobileNo.getText().toString().length() < 10) {
                            edtMobileNo.setError(getResources().getString(R.string.please_enter_registered_mobile));
                            edtMobileNo.requestFocus();
                        } else {
                            userMob = edtMobileNo.getText().toString();
                            if (isNetworkAvailable(ForgotPasswordActivity.this)) {
                                inputType = "2";
                                getForgotPass();
                            } else {
                                showNoInternetDialog(ForgotPasswordActivity.this);
                                //Toast.makeText(ForgotPasswordActivity.this, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }else {
                    showFailToast(ForgotPasswordActivity.this, "Please enter Registered Email or Mobile number");
                }

                /*startActivity(new Intent(ForgotPasswordActivity.this, OtpVerificationActivity.class).putExtra("otp","1234").putExtra("mobNumber",userMob).putExtra("email",userEmail));
                finish();*/

                break;
        }
    }

    ForgotPassBean loginBean;
    private void getForgotPass() {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog=showProgress(ForgotPasswordActivity.this,0);
        progressDialog.show();
        service.getForgotPass(userEmail,userMob)
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
                        showFailToast(ForgotPasswordActivity.this,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (loginBean.getStatus() == 1) {
                            if(!userEmail.isEmpty())
                            {
                                showSuccessToast(ForgotPasswordActivity.this,"OTP sent on Email");
                            }else if(!userMob.isEmpty())
                            {
                                showSuccessToast(ForgotPasswordActivity.this,"OTP sent on Mobile number");
                            }
                            startActivity(new Intent(ForgotPasswordActivity.this, OtpVerificationActivity.class).putExtra("otp",""+loginBean.getResult().get(0).getOtp()).putExtra("mobNumber",userMob).putExtra("email",userEmail));
                            //finish();
                        }else
                        {
                            if(inputType.equalsIgnoreCase("1")){
                                if(loginBean.getMsg().contains("Email Not"))
                                {
                                    edtEmail.setError(getResources().getString(R.string.please_enter_registered_email_id));
                                    edtEmail.requestFocus();
                                }else{
                                    showFailToast(ForgotPasswordActivity.this,  "" + loginBean.getMsg());
                                }
                            }else if(inputType.equalsIgnoreCase("2"))
                            {
                                if(loginBean.getMsg().contains("Mobile No Not"))
                                {
                                    edtMobileNo.setError(getResources().getString(R.string.please_enter_registered_mobile));
                                    edtMobileNo.requestFocus();
                                } else if(loginBean.getMsg().contains("Email Not"))
                                {
                                    edtEmail.setError(getResources().getString(R.string.please_enter_registered_email_id));
                                    edtEmail.requestFocus();
                                }else{
                                    showFailToast(ForgotPasswordActivity.this,  "" + loginBean.getMsg());
                                }
                            }

                        }
                    }
                });


    }
}
