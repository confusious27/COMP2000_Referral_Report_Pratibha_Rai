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
    List<Book> bookList;

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

        // sample books
        bookList = new ArrayList<>();
        bookList.add(new Book("1984", "George Orwell", "A dystopian novel about surveillance."));
        bookList.add(new Book("Brave New World", "Aldous Huxley", "A futuristic society based on control."));
        bookList.add(new Book("Fahrenheit 451", "Ray Bradbury", "A world where books are outlawed."));
        bookList.add(new Book("Omniscient Reader's Viewpoint", "SingShong", "Kim Dokja finds himself inside his favourite web novel 'Three Ways to Survive the Apocalypse'."));

        adapter = new BookAdapter(getContext(), bookList, false, this::onBookClicked);
        recyclerView.setAdapter(adapter);

    }

    }