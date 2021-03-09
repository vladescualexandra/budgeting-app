package ro.ase.csie.degree.settings.categories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import ro.ase.csie.degree.R;

import ro.ase.csie.degree.model.Category;
import ro.ase.csie.degree.adapters.IconAdapter;
import ro.ase.csie.degree.util.InputValidation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AddCategoryActivity extends AppCompatActivity {

    private final String GREEN_ICONS = "greens";
    private final String RED_ICONS = "reds";

    public static final String NEW_CATEGORY = "new_category";
    private List<Bitmap> icons = new ArrayList<>();

    private TextInputEditText tiet_name;
    private GridView gv_icons;
    private Button btn_save;

    private Intent intent;
    final long ONE_MEGABYTE = 1024 * 1024;

    private List<String> paths = new ArrayList<>();
    Category category = new Category();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);



        initComponents();
        setAdapter();


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference;

        intent = getIntent();
        boolean isExpense = intent.getBooleanExtra(CategoriesActivity.CATEGORY_TYPE, false);
        if (isExpense) {
            storageReference = storage.getReference().child(RED_ICONS);
        } else {
            storageReference = storage.getReference().child(GREEN_ICONS);
        }


        storageReference.listAll().addOnSuccessListener(listIconBitmaps());



    }

    private OnSuccessListener<ListResult> listIconBitmaps() {
        return listResult -> {
            for (StorageReference ref : listResult.getItems()) {
                Task<byte[]> task = ref.getBytes(ONE_MEGABYTE);
                task.addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        icons.add(bmp);
                        paths.add(ref.getPath());
                        notifyAdapter();
                    }
                });
            }
            setAdapter();
        };
    }

    private void initComponents() {
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
                category.setIcon(paths.get(position));
            }
        });
    }

    private void notifyAdapter() {
        IconAdapter adapter = (IconAdapter) gv_icons.getAdapter();
        adapter.notifyDataSetChanged();
    }


    private View.OnClickListener saveCategoryEventListener() {
        return v -> {
            InputValidation validation = new InputValidation(getApplicationContext());
            if (validation.nameValidation(tiet_name)) {
                category.setName(tiet_name.getText().toString().trim());
                intent.putExtra(NEW_CATEGORY, category);
                setResult(RESULT_OK, intent);
                finish();
            }

        };
    }
}