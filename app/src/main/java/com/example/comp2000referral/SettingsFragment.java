package com.example.comp2000referral;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

// USER
public class SettingsFragment extends Fragment {

    EditText firstName;
    EditText lastName;
    SwitchCompat pushNotif;
    Button confirmButton;

    //tells android to use the xml for the ui
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        pushNotif = view.findViewById(R.id.pushNotif);
        confirmButton = view.findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(v -> {
            String first = firstName.getText().toString().trim();
            String last = lastName.getText().toString().trim();

            if (first.isEmpty() || last.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Settings saved!", Toast.LENGTH_SHORT).show();
                // returns to previous screen
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }

        });

        //this allows the notification button to save without having to press the confirm button
//        pushNotif.setOnClickListener(v -> {
//            if (getActivity() != null) {
//                var prefs = getActivity().getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE);
//                prefs.edit()
//                        .putBoolean("pushNotif", isChecked)
//                        .apply();
//        });


    }
}
