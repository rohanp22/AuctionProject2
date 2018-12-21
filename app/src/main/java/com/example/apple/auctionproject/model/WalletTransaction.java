package com.example.apple.auctionproject.model;

public class WalletTransaction {

    public String orderid;
    public String value;
    public String description;
    public String date;

    public WalletTransaction(String orderid,String value,String description,String date){
        this.orderid = orderid;
        this.value = value;
        this.description = description;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getOrderid() {
        return orderid;
    }

    public String getValue() {
        return value;
    }
}
