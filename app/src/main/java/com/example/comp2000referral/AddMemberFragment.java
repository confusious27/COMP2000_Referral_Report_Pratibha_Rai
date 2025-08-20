package com.example.comp2000referral;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.comp2000referral.models.Book;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AddMemberFragment extends Fragment {
    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText contactInput;
    Button addButton;

    public AddMemberFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_member, container, false);

        // bind views
        firstNameInput = view.findViewById(R.id.firstNameText);
        lastNameInput = view.findViewById(R.id.lastNameText);
        emailInput = view.findViewById(R.id.emailAddText);
        contactInput = view.findViewById(R.id.contactAddText);
        addButton = view.findViewById(R.id.addMemButton);

        // button click
        addButton.setOnClickListener(v -> addMember());

        return view;
    }

    private void addMember() {
        String firstName = firstNameInput.getText().toString().trim();
        String lastName = lastNameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String contact = contactInput.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || contact.isEmpty()) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // build JSON body
            JSONObject body = new JSONObject();
            String username = (firstName + lastName).toLowerCase(); //user can change their username
            body.put("username", username);
            body.put("firstname", firstName);
            body.put("lastname", lastName);
            body.put("email", email);
            body.put("contact",contact);

            // this is for the membership
            LocalDate today = LocalDate.now();
            LocalDate endDate = today.plusYears(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            body.put("membership_end_date",endDate.format(formatter));

            // API
            APIClient.addMember(body, new APIClient.ApiCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.d("AddMember", "POST response: " + result);
                    Toast.makeText(getContext(), "Member added successfully!", Toast.LENGTH_SHORT).show();
                    // navigates back
                    requireActivity().getSupportFragmentManager().popBackStack();
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(getContext(), "Failed to add member", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
