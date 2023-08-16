package com.deadcow5.alarm.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.deadcow5.alarm.domain.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Producer {
    private final RabbitTemplate rabbitTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public String sendMessage(Message message) {
        try {
            System.out.println("Sending message.."+message.getUuid());
            String jsonMessage = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend("alarm-exchange", "alarm.key", jsonMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return message.getUuid();
    }
}
