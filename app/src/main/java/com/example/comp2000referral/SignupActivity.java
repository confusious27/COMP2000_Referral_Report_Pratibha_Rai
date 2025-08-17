package com.example.comp2000referral;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SignupActivity extends Fragment {

    EditText email;
    EditText password;
    EditText confirmPass;
    Button signButton;
    TextView loginRedirect;

    //tells android to use the xml for the ui
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // views by ID
        email = view.findViewById(R.id.signEmail);
        password = view.findViewById(R.id.choosePass);
        confirmPass = view.findViewById(R.id.confirmPass);
        signButton = view.findViewById(R.id.signButton);
        loginRedirect = view.findViewById(R.id.loginRedirect);

        signButton.setOnClickListener(v -> {
            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString();
            String confirmText = confirmPass.getText().toString();

            if (emailText.isEmpty() || passwordText.isEmpty() || confirmText.isEmpty()) {
                Toast.makeText(getContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
            } else if (!passwordText.equals(confirmText)) {
                Toast.makeText(getContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();

                // navigates to login
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, new LoginFragment());
                transaction.commit();
            }
        });

        // redirects to login
        loginRedirect.setOnClickListener(v -> {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, new LoginFragment());
        transaction.commit();
        });
    }
}

