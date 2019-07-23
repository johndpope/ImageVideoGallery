package com.deepesh.imagevideogallery.model;

public class Details {

    private String name;
    private String url;
    private String cat;
    private String price;

    public Details() {
    }

    public Details(String name, String url, String cat, String price) {
        this.name = name;
        this.url = url;
        this.cat = cat;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Details{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", cat='" + cat + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
