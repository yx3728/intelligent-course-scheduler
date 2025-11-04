package com.ics.server.mapper;

import com.ics.common.dto.*;
import com.ics.server.entity.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {
  public Section toSection(SectionEntity e) {
    return new Section(
        e.getId(),
        e.getCourse().getId(),
        e.getBuilding().getId(),
        e.getInstructor(),
        e.getMeetingTimes().stream()
            .map(mt -> new MeetingTime(mt.getDay(), mt.getStart(), mt.getEnd()))
            .collect(Collectors.toList())
    );
  }
  
  public Building toBuilding(BuildingEntity e) {
    return new Building(e.getId(), e.getName(), e.getCampus(), e.getLat(), e.getLon());
  }
  
  public Course toCourse(CourseEntity e) {
    return new Course(e.getId(), e.getCode(), e.getTitle(), e.getCredits());
  }
}





