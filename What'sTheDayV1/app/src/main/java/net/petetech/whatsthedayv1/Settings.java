package net.petetech.whatsthedayv1;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.ActionBar;


public class Settings extends AppCompatActivity {

    private Button changeS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings"); //set title of activity to settings to be shown at the top of app
        //show back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        changeS = (Button) findViewById(R.id.changeS);
        onChangePressed();
    }

    void onChangePressed() { //runs when the change schedule button is pressed
        changeS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //code runs when button is pressed
                Intent setDay = new Intent("net.petetech.whatsthedayv1.SetDay");
                startActivity(setDay);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this); //assuming both activities are within the same task letting this navigation happen
        }

        return super.onOptionsItemSelected(item);
    }
}
