package com.prometteur.cpapp.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.ComboListAppDetailsAdapter;
import com.prometteur.cpapp.adapter.OperatorAdapter;
import com.prometteur.cpapp.adapter.PromoOffListAppDetailsAdapter;
import com.prometteur.cpapp.adapter.ServiceSelectedAdapter;
import com.prometteur.cpapp.adapter.TimeSlotAdapter;
import com.prometteur.cpapp.beans.OfflineStatusBean;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.beans.ReschduleGetDataBean;
import com.prometteur.cpapp.beans.ReviewBean;
import com.prometteur.cpapp.drawer.DashboardMainActivity;
import com.prometteur.cpapp.listener.OnTimeslotItemClickListener;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.utils.NetworkChangeReceiver;
import com.prometteur.cpapp.utils.Preferences;
import com.prometteur.cpapp.utils.spinner.SingleSpinnerSearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.prometteur.cpapp.utils.Utils.getTimeShow12to24HR;
import static com.prometteur.cpapp.utils.Utils.getTimeSlots;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showProgress;
import static com.prometteur.cpapp.utils.Utils.showSuccessToast;

public class RescheduleActivity extends AppCompatActivity implements OnTimeslotItemClickListener {

    RecyclerView recyclerViewTimeSlot, recyclerViewOperator;
    GridLayoutManager mLayoutManager;
    OperatorAdapter operatorAdapter;
    TimeSlotAdapter timeSlotAdapter;
    SingleSpinnerSearch spServices;
    String serviceId = "0";
    String day = "", strDate = "";
    CalendarView calendarView;
    int position = 0;
    ReschduleGetDataBean ongoingBean;
    List<ReschduleGetDataBean.Result> results = new ArrayList<>();
    public static List<ReschduleGetDataBean.Service> services = new ArrayList<>();
    public static List<ReschduleGetDataBean.Combo> combos = new ArrayList<>();
    public static List<ReschduleGetDataBean.Offer> offers = new ArrayList<>();
    String strTimeSlot = "", optId;
    public static JSONArray jsonArray = new JSONArray();
    Button btnDone;
    boolean dateChange = false, timeChange = false, operatorChange = false;
    OfflineStatusBean offlineStatusBean;
    private List<ReschduleGetDataBean.Operator> mDataList = new ArrayList<>();
    private List<String> timeSlots = new ArrayList<>();
    private LinearLayoutManager mLayoutManager1;
    int year=0;int month=0;int dayOfMonth=0;
    TextView tvTitle;
    RecyclerView recycleComboSelected,recyclePromoOffSelected,recycleServicesSelected;
    AppCompatActivity nActivity;
    Date appDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reschedule_form);
        setToolbar();

        recycleComboSelected = findViewById(R.id.recycle_ComboSelected);
        recyclePromoOffSelected = findViewById(R.id.recycle_PromoOffSelected);
        recycleServicesSelected = findViewById(R.id.recyclerView);

        recyclerViewTimeSlot = findViewById(R.id.recyclerViewTimeSlot);
        recyclerViewOperator = findViewById(R.id.recyclerViewOperator);
        spServices = findViewById(R.id.spServices);
        calendarView = findViewById(R.id.calendarView);
        btnDone = findViewById(R.id.btnDone);
        tvTitle = findViewById(R.id.tvTitle);
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        day = outFormat.format(new Date());
        Calendar calendar=Calendar.getInstance();
        dayOfMonth=calendar.get(Calendar.DATE);
        month=calendar.get(Calendar.MONTH);
        year=calendar.get(Calendar.YEAR);
        timeSlots=new ArrayList<>();


        calendarView.setMinDate((new Date().getTime()));
        //calendarView.setMinDate((new Date().getTime()) - 86400000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            strDate=getIntent().getStringExtra("appDate");
             appDate = simpleDateFormat.parse(strDate);
            calendarView.setDate(appDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        getRescheduleGetData(day.toLowerCase());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year1, int month1, int dayOfMonth1) {
                dateChange = true;
                dayOfMonth=dayOfMonth1;
                month=month1;
                year=year1;
                String input = dayOfMonth + "-" + (month + 1) + "-" + year;
                SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = inFormat.parse(input);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                day = outFormat.format(date);
                timeSlots=new ArrayList<>();
                getRescheduleGetData(day.toLowerCase());
            }
        });
        calendarView.setMinDate(System.currentTimeMillis() - 1000);
        calendarView.setSystemUiVisibility(getResources().getColor(R.color.skyBlueDark));

        //initRecyclerView();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rescheduleAppointment();
            }
        });

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    public void setToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Reschedule Appointment");
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (Build.VERSION_CODES.P == currentapiVersion) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onSupportNavigateUp();
        finish();
        return true;
    }

    private void initAdapter() {
        nActivity=RescheduleActivity.this;
        // for time slot
        mLayoutManager = new GridLayoutManager(nActivity, 4);
        recyclerViewTimeSlot.setLayoutManager(mLayoutManager);
        // recyclerViewTimeSlot.addItemDecoration(new SpacesItemDecoration(10));
        final String[] strTimeSlots = {Preferences.getPreferenceValue(nActivity, "workingHour")};
        final String strHolidayFrom= Preferences.getPreferenceValue(nActivity,"holidayFrom");
        final String strHolidayTo= Preferences.getPreferenceValue(nActivity,"holidayTo");
        final String strOffDay=Preferences.getPreferenceValue(nActivity,"offDay");
        final String strOfflineEndTime= Preferences.getPreferenceValue(nActivity,"offlineEndTime");

        // add one because month starts at 0
        // month = month + 1;
        // output to log cat **not sure how to format year to two places here**
        String newDate = year+"-"+(month+1)+"-"+dayOfMonth;
        Calendar calendar=Calendar.getInstance();
        calendar.set( year,  month, dayOfMonth);
        Log.d("NEW_DATE", newDate);
        long date = calendar.getTimeInMillis();

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormatEndDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        try {


            Date date1=simpleDateFormat.parse(newDate);
            Date offlineEndDate=simpleDateFormatEndDate.parse(strOfflineEndTime);
            Date fromDate=simpleDateFormat.parse(strHolidayFrom);
            Date toDate=simpleDateFormat.parse(strHolidayTo);
            Log.i("fromDate",fromDate.toString()+" toDate "+toDate.toString());
            long lonHolFrom=fromDate.getTime();
            long lonHolTo=toDate.getTime();
            long todayDateTime=date1.getTime();
            long todayDateTimeEndTime=offlineEndDate.getTime();
            //date1.setMonth((date1.getMonth()+1));

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            String dayOfTheWeek = sdf.format(date1);

            if(!dayOfTheWeek.equalsIgnoreCase(strOffDay)) {

                if (!(date1.getTime() >= lonHolFrom && date1.getTime() <= lonHolTo)) {

                    // strAppDate = simpleDateFormat.format(date);
                    /*SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("E, dd MMM yyyy, ");
                    strDate = simpleDateFormat2.format(date);*/
                    strTimeSlots[0]=Preferences.getPreferenceValue(nActivity, "workingHour");
                    //  strTime="";
                    if (!strTimeSlots[0].isEmpty()) {

                        //for today
                        final SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                        final Date dateStartWithDate= new Date();
                        Date startTimeDate = null;
                        try {
                            startTimeDate = sdf1.parse(strTimeSlots[0].split("-")[0]);
                            dateStartWithDate.setHours(startTimeDate.getHours());
                            dateStartWithDate.setMinutes(startTimeDate.getMinutes());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //for today end


                        Date curDate = new Date();
                        Date nextDate = new Date();

                        nextDate.setTime(nextDate.getTime()+86400000); //added 24 hour in current date
                        date1.setHours(0);//1591883765659 1591813800000
                        date1.setMinutes(0);
                        date1.setSeconds(0);
                        curDate.setHours(0);
                        curDate.setMinutes(0);
                        curDate.setSeconds(0);
                        nextDate.setHours(0);
                        nextDate.setMinutes(0);
                        nextDate.setSeconds(0);

                        String changeFTime;
                        if (date1.getDate() == curDate.getDate() && date1.getMonth() == curDate.getMonth() && date1.getYear() == curDate.getYear()) {  //todays part

                            final long currentDateTime=new Date().getTime();
                            if(currentDateTime<dateStartWithDate.getTime())
                            {
                                Date date2 = null;
                                try {
                                    date2 = sdf1.parse(strTimeSlots[0].split("-")[0]);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Date date3 = new Date();
                                date3.setTime(date2.getTime());
                                changeFTime = sdf1.format(date3.getTime());

                                strTimeSlots[0] = changeFTime + "-" + strTimeSlots[0].split("-")[1];
                            }else
                            if(new Date().getTime()>todayDateTimeEndTime) {
                                changeFTime = sdf1.format(new Date().getTime());
                            }else
                            {
                                Date date2=new Date();
                                date2.setTime(todayDateTimeEndTime);
                                changeFTime = sdf1.format(date2.getTime());
                            }
                            strTimeSlots[0] = changeFTime + "-" + strTimeSlots[0].split("-")[1];

                            Date dateEndWithDate= new Date();
                            Date dateEnd= null;
                            try {
                                dateEnd = sdf1.parse(strTimeSlots[0].split("-")[1]);
                                dateEndWithDate.setHours(dateEnd.getHours());
                                dateEndWithDate.setMinutes(dateEnd.getMinutes());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if(new Date().getTime()<(dateEndWithDate.getTime()-900000)){
                                /*bookAppointmentBinding.tvSelectDateTitle.setVisibility(View.GONE);
                                bookAppointmentBinding.calendarView.setVisibility(View.GONE);
                                bookAppointmentBinding.tvSelectTimeSlotTitle.setVisibility(View.VISIBLE);
                                bookAppointmentBinding.recycleTimeSlot.setVisibility(View.VISIBLE);
                                bookAppointmentBinding.btnBookNow.setVisibility(View.VISIBLE);*/
                                timeSlots = getTimeSlots(strTimeSlots[0]);
                                strDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                                //  recycleTimeSlot.setAdapter(new BookingTimeSlotAdapter(nActivity, timeSlots, ActivityBookAppointment.this));
                            }else
                            {
                                showFailToast(nActivity, "Salon has holiday on this day");
                                calendarView.setDate(appDate.getTime());
                            }
                        }else {
                            String strWeekDay="";
                            boolean isOneHrNextFlag=false;
                            switch (strOffDay)
                            {
                                case "Monday":
                                    strWeekDay="Tuesday";
                                    break;
                                case "Tuesday":
                                    strWeekDay="Wednesday";
                                    break;
                                case "Wednesday":
                                    strWeekDay="Thursday";
                                    break;
                                case "Thursday":
                                    strWeekDay="Friday";
                                    break;
                                case "Friday":
                                    strWeekDay="Saturday";
                                    break;
                                case "Saturday":
                                    strWeekDay="Sunday";
                                    break;
                                case "Sunday":
                                    strWeekDay="Monday";
                                    break;
                            }
                            if (date1.getTime() == (lonHolTo+86400000)) {
                                nextDate.setTime(lonHolTo);
                                isOneHrNextFlag=true;
                            }else if(dayOfTheWeek.equalsIgnoreCase(strWeekDay)) {
                                nextDate.setTime(date1.getTime());
                                isOneHrNextFlag=true;
                            }else {
                                nextDate = new Date();
                                nextDate.setTime(nextDate.getTime()+86400000); //added 24 hour in current date
                            }



                            nextDate.setHours(0);
                            nextDate.setMinutes(0);
                            nextDate.setSeconds(0);
                            if (date1.getDate() == nextDate.getDate() && date1.getMonth() == nextDate.getMonth() && date1.getYear() == nextDate.getYear()) //next day
                            {

                                Date dateEndWithDate= new Date();
                                Date dateEnd= null;
                                try {
                                    dateEnd = sdf1.parse(strTimeSlots[0].split("-")[1]);
                                    dateEndWithDate.setHours(dateEnd.getHours());
                                    dateEndWithDate.setMinutes(dateEnd.getMinutes());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                              /*  bookAppointmentBinding.tvSelectDateTitle.setVisibility(View.GONE);
                                bookAppointmentBinding.calendarView.setVisibility(View.GONE);
                                bookAppointmentBinding.tvSelectTimeSlotTitle.setVisibility(View.VISIBLE);
                                bookAppointmentBinding.recycleTimeSlot.setVisibility(View.VISIBLE);
                                bookAppointmentBinding.btnBookNow.setVisibility(View.VISIBLE);*/
                                if(!(new Date().getTime()<(dateEndWithDate.getTime()-900000))) {//when todays time is closed


                                    Date date2 = null;
                                    try {
                                        date2 = sdf1.parse(strTimeSlots[0].split("-")[0]);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    Date date3 = new Date();
                                    date3.setTime(date2.getTime() + 3600000);  //add 1 hour in next day
                                    changeFTime = sdf1.format(date3.getTime());

                                    strTimeSlots[0] = changeFTime + "-" + strTimeSlots[0].split("-")[1];
                                }else
                                {
                                    if(isOneHrNextFlag)
                                    {
                                        Date date2 = null;
                                        try {
                                            date2 = sdf1.parse(strTimeSlots[0].split("-")[0]);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        Date date3 = new Date();
                                        date3.setTime(date2.getTime() + 3600000);  //add 1 hour in next day
                                        changeFTime = sdf1.format(date3.getTime());

                                        strTimeSlots[0] = changeFTime + "-" + strTimeSlots[0].split("-")[1];
                                    }
                                }
                                strDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                                timeSlots = getTimeSlots(strTimeSlots[0]);

                                // bookAppointmentBinding.recycleTimeSlot.setAdapter(new BookingTimeSlotAdapter(nActivity, timeSlots, ActivityBookAppointment.this));

                            } else {
                             /*   bookAppointmentBinding.tvSelectDateTitle.setVisibility(View.GONE);
                                bookAppointmentBinding.calendarView.setVisibility(View.GONE);
                                bookAppointmentBinding.tvSelectTimeSlotTitle.setVisibility(View.VISIBLE);
                                bookAppointmentBinding.btnBookNow.setVisibility(View.VISIBLE);*/
                                strDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                                timeSlots = getTimeSlots(strTimeSlots[0]);
                                // bookAppointmentBinding.recycleTimeSlot.setAdapter(new BookingTimeSlotAdapter(nActivity, timeSlots, ActivityBookAppointment.this));
                            }
                        }

                    }

                } else {
                    showFailToast(nActivity, "Salon has holiday on this day");
                    calendarView.setDate(appDate.getTime());
                }

            }else
            {
                showFailToast(nActivity, "Salon has week off on this day");
                calendarView.setDate(appDate.getTime());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        timeSlotAdapter = new TimeSlotAdapter(nActivity, timeSlots, this, getIntent().getStringExtra("appTime"));
        recyclerViewTimeSlot.setAdapter(timeSlotAdapter);

        if(ongoingBean.getServices().size()==0)
        {
            tvTitle.setVisibility(View.GONE);
        }

        //combo list
        recycleComboSelected.setLayoutManager(new LinearLayoutManager(nActivity));
        recycleComboSelected.setAdapter(new ComboListAppDetailsAdapter(nActivity,combos,false));
        //promo offer list
        recyclePromoOffSelected.setLayoutManager(new LinearLayoutManager(nActivity));
        recyclePromoOffSelected.setAdapter(new PromoOffListAppDetailsAdapter(nActivity,offers,false));
        //single services list
        recycleServicesSelected.setLayoutManager(new LinearLayoutManager(nActivity));
        recycleServicesSelected.setAdapter(new ServiceSelectedAdapter(nActivity,ongoingBean.getServices()));

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

    private void initRecyclerView() {
        initAdapter();
        recyclerViewTimeSlot.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //if(recyclerView.getChildAt(0).top < 0) dropshadow.setVisible(); else dropshadow.setGone();
            }
        });
    }
    private void getRescheduleGetData(String day) {

        final ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(RescheduleActivity.this, 0);
        progressDialog.show();
        service.getRescheduleGetData(getIntent().getStringExtra("appointmentId"), day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReschduleGetDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ReschduleGetDataBean loginBeanObj) {
                        progressDialog.dismiss();
                        ongoingBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(RescheduleActivity.this,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (ongoingBean.getStatus() == 1) {
                            results = ongoingBean.getResult();
                            services = ongoingBean.getServices();
                            combos= ongoingBean.getCombo();
                            offers = ongoingBean.getOffers();
                            strTimeSlot=getIntent().getStringExtra("appTime");
                            jsonArray = new JSONArray();
                            for (ReschduleGetDataBean.Service service1 : services) {
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("aptser_apt_id", getIntent().getStringExtra("appointmentId"));
                                    jsonObject.put("aptser_type", "single");
                                    jsonObject.put("aptser_offer_id", "");
                                    jsonObject.put("aptser_service_id", service1.getSrvcId());
                                    jsonObject.put("aptser_operator_id", service1.getSrvcOperators());
                                    jsonObject.put("aptser_service_price", service1.getAptserServicePrice());
                                    jsonObject.put("aptser_offer_price", service1.getAptserOfferPrice());
                                    jsonObject.put("aptser_offer_main_price", service1.getAptserOfferMainPrice());
                                    jsonObject.put("aptser_mooi_commission", service1.getAptserMooiCommission());
                                    jsonObject.put("aptser_offer_name", service1.getAptserOfferName());
                                    jsonObject.put("aptser_service_name", service1.getAptserServiceName());
                                    jsonObject.put("aptser_brand_name", service1.getAptserBrandName());
                                    jsonObject.put("aptser_mooi_discount", service1.getAptserMooiDiscount());
                                    jsonObject.put("aptser_offer_discount", service1.getAptserOfferDiscount());


                                } catch (JSONException e) {
                                    e.printStackTrace();//aptser_service_price":"200.00","aptser_offer_price":"0.00","aptser_offer_main_price":"0.00","aptser_mooi_commission
                                }//{"aptser_apt_id":"980","aptser_type":"single","aptser_offer_id":"","aptser_service_id":"24","aptser_operator_id":"10"},

                                jsonArray.put(jsonObject);
                            }
                            for(int i=0;i<combos.size();i++) {
                                for (ReschduleGetDataBean.ComboService comboService : combos.get(i).getComboServices()) {
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("aptser_apt_id", getIntent().getStringExtra("appointmentId"));
                                        jsonObject.put("aptser_type", "combo_offer");
                                        jsonObject.put("aptser_offer_id", combos.get(i).getOfferId());
                                        jsonObject.put("aptser_service_id", comboService.getSrvcId());
                                        jsonObject.put("aptser_operator_id", comboService.getSrvcOperators());
                                        jsonObject.put("aptser_service_price", comboService.getAptserServicePrice());
                                        jsonObject.put("aptser_offer_price", comboService.getAptserOfferPrice());
                                        jsonObject.put("aptser_offer_main_price", comboService.getAptserOfferMainPrice());
                                        jsonObject.put("aptser_mooi_commission", comboService.getAptserMooiCommission());
                                        jsonObject.put("aptser_offer_name", combos.get(i).getAptserOfferName());
                                        jsonObject.put("aptser_service_name", combos.get(i).getAptserServiceName());
                                        jsonObject.put("aptser_brand_name", combos.get(i).getAptserBrandName());
                                        jsonObject.put("aptser_mooi_discount", combos.get(i).getAptserMooiDiscount());
                                        jsonObject.put("aptser_offer_discount", combos.get(i).getAptserOfferDiscount());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }//{"aptser_apt_id":"980","aptser_type":"single","aptser_offer_id":"","aptser_service_id":"24","aptser_operator_id":"10"},

                                    jsonArray.put(jsonObject);
                                }
                            }

                            if(offers.size()>0){
                                for (ReschduleGetDataBean.ProService proService : offers.get(0).getProServices()) {
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("aptser_apt_id", getIntent().getStringExtra("appointmentId"));
                                        jsonObject.put("aptser_type", "promotional_offer");
                                        jsonObject.put("aptser_offer_id", offers.get(0).getOfferId());
                                        jsonObject.put("aptser_service_id", proService.getSrvcId());
                                        jsonObject.put("aptser_operator_id", proService.getSrvcOperators());
                                        jsonObject.put("aptser_service_price", proService.getAptserServicePrice());
                                        jsonObject.put("aptser_offer_price", proService.getAptserOfferPrice());
                                        jsonObject.put("aptser_offer_main_price", proService.getAptserOfferMainPrice());
                                        jsonObject.put("aptser_mooi_commission", proService.getAptserMooiCommission());
                                        jsonObject.put("aptser_offer_name", offers.get(0).getAptserOfferName());
                                        jsonObject.put("aptser_service_name", offers.get(0).getAptserServiceName());
                                        jsonObject.put("aptser_brand_name", offers.get(0).getAptserBrandName());
                                        jsonObject.put("aptser_mooi_discount", offers.get(0).getAptserMooiDiscount());
                                        jsonObject.put("aptser_offer_discount", offers.get(0).getAptserOfferDiscount());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }//{"aptser_apt_id":"980","aptser_type":"single","aptser_offer_id":"","aptser_service_id":"24","aptser_operator_id":"10"},

                                    jsonArray.put(jsonObject);
                                }
                            }

                            //String strTimeSlots = results.get(0).getBranWorkHrs();
                            nActivity=RescheduleActivity.this;
                            Preferences.setPreferenceValue(nActivity,"workingHour",results.get(0).getBranWorkHrs());
                            Preferences.setPreferenceValue(nActivity,"holidayFrom",results.get(0).getBranHolidayFrom());
                            Preferences.setPreferenceValue(nActivity,"holidayTo",results.get(0).getBranHolidayTo());
                            Preferences.setPreferenceValue(nActivity,"offDay",results.get(0).getBranOffDay());
                            Preferences.setPreferenceValue(nActivity,"offlineEndTime",results.get(0).getBranOpenedOn());

                            /*if (!strTimeSlots.isEmpty()) {
                                timeSlots = getTimeSlots(strTimeSlots);
                            }*/
                            //setServiceSpinner();
                            initRecyclerView();

                        } else if (ongoingBean.getStatus() == 3) {
                            showFailToast(RescheduleActivity.this,  "" + ongoingBean.getMsg());
                            logout(RescheduleActivity.this);
                        }
//                        cPresenter.updateCoinList(allCurrencyList);
                        //setEmptyMsg(results,recyclerView,ivNoAppoint);
                    }
                });

    }

    private void rescheduleAppointment() {

        jsonArray = new JSONArray();
        for (ReschduleGetDataBean.Service service1 : services) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("aptser_apt_id", getIntent().getStringExtra("appointmentId"));
                jsonObject.put("aptser_type", "single");
                jsonObject.put("aptser_offer_id", "");
                jsonObject.put("aptser_service_id", service1.getSrvcId());
                for(int i=0;i<service1.getOperators().size();i++) {
                    if(service1.getOperators().get(i).getSelected().equalsIgnoreCase("selected")) {
                        jsonObject.put("aptser_operator_id", service1.getOperators().get(i).getUserId());
                    }
                }
                jsonObject.put("aptser_service_price", service1.getAptserServicePrice());
                jsonObject.put("aptser_offer_price", service1.getAptserOfferPrice());
                jsonObject.put("aptser_offer_main_price", service1.getAptserOfferMainPrice());
                jsonObject.put("aptser_mooi_commission", service1.getAptserMooiCommission());
                jsonObject.put("aptser_offer_name", service1.getAptserOfferName());
                jsonObject.put("aptser_service_name", service1.getAptserServiceName());
                jsonObject.put("aptser_brand_name", service1.getAptserBrandName());
                jsonObject.put("aptser_mooi_discount", service1.getAptserMooiDiscount());
                jsonObject.put("aptser_offer_discount", service1.getAptserOfferDiscount());
            } catch (JSONException e) {
                e.printStackTrace();
            }//{"aptser_apt_id":"980","aptser_type":"single","aptser_offer_id":"","aptser_service_id":"24","aptser_operator_id":"10"},

            jsonArray.put(jsonObject);
        }
        for(int i=0;i<combos.size();i++) {
            for (ReschduleGetDataBean.ComboService comboService : combos.get(i).getComboServices()) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("aptser_apt_id", getIntent().getStringExtra("appointmentId"));
                    jsonObject.put("aptser_type", "combo_offer");
                    jsonObject.put("aptser_offer_id", combos.get(i).getOfferId());
                    jsonObject.put("aptser_service_id", comboService.getSrvcId());
                    boolean isOperator=false;
                    for(int j=0;j<comboService.getOperators().size();j++) {
                        if(comboService.getOperators().get(j).getSelected().equalsIgnoreCase("selected")) {
                            isOperator=true;
                            jsonObject.put("aptser_operator_id", comboService.getOperators().get(j).getUserId());
                        }
                    }
                    if(!isOperator)
                    {
                        jsonObject.put("aptser_operator_id", "");
                    }
                    jsonObject.put("aptser_service_price", comboService.getAptserServicePrice());
                    jsonObject.put("aptser_offer_price", comboService.getAptserOfferPrice());
                    jsonObject.put("aptser_offer_main_price", comboService.getAptserOfferMainPrice());
                    jsonObject.put("aptser_mooi_commission", comboService.getAptserMooiCommission());
                    jsonObject.put("aptser_offer_name", combos.get(i).getAptserOfferName());
                    jsonObject.put("aptser_service_name", comboService.getSrvcName());
                    jsonObject.put("aptser_brand_name", comboService.getBrndName());
                    jsonObject.put("aptser_mooi_discount", combos.get(i).getAptserMooiDiscount());
                    jsonObject.put("aptser_offer_discount", combos.get(i).getAptserOfferDiscount());
                } catch (JSONException e) {
                    e.printStackTrace();
                }//{"aptser_apt_id":"980","aptser_type":"single","aptser_offer_id":"","aptser_service_id":"24","aptser_operator_id":"10"},

                jsonArray.put(jsonObject);
            }
        }

        if(offers.size()>0){
            for (ReschduleGetDataBean.ProService proService : offers.get(0).getProServices()) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("aptser_apt_id", getIntent().getStringExtra("appointmentId"));
                    jsonObject.put("aptser_type", "promotional_offer");
                    jsonObject.put("aptser_offer_id", offers.get(0).getOfferId());
                    jsonObject.put("aptser_service_id", proService.getSrvcId());
                    boolean isOperator=false;
                    for(int i=0;i<proService.getOperators().size();i++) {
                        if(proService.getOperators().get(i).getSelected().equalsIgnoreCase("selected")) {
                            isOperator=true;
                            jsonObject.put("aptser_operator_id", proService.getOperators().get(i).getUserId());
                        }
                    }

                    if(!isOperator)
                    {
                        jsonObject.put("aptser_operator_id", "");
                    }
                    jsonObject.put("aptser_service_price", proService.getAptserServicePrice());
                    jsonObject.put("aptser_offer_price", proService.getAptserOfferPrice());
                    jsonObject.put("aptser_offer_main_price", proService.getAptserOfferMainPrice());
                    jsonObject.put("aptser_mooi_commission", proService.getAptserMooiCommission());
                    jsonObject.put("aptser_offer_name", offers.get(0).getAptserOfferName());
                    jsonObject.put("aptser_service_name", proService.getSrvcName());
                    jsonObject.put("aptser_brand_name", proService.getBrndName());
                    jsonObject.put("aptser_mooi_discount", offers.get(0).getAptserMooiDiscount());
                    jsonObject.put("aptser_offer_discount", offers.get(0).getAptserOfferDiscount());
                } catch (JSONException e) {
                    e.printStackTrace();
                }//{"aptser_apt_id":"980","aptser_type":"single","aptser_offer_id":"","aptser_service_id":"24","aptser_operator_id":"10"},

                jsonArray.put(jsonObject);
            }
        }

        final ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(RescheduleActivity.this, 0);
        progressDialog.show();
        String time12="";
        if(strTimeSlot.contains("AM") || strTimeSlot.contains("PM"))
        {
            time12=getTimeShow12to24HR(strTimeSlot);
        }else
        {
            time12=strTimeSlot;
        }
        service.rescheduleAppointment(getIntent().getStringExtra("appointmentId"), strDate, time12, jsonArray.toString(),
                dateChange, timeChange, operatorChange)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OfflineStatusBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OfflineStatusBean loginBeanObj) {
                        progressDialog.dismiss();
                        offlineStatusBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(RescheduleActivity.this,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (ongoingBean.getStatus() == 1) {
                            setResult(103);
                            finish();
                        } else if (ongoingBean.getStatus() == 3) {
                            showFailToast(RescheduleActivity.this,  "" + ongoingBean.getMsg());
                            logout(RescheduleActivity.this);
                        }
//                        cPresenter.updateCoinList(allCurrencyList);
                        //setEmptyMsg(results,recyclerView,ivNoAppoint);
                        finish();
                    }
                });

    }

    @Override
    public void onItemClick(String item) {
        strTimeSlot = item;
        timeChange = true;
    }

    @Override
    public void onOperatorClick(String opId) {
        optId = opId;
        /*try {
            jsonArray.getJSONObject(position).put("aptser_operator_id", opId);
            operatorChange = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


    }

}
