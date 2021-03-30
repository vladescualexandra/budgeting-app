package ro.ase.csie.degree.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.model.Transaction;
import ro.ase.csie.degree.model.Transfer;
import ro.ase.csie.degree.util.DateConverter;

public class TransactionExpandableAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private List<? extends Transaction> transactionList;

    private List<Transaction> headers = new ArrayList<>();
    private HashMap<Transaction, List<String>> expandableDetails = new HashMap<>();

    public TransactionExpandableAdapter(Context context, List<Transaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
        prepareHeaders();
        prepareDetails();
    }

    private void prepareHeaders() {
        this.headers.clear();
        this.headers.addAll(this.transactionList);
    }

    private void prepareDetails() {
        for (Transaction transaction : transactionList) {
            List<String> childList = new ArrayList<>();
            childList.add("Date: " + DateConverter.toString(transaction.getDate()));
            if (transaction.getDetails() != null && !transaction.getDetails().isEmpty()) {
                childList.add("Details: " + transaction.getDetails());
            }
            childList.add("From balance: " + transaction.getBalance_from().getName());

            this.expandableDetails.put(transaction, childList);
        }
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableDetails.get(this.headers.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row_item_transaction_expand, null);
        }


        TextView expandedListTextView = convertView
                .findViewById(R.id.row_item_transaction_expand_property);
        expandedListTextView.setText(expandedListText);

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableDetails.get(this.headers.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.headers.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.headers.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Transaction transaction = (Transaction) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row_item_transaction, null);
        }

        buildHeader(convertView, transaction);
        return convertView;
    }

    private void buildHeader(View convertView, Transaction transaction) {
        ImageView iv_bar = convertView.findViewById(R.id.row_transaction_bar);
        TextView tv_category = convertView.findViewById(R.id.row_transaction_category);
        TextView tv_amount = convertView.findViewById(R.id.row_transaction_amount);

        tv_category.setText(transaction.getCategory().getName());

        String sign;
        switch (transaction.getCategory().getType()) {
            case EXPENSE:
                sign = "-";
                break;
            case INCOME:
                sign = "+";
                break;
            default:
                sign = "";
                break;
        }

        tv_amount.setText(sign + transaction.getAmount());

        iv_bar.setBackgroundColor(context.getResources().getColor(transaction.getCategory().getColor()));
        tv_amount.setTextColor(context.getResources().getColor(transaction.getCategory().getColor()));
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}

