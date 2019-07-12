package com.revolut.money.transfer.service;

import com.revolut.money.transfer.db.repository.UserRepository;
import com.revolut.money.transfer.model.User;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUser(Long userId) {
        return userRepository.read(userId);
    }
}
