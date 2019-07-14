package com.revolut.money.transfer.service;

import com.revolut.money.transfer.db.repository.UserRepository;
import com.revolut.money.transfer.exception.NotFoundException;
import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.User;

import java.util.List;

/**
 * Service class for user manipulation.
 */
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUser(Long userId) {
        return userRepository.read(userId).orElseThrow(() -> new NotFoundException("Unable to found user"));
    }

    public List<Account> findUserAccounts(Long userId) {
        return userRepository.readUserAccounts(userId);
    }

}
