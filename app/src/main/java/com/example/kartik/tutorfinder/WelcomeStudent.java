package com.example.kartik.tutorfinder;

import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.kartik.tutorfinder.LoginRegister.LoginActivity;
import com.example.kartik.tutorfinder.LoginRegister.PreferenceManager;

public class WelcomeStudent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
        TextView tvWelcomeMsg ;
    Toolbar toolbar;
    TextView username,u_email;
ViewPager viewPager;
ViewPagerAdapter viewPagerAdapter ;
TabLayout tabLayout;
FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_student);
        Intent intent = getIntent();
        toolbar=(Toolbar) findViewById(R.id.toolbar) ;
        NavigationView nv=(NavigationView)findViewById(R.id.navigation_view);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        setSupportActionBar(toolbar);
        String name = intent.getStringExtra("name");
        int age = intent.getIntExtra("age", -1);
        String email = intent.getStringExtra("email");
        String gender = intent.getStringExtra("gender");
        String password = intent.getStringExtra("password");
        String mobile = intent.getStringExtra("mobile");
        String address = intent.getStringExtra("address");
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = inflater.inflate(R.layout.navigation_drawer_header, null); //accessing the textviews of header file
        username = (TextView)vi.findViewById(R.id.textView1);
        u_email = (TextView)vi.findViewById(R.id.textView2);
        username.setText(name);
        u_email.setText(email);
        nv.addHeaderView(vi);

        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.open,R.string.close);
        mDrawerLayout.setDrawerListener(mToggle);
        nv.setNavigationItemSelectedListener(this);

        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new TutorTab(),"Tutors");
        viewPagerAdapter.addFragments(new StudTab(),"Students");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }





    @Override
    protected   void onPostCreate(Bundle saveInstanceState){
        super.onPostCreate(saveInstanceState);
        mToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_to_tutors) {



        } else if (id == R.id.nav_home) {


        }
        else if (id==R.id.nav_logout)
        {
            PreferenceManager pm = new PreferenceManager(this,getString(R.string.login_preference));
            pm.checkLoginPreference();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        return true;
    }
}
