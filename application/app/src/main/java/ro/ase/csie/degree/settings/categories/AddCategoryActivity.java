package ro.ase.csie.degree.settings.categories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import ro.ase.csie.degree.R;

import ro.ase.csie.degree.model.Category;
import ro.ase.csie.degree.adapters.ColorAdapter;
import ro.ase.csie.degree.util.InputValidation;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddCategoryActivity extends AppCompatActivity {


    public static final String NEW_CATEGORY = "new_category";
    private List<Bitmap> icons = new ArrayList<>();

    private TextInputEditText tiet_name;
    private GridView gv_icons;
    private Button btn_save;

    private Intent intent;

    Category category = new Category();

    private List<Integer> colors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        initComponents();

        setAdapter();

        intent = getIntent();

    }


    private void initComponents() {
        tiet_name = findViewById(R.id.add_category_select_name);
        gv_icons = findViewById(R.id.add_category_select_icon);
        btn_save = findViewById(R.id.add_category_save);
        btn_save.setOnClickListener(saveCategoryEventListener());
    }

    private void setAdapter() {
        getColors();

        ColorAdapter adapter = new ColorAdapter(getApplicationContext(),
                R.layout.row_item_color, colors, getLayoutInflater());
        gv_icons.setAdapter(adapter);
        gv_icons.setOnItemClickListener((parent, view, position, id) -> {
            adapter.setSelectedIndex(position);
            adapter.notifyDataSetChanged();
            category.setColor(colors.get(position));
        });
    }


    private View.OnClickListener saveCategoryEventListener() {
        return v -> {
            if (InputValidation.nameValidation(getApplicationContext(), tiet_name)) {
                category.setName(tiet_name.getText().toString().trim());
                intent.putExtra(NEW_CATEGORY, category);
                setResult(RESULT_OK, intent);
                finish();
            }

        };
    }


    private void getColors() {
        colors = new ArrayList<>();
        colors.add(R.color.color_0);
        colors.add(R.color.color_1);
        colors.add(R.color.color_2);
        colors.add(R.color.color_3);
        colors.add(R.color.color_4);
        colors.add(R.color.color_5);
        colors.add(R.color.color_6);
        colors.add(R.color.color_7);
        colors.add(R.color.color_8);
        colors.add(R.color.color_9);
        colors.add(R.color.color_10);
        colors.add(R.color.color_11);
        colors.add(R.color.color_12);
        colors.add(R.color.color_13);
        colors.add(R.color.color_14);
        colors.add(R.color.color_15);
        colors.add(R.color.color_16);
        colors.add(R.color.color_17);
        colors.add(R.color.color_18);
        colors.add(R.color.color_19);
        colors.add(R.color.color_20);
        colors.add(R.color.color_21);
        colors.add(R.color.color_22);
        colors.add(R.color.color_23);
        colors.add(R.color.color_24);
        colors.add(R.color.color_25);
        colors.add(R.color.color_26);
        colors.add(R.color.color_27);
    }
}