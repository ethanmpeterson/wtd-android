package net.petetech.whatsthedayv1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;


public class EthanDialog extends DialogFragment { // class will construct different dialogs to be used by the rest of the app
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Dialog dialog = builder.create();

        return dialog; // return dialog object that has been created this model will be used for the dialogs in this app
    }
}
