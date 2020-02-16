package com.syahputrareno975.dompetku.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.syahputrareno975.dompetku.R;

import static com.syahputrareno975.dompetku.util.UtilFunction.formatter;

public class DialogSimpleEditText {
    private Context context;
    private OnDialogListener listener;
    private String title;
    private String text;
    private Double number = 0.0;
    private boolean typeNumber = false;

    public DialogSimpleEditText(Context context, String title,String text,OnDialogListener listener) {
        this.context = context;
        this.title = title;
        this.listener = listener;
        this.text = text;
    }

    public DialogSimpleEditText(Context context,String title, String text, OnDialogListener listener,  boolean typeNumber) {
        this.context = context;
        this.listener = listener;
        this.title = title;
        this.text = text;
        this.typeNumber = typeNumber;
    }

    public void show(){
        View v = ((Activity)context).getLayoutInflater().inflate(R.layout.edit_text_dialog,null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();

        final EditText text_editText = v.findViewById(R.id.text_editText);
        text_editText.setText(text);
        if (typeNumber){
            text_editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            text_editText.setText("0");
            text_editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable ignore) {
                    text_editText.removeTextChangedListener(this);
                    Log.e("num",text_editText.getText().toString().replaceAll("[-+.^:,]",""));
                    if (!text_editText.getText().toString().isEmpty()){
                        String s = text_editText.getText().toString().replaceAll("[-+.^:,]","");
                        number = Double.parseDouble(s);
                    }
                    Log.e("num int",number.toString());
                    text_editText.setText(text_editText.getText().toString().isEmpty() ? "" : formatter.format(number));
                    text_editText.setSelection(text_editText.getText().toString().length());
                    text_editText.addTextChangedListener(this);
                }
            });
        }

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
                String val = text_editText.getText().toString();
                if (typeNumber){
                    val = val.replaceAll("[-+.^:,]","");
                }
                listener.onOk(val);
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
        void onOk(String text);
        void onEmpty();
        void onCancel();
    }
}
