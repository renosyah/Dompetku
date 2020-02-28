package com.wawan.dompetku.ui.adapter.listReportAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.wawan.dompetku.BuildConfig;
import com.wawan.dompetku.R;
import com.wawan.dompetku.model.transaction.TransactionModel;

import java.util.ArrayList;
import static com.wawan.dompetku.util.Flow.FLOW_INCOME;
import static com.wawan.dompetku.util.UtilFunction.formatter;


// ini adalah class adapter untuk recycleview
// dalam kasus ini adapter ini akan digunakan
// untuk menampilkan item item transaksi
// yg nantinya akan dipilih oleh user
public class ListReportAdapter extends RecyclerView.Adapter<ListReportAdapter.Holder> {

    // deklarasi variabel
    private Context context;
    private ArrayList<TransactionModel> list;
    private OnListReportAdapterListener listener;


    // konstruktor class
    public ListReportAdapter(Context context, ArrayList<TransactionModel> list, OnListReportAdapterListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }


    // fungsi yg akan dilankan saat
    // menampilkan view
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(((Activity)context).getLayoutInflater().inflate(R.layout.list_report_adapter,parent,false));
    }

    // fungsi yg akan dipanggil saat view berhasil di bind
    // ke adapter
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

    // fungsi yg akan digunakan untuk
    // mengetahui panjang adapter
    // dan jug adigunakan untuk menentukan panjang kontent
    // dari view recycleview
    @Override
    public int getItemCount() {
        return list.size();
    }

    // view holder
    // adalah class untuk mendeklarasi
    // dan inisialisasi view
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

    // interface yg akan diimplementasikan
    // dan digunakan untuk callback
    public interface OnListReportAdapterListener {
        void onClick(@NonNull TransactionModel t,int pos);
    }
}
