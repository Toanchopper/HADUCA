package com.example.haduca;

import android.graphics.Bitmap;

public class Cake {
    private int pos;
    private Bitmap picture;
    private String id;
    private int price;
    private String message;
    private int prepaid;

    public Cake(int pos,Bitmap picture, String id, int price, String message, int prepaid) {
        this.pos=pos;
        this.picture = picture;
        this.id = id;
        this.price = price;
        this.message = message;
        this.prepaid = prepaid;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPrepaid() {
        return prepaid;
    }

    public void setPrepaid(int prepaid) {
        this.prepaid = prepaid;
    }

}
