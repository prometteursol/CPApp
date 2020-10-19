package com.prometteur.cpapp.services;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.AppointmentTabAdapter;
import com.prometteur.cpapp.adapter.HistoryAdapter;
import com.prometteur.cpapp.adapter.ServicesTabAdapter;
import com.prometteur.cpapp.appointment.AppointmentBottomFragmentDetails;
import com.prometteur.cpapp.beans.DateObject;
import com.prometteur.cpapp.beans.HistoryDataModel;
import com.prometteur.cpapp.beans.HistoryDataModelObject;
import com.prometteur.cpapp.beans.ListObject;
import com.prometteur.cpapp.beans.OrderStatus;
import com.prometteur.cpapp.utils.DateParser;
import com.prometteur.cpapp.utils.NetworkChangeReceiver;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class ServicesActivity extends AppCompatActivity {

    public ViewPager viewPager;
TabLayout tabLayout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        setToolbar();
        viewPager = findViewById(R.id.vpBottomAppoint);
        tabLayout = findViewById(R.id.tabLayout);

        initTabs();

    }

    public void setToolbar()
    {
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Services List");
        int currentapiVersion = Build.VERSION.SDK_INT;
        if(Build.VERSION_CODES.P==currentapiVersion)
        {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }

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

    private void initTabs() {




        String[] tabTitles=new  String[]{"Rate Card","Combo List","Promotional Offers"};

        ServicesTabAdapter servicesTabAdapter = new ServicesTabAdapter(getSupportFragmentManager());
        RateCardFragment rateCardFragment=new RateCardFragment();
        ComboListFragment comboListFragment=new ComboListFragment();
        PromoOffersFragment promoOffersFragment=new PromoOffersFragment();

            servicesTabAdapter.addCompetetorTabFragments(rateCardFragment,tabTitles[0]);
            servicesTabAdapter.addCompetetorTabFragments(comboListFragment,tabTitles[1]);
            servicesTabAdapter.addCompetetorTabFragments(promoOffersFragment,tabTitles[2]);

        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(servicesTabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

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
