package com.deadcow5.alarm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;

@Getter
@Entity
@Table(name = "alarms")
public class Alarm {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name = "alarm_id")
  private Long id;
  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private User user;
  private String title;
  private String content;
  private String url;
  private String uuid;
  @CreationTimestamp
  @Column(name = "CREATION_TIMESTAMP")
  private Timestamp creationTimestamp;
  @Column(name = "REQUEST_TIMESTAMP")
  private Timestamp requestTimestamp;
  @Column(name = "RESERVATION_TIMESTAMP")
  private Timestamp reservationTimestamp;


  protected Alarm() {}

  public Alarm(String title, String content, String url, String uuid) {
    this.title = title;
    this.content = content;
    this.url = url;
    this.uuid = uuid;
  }
  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return String.format(
        "User[id=%d, title='%s', content='%s']",
        id, title, content
    );
  }
}