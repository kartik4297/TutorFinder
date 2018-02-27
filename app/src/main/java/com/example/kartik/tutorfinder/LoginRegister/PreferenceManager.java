package com.example.kartik.tutorfinder.LoginRegister;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.toolbox.StringRequest;
import com.example.kartik.tutorfinder.R;

/**
 * Created by kartik on 06-12-2017.
 */

public class PreferenceManager {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesLogin;
    public String username,password;

    public PreferenceManager(Context context, String prefName)
    {
        this.context= context;
        getSharedPreference(prefName);
    }
    private  void getSharedPreference(String preName)
    {
        if(preName.equals( "com.example.kartik.LoginRegister.IntroSliderPref"))
            sharedPreferences = context.getSharedPreferences(preName,Context.MODE_PRIVATE);
        if(preName.equals("com.example.kartik.LoginRegister.LoginPref"))
            sharedPreferencesLogin=context.getSharedPreferences(preName,Context.MODE_PRIVATE);
    }



    public void writePreference()
    {
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(context.getString(R.string.my_preference_key),"INIT_OK");
         editor.commit();
    }

    public void writeLoginPreference(String name, String pass)
    {
        SharedPreferences.Editor editor= sharedPreferencesLogin.edit();
        editor.putString(context.getString(R.string.login_preference_key1_uname),name);
        editor.putString(context.getString(R.string.login_preference_key2_pass),pass);
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

    public boolean checkLoginPreference()
    {

        boolean status=false;
        String s = sharedPreferencesLogin.getString(context.getString(R.string.login_preference_key1_uname),"null");
        boolean boolname=sharedPreferencesLogin.getString(context.getString(R.string.login_preference_key1_uname),"null").equals("null");
        boolean boolpass=sharedPreferencesLogin.getString(context.getString(R.string.login_preference_key2_pass),"null").equals("null");


        System.out.println("STATUS:  "+boolname +" " +boolpass);
        if(boolname || boolpass)
        {

            status =false;
        }
        else
        {
            status = true;
            username=sharedPreferencesLogin.getString(context.getString(R.string.login_preference_key1_uname),null);
            password=sharedPreferencesLogin.getString(context.getString(R.string.login_preference_key2_pass),null);
        }
        return status;
    }
        public void clearPreference()
    {
        sharedPreferences.edit().clear().commit();
    }
    public void clearLoginPreference(){ sharedPreferencesLogin.edit().clear().commit(); }

}

