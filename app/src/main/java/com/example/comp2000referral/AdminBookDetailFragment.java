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

public class AdminBookDetailFragment extends Fragment{
    TextView titleView;
    TextView authorView;
    TextView descriptionView;
    Button editButton;

    public static AdminBookDetailFragment newInstance(String title, String author, String description) {
        AdminBookDetailFragment fragment = new AdminBookDetailFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("author", author);
        args.putString("description", description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleView = view.findViewById(R.id.bookTitle);
        authorView = view.findViewById(R.id.bookAuthor);
        descriptionView = view.findViewById(R.id.bookDescription);
        editButton = view.findViewById(R.id.editButton);

        Bundle args = getArguments();
        if (args != null) {
            titleView.setText(args.getString("title"));
            authorView.setText(args.getString("author"));
            descriptionView.setText(args.getString("description"));
        }

        editButton.setOnClickListener(v -> {
            EditBookFragment editFragment = EditBookFragment.newInstance(
                    titleView.getText().toString(),
                    authorView.getText().toString(),
                    descriptionView.getText().toString()
            );

            // replaces the current with the edit
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, editFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }
}