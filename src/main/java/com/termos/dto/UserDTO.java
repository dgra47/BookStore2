package com.termos.dto;

import com.termos.validator.EmailValid;
import com.termos.validator.PasswordMatches;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@PasswordMatches
public class UserDTO extends AbstractDTO{
    public String userId;

    @NotNull
    @NotEmpty
    public String city;

    @NotNull
    @NotEmpty
    public String firstName;

    @NotNull
    @NotEmpty
    public String surName;

    public int userTel;

    @NotNull
    public Timestamp regDate;

    @NotNull
    @NotEmpty
    public String username;

    @NotNull
    @NotEmpty
    public String password;

    public String matchingPassword;

    @EmailValid
    @NotNull
    @NotEmpty
    public String email;

    @NotNull
    public String [] authorities;

    @NotNull
    public boolean enabled; }