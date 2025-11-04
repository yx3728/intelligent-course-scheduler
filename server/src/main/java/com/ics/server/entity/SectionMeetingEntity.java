package com.ics.server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "section_meetings")
public class SectionMeetingEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "day_of_week")
  private String day;
  
  @Column(name = "start_time")
  private String start;
  
  @Column(name = "end_time")
  private String end;
  
  @ManyToOne
  @JoinColumn(name = "section_id")
  private SectionEntity section;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public SectionEntity getSection() {
    return section;
  }

  public void setSection(SectionEntity section) {
    this.section = section;
  }
}





