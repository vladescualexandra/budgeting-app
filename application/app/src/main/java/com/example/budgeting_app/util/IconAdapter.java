package com.example.budgeting_app.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgeting_app.R;
import com.example.budgeting_app.model.Category;

import java.util.List;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ViewHolder> {

    private Context context;
    private int resource;
    private List<String> iconList;
    private LayoutInflater layoutInflater;

    public IconAdapter(@NonNull List<String> iconList) {
        this.iconList = iconList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_icon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (iconList.size() > 0) {
            if (position < iconList.size()) {
                String icon = iconList.get(position);
                if (icon != null) {
                    int resID = context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + icon, null, null);
                    holder.iv_category_icon.setImageResource(resID);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return iconList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_category_icon;

        ViewHolder(View view) {
            super(view);
            iv_category_icon = view.findViewById(R.id.row_item_category_icon);
        }
    }
}
