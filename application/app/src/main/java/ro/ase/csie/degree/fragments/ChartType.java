package ro.ase.csie.degree.fragments;

import androidx.fragment.app.Fragment;

public enum ChartType {
    PIE_CHART,
    BAR_CHART,
    LINE_CHART;

    public static Fragment getFragment(ChartType type) {
        switch (type) {
            case BAR_CHART:
                return new BarChartFragment();
            case LINE_CHART:
                return new LineChartFragment();
            default:
                return new PieChartFragment();
        }
    }
}
