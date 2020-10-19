package com.prometteur.cpapp.statistics;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.OperatorProfileAdapter;
import com.prometteur.cpapp.adapter.OperatorWeekAdapter;
import com.prometteur.cpapp.beans.OrderStatus;
import com.prometteur.cpapp.beans.PerformanceBean;
import com.prometteur.cpapp.beans.TimeLineModel;

import java.util.ArrayList;

import static com.prometteur.cpapp.statistics.PerformaceActivity.performanceBean;


public class OperatorsWeekFragment extends Fragment{

    Context mContext;
    RecyclerView recyclerView;
    private ArrayList<PerformanceBean.Operator> mDataList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    OperatorWeekAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return inflater.inflate(R.layout.fragment_operators, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         recyclerView = view.findViewById(R.id.recyclerView);

        setDataListItems();
        initRecyclerView();
    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }



    private void setDataListItems() {
        if(performanceBean!=null) {
            mDataList= (ArrayList<PerformanceBean.Operator>) performanceBean.getOperators();
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

        adapter =new OperatorWeekAdapter(mContext,mDataList);
        recyclerView.setAdapter(adapter);
    }

}
