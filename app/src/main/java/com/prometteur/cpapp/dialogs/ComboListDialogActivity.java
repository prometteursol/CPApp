package com.prometteur.cpapp.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.adapter.ComboServiceListAdapter;
import com.prometteur.cpapp.adapter.RateCardAdapter;
import com.prometteur.cpapp.beans.ComboListBean;

import java.util.List;

import in.aabhasjindal.otptextview.OtpTextView;

import static com.prometteur.cpapp.utils.Utils.getResizedDrawable;
import static com.prometteur.cpapp.utils.Utils.getStrikeString;
import static com.prometteur.cpapp.utils.Utils.setEmptyMsg;

public class ComboListDialogActivity extends AppCompatActivity  {
    ComboListBean.Result result;
    TextView tvComboName,tvDiscountAmount,tvAmount;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_combo_list);
        tvComboName=findViewById(R.id.tvComboName);
        recyclerView=findViewById(R.id.recyclerView);
        tvDiscountAmount=findViewById(R.id.tvDiscountAmount);
        tvAmount=findViewById(R.id.tvAmount);
        ivNoData=findViewById(R.id.ivNoData);
        result= (ComboListBean.Result) getIntent().getSerializableExtra("objCombo");
        tvComboName.setText(result.getOfferName());
        tvDiscountAmount.setText("₹ " +result.getOfferDiscountPrice());
        if(!result.getOfferPrice().isEmpty()) {
            tvAmount.setText("₹ " +result.getOfferPrice());
        }else
        {
            tvAmount.setVisibility(View.GONE);
        }
        getStrikeString(tvAmount);
        initAdapter(result.getServices());
//        getResizedDrawable(mContext, R.drawable.ic_time, holder.tvTime, null, null, R.dimen._11sdp);
    }
    ComboServiceListAdapter adapter;
    ImageView ivNoData;
    private void initAdapter(List dataList) {

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ComboListDialogActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new ComboServiceListAdapter(ComboListDialogActivity.this, dataList);
        recyclerView.setAdapter(adapter);
        setEmptyMsg(dataList, recyclerView, ivNoData);
    }
    public void closeDialog(View view) {
        finish();
    }
}
