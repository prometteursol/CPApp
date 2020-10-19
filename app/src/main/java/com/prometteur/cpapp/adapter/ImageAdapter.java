package com.prometteur.cpapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.AdvertisementBean;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends PagerAdapter {
Context context;
    List<AdvertisementBean.Result> urls;


LayoutInflater mLayoutInflater;

public ImageAdapter(Context context, List<AdvertisementBean.Result> urls){
    this.context=context;
    this.urls=urls;
    mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
}
@Override
public int getCount() {
  return urls.size();
}

@Override
public boolean isViewFromObject(View view, Object object) {
  return view == ((LinearLayout) object);
}

@Override
public Object instantiateItem(ViewGroup container, int position) {
    View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

    RoundedImageView imageView =  itemView.findViewById(R.id.imageView);
   /* imageView.setImageResource();*/
    Glide.with(context).load(urls.get(position).getAdvImg()).into(imageView);

    container.addView(itemView);
    imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });
    return itemView;
}

@Override
public void destroyItem(ViewGroup container, int position, Object object) {
  container.removeView((LinearLayout)object);
}
}