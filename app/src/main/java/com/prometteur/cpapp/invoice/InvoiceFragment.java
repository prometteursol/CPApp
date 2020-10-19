package com.prometteur.cpapp.invoice;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.InvoiceAdapter;
import com.prometteur.cpapp.adapter.TodaysAdapter;
import com.prometteur.cpapp.appointment.AppointmentBottomFragmentDetails;
import com.prometteur.cpapp.beans.InvoiceBean;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.beans.OrderStatus;
import com.prometteur.cpapp.beans.PromoOfferBean;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.listener.OnItemClickListener;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.drawer.DashboardMainActivity.menuPos;
import static com.prometteur.cpapp.utils.Constants.BRANCHIDVAL;
import static com.prometteur.cpapp.utils.Utils.isNetworkAvailable;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.setEmptyMsg;
import static com.prometteur.cpapp.utils.Utils.setNoInternetMsg;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showProgress;


public class InvoiceFragment extends Fragment {

    Context mContext;
    private List<InvoiceBean.Result> mDataList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    InvoiceAdapter adapter;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.ivNoData)
    ImageView ivNoData;
    @Bind(R.id.ivNoInternet)
    ImageView ivNoInternet;
    @Bind(R.id.tvTotalAmt)
    TextView tvTotalAmt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isNetworkAvailable(mContext)) {
            getInvoices();
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

        adapter =new InvoiceAdapter(mContext,mDataList);
        recyclerView.setAdapter(adapter);

    }




    InvoiceBean invoiceBean;
    private void getInvoices() {

        final ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(mContext, 0);
        if(menuPos==2) {
            progressDialog.show();
        }
        service.getInvoices(BRANCHIDVAL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InvoiceBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(InvoiceBean loginBeanObj) {
                        progressDialog.dismiss();
                        invoiceBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(mContext,mContext.getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (invoiceBean.getStatus() == 1) {
                            mDataList = invoiceBean.getResult();
                            tvTotalAmt.setText("₹ "+new DecimalFormat("##.##").format(Double.parseDouble(invoiceBean.getTotalEarning()))+"/-");
                            initRecyclerView();
                        } else if (invoiceBean.getStatus() == 3) {
                            showFailToast(mContext,"" + invoiceBean.getMsg());
                            logout(mContext);
                        }
                        setEmptyMsg(mDataList, recyclerView, ivNoData);
                    }
                });
    }

}
