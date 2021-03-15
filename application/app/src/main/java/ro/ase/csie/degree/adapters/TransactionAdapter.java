package ro.ase.csie.degree.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.model.Transaction;
import ro.ase.csie.degree.model.TransactionType;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    private Context context;
    private int resource;
    private List<Transaction> transactionList;
    private LayoutInflater layoutInflater;

    public TransactionAdapter(@NonNull Context context,
                              int resource,
                              @NonNull List<Transaction> transactionList,
                              LayoutInflater layoutInflater) {
        super(context, resource, transactionList);
        this.context = context;
        this.resource = resource;
        this.transactionList = transactionList;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);

        if (transactionList != null) {
            if (transactionList.size() > 0 ) {
                if (position < transactionList.size()) {
                    buildTransaction(view, transactionList.get(position));
                }
            }
        }
        return view;
    }

    private void buildTransaction(View view, Transaction transaction) {
        TextView tv_category = view.findViewById(R.id.row_transaction_category);
        TextView tv_amount = view.findViewById(R.id.row_transaction_amount);
        TextView tv_balance = view.findViewById(R.id.row_transaction_balance);
        ImageButton ib_bar = view.findViewById(R.id.row_transaction_bar);

        setText(tv_category, transaction.getCategory().getName());
        setText(tv_balance, transaction.getBalance().getName());
        setText(tv_amount, String.valueOf(transaction.getAmount()));

        ib_bar.setBackgroundColor(context.getResources().getColor(transaction.getCategory().getColor()));
        tv_amount.setTextColor(context.getResources().getColor(transaction.getCategory().getColor()));

    }

    private void setText(TextView tv, String text) {
        if (text != null && !text.isEmpty()) {
            tv.setText(text);
        } else {
            tv.setText("");
        }
    }
}
