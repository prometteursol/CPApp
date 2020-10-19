package com.prometteur.cpapp.onboarding;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.LoginBean;
import com.prometteur.cpapp.beans.NotificationBean;
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

import static com.prometteur.cpapp.utils.Constants.APTCOUNT;
import static com.prometteur.cpapp.utils.Constants.BRANCHADDRESS;
import static com.prometteur.cpapp.utils.Constants.BRANCHCLOSEDSTATUS;
import static com.prometteur.cpapp.utils.Constants.BRANCHID;
import static com.prometteur.cpapp.utils.Constants.BRANCHIMG;
import static com.prometteur.cpapp.utils.Constants.BRANCHNAME;
import static com.prometteur.cpapp.utils.Constants.BRANCHOPENSTATUS;
import static com.prometteur.cpapp.utils.Constants.ISLOGIN;
import static com.prometteur.cpapp.utils.Constants.MOBNO;
import static com.prometteur.cpapp.utils.Constants.NOTCOUNT;
import static com.prometteur.cpapp.utils.Constants.USERID;
import static com.prometteur.cpapp.utils.Constants.USERSESSION;
import static com.prometteur.cpapp.utils.Utils.isNetworkAvailable;
import static com.prometteur.cpapp.utils.Utils.isValidPassword;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showNoInternetDialog;
import static com.prometteur.cpapp.utils.Utils.showProgress;
import static com.prometteur.cpapp.utils.Utils.showSuccessToast;


public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.edtUsername)
    EditText edtUsername;
    @Bind(R.id.edtPassword)
    EditText edtPassword;
    @Bind(R.id.tvExpressInterest)
    TextView tvExpressInterest;
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.tvForgotPassword)
    TextView tvForgotPassword;
    //back to exit
    boolean doubleBackToExitPressedOnce = false;
    LoginBean loginBean;
    private String userName;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setTheme(R.style.AppThemeLogin);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (Build.VERSION_CODES.P == currentapiVersion) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Log.i("width and height ", width + " " + height);

        ButterKnife.bind(this);
        setListeners();
        if (Preferences.getPreferenceValueInt(LoginActivity.this, ISLOGIN) == 1) {
            NotificationBean.Result result= (NotificationBean.Result) getIntent().getSerializableExtra("objNoti");
            try {
                if (getIntent().getExtras() != null) {
                    result = new NotificationBean.Result();
                    result.setNotiType(getIntent().getExtras().get("noti_type").toString());
                    result.setNotiAppointmentId(getIntent().getExtras().get("noti_appointment_id").toString());
                    Log.i("firebase data extras", getIntent().getExtras().get("noti_type").toString());
                    int notCount= Preferences.getPreferenceValueInt(getApplicationContext(), NOTCOUNT);
                    if(notCount>0) {
                        Preferences.setPreferenceValue(getApplicationContext(), NOTCOUNT, (notCount + 1));
                    }else
                    {
                        Preferences.setPreferenceValue(getApplicationContext(), NOTCOUNT,  1);
                    }
                    int aptCount= Preferences.getPreferenceValueInt(getApplicationContext(), APTCOUNT);
                    if(aptCount>0) {
                        Preferences.setPreferenceValue(getApplicationContext(), APTCOUNT, (aptCount + 1));
                    }else
                    {
                        Preferences.setPreferenceValue(getApplicationContext(), APTCOUNT,  1);
                    }
                }

            }catch (Exception e)
            {
                result= (NotificationBean.Result) getIntent().getSerializableExtra("objNoti");
                e.printStackTrace();
            }
            startActivity(new Intent(LoginActivity.this, DashboardMainActivity.class).putExtra("objNoti", result));
            finishAffinity();
        }


        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
//                finish();
            }
        });

    }

    private boolean isValidData() {

        boolean isValidData = true;

        try {


            if (userName.length() == 0) {

                edtUsername.setError(getResources().getString(R.string.enter_username));
                edtUsername.requestFocus();
                isValidData = false;

            } else if (!(Patterns.EMAIL_ADDRESS.matcher(userName).matches())) {

                edtUsername.setError(getResources().getString(R.string.enter_valid_username));
                edtUsername.requestFocus();
                isValidData = false;

            } else if (userPassword.length() == 0) {

                edtPassword.setError(getResources().getString(R.string.enter_password));
                edtPassword.requestFocus();

                isValidData = false;

            } /*else if (!isValidPassword(userPassword)) {

                edtPassword.setError(getResources().getString(R.string.enter_password_with_special_characters));
                edtPassword.requestFocus();

                isValidData = false;
            }*/


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return isValidData;
    }

    private void getLogIn() {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(LoginActivity.this, 0);
        progressDialog.show();
        String userFcmKey= FirebaseInstanceId.getInstance().getToken();
        Log.i("FCM Key",userFcmKey);
        service.getLogin(userName, userPassword,userFcmKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBeanObj) {
                        progressDialog.dismiss();
                        loginBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(LoginActivity.this, getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (loginBean.getStatus() == 1) {
                            LoginBean.Result result = loginBean.getResult().get(0);
                            if (result.getUserStatus().equalsIgnoreCase("0")) {
                                showFailToast(LoginActivity.this, "Your account has been disabled by admin");
                            } else {
                                Preferences.setPreferenceValue(LoginActivity.this, USERID, result.getUserId());
                                Preferences.setPreferenceValue(LoginActivity.this, MOBNO, result.getUserMbNo());
                                Preferences.setPreferenceValue(LoginActivity.this, ISLOGIN, 1);
                                Preferences.setPreferenceValue(LoginActivity.this, USERSESSION, result.getUserSession());
                                Preferences.setPreferenceValue(LoginActivity.this, BRANCHID, result.getUserBranchId());
                                Preferences.setPreferenceValue(LoginActivity.this, BRANCHOPENSTATUS, result.getBranOpenStatus());
                                Preferences.setPreferenceValue(LoginActivity.this, BRANCHCLOSEDSTATUS, result.getBranClosed());
                                Preferences.setPreferenceValue(LoginActivity.this, BRANCHNAME  ,result.getBranName());
                                Preferences.setPreferenceValue(LoginActivity.this, BRANCHIMG ,result.getBranImg());
                                Preferences.setPreferenceValue(LoginActivity.this, BRANCHADDRESS, result.getBranCity());
                                Preferences.setPreferenceValue(LoginActivity.this, APTCOUNT, loginBean.getAptRequest());
                                startActivity(new Intent(LoginActivity.this, DashboardMainActivity.class).putExtra("objNoti", (Bundle) null));
                                finish();
                            }
                        } else {
                            showFailToast(LoginActivity.this,  "" + loginBean.getMsg());
                        }
//                        cPresenter.updateCoinList(allCurrencyList);
                    }
                });


    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        showSuccessToast(this, "Please click BACK again to exit");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void setListeners() {
        tvExpressInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ExpressInterestActivity.class).putExtra("editMode", "new"));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                userName = edtUsername.getText().toString();
                userPassword = edtPassword.getText().toString();
                if (isValidData()) {

                    if (isNetworkAvailable(LoginActivity.this)) {
                        getLogIn();
                    } else {
                        // Toast.makeText(LoginActivity.this, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

                        showNoInternetDialog(LoginActivity.this);
                    }

                }
               /* startActivity(new Intent(LoginActivity.this, DashboardMainActivity.class));
                finish();*/

            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                finish();
            }
        });
    }
}
