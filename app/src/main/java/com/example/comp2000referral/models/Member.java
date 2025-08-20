package com.example.comp2000referral.models;

// changed to reflect API
public class Member {

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String contact;
    private String membershipEndDate;

    public Member(String username, String firstname, String lastname, String email, String contact, String membershipEndDate) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.contact = contact;
        this.membershipEndDate = membershipEndDate;
    }

    public String getUsername() { return username; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getFullname() { return firstname + " " + lastname; }
    public String getEmail() { return email; }
    public String getContact() { return contact; }
    public String getMembershipEndDate() { return membershipEndDate; }
}
