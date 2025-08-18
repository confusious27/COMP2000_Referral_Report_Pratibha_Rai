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

// ADMIN
public class MembersDetailFragment extends Fragment {
    EditText firstNameEdit;
    EditText lastNameEdit;
    Button confirmButton;
    String username; // comes from previous fragment

    public MembersDetailFragment() {
        // Required empty public constructor
    }

    public static MembersDetailFragment newInstance(String username) {
        MembersDetailFragment fragment = new MembersDetailFragment();
        Bundle args = new Bundle();
        args.putString("username", username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString("username");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_members_detail, container, false);

        firstNameEdit = view.findViewById(R.id.firstName);
        lastNameEdit = view.findViewById(R.id.lastName);
        confirmButton = view.findViewById(R.id.confirmMembers);

        loadMemberDetails();

        confirmButton.setOnClickListener(v -> updateMember());

        return view;
    }

    // use API to get members
    private void loadMemberDetails() {
        APIClient.get("members/" + username, new APIClient.ApiCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    firstNameEdit.setText(obj.getString("firstname"));
                    lastNameEdit.setText(obj.getString("lastname"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), "Error loading member", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateMember() {
        String firstName = firstNameEdit.getText().toString().trim();
        String lastName = lastNameEdit.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            Toast.makeText(getContext(), "Both fields required", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            JSONObject body = new JSONObject();
            body.put("firstname", firstName);
            body.put("lastname", lastName);

            APIClient.put("members/" + username, body, new APIClient.ApiCallback() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(getContext(), "Updated successfully!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

