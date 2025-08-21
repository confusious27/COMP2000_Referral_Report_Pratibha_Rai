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
    private List<Book> books;
    private OnBookClickListener listener;
    private Context context;
    private boolean isAdmin;

    public interface OnBookClickListener {
        void onBookClicked(Book book);
    }

    public BookAdapter(Context context, List<Book> books, boolean isAdmin, OnBookClickListener listener) {
        this.context = context;
        this.books = books;
        this.isAdmin = isAdmin;
        this.listener = listener;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_items, parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        holder.description.setText(book.getDescription());

        holder.itemView.setOnClickListener(v -> listener.onBookClicked(book));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, description;

        public BookViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookTitle);
            author = itemView.findViewById(R.id.bookAuthor);
            description = itemView.findViewById(R.id.bookDesc);
        }
    }
}