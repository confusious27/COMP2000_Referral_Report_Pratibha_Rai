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

import com.example.comp2000referral.models.Book;

import java.util.List;

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

        // for updating books
        getParentFragmentManager().setFragmentResultListener("updateBookKey", this, (key, bundle) -> {
            String updatedTitle = bundle.getString("updatedTitle");
            String updatedAuthor = bundle.getString("updatedAuthor");
            String updatedDescription = bundle.getString("updatedDescription");

            String originalTitle = getArguments().getString("title");

            List<Book> books = BookManager.getBooks(getContext());
            for (Book book : books) {
                if (book.getTitle().equals(originalTitle)) {
                    book.setTitle(updatedTitle);
                    book.setAuthor(updatedAuthor);
                    book.setDescription(updatedDescription);
                    break;
                }
            }

            BookManager.updateBooks(getContext(), books);

            // Update the displayed data
            titleView.setText(updatedTitle);
            authorView.setText(updatedAuthor);
            descriptionView.setText(updatedDescription);

            // Update the arguments so subsequent edits use the updated title
            getArguments().putString("title", updatedTitle);
        });


        getParentFragmentManager().setFragmentResultListener("deleteBookKey", this, (key, bundle) -> {
            String deletedTitle = bundle.getString("deletedTitle");

            List<Book> books = BookManager.getBooks(getContext());
            books.removeIf(book -> book.getTitle().equals(deletedTitle));
            BookManager.updateBooks(getContext(), books);
        });


    }
}