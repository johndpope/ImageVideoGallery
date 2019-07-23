package com.deepesh.imagevideogallery.model;

import android.media.Image;

public class MyData {
    private String name;
    private String hashtag;
    private int thumbnamil;
    private int price;

    public MyData() {
    }

    public MyData(String name, String hashtag, int thumbnamil, int price) {
        this.name = name;
        this.hashtag = hashtag;
        this.thumbnamil = thumbnamil;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public int getThumbnamil() {
        return thumbnamil;
    }

    public void setThumbnamil(int thumbnamil) {
        this.thumbnamil = thumbnamil;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "MyData{" +
                "name='" + name + '\'' +
                ", hashtag='" + hashtag + '\'' +
                ", thumbnamil=" + thumbnamil +
                ", price=" + price +
                '}';
    }
}
