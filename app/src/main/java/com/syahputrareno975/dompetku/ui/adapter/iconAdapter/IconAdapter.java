package com.syahputrareno975.dompetku.ui.adapter.iconAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.syahputrareno975.dompetku.R;
import com.syahputrareno975.dompetku.model.icon.IconModel;
import java.util.ArrayList;

import static com.syahputrareno975.dompetku.util.IconFlag.FLAG_FROM_RES;
import static com.syahputrareno975.dompetku.util.IconFlag.FLAG_FROM_URL;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.Holder> {

    private Context context;
    private ArrayList<IconModel> list;
    private OnIconAdapterListener listener;


    public IconAdapter(Context context, ArrayList<IconModel> list, OnIconAdapterListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(((Activity)context).getLayoutInflater().inflate(R.layout.adapter_icon,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final IconModel item = list.get(position);
        switch (item.Flag){
            case FLAG_FROM_URL:
                Picasso.get()
                        .load(item.imageUrl)
                        .into(holder.image);
                break;
            case FLAG_FROM_RES:
                holder.image.setImageDrawable(
                        ContextCompat.getDrawable(context, item.resId)
                );
                break;
            default:
                break;
        }

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        public ImageView image;

        public Holder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.icon_imageView);
        }
    }

    public static interface OnIconAdapterListener {
        void onClick(@NonNull IconModel i);
    }
}
