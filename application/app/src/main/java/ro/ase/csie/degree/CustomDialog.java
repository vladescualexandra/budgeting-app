package ro.ase.csie.degree;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;


public class CustomDialog {


    private static AlertDialog buildDialog(Context context, int header, int message,
                                          DialogInterface.OnClickListener positiveAction) {
        String header_text = context.getResources().getString(header);
        String message_text = context.getResources().getString(message);
        return new AlertDialog.Builder(context)
                .setTitle(header_text)
                .setMessage(message_text)
                .setPositiveButton(R.string.button_yes, positiveAction)
                .setNegativeButton(R.string.button_cancel, (dialog1, which) -> {
                })
                .create();

    }

    public static void show(Context context, int header, int message,
                            DialogInterface.OnClickListener positiveAction) {
        AlertDialog dialog = buildDialog(context, header, message, positiveAction);
        dialog.show();

        dialog
                .getButton(DialogInterface.BUTTON_NEGATIVE)
                .setTextColor(context.getResources().getColor(R.color.rally_dark_green));
        dialog
                .getButton(DialogInterface.BUTTON_POSITIVE)
                .setTextColor(context.getResources().getColor(R.color.rally_dark_green));
    }

}
