package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.mapper.UserMapper;
import com.revolut.money.transfer.model.User;
import org.apache.ibatis.session.SqlSessionManager;

public class UserRepository extends AbstractRepository<User, Long> {

    public UserRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public void create(User user) {
        sessionManager.getMapper(UserMapper.class).create(user);
    }

    @Override
    public User read(Long id) {
        return sessionManager.getMapper(UserMapper.class).read(id);
    }

    @Override
    public void update(User user) {
        sessionManager.getMapper(UserMapper.class).update(user);
    }

    @Override
    public void delete(User user) {
        sessionManager.getMapper(UserMapper.class).delete(user);
    }

}
