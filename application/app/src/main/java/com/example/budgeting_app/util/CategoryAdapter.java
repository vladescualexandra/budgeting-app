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

import com.example.budgeting_app.R;
import com.example.budgeting_app.model.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {


    private Context context;
    private int resource;
    private List<Category> categoryList;
    private LayoutInflater layoutInflater;

    public CategoryAdapter(@NonNull Context context,
                           int resource,
                           @NonNull List<Category> categoryList,
                           LayoutInflater layoutInflater) {
        super(context, resource, categoryList);
        this.context = context;
        this.resource = resource;
        this.categoryList = categoryList;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View view = layoutInflater.inflate(resource, parent, false);

        if (categoryList.size() > 0) {
            if (position < categoryList.size()) {
                Category category = categoryList.get(position);
                if (category != null) {
                    buildCategory(view, category);
                }
            }
        }
        return view;
    }

    private void buildCategory(View view, Category category) {

        ImageView iv_category_icon = view.findViewById(R.id.row_item_category_icon);
        TextView tv_category_name = view.findViewById(R.id.row_item_category_name);

        int resID = context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + category.getIcon(), null, null);
        iv_category_icon.setImageResource(resID);

        tv_category_name.setText(category.getName());
    }
}
