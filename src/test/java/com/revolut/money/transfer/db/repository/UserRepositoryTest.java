package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.mapper.UserMapper;
import com.revolut.money.transfer.model.User;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    private static final Long USER_ID = 1L;
    private static final User USER = new User().setUserId(USER_ID).setName("Test name").setSurname("Test surname");

    private UserMapper userMapper;
    private UserRepository userRepository;
    private SqlSessionManager sqlSessionManager = mock(SqlSessionManager.class);

    @BeforeEach
    void setup() {
        userRepository = new UserRepository(sqlSessionManager);
        userMapper = mock(UserMapper.class);
        when(sqlSessionManager.getMapper(UserMapper.class)).thenReturn(userMapper);
    }

    @Test
    void createTest() {
        // When
        userRepository.create(USER);

        // Then
        verify(userMapper).create(eq(USER));
    }

    @Test
    void readTest() {
        // When
        userRepository.read(USER_ID);

        // Then
        verify(userMapper).read(USER_ID);
    }

    @Test
    void updateTest() {
        // When
        userRepository.update(USER);

        // Then
        verify(userMapper).update(eq(USER));
    }

    @Test
    void deleteTest() {
        // When
        userRepository.delete(USER_ID);

        // Then
        verify(userMapper).delete(USER_ID);
    }

    @Test
    void readUserAccounts() {
        // When
        userRepository.readUserAccounts(USER_ID);

        // Then
        verify(userMapper).readUserAccounts(USER_ID);
    }

}
