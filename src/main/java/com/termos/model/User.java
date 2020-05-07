package com.termos.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.util.List;


public class User extends AbstractModel {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("city")
    private String city;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("sur_name")
    private String surName;
    @JsonProperty("user_tel")
    private int userTel;
    @JsonProperty("reg_date")
    private Timestamp regDate;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("email")
    private String email;
    @JsonProperty("authority")
    private List<Authority> authorities;
    @JsonProperty("enabled")
    private boolean enabled;

    public User() {
    }


    public User(String userId, String city, String firstName, String surName, int userTel, Timestamp regDate, String username, String password, String email, boolean enabled) {
        this.userId = userId;
        this.city = city;
        this.firstName = firstName;
        this.surName = surName;
        this.userTel = userTel;
        this.regDate = regDate;
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public int getUserTel() {
        return userTel;
    }

    public void setUserTel(int userTel) {
        this.userTel = userTel;
    }

    public Timestamp getRegDate() {
        return regDate;
    }

    public void setRegDate(Timestamp regDate) {
        this.regDate = regDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public List<Authority> getAuthority() {
        return authorities;
    }

    public void setAuthority(List<Authority> authority) {
        this.authorities = authority;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", city='" + city + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                ", userTel=" + userTel +
                ", regDate=" + regDate +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", authority='" + authorities+ '\'' +
                ", enabled='" + enabled + '\'' +
                '}';
    }


    @Override
    public String getId() {
        return null;
    }
}