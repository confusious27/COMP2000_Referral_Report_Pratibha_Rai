package com.example.comp2000referral;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import org.json.JSONObject;

public class LoginFragment extends Fragment {

    EditText userName;
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
         userName = view.findViewById(R.id.userName);
         password = view.findViewById(R.id.password);
         loginButton = view.findViewById(R.id.loginButton);
         forgotPass = view.findViewById(R.id.forgotPass);
         signupRedirect = view.findViewById(R.id.signupRedirect);

         // password for my users (pushed to the API)
        setupLocalPasswords();

        // login button
        loginButton.setOnClickListener(v -> {

            String usernameInput = userName.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();
//            Log.d("LOGIN", "Calling API: " + "http://10.240.72.69/comp2000/library/members/" + usernameInput);

            if (usernameInput.isEmpty()) {
                Toast.makeText(getContext(), "Please enter username", Toast.LENGTH_SHORT).show();
                return;
            }
            if (passwordInput.isEmpty()) {
                Toast.makeText(getContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences prefs = requireContext().getSharedPreferences("test_users", getContext().MODE_PRIVATE);
            prefs.edit().putString("logged_in_user", usernameInput).apply(); // save the username for the requests

            String storedPassword = prefs.getString(usernameInput, null);
            String role = prefs.getString(usernameInput + "_role", "user");

            // checks if the password is correct, if it is, it's API time
            if (storedPassword != null  && storedPassword.equals(passwordInput)) {
                APIClient.getMember(usernameInput, new APIClient.ApiCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d("API_CALL", "GET " + "members/" + usernameInput);
                        try {
                            JSONObject json = new JSONObject(result);

                            String firstname = json.getString("firstname");
                            String lastname = json.getString("lastname");

                            if (getActivity() instanceof MainActivity) {
                                ((MainActivity) getActivity()).onLoginSuccess(role);
                                Toast.makeText(getContext(), "Welcome " + firstname + " " + lastname + "!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Error reading user info from API", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(Exception e) {
                        if (!isAdded() || getContext() == null) return; // it wont't crash the bergentruck
                        Toast.makeText(getContext(), "User not found in API", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
            }

        });

//            // testing
//            if (emailInput.equals("user@example.com") && passwordInput.equals("1234")) {
//                // Normal user login
//                if (getActivity() instanceof MainActivity) {
//                    ((MainActivity) getActivity()).onLoginSuccess("user");
//                }
//            } else if (emailInput.equals("admin@example.com") && passwordInput.equals("admin")) {
//                // Admin login
//                if (getActivity() instanceof MainActivity) {
//                    ((MainActivity) getActivity()).onLoginSuccess("admin");
//                }
//            } else {
//                Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
//            }
//        });


        // redirects to forget password
        forgotPass.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, new ForgetFragment());
            transaction.addToBackStack(null);
            transaction.commit();

        });

        // redirects to sign up
        signupRedirect.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, new SignupFragment());
            transaction.addToBackStack(null);
            transaction.commit();

        });
    }

    // setting up local passwords
    private void setupLocalPasswords() {
        SharedPreferences prefs = requireContext().getSharedPreferences("test_users", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // for testing; roles and password
        if (!prefs.contains("Snowy")) {
            editor.putString("Snowy", "12345");
            editor.putString("Snowy_role", "user");
        }
        if (!prefs.contains("Alpine")) {
            editor.putString("Alpine", "admin");
            editor.putString("Alpine_role", "admin");
        }

        editor.apply();
    }
}

