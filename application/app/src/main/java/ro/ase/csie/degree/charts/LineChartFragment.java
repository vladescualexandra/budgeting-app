package ro.ase.csie.degree.charts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.model.Transaction;
import ro.ase.csie.degree.model.TransactionType;
import ro.ase.csie.degree.util.DateConverter;

public class LineChartFragment extends ChartFragment {

    private LineChart lineChart;
    private List<Entry> entries = new ArrayList<>();

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

        if (this.transactionList != null && !this.transactionList.isEmpty()) {
            buildLineChart(view);
        }

        return view;
    }

    private void buildLineChart(View view) {
        lineChart = view.findViewById(R.id.main_fragment_chart_line);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);


        this.entries = buildEntries();

        LineDataSet lineDataSet = new LineDataSet(this.entries, "");
        LineData lineData = new LineData(lineDataSet);

        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private List<Entry> buildEntries() {
        if (this.transactionList == null || this.transactionList.isEmpty()) {
            return new ArrayList<>();
        }

        Collections.sort(this.transactionList, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));

        for (int i = 0; i < this.transactionList.size(); i++) {
            if (!this.transactionList.get(i).getCategory().getType().equals(TransactionType.TRANSFER)) {
                float amount = (float) this.transactionList.get(i).getAmount();
                if (this.transactionList.get(i).getCategory().getType().equals(TransactionType.EXPENSE)) {
                    amount *= -1;
                }

                this.entries.add(new Entry(i, amount));
            }

        }

        return this.entries;
    }
}
