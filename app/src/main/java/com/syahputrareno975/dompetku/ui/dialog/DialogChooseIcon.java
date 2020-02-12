package com.syahputrareno975.dompetku.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syahputrareno975.dompetku.R;
import com.syahputrareno975.dompetku.model.icon.IconModel;
import com.syahputrareno975.dompetku.ui.adapter.iconAdapter.IconAdapter;

import java.util.ArrayList;

public class DialogChooseIcon {
    private Context context;
    private ArrayList<IconModel> iconModels;
    private OnDialogListener listener;
    private String title;


    public DialogChooseIcon(Context context, String title, ArrayList<IconModel> iconModels, OnDialogListener listener) {
        this.context = context;
        this.iconModels = iconModels;
        this.listener = listener;
        this.title = title;

    }

    public void show(){
        View v = ((Activity)context).getLayoutInflater().inflate(R.layout.choose_icon_dialog,null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();

        RecyclerView listIcon = v.findViewById(R.id.list_icon_recycleview);
        listIcon.setAdapter(new IconAdapter(context, iconModels, new IconAdapter.OnIconAdapterListener() {
            @Override
            public void onClick(@NonNull IconModel i) {
                listener.onOk(i);
                dialog.dismiss();
            }
        }));
        listIcon.setLayoutManager(new GridLayoutManager(context,3));

        TextView title_textview = v.findViewById(R.id.title_textview);
        title_textview.setText(title);


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
        void onOk(@NonNull IconModel i);
        void onCancel();
    }
}
