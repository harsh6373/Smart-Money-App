package com.example.smartmoney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Expanse_barchart_Activity extends AppCompatActivity {

    Toolbar toolbar;
   private BarChart barChart;
   private ArrayList<BarEntry> barEntryArrayList;
    private ArrayList<String> strings;
    private ArrayList<Data> typeandamount_arraylist ;
    private FirebaseAuth mAuth;
    private DatabaseReference mexpenseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanse_barchart);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Smart Money");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        barChart = findViewById(R.id.expanse_barChart);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser =mAuth.getCurrentUser();
        String uid = mUser.getUid();
        mexpenseDatabase = FirebaseDatabase.getInstance().getReference().child("Expense_Data").child(uid);
        barEntryArrayList = new ArrayList<>();
        strings = new ArrayList<>();


        // typeandamount_arraylist.clear();

        mexpenseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                typeandamount_arraylist =  new ArrayList<>();
                for (DataSnapshot mysnapshot: snapshot.getChildren()){

                    Data data = mysnapshot.getValue(Data.class);
                    typeandamount_arraylist.add(new Data(data.getType(),data.getAmount()));
                }
                for (int i=0;i<typeandamount_arraylist.size();i++){
                    String type=typeandamount_arraylist.get(i).getType();
                    int amount = typeandamount_arraylist.get(i).getAmount();
                    barEntryArrayList.add(new BarEntry(i,amount));
                    // Log.i(TAG, "value of "+i+amount);
                    strings.add(type);
                }

                showchart(barEntryArrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    private void showchart(ArrayList<BarEntry> barEntryArrayList) {
        BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"Expense Data");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLUE);
        barDataSet.setValueTextSize(12f);
        Description description = new Description();
        description.setText("Expenses");
        barChart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.animateY(2500);
        barChart.invalidate();
        XAxis xAxis=barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(strings));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(strings.size());
        xAxis.setLabelRotationAngle(270);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}