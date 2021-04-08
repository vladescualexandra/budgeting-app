package ro.ase.csie.degree.settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import ro.ase.csie.degree.TemplateActivity;
import ro.ase.csie.degree.TransactionActivity;
import ro.ase.csie.degree.R;
import ro.ase.csie.degree.util.Action;

public class TemplatesActivity extends AppCompatActivity {


    public static final int REQUEST_CODE_ADD_TEMPLATE = 201;
    public static final String IS_TEMPLATE = "is_template";
    private ImageButton ib_back;
    private ImageButton ib_add;
    private ListView lv_templates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);

        initComponents();

    }

    private void initComponents() {
        ib_back = findViewById(R.id.templates_back);
        ib_add = findViewById(R.id.templates_add);
        lv_templates = findViewById(R.id.templates_list);

        ib_back.setOnClickListener(v -> finish());
        ib_add.setOnClickListener(addTemplateEventListener());
        setAdapter();
    }

    private View.OnClickListener addTemplateEventListener() {
        return v -> {
            Intent intent = new Intent(getApplicationContext(), TemplateActivity.class);
            intent.putExtra(IS_TEMPLATE, true);
            intent.putExtra(Action.ACTION, Action.CREATE_NEW_TEMPLATE.toString());

            startActivityForResult(intent, REQUEST_CODE_ADD_TEMPLATE);
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_TEMPLATE
            && resultCode == RESULT_OK && data != null) {
        }
    }

    private void setAdapter() {

    }
}