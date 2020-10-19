package com.prometteur.cpapp.dialogs;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.onboarding.LoginActivity;

import static com.prometteur.cpapp.utils.Preferences.getClearPrefs;

public class ConfirmDialogActivity extends AppCompatActivity  {
    TextView tvMessage;
    Button btnOk,btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_confirmation);
        tvMessage=findViewById(R.id.tvMessage);
        btnOk=findViewById(R.id.btnOk);
        btnCancel=findViewById(R.id.btnCancel);

        tvMessage.setText(""+getIntent().getStringExtra("confirmMsg"));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClearPrefs(ConfirmDialogActivity.this);
                startActivity(new Intent(ConfirmDialogActivity.this, LoginActivity.class));
                finishAffinity();
            }
        });
    }


    public void closeDialog(View view) {
        finish();
    }



}
