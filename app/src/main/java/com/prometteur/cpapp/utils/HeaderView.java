package com.prometteur.cpapp.utils;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.prometteur.cpapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HeaderView extends LinearLayout{

    @Bind(R.id.header_view_title)
    TextView title;

    @Bind(R.id.header_view_sub_title)
    TextView subTitle;

    @Bind(R.id.ratingBar)
    RatingBar ratingBar;
    @Bind(R.id.tvHeaderViewStatus)
    TextView tvHeaderViewStatus;
    @Bind(R.id.tvReviewCount)
    TextView tvReviewCount;

    public HeaderView(Context context) {
        super(context);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void bindTo(String title) {
        bindTo(title, "",0,"0","OPEN");
    }

    public void bindTo(String title, String subTitle,float ratingBarVal,String reviewCount,String status) {
        hideOrSetText(this.title, title);
        hideOrSetText(this.subTitle, subTitle);
        if(!status.equalsIgnoreCase("OPEN"))
        {
            this.tvHeaderViewStatus.setBackground(getResources().getDrawable(R.drawable.status_rounded_red_background));
        }
        hideOrSetText(this.tvHeaderViewStatus, status);
        hideOrSetText(this.tvReviewCount, "("+reviewCount+" Reviews)");
        if(ratingBarVal==0.0)
        {
            ratingBar.setVisibility(GONE);
        }else {
            ratingBar.setVisibility(VISIBLE);
            ratingBar.setRating(ratingBarVal);
        }
    }

    private void hideOrSetText(TextView tv, String text) {
        if (text == null || text.equals(""))
            tv.setVisibility(GONE);
        else
            tv.setVisibility(VISIBLE);
            tv.setText(text);
    }

public void hideShowRating(boolean val)
{
    if(val) {
        ratingBar.setVisibility(GONE);
    }else
    {
        ratingBar.setVisibility(VISIBLE);
    }
}


}
