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

        // help fragment get book list and adapter
        // sample books
        bookList = new ArrayList<>();
        bookList.add(new Book("1984", "George Orwell", "A dystopian novel about surveillance."));
        bookList.add(new Book("Brave New World", "Aldous Huxley", "A futuristic society based on control."));

        // registers the result
        editBookLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        String deletedTitle = result.getData().getStringExtra("deletedTitle");

                        for (int i = 0; i < bookList.size(); i++) {
                            if (bookList.get(i).getTitle().equals(deletedTitle)) {
                                bookList.remove(i);
                                adapter.notifyItemRemoved(i);
                                break;
                            }
                        }
                    }
                }
        );



        // passes the click listener to the adapter
        adapter = new BookAdapter(getContext(), bookList, true, book -> {
            Intent intent = new Intent(getContext(), AdminBookDetailFragment.class);
            intent.putExtra("title", book.getTitle());
            intent.putExtra("author", book.getAuthor());
            intent.putExtra("description", book.getDescription());
            editBookLauncher.launch(intent);
        });
        recyclerView.setAdapter(adapter);

        addBookButton = view.findViewById(R.id.addBook);
        addBookButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddBookFragment.class);
            startActivity(intent);
        });

        return view;
    }
}
