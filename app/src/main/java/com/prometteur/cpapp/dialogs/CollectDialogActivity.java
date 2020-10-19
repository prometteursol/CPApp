package com.prometteur.cpapp.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.OfflineStatusBean;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.databinding.DialogCollectCashBinding;
import com.prometteur.cpapp.history.HistoryDetailsActivity;
import com.prometteur.cpapp.rating.RatingActivity;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showProgress;
import static com.prometteur.cpapp.utils.Utils.showSuccessToast;

public class CollectDialogActivity extends AppCompatActivity  {
    DialogCollectCashBinding collectCashBinding;
    AppCompatActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        collectCashBinding=DialogCollectCashBinding.inflate(getLayoutInflater());
        setContentView(collectCashBinding.getRoot());
        collectCashBinding.tvAmount.setText("₹ "+getIntent().getStringExtra("payAmt"));
        collectCashBinding.tvUserNme.setText(""+getIntent().getStringExtra("userName"));
        collectCashBinding.tvAppointNo.setText(""+getIntent().getStringExtra("appId"));

        collectCashBinding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAcceptRejectStatus("4",getIntent().getStringExtra("appId"), HistoryDetailsActivity.result);
            }
        });

    }


    public void closeDialog(View view) {
        finish();
    }



    OfflineStatusBean offlineStatusBean;
    private void updateAcceptRejectStatus(final String status, String aptId, final OngoingBean.Result result) {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(mContext, 0);
        progressDialog.show();
        service.updateAcceptRejectStatus(aptId,status,"")
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
                        showFailToast(mContext,  mContext.getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (offlineStatusBean.getStatus() == 1) {
                            showSuccessToast(mContext,mContext.getString(R.string.service_completed));
                            mContext.startActivity(new Intent(mContext, RatingActivity.class).putExtra("aptObj",result));
                            ((Activity)mContext).finish();

                        } else if (offlineStatusBean.getStatus() == 3) {
                            showFailToast(mContext,  "" + offlineStatusBean.getMsg());
                            logout(mContext);
                        }

                    }
                });
    }
}
