package com.example.smartmoney;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Dashboard_fragment extends Fragment {

    private TextView totalIncome_txt;
    private TextView totalExpense_txt;

    FirebaseAuth mAuth;
    DatabaseReference mincomeDatabase;
    DatabaseReference mexpenseDatabase;

    public Dashboard_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_dashboard_fragment, container, false);

        totalIncome_txt = myview.findViewById(R.id.dashboard_income_sum_txt);
        totalExpense_txt = myview.findViewById(R.id.dashboard_expense_sum_txt);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        mincomeDatabase = FirebaseDatabase.getInstance().getReference().child("Income_Data").child(uid);
        mexpenseDatabase = FirebaseDatabase.getInstance().getReference().child("Expense_Data").child(uid);


        mincomeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int totalsum = 0;

                for (DataSnapshot mysnapshot : snapshot.getChildren()) {
                    Data data = mysnapshot.getValue(Data.class);

                    totalsum += data.getAmount();

                    String s_total_income = String.valueOf(totalsum);

                    totalIncome_txt.setText(s_total_income);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        mexpenseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalsum_expanse = 0;

                for (DataSnapshot mysnapshot : snapshot.getChildren()) {
                    Data data = mysnapshot.getValue(Data.class);

                    totalsum_expanse += data.getAmount();

                    String s_expanse_total = String.valueOf(totalsum_expanse);

                    totalExpense_txt.setText(s_expanse_total);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return myview;
    }
}