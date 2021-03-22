package ro.ase.csie.degree.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.model.Transaction;
import ro.ase.csie.degree.model.TransactionType;

public class PieChartFragment extends Fragment {

    public static final String TRANSACTIONS = "transactions";
    private List<Transaction> transactionList;
    private Map<String, Float> categoriesMap;
    private ArrayList<PieEntry> pieEntries;
    private PieChart pieChart;
    private List<Integer> colors = new ArrayList<>();

    public PieChartFragment() {
        transactionList = new ArrayList<>();
    }

    public PieChartFragment(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            transactionList = getArguments().getParcelableArrayList(TRANSACTIONS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);


        pieChart = view.findViewById(R.id.day_pie_chart);
        pieChart.getDescription().setEnabled(false);

        this.categoriesMap = buildMap(this.transactionList);
        this.pieEntries = buildPieEntries();

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");

        pieDataSet.setColors(getColors(), getActivity().getApplicationContext());
        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.invalidate();

        return view;
    }

    private Map<String, Float> buildMap(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return null;
        } else {
            Map<String, Float> source = new HashMap<>();


            for (Transaction transaction : transactions) {

                if (transaction.getCategory().getType().equals(TransactionType.EXPENSE)) {
                    String key = transaction.getCategory().getName();

                    if (source.containsKey(key)) {
                        Float currentValue = source.get(key);
                        Float newValue = (currentValue != null ? currentValue : 0.0f)
                                + (float) transaction.getAmount();
                        source.put(key, newValue);
                    } else {
                        source.put(key, (float) transaction.getAmount());
                        this.colors.add(transaction.getCategory().getColor());
                    }
                }
            }
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

    private int[] getColors() {
        if (this.categoriesMap == null) {
            return new int[0];
        }

        int[] colorsArray = new int[this.colors.size()];

        for (int i = 0; i < this.colors.size(); i++) {
            colorsArray[i] = this.colors.get(i);
        }

        return colorsArray;
    }
}