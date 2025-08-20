package com.example.comp2000referral.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000referral.R;
import com.example.comp2000referral.models.Book;

import java.util.List;

// recyclerview adapter
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    // connects to book.java
     Context context;
     List<Book> books;
     boolean isAdmin;
     BookClickListener listener;

    public BookAdapter(Context context, List<Book> books, boolean isAdmin, BookClickListener listener) { //is admin will check user type and lister will handle the clicks in the fragment
        this.context = context;
        this.books = books;
        this.isAdmin = isAdmin;
        this.listener = listener;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView authorTextView;
        TextView descTextView;

        public BookViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.bookTitle);
            authorTextView = itemView.findViewById(R.id.bookAuthor);
            descTextView = itemView.findViewById(R.id.bookDesc);
        }
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_items, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        final Book book = books.get(position);
        holder.titleTextView.setText(book.getTitle());
        holder.authorTextView.setText(book.getAuthor());
        holder.descTextView.setText(book.getDescription());

        // calls listener when item is clicked
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBookClick(book);
            }
        });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    // this helps the adapter not decide what happens, just notifies
    public interface BookClickListener {
        void onBookClick(Book book);
    }

    // updates books when admin adds
    public void updateBooks(List<Book> newBooks) {
        this.books.clear();
        this.books.addAll(newBooks);
        notifyDataSetChanged();
    }


}