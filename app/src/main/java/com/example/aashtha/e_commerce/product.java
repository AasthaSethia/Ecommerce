package com.example.aashtha.e_commerce;

public class product {
    private String pname;
    private  String pdetails;
    private int imgURL;

    public product(String name, String details,int imgURL ) {
        this.pdetails = details;
        this.pname = name;
        this.imgURL = imgURL;

    }
    public  int getimgURL() {
        return imgURL;
    }

    public void setimgURL(int imgURL) {
        this.imgURL =imgURL;
    }

    public  String getdetails() {
        return pdetails;
    }

    public void setDetails(String details) {
        this.pdetails = details;
    }

    public  String getname() {
        return pname;
    }

    public void setname(String name) {
        this.pname = name;
    }
}
