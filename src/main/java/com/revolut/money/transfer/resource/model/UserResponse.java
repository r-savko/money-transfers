package com.revolut.money.transfer.resource.model;

public class UserResponse {

    private long userId;
    private String name;
    private String surname;

    public long getUserId() {
        return userId;
    }

    public UserResponse setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public UserResponse setSurname(String surname) {
        this.surname = surname;
        return this;
    }
}
