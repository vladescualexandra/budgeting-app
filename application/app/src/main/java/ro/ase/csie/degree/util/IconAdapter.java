package ro.ase.csie.degree.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ro.ase.csie.degree.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import android.graphics.Color;;

public class IconAdapter extends ArrayAdapter<Bitmap> { // TO DO <<<<

    private Context context;
    private int resource;
    private List<Bitmap> iconList = new ArrayList<Bitmap>();
    private LayoutInflater layoutInflater;

    private int selectedIndex = 0;

    public IconAdapter(@NonNull Context context,
                       int resource,
                       @NonNull List<Bitmap> iconList,
                       LayoutInflater layoutInflater) {
        super(context, resource, iconList);
        this.context = context;
        this.resource = resource;
        this.iconList = iconList;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View view = layoutInflater.inflate(resource, parent, false);

        if (iconList.size() > 0) {
            if (position < iconList.size()) {
                Bitmap icon = iconList.get(position);
                if (icon != null) {
                    buildIcon(view, icon);
                }

                if (position == selectedIndex) {
                    view.setBackgroundColor(Color.argb(20, 238,238,238));
                }
            }
        }
        return view;
    }

    private void buildIcon(View view, Bitmap icon) {
        ImageView iv_category_icon = view.findViewById(R.id.row_item_icon);
        iv_category_icon.setImageBitmap(icon);
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }
}
