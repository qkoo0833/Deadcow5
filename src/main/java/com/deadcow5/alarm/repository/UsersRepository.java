package com.deadcow5.alarm.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.deadcow5.alarm.domain.User;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UsersRepository {
    
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }
    
    public User findOne(Long id) {
        // return em.createQuery("select u from User u where u.id = :id", User.class)
        //     .setParameter("id", id)
        //     .getSingleResult();
        return em.find(User.class, id);
    }

    public List<User> findByName(String name) {
        return em.createQuery("select u from User u where u.name = :name", User.class)
            .setParameter("name", name)
            .getResultList();
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
            .getResultList();
    }
}
