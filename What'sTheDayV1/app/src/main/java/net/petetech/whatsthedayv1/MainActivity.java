package net.petetech.whatsthedayv1;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    final static int[][] schoolYear = {
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
    int timeFrame = 5; //will be used to access time frame row of the array
    boolean dateChanged; //storing whether user has changed the date or not
    boolean prefsAvailable; //true if there is a preferences file


    String[][] schedule = new String[6][6]; //add extra space in array to store class time frames
    private TextView period1;
    private TextView period2;
    private TextView period3;
    private TextView period4;
    private TextView day; //textView to display the dayNum
    private TextView selectedDate;
    private TextView warning;
    private TextView warningText;
    Button changeDate; //button to open second activity to change the date displayed
    File prefs = new File("/data/data/net.petetech.whatsthedayv1/shared_prefs/Schedule.xml");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefCheck();
        if (!prefsAvailable) {
            Intent setup = new Intent(this, Setup.class);
            startActivity(setup);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("What's The Day?");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        period1 = (TextView) findViewById(R.id.p1View);
        period2 = (TextView) findViewById(R.id.p2View);
        period3 = (TextView) findViewById(R.id.p3View);
        period4 = (TextView) findViewById(R.id.p4View);
        selectedDate = (TextView) findViewById(R.id.date);
        warning = (TextView) findViewById(R.id.warn);
        warningText = (TextView) findViewById(R.id.warnText);
        day = (TextView) findViewById(R.id.dayView);
        changeDate = (Button) findViewById(R.id.cd);
        prefCheck();
        update();
        selectedDate.setText(getMonth(month) + " " + dayOfMonth + ", " + cYear);
        changeDate.setText("Change Date");

        changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //shows date picker when change date is pressed
                if (!dateChanged) {
                    new DatePickerDialog(MainActivity.this, listener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                } else if (dateChanged) {
                    dayNum = schoolYear[month - 1][dayOfMonth];
                    Toast.makeText(MainActivity.this, "Date Set To: " + getMonth(month) + " " + dayOfMonth + ", " + cYear, Toast.LENGTH_SHORT).show(); //displays current date when user presses today button
                    update();
                    drawSchedule();
                    selectedDate.setText(getMonth(month) + " " + dayOfMonth + ", " + cYear);
                    changeDate.setText("Change Date");
                    dateChanged = false; //set bool back to false so date dialog comes back if user would use change date button again
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
            dayNum = schoolYear[monthInput - 1][dayInput]; //get new dayNum based on the selected date
            update();
            drawSchedule();
            Toast.makeText(MainActivity.this, "Date Set To: " + getMonth(monthOfYear + 1) + " " + dayInput + ", " + year, Toast.LENGTH_SHORT).show();
            selectedDate.setText(getMonth(monthOfYear + 1) + " " + dayInput + ", " + year);
        }
    };


    public void drawSchedule() { //function that updates the schedule on screen revolving around the change date button
        if (dayNum == 1 || dayNum == 2 || dayNum == 3 || dayNum == 4) {
            period1.setText("P1: " + schedule[dayNum][1] + schedule[timeFrame][1]);
            period2.setText("P2: " + schedule[dayNum][2] + schedule[timeFrame][2]);
            period3.setText("P3: " + schedule[dayNum][3] + schedule[timeFrame][3]);
            period4.setText("P4: " + schedule[dayNum][4] + schedule[timeFrame][4]);
            day.setText("Day " + dayNum);
        } else if (dayNum == 9) {
            day.setText("It's a Holiday!");
            period1.setText(null);
            period2.setText(null);
            period3.setText(null);
            period4.setText(null);
        }
        if (!prefsAvailable) {
            warning.setText("WARNING:"); // display warning if the user has not set their own schedule
            warningText.setText("This is the default schedule! Please set your own schedule in the settings menu");
        } else if (prefsAvailable) {
            warning.setText(null); // remove text if the schedule is set
            warningText.setText(null);
        }
    }

    String getMonth(int m) {
        if (m == 1) {
            return "January";
        }
        if (m == 2) {
            return "February";
        }
        if (m == 3) {
            return "March";
        }
        if (m == 4) {
            return "April";
        }
        if (m == 5) {
            return "May";
        }
        if (m == 6) {
            return "June";
        }
        if (m == 7) {
            return "July";
        }
        if (m == 8) {
            return "August";
        }
        if (m == 9) {
            return "September";
        }
        if (m == 10) {
            return "October";
        }
        if (m == 11) {
            return "November";
        }
        if (m == 12) {
            return "December";
        }
        return null;
    }

    public void update() { //updates schedule

        if (!dateChanged) {
            month = c.get(Calendar.MONTH) + 1; //calendar retrieves month off by 1
            dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            weekday = c.get(Calendar.DAY_OF_WEEK); //will eventually be used to determine whether it is a weekend or not
            cYear = c.get(Calendar.YEAR);
            dayNum = schoolYear[month - 1][dayOfMonth];
        }
        if (!prefsAvailable) {
            if (dayNum == 1) { //use my schedule if the user has not set their own
                schedule[dayNum][1] = "Comm. Tech";
                schedule[dayNum][2] = "Gym";
                schedule[dayNum][3] = "English";
                schedule[dayNum][4] = "Instrumental";
            }
            if (dayNum == 2) {
                schedule[dayNum][1] = "Science";
                schedule[dayNum][2] = "Software";
                schedule[dayNum][3] = "French";
                schedule[dayNum][4] = "Math";
            }
            if (dayNum == 3) {
                schedule[dayNum][1] = "Instrumental";
                schedule[dayNum][2] = "Gym";
                schedule[dayNum][3] = "English";
                schedule[dayNum][4] = "Comm. Tech";
            }
            if (dayNum == 4) {
                schedule[dayNum][1] = "Math";
                schedule[dayNum][2] = "Software";
                schedule[dayNum][3] = "French";
                schedule[dayNum][4] = "Science";
            }
        }
        if (prefsAvailable) {
            SharedPreferences S = getSharedPreferences("Schedule", Context.MODE_PRIVATE);
            //update schedule with contents of shared prefs file set by user
            // day 1
            schedule[1][1] = S.getString("D1P1", "");
            schedule[1][2] = S.getString("D1P2", "");
            schedule[1][3] = S.getString("D1P3", "");
            schedule[1][4] = S.getString("D1P4", "");
            // day 2
            schedule[2][1] = S.getString("D2P1", "");
            schedule[2][2] = S.getString("D2P2", "");
            schedule[2][3] = S.getString("D2P3", "");
            schedule[2][4] = S.getString("D2P4", "");
            // day 3
            schedule[3][1] = S.getString("D3P1", "");
            schedule[3][2] = S.getString("D3P2", "");
            schedule[3][3] = S.getString("D3P3", "");
            schedule[3][4] = S.getString("D3P4", "");
            // day 4
            schedule[4][1] = S.getString("D4P1", "");
            schedule[4][2] = S.getString("D4P2", "");
            schedule[4][3] = S.getString("D4P3", "");
            schedule[4][4] = S.getString("D4P4", "");
        }
        //setup time frame strings in array
        schedule[timeFrame][1] = "  (8:15 AM - 9:30 AM)";
        schedule[timeFrame][2] = "  (9:35 AM - 10:50 AM)";
        schedule[timeFrame][3] = "  (11:15 AM - 12:30 PM)";
        schedule[timeFrame][4] = "  (1:25 PM - 2:40 PM)";
    }

    private void prefCheck() { //checks if a shared preferences has been created
        //check for shared prefs file
        if (prefs.exists()) {
            prefsAvailable = true;
        } else if (!prefs.exists()) {
            prefsAvailable = false;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        // code is run when app is resumed
        update();
        drawSchedule();
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
        //int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch(item.getItemId()) { //allows me to add more cases for more items in the options menu
            case R.id.action_settings:
                Intent settings = new Intent("net.petetech.whatsthedayv1.Settings");
                startActivity(settings);
                break;
            case R.id.action_about:
                Intent about = new Intent(this, About.class);
                startActivity(about);
                break;
                default:
                    return super.onOptionsItemSelected(item);
        }
        return true;
    }
}