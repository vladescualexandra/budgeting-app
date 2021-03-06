package ro.ase.csie.degree.settings.categories;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import ro.ase.csie.degree.R;

import ro.ase.csie.degree.authentication.user.User;
import ro.ase.csie.degree.firebase.Callback;
import ro.ase.csie.degree.firebase.FirebaseService;
import ro.ase.csie.degree.model.Category;
import ro.ase.csie.degree.util.CategoryAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    public static final String CATEGORY_TYPE = "category_type";
    private static final int REQUEST_CODE_ADD_CATEGORY = 201;
    private BottomNavigationView menu_categories;
    private FloatingActionButton fab_add;
    private ListView lv_categories;

    private List<Category> expenses_categories = new ArrayList<>();
    private List<Category> income_categories = new ArrayList<>();

    private FirebaseService firebaseService;
    private boolean isExpense = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        initMenu();
        getCategoriesFromFirebase();
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

    private void getCategoriesFromFirebase() {
        firebaseService = FirebaseService.getInstance(FirebaseService.TABLE_CATEGORIES);
        firebaseService.updateCategoriesUI(updateCategoriesCallback(), getUID());
    }

    private Callback<List<Category>> updateCategoriesCallback() {
        return result -> {
            if (result != null) {
                expenses_categories.clear();
                income_categories.clear();
                for (Category category : result) {
                    if (category.getType().equals(Category.TYPE_EXPENSE)) {
                        expenses_categories.add(category);
                    } else if (category.getType().equals(Category.TYPE_INCOME)){
                        income_categories.add(category);
                    }
                }
                notifyAdapter();
            }
        };
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
            Intent intent = new Intent(getApplicationContext(), AddCategoryActivity.class);
            isExpense = menu_categories.getSelectedItemId() == R.id.menu_categories_expenses;

            intent.putExtra(CATEGORY_TYPE, isExpense);
            startActivityForResult(intent, REQUEST_CODE_ADD_CATEGORY);
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_CATEGORY
                && data != null
                && resultCode == RESULT_OK) {
            Category category = (Category) data.getSerializableExtra(AddCategoryActivity.NEW_CATEGORY);
            category.setType(isExpense ? Category.TYPE_EXPENSE : Category.TYPE_INCOME);
            category.setUser(getUID());
            firebaseService.insertCategory(category);
            notifyAdapter();
        }
    }

    private String getUID() {
        return getSharedPreferences(User.USER_PREFS, MODE_PRIVATE)
                .getString(User.USER_KEY, null);
    }

}