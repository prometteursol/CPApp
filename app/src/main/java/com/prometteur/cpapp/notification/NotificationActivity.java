package com.prometteur.cpapp.notification;

import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.HistoryDetailsAdapter;
import com.prometteur.cpapp.adapter.NotificationListAdapter;
import com.prometteur.cpapp.beans.HistoryDataModel;
import com.prometteur.cpapp.beans.InvoiceBean;
import com.prometteur.cpapp.beans.NotificationBean;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.beans.OrderStatus;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.NetworkChangeReceiver;
import com.prometteur.cpapp.utils.Preferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.utils.Constants.BRANCHIDVAL;
import static com.prometteur.cpapp.utils.Constants.NOTCOUNT;
import static com.prometteur.cpapp.utils.Constants.USERID;
import static com.prometteur.cpapp.utils.Constants.USERIDVAL;
import static com.prometteur.cpapp.utils.Utils.isNetworkAvailable;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.setEmptyMsg;
import static com.prometteur.cpapp.utils.Utils.setNoInternetMsg;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showProgress;


public class NotificationActivity extends AppCompatActivity {
    
    private List<NotificationBean.Result> mDataList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    NotificationListAdapter adapter;
    RadioGroup rgFilter;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.ivNoData)
    ImageView ivNoData;
    @Bind(R.id.ivNoInternet)
    ImageView ivNoInternet;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        ButterKnife.bind(this);
        setToolbar();
        rgFilter=findViewById(R.id.rgFilter);

        if (isNetworkAvailable(NotificationActivity.this)) {
            getNotifications();
        } else {
            // Toast.makeText(NotificationActivity.this, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

            setNoInternetMsg(recyclerView, ivNoInternet);
        }

    }

    public void setToolbar()
    {
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Notifications");

            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);


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

        mLayoutManager = new LinearLayoutManager(NotificationActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter =new NotificationListAdapter(NotificationActivity.this,mDataList);
        recyclerView.setAdapter(adapter);
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


    NotificationBean notificationBean;
    private void getNotifications() {

        final ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(NotificationActivity.this, 0);
        progressDialog.show();
        service.getNotifications(BRANCHIDVAL,USERIDVAL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NotificationBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NotificationBean loginBeanObj) {
                        progressDialog.dismiss();
                        notificationBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(NotificationActivity.this,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        Preferences.setPreferenceValue(getApplicationContext(), NOTCOUNT, 0);
                        if (notificationBean.getStatus() == 1) {

                            mDataList = notificationBean.getResult();
                            initRecyclerView();
                        } else if (notificationBean.getStatus() == 3) {
                            showFailToast(NotificationActivity.this,  "" + notificationBean.getMsg());
                            logout(NotificationActivity.this);
                        }
                        setEmptyMsg(mDataList, recyclerView, ivNoData);
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
