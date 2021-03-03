package ro.ase.csie.degree.settings.categories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import ro.ase.csie.degree.R;

import ro.ase.csie.degree.model.Category;
import ro.ase.csie.degree.util.IconAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddCategoryActivity extends AppCompatActivity {

    public static final String NEW_CATEGORY = "new_category";
    private List<Bitmap> icons = new ArrayList<>();

    private TextInputEditText tiet_name;
    private GridView gv_icons;
    private Button btn_save;

    private Intent intent;
    final long ONE_MEGABYTE = 1024 * 1024 * 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);



        initComponents();
        setAdapter();

        ImageView iv_test = findViewById(R.id.iv_test);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("greens").child("green_1.png");

        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Log.e("onSuccess", bmp.toString());
                iv_test.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
            }
        });

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