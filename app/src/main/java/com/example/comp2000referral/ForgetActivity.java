package com.example.comp2000referral;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ForgetActivity extends Fragment {

    EditText email;
    Button verifyButton;

    //tells android to use the xml for the ui
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forget, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // views by ID
        email = view.findViewById(R.id.forgotEmail);
        verifyButton = view.findViewById(R.id.verifyButton);

        verifyButton.setOnClickListener(v -> {
            String emailText = email.getText().toString().trim();

            if (emailText.isEmpty()) {
                Toast.makeText(getContext(), "Email is required!", Toast.LENGTH_SHORT).show();
            } else { // this doesn't do anything without an api and email connection
                Toast.makeText(getContext(), "Verification Email sent!", Toast.LENGTH_SHORT).show();

                // navigates to login
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, new LoginFragment());
                transaction.commit();
            }
        });
    }

    // for the topbar
    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showToolbar("Forgot Password");
        }
    }
}