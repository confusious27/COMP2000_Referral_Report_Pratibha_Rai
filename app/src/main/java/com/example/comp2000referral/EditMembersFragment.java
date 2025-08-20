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
    String membership;

    // passes the details from the last screen
    public static EditMembersFragment newInstance(String username, String firstName, String lastName, String email, String contact, String membership) {
        EditMembersFragment fragment = new EditMembersFragment();
        Bundle args = new Bundle();
        args.putString("username", username);
        args.putString("firstname", firstName);
        args.putString("lastname", lastName);
        args.putString("email", email);
        args.putString("contact", contact);
        args.putString("membership_end_date", membership);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_members, container, false);

        firstNameEdit = view.findViewById(R.id.firstName);
        lastNameEdit = view.findViewById(R.id.lastName);
        emailEdit = view.findViewById(R.id.emailEdit);
        contactEdit = view.findViewById(R.id.contactEdit);
        confirmButton = view.findViewById(R.id.saveMembers);
        confirmDeleteMem = view.findViewById(R.id.confirmDeleteMem);

        if (getArguments() != null) {
            username = getArguments().getString("username");
            membership = getArguments().getString("membership_end_date");

            firstNameEdit.setText(getArguments().getString("firstname"));
            lastNameEdit.setText(getArguments().getString("lastname"));
            emailEdit.setText(getArguments().getString("email"));
            contactEdit.setText(getArguments().getString("contact"));
        }

        // fetch dem details for the date pls
        loadMemberDetails();

        confirmButton.setOnClickListener(v -> updateMember());
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

                    // saves the date to give back to update :3
                    requireActivity().runOnUiThread(() -> {
                        firstNameEdit.setTag(obj.optString("membership_end_date"));
                    });

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
        try {
            JSONObject body = new JSONObject();
            body.put("firstname", firstNameEdit.getText().toString().trim());
            body.put("lastname", lastNameEdit.getText().toString().trim());
            body.put("email", emailEdit.getText().toString().trim());
            body.put("contact", contactEdit.getText().toString().trim());

            // locked >:)
            body.put("username", username);
            body.put("membership_end_date", membership);

            Log.d("DEBUG", "Update Body: " + body.toString());

            APIClient.updateMember(username, body, new APIClient.ApiCallback() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(getContext(), "Updated successfully!", Toast.LENGTH_SHORT).show();
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
                Log.e("DEBUG", "Delete failed", e);
                Toast.makeText(getContext(), "Delete failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

