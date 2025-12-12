package com.ics.engine.core;

import com.ics.common.dto.Section;
import com.ics.engine.algorithms.TimeConflictDetector;
import java.util.*;
import com.ics.common.dto.UserPrefs;

import java.util.stream.Collectors;
import com.ics.common.dto.MeetingTime;

public class ScheduleGenerator {
  private final ScheduleScorer scorer = new ScheduleScorer();

  // Single-Threaded Sequential Processing method:
//  public List<List<Section>> generate(List<List<Section>> byCourse, UserPrefs prefs,int topK) {
//    List<List<Section>> results = new ArrayList<>();
//    backtrack(byCourse, 0, new ArrayList<>(), results, topK);
//      results.sort((s1, s2) -> {
//          double score1 = calculateScore(s1, prefs);
//          double score2 = calculateScore(s2, prefs);
//          return Double.compare(score2, score1);
//      });
//    return results.size() > topK ? results.subList(0, topK) : results;
//  }

  //Parallel Stream Processing
  public List<List<Section>> generate(List<List<Section>> byCourse, UserPrefs prefs, int topK) {
      if (byCourse.isEmpty()) return new ArrayList<>();

      List<MeetingTime> globalExclusions = prefs.excludedMeetingTimes() != null ? prefs.excludedMeetingTimes() : List.of();

      List<Section> firstCourseSections = byCourse.get(0);

      System.out.println("DEBUG: Parallel Stream Processing" + firstCourseSections.size() + " original branches");


      List<List<Section>> allResults = firstCourseSections.parallelStream()
              .map(firstSection -> {
                  System.out.println("DEBUG: thread [" + Thread.currentThread().getName() + "] searching...");

                  if (conflictsWithExclusions(firstSection, globalExclusions)) {
                      return List.<List<Section>>of();
                  }

                  List<List<Section>> threadLocalResults = new ArrayList<>();

                  List<Section> currentPath = new ArrayList<>();
                  currentPath.add(firstSection);

                  backtrack(byCourse, 1, currentPath, threadLocalResults, topK * 2, globalExclusions);
                  return threadLocalResults;
              })
              .flatMap(List::stream)
              .sorted((s1, s2) -> {
                  double score1 = calculateScore(s1, prefs);
                  double score2 = calculateScore(s2, prefs);
                  return Double.compare(score2, score1);
              })
              .collect(Collectors.toList());

      return allResults.size() > topK ? allResults.subList(0, topK) : allResults;
  }

    private double calculateScore(List<Section> schedule, UserPrefs prefs) {
        double baseScore = scorer.score(schedule);

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

    //Single-Threaded Sequential Processing method:
//  private void backtrack(
//      List<List<Section>> byCourse,
//      int idx,
//      List<Section> cur,
//      List<List<Section>> out,
//      int topK
//  ) {
//    if (out.size() >= topK) return;
//    if (idx == byCourse.size()) {
//      out.add(new ArrayList<>(cur));
//      return;
//    }
//
//    for (Section s : byCourse.get(idx)) {
//      cur.add(s);
//      if (!TimeConflictDetector.hasConflict(cur)) {
//        backtrack(byCourse, idx + 1, cur, out, topK);
//      }
//      cur.remove(cur.size() - 1);
//    }
//  }

    //Parallel Stream Processing
    private void backtrack(
            List<List<Section>> byCourse,
            int idx,
            List<Section> cur,
            List<List<Section>> out,
            int limit,
            List<MeetingTime> globalExclusions
    ) {

        if (out.size() >= limit) return;

        if (idx == byCourse.size()) {
            out.add(new ArrayList<>(cur));
            return;
        }

        for (Section s : byCourse.get(idx)) {
            if (conflictsWithExclusions(s, globalExclusions)) {
                continue; // 如果冲突，跳过这个 Section
            }
            cur.add(s);
            if (!TimeConflictDetector.hasConflict(cur)) {
                backtrack(byCourse, idx + 1, cur, out, limit, globalExclusions);
            }
            cur.remove(cur.size() - 1);
        }
    }

    private boolean conflictsWithExclusions(Section section, List<MeetingTime> excluded) {
        if (excluded == null || excluded.isEmpty()) {
            return false;
        }


        for (var secMt : section.meetingTimes()) {

            for (var excMt : excluded) {


                if (secMt.day().equalsIgnoreCase(excMt.day())) {


                    if (TimeConflictDetector.hasOverlap(
                            secMt.start(), secMt.end(),
                            excMt.start(), excMt.end()
                    )) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}






