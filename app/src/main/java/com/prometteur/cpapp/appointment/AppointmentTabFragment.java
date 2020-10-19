package com.prometteur.cpapp.appointment;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.tricks.ViewPagerEx;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.AppointmentTabAdapter;
import com.prometteur.cpapp.adapter.ImageAdapter;
import com.prometteur.cpapp.beans.AdvertisementBean;
import com.prometteur.cpapp.beans.NotificationBean;
import com.prometteur.cpapp.drawer.DashboardMainActivity;
import com.prometteur.cpapp.listener.OnTabRemoveListener;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.NonSwipeableViewPager;
import com.prometteur.cpapp.utils.Preferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.drawer.DashboardMainActivity.adsResult;
import static com.prometteur.cpapp.utils.Constants.BRANCHIDVAL;
import static com.prometteur.cpapp.utils.Constants.NOTCOUNT;
import static com.prometteur.cpapp.utils.Constants.USERIDVAL;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showProgress;


public class AppointmentTabFragment extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, View.OnClickListener, OnTabRemoveListener {

    public NonSwipeableViewPager viewPager;
    Context mContext;
    Button btnOngoing, btnTodays, btnUpcoming;

    @Bind(R.id.slider)
    ViewPager slider;
    int i = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View view = inflater.inflate(R.layout.activity_appointment_tab, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewPager = view.findViewById(R.id.vpAppoint);
        btnOngoing = view.findViewById(R.id.btnOngoing);
        btnTodays = view.findViewById(R.id.btnTodays);
        btnUpcoming = view.findViewById(R.id.btnUpcoming);
        btnOngoing.setOnClickListener(this);
        btnTodays.setOnClickListener(this);
        btnUpcoming.setOnClickListener(this);
        setSlider();
        initValues();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setSlider() {
        if(adsResult!=null)
        {
            ImageAdapter adapter = new ImageAdapter(mContext, adsResult);
            slider.setAdapter(adapter);
            slider.setPageMargin(-(int) mContext.getResources().getDimension(R.dimen._35sdp));
        }


       /* if(Build.VERSION.SDK_INT==26) {
            slider.setPageMargin(-115);
        }else if(Build.VERSION.SDK_INT==27) {
            slider.setPageMargin(-90);
        }else if(Build.VERSION.SDK_INT>27) {
            slider.setPageMargin(-115);
        }else
        {
            slider.setPageMargin(-85);
        }*/
        // viewPager.setCurrentItem(adapter.getCount()-1);
        setPageNuber(adsResult, i);


            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            },1000);
*/
    }

    public void setPageNuber(final List<AdvertisementBean.Result> listUrl, int i) {
        if (listUrl.size() == i) {
            i = 0;
        }

        slider.setCurrentItem(i, true);
//    slider.setCurrentItem(i);
        //slider.setAnimation(new ());
        i++;
        final int finalI = i;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setPageNuber(listUrl, finalI);
            }
        }, 3000);

    }

    public void initValues() {


        // TabLayout competetorMenuTabLayout = (TabLayout) findViewById(R.id.competetor_tablayout);


        AppointmentTabAdapter appointmentTabAdapter = new AppointmentTabAdapter(getActivity().getSupportFragmentManager(),this);
        appointmentTabAdapter.addCompetetorTabFragments(new OngoingFragment(), getResources().getString(R.string.dashboard));
        appointmentTabAdapter.addCompetetorTabFragments(new TodaysFragment(), getResources().getString(R.string.dashboard));
        appointmentTabAdapter.addCompetetorTabFragments(new UpcomingFragment(), getResources().getString(R.string.dashboard));

//


        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(appointmentTabAdapter);
        appointmentTabAdapter.notifyDataSetChanged();

        btnOngoing.setBackground(getResources().getDrawable(R.drawable.btn_bg_small));
        btnTodays.setBackground(getResources().getDrawable(R.drawable.btn_rounded_background_white));
        btnUpcoming.setBackground(getResources().getDrawable(R.drawable.btn_rounded_background_white));
        btnOngoing.setTextColor(mContext.getResources().getColor(R.color.white));
        btnTodays.setTextColor(mContext.getResources().getColor(R.color.black));
        btnUpcoming.setTextColor(mContext.getResources().getColor(R.color.black));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOngoing:
                viewPager.setCurrentItem(0);
                btnOngoing.setBackground(getResources().getDrawable(R.drawable.btn_bg_small));
                btnTodays.setBackground(getResources().getDrawable(R.drawable.btn_rounded_background_white));
                btnUpcoming.setBackground(getResources().getDrawable(R.drawable.btn_rounded_background_white));

                btnOngoing.setTextColor(mContext.getResources().getColor(R.color.white));
                btnTodays.setTextColor(mContext.getResources().getColor(R.color.black));
                btnUpcoming.setTextColor(mContext.getResources().getColor(R.color.black));
                break;
            case R.id.btnTodays:
                viewPager.setCurrentItem(1);
                btnOngoing.setBackground(getResources().getDrawable(R.drawable.btn_rounded_background_white));
                btnTodays.setBackground(getResources().getDrawable(R.drawable.btn_bg_small));
                btnUpcoming.setBackground(getResources().getDrawable(R.drawable.btn_rounded_background_white));

                btnOngoing.setTextColor(mContext.getResources().getColor(R.color.black));
                btnTodays.setTextColor(mContext.getResources().getColor(R.color.white));
                btnUpcoming.setTextColor(mContext.getResources().getColor(R.color.black));
                break;
            case R.id.btnUpcoming:
                viewPager.setCurrentItem(2);
                btnOngoing.setBackground(getResources().getDrawable(R.drawable.btn_rounded_background_white));
                btnTodays.setBackground(getResources().getDrawable(R.drawable.btn_rounded_background_white));
                btnUpcoming.setBackground(getResources().getDrawable(R.drawable.btn_bg_small));

                btnOngoing.setTextColor(mContext.getResources().getColor(R.color.black));
                btnTodays.setTextColor(mContext.getResources().getColor(R.color.black));
                btnUpcoming.setTextColor(mContext.getResources().getColor(R.color.white));
                break;
        }
    }

    @Override
    public void onTabRemoved(int size) {

    }



}
