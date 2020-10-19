package com.prometteur.cpapp.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.ReschduleGetDataBean;
import com.prometteur.cpapp.utils.TextViewCustomFont;

import java.util.List;

public class ServiceSelectedAdapter extends RecyclerView.Adapter<ServiceSelectedAdapter.ViewHolder> {

    Activity nActivity;
    List<ReschduleGetDataBean.Service> services;
    public ServiceSelectedAdapter(Activity nActivity, List<ReschduleGetDataBean.Service> services) {
        this.nActivity = nActivity;
        this.services = services;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(nActivity).inflate(R.layout.recycle_services_selected,
                parent, false);
        return new ServiceSelectedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvSerialNo.setText("0"+(position+1)+".");
        holder.tvService.setText(services.get(position).getSrvcName());
        holder.tvServiceCost.setText("₹ " +services.get(position).getSrvcPrice());
        holder.tvServiceCost.setVisibility(View.GONE);
        if(services.get(position).getBrndName()!=null) {
            holder.tvServiceBrand.setText(services.get(position).getBrndName());
        }else
        {
            holder.tvServiceBrand.setVisibility(View.GONE);
            holder.tvServiceBrandtitle.setVisibility(View.GONE);
        }

            //holder.tvServiceOperator.setVisibility(View.GONE);
            holder.tvServiceOperatorTitle.setVisibility(View.GONE);
boolean isOperator=false;
        for(int i=0;i<services.get(position).getOperators().size();i++){
            if(services.get(position).getOperators().get(i).getSelected().equalsIgnoreCase("selected"))
            {
                if(services.get(position).getOperators().get(i).getUserFName()!=null && services.get(position).getOperators().get(i).getUserLName()!=null) {
                    isOperator=true;
                    holder.tvServiceOperator.setTextColor(nActivity.getResources().getColor(R.color.black));
                    operatorName=services.get(position).getOperators().get(i).getUserFName() + " " + services.get(position).getOperators().get(i).getUserLName();
                    holder.tvServiceOperator.setText(services.get(position).getOperators().get(i).getUserFName() + " " + services.get(position).getOperators().get(i).getUserLName());
                    holder.tvServiceOperator.setVisibility(View.VISIBLE);
                    holder.tvServiceOperatorTitle.setVisibility(View.VISIBLE);
                }
            }
        }

if(!isOperator)
{
    holder.tvServiceOperator.setText("+ Select Operator");
}

        holder.tvServiceOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOperatorSelectBottomDialog(nActivity,services.get(position).getOperators(),holder.tvServiceOperator,holder.tvServiceOperatorTitle,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextViewCustomFont tvSerialNo;
        TextViewCustomFont tvService;
        TextViewCustomFont tvServiceCost;
        TextViewCustomFont tvServiceBrandtitle;
        TextViewCustomFont tvServiceOperatorTitle;
        TextViewCustomFont tvServiceBrand;
        TextViewCustomFont tvServiceOperator;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tvSerialNo = itemView.findViewById(R.id.tvSerialNo);
            tvService = itemView.findViewById(R.id.tvService);
            tvServiceCost = itemView.findViewById(R.id.tvServiceCost);
            tvServiceBrand = itemView.findViewById(R.id.tvServiceBrand);
            tvServiceBrandtitle = itemView.findViewById(R.id.tvServiceBrandtitle);
            tvServiceOperator = itemView.findViewById(R.id.tvServiceOperator);
            tvServiceOperatorTitle = itemView.findViewById(R.id.tvServiceOperatorTitle);

        }
    }

    RecyclerView recycleOperatorsList;
    TextViewCustomFont tvOperatortitle;
    ImageView ivCloseCrossimg;
    Button btnDone;
    String operatorName="";
    public void showOperatorSelectBottomDialog(final Activity nActivity, final List<ReschduleGetDataBean.Operator> operators, final TextViewCustomFont tvOperatorName, final TextViewCustomFont tvServiceOperatorTitle, final int pos) {
        final Dialog operatorSelect = new Dialog(nActivity, R.style.CustomAlertDialog);
        operatorSelect.setContentView(R.layout.dialog_select_operator);
        Window window = operatorSelect.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        operatorSelect.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        operatorSelect.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        recycleOperatorsList = operatorSelect.findViewById(R.id.recycleOperatorsList);
        tvOperatortitle = operatorSelect.findViewById(R.id.tvOperatortitle);
        ivCloseCrossimg = operatorSelect.findViewById(R.id.ivCloseCrossimg);
        btnDone = operatorSelect.findViewById(R.id.btnDone);

        LinearLayoutManager layoutManager = new LinearLayoutManager(nActivity);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recycleOperatorsList.setLayoutManager(layoutManager);

        recycleOperatorsList.setAdapter(new OperatorsListAdapter(nActivity, operators, new OperatorsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String OperatorName) {

                if(operators.get(position).getSelected().equalsIgnoreCase("selected"))
                {
                    operators.get(position).setSelected("");
                    operatorName=null;
                }else {
                    operatorName = OperatorName;
                    operators.get(position).setSelected("selected");
                }
                //constraintLayout.setBackground(nActivity.getResources().getDrawable(R.drawable.bg_white_blue_stroke_curved));
            }
        }));
        ivCloseCrossimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (operatorName != null) {
                    if(!operatorName.isEmpty()) {
                        tvOperatorName.setText(*//*"Op : " +*//* operatorName);
                        tvServiceOperatorTitle.setVisibility(View.VISIBLE);
                        tvOperatorName.setTextColor(nActivity.getResources().getColor(R.color.black));
                    }else
                    {*/
                       /* tvOperatorName.setText("+ Select Operator");
                        tvServiceOperatorTitle.setVisibility(View.GONE);
                        tvOperatorName.setTextColor(nActivity.getResources().getColor(R.color.skyBlueDark));*/
                   /* }
                }*/
                operatorSelect.dismiss();
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (operatorName != null) {
                    tvOperatorName.setText(/*"Op : " + */operatorName);
                    tvServiceOperatorTitle.setVisibility(View.VISIBLE);
                    tvOperatorName.setTextColor(nActivity.getResources().getColor(R.color.black));
                    //notifyDataSetChanged();

                } else {
                    tvOperatorName.setText("+ Select Operator");
                    tvServiceOperatorTitle.setVisibility(View.GONE);
                    tvOperatorName.setTextColor(nActivity.getResources().getColor(R.color.skyBlueDark));
                    //Toast.makeText(nActivity, "Please select operator", Toast.LENGTH_SHORT).show();
                }
                operatorSelect.dismiss();
            }
        });
        operatorSelect.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
               /* if (operatorName != null) {
                    if(!operatorName.isEmpty()) {
                        tvOperatorName.setText(*//*"Op : " +*//* operatorName);
                        tvServiceOperatorTitle.setVisibility(View.VISIBLE);
                        tvOperatorName.setTextColor(nActivity.getResources().getColor(R.color.black));
                    }else
                    {*/
                        /*tvOperatorName.setText("+ Select Operator");
                        tvServiceOperatorTitle.setVisibility(View.GONE);
                        tvOperatorName.setTextColor(nActivity.getResources().getColor(R.color.skyBlueDark));*/
                    /*}
                }*/
                // notifyDataSetChanged();
                operatorSelect.dismiss();
            }

        });
        operatorSelect.show();
    }
}
