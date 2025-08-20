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
public class EditMembersFragment extends Fragment {
    EditText firstNameEdit;
    EditText lastNameEdit;
    EditText emailEdit;
    EditText contactEdit;
    Button confirmButton;
    Button confirmDeleteMem;
    String username; // comes from previous fragment

    public EditMembersFragment() {
        // Required empty public constructor
    }


    // passes the details from the last screen
    public static EditMembersFragment newInstance(String username, String firstName, String lastName, String email, String contact) {
        EditMembersFragment fragment = new EditMembersFragment();
        Bundle args = new Bundle();
        args.putString("username", username);
        args.putString("firstname", firstName);
        args.putString("lastname", lastName);
        args.putString("email", email);
        args.putString("contact", contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            firstNameEdit.setText(getArguments().getString("firstname"));
            lastNameEdit.setText(getArguments().getString("lastname"));
            emailEdit.setText(getArguments().getString("email"));
            contactEdit.setText(getArguments().getString("contact"));
        } else {
            loadMemberDetails(); // uses API if the details from previous screen doesn't pass over
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_members, container, false);

        firstNameEdit = view.findViewById(R.id.firstName);
        lastNameEdit = view.findViewById(R.id.lastName);
        emailEdit = view.findViewById(R.id.emailEdit);
        contactEdit = view.findViewById(R.id.contactEdit);
        confirmButton = view.findViewById(R.id.saveMembers);
        confirmDeleteMem = view.findViewById(R.id.confirmDeleteMem);

        loadMemberDetails();

        // confirm update
        confirmButton.setOnClickListener(v -> updateMember());
        //delete
        confirmDeleteMem.setOnClickListener(v -> deleteMember());

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
                    emailEdit.setText(obj.getString("email"));
                    contactEdit.setText(obj.getString("contact"));
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
        String email = emailEdit.getText().toString().trim();
        String contact = contactEdit.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || contact.isEmpty()) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            JSONObject body = new JSONObject();
            body.put("firstname", firstName);
            body.put("lastname", lastName);
            body.put("email", email);
            body.put("contact", contact);

            APIClient.updateMember(username, body, new APIClient.ApiCallback() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(getContext(), "Updated successfully!", Toast.LENGTH_SHORT).show();
                    // go back
                    if (getActivity() != null) {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
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

    private void deleteMember() {
        APIClient.deleteMember(username, new APIClient.ApiCallback() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(getContext(), "Member deleted!", Toast.LENGTH_SHORT).show();
                // go back after delete
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), "Delete failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

