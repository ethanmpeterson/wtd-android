package net.petetech.whatsthedayv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class Setup extends AppCompatActivity {

    private Button cont; // continue button in setup wizard
    private CheckBox JS; // checked if the student is in the junior school
    private CheckBox SS; // checked if the student is in the Senior School

    boolean jsStudent;
    boolean ssStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        setTitle("Welcome!");
        JS = (CheckBox) findViewById(R.id.js);
        SS = (CheckBox) findViewById(R.id.ss);
        final Intent setDay = new Intent(this, SetDay.class); // Intent linking to existing set day wizard from the settings menu
        cont = (Button) findViewById(R.id.cont);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ssStudent) {
                    startActivity(setDay);
                } else if (jsStudent) {
                    // start junior school setup activity here
                }
            }
        });
    }

    public void onCheckBoxClicked(View v) {
        boolean checked = ((CheckBox) v).isChecked(); // true if one or more of the boxes is checked

        switch(v.getId()) {
            case R.id.js:
                if (checked) {
                    jsStudent = true;
                    ssStudent = false;
                    SS.setChecked(false);
                } else {
                    clearBox();
                }
                break;
            case R.id.ss:
                if (checked) {
                    ssStudent = true;
                    jsStudent = false;
                    JS.setChecked(false);
                } else {
                    clearBox();
                }
                break;
            default:
                clearBox();
        }
    }

    private void clearBox() {
        JS.setChecked(false);
        SS.setChecked(false);
        ssStudent = false;
        jsStudent = false;
    }
}
