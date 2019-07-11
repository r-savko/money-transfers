package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.entity.User;
import com.revolut.money.transfer.db.mapper.UserMapper;
import org.apache.ibatis.session.SqlSessionManager;

public class UserRepository extends AbstractRepository<User, Long> {

    public UserRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public Long persist(User entity) {
        return sessionManager.getMapper(UserMapper.class).persist(entity);
    }

    @Override
    public User find(Long id) {
        return sessionManager.getMapper(UserMapper.class).find(id);
    }

    @Override
    public Long update(User entity) {
        return sessionManager.getMapper(UserMapper.class).update(entity);
    }

    @Override
    public Long delete(Long id) {
        return sessionManager.getMapper(UserMapper.class).delete(id);
    }

}
