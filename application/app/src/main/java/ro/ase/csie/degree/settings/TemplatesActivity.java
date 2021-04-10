package ro.ase.csie.degree.settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ro.ase.csie.degree.AddTransactionActivity;
import ro.ase.csie.degree.R;
import ro.ase.csie.degree.model.Transaction;

public class TemplatesActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_CREATE_TEMPLATE = 201;
    private ImageButton ib_back;
    private ImageButton ib_add;
    private ListView lv_templates;

    private List<Transaction> templates = new ArrayList<>();

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

        ib_back.setOnClickListener(v-> finish());
        ib_add.setOnClickListener(addTemplateEventListener());

        setAdapter();
        lv_templates.setOnItemClickListener(useTemplateEventListener());
        lv_templates.setOnItemLongClickListener(deleteTemplateListener());
    }

    private void setAdapter() {
        ArrayAdapter<Transaction> adapter = new ArrayAdapter<>
                (getApplicationContext(),
                        R.layout.row_spinner_simple,
                        templates);
        lv_templates.setAdapter(adapter);
    }

    private View.OnClickListener addTemplateEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CREATE_TEMPLATE);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CREATE_TEMPLATE && data != null) {
                Transaction template = (Transaction) data.getSerializableExtra(AddTransactionActivity.NEW_TEMPLATE);
                Toast.makeText(getApplicationContext(),
                        REQUEST_CODE_CREATE_TEMPLATE + ": " + template.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private AdapterView.OnItemClickListener useTemplateEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        };
    }

    private AdapterView.OnItemLongClickListener deleteTemplateListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        };
    }




}