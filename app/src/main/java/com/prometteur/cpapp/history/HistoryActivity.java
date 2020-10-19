package com.prometteur.cpapp.history;

import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.HistoryAdapter;
import com.prometteur.cpapp.beans.DateObject;
import com.prometteur.cpapp.beans.HistoryDataModel;
import com.prometteur.cpapp.beans.HistoryDataModelObject;
import com.prometteur.cpapp.beans.ListObject;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.DateParser;
import com.prometteur.cpapp.utils.NetworkChangeReceiver;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.utils.Constants.BRANCHIDVAL;
import static com.prometteur.cpapp.utils.Utils.isNetworkAvailable;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.setEmptyMsg;
import static com.prometteur.cpapp.utils.Utils.setNoInternetMsg;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showProgress;


public class HistoryActivity extends AppCompatActivity {

    Context mContext;
    RecyclerView recyclerView;
    HistoryAdapter adapter;
    RadioGroup rgFilter;
    ImageView ivNoData, ivNoInternet;
    List<OngoingBean.Result> results = new ArrayList<>();
    OngoingBean ongoingBean;
    private List<HistoryDataModel> mDataList = new ArrayList<HistoryDataModel>();
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mContext = this;
        setToolbar();
        recyclerView = findViewById(R.id.recyclerView);
        rgFilter = findViewById(R.id.rgFilter);
        ivNoData = findViewById(R.id.ivNoData);
        ivNoInternet = findViewById(R.id.ivNoInternet);


        if (isNetworkAvailable(mContext)) {
            getAppointmentHistory("");
        } else {
            // Toast.makeText(mContext, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

            setNoInternetMsg(recyclerView, ivNoInternet);
        }


        rgFilter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbMonth = findViewById(checkedId);
                if (rbMonth.getText().toString().equalsIgnoreCase("Last Month")) {
                    if (isNetworkAvailable(mContext)) {
                        getAppointmentHistory("1");
                    } else {
                        // Toast.makeText(mContext, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

                        setNoInternetMsg(recyclerView, ivNoInternet);
                    }
                } else {
                    if (isNetworkAvailable(mContext)) {
                        getAppointmentHistory("3");
                    } else {
                        // Toast.makeText(mContext, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

                        setNoInternetMsg(recyclerView, ivNoInternet);
                    }
                }
            }
        });

    }

    public void setToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Appointment History");
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (Build.VERSION_CODES.P == currentapiVersion) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }

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

        mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new HistoryAdapter(HistoryActivity.this, null);
        recyclerView.setAdapter(adapter);
    }

    private void groupDataIntoHashMap(List<OngoingBean.Result> chatModelList) {
        LinkedHashMap<String, Set<OngoingBean.Result>> groupedHashMap = new LinkedHashMap<>();
        Set<OngoingBean.Result> list = null;
        for (OngoingBean.Result chatModel : chatModelList) {
            //Log.d(TAG, travelActivityDTO.toString());
            String hashMapKey = DateParser.convertDateToString(chatModel.getAptDate());
            //Log.d(TAG, "start date: " + DateParser.convertDateToString(travelActivityDTO.getStartDate()));
            if (groupedHashMap.containsKey(hashMapKey)) {
                // The key is already in the HashMap; add the pojo object
                // against the existing key.
                groupedHashMap.get(hashMapKey).add(chatModel);
            } else {
                // The key is not there in the HashMap; create a new key-value pair
                list = new LinkedHashSet<>();
                list.add(chatModel);
                groupedHashMap.put(hashMapKey, list);
            }
        }
        //Generate list from map
        generateListFromMap(groupedHashMap);

    }

    private List<ListObject> generateListFromMap(LinkedHashMap<String, Set<OngoingBean.Result>> groupedHashMap) {
        // We linearly add every item into the consolidatedList.
        List<ListObject> consolidatedList = new ArrayList<>();
        for (String date : groupedHashMap.keySet()) {
            DateObject dateItem = new DateObject();
            dateItem.setDate(date);
            consolidatedList.add(dateItem);
            for (OngoingBean.Result chatModel : groupedHashMap.get(date)) {
                HistoryDataModelObject generalItem = new HistoryDataModelObject();
                generalItem.setChatModel(chatModel);
                consolidatedList.add(generalItem);
            }
        }
        initRecyclerView();
        adapter.setDataChange(consolidatedList);

        return consolidatedList;
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

    private void getAppointmentHistory(String interval) {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(mContext, 0);
        progressDialog.show();
        service.getAppointmentHistory(BRANCHIDVAL, interval)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OngoingBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OngoingBean loginBeanObj) {
                        progressDialog.dismiss();
                        ongoingBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(HistoryActivity.this,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        results.clear();
                        if (ongoingBean.getStatus() == 1) {
                            results = ongoingBean.getResult();

                            groupDataIntoHashMap(results);

                        } else if (ongoingBean.getStatus() == 3) {
                            showFailToast(HistoryActivity.this,  "" + ongoingBean.getMsg());
                            logout(HistoryActivity.this);
                        }
//                        cPresenter.updateCoinList(allCurrencyList);
                        setEmptyMsg(results, recyclerView, ivNoData);
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
