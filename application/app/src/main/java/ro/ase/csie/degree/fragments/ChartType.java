package ro.ase.csie.degree.fragments;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import ro.ase.csie.degree.model.Transaction;

public enum ChartType {
    PIE_CHART,
    BAR_CHART,
    LINE_CHART;

    public static Fragment getFragment(ChartType type, ArrayList<Transaction> transactions) {
        switch (type) {
            case BAR_CHART:
                return new BarChartFragment(transactions);
            case LINE_CHART:
                return new LineChartFragment(transactions);
            default:
                return new PieChartFragment(transactions);
        }
    }
}
