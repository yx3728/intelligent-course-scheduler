package com.ics.engine.algorithms;

import com.ics.common.dto.MeetingTime;
import com.ics.common.dto.Section;
import java.time.LocalTime;
import java.util.*;

public class TimeConflictDetector {
  private static class Interval {
    LocalTime s, e;
    Interval(LocalTime s, LocalTime e) {
      this.s = s;
      this.e = e;
    }
  }

  private static Interval toInterval(MeetingTime t) {
    return new Interval(LocalTime.parse(t.start()), LocalTime.parse(t.end()));
  }

    public static boolean hasOverlap(String start1Str, String end1Str, String start2Str, String end2Str) {
        try {
            LocalTime start1 = LocalTime.parse(start1Str);
            LocalTime end1 = LocalTime.parse(end1Str);
            LocalTime start2 = LocalTime.parse(start2Str);
            LocalTime end2 = LocalTime.parse(end2Str);

            return start1.isBefore(end2) && end1.isAfter(start2);

        } catch (Exception e) {
            return false;
        }
    }

  public static boolean hasConflict(List<Section> sections) {
    Map<String, List<Interval>> byDay = new HashMap<>();
    
    for (Section s : sections) {
      for (MeetingTime mt : s.meetingTimes()) {
        byDay.computeIfAbsent(mt.day(), k -> new ArrayList<>()).add(toInterval(mt));
      }
    }
    
    for (var entry : byDay.entrySet()) {
      var list = entry.getValue();
      list.sort(Comparator.comparing(a -> a.s));
      
      for (int i = 1; i < list.size(); i++) {
        if (!list.get(i).s.isAfter(list.get(i - 1).e)) {
          return true; // overlap or touch
        }
      }
    }
    return false;
  }
}





