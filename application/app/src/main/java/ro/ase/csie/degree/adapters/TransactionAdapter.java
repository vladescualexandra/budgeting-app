package ro.ase.csie.degree.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.model.Transaction;
import ro.ase.csie.degree.model.TransactionType;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    public static final int MAX_DOWNLOAD_SIZE_BYTES = 1024 * 1024;
    private Context context;
    private int resource;
    private List<Transaction> transactionList;
    private LayoutInflater layoutInflater;

    public TransactionAdapter(@NonNull Context context,
                              int resource,
                              @NonNull List<Transaction> transactionList,
                              LayoutInflater layoutInflater) {
        super(context, resource, transactionList);
        this.context = context;
        this.resource = resource;
        this.transactionList = transactionList;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);

        if (transactionList != null) {
            if (transactionList.size() > 0 ) {
                if (position < transactionList.size()) {
                    buildTransaction(view, transactionList.get(position));
                }
            }
        }
        return view;
    }

    private void buildTransaction(View view, Transaction transaction) {
        TextView tv_category = view.findViewById(R.id.row_transaction_category);
        TextView tv_details = view.findViewById(R.id.row_transaction_details);
        TextView tv_amount = view.findViewById(R.id.row_transaction_amount);
        TextView tv_balance = view.findViewById(R.id.row_transaction_balance);

        setBarIcon(view, transaction);
        setCategoryIcon(view, transaction);

        setText(tv_category, transaction.getCategory().getName());
        setText(tv_details, transaction.getDetails());

        String amount = String.valueOf(transaction.getAmount());
        setText(tv_amount, amount);

        setText(tv_balance, transaction.getBalance().getName());
    }

    private void setText(TextView tv, String text) {
        if (text != null && !text.isEmpty()) {
            tv.setText(text);
        } else {
            tv.setText("");
        }
    }

    private void setCategoryIcon(View view, Transaction transaction) {
        ImageView iv_icon = view.findViewById(R.id.row_transaction_icon);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child(transaction.getCategory().getIcon());
        storageReference.getBytes(MAX_DOWNLOAD_SIZE_BYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                iv_icon.setImageBitmap(bmp);
            }
        });
    }

    private void setBarIcon(View view, Transaction transaction) {
        ImageView iv_bar = view.findViewById(R.id.row_transaction_bar);
        if (transaction.getCategory().getType().equals(TransactionType.EXPENSE)) {
            iv_bar.setImageResource(R.drawable.bar_red);
        } else {
            iv_bar.setImageResource(R.drawable.bar_green);
        }
    }


}
