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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.TodaysAdapter;
import com.prometteur.cpapp.adapter.UpcomingAdapter;
import com.prometteur.cpapp.beans.DateObject;
import com.prometteur.cpapp.beans.HistoryDataModel;
import com.prometteur.cpapp.beans.HistoryDataModelObject;
import com.prometteur.cpapp.beans.ListObject;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.beans.OrderStatus;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.beans.UpcomingDataModelObject;
import com.prometteur.cpapp.listener.OnItemClickListener;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.DateParser;
import com.prometteur.cpapp.utils.Preferences;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.drawer.DashboardMainActivity.hideStatusIcon;
import static com.prometteur.cpapp.utils.Constants.APTCOUNT;
import static com.prometteur.cpapp.utils.Constants.BRANCHIDVAL;
import static com.prometteur.cpapp.utils.Utils.isNetworkAvailable;
import static com.prometteur.cpapp.utils.Utils.setEmptyMsg;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showNoInternetDialog;
import static com.prometteur.cpapp.utils.Utils.showProgress;


public class UpcomingFragment extends Fragment implements OnItemClickListener {

    Context mContext;
    RecyclerView recyclerView;
    private ArrayList<TimeLineModel> mDataList = new ArrayList<TimeLineModel>();
    private LinearLayoutManager mLayoutManager;
    UpcomingAdapter adapter;
ImageView ivNoAppoint;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return inflater.inflate(R.layout.fragment_todyas, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivNoAppoint = view.findViewById(R.id.ivNoAppoint);
         recyclerView = view.findViewById(R.id.recyclerView);

        if (isNetworkAvailable(mContext)) {
            getUpcomingAppointment();
        } else {
            // Toast.makeText(mContext, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

            showNoInternetDialog(mContext);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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

        adapter =new UpcomingAdapter(mContext,null,this);
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
                UpcomingDataModelObject generalItem = new UpcomingDataModelObject();
                generalItem.setTimeLineModel(chatModel);
                consolidatedList.add(generalItem);
            }
        }
        initRecyclerView();
        adapter.setDataChange(consolidatedList);

        return consolidatedList;
    }

    @Override
    public void onItemClick(TimeLineModel item) {


    }

    @Override
    public void onItemClick(OngoingBean.Result item) {
        Bundle bundle=new Bundle();
        bundle.putString("dynamicData",""+1);
        bundle.putString("whichPage","upcoming");
        bundle.putSerializable("appointDetails",item);
        mContext.startActivity(new Intent(mContext,AppointmentDetailsActivity.class).putExtra("appointData",bundle));
    }



    OngoingBean ongoingBean;
    List<OngoingBean.Result> results=new ArrayList<>();
    private void getUpcomingAppointment() {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog=showProgress(mContext,0);
        //progressDialog.show();
        service.getUpcomingAppointment(BRANCHIDVAL)
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
                            //initRecyclerView();
                            groupDataIntoHashMap(results);

                        }else if (ongoingBean.getStatus() == 3)
                        {
                            showFailToast(mContext,  "" + ongoingBean.getMsg());
                        }
//                        cPresenter.updateCoinList(allCurrencyList);
                        setEmptyMsg(results,recyclerView,ivNoAppoint);
                    }
                });

    }

}
