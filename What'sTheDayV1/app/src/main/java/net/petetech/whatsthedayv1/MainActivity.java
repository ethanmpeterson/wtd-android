package net.petetech.whatsthedayv1;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    int[][] schoolYear = {
            {9, 9, 9, 9, 0, 4, 1, 2, 3, 9, 9, 4, 1, 2, 3, 4, 9, 9, 1, 2, 3, 4, 1, 9, 9, 2, 3, 4, 1, 2, 9, 9}, // January
            {9, 3, 4, 1, 2, 3, 9, 9, 4, 1, 2, 3, 9, 9, 9, 9, 4, 1, 2, 3, 9, 9, 4, 1, 2, 3, 4, 9, 9, 1},       // February
            {9, 2, 3, 4, 1, 9, 9, 2, 3, 4, 1, 2, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0, 3, 4, 1}, // March
            {9, 2, 9, 9, 3, 4, 1, 2, 3, 9, 9, 4, 1, 2, 3, 4, 9, 9, 1, 2, 3, 4, 1, 9, 9, 2, 3, 4, 1, 2, 9},    // April
            {9, 9, 3, 4, 1, 2, 3, 9, 9, 4, 1, 2, 3, 4, 9, 9, 1, 2, 3, 4, 1, 9, 9, 9, 2, 3, 4, 1, 9, 9, 2, 3}, // May
            {9, 4, 1, 2, 9, 9, 9, 3, 4, 1, 2, 3, 9, 9, 4, 1, 2, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},    // June
            {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9}, // July
            {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9}, // August
            {9, 9, 9, 9, 9, 9, 9, 9, 9, 0, 1, 2, 9, 9, 3, 4, 1, 2, 3, 9, 9, 4, 1, 2, 3, 5, 9, 9, 4, 1, 2},    // September
            {9, 3, 4, 9, 9, 1, 2, 3, 4, 1, 9, 9, 9, 2, 3, 4, 1, 9, 9, 2, 3, 4, 1, 2, 9, 9, 3, 4, 1, 2, 3, 9}, // October
            {9, 9, 4, 1, 2, 3, 4, 9, 9, 1, 2, 3, 9, 9, 9, 9, 4, 1, 2, 3, 4, 9, 9, 1, 2, 3, 4, 1, 9, 9, 2},    // November
            {9, 3, 4, 1, 2, 9, 9, 3, 4, 1, 2, 3, 9, 9, 4, 1, 2, 3, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9}  // December
    }; //same array that is in the gecko firmware that is filled with the day calendar for 2015-2016 school year

    //ints and Strings for day counting
    int dayNum; //stores day num and whether it is holiday or not

    Calendar c = Calendar.getInstance();

    int month;
    int dayOfMonth;
    int weekday;
    int monthInput; //ints to be assigned to date picker input
    int dayInput;
    int yearInput;
    static final int DID = 0; //stores dialog id
    boolean isWeekend; // will store whether it is the weekend or not
    boolean dateChanged; //storing whether user has changed the date or not
    //create strings to be displayed for each class
    String p1;
    String p2;
    String p3;
    String p4;

    String p1Time;
    String p2Time;
    String p3Time;
    String p4Time;

    String cdButton; //will store the text displayed on the change date depending on whether the date has been changed or not

    //create TextViews for each school class

    private TextView period1;
    private TextView period2;
    private TextView period3;
    private TextView period4;
    private TextView day; //textView to display the dayNum
    private TextView selectedDate; //will display the date the user has selected
    Button changeDate; //button to open second activity to change the date displayed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        period1 = (TextView) findViewById(R.id.p1View);
        period2 = (TextView) findViewById(R.id.p2View);
        period3 = (TextView) findViewById(R.id.p3View);
        period4 = (TextView) findViewById(R.id.p4View);
        selectedDate = (TextView) findViewById(R.id.textView2);
        day = (TextView) findViewById(R.id.dayView);
        changeDate = (Button) findViewById(R.id.cd);
        update();
        changeDate.setText("Change Date");
        changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //shows date picker when change date is pressed
                if (dateChanged) {
                    dateChanged = false;
                }
                new DatePickerDialog(MainActivity.this, listener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        update();

        if (dayNum == 1 || dayNum == 2 || dayNum == 3 || dayNum == 4) {
            period1.setText(p1 + p1Time);
            period2.setText(p2 + p2Time);
            period3.setText(p3 + p3Time);
            period4.setText(p4 + p4Time);
            day.setText("Day " + dayNum);
        } else if (dayNum == 9) {
            day.setText("It's a Holiday!");
        }
    }
    DatePickerDialog.OnDateSetListener listener = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) { //code runs when user sets the date
            dayInput = dayOfMonth;
            monthInput = monthOfYear + 1;
            yearInput = year;
            dateChanged = true;
            update();
            drawSchedule();
        }
    };


        void drawSchedule() { //function that updates the schedule on screen revolving around the change date button
            if (dayNum == 1 || dayNum == 2 || dayNum == 3 || dayNum == 4) {
                period1.setText(p1 + p1Time);
                period2.setText(p2 + p2Time);
                period3.setText(p3 + p3Time);
                period4.setText(p4 + p4Time);
                day.setText("Day " + dayNum);
            } else if (dayNum == 9) {
                day.setText("It's a Holiday!");
            }
            if (dateChanged) {
                changeDate.setText("Today");
            }
            if (!dateChanged) {
                changeDate.setText("Change Date");
            }
        }

      public void update() { //updates schedule and

            if (!dateChanged) {
                month = c.get(Calendar.MONTH) + 1; //calendar retrieves month off by 1
                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                weekday = c.get(Calendar.DAY_OF_WEEK); //will eventually be used to determine whether it is a weekend or not
                dayNum = schoolYear[month - 1][dayOfMonth];
            }
            if (dateChanged) {
                dayNum = schoolYear[monthInput - 1][dayInput];
            }
            if (dayNum == 1) {
                p1 = "Comm. Tech";
                p2 = "Gym";
                p3 = "English";
                p4 = "Instrumental";
            }
            if (dayNum == 2) {
                p1 = "Science";
                p2 = "Software";
                p3 = "French";
                p4 = "Math";
            }
            if (dayNum == 3) {
                p1 = "Instrumental";
                p2 = "Gym";
                p3 = "English";
                p4 = "Comm. Tech";
            }
            if (dayNum == 4) {
                p1 = "Math";
                p2 = "Software";
                p3 = "French";
                p4 = "Science";
            }
            if (dayNum == 9) {
                p1 = "H";
                p2 = "H";
                p3 = "H";
                p4 = "H";
            }

            p1Time = "  (8:15 AM - 9:30 AM)";
            p2Time = "  (9:35 AM - 10:50 AM)";
            p3Time = "  (11:15 AM - 12:30 PM)";
            p4Time = "  (1:25 PM - 2:40 PM)";

        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

}