package com.prometteur.cpapp.drawer;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.appointment.AppointmentBottomFragment;
import com.prometteur.cpapp.appointment.AppointmentBottomFragmentDetails;
import com.prometteur.cpapp.appointment.AppointmentTabFragment;
import com.prometteur.cpapp.beans.AdvertisementBean;
import com.prometteur.cpapp.beans.NotificationBean;
import com.prometteur.cpapp.beans.OfflineStatusBean;
import com.prometteur.cpapp.beans.OtpBean;
import com.prometteur.cpapp.brands.BrandsActivity;
import com.prometteur.cpapp.dialogs.OfflineTimeDialogActivity;
import com.prometteur.cpapp.history.HistoryActivity;
import com.prometteur.cpapp.history.HistoryDetailsActivity;
import com.prometteur.cpapp.invoice.InvoiceFragment;
import com.prometteur.cpapp.notification.NotificationActivity;
import com.prometteur.cpapp.onboarding.ChangePasswordActivity;
import com.prometteur.cpapp.onboarding.LoginActivity;
import com.prometteur.cpapp.profile.ProfileActivity;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.services.ServicesActivity;
import com.prometteur.cpapp.settings.SettingsActivity;
import com.prometteur.cpapp.statistics.PerformaceActivity;
import com.prometteur.cpapp.statistics.StatisticsActivity;
import com.prometteur.cpapp.utils.NetworkChangeReceiver;
import com.prometteur.cpapp.utils.Preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.utils.Constants.APTCOUNT;
import static com.prometteur.cpapp.utils.Constants.BRANCHADDRESS;
import static com.prometteur.cpapp.utils.Constants.BRANCHCLOSEDSTATUS;
import static com.prometteur.cpapp.utils.Constants.BRANCHID;
import static com.prometteur.cpapp.utils.Constants.BRANCHIDVAL;
import static com.prometteur.cpapp.utils.Constants.BRANCHIMG;
import static com.prometteur.cpapp.utils.Constants.BRANCHNAME;
import static com.prometteur.cpapp.utils.Constants.BRANCHOPENSTATUS;
import static com.prometteur.cpapp.utils.Constants.MOBNO;
import static com.prometteur.cpapp.utils.Constants.NOTCOUNT;
import static com.prometteur.cpapp.utils.Constants.USERID;
import static com.prometteur.cpapp.utils.Constants.USERIDVAL;
import static com.prometteur.cpapp.utils.Constants.USERSESSION;
import static com.prometteur.cpapp.utils.Constants.USERSESSIONVAL;
import static com.prometteur.cpapp.utils.Utils.getBottomNavigationCount;
import static com.prometteur.cpapp.utils.Utils.isNetworkAvailable;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.setEmptyMsg;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showNoInternetDialog;
import static com.prometteur.cpapp.utils.Utils.showProgress;
import static com.prometteur.cpapp.utils.Utils.showSuccessToast;

public class DashboardMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {
    public static Switch swStatus;
    public static BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    ImageView ivNotification, ivSetting;
    TextView tvTitle,tvNotCount;
    //back to exit
    boolean doubleBackToExitPressedOnce = false;
    OfflineStatusBean ongoingBean;
    public static int menuPos = 0;
    public static void hideStatusIcon() {
        swStatus.setVisibility(View.GONE);
    }
boolean callAds=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_main);
        int currentapiVersion = Build.VERSION.SDK_INT;
        //   if (Build.VERSION_CODES.P == currentapiVersion || Build.VERSION_CODES.O == currentapiVersion || Build.VERSION_CODES.O_MR1 == currentapiVersion) {
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        //   }
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseApp.initializeApp(getApplicationContext());
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        FirebaseCrashlytics.getInstance().sendUnsentReports();
        setBroadCastReceiver();

        USERIDVAL = Preferences.getPreferenceValue(DashboardMainActivity.this, USERID);
        USERSESSIONVAL = Preferences.getPreferenceValue(DashboardMainActivity.this, USERSESSION);
        BRANCHIDVAL = Preferences.getPreferenceValue(DashboardMainActivity.this, BRANCHID);

        //toolbar section
        swStatus = findViewById(R.id.swStatus);
        ivNotification = findViewById(R.id.ivNotification);
        tvNotCount = findViewById(R.id.tvNotCount);
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvTitle.setMarqueeRepeatLimit(-1);
        tvTitle.setSingleLine(true);
        tvTitle.setSelected(true);
        tvTitle.setText(Preferences.getPreferenceValue(DashboardMainActivity.this, BRANCHNAME));
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardMainActivity.this, NotificationActivity.class));
            }
        });

        if (Preferences.getPreferenceValue(DashboardMainActivity.this, BRANCHOPENSTATUS).equalsIgnoreCase("1") && Preferences.getPreferenceValue(DashboardMainActivity.this, BRANCHCLOSEDSTATUS).equalsIgnoreCase("0")) {
            swStatus.setEnabled(true);
            swStatus.setChecked(true);
        } else {
            swStatus.setEnabled(false);
            swStatus.setChecked(false);
        }

        // main section
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        getBottomNavigationCount(DashboardMainActivity.this, bottomNavigationView);
       setNotificationCount();
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
//        custom hamburger icon
        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu_button, getTheme());
        toggle.setHomeAsUpIndicator(drawable);

        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(this);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;

                switch (menuItem.getItemId()) {

                    case R.id.navigation_home:
                        menuPos = 0;
                        callAds=true;
                        swStatus.setVisibility(View.VISIBLE);
                        tvTitle.setText(Preferences.getPreferenceValue(DashboardMainActivity.this, BRANCHNAME));
                        fragment = new AppointmentTabFragment();
                        getAdvertisements();
                        break;
                    case R.id.navigation_dashboard:
                        menuPos = 1;
                        swStatus.setVisibility(View.GONE);
                        tvTitle.setText(getResources().getString(R.string.appointment_requests));
                        fragment = new AppointmentBottomFragment();
                        break;
                    case R.id.navigation_profile:
                        menuPos = 2;
                        swStatus.setVisibility(View.GONE);
                        tvTitle.setText(getResources().getString(R.string.invoice));
                        fragment = new InvoiceFragment();
                        break;
                }

                return loadFragment(fragment);
            }
        });

        swStatus.setVisibility(View.VISIBLE);
        //getAdvertisements();
        NotificationBean.Result result = (NotificationBean.Result) getIntent().getSerializableExtra("objNoti");
        if (result != null) {
          if(result.getNotiType()!=null) {
              switch (Integer.parseInt(result.getNotiType())) {
                  case 1:// appoinment_request
                      menuPos=1;
                      bottomNavigationView.getMenu().findItem(R.id.navigation_dashboard).setChecked(true);
                      swStatus.setVisibility(View.GONE);
                      tvTitle.setText(getResources().getString(R.string.appointment_requests));
                      AppointmentBottomFragment fragment = new AppointmentBottomFragment();
                      loadFragment(fragment);
                      break;
                  case 2: // appoinment
                      menuPos=0;
                      bottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
                      tvTitle.setText(Preferences.getPreferenceValue(DashboardMainActivity.this, BRANCHNAME));
                      AppointmentTabFragment fragment1 = new AppointmentTabFragment();
                      loadFragment(fragment1);
                      break;
                  case 4: // Invoice
                      menuPos=2;
                      bottomNavigationView.getMenu().findItem(R.id.navigation_home).setChecked(true);
                      swStatus.setVisibility(View.GONE);
                      tvTitle.setText(getResources().getString(R.string.invoice));
                      InvoiceFragment fragment2 = new InvoiceFragment();
                      loadFragment(fragment2);
                      break;
              }
          }
        }
        if (!Preferences.getPreferenceValue(DashboardMainActivity.this, BRANCHCLOSEDSTATUS).equalsIgnoreCase("1")) {
            swStatus.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (swStatus.isChecked()) {
                        updateActiveInactiveStatus();
                    } else {
                        startActivity(new Intent(DashboardMainActivity.this, OfflineTimeDialogActivity.class).putExtra("swichVal", swStatus.isChecked()));

                    }
                }
            });
            swStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
        } else {

        }





    }

    private final int loc_request_code = 1234;
    private final String Fine_Location = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String Coarse_Location = Manifest.permission.ACCESS_COARSE_LOCATION;
    public void getLocationPermission() {
        Log.d("TAG", "getLocationPermission: getting location permission");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

            if (ContextCompat.checkSelfPermission(DashboardMainActivity.this, Fine_Location) ==
                    PackageManager.PERMISSION_GRANTED) {
                Log.d("TAG", "getLocationPermission:  fine permissions already granted");
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Coarse_Location) ==
                        PackageManager.PERMISSION_GRANTED) {
                    Loc_permissiongranted = true;
                    //initalizeMap();
                    getDeviceLocation();
                    Log.d("TAG", "getLocationPermission:  coarse permissions already granted");

                } else {
                    ActivityCompat.requestPermissions(DashboardMainActivity.this,
                            permissions, loc_request_code);
                }
            } else {
                ActivityCompat.requestPermissions(DashboardMainActivity.this,
                        permissions, loc_request_code);
            }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(loc_request_code==requestCode)
        {
            Loc_permissiongranted = true;
            getDeviceLocation();
        }
    }

    private static boolean Loc_permissiongranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private void getDeviceLocation() {
        Log.d("TAG", "getDeviceLocation: Getting current Location of device");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DashboardMainActivity.this);
        try {
                mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(DashboardMainActivity.this,
                        new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    Log.d("TAG", "onSuccess: location data");
                                    Log.d("TAG", "onSuccess: " + location.getLatitude() + "    " + location.getLongitude());
                                    strLat=""+location.getLatitude();
                                    strLong=""+location.getLongitude();
                                    // if(Preferences.getPreferenceValue(nActivity,"lat").equalsIgnoreCase("NA") || Preferences.getPreferenceValue(nActivity,"lat").isEmpty()) {
                                    Preferences.setPreferenceValue(DashboardMainActivity.this, "lat", strLat);
                                    Preferences.setPreferenceValue(DashboardMainActivity.this, "lon", strLong);
                                   /* }else
                                    {
                                        strLat=Preferences.getPreferenceValue(nActivity,"lat");
                                        strLong=Preferences.getPreferenceValue(nActivity,"lon");
                                    }*/
                                    if (isNetworkAvailable(DashboardMainActivity.this)) {

                                            getAdvertisements();

                                    } else {
                                        // Toast.makeText(ActivityLogin.this, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

                                        showNoInternetDialog(DashboardMainActivity.this);
                                    }
                                } else {
                                    Log.d("TAG", "onfailure: unable to find current device location");
                                    strLat=Preferences.getPreferenceValue(DashboardMainActivity.this,"lat");
                                    strLong=Preferences.getPreferenceValue(DashboardMainActivity.this,"lon");
                                    if(strLat.equalsIgnoreCase("NA"))
                                    {
                                        strLat="0";
                                        strLong="0";
                                    }

                                    if (isNetworkAvailable(DashboardMainActivity.this)) {
                                        getAdvertisements();
                                    } else {
                                        // Toast.makeText(ActivityLogin.this, getResources().getString(R.string.please_check_newtork_connection), Toast.LENGTH_SHORT).show();

                                        showNoInternetDialog(DashboardMainActivity.this);
                                    }

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "getDeviceLocation: " + e.getMessage());
                    }
                });

        } catch (SecurityException e) {
            Log.e("TAG", "getDeviceLocation: " + e.getMessage());
        }
    }
    private void setNotificationCount() {
        if(Preferences.getPreferenceValueInt(DashboardMainActivity.this,NOTCOUNT)!=0) {
            if(Preferences.getPreferenceValueInt(DashboardMainActivity.this,NOTCOUNT)>9) {
                tvNotCount.setText("9+");//String that you want to show in badge
            }else {
                tvNotCount.setText("" + Preferences.getPreferenceValueInt(DashboardMainActivity.this, NOTCOUNT));//String that you want to show in badge
            }
        }else{
            tvNotCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        showSuccessToast(this, "Please click BACK again to exit");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkInternet();
        getHeaderView();
        setNotificationCount();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(menuPos==0){
                getAdvertisements();
            }else if(menuPos==1){
                loadFragment(new AppointmentBottomFragment());
            }else {
                getLocationPermission();
            }
            return;
        }else {
            if(menuPos==1){
                loadFragment(new AppointmentBottomFragment());
            }else {
                getDeviceLocation();
            }
        }
        /* InstallReferrerReceiver installReferrerReceiver=new InstallReferrerReceiver();
        registerReceiver(installReferrerReceiver,null);*/
    }

    private void getHeaderView() {
        View header = navigationView.getHeaderView(0);
        ImageView ivSetting = header.findViewById(R.id.ivSetting);
        ImageView ivSalonImg = header.findViewById(R.id.ivSalonImg);
        TextView salonName = header.findViewById(R.id.salonName);
        TextView tvLocation = header.findViewById(R.id.tvLocation);
        LinearLayout linHeaderClick = header.findViewById(R.id.linHeaderClick);
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(DashboardMainActivity.this, SettingsActivity.class));
            }
        });
        linHeaderClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(DashboardMainActivity.this, ProfileActivity.class));
            }
        });
        tvLocation.setText(Preferences.getPreferenceValue(DashboardMainActivity.this, BRANCHADDRESS));
        salonName.setText(Preferences.getPreferenceValue(DashboardMainActivity.this, BRANCHNAME));
        tvTitle.setText(Preferences.getPreferenceValue(DashboardMainActivity.this, BRANCHNAME));
        Glide.with(this).load(Preferences.getPreferenceValue(DashboardMainActivity.this, BRANCHIMG)).error(R.drawable.img_chair).into(ivSalonImg);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history) {
            startActivity(new Intent(this, HistoryActivity.class));
//            startActivity(new Intent(this, ExpressInterestActivity.class));
        } else if (id == R.id.nav_statistics) {
            startActivity(new Intent(this, StatisticsActivity.class));
//            startActivity(new Intent(this, ForgotPasswordActivity.class));

        } else if (id == R.id.nav_performance) {
            startActivity(new Intent(this, PerformaceActivity.class));
        } else if (id == R.id.nav_rate_card) {
            startActivity(new Intent(this, ServicesActivity.class));
        } else if (id == R.id.nav_brand) {
            startActivity(new Intent(this, BrandsActivity.class));
        } else if (id == R.id.nav_privacy) {
            openWebPage("https://mooi.app/our-policy/Mooi_ChannelPartner_CodeOfConduct_Responsibilities.html");


        } else if (id == R.id.nav_blog) {

        } else if (id == R.id.nav_help) {
        openWebPage("https://mooi.app/our-policy/Mooi_faq.html");
        } else if (id == R.id.nav_term_condi) {
            openWebPage("https://mooi.app/our-policy/Mooi_ChannelPartner_TermsAndConditions.html");
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void updateActiveInactiveStatus() {

        ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(DashboardMainActivity.this, 0);
        progressDialog.show();
        service.updateActiveInactiveStatus(BRANCHIDVAL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OfflineStatusBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OfflineStatusBean loginBeanObj) {
                        progressDialog.dismiss();
                        ongoingBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        swStatus.setChecked(!getIntent().getBooleanExtra("swichVal", false));
                        showFailToast(DashboardMainActivity.this,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (ongoingBean.getStatus() == 1) {
                            Preferences.setPreferenceValue(DashboardMainActivity.this, BRANCHOPENSTATUS, "1");
                            swStatus.setChecked(true);
                            showSuccessToast(DashboardMainActivity.this,"Salon is Online now");

                        } else if (ongoingBean.getStatus() == 3) {
                            swStatus.setChecked(!getIntent().getBooleanExtra("swichVal", false));
                            showFailToast(DashboardMainActivity.this,  "" + ongoingBean.getMsg());
                            logout(DashboardMainActivity.this);
                        }

                    }
                });

    }

    public class InstallReferrerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent == null) {
                return;
            }

            String referrerId = intent.getStringExtra("referrer");
            Log.i("Install Receiver", referrerId);

            if (referrerId == null) {
                return;
            }
        }
    }
    BroadcastReceiver countBroadCastReceiver;
    public void setBroadCastReceiver() {
        countBroadCastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
                bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
                getBottomNavigationCount(DashboardMainActivity.this, bottomNavigationView);
                setNotificationCount();
            }
        };
       try {
           unregisterReceiver(countBroadCastReceiver);
       }catch (Exception e)
       {
           e.printStackTrace();
       }
        IntentFilter filter = new IntentFilter("SendToService");
        registerReceiver(countBroadCastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(countBroadCastReceiver);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    AdvertisementBean advertisementBean;
    public static List<AdvertisementBean.Result> adsResult=new ArrayList<>();
    private void getAdvertisements() {

        final ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(DashboardMainActivity.this, 0);
        if(!callAds) {
            progressDialog.show();
        }
        service.getAdvertisement(getPincode(),BRANCHIDVAL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AdvertisementBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AdvertisementBean loginBeanObj) {
                        progressDialog.dismiss();
                        advertisementBean = loginBeanObj;

                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        loadFragment(new AppointmentTabFragment());
                        showFailToast(DashboardMainActivity.this,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if(menuPos==0){

                                loadFragment(new AppointmentTabFragment());
                        }else if(menuPos==1){
                            loadFragment(new AppointmentBottomFragment());
                        }

                        if (advertisementBean.getStatus() == 1) {
                            adsResult=advertisementBean.getResult();
                            Preferences.setPreferenceValue(DashboardMainActivity.this, BRANCHOPENSTATUS, advertisementBean.getBranOpenStatus());
                            Preferences.setPreferenceValue(DashboardMainActivity.this, BRANCHCLOSEDSTATUS, advertisementBean.getBranCloseStatus());

                            if (Preferences.getPreferenceValue(DashboardMainActivity.this, BRANCHOPENSTATUS).equalsIgnoreCase("1") && Preferences.getPreferenceValue(DashboardMainActivity.this, BRANCHCLOSEDSTATUS).equalsIgnoreCase("0")) {
                                swStatus.setEnabled(true);
                                swStatus.setChecked(true);

                                swStatus.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        if (swStatus.isChecked()) {
                                            updateActiveInactiveStatus();
                                        } else {
                                            startActivity(new Intent(DashboardMainActivity.this, OfflineTimeDialogActivity.class).putExtra("swichVal", swStatus.isChecked()));

                                        }
                                    }
                                });
                            }else {
                                if(!Preferences.getPreferenceValue(DashboardMainActivity.this, BRANCHCLOSEDSTATUS).equalsIgnoreCase("0")) {
                                    swStatus.setEnabled(false);
                                }else
                                {
                                    swStatus.setEnabled(true);
                                }
                                swStatus.setChecked(false);
                            }


                        } else if (advertisementBean.getStatus() == 3) {
                            showFailToast(DashboardMainActivity.this,  "" + advertisementBean.getMsg());
                            logout(DashboardMainActivity.this);
                        }
                     //   setEmptyMsg(mDataList, recyclerView, ivNoData);
                    }
                });
    }
    static String strLat="0";
    static String strLong="0";
    @Override
    public void onLocationChanged(Location location) {

        strLat=String.valueOf(location.getLatitude());
        strLong=String.valueOf(location.getLongitude());

        Preferences.setPreferenceValue(DashboardMainActivity.this, "lat", strLat);
        Preferences.setPreferenceValue(DashboardMainActivity.this, "lon", strLong);


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public String getPincode() {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(DashboardMainActivity.this, Locale.getDefault());

        try {
            if (!Preferences.getPreferenceValue(DashboardMainActivity.this, "lat").equalsIgnoreCase("NA")) {
                strLat = Preferences.getPreferenceValue(DashboardMainActivity.this, "lat");
                strLong = Preferences.getPreferenceValue(DashboardMainActivity.this, "lon");
            }
            addresses = geocoder.getFromLocation(Double.parseDouble(strLat), Double.parseDouble(strLong), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (addresses != null) {
            if (addresses.size() != 0) {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                return postalCode;
            }
        }
        return "";
    }


    public NetworkChangeReceiver receiver;
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


    public void openWebPage(String url) {
        try {
            Uri webpage = Uri.parse(url);
            Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            showFailToast(this, "No application can handle this request. Please install a web browser or check your URL.");
            e.printStackTrace();
        }
    }





}
