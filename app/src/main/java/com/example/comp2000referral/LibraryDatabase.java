package com.example.comp2000referral;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.comp2000referral.models.Book;

import java.util.ArrayList;
import java.util.List;

// FOR THE BOOK CATALOGUE
public class LibraryDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "libraryBooks.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_BOOKS = "books";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_DESCRIPTION = "description";

    public LibraryDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE " + TABLE_BOOKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT NOT NULL, "
                + COLUMN_AUTHOR + " TEXT NOT NULL, "
                + COLUMN_DESCRIPTION + " TEXT NOT NULL "
                + ")"
       );

       // sample books
       insertBook(db, "1984", "George Orwell", "A dystopian novel about surveillance.");
       insertBook(db, "Brave New World", "Aldous Huxley", "A futuristic society based on control.");
       insertBook(db, "Fahrenheit 451", "Ray Bradbury", "A world where books are outlawed.");
       insertBook(db, "Omniscient Reader's Viewpoint", "SingShong", "Kim Dokja finds himself inside his favourite web novel.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }

    // CRUD

    // CREATE
    public long insertBook(String title, String author, String description) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_AUTHOR, author);
        values.put(COLUMN_DESCRIPTION, description);

        try (SQLiteDatabase db = getWritableDatabase()) {
            return db.insert(TABLE_BOOKS, null, values);
        }
    }

    // internal helper
    private long insertBook(SQLiteDatabase db, String title, String author, String description) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_AUTHOR, author);
        values.put(COLUMN_DESCRIPTION, description);
        return db.insert(TABLE_BOOKS, null, values);
    }

    // UPDATE
    public int updateBook(long id, String title, String author, String description) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, title);
            values.put(COLUMN_AUTHOR, author);
            values.put(COLUMN_DESCRIPTION, description);
            return db.update(TABLE_BOOKS, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        }
    }

    // DELETE
    public int deleteBook(long id) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            return db.delete(TABLE_BOOKS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        }
    }

    // READ: Get book by ID
    public Book getBookById(long id) {
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.query(TABLE_BOOKS,
                     new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_AUTHOR, COLUMN_DESCRIPTION},
                     COLUMN_ID + "=?",
                     new String[]{String.valueOf(id)},
                     null, null, null)) {

            if (cursor.moveToFirst()) {
                return new Book(
                        cursor.getLong(0),   // id
                        cursor.getString(1), // title
                        cursor.getString(2), // author
                        cursor.getString(3)  // description
                );
            }
            return null;
        }
    }

    // READ all
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor c = db.query(TABLE_BOOKS,
                     new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_AUTHOR, COLUMN_DESCRIPTION},
                     null, null, null, null, COLUMN_TITLE + " ASC")) {

            while (c.moveToNext()) {
                books.add(new Book(
                        c.getLong(0),   // id
                        c.getString(1), // title
                        c.getString(2), // author
                        c.getString(3)  // description
                ));
            }
        }
        return books;
    }
}