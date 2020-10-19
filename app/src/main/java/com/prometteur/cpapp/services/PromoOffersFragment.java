package com.prometteur.cpapp.services;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.ComboListAdapter;
import com.prometteur.cpapp.adapter.PromoListAdapter;
import com.prometteur.cpapp.beans.ComboListBean;
import com.prometteur.cpapp.beans.OrderStatus;
import com.prometteur.cpapp.beans.PromoOfferBean;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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


public class PromoOffersFragment extends Fragment{

    Context mContext;
    private List<PromoOfferBean.Result> mDataList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    PromoListAdapter adapter;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.ivNoData)
    ImageView ivNoData;
    @Bind(R.id.ivNoInternet)
    ImageView ivNoInternet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promo_offers, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         recyclerView = view.findViewById(R.id.recyclerView);
        if (isNetworkAvailable(mContext)) {
            getPromoOfferDetails();
        } else {
            // Toast.makeText(mContext, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

            setNoInternetMsg(recyclerView, ivNoInternet);
        }
    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
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

        adapter =new PromoListAdapter(mContext,mDataList);
        recyclerView.setAdapter(adapter);
    }
    PromoOfferBean promoOfferBean;
    private void getPromoOfferDetails() {

        final ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(mContext, 0);
        //progressDialog.show();
        service.getPromoOfferDetails(BRANCHIDVAL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PromoOfferBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PromoOfferBean loginBeanObj) {
                        progressDialog.dismiss();
                        promoOfferBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(mContext,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (promoOfferBean.getStatus() == 1) {
                            mDataList = promoOfferBean.getResult();
                            initRecyclerView();
                        } else if (promoOfferBean.getStatus() == 3) {
                            showFailToast(mContext,  "" + promoOfferBean.getMsg());
                            logout(mContext);
                        }
                        ivNoData.setImageResource(R.drawable.img_offers_empty);
                        setEmptyMsg(mDataList, recyclerView, ivNoData);
                    }
                });
    }



}
