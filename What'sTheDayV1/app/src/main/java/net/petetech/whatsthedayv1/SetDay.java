package net.petetech.whatsthedayv1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetDay extends AppCompatActivity {

    //add next Button and text fields
    private Button next;
    private EditText p1; // textbox for period 1 etc.
    private EditText p2;
    private EditText p3;
    private EditText p4;
    int daySet = 1; //storing the day number the user is setting (starts at day 1

    //String array to store schedule input
    String[][] schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_day);
        setTitle("Day " + daySet); //add number of the day being set in action bar title
        p1 = (EditText) findViewById(R.id.p1Field); //link instances of text edit class to text id in xml
        p2 = (EditText) findViewById(R.id.p2Field);
        p3 = (EditText) findViewById(R.id.p3Field);
        p4 = (EditText) findViewById(R.id.p4Field);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //code runs upon button pressed
                schedule[daySet][1] = p1.getText().toString();
                schedule[daySet][2] = p2.getText().toString();
                schedule[daySet][3] = p3.getText().toString();
                schedule[daySet][4] = p4.getText().toString();
                //check here if all text boxes are filled and display setup for next day
                if (schedule[daySet][1].equals("") || schedule[daySet][2].equals("") || schedule[daySet][3].equals("") || schedule[daySet][4].equals("")) {
                    Toast.makeText(SetDay.this, "Fill in all Classes", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
