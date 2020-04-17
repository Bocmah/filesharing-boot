package com.example.filesharing.domain;

public class Credentials {
    final private String username;

    final private String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
