package ro.ase.csie.degree.charts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import ro.ase.csie.degree.model.Transaction;

public abstract class ChartFragment extends Fragment {

    public static final String TRANSACTIONS = "transactions";
    protected List<Transaction> transactionList;

    protected ChartFragment() {
        transactionList = new ArrayList<>();
    }

    protected ChartFragment(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            transactionList = getArguments().getParcelableArrayList(TRANSACTIONS);
        }
    }


}
