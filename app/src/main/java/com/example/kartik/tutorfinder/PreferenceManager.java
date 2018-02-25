package com.example.kartik.tutorfinder;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kartik on 06-12-2017.
 */

public class PreferenceManager {

    private Context context;
    private SharedPreferences sharedPreferences;
    public PreferenceManager(Context context)
    {
        this.context= context;
        getSharedPreference();
    }
    private  void getSharedPreference()
    {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_preference),Context.MODE_PRIVATE);
    }
    public void writePreference()
    {
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(context.getString(R.string.my_preference_key),"INIT_OK");
         editor.commit();
    }
    public boolean checkPreference()
    {

        boolean status=false;
        boolean s=sharedPreferences.getString(context.getString(R.string.my_preference_key),"null").equals("null");
        System.out.println("STATUS:  "+s);
        if(sharedPreferences.getString(context.getString(R.string.my_preference_key),"null").equals("null"))
        {

            status =false;
        }
        else
        {
           status = true;
        }
        return status;
    }
    public void clearPreference()
    {
        sharedPreferences.edit().clear().commit();
    }
}
