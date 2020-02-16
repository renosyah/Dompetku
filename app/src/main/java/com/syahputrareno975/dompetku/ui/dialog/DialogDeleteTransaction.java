package com.syahputrareno975.dompetku.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.syahputrareno975.dompetku.R;

public class DialogDeleteTransaction {
    private Context context;
    private String text;
    private OnDialogDeleteTransactionListener listener;

    public DialogDeleteTransaction(Context context,String text, OnDialogDeleteTransactionListener listener) {
        this.context = context;
        this.listener = listener;
        this.text = text;
    }

    public void show(){
        View v = ((Activity)context).getLayoutInflater().inflate(R.layout.delete_dialog,null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .create();

        TextView des = v.findViewById(R.id.des_textview);
        des.setText(text);

        TextView delete = v.findViewById(R.id.ok_delete_textview);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDelete();
                dialog.dismiss();
            }
        });

        TextView cancel = v.findViewById(R.id.cancel_delete_textview);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel();
                dialog.dismiss();
            }
        });

        dialog.setView(v);
        dialog.setCancelable(false);
        dialog.show();
    }

    public interface OnDialogDeleteTransactionListener {
        void onDelete();
        void onCancel();
    }
}
