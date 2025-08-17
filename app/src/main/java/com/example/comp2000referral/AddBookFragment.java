package com.example.comp2000referral;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

// ADMIN
public class AddBookFragment extends Fragment {

    EditText titleEdit;
    EditText authorEdit;
    EditText descEdit;
    Button addButton;

    //tells android to use the xml for the ui
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_book, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleEdit = view.findViewById(R.id.titleEdit);
        authorEdit = view.findViewById(R.id.authorEdit);
        descEdit = view.findViewById(R.id.descEdit);
        addButton = view.findViewById(R.id.addButton);

        addButton.setOnClickListener(v -> {
            String title = titleEdit.getText().toString().trim();
            String author = authorEdit.getText().toString().trim();
            String description = descEdit.getText().toString().trim();

            if (title.isEmpty() || author.isEmpty() || description.isEmpty()) {
                Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Book added successfully!", Toast.LENGTH_SHORT).show();
                // returns to previous screen
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack(); //MIGHT NEED CHANGE
                }
            }
        });
    }
}
