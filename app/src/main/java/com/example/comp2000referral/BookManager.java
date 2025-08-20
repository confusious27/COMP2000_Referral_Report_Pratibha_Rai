package com.example.comp2000referral;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.comp2000referral.models.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// to keep the books so it can be shared between admin and user
public class BookManager {

    // constants for sharedpreference
    private static final String PREFS_NAME = "books_prefs";
    private static final String KEY_BOOKS = "books";

    public static List<Book> getBooks(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_BOOKS, null);
        if (json == null) return new ArrayList<>(); // returns empty
        Type listType = new TypeToken<List<Book>>() {}.getType();
        return new Gson().fromJson(json, listType);
    }

    public static void addBook(Context context, Book book) {
        List<Book> books = getBooks(context);
        books.add(book);
        updateBooks(context, books);
    }

    public static void updateBooks(Context context, List<Book> books) { SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = new Gson().toJson(books);
        prefs.edit().putString(KEY_BOOKS, json).apply();
    }

    public static void clearBooks(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_BOOKS).apply();
    }
}
