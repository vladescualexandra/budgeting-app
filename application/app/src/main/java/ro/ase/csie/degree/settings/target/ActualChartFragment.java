package ro.ase.csie.degree.settings.target;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.charts.ChartFragment;

public class ActualChartFragment extends ChartFragment {

    private Map<String, Float> actualMap;
    private PieChart pieChart;
    private ArrayList<PieEntry> pieEntries;


    public ActualChartFragment() {
    }

    public ActualChartFragment(Map<String, Float> actualMap) {
        this.actualMap = actualMap;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actual_chart, container, false);

        if (this.actualMap != null && !this.actualMap.isEmpty()) {
            buildPieChart(view);
        }

        return view;
    }

    private void buildPieChart(View view) {
        pieChart = view.findViewById(R.id.target_chart_target);
        chartSettings();

        this.pieEntries = buildPieEntries();

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(getColors());

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void chartSettings() {
        pieChart.setDrawEntryLabels(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setTextColor(getTextColor());
    }

    private ArrayList<PieEntry> buildPieEntries() {

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        if (this.actualMap == null) {
            return new ArrayList<>();
        } else {
            for (String key : this.actualMap.keySet()) {
                pieEntries.add(new PieEntry(this.actualMap.get(key), key));
            }
            return pieEntries;
        }
    }
}