package com.example.kartik.tutorfinder;



import android.app.AlertDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    public RadioGroup gender_grp;
    public RadioButton radio_btn;
    public EditText et_name,et_email,et_age,et_pass,et_confirm,et_mobile;
    public Button register_btn ;
    public TextView tv_login;
    Spinner sp_state,sp_city;

    String state,city;
    String[][] dists =new String[][]{{"Nicobar", "North and Middle Andaman","South Andaman"},{"Anantapur","Chittoor","East Godavari","Guntur","Kadapa","Krishna","Kurnool",
            "Prakasam","Sri Potti Sriramulu","Nellore","Srikakulam","Visakhapatnam","Vizianagaram","West Godavari"},{"Anjaw","Changlang","Dibang Valley","East Kameng","East Siang",
    "East Siang","Kamle","Kra Daadi","Kurung Kumey","Lohit","Longding","Lower Dibang Valley","Lower Siang","Lower Subansiri","Namsai","Papum Pare","Siang","Tawang","Tirap","Upper Siang","Upper Subansiri","West Kameng","West Siang"},
            {"Baksa","Barpeta","Bishwanath","Bongaigaon","Cachar","Charaideo","Chirang","Darrang","Dhemaji","Dhubri","Dhubri","Dibrugarh","Dima Hasao","Goalpara","Golaghat",
            "Hailakandi","Hojai","Jorhat","Kamrup","Kamrup Metropolitan","Karbi Anglong","Karimganj","Kokrajhar","Lakhimpur","Majuli","Morigaon","Nagaon","Nalbari",
                    "Sivasagar","Sonitpur","Tinsukia","Udalguri","West Karbi Anglong"},
            {"Araria","Arwal","Aurangabad","Banka","Begusarai","Bhagalpur","Bhojpur","Buxar","Darbhanga","East Champaran","Gaya","Gopalganj","Jamui","Jehanabad",
            "Kaimur","Katihar","Khagaria"," Kishanganj","Lakhisarai","Madhepura","Madhubani","Munger","Muzaffarpur","Nalanda","Nawada","Patna"},
            {"Chandīgarh"},
            {"Ahiwara","Akaltara","Ambikāpur","Bade Bacheli","Baikunthpur","Balood","Baloda Bajar","Balrampur","Bastar","Bametara","Bijapur","Bilaspur","Dantevada","Dhamtari","Durg","Gariaband","Jashpur","Kabirdham","Kanker","Kondagaon",
                    "Korba","Koriya","Mahasamund","Narayanpur","Raigarh","Raipur","Rajnandgaon","Sukma","Surajpur","Surguja"},
            {"Dadra and Nagar Haveli"},
            {"Daman","Diu"},
            {"Delhi","Jaffarpur Kalan","Qutabgarh","Ujwa","East Delhi"," New Delhi"," North Delhi","Shahdara","West Delhi","South Delhi"},
            {"Margao","North Goa","Panji","Ponda","South Gova","Mormugao","Sancoale"},
        {"Ahmadābād","Amreli","Anjār","Anklav","Bābra","Bhuj","Borsad","Chaklāsi","Chalthan","Chhota Udaipur","Dabhoi","Dahgām","Dākor","Dharampur","Dwārka","Gadhada","Gāndhīdhām","Gariadhar","Godhra","Hālol","Halvad","Hārij","Himatnagar","Īdar",
        "Jāfarābād","Jambusar","Kadi","Kheda","Lāthi","Limbdi","Lūnāvāda","Mānsa","Morvi","Mundra","Nadiād","Navsāri","Okha","Padra","Pālitāna","Porbandar","Rādhanpur","RajKot","Salāya","Surat","Sutrapada","Talala","Umreth","Vadodara"},
            };
    boolean bool_name,bool_email,bool_pass,bool_confirn,bool_mobile;
    ArrayAdapter<String> dataAdapter_dist ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        final boolean success;

        gender_grp=(RadioGroup)findViewById(R.id.radioGroup);
        et_name= (EditText)findViewById(R.id.et_name);


        et_age=(EditText)findViewById(R.id.et_age);
        et_email =(EditText)findViewById(R.id.et_email);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_confirm = (EditText) findViewById(R.id.et_confirm);
        et_mobile = (EditText)findViewById(R.id.et_mobile);
        sp_state = (Spinner) findViewById(R.id.sp_state);
        sp_city=(Spinner)findViewById(R.id.sp_city);
        tv_login =(TextView)findViewById(R.id.tv_login);
        register_btn = (Button)findViewById(R.id.reg_btn);
        et_confirm.setEnabled(false);
        et_confirm.setVisibility(View.GONE);



      //  et_confirm.setEnabled(false);
        // TextWatcher would let us check validation error on the fly
        et_name.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(et_name);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        et_email.addTextChangedListener(new TextWatcher() {
            @Override  public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override  public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override  public void afterTextChanged(Editable s) {
                 bool_email=Validation.isEmailAddress(et_email,true);

            }
        });
        et_pass.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                bool_pass=Validation.isPassword(et_pass,true);
                 if(bool_pass)
                 {
                     et_confirm.setVisibility(View.VISIBLE);
                     et_confirm.setEnabled(true);

                 }
                 else {
                     et_confirm.setVisibility(View.GONE);
                     et_confirm.setEnabled(false);

                 }

            }
        });



            et_confirm.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                     }
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) { bool_confirn=Validation.isConfirmPassword(s,et_confirm,et_pass, true); }
                @Override public void afterTextChanged(Editable s) {
                }
            });
//State spinner list
        ArrayAdapter dataAdapter_state = ArrayAdapter.createFromResource(this,R.array.india_states,android.R.layout.simple_spinner_dropdown_item);
        dataAdapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_state.setAdapter(dataAdapter_state);
        sp_state.setOnItemSelectedListener(this);
//city spinner itemSelect listener registration
        sp_city.setOnItemSelectedListener(this);
        register_btn.setOnClickListener(this);
        checkInternetConenction();
    }
    //submit method to vallidate the form on click of register button.

// Register Button Click method start
    @Override
    public void onClick(View view) {

        if(bool_email && bool_pass  && bool_confirn) {
            checkInternetConenction();
            String name = et_name.getText().toString();
            int selected_id = gender_grp.getCheckedRadioButtonId();
            radio_btn = (RadioButton) findViewById(selected_id);
            String gender = radio_btn.getText().toString();
            int age = Integer.parseInt(et_age.getText().toString());
            final String email = et_email.getText().toString();
            final String password = et_pass.getText().toString();
            String mobile = et_mobile.getText().toString();
            String address = city + " " + state;

            Response.Listener<String> responseListener = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = false,status=false;
                        success = jsonResponse.getBoolean("success");


                        if (success) {
                            Toast.makeText(RegisterActivity.this,"registered successfully.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("password", password);
                              RegisterActivity.this.startActivity(intent);


                        } else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("Register Failed")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };


            RegisterRequest registerRequest = new RegisterRequest(name, email, password, gender, age, address, mobile, responseListener);
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(registerRequest);
        }
        else {
            Toast.makeText(this,"Enter correct information above",Toast.LENGTH_SHORT).show();
        }

        }


    // Register Button Click method ends

    //Spinner  onNothinngSelected and onItemSelected metheods start
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       if(parent.equals(sp_state)){
           TextView tv = (TextView) view;
           state = tv.getText().toString();
           sp_city.setEnabled(false);
           dataAdapter_dist = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dists[position]);
           dataAdapter_dist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           sp_city.setAdapter(dataAdapter_dist);
           sp_city.setEnabled(true);
       }
       else if(parent.equals(sp_city))
       {
           TextView tv = (TextView) view;
           city = tv.getText().toString();
       }
    }
    //Spinner  onNothinngSelected and onItemSelected  metheods end

    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec
                =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED  ) {
            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

}
