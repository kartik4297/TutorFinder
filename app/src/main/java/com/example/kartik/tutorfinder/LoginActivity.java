package com.example.kartik.tutorfinder;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

  public EditText et_email,et_pass ;
  public TextView tv_reg;
  public  Button bLogin ;
        //Somewhere that has access to a context
    public void displayMessage(String toastString){
        Toast.makeText(this, toastString, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent=getIntent();
        et_email = (EditText) findViewById(R.id.et_email);
        et_pass = (EditText) findViewById(R.id.et_pass);
        tv_reg = (TextView) findViewById(R.id.tv_reg);
        bLogin = (Button) findViewById(R.id.login_btn);
        //fetching info from register page after successful registration
        if(intent!=null) {
            String Email=intent.getStringExtra("email");
            et_email.setText(Email);
            String PASS=intent.getStringExtra("password");
            et_pass.setText(PASS);

        }


        tv_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = et_email.getText().toString();
                final String password = et_pass.getText().toString();
                if (email!="" &&  password!="") {
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
                                    String name = jsonResponse.getString("name");
                                    String gender = jsonResponse.getString("gender");
                                    int age = jsonResponse.getInt("age");
                                    String mobile = jsonResponse.getString("mobile");
                                    String address = jsonResponse.getString("address");

                                    Intent intent = new Intent(LoginActivity.this, WelcomeStudent.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    intent.putExtra("age", age);
                                    intent.putExtra("gender", gender);
                                    intent.putExtra("password", password);
                                    intent.putExtra("mobile", mobile);
                                    intent.putExtra("address", address);
                                    LoginActivity.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("Login Failed")
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
                            String json = null;
                            NetworkResponse response = error.networkResponse;
                            int statusCode = response.statusCode;
                             System.out.println("ErrorCode.........................." +statusCode);
                            if (response != null && response.data != null) {
                                json = new String(response.data);
                                   if(json != null) displayMessage(json);
                                switch (statusCode) {
                                    case 404:
                                        Toast.makeText(LoginActivity.this, "incorrect URL requested.\nERROR_CODE=404", Toast.LENGTH_SHORT).show();
                                        break;
                                        default: displayMessage("invalid error");

                                }

                                //Additional cases
                            }


                        }
                    };


                    LoginRequest loginRequest = new LoginRequest(email, password, responseListener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }
                else
                {
                    displayMessage("Enter email and password");
                }
            }
        });

    }
}
