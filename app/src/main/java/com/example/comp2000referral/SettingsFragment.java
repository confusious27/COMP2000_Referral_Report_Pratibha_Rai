package com.example.comp2000referral;

import static java.security.AccessController.getContext;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.xml.transform.Result;

// USER
public class SettingsFragment extends Fragment {

    EditText firstName;
    EditText lastName;
    EditText emailSet;
    EditText contactSet;
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
        emailSet = view.findViewById(R.id.emailSet);
        contactSet = view.findViewById(R.id.contactSet);
        pushNotif = view.findViewById(R.id.pushNotif);
        confirmButton = view.findViewById(R.id.confirmButton);

        if (getActivity() == null) return;

        confirmButton.setOnClickListener(v -> {

            SharedPreferences prefs = getActivity().getSharedPreferences("test_users", AppCompatActivity.MODE_PRIVATE);
            String username = prefs.getString("logged_in_user", null);// called from shared preference

            // in case it shows up empty
            if (username == null || username.isEmpty()) {
                Toast.makeText(getContext(), "No username found", Toast.LENGTH_SHORT).show();
                return;
            }

            // fetching the user data
            APIClient.getMember(username, new APIClient.ApiCallback() {
                @Override
                public void onSuccess(String result) {
                    try {

                        JSONObject existUser = new JSONObject(result);

                        // gotta parse it bc API is throwing a fuss
                        String addedDate = existUser.getString("membership_end_date");
                        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
                        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        String apiDate = outputFormat.format(inputFormat.parse(addedDate));

                        // this is so only fields that have data in them will be updated, the rest will return the same old value
                        JSONObject updateBody = new JSONObject();
                        updateBody.put("firstname", firstName.getText().toString().trim().isEmpty()
                                ? existUser.getString("firstname")
                                : firstName.getText().toString().trim());
                        updateBody.put("lastname", lastName.getText().toString().trim().isEmpty()
                                ? existUser.getString("lastname")
                                : lastName.getText().toString().trim());
                        updateBody.put("email", emailSet.getText().toString().trim().isEmpty()
                                ? existUser.getString("email")
                                : emailSet.getText().toString().trim());
                        updateBody.put("contact", contactSet.getText().toString().trim().isEmpty()
                                ? existUser.getString("contact")
                                : contactSet.getText().toString().trim());

                        // REQUIRED fields but not added in the settings because it shouldn't be touched
                        updateBody.put("membership_end_date", apiDate);
                        updateBody.put("username", username);


                        // bring the API in! Anything for you Beyonce!
                        APIClient.updateMember(username, updateBody, new APIClient.ApiCallback() {
                            @Override
                            public void onSuccess(String result) {

                                Toast.makeText(getContext(), "Updated successfully!", Toast.LENGTH_SHORT).show();
                                if (getActivity() != null) {
                                    getActivity().getSupportFragmentManager().popBackStack();
                                }
                            }

                            @Override
                            public void onError(Exception e) {

                                Toast.makeText(getContext(), "Failed to update", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(getContext(), "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                }
            });

            });

        // this allows the notification button to save without having to press the confirm button
        SharedPreferences setPrefs = getActivity().getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE);
        pushNotif.setChecked(setPrefs.getBoolean("pushNotif", false)); // the sharedpref safeguards the saved value so that none shall pass
        pushNotif.setOnClickListener(v -> setPrefs.edit()
                .putBoolean("pushNotif", pushNotif.isChecked())
                .apply());
        }
}
