package com.example.kartik.tutorfinder.LoginRegister;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.kartik.tutorfinder.R;
import com.example.kartik.tutorfinder.WelcomeStudent;

import org.json.JSONException;
import org.json.JSONObject;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mPager ;
    private int[] layouts={R.layout.first_slide,R.layout.second_slide,R.layout.third_slide,R.layout.first_slide};
    private MpagerAdapter mpagerAdapter ;
    private LinearLayout Dots_Layout;
    private ImageView[] dots;
    private Button BnNext,BnSkip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //****************************************************************************************************//
        final PreferenceManager preferenceManager = new PreferenceManager(WelcomeActivity.this, getString(R.string.login_preference));
        final ConnectionError connectionError = new ConnectionError(WelcomeActivity.this);
        Boolean preferenceStatus = preferenceManager.checkLoginPreference();

        //*****************************************************888
        Boolean stts = new PreferenceManager(this, this.getString(R.string.my_preference)).checkPreference();
            if (stts) {
                if (preferenceStatus) {
                   onReqRes(preferenceManager.username, preferenceManager.password, preferenceManager, connectionError, WelcomeActivity.this);
                }
                else {
                    System.out.println("STATUS" + stts);
                    loadHome();
                }
            }


                if (!stts) {



                    if (Build.VERSION.SDK_INT >= 19) {
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                    } else {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    }
                    setContentView(R.layout.activity_welcome);
                    mPager = findViewById(R.id.viewPager);
                    mpagerAdapter = new MpagerAdapter(layouts, this);
                    mPager.setAdapter(mpagerAdapter);
                    Dots_Layout = (LinearLayout) findViewById(R.id.dotsLayout);
                    BnNext = (Button) findViewById(R.id.bnNext);
                    BnSkip = (Button) findViewById(R.id.bnSkip);
                    BnNext.setOnClickListener(this);
                    BnSkip.setOnClickListener(this);
                    createDots(0);
                    mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            createDots(position);
                            if (position == layouts.length - 1) {
                                BnNext.setText("START");
                                BnNext.setVisibility(View.VISIBLE);
                            } else {
                                BnNext.setText("NEXT");
                                BnSkip.setVisibility(View.VISIBLE);
                            }

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });


            }
    }
    private void createDots(int current_position)
    {

        if(Dots_Layout!=null)
        {
            Dots_Layout.removeAllViews();
            dots= new ImageView[layouts.length];
            for(int i=0;i<layouts.length;i++)
            {
                dots[i]=new ImageView(this);
                if(i==current_position)
                {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));
                }
                else
                {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.default_dots));
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(4,0,4,0);
                Dots_Layout.addView(dots[i],params);

            }
        }
    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.bnNext:
                loadNextSlide();
                break;
            case R.id.bnSkip:
                loadHome();
                new PreferenceManager(this,this.getString(R.string.my_preference)).writePreference();
                break;
        }
    }

    private void loadHome()
    {
        startActivity(new Intent(this,LoginActivity.class));
        finish();

    }
    private void loadNextSlide()
    {
        int next_slide=mPager.getCurrentItem()+1;
        if(next_slide<layouts.length)
        {
            mPager.setCurrentItem(next_slide);
        }
        else
        {
            loadHome();
            new PreferenceManager(this,this.getString(R.string.my_preference)).writePreference();
        }
    }




    //****************888
    //Method to handle the Request and Response
    ////**************************************************************************************************************************************
    public void onReqRes(String Email,String Pass,PreferenceManager preferenceManager,ConnectionError connectionError, Context ctx)
    {
        final Context context=ctx;
        final  PreferenceManager preferenceManager1=preferenceManager;
        final ConnectionError connectionError1 = connectionError;

        final String email = Email;
        final String password = Pass;

        if (connectionError.checkInternetConenction())
            if (email.equals("") && password.equals("")) {
                displayMessage(context,"Email or password can not be empty.");
            }
            else {
                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = null;
                            try {
                                jsonResponse = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            boolean success = false;

                            if(jsonResponse!=null) {
                                success = jsonResponse.getBoolean("success");
                                if (success) {
                                    preferenceManager1.writeLoginPreference(email, password);
                                    String name = jsonResponse.getString("name");
                                    String gender = jsonResponse.getString("gender");
                                    int age = jsonResponse.getInt("age");
                                    String mobile = jsonResponse.getString("mobile");
                                    String address = jsonResponse.getString("address");

                                    Intent intent = new Intent(context, WelcomeStudent.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    intent.putExtra("age", age);
                                    intent.putExtra("gender", gender);
                                    intent.putExtra("password", password);
                                    intent.putExtra("mobile", mobile);
                                    intent.putExtra("address", address);
                                    context.startActivity(intent);
                                    finish();
                                    //All these info are being sent to the Welcome page
                                    // because WelcomeStudent.java is making any server request, instead request are made by fragments.
                                    //but email and name is reqired to the header of drawer layout which is in the WelcomeStudent.java
                                    //***************************************************************
                                    displayMessage(context, "Login Successful");

                                }
                            }
                            else if(jsonResponse==null){
                                AlertDialog.Builder builder =new AlertDialog.Builder(context);
                                builder.setMessage("Something went wrong!").setNegativeButton("ok",null)
                                .create().show();
                                displayMessage(context,"Login Failed!");
                            }
                            else {
                                displayMessage(context,"Login Failed!");
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Wrong Username or Password!")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        connectionError1.volleyErrorHandling(error);
                    }
                };

                LoginRequest loginRequest = new LoginRequest(email, password, responseListener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(loginRequest);
            }
    }

    //Somewhere that has access to a context
    public void displayMessage(Context context,String toastString) {
        Toast.makeText(context, toastString, Toast.LENGTH_LONG).show();
    }


}
