package com.example.comp2000referral;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class RequestDetailActivity extends AppCompatActivity {

    TextView bookTitleView;
    TextView requesterView;
    Button acceptButton;
    Button declineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        bookTitleView = findViewById(R.id.bookTitleAdmin);
        requesterView = findViewById(R.id.requesterName);
        acceptButton = findViewById(R.id.acceptButton);
        declineButton = findViewById(R.id.declineButton);

        // Dummy data for now
        bookTitleView.setText("Book Title: The Hobbit");
        requesterView.setText("Requested by: John Doe");

        // Buttons do nothing for now
    }
}