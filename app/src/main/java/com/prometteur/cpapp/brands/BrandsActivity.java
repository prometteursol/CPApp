package com.prometteur.cpapp.brands;

import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.BrandsAdapter;
import com.prometteur.cpapp.beans.BrandListBean;
import com.prometteur.cpapp.beans.HistoryDataModel;
import com.prometteur.cpapp.beans.OrderStatus;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.NetworkChangeReceiver;

import java.util.ArrayList;
import java.util.List;

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


public class BrandsActivity extends AppCompatActivity {
    
    RecyclerView recyclerView;
    private ArrayList<HistoryDataModel> mDataList = new ArrayList<HistoryDataModel>();
    private GridLayoutManager mLayoutManager;
    BrandsAdapter adapter;
    RadioGroup rgServices;
    RadioButton rbSkin, rbHair, rbSpa, rbNail, rbMakeup;
    ImageView ivNoData,ivNoInternet;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);

        setToolbar();
        recyclerView = findViewById(R.id.recyclerView);
        rgServices=findViewById(R.id.rgServices);
        rbSkin = findViewById(R.id.rbSkin);
        rbHair = findViewById(R.id.rbHair);
        rbSpa = findViewById(R.id.rbSpa);
        rbNail = findViewById(R.id.rbNail);
        rbMakeup = findViewById(R.id.rbMakeup);
        ivNoData=findViewById(R.id.ivNoData);
        ivNoInternet=findViewById(R.id.ivNoInternet);
        rgServices.setVisibility(View.GONE);
        if (isNetworkAvailable(BrandsActivity.this)) {
            getBrands();
        } else {
            // Toast.makeText(BrandsActivity.this, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

            setNoInternetMsg(recyclerView,ivNoInternet);
        }
        rgServices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.rbHair:
                        initAdapter(dataListHair,1);
                        break;
                    case R.id.rbSkin:
                        initAdapter(dataListSkin,2);
                        break;
                    case R.id.rbSpa:
                        initAdapter(dataListSpa,3);
                        break;
                    case R.id.rbNail:
                        initAdapter(dataListNail,4);
                        break;
                    case R.id.rbMakeup:
                        initAdapter(dataListMakeup,5);
                        break;
                }
            }
        });
    }

    public void setToolbar()
    {
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Brand List");
        int currentapiVersion = Build.VERSION.SDK_INT;
        if(Build.VERSION_CODES.P==currentapiVersion)
        {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }

    }

    private void initAdapter(List mDataList, int pos) {

        mLayoutManager = new GridLayoutManager(BrandsActivity.this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter =new BrandsAdapter(BrandsActivity.this,mDataList,pos);
        recyclerView.setAdapter(adapter);
        setEmptyMsg(mDataList, recyclerView, ivNoData);
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


    List dataListHair=new ArrayList();
    List dataListSkin=new ArrayList();
    List dataListSpa=new ArrayList();
    List dataListNail=new ArrayList();
    List dataListMakeup=new ArrayList();
    BrandListBean brandListBean;
    private void getBrands() {

        final ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(BrandsActivity.this, 0);
        progressDialog.show();
        service.getBrands(BRANCHIDVAL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BrandListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BrandListBean loginBeanObj) {
                        progressDialog.dismiss();
                        brandListBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(BrandsActivity.this, getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (brandListBean.getStatus() == 1) {


                                        dataListHair=brandListBean.getResult().getHair();

                                        dataListSkin=brandListBean.getResult().getSkin();

                                        dataListSpa=brandListBean.getResult().getSpa();

                                        dataListNail=brandListBean.getResult().getNail();

                                        dataListMakeup=brandListBean.getResult().getMakeup();
                            rgServices.setVisibility(View.VISIBLE);
                            rbHair.setVisibility(View.GONE);
                            rbSkin.setVisibility(View.GONE);
                            rbSpa.setVisibility(View.GONE);
                            rbNail.setVisibility(View.GONE);
                            rbMakeup.setVisibility(View.GONE);
                            List<BrandListBean.Service> services = brandListBean.getServices();
                            boolean firstServiceFlag=false;
                            int firstPos=0;
                            for (int i = 0; i < services.size(); i++) {
                                String strService = services.get(i).getService();
                                switch (strService) {
                                    case "Hair":
                                        rbHair.setVisibility(View.VISIBLE);
                                        dataListHair = brandListBean.getResult().getHair();
                                        if (!firstServiceFlag) {
                                            rbHair.setChecked(true);
                                            firstPos = 1;
                                            initAdapter(dataListHair, firstPos);
                                            firstServiceFlag = true;
                                            setEmptyMsg(dataListHair, recyclerView, ivNoData);
                                        }
                                        break;
                                    case "Skin":
                                        rbSkin.setVisibility(View.VISIBLE);
                                        dataListSkin = brandListBean.getResult().getSkin();
                                        if (!firstServiceFlag) {
                                            rbSkin.setChecked(true);
                                            firstPos = 2;
                                            initAdapter(dataListSkin, firstPos);
                                            firstServiceFlag = true;
                                            setEmptyMsg(dataListSkin, recyclerView, ivNoData);
                                        }
                                        break;
                                    case "Spa":
                                        rbSpa.setVisibility(View.VISIBLE);
                                        dataListSpa = brandListBean.getResult().getSpa();
                                        if (!firstServiceFlag) {
                                            rbSpa.setChecked(true);
                                            firstPos = 3;
                                            initAdapter(dataListSpa, firstPos);
                                            firstServiceFlag = true;
                                            setEmptyMsg(dataListSpa, recyclerView, ivNoData);
                                        }
                                        break;
                                    case "Nails":
                                        rbNail.setVisibility(View.VISIBLE);
                                        dataListNail = brandListBean.getResult().getNail();
                                        if (!firstServiceFlag) {
                                            rbNail.setChecked(true);
                                            firstPos = 4;
                                            initAdapter(dataListNail, firstPos);
                                            firstServiceFlag = true;
                                            setEmptyMsg(dataListNail, recyclerView, ivNoData);
                                        }
                                        break;
                                    case "Makeup":
                                        rbMakeup.setVisibility(View.VISIBLE);
                                        dataListMakeup = brandListBean.getResult().getMakeup();
                                        if (!firstServiceFlag) {
                                            rbMakeup.setChecked(true);
                                            firstPos = 5;
                                            initAdapter(dataListMakeup, firstPos);
                                            firstServiceFlag = true;
                                            setEmptyMsg(dataListMakeup, recyclerView, ivNoData);
                                        }
                                        break;
                                }

                            }


                        } else if (brandListBean.getStatus() == 3) {
                            showFailToast(BrandsActivity.this, "" + brandListBean.getMsg());
                            logout(BrandsActivity.this);
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
