package net.petetech.whatsthedayv1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


public class EthanDialog extends DialogFragment { // class will construct different dialogs to be used by the rest of the app

    String dialogTitle = "Error: Set the Title Text!";
    String dialogMessage = "Error: Set the Dialog Message!";


    boolean parentMode = false;
    boolean ssMode = false;
    boolean jsMode = false;

    public void setDialogText(String title, String message) {
        dialogTitle = title;
        dialogMessage = message;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(dialogTitle);
        builder.setCancelable(false);
        builder.setSingleChoiceItems(R.array.modes_array, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        ssMode = true;
                        jsMode = false;
                        parentMode = false;
                        break;
                    case 1:
                        jsMode = true;
                        ssMode = false;
                        parentMode = false;
                        break;
                    case 2:
                        parentMode = true;
                        ssMode = false;
                        jsMode = false;
                        break;
                    default:
                        ssMode = false;
                        jsMode = false;
                        parentMode = false;
                }
            }
        });

        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // set the local mode booleans to equal the ones in xml

            }
        });

        AlertDialog dialog = builder.create();

        return dialog; // return dialog object that has been created this model will be used for the dialogs in this app
    }
}
