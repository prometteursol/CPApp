package com.prometteur.cpapp.statistics;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prometteur.cpapp.R;

import static com.prometteur.cpapp.statistics.PerformaceActivity.performanceBean;


public class SummaryFragment extends Fragment{

    Context mContext;
    TextView tvNetAmount,tvPerService,tvPerAppoint;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        return inflater.inflate(R.layout.fragment_summary, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNetAmount=view.findViewById(R.id.tvNetAmount);
        tvPerService=view.findViewById(R.id.tvPerService);
        tvPerAppoint=view.findViewById(R.id.tvPerAppoint);

        tvNetAmount.setText(""+performanceBean.getNetAmount()+" /- ");
                tvPerService.setText(""+performanceBean.getAvgServiceCost()+" /- ");
       // tvPerAppoint.setText(""+performanceBean.geta);
    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }









}
