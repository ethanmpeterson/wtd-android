package net.petetech.whatsthedayv1;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/* Public class to store global variables to be accessible from other activities */

public class Global extends Application {

    public void putSharedPrefs(String key, String val) {
        SharedPreferences s = getSharedPreferences("s", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = s.edit(); //create instance of the editor object allowing the preferences to be edited
        editor.putString(key, val);
        editor.commit();
    }

    public String getSharedPrefs(String key, String val) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return preferences.getString(key, val);
    }
}
