package com.example.smartmoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Dashboard_Activity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Smart Money");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Dashboard_fragment dashboard_fragment = new Dashboard_fragment();
        Income_fragment income_fragment = new Income_fragment();
        Expense_fragment expense_fragment = new Expense_fragment();
        Analytics_Fragment analytics_fragment =new Analytics_Fragment();
        Profile_Fragment profile_fragment = new Profile_Fragment();
        About_Fragment about_fragment=new About_Fragment();
        FeedbackFragment feedbackFragment = new FeedbackFragment();



        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.dashBoard);
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, dashboard_fragment).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dashBoard:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, dashboard_fragment).commit();
                        return true;

                    case R.id.income:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, income_fragment).commit();
                        return true;

                    case R.id.expense:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, expense_fragment).commit();
                        return true;
                }
                return false;
            }
        });



        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        if(drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }else{
            super.onBackPressed();
        }
    }

    public void displaySelectedListener(int itemId){
        Fragment fragment = null;
        switch (itemId){
            case R.id.dashBoard_menu:
                fragment = new Dashboard_fragment();
                bottomNavigationView.setVisibility(View.VISIBLE);
                break;

            case R.id.analytics_menu:
                fragment = new Analytics_Fragment();
                bottomNavigationView.setVisibility(View.INVISIBLE);
                break;

            case R.id.Profile_menu:
                fragment=new Profile_Fragment();
                bottomNavigationView.setVisibility(View.INVISIBLE);
                break;

            case R.id.logout_menu:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;

            case R.id.about_menu:
                fragment=new About_Fragment();
                bottomNavigationView.setVisibility(View.INVISIBLE);
                break;

            case R.id.feedback_menu:
                fragment =new FeedbackFragment();
                bottomNavigationView.setVisibility(View.INVISIBLE);
                break;
        }
        if (fragment!=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flFragment,fragment);
            ft.commit();
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        displaySelectedListener(item.getItemId());
        return true;
    }
}