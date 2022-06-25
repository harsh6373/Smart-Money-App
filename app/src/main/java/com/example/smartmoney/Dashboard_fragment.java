package com.example.smartmoney;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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


    private RecyclerView recyclerView_income;
    private RecyclerView recyclerView_expanse;

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

        recyclerView_income = myview.findViewById(R.id.dashboard_recycleview_income);
        recyclerView_expanse = myview.findViewById(R.id.dashboard_recycleview_expanse);

        mincomeDatabase = FirebaseDatabase.getInstance().getReference().child("Income_Data").child(uid);
        mexpenseDatabase = FirebaseDatabase.getInstance().getReference().child("Expense_Data").child(uid);

        mincomeDatabase.keepSynced(true);
        mexpenseDatabase.keepSynced(true);

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


        LinearLayoutManager layoutManager_income = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        layoutManager_income.setStackFromEnd(true);
        layoutManager_income.setReverseLayout(true);
        recyclerView_income.setHasFixedSize(true);
        recyclerView_income.setLayoutManager(layoutManager_income);


        LinearLayoutManager layoutManager_expanse = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager_expanse.setStackFromEnd(true);
        layoutManager_expanse.setReverseLayout(true);
        recyclerView_expanse.setHasFixedSize(true);
        recyclerView_expanse.setLayoutManager(layoutManager_expanse);


        return myview;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mincomeDatabase, Data.class)
                .build();

        FirebaseRecyclerAdapter<Data,IncomeViewHolder> income_adapter = new FirebaseRecyclerAdapter<Data, IncomeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull IncomeViewHolder holder, int position, @NonNull Data model) {
                holder.setIncomeType(model.getType());
                holder.setIncomeAmount(model.getAmount());
                holder.setIncomeDate(model.getDate());

            }
            @NonNull
            @Override
            public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view_income = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_income_recycler_data, parent, false);
                return new IncomeViewHolder(view_income);
            }
        };

        FirebaseRecyclerOptions<Data> options_expanse = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mexpenseDatabase, Data.class)
                .build();


        FirebaseRecyclerAdapter<Data,ExpanseViewHolder> expanse_adapter = new FirebaseRecyclerAdapter<Data, ExpanseViewHolder>(options_expanse) {
            @Override
            protected void onBindViewHolder(@NonNull ExpanseViewHolder holder, int position, @NonNull Data model) {
                holder.setExpanseType(model.getType());
                holder.setExpanseAmount(model.getAmount());
                holder.setExpanseDate(model.getDate());
            }

            @NonNull
            @Override
            public ExpanseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view_expanse = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_expanse_recycler_data, parent, false);
                return new ExpanseViewHolder(view_expanse);
            }
        };
        recyclerView_income.setAdapter(income_adapter);
       income_adapter.startListening();


        recyclerView_expanse.setAdapter(expanse_adapter);
        expanse_adapter.startListening();
    }


    public static class ExpanseViewHolder extends RecyclerView.ViewHolder{

        View mExpanseView;
        public ExpanseViewHolder(@NonNull View itemView) {
            super(itemView);
            mExpanseView=itemView;
        }

        public void setExpanseType(String type) {
            TextView mType_expanse = mExpanseView.findViewById(R.id.db_type_txt_expanse);
            mType_expanse.setText(type);
        }
        public void setExpanseAmount(int amount) {
            TextView mAmount_expanse = mExpanseView.findViewById(R.id.db_amount_txt_expanse);
            String stamount = String.valueOf(amount);
            mAmount_expanse.setText(stamount);
        }

        public void setExpanseDate(String date) {
            TextView mDate_expanse = mExpanseView.findViewById(R.id.db_date_txt_expanse);
            mDate_expanse.setText(date);
        }

    }



    public static class IncomeViewHolder extends RecyclerView.ViewHolder {

        View mIncomeView;

        public IncomeViewHolder(@NonNull View itemViewe) {
            super(itemViewe);
            mIncomeView = itemViewe;
        }

        public void setIncomeType(String type) {
            TextView mType_income = mIncomeView.findViewById(R.id.db_type_txt_income);
            mType_income.setText(type);
        }
        public void setIncomeAmount(int amount) {
            TextView mAmount_income = mIncomeView.findViewById(R.id.db_amount_txt_income);
            String stamount = String.valueOf(amount);
            mAmount_income.setText(stamount);
        }

        public void setIncomeDate(String date) {
            TextView mDate_income = mIncomeView.findViewById(R.id.db_date_txt_income);
            mDate_income.setText(date);
        }


    }
}