package com.prometteur.cpapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.ReschduleGetDataBean;
import com.prometteur.cpapp.utils.TextViewCustomFont;

import java.text.DecimalFormat;
import java.util.List;

import static com.prometteur.cpapp.utils.Utils.getStrikeString;


public class ComboListAppDetailsAdapter extends RecyclerView.Adapter<ComboListAppDetailsAdapter.ViewHolder> {

    AppCompatActivity nActivity;

    boolean bottomlist;
    List<ReschduleGetDataBean.Combo> results;

    public ComboListAppDetailsAdapter(AppCompatActivity nActivity, List<ReschduleGetDataBean.Combo> results, boolean bottomlist) {
        this.nActivity = nActivity;
        this.results=results;
        this.bottomlist=bottomlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(nActivity).inflate(R.layout.recycle_details_combo_list,
                parent, false);

        return new ComboListAppDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            holder.tvComboName.setText(results.get(position).getOfferName());
            if(results.get(position).getOfferDiscountPrice()!=null || !results.get(position).getOfferDiscountPrice().isEmpty()) {
                holder.tvComboOfferPrice.setText("₹ " + new DecimalFormat("##.##").format(Double.parseDouble(results.get(position).getOfferDiscountPrice())));
            }
            if(results.get(position).getOfferPrice()!=null || !results.get(position).getOfferPrice().isEmpty()) {
                holder.tvComboprice.setText("₹ " + new DecimalFormat("##.##").format(Double.parseDouble(results.get(position).getOfferPrice())));
            }
            holder.tvComboOfferPrice.setVisibility(View.GONE);
            holder.tvComboprice.setVisibility(View.GONE);
            holder.recycleComboServicesOffers.setLayoutManager(new LinearLayoutManager(nActivity));
            holder.recycleComboServicesOffers.setAdapter(new ComboAppDetailsServicesAdapter(nActivity, results.get(position).getComboServices(), false));


/*holder.ivRemoveCombo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getRemoveCart(position,results.get(position).getCartId());   //remove from cart
    }
});*/

            getStrikeString(holder.tvComboprice);

      //  getResizedDrawable(nActivity,R.drawable.ic_clock_blue,holder.tvTime,null,null,R.dimen._12sdp);


    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextViewCustomFont tvComboName;
        TextViewCustomFont tvComboOfferPrice;
        TextViewCustomFont tvComboprice;


        RecyclerView recycleComboServicesOffers;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvComboName = itemView.findViewById(R.id.tvComboName);
            tvComboOfferPrice = itemView.findViewById(R.id.tvComboOfferPrice);

            tvComboprice = itemView.findViewById(R.id.tvComboprice);
            recycleComboServicesOffers = itemView.findViewById(R.id.recycleComboServicesOffers);
            recycleComboServicesOffers = itemView.findViewById(R.id.recycleComboServicesOffers);
        }
    }


}
