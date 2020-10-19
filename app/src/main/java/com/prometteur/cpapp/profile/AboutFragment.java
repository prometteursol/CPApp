package com.prometteur.cpapp.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.TimeLineAdapter;
import com.prometteur.cpapp.beans.OrderStatus;
import com.prometteur.cpapp.beans.TimeLineModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.prometteur.cpapp.profile.ProfileActivity.profileData;
import static com.prometteur.cpapp.utils.Utils.getDateShowDDMMMYYYY;
import static com.prometteur.cpapp.utils.Utils.getTimeShow24to12HR;


public class AboutFragment extends Fragment{

    Context mContext;
    @Bind(R.id.tvAbout)
    TextView tvAbout;
    @Bind(R.id.tvHoursMtoF)
    TextView tvHoursMtoF;
    @Bind(R.id.tvHoursStoS)
    TextView tvHoursStoS;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.tvHoliday)
    TextView tvHoliday;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_about, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(profileData!=null) {
            tvAbout.setText(profileData.getBranDiscription());
            String strAddress="";
            if(!profileData.getBranAddr().isEmpty())
            {
                strAddress=profileData.getBranAddr();
            }if(!profileData.getBranCity().isEmpty())
            {
                strAddress=strAddress+", "+profileData.getBranCity();
            }if(!profileData.getBranState().isEmpty())
            {
                strAddress=strAddress+"\n"+profileData.getBranState()+"-"+profileData.getBranPinCode()+".";
            }
            tvAddress.setText(strAddress);
            if (!profileData.getBranWorkingHrs().isEmpty()) {
                tvHoursMtoF.setText(getTimeShow24to12HR(profileData.getBranWorkingHrs().split("-")[0])+" - "+getTimeShow24to12HR(profileData.getBranWorkingHrs().split("-")[1]));
            }

            if (!profileData.getBranOffDay().isEmpty()) {
                tvHoursStoS.setText(profileData.getBranOffDay());
            }else
            {
                tvHoursStoS.setText("-");
            }

            if (!profileData.getBranHolidayFrom().equalsIgnoreCase("0000-00-00") && !profileData.getBranHolidayTo().equalsIgnoreCase("0000-00-00")) {
                tvHoliday.setText(getDateShowDDMMMYYYY(profileData.getBranHolidayFrom()) + " To " + getDateShowDDMMMYYYY(profileData.getBranHolidayTo()));
            }else
            {
                tvHoliday.setText("-");
            }
        }
    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }









}
