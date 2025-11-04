package com.ics.server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class CourseEntity {
  @Id
  private String id;
  
  @Column(unique = true)
  private String code;
  
  private String title;
  private int credits;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getCredits() {
    return credits;
  }

  public void setCredits(int credits) {
    this.credits = credits;
  }
}





