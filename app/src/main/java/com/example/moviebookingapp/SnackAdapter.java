package com.example.moviebookingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import java.util.ArrayList;

public class SnackAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Snack> snackList;
    private LayoutInflater inflater;

    public SnackAdapter(Context context, ArrayList<Snack> snackList) {
        this.context = context;
        this.snackList = snackList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return snackList.size();
    }

    @Override
    public Object getItem(int position) {
        return snackList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_snack, parent, false);
            holder = new ViewHolder();
            holder.imgSnack = convertView.findViewById(R.id.imgSnack);
            holder.tvSnackName = convertView.findViewById(R.id.tvSnackName);
            holder.tvSnackDescription = convertView.findViewById(R.id.tvSnackDescription);
            holder.tvSnackPrice = convertView.findViewById(R.id.tvSnackPrice);
            holder.tvQuantity = convertView.findViewById(R.id.tvQuantity);
            holder.btnPlus = convertView.findViewById(R.id.btnPlus);
            holder.btnMinus = convertView.findViewById(R.id.btnMinus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Snack snack = snackList.get(position);

        holder.imgSnack.setImageResource(resolveDrawable(snack.getImageResourceId()));
        holder.tvSnackName.setText(snack.getName());
        holder.tvSnackDescription.setText(snack.getDescription());
        holder.tvSnackPrice.setText(String.format("$%.2f", snack.getPrice()));
        holder.tvQuantity.setText(String.valueOf(snack.getQuantity()));

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snack.incrementQuantity();
                notifyDataSetChanged();
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snack.decrementQuantity();
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @DrawableRes
    private int resolveDrawable(int resId) {
        if (resId == 0) return android.R.drawable.ic_menu_gallery;
        try {
            String type = context.getResources().getResourceTypeName(resId);
            if ("drawable".equals(type) || "mipmap".equals(type)) return resId;
        } catch (Exception ignored) {
            // fall through to default
        }
        return android.R.drawable.ic_menu_gallery;
    }

    static class ViewHolder {
        ImageView imgSnack;
        TextView tvSnackName;
        TextView tvSnackDescription;
        TextView tvSnackPrice;
        TextView tvQuantity;
        Button btnPlus;
        Button btnMinus;
    }
}