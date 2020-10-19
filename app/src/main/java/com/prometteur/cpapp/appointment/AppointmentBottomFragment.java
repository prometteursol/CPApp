package com.prometteur.cpapp.appointment;

import android.app.Activity;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.tricks.ViewPagerEx;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.AppointmentTabAdapter;
import com.prometteur.cpapp.beans.NotificationBean;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.drawer.DashboardMainActivity;
import com.prometteur.cpapp.listener.OnTabRemoveListener;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.Preferences;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.drawer.DashboardMainActivity.bottomNavigationView;
import static com.prometteur.cpapp.drawer.DashboardMainActivity.menuPos;
import static com.prometteur.cpapp.utils.Constants.APTCOUNT;
import static com.prometteur.cpapp.utils.Constants.BRANCHIDVAL;
import static com.prometteur.cpapp.utils.Utils.getBottomNavigationCount;
import static com.prometteur.cpapp.utils.Utils.isNetworkAvailable;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.setEmptyMsg;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showNoInternetDialog;
import static com.prometteur.cpapp.utils.Utils.showProgress;


public class AppointmentBottomFragment extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, OnTabRemoveListener {

    public ViewPager viewPager;
    Context mContext;
    ImageView ivNoAppoint;
    OngoingBean ongoingBean;
    List<OngoingBean.Result> results = new ArrayList<>();
    public static AppointmentTabAdapter appointmentTabAdapter;
    NotificationBean.Result notiResult;
    String notiId="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return inflater.inflate(R.layout.fragment_appointment_bottom_tab, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivNoAppoint = view.findViewById(R.id.ivNoAppoint);
        viewPager = view.findViewById(R.id.vpBottomAppoint);
        notiResult = (NotificationBean.Result) ((Activity)mContext).getIntent().getSerializableExtra("objNoti");
        if(notiResult!=null)
        {
            notiId=notiResult.getNotiId();
        }
       /* if (isNetworkAvailable(mContext)) {
            getAppointmentRequests();
        } else {
            // Toast.makeText(mContext, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

            showNoInternetDialog(mContext);
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
       /* if(results.size()==0)
        {
            if (isNetworkAvailable(mContext)) {
                getAppointmentRequests();
            } else {
                // Toast.makeText(mContext, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

                showNoInternetDialog(mContext);
            }
        }*/
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (isNetworkAvailable(mContext)) {
            getAppointmentRequests();
        } else {
            // Toast.makeText(mContext, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

            showNoInternetDialog(mContext);
        }
    }

    /*@Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }*/

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

    private void initValues() {


        // TabLayout competetorMenuTabLayout = (TabLayout) findViewById(R.id.competetor_tablayout);


         appointmentTabAdapter = new AppointmentTabAdapter(((AppCompatActivity)mContext).getSupportFragmentManager(),this);
        for (int i = 0; i < results.size(); i++) {
            AppointmentBottomFragmentDetails appointmentBottomFragmentDetails = new AppointmentBottomFragmentDetails();
            Bundle bundle = new Bundle();
            bundle.putString("dynamicData", "" + results.size());
            bundle.putString("whichPage", "appointment");
            bundle.putSerializable("appointDetails", results.get(i));
            appointmentBottomFragmentDetails.setArguments(bundle);
            appointmentTabAdapter.addCompetetorTabFragments(appointmentBottomFragmentDetails, mContext.getResources().getString(R.string.dashboard));
        }
//

  /* viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                int pageWidth = viewPager.getMeasuredWidth() - viewPager.getPaddingLeft() - viewPager.getPaddingRight();
                int pageHeight = viewPager.getHeight();
                int paddingLeft = viewPager.getPaddingLeft();
                float transformPos = (float) (page.getLeft() - (viewPager.getScrollX() + paddingLeft)) / pageWidth;

                final float normalizedposition = Math.abs(Math.abs(transformPos) - 1);
                page.setAlpha(normalizedposition + 0.5f);

                int max = -pageHeight / 10;

                if (transformPos < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setTranslationY(0);
                } else if (transformPos <= 1) { // [-1,1]
                    page.setTranslationY(max * (1-Math.abs(transformPos)));

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setTranslationY(0);
                }


            }
        });*/
        viewPager.setClipToPadding(false);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(appointmentTabAdapter);
        viewPager.setPageMargin(-24);
        viewPager.setPadding(48, 8, 48, 8);
    }

    private void getAppointmentRequests() {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(mContext, 0);
        if(menuPos==1) {
            progressDialog.show();
        }
        service.getAppointmentRequests(BRANCHIDVAL,notiId)
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
                        Preferences.setPreferenceValue(mContext, APTCOUNT, results.size());
                        getBottomNavigationCount(mContext, bottomNavigationView);
                        if (ongoingBean.getStatus() == 1) {
                            results = ongoingBean.getResult();
                            Preferences.setPreferenceValue(mContext, APTCOUNT, results.size());
                            getBottomNavigationCount(mContext, bottomNavigationView);
                            initValues();

                        } else if (ongoingBean.getStatus() == 3) {
                            showFailToast(mContext,  "" + ongoingBean.getMsg());
                            logout(mContext);
                        }
//                        cPresenter.updateCoinList(allCurrencyList);
                        setEmptyMsg(results, viewPager, ivNoAppoint);
                    }
                });
    }

    @Override
    public void onTabRemoved(int size) {
        if(size==0) {
            Preferences.setPreferenceValue(mContext, APTCOUNT, 0);
            Intent intent1=new Intent("SendToService");
            mContext.sendBroadcast(intent1);
            //getBottomNavigationCount(mContext, bottomNavigationView);
            if (isNetworkAvailable(mContext)) {
                results = new ArrayList<>();
                getAppointmentRequests();
            } else {
                // Toast.makeText(mContext, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

                showNoInternetDialog(mContext);
            }
        }
    }
}
