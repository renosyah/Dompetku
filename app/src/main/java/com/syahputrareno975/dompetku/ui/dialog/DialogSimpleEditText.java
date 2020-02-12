package com.syahputrareno975.dompetku.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.syahputrareno975.dompetku.R;

public class DialogSimpleEditText {
    private Context context;
    private OnDialogListener listener;
    private String title;
    private String text;

    public DialogSimpleEditText(Context context, String title,String text,OnDialogListener listener) {
        this.context = context;
        this.title = title;
        this.listener = listener;
        this.text = text;
    }

    public void show(){
        View v = ((Activity)context).getLayoutInflater().inflate(R.layout.edit_text_dialog,null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();

        final EditText text_editText = v.findViewById(R.id.text_editText);
        text_editText.setText(text);

        TextView title_textview = v.findViewById(R.id.title_textview);
        title_textview.setText(title);

        TextView ok_textview = v.findViewById(R.id.ok_textview);
        ok_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text_editText.getText().toString().trim().isEmpty()){
                    listener.onEmpty();
                    return;
                }
                listener.onOk(text_editText.getText().toString());
                dialog.dismiss();
            }
        });

        TextView cancel_textview = v.findViewById(R.id.cancel_textview);
        cancel_textview.setOnClickListener(new View.OnClickListener() {
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

    public interface OnDialogListener {
        void onOk(@NonNull String text);
        void onEmpty();
        void onCancel();
    }
}
