package com.deadcow5.alarm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deadcow5.alarm.domain.User;
import com.deadcow5.alarm.repository.UsersRepository;

import lombok.RequiredArgsConstructor;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    
    private final UsersRepository usersRepository;

    /*
     * 유저 생성
     */
    @Transactional
    public Long join(User user) {
        validateDuplicateUser(user);
        usersRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        List<User> findUsers = usersRepository.findByName(user.getName());
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 사용자입니다.");
        }
    }

    /*
     * 유저 목록 조회
     */
    public List<User> findUsers() {
        return usersRepository.findAll();
    }

    public User findOne(Long userId) {
        return usersRepository.findOne(userId);
    }
}
