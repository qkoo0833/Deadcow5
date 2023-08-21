package com.deadcow5.alarm.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deadcow5.alarm.domain.Alarm;
import com.deadcow5.alarm.repository.AlarmsRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlarmService {
    
    private final AlarmsRepository alarmsRepository;
    private final RabbitTemplate rabbitTemplate;

    /*
     * 알람 조회
     */
    public List<Alarm> findAlarms(Long userId) {
        return alarmsRepository.findAllByUserId(userId);
    }

    public void clearQueue(String queueName) {
        rabbitTemplate.receiveAndConvert(queueName);
    }

    @Transactional
    public void updateAlarm(String uuid, Long userId) {
        Alarm alarm = alarmsRepository.findByUuid(uuid);
        alarmsRepository.updateUser(alarm, userId);
    }
}
