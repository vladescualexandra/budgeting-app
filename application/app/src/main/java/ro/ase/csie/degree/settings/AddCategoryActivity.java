package ro.ase.csie.degree.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import ro.ase.csie.degree.R;

import ro.ase.csie.degree.model.Category;
import ro.ase.csie.degree.util.IconAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddCategoryActivity extends AppCompatActivity {

    private final String NEW_CATEGORY = "new_category";
    private List<String> icons = new ArrayList<>();

    private TextInputEditText tiet_name;
    private GridView gv_icons;
    private Button btn_save;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);


        icons.add("icon_dark_mode_1");
        icons.add("icon_dark_mode_2");
        icons.add("icon_dark_mode_3");
        icons.add("ic_baseline_add_circle_24");
        icons.add("ic_baseline_add_circle_24");
        icons.add("ic_baseline_add_circle_24");
        icons.add("ic_baseline_add_circle_24");
        icons.add("ic_baseline_add_circle_24");


        initComponents();
        setAdapter();


    }

    private void initComponents() {
        intent = getIntent();
        tiet_name = findViewById(R.id.add_category_select_name);
        gv_icons = findViewById(R.id.add_category_select_icon);
        btn_save = findViewById(R.id.add_category_save);
        btn_save.setOnClickListener(saveCategoryEventListener());
    }

    private void setAdapter() {
        IconAdapter adapter = new IconAdapter(getApplicationContext(),
                R.layout.row_item_icon, icons, getLayoutInflater());
        gv_icons.setAdapter(adapter);
        gv_icons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedIndex(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private View.OnClickListener saveCategoryEventListener() {
        return v -> {
            Category category = new Category((String) gv_icons.getSelectedItem(), tiet_name.getText().toString().trim());
            intent.putExtra(NEW_CATEGORY, category);
            setResult(RESULT_OK, intent);
            finish();
        };
    }
}