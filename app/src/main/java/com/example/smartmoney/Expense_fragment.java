package com.example.smartmoney;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class Expense_fragment extends Fragment {

    EditText edt_expense_type,edt_expense_amount,edt_expense_note;
    Button add_expense;

    FirebaseAuth mAuth;
    DatabaseReference mexpenseDatabase;

    public Expense_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_expense_fragment, container, false);

        edt_expense_type = view.findViewById(R.id.edt_expence_type);
        edt_expense_amount = view.findViewById(R.id.edt_expence_amount);
        edt_expense_note = view.findViewById(R.id.edt_expence_note);
        add_expense = view.findViewById(R.id.btn_add_expence);


        add_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth=FirebaseAuth.getInstance();
                FirebaseUser mUser =mAuth.getCurrentUser();
                String uid = mUser.getUid();

                mexpenseDatabase = FirebaseDatabase.getInstance().getReference().child("Expense_Data").child(uid);



                String expense_type = edt_expense_type.getText().toString().trim();
                String expense_amount = edt_expense_amount.getText().toString().trim();
                String expense_note = edt_expense_note.getText().toString().trim();
                int amount_expense = Integer.parseInt(expense_amount);

                if(expense_type.isEmpty())
                {
                    edt_expense_type.setError("income type is empty");
                    edt_expense_type.requestFocus();
                    return;
                }
                if(expense_amount.isEmpty())
                {
                    edt_expense_amount.setError("amount is empty");
                    edt_expense_amount.requestFocus();
                    return;
                }
                if(expense_note.isEmpty())
                {
                    edt_expense_note.setError("note is empty");
                    edt_expense_note.requestFocus();
                    return;
                }


                String id = mexpenseDatabase.push().getKey();

                String mDate = DateFormat.getDateInstance().format(new Date());

                Data data = new Data(amount_expense,expense_type,expense_note,id,mDate);

                mexpenseDatabase.child(id).setValue(data);

                Toast.makeText(getActivity(), "Data added", Toast.LENGTH_LONG).show();

            }
        });


        return view;
    }

}