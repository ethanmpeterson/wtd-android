package net.petetech.whatsthedayv1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SetDay extends AppCompatActivity {

    //add next Button and text fields
    private Button next;
    int daySet = 1; //storing the day number the user is setting (starts at day 1)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_day);
        setTitle("Day " + daySet); //add number of the day being set in action bar title
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //code runs upon button pressed
                //check here if all text boxes are filled and display setup for next day
            }
        });
    }
}
