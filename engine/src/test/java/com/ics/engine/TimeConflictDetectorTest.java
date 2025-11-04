package com.ics.engine;

import com.ics.engine.algorithms.TimeConflictDetector;
import com.ics.common.dto.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TimeConflictDetectorTest {
  @Test
  void detectsOverlap() {
    var s1 = new Section("s1", "c1", "b1", "i", List.of(new MeetingTime("MON", "09:00", "10:00")));
    var s2 = new Section("s2", "c2", "b1", "i", List.of(new MeetingTime("MON", "09:30", "10:30")));
    assertTrue(TimeConflictDetector.hasConflict(List.of(s1, s2)));
  }
  
  @Test
  void detectsNoOverlap() {
    var s1 = new Section("s1", "c1", "b1", "i", List.of(new MeetingTime("MON", "09:00", "10:00")));
    var s2 = new Section("s2", "c2", "b1", "i", List.of(new MeetingTime("MON", "11:00", "12:00")));
    assertFalse(TimeConflictDetector.hasConflict(List.of(s1, s2)));
  }
  
  @Test
  void detectsTouchAsConflict() {
    var s1 = new Section("s1", "c1", "b1", "i", List.of(new MeetingTime("MON", "09:00", "10:00")));
    var s2 = new Section("s2", "c2", "b1", "i", List.of(new MeetingTime("MON", "10:00", "11:00")));
    assertTrue(TimeConflictDetector.hasConflict(List.of(s1, s2))); // touch counts as conflict
  }
  
  @Test
  void noConflictDifferentDays() {
    var s1 = new Section("s1", "c1", "b1", "i", List.of(new MeetingTime("MON", "09:00", "10:00")));
    var s2 = new Section("s2", "c2", "b1", "i", List.of(new MeetingTime("TUE", "09:00", "10:00")));
    assertFalse(TimeConflictDetector.hasConflict(List.of(s1, s2)));
  }
}





