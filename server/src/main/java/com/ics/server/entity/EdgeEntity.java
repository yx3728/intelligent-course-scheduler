package com.ics.server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "edges")
public class EdgeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String fromBuildingId;
  private String toBuildingId;
  private int walkMinutes;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFromBuildingId() {
    return fromBuildingId;
  }

  public void setFromBuildingId(String fromBuildingId) {
    this.fromBuildingId = fromBuildingId;
  }

  public String getToBuildingId() {
    return toBuildingId;
  }

  public void setToBuildingId(String toBuildingId) {
    this.toBuildingId = toBuildingId;
  }

  public int getWalkMinutes() {
    return walkMinutes;
  }

  public void setWalkMinutes(int walkMinutes) {
    this.walkMinutes = walkMinutes;
  }
}





