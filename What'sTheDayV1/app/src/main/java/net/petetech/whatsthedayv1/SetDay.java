package net.petetech.whatsthedayv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SetDay extends AppCompatActivity {

    //add next Button and text fields
    private Button next;
    private EditText p1; // textbox for period 1 etc.
    private EditText p2;
    private EditText p3;
    private EditText p4;
    private TextView header;
    private Global g = new Global(); // private instance of global variable to make global variables for other activities to use
    int daySet = 1; //storing the day number the user is setting (starts at day 1
    //setup shared preference file for the users schedule;

    String schedule[][] = new String[5][5]; //make array 5x5 because of zero indexing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_day);
        setTitle("Day " + daySet); //add number of the day being set in action bar title
        p1 = (EditText) findViewById(R.id.p1Field); //link instances of text edit class to text id in xml
        p2 = (EditText) findViewById(R.id.p2Field);
        p3 = (EditText) findViewById(R.id.p3Field);
        p4 = (EditText) findViewById(R.id.p4Field);
        header = (TextView) findViewById(R.id.header);
        next = (Button) findViewById(R.id.next);
        final Intent main; //Intent returns user to main activity
        main = new Intent(this, MainActivity.class);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //code runs upon button pressed
                //check here if all text boxes are filled and display setup for next day
                if (p1.getText().toString().equals("") || p2.getText().toString().equals("") || p3.getText().toString().equals("") || p4.getText().toString().equals("")) {
                    Toast.makeText(SetDay.this, "Fill in all Classes", Toast.LENGTH_SHORT).show();
                } else if (!p1.getText().toString().equals("") && !p2.getText().toString().equals("") && !p3.getText().toString().equals("") && !p4.getText().toString().equals("")) {
                    schedule[daySet][1] = p1.getText().toString(); //collect strings from the text box if they have been filled in
                    schedule[daySet][2] = p2.getText().toString();
                    schedule[daySet][3] = p3.getText().toString();
                    schedule[daySet][4] = p4.getText().toString();
                    Toast.makeText(SetDay.this, "Day " + daySet + " Set", Toast.LENGTH_SHORT).show();
                    if (daySet == 3) {
                        next.setText("Finish");
                    }
                    if (daySet == 4) { // If statement will be used to write new schedule to shared preference
                        saveSchedule();
                        Toast.makeText(SetDay.this, "Schedule Saved!", Toast.LENGTH_SHORT).show();
                        startActivity(main);
                    } else {
                        daySet++; //increment daySet so that the next values put into array get their own row
                        header.setText("Set Day " + daySet + " Classes Below:"); //set header text to instruct user to set their schedule for the next day
                        //clear text box text
                        clearText();
                        setTitle("Day " + daySet);
                    }
                }
            }
        });
    }

    private void clearText() { //function to clear user inputted text
        //although function is simple it prevents the same coed being written repetitively
        p1.setText("");
        p2.setText("");
        p3.setText("");
        p4.setText("");
    }

    public void saveSchedule() {
        SharedPreferences Schedule = getSharedPreferences("Schedule", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Schedule.edit(); //create instance of the editor object allowing the preferences to be edited

        editor.clear(); // clear values currently in the file if the change schedule function had been used before

        //add day 1 schedule to file
        editor.putString("D1P1", schedule[1][1]); //first string passed into the function will be the naming scheme for the rest of the file day then period
        editor.putString("D1P2", schedule[1][2]);
        editor.putString("D1P3", schedule[1][3]);
        editor.putString("D1P4", schedule[1][4]);
        //day 2
        editor.putString("D2P1", schedule[2][1]);
        editor.putString("D2P2", schedule[2][2]);
        editor.putString("D2P3", schedule[2][3]);
        editor.putString("D2P4", schedule[2][4]);
        //day 3
        editor.putString("D3P1", schedule[3][1]);
        editor.putString("D3P2", schedule[3][2]);
        editor.putString("D3P3", schedule[3][3]);
        editor.putString("D3P4", schedule[3][4]);
        //day 4
        editor.putString("D4P1", schedule[4][1]);
        editor.putString("D4P2", schedule[4][2]);
        editor.putString("D4P3", schedule[4][3]);
        editor.putString("D4P4", schedule[4][4]);
        //write changes to file
        editor.commit();
    }
}
