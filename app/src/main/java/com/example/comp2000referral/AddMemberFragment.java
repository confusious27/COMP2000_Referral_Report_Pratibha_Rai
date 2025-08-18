package com.example.comp2000referral;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMemberFragment extends Fragment {
    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
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
        addButton = view.findViewById(R.id.addMemButton);

        // button click
        addButton.setOnClickListener(v -> addMember());

        return view;
    }

    private void addMember() {
        String firstName = firstNameInput.getText().toString().trim();
        String lastName = lastNameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // build JSON body
            JSONObject body = new JSONObject();
            body.put("firstname", firstName);
            body.put("lastname", lastName);
            body.put("email", email);

            // API
            APIClient.post("members", body, new APIClient.ApiCallback() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(getContext(), "Member added successfully!", Toast.LENGTH_SHORT).show();

                    // optional: clear form
                    firstNameInput.setText("");
                    lastNameInput.setText("");
                    emailInput.setText("");

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
