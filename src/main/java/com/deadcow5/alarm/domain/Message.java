package com.deadcow5.alarm.domain;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Message {
    private Long userId;
    private String title;
    private String content;
    private String url;
    private String uuid;
    public Message(Long userId, String title, String content, String url) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.url = url;
        this.uuid = UUID.randomUUID().toString();
    }
    
}
