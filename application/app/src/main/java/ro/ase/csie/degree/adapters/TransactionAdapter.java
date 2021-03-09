package ro.ase.csie.degree.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ro.ase.csie.degree.model.Transaction;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    public static final int MAX_DOWNLOAD_SIZE_BYTES = 1024 * 1024;
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
        View view = getView(position, convertView, parent);
        if (transactionList != null) {
            if (transactionList.size() > 0 ) {
                if (position < transactionList.size()) {
                    // TO DO
                }
            }
        }
        return view;
    }
}
