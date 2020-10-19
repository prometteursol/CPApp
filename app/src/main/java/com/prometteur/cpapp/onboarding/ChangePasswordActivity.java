package com.prometteur.cpapp.onboarding;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.LoginBean;
import com.prometteur.cpapp.dialogs.SuccessDialogActivity;
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
import static com.prometteur.cpapp.utils.Constants.USERSESSION;
import static com.prometteur.cpapp.utils.Utils.isNetworkAvailable;
import static com.prometteur.cpapp.utils.Utils.isValidPassword;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showNoInternetDialog;
import static com.prometteur.cpapp.utils.Utils.showProgress;

public class ChangePasswordActivity extends AppCompatActivity  {

    @Bind(R.id.edtPassword)
    EditText edtPassword;

    @Bind(R.id.edtConfPassword)
    EditText edtConfPassword;

    @Bind(R.id.btnDone)
    Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        int currentapiVersion = Build.VERSION.SDK_INT;
        if(Build.VERSION_CODES.P==currentapiVersion)
        {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPassword.getText().toString().length()<8)
                {
                    edtPassword.setError("Password should be at least 8 characters long, must include letters, numbers and special characters");
                    edtPassword.requestFocus();
                }
                else if(!isValidPassword(edtPassword.getText().toString()))
                {
                    edtPassword.setError(getResources().getString(R.string.enter_password_with_special_characters));
                    edtPassword.requestFocus();
                }
                else if(edtConfPassword.getText().toString().isEmpty())
                {
                    edtConfPassword.setError("Enter confirm password");
                    edtConfPassword.requestFocus();
                }else if(edtConfPassword.getText().toString().length()<8)
                {
                    edtConfPassword.setError("Password doesn't match");
                    edtConfPassword.requestFocus();
                }else if(!isValidPassword(edtConfPassword.getText().toString()))
                {
                    edtConfPassword.setError("Password doesn't match");
                    edtConfPassword.requestFocus();
                }
                else if(!edtPassword.getText().toString().equals(edtConfPassword.getText().toString()))
                {
                    edtConfPassword.setError("Password doesn't match");
                    edtConfPassword.requestFocus();
                }else
                {
                    //TODO API call
                    if (isNetworkAvailable(ChangePasswordActivity.this)) {
                        getChangePass();
                    } else {
                       // Toast.makeText(ChangePasswordActivity.this, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();
                        showNoInternetDialog(ChangePasswordActivity.this);
                    }
//                    startActivity(new Intent(ChangePasswordActivity.this, SuccessDialogActivity.class));
                }
            }
        });
    }


    public void closeDialog(View view) {
        finish();
    }

public static int resultCodeChangePass=103;
    LoginBean loginBean;
    private void getChangePass() {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog=showProgress(ChangePasswordActivity.this,0);
        progressDialog.show();
        service.getChangePass(getIntent().getStringExtra("email"),getIntent().getStringExtra("mobNumber"), edtPassword.getText().toString())
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
                        showFailToast(ChangePasswordActivity.this, getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (loginBean.getStatus() == 1) {
                            startActivityForResult(new Intent(ChangePasswordActivity.this, SuccessDialogActivity.class).putExtra("msg",getResources().getString(R.string.your_password_have_been_changed)),resultCodeChangePass);

                        }else
                        {
                            showFailToast(ChangePasswordActivity.this,  "" + loginBean.getMsg());
                        }
//                        cPresenter.updateCoinList(allCurrencyList);
                    }
                });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCodeChangePass==resultCode)
        {
            startActivity(new Intent(ChangePasswordActivity.this,LoginActivity.class));
            finish();
        }
    }



}
