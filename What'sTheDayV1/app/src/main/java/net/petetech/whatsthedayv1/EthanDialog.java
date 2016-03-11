package net.petetech.whatsthedayv1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;


public class EthanDialog extends DialogFragment { // class will construct different dialogs to be used by the rest of the app

    String dialogTitle = "Error: Set the Title Text!";
    String dialogMessage = "Error: Set the Dialog Message!";
    private boolean parentMode = false;
    private boolean ssMode = false;
    private boolean jsMode = false;

    public void setDialogText(String title, String message) {
        dialogTitle = title;
        dialogMessage = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(dialogTitle);
        builder.setSingleChoiceItems(R.array.modes_array, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Negative Button Was Clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Positive Button Was Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        Dialog dialog = builder.create();

        return dialog; // return dialog object that has been created this model will be used for the dialogs in this app
    }
}
