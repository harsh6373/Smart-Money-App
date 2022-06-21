package com.example.smartmoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard_Activity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.dashBoard);


    }
    Dashboard_fragment dashboard_fragment = new Dashboard_fragment();
    Income_fragment income_fragment = new Income_fragment();
    Expense_fragment expense_fragment = new Expense_fragment();

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
}