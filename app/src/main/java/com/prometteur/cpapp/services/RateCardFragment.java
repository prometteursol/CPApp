package com.prometteur.cpapp.services;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.RateCardAdapter;
import com.prometteur.cpapp.beans.RateCardBean;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.drawer.DashboardMainActivity;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;

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


public class RateCardFragment extends Fragment {

    Context mContext;
    RecyclerView recyclerView;
    RateCardAdapter adapter;
    RadioGroup rgServices;
    RadioButton rbSkin, rbHair, rbSpa, rbNail, rbMakeup;
    RateCardBean rateCardBean;
    private ArrayList<TimeLineModel> mDataList = new ArrayList<TimeLineModel>();
    private LinearLayoutManager mLayoutManager;
    ImageView ivNoData,ivNoInternet;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View view = inflater.inflate(R.layout.fragment_rate_card, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        rgServices = view.findViewById(R.id.rgServices);
        rbSkin = view.findViewById(R.id.rbSkin);
        rbHair = view.findViewById(R.id.rbHair);
        rbSpa = view.findViewById(R.id.rbSpa);
        rbNail = view.findViewById(R.id.rbNail);
        rbMakeup = view.findViewById(R.id.rbMakeup);
        ivNoData=view.findViewById(R.id.ivNoData);
        ivNoInternet=view.findViewById(R.id.ivNoInternet);
        rgServices.setVisibility(View.GONE);
        if (isNetworkAvailable(mContext)) {
            getRateCardDetails();
        } else {
            // Toast.makeText(mContext, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /*private void initRecyclerView() {
        initAdapter(mDataList,0);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //if(recyclerView.getChildAt(0).top < 0) dropshadow.setVisible(); else dropshadow.setGone();
            }
        });
    }*/

    private void initAdapter(List dataList,int pos) {

        mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new RateCardAdapter(mContext, dataList,pos);
        recyclerView.setAdapter(adapter);
        setEmptyMsg(dataList, recyclerView, ivNoData);
    }

    List dataListHair=new ArrayList();
    List dataListSkin=new ArrayList();
    List dataListSpa=new ArrayList();
    List dataListNail=new ArrayList();
    List dataListMakeup=new ArrayList();
    private void getRateCardDetails() {

        final ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(mContext, 0);
        progressDialog.show();
        service.getRateCardDetails(BRANCHIDVAL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RateCardBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RateCardBean loginBeanObj) {
                        progressDialog.dismiss();
                        rateCardBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        //showFailToast(mContext,  mContext.getResources().getString(R.string.went_wrong));
                        ivNoData.setImageResource(R.drawable.img_no_services_empty);
                        setEmptyMsg(dataListHair, recyclerView, ivNoData);
                        setEmptyMsg(dataListSkin, recyclerView, ivNoData);
                        setEmptyMsg(dataListSpa, recyclerView, ivNoData);
                        setEmptyMsg(dataListNail, recyclerView, ivNoData);
                        setEmptyMsg(dataListMakeup, recyclerView, ivNoData);
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (rateCardBean.getStatus() == 1) {
                            rgServices.setVisibility(View.VISIBLE);
                            rbHair.setVisibility(View.GONE);
                            rbSkin.setVisibility(View.GONE);
                            rbSpa.setVisibility(View.GONE);
                            rbNail.setVisibility(View.GONE);
                            rbMakeup.setVisibility(View.GONE);
                            List<RateCardBean.Service> services = rateCardBean.getServices();
                            boolean firstServiceFlag=false;
                            int firstPos=0;
                            ivNoData.setImageResource(R.drawable.img_no_services_empty);
                            for (int i = 0; i < services.size(); i++) {
                                String strService = services.get(i).getService();
                                switch (strService) {
                                    case "Hair":
                                        rbHair.setVisibility(View.VISIBLE);
                                        dataListHair=rateCardBean.getResult().getHair();
                                        if(!firstServiceFlag)
                                        {
                                            rbHair.setChecked(true);
                                            firstPos=1;
                                            initAdapter(dataListHair,firstPos);
                                            firstServiceFlag=true;
                                            setEmptyMsg(dataListHair, recyclerView, ivNoData);
                                        }
                                        break;
                                    case "Skin":
                                        rbSkin.setVisibility(View.VISIBLE);
                                        dataListSkin=rateCardBean.getResult().getSkin();
                                        if(!firstServiceFlag)
                                        {
                                            rbSkin.setChecked(true);
                                            firstPos=2;
                                            initAdapter(dataListSkin,firstPos);
                                            firstServiceFlag=true;
                                            setEmptyMsg(dataListSkin, recyclerView, ivNoData);
                                        }
                                        break;
                                    case "Spa":
                                        rbSpa.setVisibility(View.VISIBLE);
                                        dataListSpa=rateCardBean.getResult().getSpa();
                                        if(!firstServiceFlag)
                                        {
                                            rbSpa.setChecked(true);
                                            firstPos=3;
                                            initAdapter(dataListSpa,firstPos);
                                            firstServiceFlag=true;
                                            setEmptyMsg(dataListSpa, recyclerView, ivNoData);
                                        }
                                        break;
                                    case "Nails":
                                        rbNail.setVisibility(View.VISIBLE);
                                        dataListNail=rateCardBean.getResult().getNail();
                                        if(!firstServiceFlag)
                                        {
                                            rbNail.setChecked(true);
                                            firstPos=4;
                                            initAdapter(dataListNail,firstPos);
                                            firstServiceFlag=true;
                                            setEmptyMsg(dataListNail, recyclerView, ivNoData);
                                        }
                                        break;
                                    case "Makeup":
                                        rbMakeup.setVisibility(View.VISIBLE);
                                        dataListMakeup=rateCardBean.getResult().getMakeup();
                                        if(!firstServiceFlag)
                                        {
                                            rbMakeup.setChecked(true);
                                            firstPos=5;
                                            initAdapter(dataListMakeup,firstPos);
                                            firstServiceFlag=true;
                                            setEmptyMsg(dataListMakeup, recyclerView, ivNoData);
                                        }
                                        break;
                                }
                            }


                        } else if (rateCardBean.getStatus() == 3) {
                            showFailToast(mContext,  "" + rateCardBean.getMsg());
                            logout(mContext);
                        }

                    }
                });
    }


}
