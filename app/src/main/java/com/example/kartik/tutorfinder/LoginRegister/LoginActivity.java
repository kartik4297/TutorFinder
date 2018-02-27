package com.example.kartik.tutorfinder.LoginRegister;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartik.tutorfinder.R;
import com.example.kartik.tutorfinder.WelcomeStudent;

import org.json.JSONException;
import org.json.JSONObject;
public class LoginActivity extends AppCompatActivity {
    public EditText et_email, et_pass;
    public TextView tv_reg;
    public Button bLogin;
public  String Email,Password;


    //Somewhere that has access to a context
    public void displayMessage(String toastString) {
        Toast.makeText(LoginActivity.this, toastString, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String pref=getString(R.string.login_preference);
        final PreferenceManager preferenceManager =new PreferenceManager(LoginActivity.this,pref);
        final ConnectionError connectionError = new ConnectionError(LoginActivity.this);
        //Boolean preferenceStatus = preferenceManager.checkLoginPreference();





            Intent intent = getIntent();
            et_email = (EditText) findViewById(R.id.et_email);
            et_pass = (EditText) findViewById(R.id.et_pass);
            tv_reg = (TextView) findViewById(R.id.tv_reg);
            bLogin = (Button) findViewById(R.id.login_btn);
            //fetching info from register page after successful registration
            if (intent != null) {
                String EMAIL = intent.getStringExtra("email");
                et_email.setText(EMAIL);
                String PASS = intent.getStringExtra("password");
                et_pass.setText(PASS);

            }

//text link to Registration
            tv_reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                    LoginActivity.this.startActivity(registerIntent);
                    finish();
                }
            });
            connectionError.checkInternetConenction();
            bLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Email = et_email.getText().toString();
                    Password = et_pass.getText().toString();
                    onReqRes(Email,Password,preferenceManager,connectionError, LoginActivity.this);
                }
            });


    }//on create() ends

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
                displayMessage("Email or password can not be empty.");
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
                            success = jsonResponse.getBoolean("success");
                            if (success) {
                                preferenceManager1.writeLoginPreference(email,password);
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
                                displayMessage("Login Successful");

                            } else {
                                displayMessage("Login Failed!");
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("wrong username or password")
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
    }

