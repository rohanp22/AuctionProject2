package com.example.apple.auctionproject.model;

public class CartItems {

    public String image_url,mybid,status,titleCart;

    public CartItems(String image_url,String mybid,String status,String titleCart){
        this.image_url = image_url;
        this.mybid = mybid;
        this.status = status;
        this.titleCart = titleCart;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getMybid() {
        return mybid;
    }

    public String getStatus() {
        return status;
    }

    public String getTitleCart() {
        return titleCart;
    }
}

