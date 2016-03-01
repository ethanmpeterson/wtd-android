package net.petetech.whatsthedayv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Setup extends AppCompatActivity {

    private Button cont; // continue button in setup wizard
    Intent setDay = new Intent(this, SetDay.class); // Intent linking to existing set day wizard from the settings menu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        setTitle("Welcome");
        cont = (Button) findViewById(R.id.cont);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(setDay);
            }
        });
    }
}
