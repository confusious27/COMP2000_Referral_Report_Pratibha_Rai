package com.example.comp2000referral;

public class Member {

    private String name;
    private String profileUrl;

    public Member(String name, String profileUrl) {
        this.name = name;
        this.profileUrl = profileUrl;
    }

    public String getName() {
        return name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

}
