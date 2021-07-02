package com.example.fininfotask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private final ArrayList<DataModel> itemsList;
    private final Context context;

    public DataAdapter(ArrayList<DataModel> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_list_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_emailId.setText(itemsList.get(holder.getAdapterPosition()).getEmailId());
        holder.tv_mobileNumber.setText(itemsList.get(holder.getAdapterPosition()).getMobileNumber());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_emailId, tv_mobileNumber;

        ViewHolder(View itemView) {
            super(itemView);
            tv_emailId = itemView.findViewById(R.id.tv_emailId);
            tv_mobileNumber = itemView.findViewById(R.id.tv_mobileNumber);
        }
    }
}
