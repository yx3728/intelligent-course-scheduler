package com.ics.engine.core;

import com.ics.common.dto.Section;
import java.util.List;

public class ScheduleScorer {
  // Simple placeholder: higher score for more sections (can later add travel time, prefs, etc.)
  public double score(List<Section> sections) {
    return sections.size();
  }
}





