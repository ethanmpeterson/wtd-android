package net.petetech.whatsthedayv1;

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
    int daySet = 1; //storing the day number the user is setting (starts at day 1

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
                    if (daySet == 4) { // If statement will be used to write new schedule to shared preference
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

    private void saveDay(int dayNumber) {/* This function will be used later to save the user inputted schedule*/}

}
