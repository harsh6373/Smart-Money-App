package com.example.smartmoney;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Fill_Details extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edt_fname, edt_lname, edt_address, edt_email;
    Spinner profession;
    RadioGroup radio_gender;
    Button submit;
    FirebaseDatabase db;
    DatabaseReference root;
    FirebaseUser firebaseUser;
    User user;
    String[] prof = {"Student", "Goverment Employee", "Private Employee", "Freelancer", "Entrepreneur"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_details);

        edt_fname = findViewById(R.id.edt_fname);
        edt_lname = findViewById(R.id.edt_lname);
        edt_email = findViewById(R.id.edt_email);
        edt_address = findViewById(R.id.edt_address);
        profession = findViewById(R.id.profession);
        radio_gender = findViewById(R.id.gender);
        submit = findViewById(R.id.btn_submit_details);
        profession.setOnItemSelectedListener(this);


        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, prof);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        profession.setAdapter(aa);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = edt_fname.getText().toString();
                String lname = edt_lname.getText().toString();
                String email = edt_email.getText().toString();
                String address = edt_address.getText().toString();
                int id = radio_gender.getCheckedRadioButtonId();
                String prof_list = profession.getSelectedItem().toString();
                String gender = "";
                user = new User();


                if (id == R.id.male) {
                    gender = "Male";
                }
                if (id == R.id.female) {
                    gender = "Female";
                }
                if (id == R.id.other) {
                    gender = "Other";
                }

                if (fname.isEmpty()) {
                    edt_fname.setError("First Name is empty");
                    edt_fname.requestFocus();
                    return;
                }
                if (lname.isEmpty()) {
                    edt_lname.setError("Last Name is empty");
                    edt_lname.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    edt_email.setError("Email is empty");
                    edt_email.requestFocus();
                    return;
                }
                if (address.isEmpty()) {
                    edt_address.setError("Address is empty");
                    edt_address.requestFocus();
                    return;
                }
                db = FirebaseDatabase.getInstance();
                root = db.getReference().child("Users");
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                addDatatoFirebase(fname, lname, email, address, gender, prof_list);

                Intent i = new Intent(getApplicationContext(), Dashboard_Activity.class);
                startActivity(i);
            }
        });


    }

    private void addDatatoFirebase(String fname, String lname, String email, String address, String gender, String prof_list) {

        user.setFName(fname);
        user.setLName(lname);
        user.setEmail(email);
        user.setAddress(address);
        user.setGender(gender);
        user.setProf_list(prof_list);


        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                root.child(firebaseUser.getUid()).setValue(user);
                Toast.makeText(getApplicationContext(), "Data Inserted Sucessfully", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getApplicationContext(), "Error..", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}