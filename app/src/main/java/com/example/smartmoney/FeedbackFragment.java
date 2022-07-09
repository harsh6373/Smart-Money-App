package com.example.smartmoney;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


public class FeedbackFragment extends Fragment {

    EditText to, feedback_msg;
    Button send;
    RatingBar ratingBar;
    TextView rate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        to=view.findViewById(R.id.email_feedback);
        feedback_msg=view.findViewById(R.id.feedback_msg);
        send=view.findViewById(R.id.btn_feedback_send);
        ratingBar= view.findViewById(R.id.rating_bar);
        rate= view.findViewById(R.id.txt_rate);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW
                , Uri.parse("mailto:"+to.getText().toString()));
                intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback to Smart Money");
                intent.putExtra(Intent.EXTRA_TEXT,feedback_msg.getText().toString());
                startActivity(intent);
            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rate.setText("Rate :"+v);
                Toast.makeText(getActivity(), "Thanks For Rating", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}