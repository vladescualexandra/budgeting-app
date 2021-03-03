package ro.ase.csie.degree.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ro.ase.csie.degree.R;


import java.util.List;
import android.graphics.Color;;

public class IconAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resource;
    private List<String> iconList;
    private LayoutInflater layoutInflater;

    private int selectedIndex = 0;

    public IconAdapter(@NonNull Context context,
                       int resource,
                       @NonNull List<String> iconList,
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
                String icon = iconList.get(position);
                if (icon != null) {
                    buildIcon(view, icon);
                }

                if (position == selectedIndex) {
                    view.setBackgroundColor(Color.BLUE);
                }
            }
        }
        return view;
    }

    private void buildIcon(View view, String icon) {
        ImageView iv_category_icon = view.findViewById(R.id.row_item_icon);

        int resID = context.getResources().getIdentifier(icon, "drawable", context.getPackageName());
        iv_category_icon.setImageResource(resID);
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }
}
