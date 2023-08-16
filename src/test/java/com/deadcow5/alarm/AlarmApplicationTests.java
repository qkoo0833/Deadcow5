package com.deadcow5.alarm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.deadcow5.alarm.domain.Alarm;
import com.deadcow5.alarm.domain.Message;
import com.deadcow5.alarm.domain.User;
import com.deadcow5.alarm.repository.AlarmsRepository;
import com.deadcow5.alarm.repository.UsersRepository;
import com.deadcow5.alarm.service.AlarmService;
import com.deadcow5.alarm.service.Producer;
import com.deadcow5.alarm.service.UserService;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
class AlarmApplicationTests {

	@Autowired
    UserService userService;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    AlarmsRepository alarmsRepository;
    @Autowired
    AlarmService alarmService;
    @Autowired
    Producer producer;
    @Autowired
    EntityManager em;

    @BeforeEach
    public void clearQueue() {
        alarmService.clearQueue("alarm-queue");
    }

    @Test
    public void userCreate() throws Exception {
        String name = "Koosh";
        User user = new User(name);
        
        Long saveId = userService.join(user);

        assertEquals(user, usersRepository.findOne(saveId));
    }

    @Test
    public void duplicatedUserName() throws Exception {
        String name = "Koosh";
        User user1 = new User(name);
        User user2 = new User(name);

        assertThrows(IllegalStateException.class,() -> {
            userService.join(user1);
            userService.join(user2);
        });
    }
    
    @Test
    public void produceAndConsume() throws Exception {
        String title = "hello";
        String content = "My name is koosh";
        String url = "https://www.naver.com";
        User user1 = new User("koosh");
        userService.join(user1);
        Message message = new Message(user1.getId(), title, content, url);
        em.flush();

        String uuid = producer.sendMessage(message);
        TimeUnit.MILLISECONDS.sleep(100);
        alarmService.updateAlarm(uuid, user1.getId());

        Alarm alarm = alarmsRepository.findByUuid(uuid);
        assertEquals(title, alarm.getTitle());
        assertEquals(content, alarm.getContent());
        assertEquals(url, alarm.getUrl());
    }

    @Test
    public void getAlarms() throws Exception {
        String title1 = "hello koosh";
        String content1 = "My name is koosh";
        String url1 = "https://www.naver.com";
        String title2 = "greeting";
        String content2 = "Nice to meet you";
        String url2 = "https://www.naver.com";
        User user1 = new User("koosh");
        Long userId = userService.join(user1);
        Message message1 = new Message(userId, title1, content1, url1);
        Message message2 = new Message(userId, title2, content2, url2);
        em.flush();

        String uuid1 = producer.sendMessage(message1);
        TimeUnit.MILLISECONDS.sleep(100);
        String uuid2 = producer.sendMessage(message2);
        TimeUnit.MILLISECONDS.sleep(100);
        alarmService.updateAlarm(uuid1, userId);
        alarmService.updateAlarm(uuid2, userId);

        List<Alarm> alarms = alarmService.findAlarms(userId);
        assertEquals(title1,alarms.get(0).getTitle());
        assertEquals(content1,alarms.get(0).getContent());
        assertEquals(url1,alarms.get(0).getUrl());
        assertEquals(title2,alarms.get(1).getTitle());
        assertEquals(content2,alarms.get(1).getContent());
        assertEquals(url2,alarms.get(1).getUrl());
    }
}
