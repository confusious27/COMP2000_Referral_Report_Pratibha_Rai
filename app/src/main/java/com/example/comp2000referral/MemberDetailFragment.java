package com.example.comp2000referral;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class MemberDetailFragment extends Fragment {

    TextView memberName;
    TextView firstNameView;
    TextView lastNameView;
    TextView emailView;
    TextView contactView;
    TextView membershipView;
    Button editButton;

    public static MemberDetailFragment newInstance(String username, String firstname, String lastname,
                                                     String email, String contact, String membershipEndDate) {
        MemberDetailFragment fragment = new MemberDetailFragment();
        Bundle args = new Bundle();
        args.putString("username", username);
        args.putString("firstname", firstname);
        args.putString("lastname", lastname);
        args.putString("email", email);
        args.putString("contact", contact);
        args.putString("membershipEndDate", membershipEndDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_member_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        memberName = view.findViewById(R.id.memberName);
        firstNameView = view.findViewById(R.id.firstNameView);
        lastNameView = view.findViewById(R.id.lastNameView);
        emailView = view.findViewById(R.id.emailView);
        contactView = view.findViewById(R.id.contactView);
        membershipView = view.findViewById(R.id.membershipView);
        editButton = view.findViewById(R.id.editMemButton);

        Bundle args = getArguments();
        if (args != null) {
            memberName.setText("Username: " + args.getString("username"));
            firstNameView.setText("First Name: " + args.getString("firstname"));
            lastNameView.setText("Last Name: " + args.getString("lastname"));
            emailView.setText("Email: " + args.getString("email"));
            contactView.setText("Contact: " + args.getString("contact"));
            membershipView.setText("Membership ends on: " + args.getString("membershipEndDate"));
        }

        editButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Edit Member clicked!", Toast.LENGTH_SHORT).show();
            // You can navigate to an EditMemberFragment here if needed.
        });
    }
}