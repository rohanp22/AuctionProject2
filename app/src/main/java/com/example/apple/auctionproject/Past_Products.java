package com.example.apple.auctionproject;

public class Past_Products {

    private String id;
    private String image_url;
    private String title;
    private String end_date;
    private String mrp;
    private String sp;

    public Past_Products(String id,String image_url,String title,String end_date,String mrp,String sp){
        this.end_date = end_date;
        this.title = title;
        this.image_url = image_url;
        this.id = id;
        this.mrp = mrp;
        this.sp = sp;
    }

    public String getMrp() {
        return mrp;
    }

    public String getSp() {
        return sp;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getId() {
        return id;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getTitle() {
        return title;
    }
}
