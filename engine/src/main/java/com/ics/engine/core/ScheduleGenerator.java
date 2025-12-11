package com.ics.engine.core;

import com.ics.common.dto.Section;
import com.ics.engine.algorithms.TimeConflictDetector;
import java.util.*;
import com.ics.common.dto.UserPrefs;

public class ScheduleGenerator {
  private final ScheduleScorer scorer = new ScheduleScorer();

  public List<List<Section>> generate(List<List<Section>> byCourse, UserPrefs prefs,int topK) {
    List<List<Section>> results = new ArrayList<>();
    backtrack(byCourse, 0, new ArrayList<>(), results, topK);
      results.sort((s1, s2) -> {
          double score1 = calculateScore(s1, prefs);
          double score2 = calculateScore(s2, prefs);
          return Double.compare(score2, score1);
      });
    return results.size() > topK ? results.subList(0, topK) : results;
  }

    private double calculateScore(List<Section> schedule, UserPrefs prefs) {
        double baseScore = scorer.score(schedule); // 原有的打分逻辑

        if (prefs != null && prefs.preferredStartTime() != null) {
            int minHour = prefs.preferredStartTime();
            int penaltyCount = countTooEarlyClasses(schedule, minHour);

            baseScore -= (penaltyCount * 50.0);
        }
        return baseScore;
    }

    private int countTooEarlyClasses(List<Section> schedule, int minHour) {
        int count = 0;
        for (Section s : schedule) {
            for (var mt : s.meetingTimes()) {
                try {
                    String startStr = mt.start();
                    int hour;
                    if (startStr.contains(":")) {
                        hour = Integer.parseInt(startStr.split(":")[0]);
                    } else {
                        hour = Integer.parseInt(startStr);
                    }

                    if (hour < minHour) {
                        count++;
                        break;
                    }
                } catch (Exception e) {
                    //ignore
                }
            }
        }
        return count;
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





