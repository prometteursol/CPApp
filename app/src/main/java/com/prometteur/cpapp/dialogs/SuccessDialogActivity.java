package com.prometteur.cpapp.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.prometteur.cpapp.R;

import static com.prometteur.cpapp.onboarding.ChangePasswordActivity.resultCodeChangePass;

public class SuccessDialogActivity extends AppCompatActivity  {
TextView tvMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_success);
        this.setFinishOnTouchOutside(false);
        tvMessage=findViewById(R.id.tvMessage);
        tvMessage.setText(""+getIntent().getStringExtra("msg"));
    }


    public void closeDialog(View view) {
        setResult(resultCodeChangePass);
        finish();
    }
}
