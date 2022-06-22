package com.example.smartmoney;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

public class Expense_fragment extends Fragment {

    EditText edt_expense_type,edt_expense_amount,edt_expense_note;
    Button add_expense;

    FirebaseAuth mAuth;
    DatabaseReference mexpenseDatabase;

    RecyclerView recyclerView;

    TextView expanse_total_txt;


    private EditText edt_Amount;
    private EditText edt_Type;
    private EditText edt_Note;

    private Button btn_Update;
    private Button btn_Delete;

    private String type;
    private String note;
    private int amount;

    private String post_key;



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
        expanse_total_txt = view.findViewById(R.id.expense_txt_result);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser =mAuth.getCurrentUser();
        String uid = mUser.getUid();

        mexpenseDatabase = FirebaseDatabase.getInstance().getReference().child("Expense_Data").child(uid);

        recyclerView = view.findViewById(R.id.recycleview_expanse);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        add_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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


        mexpenseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int totalValue_expanse = 0;


                for (DataSnapshot mysnapshot: snapshot.getChildren()){

                    Data data = mysnapshot.getValue(Data.class);

                    totalValue_expanse+= data.getAmount();
                    String stTotalvalue_expanse = String.valueOf(totalValue_expanse);

                    expanse_total_txt.setText(stTotalvalue_expanse);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(mexpenseDatabase, Data.class)
                .build();

        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Data model) {

                holder.setType(model.getType());
                holder.setNote(model.getNote());
                holder.setDate(model.getDate());
                holder.setAmount(model.getAmount());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        post_key=getRef(position).getKey();
                        type=model.getType();
                        note=model.getNote();
                        amount=model.getAmount();

                        updateDataitem();
                    }
                });
            }
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_recycler_data, parent, false);
                return new MyViewHolder(view);
            }
        };


        recyclerView.setAdapter(adapter);
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
            TextView mType = mView.findViewById(R.id.type_txt_expense);
            mType.setText(type);
        }
        private void setNote(String note){
            TextView mNote = mView.findViewById(R.id.note_txt_expense);
            mNote.setText(note);
        }
        private void setDate(String date){
            TextView mDate = mView.findViewById(R.id.date_txt_expense);
            mDate.setText(date);
        }
        private void setAmount(int amount){
            TextView mAmount = mView.findViewById(R.id.amount_txt_expense);
            String stamount = String.valueOf(amount);
            mAmount.setText(stamount);
        }
    }


    private void updateDataitem(){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View myview = inflater.inflate(R.layout.custom_layout_for_dialogbox,null);
        mydialog.setView(myview);

        edt_Amount = myview.findViewById(R.id.amount_edt);
        edt_Type = myview.findViewById(R.id.type_edt);
        edt_Note = myview.findViewById(R.id.note_edt);

        edt_Type.setText(type);
        edt_Type.setSelection(type.length());

        edt_Note.setText(note);
        edt_Note.setSelection(note.length());

        edt_Amount.setText(String.valueOf(amount));
        edt_Amount.setSelection(String.valueOf(amount).length());


        btn_Update= myview.findViewById(R.id.btn_update);
        btn_Delete = myview.findViewById(R.id.btn_delete);

        AlertDialog dialog = mydialog.create();

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type= edt_Type.getText().toString().trim();
                note=edt_Note.getText().toString().trim();

                String mdamount= String.valueOf(amount);

                mdamount=edt_Amount.getText().toString().trim();

                int myAmount = Integer.parseInt(mdamount);

                String mDate=DateFormat.getDateInstance().format(new Date());

                Data data= new Data(myAmount,type,note,post_key,mDate);

                mexpenseDatabase.child(post_key).setValue(data);

                dialog.dismiss();
            }
        });

        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}