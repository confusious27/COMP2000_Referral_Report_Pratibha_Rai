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

import androidx.fragment.app.Fragment;


import org.json.JSONObject;

public class LoginFragment extends Fragment {

    EditText email;
    EditText password;
    Button loginButton;
    TextView forgotPass;
    TextView signupRedirect;

    //tells android to use the xml for the ui
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            // views by ID
            email = view.findViewById(R.id.emailAddress);
            password = view.findViewById(R.id.password);
            loginButton = view.findViewById(R.id.loginButton);
            forgotPass = view.findViewById(R.id.forgotPass);
            signupRedirect = view.findViewById(R.id.signupRedirect);

            // login button
            loginButton.setOnClickListener(v -> {
                String emailInput = email.getText().toString();
                if (emailInput.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                String passwordInput = password.getText().toString();
                if (passwordInput.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // testing
                if (emailInput.equals("user@example.com") && passwordInput.equals("1234")) {
                    // Normal user login
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).onLoginSuccess("user");
                    }
                } else if (emailInput.equals("admin@example.com") && passwordInput.equals("admin")) {
                    // Admin login
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).onLoginSuccess("admin");
                    }
                } else {
                    Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            });

//                APIClient.get("members/" + emailInput, new APIClient.ApiCallback() {
//                    @Override
//                    public void onSuccess(String result) {
//                        try {
//                            JSONObject json = new JSONObject(result);
//                            String role = json.getString("role");
//
//                            // calls the activity method to proceed after the login
//                            if (getActivity() instanceof MainActivity) {
//                                ((MainActivity) getActivity()).onLoginSuccess(role);
//                            }
//
//                        } catch (Exception e) {
//                            Toast.makeText(getContext(), "Invalid response from server", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        Toast.makeText(getContext(), "Login failed: User not found", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            });

            // redirects to forget password
            forgotPass.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ForgetFragment.class);
                startActivity(intent);
            });

            // redirects to sign up
            signupRedirect.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), SignupFragment.class);
                startActivity(intent);
            });

        }
    }

