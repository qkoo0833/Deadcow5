package com.deadcow5.alarm.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deadcow5.alarm.domain.Alarm;
import com.deadcow5.alarm.domain.Message;
import com.deadcow5.alarm.dto.AlarmCreateDto;
import com.deadcow5.alarm.service.AlarmService;
import com.deadcow5.alarm.service.Producer;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AlarmController {
    
    private final AlarmService alarmService;
    private final Producer producer;

    @GetMapping(value = "/alarm/{id}")
    public ResponseEntity<List<Alarm>> getAllAlarms(@PathVariable Long id) {
        List<Alarm> alarms = alarmService.findAlarms(id);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(alarms);
    }

    @PostMapping(value = "/alarm")
    public ResponseEntity<Message> createAlarm(@RequestBody AlarmCreateDto alarmRequest) throws Exception{
        Message message = new Message(
            alarmRequest.getUserId(),
            alarmRequest.getTitle(),
            alarmRequest.getContent(),
            alarmRequest.getUrl()
        );
        producer.sendMessage(message);
        TimeUnit.MILLISECONDS.sleep(100);
        alarmService.updateAlarm(message.getUuid(), message.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }
}
