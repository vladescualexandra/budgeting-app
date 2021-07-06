package ro.ase.csie.degree.charts;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.model.Transaction;
import ro.ase.csie.degree.util.theme.ThemeManager;

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

    protected int getTextColor() {
        Context context = Objects.requireNonNull(getActivity()).getApplicationContext();
        boolean isNightTheme = ThemeManager.getTheme(context);
        if (isNightTheme) {
            return getResources().getColor(R.color.rally_white);
        } else {
            return getResources().getColor(R.color.rally_dark_grey);
        }
    }


    protected List<Integer> getColors() {
        int spendingsColor = R.color.rally_dark_red;
        int savingsColor = R.color.rally_dark_green;
        List<Integer> colors = new ArrayList<>();
        colors.add(spendingsColor);
        colors.add(savingsColor);
        return colors;
    }
}


