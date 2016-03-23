package net.petetech.whatsthedayv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class Setup extends AppCompatActivity {

    private Button cont; // continue button in setup wizard
    private CheckBox JS; // checked if the student is in the junior school
    private CheckBox SS; // checked if the student is in the Senior School
    private CheckBox both; // checked if user is junior and senior school parent

    private boolean jsStudent;
    private boolean ssStudent;
    private boolean parent; // true if the box saying that the user is a junior and senior school parent is checked
    private boolean nothingChecked; // boolean true if the user has not checked any boxes preventing them from going forward with the setup wizard

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        setTitle("Welcome!");
        JS = (CheckBox) findViewById(R.id.js);
        SS = (CheckBox) findViewById(R.id.ss);
        both = (CheckBox) findViewById(R.id.jsss);
        final Intent setDay = new Intent(this, SetDay.class); // Intent linking to existing set day wizard from the settings menu
        cont = (Button) findViewById(R.id.cont);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences setupParams = getSharedPreferences("setupParams", Context.MODE_WORLD_WRITEABLE); //set up shared prefs file to store selections
                SharedPreferences.Editor edit = setupParams.edit();
                if (ssStudent) {
                    startActivity(setDay);
                    edit.putBoolean("ssMode", true);
                } else if (jsStudent) {
                    // start junior school setup activity here
                    edit.putBoolean("jsMode", true);
                } else if (parent) {
                    // start parent setup
                    edit.putBoolean("parentMode", true);
                } else if (nothingChecked) {
                    Toast.makeText(Setup.this, "Please Pick Which School You Are In", Toast.LENGTH_LONG).show();
                }
                edit.commit();
            }
        });
    }

    public void onCheckBoxClicked(View v) { // code runs when any of the 3 checkboxes are clicked
        boolean checked = ((CheckBox) v).isChecked(); // true if one or more of the boxes is checked

        switch(v.getId()) { // switch makes the setup menu expandable with potential for the addition of other textboxes
            case R.id.js:
                if (checked) {
                    jsStudent = true;
                    ssStudent = false;
                    parent = false;
                    SS.setChecked(false);
                    both.setChecked(false);
                } else {
                    clearBox();
                }
                break;
            case R.id.ss:
                if (checked) {
                    ssStudent = true;
                    jsStudent = false;
                    parent = false;
                    JS.setChecked(false);
                    both.setChecked(false);
                } else {
                    clearBox();
                }
                break;
            case R.id.jsss:
                if (checked) {
                    ssStudent = false;
                    jsStudent = false;
                    parent = true;
                    JS.setChecked(false);
                    SS.setChecked(false);
                } else {
                    clearBox();
                }
            default:
                clearBox();
        }
        if (!checked) {
            nothingChecked = true;
        }
    }

    private void clearBox() {
        JS.setChecked(false);
        SS.setChecked(false);
        ssStudent = false;
        jsStudent = false;
    }
}
