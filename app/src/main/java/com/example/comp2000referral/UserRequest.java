package com.example.comp2000referral;

public class UserRequest {
    private String bookTitle;
    private String status;

    public UserRequest(String bookTitle, String status) {
        this.bookTitle = bookTitle;
        this.status = status;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getStatus() {
        return status;
    }
}
