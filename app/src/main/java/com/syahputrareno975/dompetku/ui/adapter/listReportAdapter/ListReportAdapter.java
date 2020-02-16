package com.syahputrareno975.dompetku.ui.adapter.listReportAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.syahputrareno975.dompetku.BuildConfig;
import com.syahputrareno975.dompetku.R;
import com.syahputrareno975.dompetku.model.transaction.TransactionModel;

import java.util.ArrayList;
import static com.syahputrareno975.dompetku.util.Flow.FLOW_INCOME;
import static com.syahputrareno975.dompetku.util.UtilFunction.formatter;

public class ListReportAdapter extends RecyclerView.Adapter<ListReportAdapter.Holder> {

    private Context context;
    private ArrayList<TransactionModel> list;
    private OnListReportAdapterListener listener;

    public ListReportAdapter(Context context, ArrayList<TransactionModel> list, OnListReportAdapterListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(((Activity)context).getLayoutInflater().inflate(R.layout.list_report_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        TransactionModel item = list.get(position);
        holder.header.setVisibility(item.isHeader ? View.VISIBLE : View.GONE);
        holder.body.setVisibility(item.isHeader ? View.GONE : View.VISIBLE);

        // list only contain header
        // or empty
        holder.empty.setVisibility(list.size() == 1 || list.isEmpty() ? View.VISIBLE : View.GONE);

        holder.date.setText(item.getDate() == null ? "" : item.getDate().toString());
        holder.des.setText(item.getDescription());
        holder.amount.setText((item.getFlow() == FLOW_INCOME ?"+ " : "- ") + BuildConfig.CURRENCY + " " + formatter.format(item.getAmount() == null ? 0 : item.getAmount() ));
        holder.body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(item,position);
            }
        });
        holder.amount.setTextColor(item.getFlow() == FLOW_INCOME ? ContextCompat.getColor(context,R.color.textColorGreen) : ContextCompat.getColor(context,R.color.textColorRed) );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public LinearLayout header;
        public LinearLayout body;
        public LinearLayout empty;
        public TextView date;
        public TextView des;
        public TextView amount;

        public Holder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header_linearlayout);
            body = itemView.findViewById(R.id.body_linearlayout);
            empty = itemView.findViewById(R.id.empty_linearlayout);
            date = itemView.findViewById(R.id.date_report_textview);
            des = itemView.findViewById(R.id.des_report_textview);
            amount = itemView.findViewById(R.id.amount_report_textview);
        }
    }

    public interface OnListReportAdapterListener {
        void onClick(@NonNull TransactionModel t,int pos);
    }
}
