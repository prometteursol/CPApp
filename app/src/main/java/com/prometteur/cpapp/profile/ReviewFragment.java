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
import com.prometteur.cpapp.adapter.ReviewAdapter;
import com.prometteur.cpapp.beans.ProfileBean;

import java.util.ArrayList;
import java.util.List;

import static com.prometteur.cpapp.profile.ProfileActivity.profileData;
import static com.prometteur.cpapp.utils.Utils.setEmptyMsg;


public class ReviewFragment extends Fragment {

    Context mContext;
    RecyclerView recyclerView;
    ReviewAdapter adapter;
    TextView tvReviewCount;
    ImageView ivNoData;
    private List<ProfileBean.Review> mDataList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return inflater.inflate(R.layout.fragment_review, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvReviewCount = view.findViewById(R.id.tvReviewCount);
        recyclerView = view.findViewById(R.id.recyclerView);
        ivNoData = view.findViewById(R.id.ivNoDataReview);

        setDataListItems();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }


    private void setDataListItems() {
        if(profileData!=null) {
            mDataList = profileData.getReviews();
        }
        if(mDataList.size()!=0) {
            tvReviewCount.setText("All Reviews (" + mDataList.size() + ")");
            tvReviewCount.setVisibility(View.VISIBLE);
        }else
        {
            tvReviewCount.setVisibility(View.GONE);
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

        adapter = new ReviewAdapter(mContext, mDataList);
        recyclerView.setAdapter(adapter);
        //ivNoData.setImageResource(R.drawable.img_no_reviews_empty);
        setEmptyMsg(mDataList, recyclerView, ivNoData);
    }


}
