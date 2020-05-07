package com.termos.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Authority extends AbstractModel {


    @JsonProperty("username")
    private String username;
    @JsonProperty("authority")
    private String authority;

    public Authority() {
        super();
    }

    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }


    @Override
    public String toString() {
        return "Authority{" +
                "username='" + username + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }

    @Override
    public String getId() {
        return username;
    }
}

