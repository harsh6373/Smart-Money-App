package com.example.smartmoney;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile_Fragment extends Fragment {

    TextView t_fname,t_lname,t_email,t_profession,t_address,t_gender;
    FirebaseDatabase db;
    DatabaseReference root;
    FirebaseUser firebaseUser;
    String uid;
    User user;
    Button update, logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_, container, false);
        db = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        root = db.getReference().child("Users").child(uid);
        user = new User();

        t_fname=view.findViewById(R.id.profile_fname);
        t_lname=view.findViewById(R.id.profile_lname);
        t_email=view.findViewById(R.id.profile_email);
        t_profession=view.findViewById(R.id.profile_profession);
        t_address=view.findViewById(R.id.profile_address);
        t_gender=view.findViewById(R.id.profile_gender);
        update=view.findViewById(R.id.update_profile);
        logout=view.findViewById(R.id.logout_profile_btn);


        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                t_fname.setText("First Name :"+" "+user.getFName());
                t_lname.setText("Last name :"+" "+user.getLName());
                t_email.setText("Email :"+" "+user.getEmail());
                t_profession.setText("Profession :"+" "+user.getProf_list());
                t_address.setText("Address :"+" "+user.getAddress());
                t_gender.setText("Gender :"+" "+user.getGender());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),Fill_Details.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(getActivity(),MainActivity.class);
                startActivity(i);
            }
        });


        return view;
    }
}