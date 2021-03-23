package ro.ase.csie.degree.charts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.model.Transaction;

public class LineChartFragment extends ChartFragment {
    public LineChartFragment() {
        super();
    }

    public LineChartFragment(List<Transaction> transactionList) {
        super(transactionList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_line_chart, container, false);
        return view;
    }
}
