package com.prometteur.cpapp.settings;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.NotificationListAdapter;
import com.prometteur.cpapp.beans.OrderStatus;
import com.prometteur.cpapp.beans.TimeLineModel;
import com.prometteur.cpapp.dialogs.ConfirmDialogActivity;
import com.prometteur.cpapp.utils.NetworkChangeReceiver;
import com.prometteur.cpapp.utils.Preferences;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SettingsActivity extends AppCompatActivity {

    @Bind(R.id.swNotification)
    Switch swNotification;
    @Bind(R.id.tvLogout)
    TextView tvLogout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setToolbar();

        if(Preferences.getPreferenceValue(SettingsActivity.this,"notification").equalsIgnoreCase("NA"))
        {
            swNotification.setChecked(true);
            Preferences.setPreferenceValue(SettingsActivity.this,"notification","true");
        }

        if(Preferences.getPreferenceValue(SettingsActivity.this,"notification").equalsIgnoreCase("true"))
        {
            swNotification.setChecked(true);
        }else
        {
            swNotification.setChecked(false);
        }

        swNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    Preferences.setPreferenceValue(SettingsActivity.this,"notification",""+isChecked);
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(SettingsActivity.this,ConfirmDialogActivity.class).putExtra("confirmMsg","Are you sure you want to logout from the application?"));
            }
        });

    }

    public void setToolbar()
    {
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Settings");
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
