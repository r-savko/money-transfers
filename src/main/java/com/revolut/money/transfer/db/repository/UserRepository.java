package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.mapper.UserMapper;
import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.User;
import org.apache.ibatis.session.SqlSessionManager;

import java.util.List;
import java.util.Optional;

/**
 * Repository to work with users
 */
public class UserRepository extends AbstractRepository<User, Long> {

    public UserRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public void create(User user) {
        sessionManager.getMapper(UserMapper.class).create(user);
    }

    @Override
    public Optional<User> read(Long userId) {
        return sessionManager.getMapper(UserMapper.class).read(userId);
    }

    @Override
    public void update(User user) {
        sessionManager.getMapper(UserMapper.class).update(user);
    }

    @Override
    public void delete(Long userId) {
        sessionManager.getMapper(UserMapper.class).delete(userId);
    }

    public List<Account> readUserAccounts(Long userId) {
        return sessionManager.getMapper(UserMapper.class).readUserAccounts(userId);
    }

}
