package com.example.budgeting_app.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.budgeting_app.R;
import com.example.budgeting_app.model.Category;
import com.example.budgeting_app.util.CategoryAdapter;
import com.example.budgeting_app.util.IconAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private BottomNavigationView menu_categories;
    private FloatingActionButton fab_add;
    private ListView lv_categories;

    private List<Category> expenses_categories;
    private List<Category> income_categories;

    private List<String> icons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        icons.add("ic_baseline_add_circle_24");
        icons.add("ic_baseline_add_circle_24");
        icons.add("ic_baseline_add_circle_24");
        icons.add("ic_baseline_add_circle_24");
        icons.add("ic_baseline_add_circle_24");
        icons.add("ic_baseline_add_circle_24");

        Category c_ex1 = new Category("", "ex1");
        Category c_ex2 = new Category("", "ex2");
        Category c_ex3 = new Category("", "ex3");

        Category c_inc1 = new Category("", "inc1");
        Category c_inc2 = new Category("", "inc2");
        Category c_inc3 = new Category("", "inc3");

        expenses_categories = new ArrayList<>();
        expenses_categories.add(c_ex1);
        expenses_categories.add(c_ex2);
        expenses_categories.add(c_ex3);
        income_categories = new ArrayList<>();
        income_categories.add(c_inc1);
        income_categories.add(c_inc2);
        income_categories.add(c_inc3);

        initMenu();
    }

    private void initMenu() {
        menu_categories = findViewById(R.id.categories_bottom_menu);
        menu_categories.setOnNavigationItemSelectedListener(changeCategoryEventListener());

        lv_categories = findViewById(R.id.categories_list);
        menu_categories.getMenu().getItem(0).setChecked(true); // Expenses
        setAdapter(expenses_categories);

        fab_add = findViewById(R.id.setting_categories_add_new);
        fab_add.setOnClickListener(addCategoryEventListener());
    }

    private void setAdapter(List<Category> list) {

        CategoryAdapter adapter = new CategoryAdapter(getApplicationContext(),
                R.layout.row_item_category,
                list,
                getLayoutInflater());
        lv_categories.setAdapter(adapter);
    }

    private void notifyAdapter() {
        CategoryAdapter adapter = (CategoryAdapter) lv_categories.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener changeCategoryEventListener() {
        return item -> {
            if (item.getItemId() == R.id.menu_categories_expenses) {
                setAdapter(expenses_categories);
                return true;
            } else if (item.getItemId() == R.id.menu_categories_income) {
                setAdapter(income_categories);
                return true;
            }

            return false;
        };
    }

    private View.OnClickListener addCategoryEventListener() {
        return v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext(),
                    R.style.Widget_AppCompat_Button_ButtonBar_AlertDialog);

            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            View view = layoutInflater.inflate(R.layout.dialog_add_category, null);

            RecyclerView rv_icons = view.findViewById(R.id.add_category_select_icon);
            IconAdapter adapter = new IconAdapter(icons);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv_icons.setLayoutManager(linearLayoutManager);
            rv_icons.setItemAnimator(new DefaultItemAnimator());
            rv_icons.setAdapter(adapter);


            alertDialogBuilder.setView(view);

            ListView lv_icons = findViewById(R.id.add_category_select_icon);
            EditText tiet_name = findViewById(R.id.add_category_select_name);

            alertDialogBuilder.setPositiveButton(R.string.dialog_save, (dialog, which) -> {
                Category category = new Category((String) lv_icons.getSelectedItem(), tiet_name.getText().toString().trim());
                if (menu_categories.getSelectedItemId() == R.id.menu_categories_expenses) {
                    expenses_categories.add(category);
                } else if (menu_categories.getSelectedItemId() == R.id.menu_categories_income) {
                    income_categories.add(category);
                }
                notifyAdapter();
            });

            alertDialogBuilder.setNegativeButton(R.string.dialog_cancel, null);
            alertDialogBuilder.show();
        };
    }
}