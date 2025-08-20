package com.example.comp2000referral;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

// USER
public class BookDetailFragment extends Fragment {

    TextView titleView;
    TextView authorView;
    TextView descriptionView;
    Button requestButton;

    // fragments can't easily pass data, hence this
    public static BookDetailFragment newInstance(String title, String author, String description) {
        BookDetailFragment fragment = new BookDetailFragment();
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
        return inflater.inflate(R.layout.fragment_book_detail_user, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleView = view.findViewById(R.id.bookTitle);
        authorView = view.findViewById(R.id.bookAuthorUser);
        descriptionView = view.findViewById(R.id.bookDescriptionUser);
        requestButton = view.findViewById(R.id.requestButton);

        // get intent is activity based
        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString("title");
            String author = args.getString("author");
            String description = args.getString("description");

            titleView.setText(title);
            authorView.setText(author);
            descriptionView.setText(description);

            requestButton.setOnClickListener(v -> {
                if (getContext() == null) return;

                // initializes it
                UserRequestManager manager = new UserRequestManager(getContext());

                if (manager.hasRequested(title)) {
                    Toast.makeText(getContext(),
                            "You've already requested \"" + title + "\"",
                            Toast.LENGTH_SHORT).show();
                } else {

                    SharedPreferences prefs =  requireContext().getSharedPreferences("test_users", Context.MODE_PRIVATE);
                    String username = prefs.getString("logged_in_user", "Unknown"); // this assumes you requested it on this login

                    // create new request with "Pending" status
                    UserRequest newRequest = new UserRequest(title, "Pending", username);
                    manager.addRequest(newRequest);

                    // Notify admin - for now just show a toast
                    Toast.makeText(getContext(),
                            "Request sent for \"" + title + "\"", Toast.LENGTH_SHORT).show();

                }
            });

            //  show the toolbar with back button
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).showToolbar();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hide toolbar when leaving this fragment if needed
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).hideToolbar();
        }
    }

}