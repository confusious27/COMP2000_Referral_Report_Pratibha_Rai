package com.example.comp2000referral;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class AdminBookDetailFragment extends Fragment {

    EditText titleView;
    EditText authorView;
    EditText descriptionView;
    Button confirmButtAdmin;
    Button confirmDelete;

    // fragments can't easily pass data, hence this
    public static AdminBookDetailFragment newInstance(String title, String author, String description) {
        AdminBookDetailFragment fragment = new AdminBookDetailFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("author", author);
        args.putString("description", description);
        fragment.setArguments(args);
        return fragment;
    }

    //tells android to use the xml for the ui
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleView = view.findViewById(R.id.titleEdit);
        authorView = view.findViewById(R.id.authorEdit);
        descriptionView = view.findViewById(R.id.descEdit);
        confirmButtAdmin = view.findViewById(R.id.confirmEdit);
        confirmDelete = view.findViewById(R.id.confirmDelete);

        Bundle args = getArguments();
        if (args != null) {
            titleView.setText(args.getString("title"));
            authorView.setText(args.getString("author"));
            descriptionView.setText(args.getString("description"));
        }

        confirmButtAdmin.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Book updated!", Toast.LENGTH_SHORT).show();
            // send results back to previous fragment
            Bundle result = new Bundle();
            result.putString("updatedTitle", titleView.getText().toString());
            getParentFragmentManager().setFragmentResult("updateBookKey", result);

            // returns to previous screen
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        confirmDelete.setOnClickListener(v -> {
            // send results back to previous fragment
            Bundle result = new Bundle();
            result.putString("deletedTitle", titleView.getText().toString());

            getParentFragmentManager().setFragmentResult("deleteBookKey", result);

            // returns to previous screen
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}