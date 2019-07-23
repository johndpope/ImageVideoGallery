package com.deepesh.imagevideogallery.model;

public class User {
    private String name;
    private String email;
    private String mob;
    private String pass;
    private String imageurl;
    private String token;

    public User() {
    }

    public User(String name, String email, String mob, String pass, String imageurl, String token) {
        this.name = name;
        this.email = email;
        this.mob = mob;
        this.pass = pass;
        this.imageurl = imageurl;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mob='" + mob + '\'' +
                ", pass='" + pass + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
