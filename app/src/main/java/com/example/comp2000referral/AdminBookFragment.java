package com.example.comp2000referral;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000referral.adapters.BookAdapter;
import com.example.comp2000referral.models.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AdminBookFragment extends Fragment {
    RecyclerView recyclerView;
    BookAdapter adapter;
    List<Book> bookList;
    FloatingActionButton addBookButton;
    ActivityResultLauncher<Intent> editBookLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_books, container, false);

        recyclerView = view.findViewById(R.id.booksView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // loads books from SQLite
        LibraryDatabase db = new LibraryDatabase(getContext());
        bookList = db.getAllBooks();


        // sample books if list is empty (first launch)
        if (bookList.isEmpty()) {
            db.insertBook("1984", "George Orwell", "A dystopian novel about surveillance.");
            db.insertBook("Brave New World", "Aldous Huxley", "A futuristic society based on control.");
            db.insertBook("Fahrenheit 451", "Ray Bradbury", "A world where books are outlawed.");
            db.insertBook("Omniscient Reader's Viewpoint", "SingShong", "Kim Dokja finds himself inside his favourite web novel.");

            bookList = db.getAllBooks(); // reload after insertion
        }


        // passes the click listener to the , also helps with the admin check (so user cannot edit/delete)
        adapter = new BookAdapter(getContext(), bookList, true, book -> {
            AdminBookDetailFragment fragment = AdminBookDetailFragment.newInstance(
                    book.getTitle(),
                    book.getAuthor(),
                    book.getDescription()
            );

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();

        });
        recyclerView.setAdapter(adapter);

        addBookButton = view.findViewById(R.id.addBook);
        addBookButton.setOnClickListener(v -> {
            AddBookFragment addBookFragment = new AddBookFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, addBookFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
