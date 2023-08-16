package com.deadcow5.alarm.service;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deadcow5.alarm.domain.Alarm;
import com.deadcow5.alarm.domain.Message;
import com.deadcow5.alarm.repository.AlarmsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class Receiver {
    private final AlarmsRepository alarmsRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "alarm-queue")
    public void receiveMessage(String jsonMessage) throws JsonMappingException, JsonProcessingException{
        Message message = objectMapper.readValue(jsonMessage, Message.class);
        System.out.println("Received <" + message.getTitle() + ">");
        Alarm alarm = new Alarm(message.getTitle(), message.getContent(), message.getUrl(), message.getUuid());
        alarmsRepository.save(alarm);
    }
}
