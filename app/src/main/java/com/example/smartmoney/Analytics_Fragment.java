package com.example.smartmoney;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Analytics_Fragment extends Fragment {

    Button incomebar,incomepie,expansebar,expansepie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_analytics_, container, false);


        incomebar=view.findViewById(R.id.btn_income_bar);
        incomepie=view.findViewById(R.id.btn_income_pie);
        expansebar=view.findViewById(R.id.btn_expanse_bar);
        expansepie=view.findViewById(R.id.btn_expanse_pie);

        incomebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Income_barchart_Activity.class));
            }
        });

        incomepie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Income_piechart_Activity.class));
            }
        });

        expansebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Expanse_barchart_Activity.class));
            }
        });

        expansepie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Expanse_piechart_Activity.class));
            }
        });
        return view;
    }
}