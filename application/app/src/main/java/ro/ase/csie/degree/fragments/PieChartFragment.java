package ro.ase.csie.degree.fragments;

import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.model.Transaction;
import ro.ase.csie.degree.model.TransactionType;

public class PieChartFragment extends ChartFragment {

    private Map<String, Float> categoriesMap;
    private ArrayList<PieEntry> pieEntries;
    private PieChart pieChart;
    private ArrayList<Integer> colors = new ArrayList<>();

    private HashMap<Integer, Float> colorsMap = new HashMap<>();

    public PieChartFragment() {
        super();
    }

    public PieChartFragment(List<Transaction> transactionList) {
        super(transactionList);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);

        pieChart = view.findViewById(R.id.day_pie_chart);
        pieChart.getDescription().setEnabled(false);

        this.categoriesMap = buildMap();
        this.pieEntries = buildPieEntries();

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(this.colors);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

        return view;
    }


    private Map<String, Float> buildMap() {
        if (transactionList == null || transactionList.isEmpty()) {
            return null;
        } else {
            Map<String, Float> source = new HashMap<>();
            for (Transaction transaction : transactionList) {
                if (transaction.getCategory().getType().equals(TransactionType.EXPENSE)) {
                    String key = transaction.getCategory().getName();

                    int color = transaction.getCategory().getColor();


                    if (source.containsKey(key)) {
                        Float currentValue = source.get(key);
                        Float newValue = (currentValue != null ? currentValue : 0.0f)
                                + (float) transaction.getAmount();
                        source.put(key, newValue);
                        this.colorsMap.put(color, newValue);
                    } else {
                        source.put(key, (float) transaction.getAmount());
                        this.colorsMap.put(color, (float) transaction.getAmount());
                    }
                }
            }


            this.colors = sortByValue(this.colorsMap);
            return source;
        }
    }

    private ArrayList<PieEntry> buildPieEntries() {

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        if (this.categoriesMap == null) {
            return new ArrayList<>();
        } else {
            for (String key : this.categoriesMap.keySet()) {
                pieEntries.add(new PieEntry(this.categoriesMap.get(key), key));
            }
            return pieEntries;
        }
    }

    private ArrayList<Integer> sortByValue(HashMap<Integer, Float> source) {
        List<Map.Entry<Integer, Float>> list = new ArrayList<>(source.entrySet());
        Collections.sort(list, (e1, e2) -> (e1.getValue().compareTo(e2.getValue())));
        Collections.reverse(list);

        for (Map.Entry<Integer, Float> entry : list) {
            this.colors.add(getResources().getColor(entry.getKey()));
        }

        return this.colors;
    }
}