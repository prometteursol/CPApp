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
import com.prometteur.cpapp.adapter.PromoOfferServiceListAdapter;
import com.prometteur.cpapp.beans.ComboListBean;
import com.prometteur.cpapp.beans.PromoOfferBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.prometteur.cpapp.utils.DateParser.convertDateToString;
import static com.prometteur.cpapp.utils.Utils.setEmptyMsg;

public class PromoOfferListDialogActivity extends AppCompatActivity  {
    @Bind(R.id.tvOfferName)
    TextView tvOfferName;
    @Bind(R.id.tvStartDate)
    TextView tvStartDate;
    @Bind(R.id.tvEndDate)
    TextView tvEndDate;
    @Bind(R.id.tvPercentage)
    TextView tvPercentage;
    @Bind(R.id.ivNoData)
    ImageView ivNoData;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    PromoOfferBean.Result result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_promo_offer_list);
        ButterKnife.bind(this);

        result= (PromoOfferBean.Result) getIntent().getSerializableExtra("objOffer");
        tvOfferName.setText(result.getProofferName());
        tvStartDate.setText(convertDateToString(result.getProofferStartDate()));
        tvEndDate.setText(convertDateToString(result.getProofferEndDate()));
        tvPercentage.setText(result.getProofferDiscount()+"%");
        initAdapter( result.getServices());
    }
    PromoOfferServiceListAdapter adapter;
    private void initAdapter(List dataList) {

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(PromoOfferListDialogActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new PromoOfferServiceListAdapter(PromoOfferListDialogActivity.this, dataList);
        recyclerView.setAdapter(adapter);
        setEmptyMsg(dataList, recyclerView, ivNoData);
    }

    public void closeDialog(View view) {
        finish();
    }
}
