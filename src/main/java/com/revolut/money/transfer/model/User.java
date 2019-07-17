package com.revolut.money.transfer.model;

import com.google.common.base.Objects;

public class User {

    private Long userId;
    private String name;
    private String surname;

    public long getUserId() {
        return userId;
    }

    public User setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public User setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                Objects.equal(name, user.name) &&
                Objects.equal(surname, user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, name, surname);
    }
}
