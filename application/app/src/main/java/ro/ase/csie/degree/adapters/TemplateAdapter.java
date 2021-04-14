package ro.ase.csie.degree.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.List;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.model.Transaction;

public class TemplateAdapter extends ArrayAdapter<Transaction> {

    private final Context context;
    private final int resource;
    private final List<Transaction> templates;
    private final LayoutInflater layoutInflater;

    public TemplateAdapter(@NonNull Context context,
                           int resource,
                           List<Transaction> templates,
                           LayoutInflater layoutInflater) {
        super(context, resource, templates);
        this.context = context;
        this.resource = resource;
        this.templates = templates;
        this.layoutInflater = layoutInflater;
        Log.e("TemplateAdapter", templates.toString());
    }


    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(resource, parent, false);

        Log.e("TemplateAdapter", "size: " + templates.size());

        if (templates.size() > 0) {
            if (position < templates.size()) {
                Transaction template = templates.get(position);
                if (template != null) {
                    buildTemplate(view, template);
                }
            }
        }
        return view;
    }

    private void buildTemplate(View view, Transaction template) {

        RadioButton rb_category_type = view.findViewById(R.id.row_item_template_category_type);
        CardView cv_category_color = view.findViewById(R.id.row_item_template_category_color);
        TextView tv_category_name = view.findViewById(R.id.row_item_template_category_name);
        TextView tv_amount = view.findViewById(R.id.row_item_template_amount);
        TextView tv_balance_from = view.findViewById(R.id.row_item_template_balance_from);
        TextView tv_balance_to = view.findViewById(R.id.row_item_template_balance_to);

        rb_category_type.setText(template.getCategory().getType().toString());
        rb_category_type.setTextColor(context.getResources().getColor(R.color.rally_white));

        cv_category_color.setCardBackgroundColor(context.getResources().getColor(template.getCategory().getColor()));

        tv_category_name.setText(template.getCategory().getName());

        tv_amount.setText(String.valueOf(template.getAmount()));

        if (template.getBalance_from() != null) {
            tv_balance_from.setText(template.getBalance_from().getName());
        }

        if (template.getBalance_to() != null) {
            tv_balance_to.setText(template.getBalance_to().getName());
        }
    }

}
