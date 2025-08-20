package com.example.comp2000referral;

import java.io.Serializable; //to pack it into a "bundle" and move the data

public class Request implements Serializable {
    private String bookTitle;
    private String requesterName;
    private String status;

    public Request(String bookTitle, String requesterName, String status) {
        this.bookTitle = bookTitle;
        this.requesterName = requesterName;
        this.status = status;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
