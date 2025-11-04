package com.ics.server.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "sections")
public class SectionEntity {
  @Id
  private String id;
  
  @ManyToOne
  @JoinColumn(name = "course_id")
  private CourseEntity course;
  
  @ManyToOne
  @JoinColumn(name = "building_id")
  private BuildingEntity building;
  
  private String instructor;
  
  @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<SectionMeetingEntity> meetingTimes = new ArrayList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public CourseEntity getCourse() {
    return course;
  }

  public void setCourse(CourseEntity course) {
    this.course = course;
  }

  public BuildingEntity getBuilding() {
    return building;
  }

  public void setBuilding(BuildingEntity building) {
    this.building = building;
  }

  public String getInstructor() {
    return instructor;
  }

  public void setInstructor(String instructor) {
    this.instructor = instructor;
  }

  public List<SectionMeetingEntity> getMeetingTimes() {
    return meetingTimes;
  }

  public void setMeetingTimes(List<SectionMeetingEntity> meetingTimes) {
    this.meetingTimes = meetingTimes;
  }
}





