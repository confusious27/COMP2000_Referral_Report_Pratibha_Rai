package com.example.comp2000referral;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class UserProfileFragment extends Fragment {

    Button editButton;
    Button logOutButton;

    //tells android to use the xml for the ui
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editButton = view.findViewById(R.id.editButton);
        logOutButton = view.findViewById(R.id.logOut);

        editButton.setOnClickListener(v -> navigateToSettingsActivity());

        logOutButton.setOnClickListener(v -> {
            // clears saved login info
            requireActivity().getSharedPreferences("test_users", getContext().MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();

            // navigates back to login
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new LoginFragment())
                    .commit();
        });
    }

    // launches SettingsActivity when button is clicked
    private void navigateToSettingsActivity() {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new SettingsFragment())
                .addToBackStack(null) // so back button works to go back
                .commit();
    }


}
