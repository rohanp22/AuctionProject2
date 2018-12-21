package com.example.apple.auctionproject.model;

public class User {

    private String id;
    private String username;
    private String email;
    private String firstname;
    private String balance;
    private String address;
    private String contactno;
    private String pincode;
    private String lastname;

    public User(String id, String username, String email, String firstname,String lastname, String balance,String address,String contactno,String pincode){
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.balance = balance;
        this.address = address;
        this.contactno = contactno;
        this.pincode = pincode;
        this.lastname = lastname;
    }

    public String getId() {
        return id;
    }

    public String getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    public String getContactno() {
        return contactno;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPincode() {
        return pincode;
    }
}
