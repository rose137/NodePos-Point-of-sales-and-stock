package com.example.nodepos.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nodepos.R;
import com.example.nodepos.model.riwayatModel;
import android.widget.TextView;

import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.ViewHolder> {

    private Context context;
    private List<riwayatModel> transactionList;

    public RiwayatAdapter(Context context, List<riwayatModel> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        riwayatModel transaction = transactionList.get(position);
        holder.tvInvoice.setText(transaction.getInvoice());
        holder.tvStatus.setText(transaction.getStatus());
        holder.tvDate.setText(transaction.getDate());
        holder.tvTotal.setText(transaction.getTotal());

        // Warna status berdasarkan kondisi
        if (transaction.getStatus().equalsIgnoreCase("Selesai")) {
            holder.tvStatus.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        } else if (transaction.getStatus().equalsIgnoreCase("Pending")) {
            holder.tvStatus.setTextColor(context.getResources().getColor(android.R.color.holo_orange_dark));
        } else {
            holder.tvStatus.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvInvoice, tvStatus, tvDate, tvTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInvoice = itemView.findViewById(R.id.tvInvoice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }
}