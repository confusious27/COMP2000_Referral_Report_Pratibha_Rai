package com.example.comp2000referral;

public class UserRequest {
    private String bookTitle;
    private String status;
    private String requestedBy; // to extract username

    public UserRequest(String bookTitle, String status, String requestedBy) {
        this.bookTitle = bookTitle;
        this.status = status;
        this.requestedBy = requestedBy;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getStatus() {
        return status;
    }

    public String getRequestedBy() { return requestedBy; }


    // for the request status
    public void setStatus(String status) { this.status = status; }
}
