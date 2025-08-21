package com.example.comp2000referral.models;

public class Book {
    private long id;
    private String title;
    private String author;
    private String description;

    // for books without ID
    public Book(String title, String author, String description) {
        this.id = 0; // or it will break :(
        this.title = title;
        this.author = author;
        this.description = description;
    }

    // for books WITH ID (from DB)
    public Book(long id, String title, String author, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
    }

    // Getters
    public long getId() { return id; }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getDescription() { return description; }

    // Setters
    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setDescription(String description) { this.description = description; }
}
