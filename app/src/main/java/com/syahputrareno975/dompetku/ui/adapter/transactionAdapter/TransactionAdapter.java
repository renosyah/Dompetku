package com.syahputrareno975.dompetku.ui.adapter.transactionAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.syahputrareno975.dompetku.R;
import com.syahputrareno975.dompetku.model.transaction.TransactionModel;

import java.util.ArrayList;

import static com.syahputrareno975.dompetku.util.Flow.FLOW_EXPENSE;
import static com.syahputrareno975.dompetku.util.Flow.FLOW_INCOME;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.Holder> {

    private Context context;
    private ArrayList<TransactionModel> list;

    public TransactionAdapter(Context context, ArrayList<TransactionModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(((Activity) context).getLayoutInflater().inflate(R.layout.adapter_transaction,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        TransactionModel item = list.get(position);
        String currency = "Rp";

        holder.Date.setText(item.getDate().toString());

        switch (item.getFlow()){
            case FLOW_INCOME:
                holder.Amount.setTextColor(ContextCompat.getColor(context,R.color.textColorGreen));
                holder.Amount.setText("+ " + currency + " " + item.getAmount());
                break;
            case FLOW_EXPENSE:
                holder.Amount.setTextColor(ContextCompat.getColor(context,R.color.textColorRed));
                holder.Amount.setText("- " + currency + " " + item.getAmount());
                break;
                default:
                    break;
        }

        holder.Description.setText(item.getDescription());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private TextView Date;
        private TextView Description;
        private TextView Amount;

        public Holder(@NonNull View itemView) {
            super(itemView);
            Date = itemView.findViewById(R.id.transaction_date_textview);
            Description = itemView.findViewById(R.id.transaction_des_textview);
            Amount = itemView.findViewById(R.id.transaction_amount_textview);
        }
    }
}
