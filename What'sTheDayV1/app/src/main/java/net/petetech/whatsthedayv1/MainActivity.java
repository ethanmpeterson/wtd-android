package net.petetech.whatsthedayv1;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


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
    int cYear; //stores current year
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

    //create TextViews for each school class

    private TextView period1;
    private TextView period2;
    private TextView period3;
    private TextView period4;
    private TextView day; //textView to display the dayNum
    Button changeDate; //button to open second activity to change the date displayed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("What's The Day?");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        period1 = (TextView) findViewById(R.id.p1View);
        period2 = (TextView) findViewById(R.id.p2View);
        period3 = (TextView) findViewById(R.id.p3View);
        period4 = (TextView) findViewById(R.id.p4View);
        day = (TextView) findViewById(R.id.dayView);
        changeDate = (Button) findViewById(R.id.cd);
        update();
        changeDate.setText("Change Date");
        changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //shows date picker when change date is pressed
                if (!dateChanged) {
                    new DatePickerDialog(MainActivity.this, listener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                } else if (dateChanged) {
                    dayNum = schoolYear[month - 1][dayOfMonth];
                    Toast.makeText(MainActivity.this, "Date Set To: " + getMonth() + " " + dayOfMonth + ", " + cYear, Toast.LENGTH_SHORT).show(); //displays current date when user presses today button
                    drawSchedule();
                    changeDate.setText("Change Date");
                    dateChanged = false; //set bool back to false so date dialog comes back if user would
                }
            }
        });
        update();
        drawSchedule();
    }
    DatePickerDialog.OnDateSetListener listener = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) { //code runs when user sets the date
            dayInput = dayOfMonth;
            monthInput = monthOfYear + 1;
            yearInput = year;
            dateChanged = true;
            changeDate.setText("Today");
            update();
            drawSchedule();
            Toast.makeText(MainActivity.this, "Date Set To: " + getMonth() + " " + dayInput + ", " + year, Toast.LENGTH_SHORT).show();
        }
    };


        public void drawSchedule() { //function that updates the schedule on screen revolving around the change date button
            if (dayNum == 1 || dayNum == 2 || dayNum == 3 || dayNum == 4) {
                period1.setText(p1 + p1Time);
                period2.setText(p2 + p2Time);
                period3.setText(p3 + p3Time);
                period4.setText(p4 + p4Time);
                day.setText("Day " + dayNum);
            } else if (dayNum == 9) {
                day.setText("It's a Holiday!");
                period1.setText(null);
                period2.setText(null);
                period3.setText(null);
                period4.setText(null);
            }
        }

        String getMonth() {
            if (month == 1 || monthInput == 1) {
                return "January";
            }
            if (month == 2 || monthInput == 2) {
                return "February";
            }
            if (month == 3 || monthInput == 3) {
                return "March";
            }
            if (month == 4 || monthInput == 4) {
                return "April";
            }
            if (month == 5 || monthInput == 5) {
                return "May";
            }
            if (month == 6 || monthInput == 6) {
                return "June";
            }
            if (month == 7 || monthInput == 7) {
                return "July";
            }
            if (month == 8 || monthInput == 8) {
                return "August";
            }
            if (month == 9 || monthInput == 9) {
                return "September";
            }
            if (month == 10 || monthInput == 10) {
                return "October";
            }
            if (month == 11 || monthInput == 10) {
                return "November";
            }
            if (month == 12 || monthInput == 12) {
                return "December";
            }
            return null;
        }

        public void update() { //updates schedule and

            if (!dateChanged) {
                month = c.get(Calendar.MONTH) + 1; //calendar retrieves month off by 1
                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                weekday = c.get(Calendar.DAY_OF_WEEK); //will eventually be used to determine whether it is a weekend or not
                cYear = c.get(Calendar.YEAR);
                dayNum = schoolYear[month - 1][dayOfMonth];
            }
            if (dateChanged) {
                month = c.get(Calendar.MONTH) + 1;
                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                weekday = c.get(Calendar.DAY_OF_WEEK);
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
            if (id == R.id.action_settings) { //code runs if settings button is pressed
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
}