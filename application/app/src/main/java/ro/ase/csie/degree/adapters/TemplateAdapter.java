package ro.ase.csie.degree.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import java.util.List;

import ro.ase.csie.degree.AddTransactionActivity;
import ro.ase.csie.degree.R;
import ro.ase.csie.degree.firebase.services.TemplateService;
import ro.ase.csie.degree.model.Transaction;
import ro.ase.csie.degree.settings.TemplatesActivity;

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
    }


    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(resource, parent, false);

        if (templates.size() > 0) {
            if (position < templates.size()) {
                Transaction template = templates.get(position);
                if (template != null) {
                    buildTemplate(view, template);
                }
            }
        }

        view.setOnClickListener(useTemplateEventListener(position));
        view.setOnLongClickListener(deleteTemplateListener(position));

        return view;
    }

    private View.OnLongClickListener deleteTemplateListener(int position) {
        return v -> {
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this template?")
                    .setPositiveButton("Yes", (dialog12, which) -> {
                        TemplateService templateService = new TemplateService();
                        templateService.delete(templates.get(position));
                    })
                    .setNegativeButton("Cancel", (dialog1, which) -> {
                    })
                    .create();


            dialog.show();

            dialog
                    .getButton(DialogInterface.BUTTON_NEGATIVE)
                    .setTextColor(context.getResources().getColor(R.color.rally_dark_green));
            dialog
                    .getButton(DialogInterface.BUTTON_POSITIVE)
                    .setTextColor(context.getResources().getColor(R.color.rally_dark_green));
            return false;
        };
    }

    private View.OnClickListener useTemplateEventListener(int position) {
        return v -> {
            Intent intent = new Intent(context, AddTransactionActivity.class);
            intent.putExtra(TemplatesActivity.USE_TEMPLATE, templates.get(position));
            context.startActivity(intent);
        };
    }

    private void buildTemplate(View view, Transaction template) {
        buildCategory(view, template);
        buildAmount(view, template);
        buildBalances(view, template);
    }

    private void buildCategory(View view, Transaction template) {
        RadioButton rb_category_type = view.findViewById(R.id.row_item_template_category_type);
        rb_category_type.setText(template.getCategory().getType().toString());
        rb_category_type.setTextColor(context.getResources().getColor(R.color.rally_white));

        CardView cv_category_color = view.findViewById(R.id.row_item_template_category_color);
        TextView tv_category_name = view.findViewById(R.id.row_item_template_category_name);
        cv_category_color.setCardBackgroundColor(context.getResources().getColor(template.getCategory().getColor()));
        tv_category_name.setText(template.getCategory().getName());
    }

    private void buildAmount(View view, Transaction template) {
        TextView tv_amount = view.findViewById(R.id.row_item_template_amount);
        tv_amount.setText(String.valueOf(template.getAmount()));
    }

    private void buildBalances(View view, Transaction template) {
        TextView tv_balance_from = view.findViewById(R.id.row_item_template_balance_from);
        TextView tv_balance_to = view.findViewById(R.id.row_item_template_balance_to);

        if (template.getBalance_from() != null) {
            tv_balance_from.setText(template.getBalance_from().getName());
        }

        if (template.getBalance_to() != null) {
            tv_balance_to.setText(template.getBalance_to().getName());
        }
    }


}
