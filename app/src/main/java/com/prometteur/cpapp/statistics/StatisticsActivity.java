package com.prometteur.cpapp.statistics;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.PerformanceBean;
import com.prometteur.cpapp.beans.StatisticsBean;
import com.prometteur.cpapp.retrofit.ApiInterface;
import com.prometteur.cpapp.retrofit.RetrofitInstance;
import com.prometteur.cpapp.statistics.custom.DayAxisValueFormatter;
import com.prometteur.cpapp.statistics.custom.MyValueFormatter;
import com.prometteur.cpapp.statistics.custom.XYMarkerView;
import com.prometteur.cpapp.utils.NetworkChangeReceiver;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnWeekChangeListener;

import static com.prometteur.cpapp.utils.Constants.BRANCHIDVAL;
import static com.prometteur.cpapp.utils.Utils.logout;
import static com.prometteur.cpapp.utils.Utils.showFailToast;
import static com.prometteur.cpapp.utils.Utils.showProgress;

public class StatisticsActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {


    RadioGroup rgFilter;
    //  private TextView tvX, tvY;
    WeekCalendar weekCalendar;
    //graph

    private BarChart barChart;
    @Bind(R.id.tvEarningAmount)
    TextView tvEarningAmount;
    @Bind(R.id.tvPayouts)
    TextView tvPayouts;
    @Bind(R.id.tvExpenses)
    TextView tvExpenses;
    @Bind(R.id.tvPreviousBalance)
    TextView tvPreviousBalance;

    @Bind(R.id.tvWeeks)
    TextView tvWeeks;
    @Bind(R.id.ivPrev)
    ImageView ivPrev;
    @Bind(R.id.ivNext)
    ImageView ivNext;

    @Bind(R.id.btnViewDetails)
    Button btnViewDetails;
    String startDate="",endDate="",startPreDate="",endPreDate="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);
        setToolbar();
        rgFilter = findViewById(R.id.rgFilter);
        barChart = findViewById(R.id.chartBar);
        weekCalendar = findViewById(R.id.weekCalendar);
        DateTime dateTime=new DateTime();
        SimpleDateFormat dateFormat=new SimpleDateFormat("MMMM yyyy");
        final SimpleDateFormat dateStartEnd=new SimpleDateFormat("yyyy-MM-dd");
        if(dateFormat.format(dateTime.getMillis()).equalsIgnoreCase(dateFormat.format(dateTime.plusDays(6).getMillis())))
        {
            tvWeeks.setText(""+dateFormat.format(dateTime.getMillis()));
        }else {
            tvWeeks.setText("" + dateFormat.format(dateTime.getMillis()) + "-" + dateFormat.format(dateTime.plusDays(6).getMillis()));
        }
        Log.i("first day of week init", "" + dateFormat.format(dateTime.getMillis())+ "-" + dateFormat.format(dateTime.plusDays(6).getMillis()));
        startDate="";
        endDate="";
        startPreDate="";
        endPreDate="";
        getStatistics();
        weekCalendar.setOnWeekChangeListener(new OnWeekChangeListener() {
            @Override
            public void onWeekChange(DateTime firstDayOfTheWeek, boolean forward) {
                SimpleDateFormat dateFormat=new SimpleDateFormat("MMMM yyyy");
                if(dateFormat.format(firstDayOfTheWeek.getMillis()).equalsIgnoreCase(dateFormat.format(firstDayOfTheWeek.plusDays(6).getMillis())))
                {
                    tvWeeks.setText(""+dateFormat.format(firstDayOfTheWeek.getMillis()));
                }else {
                    tvWeeks.setText("" + dateFormat.format(firstDayOfTheWeek.getMillis()) + "-" + dateFormat.format(firstDayOfTheWeek.plusDays(6).getMillis()));
                }
                Log.i("first day of week", "" + dateFormat.format(firstDayOfTheWeek.getMillis()) + "-" + dateFormat.format(firstDayOfTheWeek.plusDays(6).getMillis()));
                startDate=dateStartEnd.format(firstDayOfTheWeek.getMillis());
                endDate=dateStartEnd.format(firstDayOfTheWeek.plusDays(6).getMillis());
                startPreDate=dateStartEnd.format(firstDayOfTheWeek.minusDays(7).getMillis());
                endPreDate=dateStartEnd.format(firstDayOfTheWeek.minusDays(1).getMillis());
                getStatistics();
            }
        });


        showBarChart();
       // setBarChartData(7, 4.5f);

        btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StatisticsActivity.this,PaymentDetailsActivity.class));
            }
        });
    }

    private void showBarChart() {
        BarChart chart = barChart;
        chart.setOnChartValueSelectedListener(this);

chart.setDoubleTapToZoomEnabled(false);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        ValueFormatter custom = new MyValueFormatter("₹");

        YAxis leftAxis = chart.getAxisLeft();
        //leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setDrawGridLines(false);

        YAxis rightYAxis = barChart.getAxisRight();  //hide right y axis
        rightYAxis.setEnabled(false);
      /*  YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        //rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)*/

       /* Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);*/

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

    }

    private void setBarChartData(int count, float range) {


        ArrayList<BarEntry> values = new ArrayList<>();
        ArrayList<String> days=new ArrayList<>();
        days.add("Mon");
        days.add("Tue");
        days.add("Wed");
        days.add("Thu");
        days.add("Fri");
        days.add("Sat");
        days.add("Sun");
        BarChart chart = barChart;
        for(int j=0;j<days.size();j++) {
            boolean flag=false;
            for (int i = 0; i < performanceBean.getResult().size(); i++) {
                if(days.get(j).equalsIgnoreCase(performanceBean.getResult().get(i).getWeekday())) {
                    if (performanceBean.getResult().get(i).getAmount().isEmpty()) {
                        performanceBean.getResult().get(i).setAmount("0");
                    }
                    float val = Float.parseFloat(performanceBean.getResult().get(i).getAmount());
                    values.add(new BarEntry(j+1, val));
                    flag=true;
                }
            }
            if(!flag)
            {
                values.add(new BarEntry(j+1, 0));
            }
        }

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values, "");

            set1.setDrawIcons(false);

            // int[] color={R.color.grey};
            // set1.setColors(color);
            set1.setColor(getResources().getColor(R.color.gray));

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            // data.setValueTypeface(tfLight);
            data.setBarWidth(0.9f);

            chart.setData(data);
        }
    }



    public void setToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Statistics");
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (Build.VERSION_CODES.P == currentapiVersion) {
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

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
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


    public static StatisticsBean performanceBean;
    List<StatisticsBean.Result> performanceResult=new ArrayList<>();
    private void getStatistics() {

        final ApiInterface service = RetrofitInstance.getClient().create(ApiInterface.class);
        final Dialog progressDialog = showProgress(StatisticsActivity.this, 0);
        progressDialog.show();
        service.getStatistics(BRANCHIDVAL,startDate,endDate,startPreDate,endPreDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StatisticsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StatisticsBean loginBeanObj) {
                        progressDialog.dismiss();
                        performanceBean = loginBeanObj;
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showFailToast(StatisticsActivity.this,  getResources().getString(R.string.went_wrong));
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        progressDialog.dismiss();
                        if (performanceBean.getStatus() == 1) {
                            performanceResult=performanceBean.getResult();

                            tvEarningAmount.setText("₹ "+performanceBean.getTotalEarning()+" /-");
                            tvPayouts.setText("₹ "+performanceBean.getMooiPayouts()+" /-");
                            tvExpenses.setText("₹ "+performanceBean.getMooiPayouts()+" /-");
                            if(performanceBean.getPreviousBalance()!=null) {
                                tvPreviousBalance.setText("₹ " + performanceBean.getPreviousBalance() + " /-");
                            }else {
                                tvPreviousBalance.setText("₹ 0.00 /-");
                            }

                            setBarChartData(7, 5f);
                            setBarChartData(7, 5f);

                        } else if (performanceBean.getStatus() == 3) {
                            showFailToast(StatisticsActivity.this,  "" + performanceBean.getMsg());
                            logout(StatisticsActivity.this);
                        }else
                        {
                            tvEarningAmount.setText("₹ 0.00 /-");
                            tvPayouts.setText("₹ 0.00 /-");
                            tvExpenses.setText("₹ 0.00 /-");
                            tvPreviousBalance.setText("₹ 0.00 /-");
                            setBarChartData(7, 5f);
                        }
                        //   setEmptyMsg(mDataList, recyclerView, ivNoData);
                    }
                });
    }
}
