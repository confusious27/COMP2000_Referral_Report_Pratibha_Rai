package com.example.comp2000referral;

public class Request {
    private String bookTitle;
    private String requesterName;

    public Request(String bookTitle, String requesterName) {
        this.bookTitle = bookTitle;
        this.requesterName = requesterName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getRequesterName() {
        return requesterName;
    }
}
