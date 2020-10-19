package com.prometteur.cpapp.rating;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.appointment.AppointmentBottomFragmentDetails;
import com.prometteur.cpapp.beans.OfflineStatusBean;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.beans.ReviewBean;
import com.prometteur.cpapp.drawer.DashboardMainActivity;
import com.prometteur.cpapp.history.HistoryDetailsActivity;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.NetworkChangeReceiver;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.appointment.AppointmentBottomFragment.appointmentTabAdapter;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showProgress;
import static com.prometteur.cpapp.utils.Utils.showSuccessToast;

public class RatingActivity extends AppCompatActivity {

    RatingBar ratingBar;
    EditText edtReview;
    Button btnSave;
    OngoingBean.Result result;
    ReviewBean offlineStatusBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        setToolbar();
        ratingBar = findViewById(R.id.ratingBar);
        edtReview = findViewById(R.id.edtReview);
        btnSave = findViewById(R.id.btnSave);
        result = (OngoingBean.Result) getIntent().getSerializableExtra("aptObj");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ratingBar.getRating() != 0.0) {
                    setRatings();
                } else {
                    showFailToast(RatingActivity.this, "Write reviews / ratings to proceed.");
                }
            }
        });
    }

    public void setToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Review & Rating");
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (Build.VERSION_CODES.P == currentapiVersion) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        super.onSupportNavigateUp();
        startActivity(new Intent(RatingActivity.this, DashboardMainActivity.class).putExtra("objNoti", (Bundle) null));
        ((Activity)RatingActivity.this).finishAffinity();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RatingActivity.this, DashboardMainActivity.class).putExtra("objNoti", (Bundle) null));
        ((Activity)RatingActivity.this).finishAffinity();
    }

    private void setRatings() {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(RatingActivity.this, 0);
        progressDialog.show();
        service.setReviews(result.getAptUserId(), String.valueOf(ratingBar.getRating()), edtReview.getText().toString(), result.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReviewBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ReviewBean loginBeanObj) {
                        progressDialog.dismiss();
                        offlineStatusBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(RatingActivity.this,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (offlineStatusBean.getStatus() == 1) {
                            showSuccessToast(RatingActivity.this,  "Review submitted" );
                            startActivity(new Intent(RatingActivity.this, DashboardMainActivity.class).putExtra("objNoti", (Bundle) null));
                            ((Activity)RatingActivity.this).finishAffinity();

                        } else if (offlineStatusBean.getStatus() == 3) {
                            showFailToast(RatingActivity.this,  "" + offlineStatusBean.getMsg());
                            logout(RatingActivity.this);
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


}
