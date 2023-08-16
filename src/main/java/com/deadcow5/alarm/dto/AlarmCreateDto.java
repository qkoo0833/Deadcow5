package com.deadcow5.alarm.dto;

import lombok.Getter;

@Getter
public class AlarmCreateDto {
    private Long userId;
    private String title;
    private String content;
    private String url;
}
