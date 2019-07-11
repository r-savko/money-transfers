package com.revolut.money.transfer.model;

public class UserResponse {

    private String name;
    private String surname;

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
