package com.prometteur.cpapp.adapter;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.prometteur.cpapp.drawer.DashboardMainActivity;
import com.prometteur.cpapp.listener.OnTabRemoveListener;
import com.prometteur.cpapp.utils.Preferences;

import java.util.ArrayList;

import static com.prometteur.cpapp.drawer.DashboardMainActivity.bottomNavigationView;
import static com.prometteur.cpapp.utils.Constants.APTCOUNT;
import static com.prometteur.cpapp.utils.Utils.getBottomNavigationCount;

public class AppointmentTabAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private ArrayList<String> tabStringArrayList = new ArrayList<>();
    OnTabRemoveListener onTabRemoveListener;
    public AppointmentTabAdapter(FragmentManager fragmentManager,OnTabRemoveListener onTabRemoveListener)
    {
        super(fragmentManager);
        this.onTabRemoveListener=onTabRemoveListener;
    }

    @Override
    public Fragment getItem(int position) {
        //return null;
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        //return 0;
        return fragmentArrayList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //return super.getPageTitle(position);
        return tabStringArrayList.get(position);
    }

    public void addCompetetorTabFragments(Fragment tabFragment, String tabTitle)
    {
        fragmentArrayList.add(tabFragment);
        tabStringArrayList.add(tabTitle);
    }
    public void removeCompetetorTabFragments(Fragment tabFragment)
    {
        fragmentArrayList.remove(tabFragment);
        try {
            int count = Preferences.getPreferenceValueInt(tabFragment.getContext(), APTCOUNT);
            if (count > 0) {
                Preferences.setPreferenceValue(tabFragment.getContext(), APTCOUNT, (count - 1));
            }
            Intent intent1 = new Intent("SendToService");
            tabFragment.getContext().sendBroadcast(intent1);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        //getBottomNavigationCount(tabFragment.getContext(), bottomNavigationView);
        notifyDataSetChanged();
        onTabRemoveListener.onTabRemoved(fragmentArrayList.size());
        //tabStringArrayList.remove(tabTitle);
    }


    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }
}
