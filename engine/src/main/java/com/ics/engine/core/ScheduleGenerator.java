package com.ics.engine.core;

import com.ics.common.dto.Section;
import com.ics.engine.algorithms.TimeConflictDetector;
import java.util.*;

public class ScheduleGenerator {
  private final ScheduleScorer scorer = new ScheduleScorer();

  public List<List<Section>> generate(List<List<Section>> byCourse, int topK) {
    List<List<Section>> results = new ArrayList<>();
    backtrack(byCourse, 0, new ArrayList<>(), results, topK);
    results.sort(Comparator.comparingDouble((List<Section> ss) -> -scorer.score(ss)));
    return results.size() > topK ? results.subList(0, topK) : results;
  }

  private void backtrack(
      List<List<Section>> byCourse,
      int idx,
      List<Section> cur,
      List<List<Section>> out,
      int topK
  ) {
    if (out.size() >= topK) return;
    if (idx == byCourse.size()) {
      out.add(new ArrayList<>(cur));
      return;
    }
    
    for (Section s : byCourse.get(idx)) {
      cur.add(s);
      if (!TimeConflictDetector.hasConflict(cur)) {
        backtrack(byCourse, idx + 1, cur, out, topK);
      }
      cur.remove(cur.size() - 1);
    }
  }
}





