package net.petetech.whatsthedayv1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ScheduleDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Please Set Your Schedule");
        builder.setCancelable(false);
        builder.setPositiveButton("Set My Schedule!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent setDay = new Intent(getActivity(), SetDay.class);
                startActivity(setDay);
            }
        });
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
