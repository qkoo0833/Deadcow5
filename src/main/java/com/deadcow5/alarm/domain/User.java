package com.deadcow5.alarm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name= "user_table")
public class User {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name = "USER_ID")
  private Long id;
  private String name;

  public User(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return String.format(
        "User[id=%d, Name='%s']",
        id, name
    );
  }
}