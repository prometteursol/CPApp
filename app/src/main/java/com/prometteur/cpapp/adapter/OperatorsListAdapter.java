package com.prometteur.cpapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.ReschduleGetDataBean;
import com.prometteur.cpapp.utils.TextViewCustomFont;

import java.util.List;

public class OperatorsListAdapter extends RecyclerView.Adapter<OperatorsListAdapter.ViewHolder> {

    Activity nActivity;
    private final OnItemClickListener listener;
    int selectedPosition = -1;
    List<ReschduleGetDataBean.Operator> operators;
    public OperatorsListAdapter(Activity nActivity, List<ReschduleGetDataBean.Operator> operators, OnItemClickListener listener) {
        this.nActivity = nActivity;
        this.operators = operators;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String OperatorName);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(nActivity).inflate(R.layout.operator_row,
                parent, false);
        return new OperatorsListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

       // holder.bind(position, listener, holder.tvOperatorName.getText().toString(), holder.conlayMainOperatorList);
        holder.tvOperatorName.setText(operators.get(position).getUserFName()+" "+operators.get(position).getUserLName());
        Glide.with(nActivity).load(operators.get(position).getUserImg()).placeholder(R.drawable.placeholder_gray_circle).error(R.drawable.placeholder_gray_circle).into(holder.civ_OperatorImage);
        holder.linOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // holder.bind(position, listener, holder.tvOperatorName.getText().toString(), holder.conlayMainOperatorList);
                if(operators.get(position).getSelected().equalsIgnoreCase("selected"))
                {
                    //operators.get(position).setSelected("");
                    selectedPosition=-1;
                }else
                {
                    for(int i=0;i<operators.size();i++){
                        operators.get(i).setSelected("");
                    }
                    selectedPosition = position;
                }
                listener.onItemClick(position,holder.tvOperatorName.getText().toString());
                notifyDataSetChanged();
            }
        });
        if (operators.get(position).getSelected().equalsIgnoreCase("selected")){
            selectedPosition = position;
        }
        if (selectedPosition == position) {
            selectedPosition = position;
            holder.civ_OperatorImage.setBorderColor(nActivity.getResources().getColor(R.color.skyBlueDark));
            holder.tvOperatorName.setTextColor(nActivity.getResources().getColor(R.color.skyBlueDark));
        } else {
            holder.civ_OperatorImage.setBorderColor(nActivity.getResources().getColor(R.color.grey));
            holder.tvOperatorName.setTextColor(nActivity.getResources().getColor(R.color.grey));

        }
        /*if(operators.get(position).getOperatorRating()!=null) {
            holder.rbOptRating.setRating(Float.parseFloat(operators.get(position).getOperatorRating()));
        }else
        {
            holder.rbOptRating.setRating(0);
        }*/


    }

    @Override
    public int getItemCount() {
        return operators.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linOperator;
        CircularImageView civ_OperatorImage;
        TextViewCustomFont tvOperatorName;
RatingBar rbOptRating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linOperator = itemView.findViewById(R.id.linOperator);
            civ_OperatorImage = itemView.findViewById(R.id.civ_OperatorImage);
            tvOperatorName = itemView.findViewById(R.id.tvOperatorName);
            rbOptRating = itemView.findViewById(R.id.rbOptRating);
        }

        /*public void bind(final int position, final OnItemClickListener listener, final String operatorName, final ConstraintLayout conlayhighlighting) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position, operatorName);

                    notifyDataSetChanged();
                }
            });
        }*/
    }

}
