package com.prometteur.cpapp.appointment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.TimeLineAdapter;
import com.prometteur.cpapp.beans.LoginBean;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.beans.OrderStatus;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.dialogs.OtpStartEndDialogActivity;
import com.prometteur.cpapp.drawer.DashboardMainActivity;
import com.prometteur.cpapp.history.HistoryDetailsActivity;
import com.prometteur.cpapp.listener.OnItemClickListener;
import com.prometteur.cpapp.onboarding.LoginActivity;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.Preferences;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.drawer.DashboardMainActivity.hideStatusIcon;
import static com.prometteur.cpapp.drawer.DashboardMainActivity.menuPos;
import static com.prometteur.cpapp.utils.Constants.APTCOUNT;
import static com.prometteur.cpapp.utils.Constants.BRANCHIDVAL;
import static com.prometteur.cpapp.utils.Constants.ISLOGIN;
import static com.prometteur.cpapp.utils.Constants.MOBNO;
import static com.prometteur.cpapp.utils.Constants.USERID;
import static com.prometteur.cpapp.utils.Constants.USERSESSION;
import static com.prometteur.cpapp.utils.Utils.isNetworkAvailable;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.setEmptyMsg;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showNoInternetDialog;
import static com.prometteur.cpapp.utils.Utils.showProgress;


public class OngoingFragment extends Fragment implements OnItemClickListener {

    Context mContext;
    RecyclerView recyclerView;
    private ArrayList<TimeLineModel> mDataList = new ArrayList<TimeLineModel>();
    private LinearLayoutManager mLayoutManager;
    TimeLineAdapter adapter;
    ImageView ivNoAppoint;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return inflater.inflate(R.layout.fragment_ongoing, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        ivNoAppoint = view.findViewById(R.id.ivNoAppoint);

        if (isNetworkAvailable(mContext)) {
            getOngoingAppointment();
        } else {
            // Toast.makeText(mContext, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

            showNoInternetDialog(mContext);
        }
    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public void onResume() {
        super.onResume();
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

        adapter =new TimeLineAdapter(mContext,results,this);
        recyclerView.setAdapter(adapter);


    }




    @Override
    public void onItemClick(TimeLineModel item) {


    }

    @Override
    public void onItemClick(OngoingBean.Result item) {

        /*Bundle bundle=new Bundle();
        bundle.putString("dynamicData",""+1);
        bundle.putString("whichPage","onGoingFrag");
        bundle.putSerializable("appointDetails",item);
        mContext.startActivity(new Intent(mContext,AppointmentDetailsActivity.class).putExtra("appointData",bundle));*/
        startActivity(new Intent(mContext, HistoryDetailsActivity.class).putExtra("pageType","endOtp").putExtra("appId",item.getAptId()));
        //finish();
    }

    OngoingBean ongoingBean;
    List<OngoingBean.Result> results=new ArrayList<>();
    private void getOngoingAppointment() {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog=showProgress(mContext,0);
        if(menuPos==0) {
            progressDialog.show();
        }
        service.getOngoingAppointment(BRANCHIDVAL)
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
                        showFailToast(mContext,  mContext.getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (ongoingBean.getStatus() == 1) {
                            results = ongoingBean.getResult();
                            Preferences.setPreferenceValue(mContext, APTCOUNT, ongoingBean.getAptRequest());
                            Intent intent1=new Intent("SendToService");
                            mContext.sendBroadcast(intent1);
                            initRecyclerView();

                        }else if (ongoingBean.getStatus() == 3)
                        {
                            showFailToast(mContext,  "" + ongoingBean.getMsg());
                           // logout(mContext);
                        }
//                        cPresenter.updateCoinList(allCurrencyList);
                        setEmptyMsg(results,recyclerView,ivNoAppoint);
                    }
                });

    }
}
