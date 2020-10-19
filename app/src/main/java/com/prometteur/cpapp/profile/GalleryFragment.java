package com.prometteur.cpapp.profile;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.GalleryAdapter;
import com.prometteur.cpapp.adapter.TimeLineAdapter;
import com.prometteur.cpapp.beans.OrderStatus;
import com.prometteur.cpapp.beans.TimeLineModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.prometteur.cpapp.profile.ProfileActivity.profileData;
import static com.prometteur.cpapp.utils.Utils.setEmptyMsg;


public class GalleryFragment extends Fragment{

    Context mContext;
    RecyclerView recyclerView;
    private List<String> mDataList = new ArrayList<>();
    GalleryAdapter adapter;
ImageView ivNoData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return inflater.inflate(R.layout.fragment_gallery, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         recyclerView = view.findViewById(R.id.recyclerView);
        ivNoData = view.findViewById(R.id.ivNoDataGallery);
        initRecyclerView();
        setDataListItems();
    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }



    private void setDataListItems() {
        if(profileData!=null) {
            if (!profileData.getBranGallaryImg().isEmpty()) {
                String[] imgArr = profileData.getBranGallaryImg().split(",");
                mDataList = Arrays.asList(imgArr);
            }
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

        GridLayoutManager homeIconGridLayoutManager =new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(homeIconGridLayoutManager);

        adapter =new GalleryAdapter(mContext,mDataList);
        recyclerView.setAdapter(adapter);
        ivNoData.setImageResource(R.drawable.img_no_photos_empty);
       // ivNoData.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,500));
        setEmptyMsg(mDataList,recyclerView,ivNoData);
    }




}
