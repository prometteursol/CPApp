package com.prometteur.cpapp.profile;

import android.app.Dialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.ServicesTabAdapter;
import com.prometteur.cpapp.adapter.TopServicesAdapter;
import com.prometteur.cpapp.beans.NotificationBean;
import com.prometteur.cpapp.beans.ProfileBean;
import com.prometteur.cpapp.history.HistoryActivity;
import com.prometteur.cpapp.listener.OnTopServiceItemClickListener;
import com.prometteur.cpapp.onboarding.LoginActivity;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.HeaderView;
import com.prometteur.cpapp.utils.HeightWrappingViewPager;
import com.prometteur.cpapp.utils.NetworkChangeReceiver;
import com.prometteur.cpapp.utils.Preferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.utils.Constants.BRANCHADDRESS;
import static com.prometteur.cpapp.utils.Constants.BRANCHCLOSEDSTATUS;
import static com.prometteur.cpapp.utils.Constants.BRANCHIDVAL;
import static com.prometteur.cpapp.utils.Constants.BRANCHIMG;
import static com.prometteur.cpapp.utils.Constants.BRANCHNAME;
import static com.prometteur.cpapp.utils.Constants.BRANCHOPENSTATUS;
import static com.prometteur.cpapp.utils.Utils.isNetworkAvailable;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showNoInternetDialog;
import static com.prometteur.cpapp.utils.Utils.showProgress;


public class ProfileActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener, OnTopServiceItemClickListener {


    public static ProfileBean.Topservice topItem;
    public static ProfileBean.Result profileData;
    RecyclerView recyclerView;
    TopServicesAdapter adapter;
    RadioGroup rgFilter;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RelativeLayout rlRatingSection;
    Toolbar toolbar;
    HeaderView toolbarHeaderView;
    HeaderView floatHeaderView;
    boolean isHideToolbarView = false;
    @Bind(R.id.vpSalonInfo)
    HeightWrappingViewPager viewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.ivHeaderImg)
    ImageView ivHeaderImg;
    List<Object> colorList = new ArrayList<>();

    ArrayList<Integer> dynamicColorList = new ArrayList<>();
    ProfileBean profileBean;
    String status = "";
    private List<ProfileBean.Topservice> mDataList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    NotificationBean.Result notiResult;
    String notiId="";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setToolbar();
        recyclerView = findViewById(R.id.recyclerView);
        rgFilter = findViewById(R.id.rgFilter);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        rlRatingSection = findViewById(R.id.rlRatingSection);
        toolbarHeaderView = findViewById(R.id.toolbar_header_view);
        floatHeaderView = findViewById(R.id.float_header_view);
        appBarLayout = findViewById(R.id.app_bar_layout);
        toolbar = findViewById(R.id.toolbar);
        colorList.add(R.color.skyBlue1);
        colorList.add(R.color.skyBlue2);
        colorList.add(R.color.skyBlue3);
        colorList.add(R.color.skyBlue4);
        colorList.add(R.color.skyBlue5);
        colorList.add(R.color.skyBlue6);
        colorList.add(R.color.skyBlue7);
        colorList.add(R.color.skyBlue1);
        colorList.add(R.color.skyBlue2);
        colorList.add(R.color.skyBlue3);
        colorList.add(R.color.skyBlue4);
        colorList.add(R.color.skyBlue5);
        colorList.add(R.color.skyBlue6);
        colorList.add(R.color.skyBlue7);
        colorList.add(R.color.skyBlue1);
        colorList.add(R.color.skyBlue2);
        colorList.add(R.color.skyBlue3);
        notiResult = (NotificationBean.Result) getIntent().getSerializableExtra("objNoti");
if(notiResult!=null)
{
    notiId=notiResult.getNotiId();
}
        if (isNetworkAvailable(ProfileActivity.this)) {
            getProfileDetails();
        } else {
            // Toast.makeText(LoginActivity.this, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

            showNoInternetDialog(ProfileActivity.this);
        }


        collapsingToolbarLayout.setTitle(" ");

        toolbarHeaderView.bindTo("Loading...", "Loading...", 0.0f, "0", "OPEN");
        floatHeaderView.bindTo("Loading...", "Loading...", 0.0f, "0", "OPEN");
        appBarLayout.addOnOffsetChangedListener(ProfileActivity.this);

    }

    public void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Profile");


        int currentapiVersion = Build.VERSION.SDK_INT;
        if (Build.VERSION_CODES.P == currentapiVersion) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }

    }

    private void initTabs() {


        String[] tabTitles = new String[]{"About", "Gallery", "Operators", "Review"};

        ServicesTabAdapter servicesTabAdapter = new ServicesTabAdapter(getSupportFragmentManager());
        AboutFragment aboutFragment = new AboutFragment();
        GalleryFragment galleryFragment = new GalleryFragment();
        OperatorsFragment operatorsFragment = new OperatorsFragment();
        ReviewFragment reviewFragment = new ReviewFragment();


        servicesTabAdapter.addCompetetorTabFragments(aboutFragment, tabTitles[0]);
        servicesTabAdapter.addCompetetorTabFragments(galleryFragment, tabTitles[1]);
        servicesTabAdapter.addCompetetorTabFragments(operatorsFragment, tabTitles[2]);
        servicesTabAdapter.addCompetetorTabFragments(reviewFragment, tabTitles[3]);

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(servicesTabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        if (notiResult != null) {
            viewPager.setCurrentItem(3);
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

        mLayoutManager = new LinearLayoutManager(ProfileActivity.this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new TopServicesAdapter(ProfileActivity.this, mDataList, this, dynamicColorList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(ProfileBean.Topservice item) {
        topItem = item;
        BottomSheetDialogFragment bottomSheetDialogFragment = new ServicesProfileFragment();
        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
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

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {

        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.VISIBLE);
            rlRatingSection.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
            toolbarHeaderView.hideShowRating(true);
        } else if (percentage < 1f && !isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.GONE);
            rlRatingSection.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;
            toolbarHeaderView.hideShowRating(true);
        }
    }

    private void setColorList() {
        List<Object> list = colorList;
        /*  Collections.shuffle(list);*/

        if (list.size() < mDataList.size()) {
            int j = 0;
            for (int i = 0; i < mDataList.size(); i++) {

                if (list.size() == j) {
                    j = 0;
                    Collections.shuffle(list);
                }
                dynamicColorList.add((Integer) list.get(j));
                j++;
            }


        } else {
            for (int i = 0; i < mDataList.size(); i++) {

                dynamicColorList.add((Integer) list.get(i));
            }
        }
    }

    private void getProfileDetails() {

        final ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(ProfileActivity.this, 0);
        progressDialog.show();
        service.getProfileDetails(BRANCHIDVAL,notiId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProfileBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ProfileBean loginBeanObj) {
                        progressDialog.dismiss();
                        profileBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(ProfileActivity.this,  getResources().getString(R.string.went_wrong));

                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (profileBean.getStatus() == 1) {
                            ProfileBean.Result result = profileBean.getResult().get(0);
                            float rating = 0;
                            String reviews = "0";
                            if (result.getBranchRating() != null && !result.getBranchRating().isEmpty()) {
                                rating = Float.parseFloat(result.getBranchRating());
                                reviews = result.getReviewCount();
                            }


                            Preferences.setPreferenceValue(ProfileActivity.this, BRANCHNAME, result.getBranName());
                            Preferences.setPreferenceValue(ProfileActivity.this, BRANCHIMG, result.getBranImg());
                            Preferences.setPreferenceValue(ProfileActivity.this, BRANCHADDRESS, result.getBranCity());
                            Preferences.setPreferenceValue(ProfileActivity.this, BRANCHOPENSTATUS, result.getBranOpenStatus());
                            Preferences.setPreferenceValue(ProfileActivity.this, BRANCHCLOSEDSTATUS, result.getBranClosed());
                            if (result.getBranOpenStatus().equalsIgnoreCase("1") && result.getBranClosed().equalsIgnoreCase("0")) {
                                status = "OPEN";

                            } else {
                                status = "CLOSED";

                            }

                            toolbarHeaderView.bindTo(result.getBranName(), result.getBranCity(), rating, reviews, status);
                            floatHeaderView.bindTo(result.getBranName(), result.getBranCity(), rating, reviews, status);
                            Glide.with(ProfileActivity.this).load(result.getBranImg()).into(ivHeaderImg);

                            mDataList = result.getTopservices();
                            setColorList();
                            profileData = result;
                            initRecyclerView();
                        } else if (profileBean.getStatus() == 3) {
                            showFailToast(ProfileActivity.this,  "" + profileBean.getMsg());
                            logout(ProfileActivity.this);
                        }

                        initTabs();
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
