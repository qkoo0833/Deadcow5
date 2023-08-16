package com.deadcow5.alarm.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.deadcow5.alarm.domain.Alarm;
import com.deadcow5.alarm.domain.User;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AlarmsRepository {

    private final EntityManager em;

    public void save(Alarm alarm) {
        em.persist(alarm);
    }

    public void updateUser(Alarm alarm, Long userId) {
        User user = em.find(User.class, userId);
        alarm.setUser(user);
        em.merge(alarm);
        em.flush();
    }

    public Alarm findOne(Long id) {
        return em.find(Alarm.class, id);
    }

    public Alarm findByUuid(String uuid) {
        return em.createQuery("select a from Alarm a where a.uuid = :uuid", Alarm.class)
            .setParameter("uuid", uuid)
            .getSingleResult();
    }

    public List<Alarm> findAllByUserId(Long id) {
        return em.createQuery("select a from Alarm a join a.user u where u.id=:uid", Alarm.class)
            .setParameter("uid", id)
            .getResultList();
    }
    
}
