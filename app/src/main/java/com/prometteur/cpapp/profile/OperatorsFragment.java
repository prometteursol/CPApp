package com.prometteur.cpapp.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.OperatorProfileAdapter;
import com.prometteur.cpapp.beans.ProfileBean;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.prometteur.cpapp.profile.ProfileActivity.profileData;
import static com.prometteur.cpapp.utils.Utils.setEmptyMsg;


public class OperatorsFragment extends Fragment {

    Context mContext;
    RecyclerView recyclerView;
    OperatorProfileAdapter adapter;
    TextView tvWeekly;
    ImageView ivNoData;
    ImageView ivNoInternet;
    List<ProfileBean.Operator> operators=new ArrayList<>();
    private LinearLayoutManager mLayoutManager;

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
        ivNoData = view.findViewById(R.id.ivNoData);
        ivNoInternet = view.findViewById(R.id.ivNoInternet);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvWeekly = view.findViewById(R.id.tvWeekly);
        tvWeekly.setVisibility(View.GONE);

        setDataListItems();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void setDataListItems() {
if(profileData!=null) {
    operators = profileData.getOperators();
}
        initRecyclerView();
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

        adapter = new OperatorProfileAdapter(mContext, operators);
        recyclerView.setAdapter(adapter);
        setEmptyMsg(operators,recyclerView,ivNoData);
    }


}
