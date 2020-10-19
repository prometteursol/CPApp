package com.prometteur.cpapp.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.OfflineStatusBean;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.drawer.DashboardMainActivity;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.Preferences;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.drawer.DashboardMainActivity.swStatus;
import static com.prometteur.cpapp.utils.Constants.BRANCHIDVAL;
import static com.prometteur.cpapp.utils.Constants.BRANCHOPENSTATUS;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showProgress;
import static com.prometteur.cpapp.utils.Utils.showSuccessToast;

public class OfflineTimeDialogActivity extends AppCompatActivity {
    Button btnOk, btnCancel;
    RadioGroup rgOfflineTime;
    OfflineStatusBean ongoingBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_offline_time);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);
        rgOfflineTime = findViewById(R.id.rgOfflineTime);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rgb = findViewById(rgOfflineTime.getCheckedRadioButtonId());
                String hours = "";
                switch (rgOfflineTime.getCheckedRadioButtonId()) {
                    case R.id.rbOneHr:
                        hours = "1";
                        break;case R.id.rbTwoHr:
                        hours = "2";
                        break;case R.id.rbFourHr:
                        hours = "4";
                        break;case R.id.rbEightHr:
                        hours = "8";
                        break;case R.id.rbCompleteDay:
                        hours = "24";
                        break;
                }

                updateActiveInactiveStatus(hours);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                swStatus.setChecked(!getIntent().getBooleanExtra("swichVal", false));
                finish();
            }
        });

    }

    private void updateActiveInactiveStatus(String hours) {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(OfflineTimeDialogActivity.this, 0);
        progressDialog.show();
        service.updateActiveInactiveStatus(BRANCHIDVAL, hours)
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
                        swStatus.setChecked(!getIntent().getBooleanExtra("swichVal", false));
                        showFailToast(OfflineTimeDialogActivity.this,  getResources().getString(R.string.went_wrong));
                        finish();
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (ongoingBean.getStatus() == 1) {
                            Preferences.setPreferenceValue(OfflineTimeDialogActivity.this, BRANCHOPENSTATUS, "0");
                            swStatus.setChecked(getIntent().getBooleanExtra("swichVal", false));
                            showSuccessToast(OfflineTimeDialogActivity.this,"Salon is Offline now");
                        } else if (ongoingBean.getStatus() == 3) {
                            swStatus.setChecked(!getIntent().getBooleanExtra("swichVal", false));
                            showFailToast(OfflineTimeDialogActivity.this,  "" + ongoingBean.getMsg());
                            logout(OfflineTimeDialogActivity.this);
                        }
                        finish();
                    }
                });

    }


}
