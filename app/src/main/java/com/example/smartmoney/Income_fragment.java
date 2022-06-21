package com.example.smartmoney;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;



public class Income_fragment extends Fragment {

    EditText edt_income_type,edt_income_amount,edt_income_note;
    Button add_income;

    FirebaseAuth mAuth;
    DatabaseReference mincomeDatabase;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    RecyclerView recyclerView;




    public Income_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income_fragment, container, false);

        edt_income_type = view.findViewById(R.id.edt_income_type);
        edt_income_amount = view.findViewById(R.id.edt_income_amount);
        edt_income_note = view.findViewById(R.id.edt_income_note);
        add_income = view.findViewById(R.id.btn_add_income);



        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser =mAuth.getCurrentUser();
        String uid = mUser.getUid();

        mincomeDatabase = FirebaseDatabase.getInstance().getReference().child("Income_Data").child(uid);



        recyclerView = view.findViewById(R.id.recycleview_income);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);



        add_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String income_type = edt_income_type.getText().toString().trim();
                String income_amount = edt_income_amount.getText().toString().trim();
                String income_note = edt_income_note.getText().toString().trim();
                int amount = Integer.parseInt(income_amount);


                if(income_type.isEmpty())
                {
                    edt_income_type.setError("income type is empty");
                    edt_income_type.requestFocus();
                    return;
                }
                if(income_amount.isEmpty())
                {
                    edt_income_amount.setError("amount is empty");
                    edt_income_amount.requestFocus();
                    return;
                }
                if(income_note.isEmpty())
                {
                    edt_income_note.setError("note is empty");
                    edt_income_note.requestFocus();
                    return;
                }


                String id = mincomeDatabase.push().getKey();

                String mDate = DateFormat.getDateInstance().format(new Date());

                Data data = new Data(amount,income_type,income_note,id,mDate);

                mincomeDatabase.child(id).setValue(data);

                Toast.makeText(getActivity(), "Data added", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mincomeDatabase, Data.class)
                .build();

        FirebaseRecyclerAdapter<Data,MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Data model) {

                holder.setType(model.getType());
                holder.setNote(model.getNote());
                holder.setDate(model.getDate());
                holder.setAmount(model.getAmount());
            }
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_recycler_data, parent, false);
                return new MyViewHolder(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        private void setType(String type){
            TextView mType = mView.findViewById(R.id.type_txt_income);
            mType.setText(type);
        }
        private void setNote(String note){
            TextView mNote = mView.findViewById(R.id.note_txt_income);
            mNote.setText(note);
        }
        private void setDate(String date){
            TextView mDate = mView.findViewById(R.id.date_txt_income);
            mDate.setText(date);
        }
        private void setAmount(int amount){
            TextView mAmount = mView.findViewById(R.id.amount_txt_income);
            String stamount = String.valueOf(amount);
            mAmount.setText(stamount);
        }
    }

}