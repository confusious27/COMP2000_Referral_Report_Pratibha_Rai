package com.example.comp2000referral;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000referral.adapters.BookAdapter;
import com.example.comp2000referral.models.Book;

import java.util.ArrayList;
import java.util.List;

public class UserBookFragment extends Fragment {

    RecyclerView recyclerView;
    BookAdapter adapter;
    LibraryDatabase db;

    public UserBookFragment() {
        // Required empty public constructor
    }

    public void onBookClicked(Book book) {
        BookDetailFragment detailFragment = BookDetailFragment.newInstance(
                book.getTitle(), book.getAuthor(), book.getDescription()
        );

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, detailFragment)
                .addToBackStack(null)  // Enables back navigation
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_books, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.booksView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = new LibraryDatabase(requireContext());
        List<Book> books = db.getAllBooks(); // fetch all da books from SQLite

        adapter = new BookAdapter(getContext(), books, false, book -> {
            BookDetailFragment fragment = BookDetailFragment.newInstance(
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
    }

    @Override
    public void onResume() {
        super.onResume();
        // refreshes the list
        List<Book> updatedBooks = db.getAllBooks();
        adapter.setBooks(updatedBooks);
    }

}